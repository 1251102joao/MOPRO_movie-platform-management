package org.example.ui;

import org.example.model.*;
import org.example.utils.Utils;

public class MenuGerirFilmes {

    private IMDB imdb;
    private String opcao;

    public MenuGerirFilmes(IMDB imdb) {
        this.imdb = imdb;
    }

    public void run() {
        do {
            System.out.println("\n\n");
            System.out.println("#################################################");
            System.out.println("#                GERIR FILMES                   #");
            System.out.println("#################################################");
            System.out.println("#  1. Ver todos os filmes                       #");
            System.out.println("#  2. Adicionar filme                           #");
            System.out.println("#  3. Remover filme                             #");
            System.out.println("#  4. Associar ator a filme                     #");
            System.out.println("#  5. Associar género a filme                   #");
            System.out.println("#  0. Voltar                                    #");
            System.out.println("#################################################");
            System.out.println();

            opcao = Utils.readLineFromConsole("Escolha uma opção: ");

            if (opcao.equals("1")) {
                System.out.println(imdb.listarFilmes()); pausa();
            } else if (opcao.equals("2")) {
                adicionarFilme(); pausa();
            } else if (opcao.equals("3")) {
                removerFilme(); pausa();
            } else if (opcao.equals("4")) {
                associarAtorFilme(); pausa();
            } else if (opcao.equals("5")) {
                associarGeneroFilme(); pausa();
            } else if (!opcao.equals("0")) {
                System.out.println("Opção inválida.");
            }
        } while (!opcao.equals("0"));
    }

    private void pausa() {
        Utils.readLineFromConsole("\nPrime Enter para continuar...");
    }

    private void adicionarFilme() {
        String titulo = Utils.readLineFromConsole("Título: ");
        int ano = Utils.readIntFromConsole("Ano de lançamento: ");
        int duracao = Utils.readIntFromConsole("Duração (minutos): ");
        if (Utils.confirma("Confirma a adição de \"" + titulo + "\"? (S/N)")) {
            try {
                imdb.adicionarFilme(new Filme(titulo, ano, duracao));
                System.out.println("Filme adicionado. Associe géneros (op.5) e atores (op.4).");
            } catch (Exception e) {
                System.out.println("ERRO: " + e.getMessage());
            }
        }
    }

    private void removerFilme() {
        Filme f = MenuSemLogin.selecionarFilmeDaLista(imdb);
        if (f == null) return;
        if (Utils.confirma("Confirma a remoção de \"" + f.getTitulo() + "\"? (S/N)")) {
            imdb.removerFilme(f);
            System.out.println("Filme removido.");
        }
    }

    private void associarAtorFilme() {
        Filme f = MenuSemLogin.selecionarFilmeDaLista(imdb);
        if (f == null) return;
        System.out.println(imdb.listarAtores());
        String nomeAtor = Utils.readLineFromConsole("Nome do ator a associar: ");
        Ator ator = imdb.pesquisaAtor(nomeAtor);
        if (ator == null) {
            System.out.println("Ator não encontrado.");
            return;
        }
        try {
            f.adicionarAtor(ator);
            System.out.println("Ator \"" + ator.getNome() + "\" associado a \"" + f.getTitulo() + "\".");
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage());
        }
    }

    private void associarGeneroFilme() {
        Filme f = MenuSemLogin.selecionarFilmeDaLista(imdb);
        if (f == null) return;
        Genero[] generos = Genero.values();
        System.out.println("\nGéneros disponíveis:");
        for (int i = 0; i < generos.length; i++)
            System.out.println("  " + (i + 1) + ". " + generos[i]);
        int escolha = Utils.readIntFromConsole("Escolha o número do género: ");
        if (escolha < 1 || escolha > generos.length) {
            System.out.println("Opção inválida.");
            return;
        }
        try {
            f.adicionarGenero(generos[escolha - 1]);
            System.out.println("Género \"" + generos[escolha - 1] + "\" associado a \"" + f.getTitulo() + "\".");
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage());
        }
    }
}
