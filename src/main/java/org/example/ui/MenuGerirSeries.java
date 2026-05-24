package org.example.ui;

import org.example.model.*;
import org.example.utils.Utils;

import java.util.ArrayList;

public class MenuGerirSeries {

    private IMDB imdb;
    private String opcao;

    public MenuGerirSeries(IMDB imdb) {
        this.imdb = imdb;
    }

    public void run() {
        do {
            System.out.println("\n\n");
            System.out.println("#################################################");
            System.out.println("#                GERIR SÉRIES                   #");
            System.out.println("#################################################");
            System.out.println("#  1. Ver todas as séries                       #");
            System.out.println("#  2. Adicionar série                           #");
            System.out.println("#  3. Adicionar temporada a série               #");
            System.out.println("#  4. Adicionar episódio a temporada            #");
            System.out.println("#  5. Associar ator a episódio                  #");
            System.out.println("#  6. Associar género a série                   #");
            System.out.println("#  7. Remover série                             #");
            System.out.println("#  0. Voltar                                    #");
            System.out.println("#################################################");
            System.out.println();

            opcao = Utils.readLineFromConsole("Escolha uma opção: ");

            if (opcao.equals("1")) {
                listarSeriesDetalhadas(); pausa();
            } else if (opcao.equals("2")) {
                adicionarSerie(); pausa();
            } else if (opcao.equals("3")) {
                adicionarTemporada(); pausa();
            } else if (opcao.equals("4")) {
                adicionarEpisodio(); pausa();
            } else if (opcao.equals("5")) {
                associarAtorEpisodio(); pausa();
            } else if (opcao.equals("6")) {
                associarGeneroSerie(); pausa();
            } else if (opcao.equals("7")) {
                removerSerie(); pausa();
            } else if (!opcao.equals("0")) {
                System.out.println("Opção inválida.");
            }
        } while (!opcao.equals("0"));
    }

    private void pausa() {
        Utils.readLineFromConsole("\nPrime Enter para continuar...");
    }

    private void listarSeriesDetalhadas() {
        ArrayList<Serie> series = imdb.getLstSeries();
        if (series.isEmpty()) {
            System.out.println("Nenhuma série registada.");
            return;
        }
        for (Serie s : series) {
            System.out.println("\n" + s);
            for (Temporada t : s.getTemporadas()) System.out.println("  " + t);
        }
    }

    private void adicionarSerie() {
        String titulo = Utils.readLineFromConsole("Título da série: ");
        int ano = Utils.readIntFromConsole("Ano de estreia: ");
        if (Utils.confirma("Confirma a adição de \"" + titulo + "\"? (S/N)")) {
            try {
                imdb.adicionarSerie(new Serie(titulo, ano));
                System.out.println("Série adicionada com sucesso.");
            } catch (Exception e) {
                System.out.println("ERRO: " + e.getMessage());
            }
        }
    }

    private void adicionarTemporada() {
        Serie serie = MenuSemLogin.selecionarSerieDaLista(imdb);
        if (serie == null) return;
        int num = Utils.readIntFromConsole("Número da temporada: ");
        try {
            serie.adicionarTemporada(new Temporada(num));
            System.out.println("Temporada " + num + " adicionada a \"" + serie.getTitulo() + "\".");
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage());
        }
    }

    private void adicionarEpisodio() {
        Serie serie = MenuSemLogin.selecionarSerieDaLista(imdb);
        if (serie == null) return;
        Temporada t = selecionarTemporada(serie);
        if (t == null) return;
        int numEp = Utils.readIntFromConsole("Número do episódio: ");
        String titulo = Utils.readLineFromConsole("Título do episódio: ");
        int duracao = Utils.readIntFromConsole("Duração (minutos): ");
        try {
            t.adicionarEpisodio(new Episodio(titulo, numEp, duracao));
            System.out.println("Episódio adicionado com sucesso.");
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage());
        }
    }

    private void associarAtorEpisodio() {
        Serie serie = MenuSemLogin.selecionarSerieDaLista(imdb);
        if (serie == null) return;
        Temporada t = selecionarTemporada(serie);
        if (t == null) return;
        Episodio ep = selecionarEpisodio(t);
        if (ep == null) return;
        System.out.println(imdb.listarAtores());
        String nomeAtor = Utils.readLineFromConsole("Nome do ator: ");
        Ator ator = imdb.pesquisaAtor(nomeAtor);
        if (ator == null) {
            System.out.println("Ator não encontrado.");
            return;
        }
        try {
            ep.adicionarAtor(ator);
            System.out.println("Ator associado ao episódio com sucesso.");
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage());
        }
    }

    private void associarGeneroSerie() {
        Serie serie = MenuSemLogin.selecionarSerieDaLista(imdb);
        if (serie == null) return;
        Genero[] generos = Genero.values();
        System.out.println("\nGéneros disponíveis:");
        for (int i = 0; i < generos.length; i++) System.out.println("  " + (i + 1) + ". " + generos[i]);
        int escolha = Utils.readIntFromConsole("Número do género: ");
        if (escolha < 1 || escolha > generos.length) {
            System.out.println("Opção inválida.");
            return;
        }
        try {
            serie.adicionarGenero(generos[escolha - 1]);
            System.out.println("Género associado com sucesso.");
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage());
        }
    }

    private void removerSerie() {
        Serie serie = MenuSemLogin.selecionarSerieDaLista(imdb);
        if (serie == null) return;
        if (Utils.confirma("Confirma a remoção de \"" + serie.getTitulo() + "\"? (S/N)")) {
            imdb.removerSerie(serie);
            System.out.println("Série removida.");
        }
    }

    private Temporada selecionarTemporada(Serie s) {
        ArrayList<Temporada> temporadas = s.getTemporadas();
        if (temporadas.isEmpty()) {
            System.out.println("Esta série não tem temporadas.");
            return null;
        }
        System.out.println("Temporadas de \"" + s.getTitulo() + "\":");
        for (int i = 0; i < temporadas.size(); i++)
            System.out.println("  " + (i + 1) + ". Temporada " + temporadas.get(i).getNumero());
        int idx = Utils.readIntFromConsole("Escolha a temporada: ");
        if (idx < 1 || idx > temporadas.size()) {
            System.out.println("Opção inválida.");
            return null;
        }
        return temporadas.get(idx - 1);
    }

    private Episodio selecionarEpisodio(Temporada t) {
        ArrayList<Episodio> episodios = t.getEpisodios();
        if (episodios.isEmpty()) {
            System.out.println("Esta temporada não tem episódios.");
            return null;
        }
        System.out.println("Episódios da Temporada " + t.getNumero() + ":");
        for (int i = 0; i < episodios.size(); i++)
            System.out.println("  " + (i + 1) + ". Ep." + episodios.get(i).getNumero() + " - " + episodios.get(i).getTitulo());
        int idx = Utils.readIntFromConsole("Escolha o episódio: ");
        if (idx < 1 || idx > episodios.size()) {
            System.out.println("Opção inválida.");
            return null;
        }
        return episodios.get(idx - 1);
    }
}
