package mundia.louki.smart_bank.dtos;

import lombok.Data;

@Data
public class BankAccountDTO {
    private String type; // Pour distinguer SA (Saving) ou CA (Current) dans le JSON
    // Pas d'abstract ici pour faciliter la sérialisation JSON si nécessaire,
    // mais en pratique on instanciera les classes filles.
}