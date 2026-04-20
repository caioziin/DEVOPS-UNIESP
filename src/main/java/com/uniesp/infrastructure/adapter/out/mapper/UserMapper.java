package com.uniesp.infrastructure.adapter.out.mapper;

import com.uniesp.domain.model.User;
import com.uniesp.infrastructure.adapter.out.entity.UserEntity;

// SRP: única responsabilidade — converter entre domínio e entity
// OCP: para mudar o mapeamento, muda só aqui sem tocar em mais nada
public class UserMapper {

    private UserMapper() {}

    public static UserEntity toEntity(User user) {
        UserEntity e = new UserEntity();
        e.setId(user.getId());
        e.setName(user.getName());
        e.setEmail(user.getEmail());
        e.setCreatedAt(user.getCreatedAt());
        return e;
    }

    public static User toDomain(UserEntity e) {
        return new User(e.getId(), e.getName(), e.getEmail(), e.getCreatedAt());
    }
}