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
import uz.pdp.appsecurityrestjwttransfercard.payload.IncomeDto;
import uz.pdp.appsecurityrestjwttransfercard.payload.OutcomeDto;
import uz.pdp.appsecurityrestjwttransfercard.repository.CardRepository;
import uz.pdp.appsecurityrestjwttransfercard.repository.IncomeRepository;
import uz.pdp.appsecurityrestjwttransfercard.repository.OutcomeRepository;
import uz.pdp.appsecurityrestjwttransfercard.security.JwtProvider;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/card")
public class CardController {
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    CardRepository cardRepository;


    @PostMapping
    public HttpEntity<?> addCard(@RequestBody CardDto cardDto, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        token = token.substring(7);
        String username = jwtProvider.getUsernameFromToken(token);
        if (cardRepository.existsByNumber(cardDto.getCardNumber())) {
            Card card = new Card();
            card.setUsername(username);
            card.setCardNumber(cardDto.getCardNumber());
            card.setExpiredDate(cardDto.getExpiredDate());
            cardRepository.save(card);
        }
            return ResponseEntity.ok("Karta Ochildi!");
    }


}
