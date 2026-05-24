package org.example.model;

import org.example.utils.Data;
import java.io.Serializable;

/**
 * Representa um ator que pode participar em filmes e episódios.
 * Implementa Pesquisavel para permitir pesquisa por nome.
 * Um ator pode participar em vários filmes/episódios (agregação).
 */
public class Ator implements Pesquisavel, Serializable {

    /** Nome do ator. */
    private String nome;

    /** Data de nascimento do ator. */
    private Data dataNascimento;

    /**
     * Constrói um ator com nome e data de nascimento.
     *
     * @param nome           nome do ator
     * @param dataNascimento data de nascimento do ator
     */
    public Ator(String nome, Data dataNascimento) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
    }

    /**
     * Devolve o nome do ator.
     *
     * @return nome do ator
     */
    public String getNome() {
        return nome;
    }

    /**
     * Verifica se o ator tem o nome indicado (ignora maiúsculas/minúsculas).
     *
     * @param nome nome a verificar
     * @return true se corresponder
     */
    public boolean temNome(String nome) {
        return this.nome.equalsIgnoreCase(nome);
    }

    @Override
    public boolean correspondePesquisa(String texto) {
        return nome.toLowerCase().contains(texto.toLowerCase());
    }

    @Override
    public String toString() {
        return nome + " [" + dataNascimento + "]";
    }
}
