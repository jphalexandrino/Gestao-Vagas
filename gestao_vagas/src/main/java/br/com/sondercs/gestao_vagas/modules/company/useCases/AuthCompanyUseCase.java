package br.com.sondercs.gestao_vagas.modules.company.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.sondercs.gestao_vagas.modules.company.dto.AuthCompanyDTO;
import br.com.sondercs.gestao_vagas.modules.company.repositories.CompanyRepository;
import jakarta.security.auth.message.AuthException;

@Service
public class AuthCompanyUseCase {

    @Value("${security.token.secret}")
    private String secretKey;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String execute(AuthCompanyDTO authCompanyDTO) throws AuthException{
        var company = this.companyRepository.findByUsername(authCompanyDTO.getUsername()).orElseThrow(
            () -> {
                throw new UsernameNotFoundException("Username/Password incorrect");
            });
            // Verificar a senha
            var passwordMatches = this.passwordEncoder.matches(authCompanyDTO.getPassword(), company.getPassword());
            // Se não for igual:
            if (!passwordMatches) {
                throw new AuthException();
            }
            // Se for igual
            Algorithm algorithm = Algorithm.HMAC256("secretKey");
            var token = JWT.create().withIssuer("javagas")
            .withSubject(company.getId().toString())
            .sign(algorithm);
            return token;
    }  
}
