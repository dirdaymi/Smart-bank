package mundia.louki.smart_bank.web;

import mundia.louki.smart_bank.services.ai.BankAccountAIService;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/ai")
@CrossOrigin(origins = "*")
public class ChatBotController {

    private final BankAccountAIService aiService;

    public ChatBotController(BankAccountAIService aiService) {
        this.aiService = aiService;
    }

    @PostMapping("/chat")
    public Map<String, String> chat(@RequestBody Map<String, String> payload) {
        String question = payload.get("question");
        String accountId = payload.get("accountId");

        System.out.println("Question reçue: " + question);
        System.out.println("ID Compte demandé: " + accountId);

        try {
            // On tente d'appeler le service IA
            String response = aiService.askQuestion(question, accountId);
            return Map.of("response", response);
        } catch (Exception e) {
            // En cas d'erreur (ID introuvable, pas de clé API, etc.)
            e.printStackTrace(); // Affiche l'erreur complète dans la console Java
            return Map.of("response", "Erreur Backend : " + e.getMessage());
        }
    }
}