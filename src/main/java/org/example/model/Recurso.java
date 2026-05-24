package org.example.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Classe abstrata que representa um recurso da plataforma (filme ou série).
 * Implementa Pesquisavel para pesquisa por título.
 * Guarda as classificações e a lista de espectadores que viram o recurso,
 * evitando referências circulares com Espectador.
 * Serve de base para Filme e Serie.
 */
public abstract class Recurso implements Pesquisavel, Serializable {

    /** Título do recurso. */
    protected String titulo;

    /** Ano de lançamento do recurso. */
    protected int ano;

    /** Lista de géneros do recurso. */
    protected ArrayList<Genero> generos;

    /** Lista de nomes de espectadores que viram este recurso. */
    protected ArrayList<String> vistos;

    /** Lista de classificações atribuídas ao recurso. */
    protected ArrayList<Classificacao> classificacoes;

    /**
     * Constrói um recurso com título e ano.
     *
     * @param titulo título do recurso
     * @param ano    ano de lançamento
     */
    public Recurso(String titulo, int ano) {
        this.titulo = titulo;
        this.ano = ano;
        this.generos = new ArrayList<>();
        this.vistos = new ArrayList<>();
        this.classificacoes = new ArrayList<>();
    }

    /**
     * Devolve o título do recurso.
     *
     * @return título
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Devolve o ano do recurso.
     *
     * @return ano
     */
    public int getAno() {
        return ano;
    }

    /**
     * Devolve a lista de géneros do recurso.
     *
     * @return lista de géneros
     */
    public ArrayList<Genero> getGeneros() {
        return generos;
    }

    /**
     * Devolve a lista de classificações do recurso.
     *
     * @return lista de classificações
     */
    public ArrayList<Classificacao> getClassificacoes() {
        return classificacoes;
    }

    /**
     * Adiciona um género ao recurso.
     *
     * @param genero género a adicionar
     * @throws Exception se o género já estiver associado
     */
    public void adicionarGenero(Genero genero) throws Exception {
        if (generos.contains(genero)) {
            throw new Exception("O género '" + genero + "' já está associado a este recurso.");
        }
        generos.add(genero);
    }

    /**
     * Verifica se o recurso foi visto pelo espectador indicado.
     *
     * @param espectador espectador a verificar
     * @return true se já foi visto
     */
    public boolean isVisto(Espectador espectador) {
        return vistos.contains(espectador.getNome());
    }

    /**
     * Marca o recurso como visto pelo espectador.
     *
     * @param espectador espectador que viu o recurso
     * @throws Exception se já tiver sido marcado como visto
     */
    public void marcarComoVisto(Espectador espectador) throws Exception {
        if (isVisto(espectador)) {
            throw new Exception("Este recurso já foi marcado como visto.");
        }
        vistos.add(espectador.getNome());
    }

    /**
     * Adiciona uma classificação ao recurso.
     *
     * @param classificacao classificação a adicionar
     * @throws Exception se o espectador não tiver visto o recurso ou já o tiver classificado
     */
    public void adicionarClassificacao(Classificacao classificacao) throws Exception {
        if (!vistos.contains(classificacao.getNomeEspectador())) {
            throw new Exception("Só pode classificar recursos que já viu.");
        }
        for (Classificacao c : classificacoes) {
            if (c.getNomeEspectador().equals(classificacao.getNomeEspectador())) {
                throw new Exception("Já classificou este recurso.");
            }
        }
        classificacoes.add(classificacao);
    }

    /**
     * Calcula a classificação média do recurso.
     *
     * @return média das classificações, ou 0 se não houver
     */
    public double getClassificacaoMedia() {
        if (classificacoes.isEmpty()) return 0;
        double soma = 0;
        for (Classificacao c : classificacoes) soma += c.getNota();
        return soma / classificacoes.size();
    }

    /**
     * Devolve a categoria qualitativa com base na classificação média.
     *
     * @return "Fraco", "Médio" ou "Bom"
     */
    public abstract String getCategoriaClassificacao();

    @Override
    public boolean correspondePesquisa(String texto) {
        return titulo.toLowerCase().contains(texto.toLowerCase());
    }

    /**
     * Devolve a representação em texto dos géneros do recurso.
     *
     * @return géneros como string
     */
    protected String generosToString() {
        if (generos.isEmpty()) return "(sem géneros)";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < generos.size(); i++) {
            sb.append(generos.get(i));
            if (i < generos.size() - 1) sb.append(", ");
        }
        return sb.toString();
    }
}
