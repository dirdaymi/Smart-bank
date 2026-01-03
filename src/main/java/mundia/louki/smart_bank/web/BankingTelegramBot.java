package mundia.louki.smart_bank.web;

import mundia.louki.smart_bank.services.ai.BankAccountAIService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class BankingTelegramBot extends TelegramLongPollingBot {

    private final BankAccountAIService bankAccountAIService;

    @Value("${telegram.bot.username}")
    private String botUsername;

    @Value("${telegram.bot.token}")
    private String botToken;

    public BankingTelegramBot(BankAccountAIService bankAccountAIService) {
        this.bankAccountAIService = bankAccountAIService;
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            System.out.println("📩 Message reçu : " + messageText);

            // 1. Détection intelligente de l'ID de compte (UUID)
            // On cherche un motif du type : xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx
            String accountId = extractAccountId(messageText);

            // Si on a trouvé un ID, on peut le nettoyer du message pour ne garder que la question
            // (Optionnel, ici on envoie tout le message, l'IA triera)

            String response;
            try {
                // On appelle le service IA avec l'ID (s'il existe) ou null
                response = bankAccountAIService.askQuestion(messageText, accountId);
            } catch (Exception e) {
                e.printStackTrace();
                response = "Oups, une erreur technique est survenue.";
            }

            sendResponse(chatId, response);
        }
    }

    // Méthode utilitaire pour trouver un UUID dans une phrase
    private String extractAccountId(String message) {
        // Regex pour un UUID standard (8-4-4-4-12 caractères hexadécimaux)
        Pattern uuidPattern = Pattern.compile("[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}");
        Matcher matcher = uuidPattern.matcher(message);

        if (matcher.find()) {
            String foundId = matcher.group();
            System.out.println("🕵️ ID détecté dans le message : " + foundId);
            return foundId;
        }
        return null; // Aucun ID trouvé
    }

    private void sendResponse(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            System.err.println("Erreur d'envoi Telegram : " + e.getMessage());
        }
    }
}