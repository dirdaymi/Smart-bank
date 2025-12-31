package ma.mundiapolis.smart_bank.bot;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.stereotype.Service;

@Service
public class ChatAgent {

    private final ChatModel chatModel;

    // Injection automatique du modèle Gemini configuré par Spring AI
    public ChatAgent(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public String chat(String userMessage) {
        try {
            // Appel direct à Gemini
            return chatModel.call(userMessage);
        } catch (Exception e) {
            return "Désolé, je rencontre une erreur avec l'IA : " + e.getMessage();
        }
    }
}