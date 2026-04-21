package com.uniesp.domain.service;

import com.uniesp.domain.exception.BusinessException;

/**
 * Migra as validações que no Go eram feitas inline no handler:
 *
 * Go original:
 *   name := r.URL.Query().Get("name")
 *   if name == "" {
 *       w.WriteHeader(http.StatusBadRequest)
 *       return
 *   }
 *
 * Agora: validações explícitas com mensagens de erro claras
 */
public class UserValidationService {

    private static final int NOME_MIN  = 2;
    private static final int NOME_MAX  = 100;
    private static final int EMAIL_MAX = 150;

    private UserValidationService() {}

    public static void validarNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new BusinessException("O nome é obrigatório");
        }
        if (nome.length() < NOME_MIN || nome.length() > NOME_MAX) {
            throw new BusinessException(
                    "O nome deve ter entre " + NOME_MIN + " e " + NOME_MAX + " caracteres");
        }
    }

    public static void validarEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new BusinessException("O e-mail é obrigatório");
        }
        if (!email.matches("^[\\w._%+\\-]+@[\\w.\\-]+\\.[a-zA-Z]{2,}$")) {
            throw new BusinessException("Formato de e-mail inválido: " + email);
        }
        if (email.length() > EMAIL_MAX) {
            throw new BusinessException("E-mail deve ter no máximo " + EMAIL_MAX + " caracteres");
        }
    }
}