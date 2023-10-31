package br.com.sondercs.gestao_vagas.modules.company.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.sondercs.gestao_vagas.modules.company.dto.AuthCompanyDTO;
import br.com.sondercs.gestao_vagas.modules.company.repositories.CompanyRepository;
import jakarta.security.auth.message.AuthException;

@Service
public class AuthCompanyUseCase {


    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void execute(AuthCompanyDTO authCompanyDTO) throws AuthException{
        var company = this.companyRepository.findByUsername(authCompanyDTO.getUsername()).orElseThrow(
            () -> {
                throw new UsernameNotFoundException("Company not found");
            });
            // Verificar a senha
            var passwordMatches = this.passwordEncoder.matches(authCompanyDTO.getPassword(), company.getPassword());
            // Se não for igual:
            if (!passwordMatches) {
                throw new AuthException();
            }
            // Se for igual
    }
}
