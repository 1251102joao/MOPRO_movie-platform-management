package org.example.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Representa uma temporada de uma série.
 * Cada temporada é composta por episódios (composição) —
 * os episódios não existem fora da temporada.
 */
public class Temporada implements Serializable {

    /** Número da temporada. */
    private int numero;

    /** Lista de episódios da temporada (composição). */
    private ArrayList<Episodio> episodios;

    /**
     * Constrói uma temporada com o número indicado.
     *
     * @param numero número da temporada
     */
    public Temporada(int numero) {
        this.numero = numero;
        this.episodios = new ArrayList<>();
    }

    /**
     * Devolve o número da temporada.
     *
     * @return número da temporada
     */
    public int getNumero() {
        return numero;
    }

    /**
     * Devolve a lista de episódios da temporada.
     *
     * @return lista de episódios
     */
    public ArrayList<Episodio> getEpisodios() {
        return episodios;
    }

    /**
     * Adiciona um episódio à temporada.
     *
     * @param episodio episódio a adicionar
     * @throws Exception se já existir um episódio com o mesmo número
     */
    public void adicionarEpisodio(Episodio episodio) throws Exception {
        for (Episodio e : episodios) {
            if (e.getNumero() == episodio.getNumero()) {
                throw new Exception("Já existe um episódio com o número " + episodio.getNumero() + " nesta temporada.");
            }
        }
        episodios.add(episodio);
    }

    /**
     * Pesquisa um episódio pelo número.
     *
     * @param numero número do episódio
     * @return episódio encontrado ou null
     */
    public Episodio getEpisodio(int numero) {
        for (Episodio e : episodios) {
            if (e.getNumero() == numero) return e;
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Temporada " + numero + " (" + episodios.size() + " episódios)");
        for (Episodio e : episodios) {
            sb.append("\n").append(e);
        }
        return sb.toString();
    }
}
