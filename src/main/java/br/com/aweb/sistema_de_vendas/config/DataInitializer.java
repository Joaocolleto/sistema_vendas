package br.com.aweb.sistema_de_vendas.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.aweb.sistema_de_vendas.model.Usuario;
import br.com.aweb.sistema_de_vendas.model.enums.UsuarioRole;
import br.com.aweb.sistema_de_vendas.repository.UsuarioRepository;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner inicializarDados(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Cria usuário ADMIN se não existir
            if (usuarioRepository.findByEmailIgnoreCase("admin@sistema.com").isEmpty()) {
                Usuario admin = new Usuario(
                    "Administrador",
                    "admin@sistema.com",
                    passwordEncoder.encode("admin123"),
                    UsuarioRole.ADMIN
                );
                usuarioRepository.save(admin);
                System.out.println("[DataInitializer] Usuário ADMIN criado: admin@sistema.com");
            }

            // Cria usuário PADRAO se não existir
            if (usuarioRepository.findByEmailIgnoreCase("vendedor@sistema.com").isEmpty()) {
                Usuario vendedor = new Usuario(
                    "Vendedor Padrão",
                    "vendedor@sistema.com",
                    passwordEncoder.encode("vendas123"),
                    UsuarioRole.PADRAO
                );
                usuarioRepository.save(vendedor);
                System.out.println("[DataInitializer] Usuário PADRAO criado: vendedor@sistema.com");
            }
        };
    }
}
