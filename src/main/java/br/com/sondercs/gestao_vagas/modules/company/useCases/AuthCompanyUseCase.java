package br.com.sondercs.gestao_vagas.modules.company.useCases;

import java.time.Instant;
import java.util.Arrays;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.sondercs.gestao_vagas.modules.company.dto.AuthCompanyDTO;
import br.com.sondercs.gestao_vagas.modules.company.dto.AuthCompanyResponseDTO;
import br.com.sondercs.gestao_vagas.modules.company.repositories.CompanyRepository;

@Service
public class AuthCompanyUseCase {

    @Value("${security.token.secret}")
    private String secretKey;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthCompanyResponseDTO execute(AuthCompanyDTO authCompanyDTO) throws AuthenticationException{
        var company = this.companyRepository.findByUsername(authCompanyDTO.getUsername()).orElseThrow(
            () -> {
                throw new UsernameNotFoundException("Username/Password incorrect");
            });
            // Verificar a senha
            var passwordMatches = this.passwordEncoder.matches(authCompanyDTO.getPassword(), company.getPassword());
            // Se não for igual:
            if (!passwordMatches) {
                throw new AuthenticationException();
            }
            // Se for igual
            Algorithm algorithm = Algorithm.HMAC256(secretKey);

            var expires_in = Instant.now().plus(java.time.Duration.ofHours(2));

            var token = JWT.create().withIssuer("javagas")
            .withSubject(company.getId().toString())
            .withExpiresAt(expires_in)
            .withClaim("roles", Arrays.asList("COMPANY"))
            .sign(algorithm);

            var AuthCandidateResponseDTO = AuthCompanyResponseDTO.builder()
            .access_token(token)
            .expires_in(expires_in.toEpochMilli())
            .build();

        return AuthCandidateResponseDTO;
    }  
}
