package org.example.model;

import java.util.ArrayList;

/**
 * Representa uma série na plataforma.
 * Herda de Recurso e implementa MarcavelComoVisto.
 * É composta por temporadas (composição), cada uma com os seus episódios.
 * Os vistos e classificações são geridos pela superclasse Recurso.
 */
public class Serie extends Recurso implements MarcavelComoVisto {

    /** Lista de temporadas da série (composição). */
    private ArrayList<Temporada> temporadas;

    /**
     * Constrói uma série.
     *
     * @param titulo título da série
     * @param ano    ano de estreia
     */
    public Serie(String titulo, int ano) {
        super(titulo, ano);
        this.temporadas = new ArrayList<>();
    }

    /**
     * Devolve a lista de temporadas da série.
     *
     * @return lista de temporadas
     */
    public ArrayList<Temporada> getTemporadas() {
        return temporadas;
    }

    /**
     * Adiciona uma temporada à série.
     *
     * @param temporada temporada a adicionar
     * @throws Exception se já existir uma temporada com o mesmo número
     */
    public void adicionarTemporada(Temporada temporada) throws Exception {
        for (Temporada t : temporadas) {
            if (t.getNumero() == temporada.getNumero()) {
                throw new Exception("Já existe a temporada " + temporada.getNumero() + " nesta série.");
            }
        }
        temporadas.add(temporada);
    }

    /**
     * Pesquisa uma temporada pelo número.
     *
     * @param numero número da temporada
     * @return temporada encontrada ou null
     */
    public Temporada getTemporada(int numero) {
        for (Temporada t : temporadas) {
            if (t.getNumero() == numero) return t;
        }
        return null;
    }

    /**
     * Devolve todos os episódios de todas as temporadas.
     *
     * @return lista de todos os episódios
     */
    public ArrayList<Episodio> getTodosEpisodios() {
        ArrayList<Episodio> todos = new ArrayList<>();
        for (Temporada t : temporadas) {
            todos.addAll(t.getEpisodios());
        }
        return todos;
    }

    @Override
    public String getCategoriaClassificacao() {
        double media = getClassificacaoMedia();
        if (media == 0) return "Sem classificação";
        if (media < 5) return "Fraco";
        if (media <= 7.8) return "Médio";
        return "Bom";
    }

    @Override
    public String toString() {
        return String.format("\"%s\" (%d) | %d temporadas | Géneros: %s | Classificação: %.1f/10 [%s]",
                titulo, ano, temporadas.size(), generosToString(),
                getClassificacaoMedia(), getCategoriaClassificacao());
    }
}
