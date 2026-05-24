package org.example.model;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Classe central que representa a base de dados da plataforma.
 * Gere utilizadores, atores, filmes e séries.
 * Suporta serialização para persistência de dados.
 */
public class IMDB implements Serializable {

    /** URL identificador da plataforma. */
    private String url;

    /** Lista de utilizadores registados. */
    private ArrayList<UtilizadorRegistado> lstUtilizadores;

    /** Lista de atores. */
    private ArrayList<Ator> lstAtores;

    /** Lista de filmes. */
    private ArrayList<Filme> lstFilmes;

    /** Lista de séries. */
    private ArrayList<Serie> lstSeries;

    /**
     * Constrói a base de dados com o URL indicado.
     *
     * @param url URL da plataforma
     */
    public IMDB(String url) {
        this.url = url;
        this.lstAtores = new ArrayList<>();
        this.lstUtilizadores = new ArrayList<>();
        this.lstFilmes = new ArrayList<>();
        this.lstSeries = new ArrayList<>();
    }

    // ==============================
    // ATORES
    // ==============================

    /**
     * Adiciona um ator à base de dados.
     *
     * @param a ator a adicionar
     */
    public void adicionarAtor(Ator a) {
        lstAtores.add(a);
    }

    /**
     * Remove um ator da base de dados.
     *
     * @param ator ator a remover
     */
    public void removerAtor(Ator ator) {
        lstAtores.remove(ator);
    }

    /**
     * Pesquisa um ator pelo nome exato (ignora maiúsculas/minúsculas).
     *
     * @param nome nome do ator
     * @return ator encontrado ou null
     */
    public Ator pesquisaAtor(String nome) {
        for (Ator a : lstAtores) {
            if (a.temNome(nome)) return a;
        }
        return null;
    }

    /**
     * Pesquisa atores cujo nome contenha o texto indicado.
     *
     * @param texto texto de pesquisa
     * @return lista de atores correspondentes
     */
    public ArrayList<Ator> pesquisarAtores(String texto) {
        ArrayList<Ator> resultado = new ArrayList<>();
        for (Ator a : lstAtores) {
            if (a.correspondePesquisa(texto)) resultado.add(a);
        }
        return resultado;
    }

    /**
     * Devolve a lista de atores ordenada por nome.
     *
     * @return lista ordenada por nome
     */
    public ArrayList<Ator> getAtoresOrdenadosPorNome() {
        ArrayList<Ator> ordenados = new ArrayList<>(lstAtores);
        ordenados.sort(Comparator.comparing(Ator::getNome));
        return ordenados;
    }

    /**
     * Devolve a lista de atores ordenada pelo número de filmes em que participam.
     *
     * @return lista ordenada por número de filmes (decrescente)
     */
    public ArrayList<Ator> getAtoresOrdenadosPorNumFilmes() {
        ArrayList<Ator> ordenados = new ArrayList<>(lstAtores);
        ordenados.sort((a1, a2) -> {
            int count1 = 0, count2 = 0;
            for (Filme f : lstFilmes) {
                if (f.getAtores().contains(a1)) count1++;
                if (f.getAtores().contains(a2)) count2++;
            }
            return Integer.compare(count2, count1);
        });
        return ordenados;
    }

    /**
     * Devolve a lista completa de atores.
     *
     * @return lista de atores
     */
    public ArrayList<Ator> getLstAtores() {
        return lstAtores;
    }

    // ==============================
    // UTILIZADORES
    // ==============================

    /**
     * Adiciona um utilizador registado à base de dados.
     *
     * @param u utilizador a adicionar
     */
    public void adicionarUtilizador(UtilizadorRegistado u) {
        lstUtilizadores.add(u);
    }

    /**
     * Remove um utilizador da base de dados.
     *
     * @param u utilizador a remover
     */
    public void removerUtilizador(UtilizadorRegistado u) {
        lstUtilizadores.remove(u);
    }

    /**
     * Pesquisa um utilizador pelo username.
     *
     * @param username username a pesquisar
     * @return utilizador encontrado ou null
     */
    public UtilizadorRegistado pesquisaUtilizador(String username) {
        for (UtilizadorRegistado u : lstUtilizadores) {
            if (u.temNome(username)) return u;
        }
        return null;
    }

    /**
     * Realiza o login de um utilizador verificando username e password.
     *
     * @param username username do utilizador
     * @param password password do utilizador
     * @return utilizador autenticado ou null se as credenciais forem inválidas
     */
    public UtilizadorRegistado login(String username, String password) {
        UtilizadorRegistado ur = pesquisaUtilizador(username);
        if (ur != null && ur.temPassord(password)) return ur;
        return null;
    }

    /**
     * Devolve a lista de espectadores ordenada pelo número de filmes vistos (decrescente).
     *
     * @return lista ordenada de espectadores
     */
    public ArrayList<Espectador> getEspectadoresOrdenadosPorFilmesVistos() {
        ArrayList<Espectador> espectadores = new ArrayList<>();
        for (UtilizadorRegistado u : lstUtilizadores) {
            if (u instanceof Espectador) espectadores.add((Espectador) u);
        }
        espectadores.sort((e1, e2) ->
                Integer.compare(e2.getNumFilmesVistos(lstFilmes), e1.getNumFilmesVistos(lstFilmes)));
        return espectadores;
    }

    /**
     * Devolve a lista completa de utilizadores registados.
     *
     * @return lista de utilizadores
     */
    public ArrayList<UtilizadorRegistado> getLstUtilizadores() {
        return lstUtilizadores;
    }

    // ==============================
    // FILMES
    // ==============================

    /**
     * Adiciona um filme à base de dados.
     *
     * @param filme filme a adicionar
     * @throws Exception se já existir um filme com o mesmo título e ano
     */
    public void adicionarFilme(Filme filme) throws Exception {
        for (Filme f : lstFilmes) {
            if (f.getTitulo().equalsIgnoreCase(filme.getTitulo()) && f.getAno() == filme.getAno()) {
                throw new Exception("Já existe um filme com o título '" + filme.getTitulo() + "' do ano " + filme.getAno() + ".");
            }
        }
        lstFilmes.add(filme);
    }

    /**
     * Remove um filme da base de dados.
     *
     * @param filme filme a remover
     */
    public void removerFilme(Filme filme) {
        lstFilmes.remove(filme);
    }

    /**
     * Pesquisa um filme pelo título exato e ano.
     *
     * @param titulo título do filme
     * @param ano    ano do filme
     * @return filme encontrado ou null
     */
    public Filme pesquisaFilme(String titulo, int ano) {
        for (Filme f : lstFilmes) {
            if (f.getTitulo().equalsIgnoreCase(titulo) && f.getAno() == ano) return f;
        }
        return null;
    }

    /**
     * Pesquisa filmes cujo título contenha o texto indicado.
     *
     * @param texto texto de pesquisa
     * @return lista de filmes correspondentes
     */
    public ArrayList<Filme> pesquisarFilmes(String texto) {
        ArrayList<Filme> resultado = new ArrayList<>();
        for (Filme f : lstFilmes) {
            if (f.correspondePesquisa(texto)) resultado.add(f);
        }
        return resultado;
    }

    /**
     * Devolve os filmes ordenados por título (ignora maiúsculas/minúsculas).
     *
     * @return lista ordenada por título
     */
    public ArrayList<Filme> getFilmesOrdenadosPorTitulo() {
        ArrayList<Filme> ordenados = new ArrayList<>(lstFilmes);
        ordenados.sort(Comparator.comparing(Filme::getTitulo, String.CASE_INSENSITIVE_ORDER));
        return ordenados;
    }

    /**
     * Devolve os filmes ordenados por classificação média (decrescente).
     *
     * @return lista ordenada por classificação
     */
    public ArrayList<Filme> getFilmesOrdenadosPorClassificacao() {
        ArrayList<Filme> ordenados = new ArrayList<>(lstFilmes);
        ordenados.sort((f1, f2) -> Double.compare(f2.getClassificacaoMedia(), f1.getClassificacaoMedia()));
        return ordenados;
    }

    /**
     * Devolve a lista completa de filmes.
     *
     * @return lista de filmes
     */
    public ArrayList<Filme> getLstFilmes() {
        return lstFilmes;
    }

    // ==============================
    // SÉRIES
    // ==============================

    /**
     * Adiciona uma série à base de dados.
     *
     * @param serie série a adicionar
     * @throws Exception se já existir uma série com o mesmo título e ano
     */
    public void adicionarSerie(Serie serie) throws Exception {
        for (Serie s : lstSeries) {
            if (s.getTitulo().equalsIgnoreCase(serie.getTitulo()) && s.getAno() == serie.getAno()) {
                throw new Exception("Já existe uma série com o título '" + serie.getTitulo() + "' do ano " + serie.getAno() + ".");
            }
        }
        lstSeries.add(serie);
    }

    /**
     * Remove uma série da base de dados.
     *
     * @param serie série a remover
     */
    public void removerSerie(Serie serie) {
        lstSeries.remove(serie);
    }

    /**
     * Pesquisa séries cujo título contenha o texto indicado.
     *
     * @param texto texto de pesquisa
     * @return lista de séries correspondentes
     */
    public ArrayList<Serie> pesquisarSeries(String texto) {
        ArrayList<Serie> resultado = new ArrayList<>();
        for (Serie s : lstSeries) {
            if (s.correspondePesquisa(texto)) resultado.add(s);
        }
        return resultado;
    }

    /**
     * Devolve a lista completa de séries.
     *
     * @return lista de séries
     */
    public ArrayList<Serie> getLstSeries() {
        return lstSeries;
    }

    // ==============================
    // LISTAGENS
    // ==============================

    /**
     * Devolve uma representação textual da lista de utilizadores.
     *
     * @return string com a lista de utilizadores
     */
    public String listarUtilizadores() {
        StringBuilder sb = new StringBuilder("\nLista de Utilizadores:");
        if (lstUtilizadores.isEmpty()) {
            sb.append(" (VAZIA)\n");
        } else {
            for (UtilizadorRegistado u : lstUtilizadores) {
                sb.append("\n\t- ").append(u).append(u instanceof Admin ? " (admin)" : "");
            }
        }
        return sb.toString();
    }

    /**
     * Devolve uma representação textual da lista de atores.
     *
     * @return string com a lista de atores
     */
    public String listarAtores() {
        StringBuilder sb = new StringBuilder("\nLista de Atores:");
        if (lstAtores.isEmpty()) {
            sb.append(" (VAZIA)\n");
        } else {
            for (Ator ator : lstAtores) {
                sb.append("\n\t- ").append(ator);
            }
        }
        return sb.toString();
    }

    /**
     * Devolve uma representação textual da lista de filmes.
     *
     * @return string com a lista de filmes
     */
    public String listarFilmes() {
        StringBuilder sb = new StringBuilder("\nLista de Filmes:");
        if (lstFilmes.isEmpty()) {
            sb.append(" (VAZIA)\n");
        } else {
            for (Filme f : lstFilmes) {
                sb.append("\n\t- ").append(f);
            }
        }
        return sb.toString();
    }

    /**
     * Devolve uma representação textual da lista de séries.
     *
     * @return string com a lista de séries
     */
    public String listarSeries() {
        StringBuilder sb = new StringBuilder("\nLista de Séries:");
        if (lstSeries.isEmpty()) {
            sb.append(" (VAZIA)\n");
        } else {
            for (Serie s : lstSeries) {
                sb.append("\n\t- ").append(s);
            }
        }
        return sb.toString();
    }

    // ==============================
    // SERIALIZAÇÃO
    // ==============================

    /**
     * Grava o estado da base de dados num ficheiro (serialização).
     *
     * @param caminho caminho do ficheiro de destino
     * @throws IOException se ocorrer um erro de escrita
     */
    public void gravarFicheiro(String caminho) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(caminho))) {
            oos.writeObject(this);
        }
    }

    /**
     * Carrega o estado da base de dados a partir de um ficheiro (desserialização).
     *
     * @param caminho caminho do ficheiro a ler
     * @return instância de IMDB carregada
     * @throws IOException            se ocorrer um erro de leitura
     * @throws ClassNotFoundException se a classe não for encontrada durante a desserialização
     */
    public static IMDB carregarFicheiro(String caminho) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(caminho))) {
            return (IMDB) ois.readObject();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("=== Estado atual da IMDB ===\n");
        sb.append("(").append(url).append(")");
        sb.append(listarUtilizadores());
        sb.append(listarAtores());
        sb.append(listarFilmes());
        sb.append(listarSeries());
        return sb.toString();
    }
}
