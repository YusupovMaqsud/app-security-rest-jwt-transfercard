package uz.pdp.appsecurityrestjwttransfercard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appsecurityrestjwttransfercard.entity.Card;
import uz.pdp.appsecurityrestjwttransfercard.entity.Income;
import uz.pdp.appsecurityrestjwttransfercard.entity.Outcome;
import uz.pdp.appsecurityrestjwttransfercard.payload.CardDto;
import uz.pdp.appsecurityrestjwttransfercard.payload.OutcomeDto;
import uz.pdp.appsecurityrestjwttransfercard.repository.CardRepository;
import uz.pdp.appsecurityrestjwttransfercard.repository.IncomeRepository;
import uz.pdp.appsecurityrestjwttransfercard.repository.OutcomeRepository;
import uz.pdp.appsecurityrestjwttransfercard.security.JwtProvider;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class OutcomeController {
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    CardRepository cardRepository;
    @Autowired
    IncomeRepository incomeRepository;
    @Autowired
    OutcomeRepository outcomeRepository;


    @PostMapping("/outcome")
    public HttpEntity<?> addOutcome(@RequestBody OutcomeDto outcomeDto, HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("Authorization");

        token = token.substring(7);

        String username = jwtProvider.getUsernameFromToken(token);


        Optional<Card> optionalFromCard = cardRepository.findById(outcomeDto.getFromCardId());
        if (!optionalFromCard.isPresent()) return ResponseEntity.ok("from card not found!");

        Optional<Card> optionalToCard = cardRepository.findById(outcomeDto.getToCardId());
        if (!optionalToCard.isPresent()) return ResponseEntity.ok("To card not found!");

        if (outcomeDto.getAmount() < 0) return ResponseEntity.ok("Manfiy bo'lamsin");

        if (!username.equals(optionalFromCard.get().getUsername()))
            return ResponseEntity.ok("BU sizni kartangiz emas!");

        if (outcomeDto.getAmount() > optionalFromCard.get().getBalance() - (outcomeDto.getAmount() * outcomeDto.getCommisionAmount()))
            return ResponseEntity.ok("Pul mablagi yetarli emas!");

        Outcome outcome = new Outcome();

        outcome.setAmount(outcomeDto.getAmount());
        outcome.setCommisionAmount(outcomeDto.getCommisionAmount());
        outcome.setFromCard(optionalFromCard.get());
        outcome.setToCard(optionalToCard.get());
        outcome.setDate(new Date(System.currentTimeMillis()));

        outcomeRepository.save(outcome);
        Income income = new Income();

        income.setAmount(outcomeDto.getAmount());
        income.setFromCard(optionalFromCard.get());
        income.setToCard(optionalToCard.get());
        income.setDate(new Date(System.currentTimeMillis()));

        incomeRepository.save(income);

        Card fromCard = optionalFromCard.get();

        Card toCard = optionalToCard.get();

        toCard.setBalance(toCard.getBalance() + outcomeDto.getAmount());

        double total = fromCard.getBalance() - outcomeDto.getAmount() - (outcomeDto.getAmount() * outcomeDto.getCommisionAmount());
        fromCard.setBalance(total);

        cardRepository.save(fromCard);
        cardRepository.save(toCard);

        return ResponseEntity.ok("Muvaffaqiyatli bajarildi!");
    }


    @GetMapping("/income")
    public HttpEntity<?> getIncome() {
        List<Income> incomes = incomeRepository.findAll();
        return ResponseEntity.ok(incomes);
    }

    @GetMapping("/outcome")
    private HttpEntity<?> getOutcome() {
        List<Outcome> outcomes = outcomeRepository.findAll();
        return ResponseEntity.ok(outcomes);
    }

}
