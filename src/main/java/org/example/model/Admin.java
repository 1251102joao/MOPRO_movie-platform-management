package org.example.model;

/**
 * Representa um administrador da plataforma.
 * Herda de UtilizadorRegistado e tem acesso total ao sistema.
 */
public class Admin extends UtilizadorRegistado {

    /**
     * Constrói um administrador.
     *
     * @param email    email do administrador
     * @param nome     nome/username do administrador
     * @param password password do administrador
     */
    public Admin(String email, String nome, String password) {
        super(email, nome, password);
    }
}
