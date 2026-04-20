package com.uniesp.application.port.in;

import com.uniesp.domain.model.User;
import java.util.List;

// O que a aplicação OFERECE para o mundo externo
// Qualquer adapter de entrada (REST, CLI, mensageria) usa esta interface
public interface UserInputPort {

    List<User> findAll();

    User findById(Long id);

    User create(String name, String email);

    User update(Long id, String name, String email);

    void delete(Long id);
}