package uz.pdp.appsecurityrestjwttransfercard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appsecurityrestjwttransfercard.entity.Card;
import uz.pdp.appsecurityrestjwttransfercard.entity.Outcome;

import java.util.Optional;

public interface OutcomeRepository extends JpaRepository<Outcome,Integer> {
}
