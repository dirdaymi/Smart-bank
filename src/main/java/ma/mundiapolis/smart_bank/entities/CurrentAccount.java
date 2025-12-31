package ma.mundiapolis.smart_bank.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("CA")
@Data @NoArgsConstructor @EqualsAndHashCode(callSuper = true)
public class CurrentAccount extends BankAccount {
    private double overDraft; // Découvert autorisé
}