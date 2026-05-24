package org.example.ui;

import org.example.model.*;
import org.example.utils.Utils;

public class MenuAdministrador {

    private IMDB imdb;
    private String opcao;

    public MenuAdministrador(IMDB imdb) {
        this.imdb = imdb;
    }

    public void run() {
        do {
            System.out.println("\n\n");
            System.out.println("#################################################");
            System.out.println("#              MENU ADMINISTRADOR               #");
            System.out.println("#################################################");
            System.out.println("#  --- RECURSOS ---                             #");
            System.out.println("#  1. Gerir filmes                              #");
            System.out.println("#  2. Gerir séries                              #");
            System.out.println("#  3. Gerir atores                              #");
            System.out.println("#  --- UTILIZADORES ---                         #");
            System.out.println("#  4. Listar utilizadores                       #");
            System.out.println("#  5. Adicionar utilizador                      #");
            System.out.println("#  6. Remover utilizador                        #");
            System.out.println("#  7. Utilizadores com mais filmes vistos       #");
            System.out.println("#  --- CLASSIFICAÇÕES ---                       #");
            System.out.println("#  8. Ver classificações de um filme            #");
            System.out.println("#  --- DADOS ---                                #");
            System.out.println("#  9. Guardar dados em ficheiro                 #");
            System.out.println("# 10. Ver estado geral da plataforma            #");
            System.out.println("#  0. Logout                                    #");
            System.out.println("#################################################");
            System.out.println();

            opcao = Utils.readLineFromConsole("Escolha uma opção: ");

            if (opcao.equals("1")) {
                new MenuGerirFilmes(imdb).run();
            } else if (opcao.equals("2")) {
                new MenuGerirSeries(imdb).run();
            } else if (opcao.equals("3")) {
                new MenuGerirAtores(imdb).run();
            } else if (opcao.equals("4")) {
                System.out.println(imdb.listarUtilizadores()); pausa();
            } else if (opcao.equals("5")) {
                adicionarUtilizador(); pausa();
            } else if (opcao.equals("6")) {
                removerUtilizador(); pausa();
            } else if (opcao.equals("7")) {
                listarPorFilmesVistos(); pausa();
            } else if (opcao.equals("8")) {
                verClassificacoesFilme(); pausa();
            } else if (opcao.equals("9")) {
                guardarDados(); pausa();
            } else if (opcao.equals("10")) {
                System.out.println(imdb); pausa();
            } else if (opcao.equals("0")) {
                System.out.println("Logout efetuado.");
            } else {
                System.out.println("Opção inválida.");
            }
        } while (!opcao.equals("0"));
    }

    private void pausa() {
        Utils.readLineFromConsole("\nPrime Enter para continuar...");
    }

    private void adicionarUtilizador() {
        System.out.println("\n  1. Espectador   2. Administrador");
        String tipo = Utils.readLineFromConsole("Tipo: ");
        String username = lerUsernameDisponivel();
        if (username == null) return;
        String email = Utils.readLineFromConsole("Email: ");
        String password = Utils.readPasswordWithMask("Password: ");
        if (password == null || password.length() < 3) {
            System.out.println("Password inválida.");
            return;
        }
        UtilizadorRegistado novo;
        if (tipo.equals("2")) {
            novo = new Admin(email, username, password);
        } else {
            novo = new Espectador(email, username, password);
        }
        if (Utils.confirma("Confirma a adição de \"" + username + "\"? (S/N)")) {
            imdb.adicionarUtilizador(novo);
            System.out.println("Utilizador adicionado com sucesso.");
        }
    }

    private String lerUsernameDisponivel() {
        while (true) {
            String username = Utils.readLineFromConsole("Username: ");
            if (username == null || username.trim().isEmpty()) {
                System.out.println("Username inválido.");
            } else if (imdb.pesquisaUtilizador(username) != null) {
                System.out.println("Username já em uso.");
            } else {
                return username;
            }
        }
    }

    private void removerUtilizador() {
        System.out.println(imdb.listarUtilizadores());
        String username = Utils.readLineFromConsole("Username a remover: ");
        UtilizadorRegistado u = imdb.pesquisaUtilizador(username);
        if (u == null) {
            System.out.println("Utilizador não encontrado.");
            return;
        }
        if (u instanceof Admin) {
            System.out.println("Não é possível remover administradores.");
            return;
        }
        if (Utils.confirma("Confirma a remoção de \"" + username + "\"? (S/N)")) {
            imdb.removerUtilizador(u);
            System.out.println("Utilizador removido.");
        }
    }

    private void listarPorFilmesVistos() {
        System.out.println("\nUtilizadores com mais filmes vistos:");
        int i = 1;
        for (Espectador e : imdb.getEspectadoresOrdenadosPorFilmesVistos()) {
            System.out.printf("  %d. %-20s -> %d filmes vistos%n",
                    i++, e.getNome(), e.getNumFilmesVistos(imdb.getLstFilmes()));
        }
    }

    private void verClassificacoesFilme() {
        Filme f = MenuSemLogin.selecionarFilmeDaLista(imdb);
        if (f == null) return;
        System.out.println("\nClassificações de \"" + f.getTitulo() + "\":");
        if (f.getClassificacoes().isEmpty()) {
            System.out.println("  (sem classificações)");
        } else {
            for (Classificacao c : f.getClassificacoes()) System.out.println("  - " + c);
            System.out.printf("  Média: %.1f/10 [%s]%n", f.getClassificacaoMedia(), f.getCategoriaClassificacao());
        }
    }

    private void guardarDados() {
        String caminho = Utils.readLineFromConsole("Caminho do ficheiro (ex: dados.ser): ");
        if (caminho == null || caminho.trim().isEmpty()) caminho = "dados.ser";
        try {
            imdb.gravarFicheiro(caminho);
            System.out.println("Dados guardados em: " + caminho);
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage());
        }
    }
}
