package org.example.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Representa um episódio de uma temporada de uma série.
 * Implementa MarcavelComoVisto — pode ser marcado como visto por espectadores.
 * Guarda vistos e classificações como strings de nomes, evitando referências circulares.
 */
public class Episodio implements MarcavelComoVisto, Serializable {

    /** Título do episódio. */
    private String titulo;

    /** Duração do episódio em minutos. */
    private int duracao;

    /** Número do episódio na temporada. */
    private int numero;

    /** Lista de atores que participam no episódio. */
    private ArrayList<Ator> atores;

    /** Nomes dos espectadores que viram este episódio (evita referência circular). */
    private ArrayList<String> vistos;

    /** Lista de classificações atribuídas ao episódio. */
    private ArrayList<Classificacao> classificacoes;

    /**
     * Constrói um episódio.
     *
     * @param titulo  título do episódio
     * @param numero  número do episódio na temporada
     * @param duracao duração em minutos
     */
    public Episodio(String titulo, int numero, int duracao) {
        this.titulo = titulo;
        this.numero = numero;
        this.duracao = duracao;
        this.atores = new ArrayList<>();
        this.vistos = new ArrayList<>();
        this.classificacoes = new ArrayList<>();
    }

    /**
     * Devolve o título do episódio.
     *
     * @return título
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Devolve o número do episódio.
     *
     * @return número
     */
    public int getNumero() {
        return numero;
    }

    /**
     * Devolve a duração do episódio.
     *
     * @return duração em minutos
     */
    public int getDuracao() {
        return duracao;
    }

    /**
     * Devolve a lista de atores do episódio.
     *
     * @return lista de atores
     */
    public ArrayList<Ator> getAtores() {
        return atores;
    }

    /**
     * Devolve a lista de classificações do episódio.
     *
     * @return lista de classificações
     */
    public ArrayList<Classificacao> getClassificacoes() {
        return classificacoes;
    }

    /**
     * Associa um ator ao episódio.
     *
     * @param ator ator a associar
     * @throws Exception se o ator já estiver associado
     */
    public void adicionarAtor(Ator ator) throws Exception {
        if (atores.contains(ator)) {
            throw new Exception("O ator '" + ator.getNome() + "' já está associado a este episódio.");
        }
        atores.add(ator);
    }

    /**
     * Adiciona uma classificação ao episódio.
     *
     * @param classificacao classificação a adicionar
     * @throws Exception se o espectador não tiver visto o episódio ou já o tiver classificado
     */
    public void adicionarClassificacao(Classificacao classificacao) throws Exception {
        if (!vistos.contains(classificacao.getNomeEspectador())) {
            throw new Exception("Só pode classificar episódios que já viu.");
        }
        for (Classificacao c : classificacoes) {
            if (c.getNomeEspectador().equals(classificacao.getNomeEspectador())) {
                throw new Exception("Já classificou este episódio.");
            }
        }
        classificacoes.add(classificacao);
    }

    /**
     * Calcula a classificação média do episódio.
     *
     * @return média das classificações, ou 0 se não houver
     */
    public double getClassificacaoMedia() {
        if (classificacoes.isEmpty()) return 0;
        double soma = 0;
        for (Classificacao c : classificacoes) soma += c.getNota();
        return soma / classificacoes.size();
    }

    @Override
    public boolean isVisto(Espectador espectador) {
        return vistos.contains(espectador.getNome());
    }

    @Override
    public void marcarComoVisto(Espectador espectador) throws Exception {
        if (isVisto(espectador)) {
            throw new Exception("Este episódio já foi marcado como visto.");
        }
        vistos.add(espectador.getNome());
    }

    @Override
    public String toString() {
        return String.format("  Ep.%d - %s (%d min) | Atores: %d | Classificação: %.1f/10",
                numero, titulo, duracao, atores.size(), getClassificacaoMedia());
    }
}
