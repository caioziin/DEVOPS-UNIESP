package com.uniesp.infrastructure.adapter.out;

import com.uniesp.application.port.out.UserOutputPort;
import com.uniesp.domain.model.User;
import com.uniesp.infrastructure.adapter.out.mapper.UserMapper;
import com.uniesp.infrastructure.adapter.out.repository.UserJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserRepositoryAdapter implements UserOutputPort {

    private final UserJpaRepository jpa;

    public UserRepositoryAdapter(UserJpaRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public List<User> findAll() {
        return jpa.findAll().stream().map(UserMapper::toDomain).toList();
    }

    @Override
    public Optional<User> findById(Long id) {
        return jpa.findById(id).map(UserMapper::toDomain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpa.existsByEmail(email);
    }

    @Override
    public User save(User user) {
        return UserMapper.toDomain(jpa.save(UserMapper.toEntity(user)));
    }

    @Override
    public void deleteById(Long id) {
        jpa.deleteById(id);
    }
}