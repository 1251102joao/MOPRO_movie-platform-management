package org.example.ui;

import org.example.model.*;
import org.example.utils.Data;
import org.example.utils.Utils;
import java.util.ArrayList;


public class MenuGerirAtores {

    private IMDB imdb;
    private String opcao;

    public MenuGerirAtores(IMDB imdb) {
        this.imdb = imdb;
    }

    public void run() {
        do {
            System.out.println("\n\n");
            System.out.println("#################################################");
            System.out.println("#                GERIR ATORES                   #");
            System.out.println("#################################################");
            System.out.println("#  1. Ver todos os atores                       #");
            System.out.println("#  2. Adicionar ator                            #");
            System.out.println("#  3. Remover ator                              #");
            System.out.println("#  4. Pesquisar ator por nome                   #");
            System.out.println("#  0. Voltar                                    #");
            System.out.println("#################################################");
            System.out.println();

            opcao = Utils.readLineFromConsole("Escolha uma opção: ");

            if (opcao.equals("1")) {
                System.out.println(imdb.listarAtores()); pausa();
            } else if (opcao.equals("2")) {
                adicionarAtor(); pausa();
            } else if (opcao.equals("3")) {
                removerAtor(); pausa();
            } else if (opcao.equals("4")) {
                pesquisarAtor(); pausa();
            } else if (!opcao.equals("0")) {
                System.out.println("Opção inválida.");
            }
        } while (!opcao.equals("0"));
    }

    private void pausa() {
        Utils.readLineFromConsole("\nPrime Enter para continuar...");
    }

    private void adicionarAtor() {
        String nome = Utils.readLineFromConsole("Nome do ator: ");
        Data dataNascimento = Utils.readDateFromConsole("Data de nascimento ");
        Ator novoAtor = new Ator(nome, dataNascimento);
        if (Utils.confirma("Confirma a adição de \"" + nome + "\"? (S/N)")) {
            imdb.adicionarAtor(novoAtor);
            System.out.println("Ator adicionado com sucesso.");
        }
    }

    private void removerAtor() {
        System.out.println(imdb.listarAtores());
        String nome = Utils.readLineFromConsole("Nome do ator a remover: ");
        Ator ator = imdb.pesquisaAtor(nome);
        if (ator == null) {
            System.out.println("Ator não encontrado.");
            return;
        }
        if (Utils.confirma("Confirma a remoção de \"" + ator.getNome() + "\"? (S/N)")) {
            imdb.removerAtor(ator);
            System.out.println("Ator removido.");
        }
    }

    private void pesquisarAtor() {
        String texto = Utils.readLineFromConsole("Nome a pesquisar: ");
        ArrayList<Ator> res = imdb.pesquisarAtores(texto);
        if (res.isEmpty()) {
            System.out.println("Nenhum ator encontrado.");
        } else {
            for (Ator a : res) System.out.println("  - " + a);
        }
    }
}
