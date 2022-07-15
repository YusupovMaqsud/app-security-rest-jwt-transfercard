package uz.pdp.appsecurityrestjwttransfercard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appsecurityrestjwttransfercard.entity.Card;
import uz.pdp.appsecurityrestjwttransfercard.entity.Income;

import java.util.Optional;

public interface IncomeRepository extends JpaRepository<Income,Integer> {
}
