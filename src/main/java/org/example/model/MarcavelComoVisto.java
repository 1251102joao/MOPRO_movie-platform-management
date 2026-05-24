package org.example.model;

/**
 * Interface que define o contrato para recursos que podem ser marcados como vistos.
 * Implementada por Filme, Serie e Episodio.
 */
public interface MarcavelComoVisto {

    /**
     * Verifica se o recurso já foi visto pelo espectador.
     *
     * @param espectador espectador a verificar
     * @return true se já foi visto, false caso contrário
     */
    boolean isVisto(Espectador espectador);

    /**
     * Marca o recurso como visto pelo espectador.
     *
     * @param espectador espectador que viu o recurso
     * @throws Exception se já tiver sido marcado como visto por este espectador
     */
    void marcarComoVisto(Espectador espectador) throws Exception;
}
