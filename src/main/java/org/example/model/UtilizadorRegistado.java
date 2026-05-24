package org.example.model;

import java.io.Serializable;

/**
 * Classe abstrata que representa um utilizador registado na plataforma.
 * Serve de base para Admin e Espectador.
 */
public abstract class UtilizadorRegistado implements Serializable {

    /** Email do utilizador. */
    private String email;

    /** Nome/username do utilizador. */
    private String nome;

    /** Password do utilizador (não visível externamente). */
    private String password;

    /**
     * Constrói um utilizador registado.
     *
     * @param email    email do utilizador
     * @param nome     nome/username do utilizador
     * @param password password do utilizador
     */
    public UtilizadorRegistado(String email, String nome, String password) {
        this.email = email;
        this.nome = nome;
        this.password = password;
    }

    /**
     * Verifica se a password fornecida corresponde à password do utilizador.
     *
     * @param pass password a verificar
     * @return true se corresponder, false caso contrário
     */
    public boolean temPassord(String pass) {
        return password.equals(pass);
    }

    /**
     * Devolve o nome do utilizador.
     *
     * @return nome do utilizador
     */
    public String getNome() {
        return nome;
    }

    /**
     * Devolve o email do utilizador.
     *
     * @return email do utilizador
     */
    public String getEmail() {
        return email;
    }

    /**
     * Verifica se o utilizador tem o username indicado.
     *
     * @param username username a verificar
     * @return true se corresponder
     */
    public boolean temNome(String username) {
        return username.equals(nome);
    }

    @Override
    public String toString() {
        return nome + " <" + email + ">";
    }
}
