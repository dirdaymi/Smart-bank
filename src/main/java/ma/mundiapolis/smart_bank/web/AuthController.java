package ma.mundiapolis.smart_bank.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtEncoder jwtEncoder;
    // Note: Pour faire simple, on simule l'auth manager ou on utilise UserDetailsService directement
    // Dans une architecture complète, injecter AuthenticationManager

    @PostMapping("/login")
    public Map<String, String> login(String username, String password) {
        // Simulation d'authentification réussie pour le TP (Normalement AuthenticationManager.authenticate)
        // Génération du Token
        Instant now = Instant.now();
        String scope = "USER"; // Simplifié
        if(username.equals("admin")) scope = "USER ADMIN";

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(10, ChronoUnit.MINUTES))
                .subject(username)
                .claim("scope", scope)
                .build();

        JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters.from(
                JwsHeader.with(MacAlgorithm.HS512).build(),
                claims
        );
        String jwt = jwtEncoder.encode(jwtEncoderParameters).getTokenValue();
        return Map.of("access-token", jwt);
    }
}