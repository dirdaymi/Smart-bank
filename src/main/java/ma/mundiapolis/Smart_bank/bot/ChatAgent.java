package ma.mundiapolis.Smart_bank.bot;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.spring.AiService;

@AiService
public interface ChatAgent {
    @SystemMessage("Tu es un assistant bancaire intelligent. Tu aides les clients à vérifier leurs comptes. " +
            "Si tu as besoin d'infos, utilise les tools disponibles.")
    String chat(String userMessage);
}