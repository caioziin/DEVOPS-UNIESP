package com.uniesp.infrastructure.config;

import com.uniesp.application.port.out.ProductOutputPort;
import com.uniesp.application.port.out.UserOutputPort;
import com.uniesp.application.useCase.ProductUseCase;
import com.uniesp.application.useCase.UserUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Aqui o Spring monta o grafo de dependências
// O domínio e application nunca usam @Component — ficam limpos
@Configuration
public class BeanConfig {

    @Bean
    public UserUseCase userUseCase(UserOutputPort port) {
        return new UserUseCase(port);
    }

    @Bean
    public ProductUseCase productUseCase(ProductOutputPort port) {
        return new ProductUseCase(port);
    }
}