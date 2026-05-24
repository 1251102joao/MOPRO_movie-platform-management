package org.example.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa um espectador (utilizador registado) da plataforma.
 * Herda de UtilizadorRegistado.
 * Pode gerir uma lista pessoal de filmes e episódios.
 */
public class Espectador extends UtilizadorRegistado implements Serializable {

    /** Lista pessoal de filmes do espectador. */
    private ArrayList<Filme> listaFilmes;

    /** Lista pessoal de episódios do espectador. */
    private ArrayList<Episodio> listaEpisodios;

    /**
     * Constrói um espectador.
     *
     * @param email    email do espectador
     * @param nome     nome/username do espectador
     * @param password password do espectador
     */
    public Espectador(String email, String nome, String password) {
        super(email, nome, password);
        this.listaFilmes = new ArrayList<>();
        this.listaEpisodios = new ArrayList<>();
    }

    /**
     * Adiciona um filme à lista pessoal do espectador.
     *
     * @param filme filme a adicionar
     * @throws Exception se o filme já estiver na lista
     */
    public void adicionarFilmeLista(Filme filme) throws Exception {
        if (listaFilmes.contains(filme)) {
            throw new Exception("O filme '" + filme.getTitulo() + "' já está na sua lista pessoal.");
        }
        listaFilmes.add(filme);
    }

    /**
     * Remove um filme da lista pessoal do espectador.
     *
     * @param filme filme a remover
     * @throws Exception se o filme não estiver na lista
     */
    public void removerFilmeLista(Filme filme) throws Exception {
        if (!listaFilmes.remove(filme)) {
            throw new Exception("O filme '" + filme.getTitulo() + "' não está na sua lista pessoal.");
        }
    }

    /**
     * Adiciona um episódio à lista pessoal do espectador.
     *
     * @param episodio episódio a adicionar
     * @throws Exception se o episódio já estiver na lista
     */
    public void adicionarEpisodioLista(Episodio episodio) throws Exception {
        if (listaEpisodios.contains(episodio)) {
            throw new Exception("O episódio já está na sua lista pessoal.");
        }
        listaEpisodios.add(episodio);
    }

    /**
     * Remove um episódio da lista pessoal do espectador.
     *
     * @param episodio episódio a remover
     * @throws Exception se o episódio não estiver na lista
     */
    public void removerEpisodioLista(Episodio episodio) throws Exception {
        if (!listaEpisodios.remove(episodio)) {
            throw new Exception("O episódio não está na sua lista pessoal.");
        }
    }

    /**
     * Devolve a lista pessoal de filmes do espectador.
     *
     * @return lista de filmes
     */
    public ArrayList<Filme> getListaFilmes() {
        return listaFilmes;
    }

    /**
     * Devolve a lista pessoal de episódios do espectador.
     *
     * @return lista de episódios
     */
    public ArrayList<Episodio> getListaEpisodios() {
        return listaEpisodios;
    }

    /**
     * Devolve o número de filmes vistos pelo espectador.
     *
     * @param todosFilmes lista de todos os filmes da plataforma
     * @return número de filmes vistos
     */
    public int getNumFilmesVistos(List<Filme> todosFilmes) {
        int count = 0;
        for (Filme f : todosFilmes) {
            if (f.isVisto(this)) count++;
        }
        return count;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
