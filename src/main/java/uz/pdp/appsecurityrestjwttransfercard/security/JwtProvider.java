package uz.pdp.appsecurityrestjwttransfercard.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {
    String kalitSuz="MaxfiySuz123456789";
    long expireTime=36_000_000;
    public String generateToken(String username){
        String token = Jwts
                .builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(SignatureAlgorithm.HS512, kalitSuz)
                .compact();
        return token;
    }



    public boolean validateToken(String token){
         try {
             Jwts
                     .parser()
                     .setSigningKey(kalitSuz)
                     .parseClaimsJws(token);
         }catch (Exception e){
             e.printStackTrace();
         }
         return false;
    }




    public String getUsernameFromToken(String token){
        String username = Jwts
                .parser()
                .setSigningKey(kalitSuz)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

        return username;
    }
}
