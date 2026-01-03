package mundia.louki.smart_bank.dtos;

import lombok.Data;
import mundia.louki.smart_bank.enums.AccountStatus;
import java.util.Date;
import lombok.EqualsAndHashCode; // <--- Import nécessaire


@Data
@EqualsAndHashCode(callSuper = true)
public class SavingBankAccountDTO extends BankAccountDTO {
    private String id;
    private double balance;
    private Date createdAt;
    private AccountStatus status;
    private String currency;
    private double interestRate; // Spécifique Épargne
    private CustomerDTO customerDTO;
}