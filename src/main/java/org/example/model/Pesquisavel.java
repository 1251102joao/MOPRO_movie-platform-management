package org.example.model;

/**
 * Interface que define o contrato de pesquisa para recursos da plataforma.
 * Classes que implementem esta interface podem ser pesquisadas por texto.
 */
public interface Pesquisavel {

    /**
     * Verifica se o objeto corresponde ao texto de pesquisa fornecido.
     *
     * @param texto texto a pesquisar
     * @return true se corresponder, false caso contrário
     */
    boolean correspondePesquisa(String texto);
}
