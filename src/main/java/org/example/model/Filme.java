package org.example.model;

import java.util.ArrayList;

/**
 * Representa um filme na plataforma.
 * Herda de Recurso e implementa MarcavelComoVisto.
 * Agrega atores (agregação) — um ator existe independentemente do filme.
 * Os vistos e classificações são geridos pela superclasse Recurso.
 */
public class Filme extends Recurso implements MarcavelComoVisto {

    /** Duração do filme em minutos. */
    private int duracao;

    /** Lista de atores do filme (agregação). */
    private ArrayList<Ator> atores;

    /**
     * Constrói um filme.
     *
     * @param titulo  título do filme
     * @param ano     ano de lançamento
     * @param duracao duração em minutos
     */
    public Filme(String titulo, int ano, int duracao) {
        super(titulo, ano);
        this.duracao = duracao;
        this.atores = new ArrayList<>();
    }

    /**
     * Devolve a duração do filme em minutos.
     *
     * @return duração
     */
    public int getDuracao() {
        return duracao;
    }

    /**
     * Devolve a lista de atores do filme.
     *
     * @return lista de atores
     */
    public ArrayList<Ator> getAtores() {
        return atores;
    }

    /**
     * Associa um ator ao filme.
     *
     * @param ator ator a associar
     * @throws Exception se o ator já estiver associado
     */
    public void adicionarAtor(Ator ator) throws Exception {
        if (atores.contains(ator)) {
            throw new Exception("O ator '" + ator.getNome() + "' já está associado a este filme.");
        }
        atores.add(ator);
    }

    @Override
    public String getCategoriaClassificacao() {
        double media = getClassificacaoMedia();
        if (media == 0) return "Sem classificação";
        if (media < 4) return "Fraco";
        if (media <= 8) return "Médio";
        return "Bom";
    }

    @Override
    public String toString() {
        return String.format("\"%s\" (%d) | %d min | Géneros: %s | Atores: %d | Classificação: %.1f/10 [%s]",
                titulo, ano, duracao, generosToString(), atores.size(),
                getClassificacaoMedia(), getCategoriaClassificacao());
    }
}
