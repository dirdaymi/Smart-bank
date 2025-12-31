package ma.mundiapolis.smart_bank.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("SA")
@Data @NoArgsConstructor @EqualsAndHashCode(callSuper = true)
public class SavingAccount extends BankAccount {
    private double interestRate;
}