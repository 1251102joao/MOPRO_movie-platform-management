package org.example.ui;

import org.example.model.*;
import org.example.utils.Utils;

public class MenuInicial {

    private IMDB imdb;
    private String opcao;

    public MenuInicial(IMDB imdb) {
        this.imdb = imdb;
    }

    public void run() {
        do {
            System.out.println("\n\n");
            System.out.println("#################################################");
            System.out.println("#                   IMDB                        #");
            System.out.println("#################################################");
            System.out.println("#  1. Explorar sem login                        #");
            System.out.println("#  2. Login                                     #");
            System.out.println("#  3. Registar novo utilizador                  #");
            System.out.println("#  4. Guardar dados em ficheiro                 #");
            System.out.println("#  5. Carregar dados de ficheiro                #");
            System.out.println("#  0. Sair                                      #");
            System.out.println("#################################################");
            System.out.println();

            opcao = Utils.readLineFromConsole("Escolha uma opção: ");

            if (opcao.equals("1")) {
                new MenuSemLogin(imdb).run();
            } else if (opcao.equals("2")) {
                fazerLogin();
            } else if (opcao.equals("3")) {
                registarUtilizador();
            } else if (opcao.equals("4")) {
                guardarDados();
            } else if (opcao.equals("5")) {
                carregarDados();
            } else if (opcao.equals("0")) {
                System.out.println("Até logo!");
            } else {
                System.out.println("Opção inválida.");
            }
        } while (!opcao.equals("0"));
    }


    private void fazerLogin() {
        int tentativas = 0;
        while (tentativas < 3) {
            String username = Utils.readLineFromConsole("Username: ");
            String password = Utils.readPasswordWithMask("Password: ");


            UtilizadorRegistado ur = imdb.login(username, password);
            if (ur != null) {
                System.out.println("Bem-vindo/a, " + ur.getNome() + "!");
                if (ur instanceof Admin) {
                    new MenuAdministrador(imdb).run();
                } else if (ur instanceof Espectador) {
                    new MenuUtilizadorRegistado(imdb, (Espectador) ur).run();
                }
                return;
            }
            tentativas++;
            System.out.println("Credenciais inválidas. Tentativas restantes: " + (3 - tentativas));
        }
        System.out.println("Demasiadas tentativas falhadas.");
    }

    private void registarUtilizador() {
        System.out.println("\n--- Registar novo utilizador ---");
        String username;
        while (true) {
            username = Utils.readLineFromConsole("Username (sem espaços): ");
            if (username == null || username.trim().isEmpty() || username.contains(" ")) {
                System.out.println("Username inválido.");
            } else if (imdb.pesquisaUtilizador(username) != null) {
                System.out.println("Username já em uso. Escolha outro.");
            } else {
                break;
            }
        }
        String email = Utils.readLineFromConsole("Email: ");
        String password;
        while (true) {
            password = Utils.readPasswordWithMask("Password (mínimo 3 caracteres): ");
            if (password == null || password.length() < 3) {
                System.out.println("Password demasiado curta.");
            } else if (!password.equals(Utils.readPasswordWithMask("Confirmar password: "))) {
                System.out.println("As passwords não coincidem.");
            } else {
                break;
            }
        }
        System.out.println("\nResumo: username=" + username + " | email=" + email);
        if (Utils.confirma("Confirma o registo? (S/N)")) {
            imdb.adicionarUtilizador(new Espectador(email, username, password));
            System.out.println("Conta criada! Já pode fazer login com \"" + username + "\".");
        } else {
            System.out.println("Registo cancelado.");
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

    private void carregarDados() {
        String caminho = Utils.readLineFromConsole("Caminho do ficheiro (ex: dados.ser): ");
        try {
            imdb = IMDB.carregarFicheiro(caminho);
            System.out.println("Dados carregados de: " + caminho);
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage());
        }
    }
}
