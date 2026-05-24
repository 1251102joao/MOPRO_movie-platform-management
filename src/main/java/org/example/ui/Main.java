package org.example.ui;

import org.example.model.*;
import org.example.utils.Data;

public class Main {

    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("   IMDB - Sistema de Gestão      ");
        System.out.println("=================================================");
        System.out.println("A carregar dados demo...");

        IMDB imdb = construirDadosDemo();

        System.out.println("Dados carregados com sucesso!");
        System.out.println("-------------------------------------------------");
        System.out.println("  CREDENCIAIS DEMO:");
        System.out.println("  Admin    -> username: admin   | password: admin");
        System.out.println("  Espect.  -> username: ana     | password: abc");
        System.out.println("  Espect.  -> username: pedro   | password: qwerty");
        System.out.println("-------------------------------------------------");

        MenuInicial menuInicial = new MenuInicial(imdb);
        menuInicial.run();
    }


    private static IMDB construirDadosDemo() { //Constrói a base de dados com objetos de demonstração.
        IMDB imdb = new IMDB("www.imdb.com");

        // --- Utilizadores ---
        Admin admin = new Admin("admin@imdb.com", "admin", "admin");
        imdb.adicionarUtilizador(admin);

        Espectador ana = new Espectador("ana@email.com", "ana", "abc");
        imdb.adicionarUtilizador(ana);

        Espectador pedro = new Espectador("pedro@email.com", "pedro", "qwerty");
        imdb.adicionarUtilizador(pedro);

        // --- Atores ---
        Ator pierceBrosnan = new Ator("Pierce Brosnan", new Data(1953, 5, 16));
        imdb.adicionarAtor(pierceBrosnan);

        Ator tomHardy = new Ator("Tom Hardy", new Data(1977, 9, 15));
        imdb.adicionarAtor(tomHardy);

        Ator helenMirren = new Ator("Helen Mirren", new Data(1945, 7, 26));
        imdb.adicionarAtor(helenMirren);

        Ator cillianMurphy = new Ator("Cillian Murphy", new Data(1976, 5, 25));
        imdb.adicionarAtor(cillianMurphy);

        Ator emmaWatson = new Ator("Emma Watson", new Data(1990, 4, 15));
        imdb.adicionarAtor(emmaWatson);

        Ator danielRadcliffe = new Ator("Daniel Radcliffe", new Data(1989, 7, 23));
        imdb.adicionarAtor(danielRadcliffe);

        // --- Filmes ---
        try {
            Filme goldenEye = new Filme("GoldenEye", 1995, 130);
            goldenEye.adicionarGenero(Genero.ACTION);
            goldenEye.adicionarGenero(Genero.ADVENTURE);
            goldenEye.adicionarAtor(pierceBrosnan);
            goldenEye.adicionarAtor(helenMirren);
            imdb.adicionarFilme(goldenEye);

            Filme joker = new Filme("Joker", 2009, 122);
            joker.adicionarGenero(Genero.DRAMA);
            joker.adicionarGenero(Genero.HORROR);
            imdb.adicionarFilme(joker);

            Filme the_take = new Filme("The_take", 2009, 130);
            the_take.adicionarGenero(Genero.DRAMA);
            the_take.adicionarGenero(Genero.ACTION);
            the_take.adicionarAtor(tomHardy);
            imdb.adicionarFilme(the_take);

            Filme dunkirk = new Filme("Dunkirk", 2017, 106);
            dunkirk.adicionarGenero(Genero.ACTION);
            dunkirk.adicionarGenero(Genero.DRAMA);
            dunkirk.adicionarGenero(Genero.WAR);
            dunkirk.adicionarAtor(tomHardy);
            imdb.adicionarFilme(dunkirk);

            Filme oppenheimer = new Filme("Oppenheimer", 2023, 180);
            oppenheimer.adicionarGenero(Genero.BIOGRAPHY);
            oppenheimer.adicionarGenero(Genero.DRAMA);
            oppenheimer.adicionarGenero(Genero.HISTORY);
            oppenheimer.adicionarAtor(cillianMurphy);
            imdb.adicionarFilme(oppenheimer);

            Filme harryPotter = new Filme("Harry Potter e a Pedra Filosofal", 2001, 152);
            harryPotter.adicionarGenero(Genero.ADVENTURE);
            harryPotter.adicionarGenero(Genero.FAMILY);
            harryPotter.adicionarGenero(Genero.FANTASY);
            harryPotter.adicionarAtor(danielRadcliffe);
            harryPotter.adicionarAtor(emmaWatson);
            imdb.adicionarFilme(harryPotter);

            // Ana vê e classifica filmes
            goldenEye.marcarComoVisto(ana);
            goldenEye.adicionarClassificacao(new Classificacao(ana, 9, "Clássico do Bond!"));
            dunkirk.marcarComoVisto(ana);
            dunkirk.adicionarClassificacao(new Classificacao(ana, 7, "Muito intenso."));
            harryPotter.marcarComoVisto(ana);
            harryPotter.adicionarClassificacao(new Classificacao(ana, 9, "Mágico!"));

            // Pedro vê e classifica filmes
            goldenEye.marcarComoVisto(pedro);
            goldenEye.adicionarClassificacao(new Classificacao(pedro, 8, "Muito bom."));
            oppenheimer.marcarComoVisto(pedro);
            oppenheimer.adicionarClassificacao(new Classificacao(pedro, 10, "Obra-prima!"));
            harryPotter.marcarComoVisto(pedro);
            harryPotter.adicionarClassificacao(new Classificacao(pedro, 8, "Ótimo filme familiar."));

            // Listas pessoais
            ana.adicionarFilmeLista(goldenEye);
            ana.adicionarFilmeLista(harryPotter);
            pedro.adicionarFilmeLista(oppenheimer);

        } catch (Exception e) {
            System.out.println("[ERRO demo filmes] " + e.getMessage());
        }

        // --- Séries ---
        try {
            Serie peakyBlinders = new Serie("Peaky Blinders", 2013);
            peakyBlinders.adicionarGenero(Genero.CRIME);
            peakyBlinders.adicionarGenero(Genero.DRAMA);

            Temporada t1pb = new Temporada(1);
            Episodio pb1e1 = new Episodio("Episode 1", 1, 58);
            pb1e1.adicionarAtor(cillianMurphy);
            Episodio pb1e2 = new Episodio("Episode 2", 2, 55);
            pb1e2.adicionarAtor(cillianMurphy);
            Episodio pb1e3 = new Episodio("Episode 3", 3, 55);
            pb1e3.adicionarAtor(cillianMurphy);
            t1pb.adicionarEpisodio(pb1e1);
            t1pb.adicionarEpisodio(pb1e2);
            t1pb.adicionarEpisodio(pb1e3);
            peakyBlinders.adicionarTemporada(t1pb);

            Temporada t2pb = new Temporada(2);
            Episodio pb2e1 = new Episodio("Episode 1", 1, 58);
            pb2e1.adicionarAtor(cillianMurphy);
            Episodio pb2e2 = new Episodio("Episode 2", 2, 55);
            pb2e2.adicionarAtor(cillianMurphy);
            t2pb.adicionarEpisodio(pb2e1);
            t2pb.adicionarEpisodio(pb2e2);
            peakyBlinders.adicionarTemporada(t2pb);

            pb1e1.marcarComoVisto(ana);
            pb1e1.adicionarClassificacao(new Classificacao(ana, 9, "Excelente começo!"));
            pb1e2.marcarComoVisto(ana);
            pb1e2.adicionarClassificacao(new Classificacao(ana, 8, "Muito bom."));

            imdb.adicionarSerie(peakyBlinders);

            Serie breakingBad = new Serie("Breaking Bad", 2008);
            breakingBad.adicionarGenero(Genero.CRIME);
            breakingBad.adicionarGenero(Genero.DRAMA);
            breakingBad.adicionarGenero(Genero.THRILLER);

            Temporada t1bb = new Temporada(1);
            Episodio bb1e1 = new Episodio("Pilot", 1, 58);
            bb1e1.adicionarAtor(tomHardy);
            t1bb.adicionarEpisodio(bb1e1);
            breakingBad.adicionarTemporada(t1bb);
            imdb.adicionarSerie(breakingBad);

        } catch (Exception e) {
            System.out.println("[ERRO demo séries] " + e.getMessage());
        }

        return imdb;
    }
}
