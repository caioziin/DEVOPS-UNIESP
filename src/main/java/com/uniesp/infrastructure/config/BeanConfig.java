package com.uniesp.infrastructure.config;

import com.uniesp.application.port.in.ProductInputPort;
import com.uniesp.application.port.in.UserInputPort;
import com.uniesp.application.port.out.ProductOutputPort;
import com.uniesp.application.port.out.UserOutputPort;
import com.uniesp.application.useCase.ProductUseCase;
import com.uniesp.application.useCase.UserUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    // DIP: retorna a INTERFACE (UserInputPort), não o tipo concreto
    // O Spring injeta UserInputPort em qualquer lugar que precisar
    @Bean
    public UserInputPort userInputPort(UserOutputPort port) {
        return new UserUseCase(port);
    }

    @Bean
    public ProductInputPort productInputPort(ProductOutputPort port) {
        return new ProductUseCase(port);
    }
}