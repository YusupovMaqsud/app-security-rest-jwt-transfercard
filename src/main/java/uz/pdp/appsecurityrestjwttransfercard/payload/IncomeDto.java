package uz.pdp.appsecurityrestjwttransfercard.payload;

import lombok.Data;

import java.util.Date;

@Data
public class IncomeDto {
    private Integer fromCardId;
    private Integer toCardId;
    private double amount;
    private Date date;
}
