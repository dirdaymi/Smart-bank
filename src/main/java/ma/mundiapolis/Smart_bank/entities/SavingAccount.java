package ma.mundiapolis.Smart_bank.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@DiscriminatorValue("SA")
@Data @EqualsAndHashCode(callSuper = true)
public class SavingAccount extends BankAccount {
    private double interestRate;
}