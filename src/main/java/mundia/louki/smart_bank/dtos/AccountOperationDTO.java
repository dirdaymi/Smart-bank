package mundia.louki.smart_bank.dtos;

import lombok.Data;
import mundia.louki.smart_bank.enums.OperationType;
import java.util.Date;

@Data
public class AccountOperationDTO {
    private Long id;
    private Date operationDate;
    private double amount;
    private OperationType type;
    private String description;
}