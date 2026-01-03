package mundia.louki.smart_bank.config;


import mundia.louki.smart_bank.web.BankingTelegramBot;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
public class BotConfig {

    @Bean
    public TelegramBotsApi telegramBotsApi(BankingTelegramBot bankingTelegramBot) throws TelegramApiException {
        System.out.println("🚀 Initialisation de l'API Telegram...");

        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);

        try {
            botsApi.registerBot(bankingTelegramBot);
            System.out.println("✅ Bot enregistré et connecté avec succès !");
        } catch (TelegramApiException e) {
            System.err.println("❌ Erreur lors de l'enregistrement du Bot : " + e.getMessage());
            e.printStackTrace();
        }

        return botsApi;
    }
}