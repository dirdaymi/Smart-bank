package mundia.louki.smart_bank.services.ai;

import mundia.louki.smart_bank.entities.BankAccount;
import mundia.louki.smart_bank.services.BankAccountService;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class BankAccountAIService {

    private final ChatModel chatModel;
    private final BankAccountService bankAccountService;

    // ON MET LE TEXTE DIRECTEMENT ICI (Plus de problème de lecture de fichier !)
    private static final String DOCUMENTATION_BANQUE = """
            Document de Référence Bancaire – Smart Bank Maroc
            1. Comptes Bancaires
            1.1 Compte Courant
            Description : Compte principal pour les opérations quotidiennes.
            Frais de tenue de compte : 20 MAD/mois (gratuit pour les moins de 26 ans).
            Retraits aux DAB du réseau : gratuits.

            1.2 Compte Épargne
            Compte sur Carnet :
            Taux actuel : 3,0 % (net d’impôts).
            Plafond de dépôt : 200 000 MAD.
            Compte à Terme (CAT) :
            Taux fixe garanti (exemple : 3,5 % sur 12 mois).
            Dépôt minimum : 15 000 MAD.

            2. Cartes Bancaires
            2.1 Carte de Débit (Visa Classic)
            Plafond Paiement : 30 000 MAD/jour.
            Plafond Retrait : 3 000 MAD/jour.
            Frais : 150 MAD/an (gratuite avec un revenu mensuel > 12 000 MAD sur le compte).
            
            2.2 Carte de Crédit (Visa Premier / Gold)
            Plafond : 75 000 MAD/mois.
            Frais : 900 MAD/an (inclut assurance voyages et assistance).
            Avantages : Cashback de 0,5 % sur tous les achats.

            3. Prêts et Crédits
            3.1 Prêt Personnel
            Montant : 10 000 MAD à 750 000 MAD.
            Taux d’intérêt moyen : 4,5 % (TAEG).

            3.2 Prêt Immobilier
            Taux fixe actuel : 4,2 % (sur 20 ans).

            4. Procédures
            Ouverture de compte : Pièce d’identité, Justificatif de domicile, RIB.
            En cas de fraude : Contactez le 05 22 00 00 00.
            """;

    public BankAccountAIService(ChatModel chatModel, BankAccountService bankAccountService) {
        this.chatModel = chatModel;
        this.bankAccountService = bankAccountService;
    }

    public String askQuestion(String question, String accountId) {
        // 1. Récupération info compte (Base de données)
        String accountInfo = "Le client n'est pas identifié (aucun ID de compte fourni).";

        if(accountId != null && !accountId.isEmpty()){
            try {
                BankAccount account = bankAccountService.getBankAccount(accountId);
                accountInfo = String.format("ID Compte: %s, Solde Actuel: %.2f MAD, Type: %s",
                        account.getId(), account.getBalance(), account.getClass().getSimpleName());
            } catch (Exception e) {
                System.out.println("⚠️ ID détecté mais compte introuvable en base : " + accountId);
            }
        }

        // 2. Création du Prompt
        // On combine la doc statique (Java) + l'info dynamique (BDD) + la question
        String promptText = """
            Tu es l'assistant virtuel expert de la Smart Bank Maroc.
            
            --- DOCUMENTATION OFFICIELLE (Règles à suivre scrupuleusement) ---
            {docBanque}
            
            --- CONTEXTE CLIENT ACTUEL ---
            {accountInfo}
            
            --- QUESTION UTILISATEUR ---
            {question}
            
            Directives :
            1. Si la question porte sur les produits/tarifs, utilise la DOCUMENTATION OFFICIELLE.
            2. Si la question porte sur "mon argent", "mon solde", utilise le CONTEXTE CLIENT.
            3. Réponds de manière courte, professionnelle et serviable.
            """;

        PromptTemplate template = new PromptTemplate(promptText);
        Prompt prompt = template.create(Map.of(
                "docBanque", DOCUMENTATION_BANQUE,
                "accountInfo", accountInfo,
                "question", question
        ));

        // 3. Appel IA
        return chatModel.call(prompt).getResult().getOutput().getText();
    }
}