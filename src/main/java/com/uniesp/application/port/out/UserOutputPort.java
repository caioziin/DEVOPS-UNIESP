package com.uniesp.application.port.out;

import com.uniesp.domain.model.User;
import java.util.List;
import java.util.Optional;

// O que a aplicação PRECISA do mundo externo (banco, API, etc.)
// O use case depende desta interface — nunca do JPA diretamente (DIP do SOLID)
public interface UserOutputPort {

    List<User> findAll();

    Optional<User> findById(Long id);

    boolean existsByEmail(String email);

    User save(User user);

    void deleteById(Long id);
}