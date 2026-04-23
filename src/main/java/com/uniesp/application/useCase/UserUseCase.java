package com.uniesp.application.useCase;

import com.uniesp.application.port.in.UserInputPort;
import com.uniesp.application.port.out.UserOutputPort;
import com.uniesp.domain.exception.BusinessException;
import com.uniesp.domain.exception.ResourceNotFoundException;
import com.uniesp.domain.model.User;
import com.uniesp.domain.service.UserValidationService;

import java.util.List;

public class UserUseCase implements UserInputPort {

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
        // Validação de domínio — corrige o bug Go que só checava name != ""
        UserValidationService.validarNome(name);
        UserValidationService.validarEmail(email);

        if (userOutputPort.existsByEmail(email)) {
            throw new BusinessException("Já existe um usuário com o e-mail: " + email);
        }
        return userOutputPort.save(new User(name, email));
    }

    @Override
    public User update(Long id, String name, String email) {
        User user = findById(id);

        UserValidationService.validarNome(name);
        UserValidationService.validarEmail(email);

        if (userOutputPort.existsByEmail(email) && !user.getEmail().equals(email)) {
            throw new BusinessException("E-mail já em uso: " + email);
        }

        user.updateProfile(name, email);
        return userOutputPort.save(user);
    }

    @Override
    public void delete(Long id) {
        findById(id);
        userOutputPort.deleteById(id);
    }
}