package uz.pdp.appsecurityrestjwttransfercard.payload;

import lombok.Data;

import java.util.Date;

@Data
public class OutcomeDto {
    private Integer fromCardId;
    private Integer toCardId;
    private double amount;
    private double commisionAmount;
}
