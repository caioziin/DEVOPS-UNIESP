package com.uniesp.application.useCase;

import com.uniesp.application.port.in.UserInputPort;
import com.uniesp.application.port.out.UserOutputPort;
import com.uniesp.domain.exception.BusinessException;
import com.uniesp.domain.exception.ResourceNotFoundException;
import com.uniesp.domain.model.User;

import java.util.List;

// SRP: responsável apenas pela orquestração das regras de negócio de User
// DIP: depende da abstração UserOutputPort, não de JPA/banco
public class UserUseCase implements UserInputPort {

    // Injeção via construtor (facilita testes com mock)
    private final UserOutputPort userOutputPort;

    public UserUseCase(UserOutputPort userOutputPort) {
        this.userOutputPort = userOutputPort;
    }

    @Override
    public List<User> findAll() {
        return userOutputPort.findAll();
    }

    @Override
    public User findById(Long id) {
        return userOutputPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário", id));
    }

    @Override
    public User create(String name, String email) {
        // Regra de negócio: e-mail único
        if (userOutputPort.existsByEmail(email)) {
            throw new BusinessException("Já existe um usuário com o e-mail: " + email);
        }
        return userOutputPort.save(new User(name, email));
    }

    @Override
    public User update(Long id, String name, String email) {
        User user = findById(id); // lança 404 se não existir

        boolean emailEmUso = userOutputPort.existsByEmail(email)
                && !user.getEmail().equals(email);
        if (emailEmUso) {
            throw new BusinessException("E-mail já em uso: " + email);
        }

        user.setName(name);
        user.setEmail(email);
        return userOutputPort.save(user);
    }

    @Override
    public void delete(Long id) {
        findById(id); // garante que existe antes de deletar
        userOutputPort.deleteById(id);
    }
}