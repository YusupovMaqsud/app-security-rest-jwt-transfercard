package uz.pdp.appsecurityrestjwttransfercard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appsecurityrestjwttransfercard.entity.Card;

import java.util.Optional;

public interface CardRepository extends JpaRepository<Card,Integer> {
    boolean existsByNumber(String number);
}
