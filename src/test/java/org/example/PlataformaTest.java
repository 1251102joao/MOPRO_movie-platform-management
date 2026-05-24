package org.example;

import org.example.model.*;
import org.example.utils.Data;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.File;

/**
 * Testes unitários para as principais funcionalidades da plataforma de filmes.
 */
public class PlataformaTest {

    private IMDB imdb;
    private Espectador ana;
    private Espectador pedro;
    private Ator tomHardy;
    private Ator cillianMurphy;
    private Filme dunkirk;
    private Filme oppenheimer;
    private Serie peakyBlinders;

    /**
     * Inicializa os objetos de teste antes de cada teste.
     */
    @Before
    public void setUp() throws Exception {
        imdb = new IMDB("www.imdb.com");

        ana = new Espectador("ana@email.com", "ana", "abc");
        pedro = new Espectador("pedro@email.com", "pedro", "qwerty");
        imdb.adicionarUtilizador(ana);
        imdb.adicionarUtilizador(pedro);
        imdb.adicionarUtilizador(new Admin("admin@email.com", "admin", "admin"));

        tomHardy = new Ator("Tom Hardy", new Data(1977, 9, 15));
        cillianMurphy = new Ator("Cillian Murphy", new Data(1976, 5, 25));
        imdb.adicionarAtor(tomHardy);
        imdb.adicionarAtor(cillianMurphy);

        dunkirk = new Filme("Dunkirk", 2017, 106);
        dunkirk.adicionarGenero(Genero.ACTION);
        dunkirk.adicionarAtor(tomHardy);
        imdb.adicionarFilme(dunkirk);

        oppenheimer = new Filme("Oppenheimer", 2023, 180);
        oppenheimer.adicionarGenero(Genero.BIOGRAPHY);
        oppenheimer.adicionarAtor(cillianMurphy);
        imdb.adicionarFilme(oppenheimer);

        peakyBlinders = new Serie("Peaky Blinders", 2013);
        peakyBlinders.adicionarGenero(Genero.CRIME);
        Temporada t1 = new Temporada(1);
        Episodio ep1 = new Episodio("Episode 1", 1, 58);
        ep1.adicionarAtor(cillianMurphy);
        t1.adicionarEpisodio(ep1);
        peakyBlinders.adicionarTemporada(t1);
        imdb.adicionarSerie(peakyBlinders);
    }

    // ==================== TESTES DE LOGIN ====================

    /**
     * Testa login com credenciais corretas.
     */
    @Test
    public void testLoginCorreto() {
        UtilizadorRegistado u = imdb.login("ana", "abc");
        assertNotNull(u);
        assertTrue(u instanceof Espectador);
    }

    /**
     * Testa login com password errada.
     */
    @Test
    public void testLoginPasswordErrada() {
        UtilizadorRegistado u = imdb.login("ana", "errada");
        assertNull(u);
    }

    /**
     * Testa login com username inexistente.
     */
    @Test
    public void testLoginUsernameInexistente() {
        UtilizadorRegistado u = imdb.login("naoexiste", "abc");
        assertNull(u);
    }

    /**
     * Testa que o admin faz login corretamente.
     */
    @Test
    public void testLoginAdmin() {
        UtilizadorRegistado u = imdb.login("admin", "admin");
        assertNotNull(u);
        assertTrue(u instanceof Admin);
    }

    // ==================== TESTES DE FILMES ====================

    /**
     * Testa que não é possível adicionar filmes duplicados.
     */
    @Test(expected = Exception.class)
    public void testFilmeDuplicado() throws Exception {
        Filme duplicado = new Filme("Dunkirk", 2017, 106);
        duplicado.adicionarAtor(tomHardy);
        imdb.adicionarFilme(duplicado);
    }

    /**
     * Testa que a classificação média é calculada corretamente.
     */
    @Test
    public void testClassificacaoMediaFilme() throws Exception {
        dunkirk.marcarComoVisto(ana);
        dunkirk.marcarComoVisto(pedro);
        dunkirk.adicionarClassificacao(new Classificacao(ana, 8, ""));
        dunkirk.adicionarClassificacao(new Classificacao(pedro, 6, ""));
        assertEquals(7.0, dunkirk.getClassificacaoMedia(), 0.01);
    }

    /**
     * Testa que filme sem classificações tem média 0.
     */
    @Test
    public void testClassificacaoMediaSemClassificacoes() {
        assertEquals(0.0, dunkirk.getClassificacaoMedia(), 0.01);
    }

    /**
     * Testa que o ator não pode ser adicionado ao mesmo filme duas vezes.
     */
    @Test(expected = Exception.class)
    public void testAtorDuplicadoFilme() throws Exception {
        dunkirk.adicionarAtor(tomHardy);
    }

    /**
     * Testa que um utilizador não pode classificar um filme não visto.
     */
    @Test(expected = Exception.class)
    public void testClassificarFilmeNaoVisto() throws Exception {
        dunkirk.adicionarClassificacao(new Classificacao(ana, 8, ""));
    }

    /**
     * Testa que um utilizador não pode classificar o mesmo filme duas vezes.
     */
    @Test(expected = Exception.class)
    public void testClassificarFilmeDuasVezes() throws Exception {
        dunkirk.marcarComoVisto(ana);
        dunkirk.adicionarClassificacao(new Classificacao(ana, 8, ""));
        dunkirk.adicionarClassificacao(new Classificacao(ana, 9, ""));
    }

    /**
     * Testa a categoria de classificação de filmes.
     */
    @Test
    public void testCategoriaClassificacaoFilme() throws Exception {
        dunkirk.marcarComoVisto(ana);
        dunkirk.adicionarClassificacao(new Classificacao(ana, 9, ""));
        assertEquals("Bom", dunkirk.getCategoriaClassificacao());

        oppenheimer.marcarComoVisto(pedro);
        oppenheimer.adicionarClassificacao(new Classificacao(pedro, 5, ""));
        assertEquals("Médio", oppenheimer.getCategoriaClassificacao());

        Filme fraco = new Filme("Filme Fraco", 2020, 90);
        fraco.adicionarAtor(tomHardy);
        imdb.adicionarFilme(fraco);
        fraco.marcarComoVisto(ana);
        fraco.adicionarClassificacao(new Classificacao(ana, 2, ""));
        assertEquals("Fraco", fraco.getCategoriaClassificacao());
    }

    // ==================== TESTES DE MARCAR COMO VISTO ====================

    /**
     * Testa marcar filme como visto.
     */
    @Test
    public void testMarcarFilmeComoVisto() throws Exception {
        assertFalse(dunkirk.isVisto(ana));
        dunkirk.marcarComoVisto(ana);
        assertTrue(dunkirk.isVisto(ana));
    }

    /**
     * Testa que não é possível marcar o mesmo filme como visto duas vezes.
     */
    @Test(expected = Exception.class)
    public void testMarcarFilmeVistoDuasVezes() throws Exception {
        dunkirk.marcarComoVisto(ana);
        dunkirk.marcarComoVisto(ana);
    }

    /**
     * Testa marcar episódio como visto.
     */
    @Test
    public void testMarcarEpisodioComoVisto() throws Exception {
        Episodio ep = peakyBlinders.getTemporada(1).getEpisodio(1);
        assertFalse(ep.isVisto(ana));
        ep.marcarComoVisto(ana);
        assertTrue(ep.isVisto(ana));
    }

    // ==================== TESTES DE LISTA PESSOAL ====================

    /**
     * Testa adicionar filme à lista pessoal.
     */
    @Test
    public void testAdicionarFilmeLista() throws Exception {
        ana.adicionarFilmeLista(dunkirk);
        assertTrue(ana.getListaFilmes().contains(dunkirk));
    }

    /**
     * Testa que não é possível adicionar o mesmo filme duas vezes à lista pessoal.
     */
    @Test(expected = Exception.class)
    public void testFilmeDuplicadoNaLista() throws Exception {
        ana.adicionarFilmeLista(dunkirk);
        ana.adicionarFilmeLista(dunkirk);
    }

    /**
     * Testa remover filme da lista pessoal.
     */
    @Test
    public void testRemoverFilmeLista() throws Exception {
        ana.adicionarFilmeLista(dunkirk);
        ana.removerFilmeLista(dunkirk);
        assertFalse(ana.getListaFilmes().contains(dunkirk));
    }

    /**
     * Testa remover filme que não está na lista.
     */
    @Test(expected = Exception.class)
    public void testRemoverFilmeNaoNaLista() throws Exception {
        ana.removerFilmeLista(dunkirk);
    }

    // ==================== TESTES DE SÉRIES ====================

    /**
     * Testa que não é possível adicionar séries duplicadas.
     */
    @Test(expected = Exception.class)
    public void testSerieDuplicada() throws Exception {
        Serie duplicada = new Serie("Peaky Blinders", 2013);
        imdb.adicionarSerie(duplicada);
    }

    /**
     * Testa adição de temporada a série.
     */
    @Test
    public void testAdicionarTemporada() throws Exception {
        peakyBlinders.adicionarTemporada(new Temporada(2));
        assertNotNull(peakyBlinders.getTemporada(2));
        assertEquals(2, peakyBlinders.getTemporadas().size());
    }

    /**
     * Testa que não é possível adicionar temporada duplicada.
     */
    @Test(expected = Exception.class)
    public void testTemporadaDuplicada() throws Exception {
        peakyBlinders.adicionarTemporada(new Temporada(1));
    }

    // ==================== TESTES DE PESQUISA ====================

    /**
     * Testa pesquisa de filmes por título.
     */
    @Test
    public void testPesquisarFilmes() {
        assertEquals(1, imdb.pesquisarFilmes("Dunkirk").size());
        assertEquals(1, imdb.pesquisarFilmes("dunk").size());
        assertEquals(0, imdb.pesquisarFilmes("Titanic").size());
    }

    /**
     * Testa pesquisa de atores por nome.
     */
    @Test
    public void testPesquisarAtores() {
        assertEquals(1, imdb.pesquisarAtores("Tom").size());
        assertEquals(1, imdb.pesquisarAtores("hardy").size());
        assertEquals(0, imdb.pesquisarAtores("Brosnan").size());
    }

    /**
     * Testa pesquisa de séries.
     */
    @Test
    public void testPesquisarSeries() {
        assertEquals(1, imdb.pesquisarSeries("Peaky").size());
        assertEquals(0, imdb.pesquisarSeries("Game").size());
    }

    // ==================== TESTES DE ORDENAÇÃO ====================

    /**
     * Testa ordenação de filmes por título.
     */
    @Test
    public void testFilmesOrdenadosPorTitulo() {
        var filmes = imdb.getFilmesOrdenadosPorTitulo();
        assertEquals("Dunkirk", filmes.get(0).getTitulo());
        assertEquals("Oppenheimer", filmes.get(1).getTitulo());
    }

    /**
     * Testa ordenação de atores por nome.
     */
    @Test
    public void testAtoresOrdenadosPorNome() {
        var atores = imdb.getAtoresOrdenadosPorNome();
        assertEquals("Cillian Murphy", atores.get(0).getNome());
        assertEquals("Tom Hardy", atores.get(1).getNome());
    }

    // ==================== TESTES DE SERIALIZAÇÃO ====================

    /**
     * Testa gravação e leitura de dados em ficheiro.
     */
    @Test
    public void testSerializacao() throws Exception {
        String caminho = "test_dados.ser";
        imdb.gravarFicheiro(caminho);

        IMDB carregado = IMDB.carregarFicheiro(caminho);
        assertNotNull(carregado);
        assertEquals(imdb.getLstFilmes().size(), carregado.getLstFilmes().size());
        assertEquals(imdb.getLstAtores().size(), carregado.getLstAtores().size());
        assertEquals(imdb.getLstUtilizadores().size(), carregado.getLstUtilizadores().size());

        new File(caminho).delete();
    }

    // ==================== TESTES DE CLASSIFICAÇÃO ====================

    /**
     * Testa que a nota deve estar entre 1 e 10.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNotaInvalida() {
        new Classificacao(ana, 11, "");
    }

    /**
     * Testa nota mínima válida.
     */
    @Test
    public void testNotaMinima() throws Exception {
        dunkirk.marcarComoVisto(ana);
        dunkirk.adicionarClassificacao(new Classificacao(ana, 1, ""));
        assertEquals(1.0, dunkirk.getClassificacaoMedia(), 0.01);
    }

    /**
     * Testa nota máxima válida.
     */
    @Test
    public void testNotaMaxima() throws Exception {
        dunkirk.marcarComoVisto(ana);
        dunkirk.adicionarClassificacao(new Classificacao(ana, 10, ""));
        assertEquals(10.0, dunkirk.getClassificacaoMedia(), 0.01);
    }

    // ==================== TESTES DE HERANÇA ====================

    /**
     * Testa que Admin é um UtilizadorRegistado.
     */
    @Test
    public void testAdminEUtilizadorRegistado() {
        UtilizadorRegistado admin = new Admin("a@a.com", "a", "a");
        assertTrue(admin instanceof UtilizadorRegistado);
        assertTrue(admin instanceof Admin);
    }

    /**
     * Testa que Espectador é um UtilizadorRegistado.
     */
    @Test
    public void testEspectadorEUtilizadorRegistado() {
        assertTrue(ana instanceof UtilizadorRegistado);
        assertTrue(ana instanceof Espectador);
    }

    /**
     * Testa que Filme é um Recurso.
     */
    @Test
    public void testFilmeERecurso() {
        assertTrue(dunkirk instanceof Recurso);
        assertTrue(dunkirk instanceof MarcavelComoVisto);
        assertTrue(dunkirk instanceof Pesquisavel);
    }

    /**
     * Testa que Serie é um Recurso.
     */
    @Test
    public void testSerieERecurso() {
        assertTrue(peakyBlinders instanceof Recurso);
        assertTrue(peakyBlinders instanceof MarcavelComoVisto);
        assertTrue(peakyBlinders instanceof Pesquisavel);
    }
}
