package ma.mundiapolis.smart_bank.bot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    // On injecte l'IA, mais on va gérer le cas où elle est absente
    private final ChatAgent chatAgent;

    @Value("${telegram.bot.username}")
    private String botUsername;

    @Value("${telegram.bot.token}")
    private String botToken;

    public TelegramBot(ChatAgent chatAgent) {
        this.chatAgent = chatAgent;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            String chatId = update.getMessage().getChatId().toString();

            System.out.println(">>> MESSAGE REÇU DEPUIS TELEGRAM : " + messageText);

            String reponseEnvoyer = "";

            try {
                // TENTATIVE 1 : Demander à l'IA
                reponseEnvoyer = chatAgent.chat(messageText);
            } catch (Exception e) {
                // SI L'IA PLANTE (Clé invalide, Quota dépassé...), on ne crash pas !
                System.err.println(">>> ERREUR OPENAI : " + e.getMessage());
                reponseEnvoyer = "Désolé, l'IA est indisponible (Vérifiez la clé API). Mais je suis bien connecté à Telegram !";
            }

            // Envoi de la réponse sur Telegram
            sendMessage(chatId, reponseEnvoyer);
        }
    }

    private void sendMessage(String chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        try {
            execute(message);
            System.out.println(">>> RÉPONSE ENVOYÉE AVEC SUCCÈS !");
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() { return botUsername; }
    @Override
    public String getBotToken() { return botToken; }
}