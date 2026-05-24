package org.example.ui;

import org.example.model.*;
import org.example.utils.Utils;

import java.util.ArrayList;

public class MenuUtilizadorRegistado {

    private IMDB imdb;
    private Espectador utilizador;
    private String opcao;

    public MenuUtilizadorRegistado(IMDB imdb, Espectador utilizador) {
        this.utilizador = utilizador;
        this.imdb = imdb;
    }

    public void run() {
        do {
            System.out.println("\n\n");
            System.out.println("#################################################");
            System.out.printf( "#    Olá, %-38s#%n", utilizador.getNome() + "!");
            System.out.println("#################################################");
            System.out.println("#  --- EXPLORAR ---                             #");
            System.out.println("#  1. Ver filmes                                #");
            System.out.println("#  2. Ver séries                                #");
            System.out.println("#  3. Pesquisar filmes                          #");
            System.out.println("#  4. Pesquisar séries                          #");
            System.out.println("#  --- INTERAGIR ---                            #");
            System.out.println("#  5. Marcar filme como visto                   #");
            System.out.println("#  6. Marcar episódio como visto                #");
            System.out.println("#  7. Classificar e comentar filme              #");
            System.out.println("#  8. Classificar e comentar episódio           #");
            System.out.println("#  --- LISTA PESSOAL ---                        #");
            System.out.println("#  9. Ver minha lista pessoal                   #");
            System.out.println("# 10. Adicionar filme à lista                  #");
            System.out.println("# 11. Remover filme da lista                   #");
            System.out.println("# 12. Adicionar episódio à lista               #");
            System.out.println("# 13. Remover episódio da lista                #");
            System.out.println("#  --- HISTÓRICO ---                            #");
            System.out.println("# 14. Ver filmes que já vi                      #");
            System.out.println("#  0. Logout                                    #");
            System.out.println("#################################################");
            System.out.println();

            opcao = Utils.readLineFromConsole("Escolha uma opção: ");

            if (opcao.equals("1")) {
                System.out.println(imdb.listarFilmes()); pausa();
            } else if (opcao.equals("2")) {
                System.out.println(imdb.listarSeries()); pausa();
            } else if (opcao.equals("3")) {
                pesquisar("filmes"); pausa();
            } else if (opcao.equals("4")) {
                pesquisar("series"); pausa();
            } else if (opcao.equals("5")) {
                marcarFilmeComoVisto(); pausa();
            } else if (opcao.equals("6")) {
                marcarEpisodioComoVisto(); pausa();
            } else if (opcao.equals("7")) {
                classificarFilme(); pausa();
            } else if (opcao.equals("8")) {
                classificarEpisodio(); pausa();
            } else if (opcao.equals("9")) {
                verListaPessoal(); pausa();
            } else if (opcao.equals("10")) {
                adicionarFilmeLista(); pausa();
            } else if (opcao.equals("11")) {
                removerFilmeLista(); pausa();
            } else if (opcao.equals("12")) {
                adicionarEpisodioLista(); pausa();
            } else if (opcao.equals("13")) {
                removerEpisodioLista(); pausa();
            } else if (opcao.equals("14")) {
                verFilmesVistos(); pausa();
            } else if (opcao.equals("0")) {
                System.out.println("Até logo, " + utilizador.getNome() + "!");
            } else {
                System.out.println("Opção inválida.");
            }
        } while (!opcao.equals("0"));
    }

    private void pausa() {
        Utils.readLineFromConsole("\nPrime Enter para continuar...");
    }

    private void pesquisar(String tipo) {
        String texto = Utils.readLineFromConsole("Texto a pesquisar: ");
        if (tipo.equals("filmes")) {
            ArrayList<Filme> res = imdb.pesquisarFilmes(texto);
            if (res.isEmpty()) {
                System.out.println("Nenhum resultado.");
            } else {
                for (Filme f : res) System.out.println("  - " + f);
            }
        } else {
            ArrayList<Serie> res = imdb.pesquisarSeries(texto);
            if (res.isEmpty()) {
                System.out.println("Nenhum resultado.");
            } else {
                for (Serie s : res) System.out.println("  - " + s);
            }
        }
    }

    private void marcarFilmeComoVisto() {
        Filme f = MenuSemLogin.selecionarFilmeDaLista(imdb);
        if (f == null) return;
        if (f.isVisto(utilizador)) {
            System.out.println("Já marcou \"" + f.getTitulo() + "\" como visto.");
            return;
        }
        try {
            f.marcarComoVisto(utilizador);
            System.out.println("\"" + f.getTitulo() + "\" marcado como visto!");
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage());
        }
    }

    private void marcarEpisodioComoVisto() {
        Serie s = MenuSemLogin.selecionarSerieDaLista(imdb);
        if (s == null) return;
        Episodio ep = selecionarEpisodio(s);
        if (ep == null) return;
        if (ep.isVisto(utilizador)) {
            System.out.println("Já marcou este episódio como visto.");
            return;
        }
        try {
            ep.marcarComoVisto(utilizador);
            System.out.println("\"" + ep.getTitulo() + "\" marcado como visto!");
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage());
        }
    }

    private void classificarFilme() {
        Filme f = MenuSemLogin.selecionarFilmeDaLista(imdb);
        if (f == null) return;
        if (!f.isVisto(utilizador)) {
            System.out.println("Tem de marcar o filme como visto primeiro (opção 5).");
            return;
        }
        int nota = lerNota();
        String comentario = Utils.readLineFromConsole("Comentário (Enter para ignorar): ");
        try {
            f.adicionarClassificacao(new Classificacao(utilizador, nota, comentario));
            System.out.println("Classificação guardada!");
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage());
        }
    }

    private void classificarEpisodio() {
        Serie s = MenuSemLogin.selecionarSerieDaLista(imdb);
        if (s == null) return;
        Episodio ep = selecionarEpisodio(s);
        if (ep == null) return;
        if (!ep.isVisto(utilizador)) {
            System.out.println("Tem de marcar o episódio como visto primeiro (opção 6).");
            return;
        }
        int nota = lerNota();
        String comentario = Utils.readLineFromConsole("Comentário (Enter para ignorar): ");
        try {
            ep.adicionarClassificacao(new Classificacao(utilizador, nota, comentario));
            System.out.println("Classificação guardada!");
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage());
        }
    }

    private int lerNota() {
        int nota = 0;
        while (nota < 1 || nota > 10) {
            nota = Utils.readIntFromConsole("Nota (1 a 10): ");
            if (nota < 1 || nota > 10) System.out.println("Nota inválida. Tem de ser entre 1 e 10.");
        }
        return nota;
    }

    private void verListaPessoal() {
        System.out.println("\nA sua lista pessoal:");
        System.out.println("  Filmes:");
        if (utilizador.getListaFilmes().isEmpty()) {
            System.out.println("    (vazia)");
        } else {
            for (int i = 0; i < utilizador.getListaFilmes().size(); i++)
                System.out.println("    " + (i + 1) + ". " + utilizador.getListaFilmes().get(i));
        }
        System.out.println("  Episódios:");
        if (utilizador.getListaEpisodios().isEmpty()) {
            System.out.println("    (vazia)");
        } else {
            for (int i = 0; i < utilizador.getListaEpisodios().size(); i++)
                System.out.println("    " + (i + 1) + ". " + utilizador.getListaEpisodios().get(i));
        }
    }

    private void adicionarFilmeLista() {
        Filme f = MenuSemLogin.selecionarFilmeDaLista(imdb);
        if (f == null) return;
        try {
            utilizador.adicionarFilmeLista(f);
            System.out.println("\"" + f.getTitulo() + "\" adicionado à sua lista.");
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage());
        }
    }

    private void removerFilmeLista() {
        if (utilizador.getListaFilmes().isEmpty()) {
            System.out.println("A sua lista de filmes está vazia.");
            return;
        }
        ArrayList<Filme> lista = utilizador.getListaFilmes();
        System.out.println("\nFilmes na sua lista:");
        for (int i = 0; i < lista.size(); i++)
            System.out.println("  " + (i + 1) + ". \"" + lista.get(i).getTitulo() + "\" (" + lista.get(i).getAno() + ")");
        int escolha = Utils.readIntFromConsole("Número do filme a remover: ");
        if (escolha < 1 || escolha > lista.size()) {
            System.out.println("Opção inválida.");
            return;
        }
        Filme f = lista.get(escolha - 1);
        try {
            utilizador.removerFilmeLista(f);
            System.out.println("\"" + f.getTitulo() + "\" removido da sua lista.");
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage());
        }
    }

    private void adicionarEpisodioLista() {
        Serie s = MenuSemLogin.selecionarSerieDaLista(imdb);
        if (s == null) return;
        Episodio ep = selecionarEpisodio(s);
        if (ep == null) return;
        try {
            utilizador.adicionarEpisodioLista(ep);
            System.out.println("Episódio adicionado à sua lista.");
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage());
        }
    }

    private void removerEpisodioLista() {
        if (utilizador.getListaEpisodios().isEmpty()) {
            System.out.println("A sua lista de episódios está vazia.");
            return;
        }
        ArrayList<Episodio> lista = utilizador.getListaEpisodios();
        System.out.println("\nEpisódios na sua lista:");
        for (int i = 0; i < lista.size(); i++)
            System.out.println("  " + (i + 1) + ". " + lista.get(i).getTitulo());
        int escolha = Utils.readIntFromConsole("Número do episódio a remover: ");
        if (escolha < 1 || escolha > lista.size()) {
            System.out.println("Opção inválida.");
            return;
        }
        Episodio ep = lista.get(escolha - 1);
        try {
            utilizador.removerEpisodioLista(ep);
            System.out.println("Episódio removido da sua lista.");
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage());
        }
    }

    private void verFilmesVistos() {
        System.out.println("\nFilmes que já viu:");
        boolean algum = false;
        for (Filme f : imdb.getLstFilmes()) {
            if (f.isVisto(utilizador)) {
                System.out.println("  - " + f);
                algum = true;
            }
        }
        if (!algum) System.out.println("  (Ainda não viu nenhum filme.)");
    }

    private Episodio selecionarEpisodio(Serie s) {
        ArrayList<Temporada> temporadas = s.getTemporadas();
        if (temporadas.isEmpty()) {
            System.out.println("Esta série não tem temporadas.");
            return null;
        }
        System.out.println("Temporadas de \"" + s.getTitulo() + "\":");
        for (int i = 0; i < temporadas.size(); i++)
            System.out.println("  " + (i + 1) + ". Temporada " + temporadas.get(i).getNumero()
                    + " (" + temporadas.get(i).getEpisodios().size() + " episódios)");
        int idt = Utils.readIntFromConsole("Escolha a temporada: ");
        if (idt < 1 || idt > temporadas.size()) {
            System.out.println("Opção inválida.");
            return null;
        }
        Temporada t = temporadas.get(idt - 1);
        ArrayList<Episodio> episodios = t.getEpisodios();
        if (episodios.isEmpty()) {
            System.out.println("Esta temporada não tem episódios.");
            return null;
        }
        System.out.println("Episódios da Temporada " + t.getNumero() + ":");
        for (int i = 0; i < episodios.size(); i++)
            System.out.println("  " + (i + 1) + ". Ep." + episodios.get(i).getNumero()
                    + " - " + episodios.get(i).getTitulo());
        int ide = Utils.readIntFromConsole("Escolha o episódio: ");
        if (ide < 1 || ide > episodios.size()) {
            System.out.println("Opção inválida.");
            return null;
        }
        return episodios.get(ide - 1);
    }
}
