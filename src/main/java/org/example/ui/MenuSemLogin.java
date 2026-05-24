package org.example.ui;

import org.example.model.*;
import org.example.utils.Utils;

import java.util.ArrayList;

public class MenuSemLogin {

    private IMDB imdb;
    private String opcao;

    public MenuSemLogin(IMDB imdb) {
        this.imdb = imdb;
    }

    public void run() {
        do {
            System.out.println("\n\n");
            System.out.println("#################################################");
            System.out.println("#              CONSULTA                         #");
            System.out.println("#################################################");
            System.out.println("#  1.  Listar filmes                            #");
            System.out.println("#  2.  Listar séries                            #");
            System.out.println("#  3.  Listar atores                            #");
            System.out.println("#  4.  Pesquisar filmes por título              #");
            System.out.println("#  5.  Pesquisar séries por título              #");
            System.out.println("#  6.  Pesquisar atores por nome               #");
            System.out.println("#  7.  Filmes ordenados por título              #");
            System.out.println("#  8.  Filmes ordenados por classificação       #");
            System.out.println("#  9.  Atores ordenados por nome               #");
            System.out.println("#  10. Atores ordenados por nº de filmes       #");
            System.out.println("#  11. Ver detalhe de um filme                  #");
            System.out.println("#  12. Ver detalhe de uma série                 #");
            System.out.println("#  0.  Voltar                                   #");
            System.out.println("#################################################");
            System.out.println();

            opcao = Utils.readLineFromConsole("Escolha uma opção: ");

            if (opcao.equals("1")) {
                System.out.println(imdb.listarFilmes()); pausa();
            } else if (opcao.equals("2")) {
                System.out.println(imdb.listarSeries()); pausa();
            } else if (opcao.equals("3")) {
                System.out.println(imdb.listarAtores()); pausa();
            } else if (opcao.equals("4")) {
                pesquisarFilmes(); pausa();
            } else if (opcao.equals("5")) {
                pesquisarSeries(); pausa();
            } else if (opcao.equals("6")) {
                pesquisarAtores(); pausa();
            } else if (opcao.equals("7")) {
                listarFilmesOrdenados(imdb.getFilmesOrdenadosPorTitulo(), "por título"); pausa();
            } else if (opcao.equals("8")) {
                listarFilmesOrdenados(imdb.getFilmesOrdenadosPorClassificacao(), "por classificação"); pausa();
            } else if (opcao.equals("9")) {
                listarAtoresOrdenados(imdb.getAtoresOrdenadosPorNome(), "por nome"); pausa();
            } else if (opcao.equals("10")) {
                listarAtoresOrdenados(imdb.getAtoresOrdenadosPorNumFilmes(), "por nº de filmes"); pausa();
            } else if (opcao.equals("11")) {
                verDetalheFilme(); pausa();
            } else if (opcao.equals("12")) {
                verDetalheSerie(); pausa();
            } else if (!opcao.equals("0")) {
                System.out.println("Opção inválida.");
            }
        } while (!opcao.equals("0"));
    }

    private void pausa() {
        Utils.readLineFromConsole("\nPrime Enter para continuar...");
    }

    private void pesquisarFilmes() {
        String texto = Utils.readLineFromConsole("Texto a pesquisar: ");
        ArrayList<Filme> res = imdb.pesquisarFilmes(texto);
        if (res.isEmpty()) {
            System.out.println("Nenhum filme encontrado.");
        } else {
            for (Filme f : res) System.out.println("  - " + f);
        }
    }

    private void pesquisarSeries() {
        String texto = Utils.readLineFromConsole("Texto a pesquisar: ");
        ArrayList<Serie> res = imdb.pesquisarSeries(texto);
        if (res.isEmpty()) {
            System.out.println("Nenhuma série encontrada.");
        } else {
            for (Serie s : res) System.out.println("  - " + s);
        }
    }

    private void pesquisarAtores() {
        String texto = Utils.readLineFromConsole("Nome a pesquisar: ");
        ArrayList<Ator> res = imdb.pesquisarAtores(texto);
        if (res.isEmpty()) {
            System.out.println("Nenhum ator encontrado.");
        } else {
            for (Ator a : res) System.out.println("  - " + a);
        }
    }

    private void listarFilmesOrdenados(ArrayList<Filme> filmes, String criterio) {
        System.out.println("\nFilmes ordenados " + criterio + ":");
        if (filmes.isEmpty()) {
            System.out.println("  (sem filmes)");
        } else {
            for (int i = 0; i < filmes.size(); i++)
                System.out.println("  " + (i + 1) + ". " + filmes.get(i));
        }
    }

    private void listarAtoresOrdenados(ArrayList<Ator> atores, String criterio) {
        System.out.println("\nAtores ordenados " + criterio + ":");
        if (atores.isEmpty()) {
            System.out.println("  (sem atores)");
        } else {
            for (int i = 0; i < atores.size(); i++)
                System.out.println("  " + (i + 1) + ". " + atores.get(i));
        }
    }

    private void verDetalheFilme() {
        Filme f = selecionarFilmeDaLista(imdb);
        if (f == null) return;
        System.out.println("\n" + f);
        System.out.println("  Atores:");
        for (Ator a : f.getAtores()) System.out.println("    - " + a);
        System.out.println("  Classificações:");
        if (f.getClassificacoes().isEmpty()) {
            System.out.println("    (sem classificações)");
        } else {
            for (Classificacao c : f.getClassificacoes()) System.out.println("    - " + c);
        }
    }

    private void verDetalheSerie() {
        Serie s = selecionarSerieDaLista(imdb);
        if (s == null) return;
        System.out.println("\n" + s);
        for (Temporada t : s.getTemporadas()) System.out.println("  " + t);
    }

    static Filme selecionarFilmeDaLista(IMDB imdb) {
        ArrayList<Filme> filmes = imdb.getLstFilmes();
        if (filmes.isEmpty()) {
            System.out.println("Não há filmes registados.");
            return null;
        }
        System.out.println("\nFilmes disponíveis:");
        for (int i = 0; i < filmes.size(); i++)
            System.out.println("  " + (i + 1) + ". \"" + filmes.get(i).getTitulo() + "\" (" + filmes.get(i).getAno() + ")");
        int escolha = Utils.readIntFromConsole("Escolha o número do filme: ");
        if (escolha < 1 || escolha > filmes.size()) {
            System.out.println("Opção inválida.");
            return null;
        }
        return filmes.get(escolha - 1);
    }


    static Serie selecionarSerieDaLista(IMDB imdb) {
        ArrayList<Serie> series = imdb.getLstSeries();
        if (series.isEmpty()) {
            System.out.println("Não há séries registadas.");
            return null;
        }
        System.out.println("\nSéries disponíveis:");
        for (int i = 0; i < series.size(); i++)
            System.out.println("  " + (i + 1) + ". \"" + series.get(i).getTitulo() + "\" (" + series.get(i).getAno() + ")");
        int escolha = Utils.readIntFromConsole("Escolha o número da série: ");
        if (escolha < 1 || escolha > series.size()) {
            System.out.println("Opção inválida.");
            return null;
        }
        return series.get(escolha - 1);
    }
}
