package ma.mundiapolis.Smart_bank.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@DiscriminatorValue("CA")
@Data @EqualsAndHashCode(callSuper = true)
public class CurrentAccount extends BankAccount {
    private double overDraft;
}