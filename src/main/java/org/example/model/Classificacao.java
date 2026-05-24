package org.example.model;

import java.io.Serializable;

/**
 * Representa uma classificação atribuída por um espectador a um recurso.
 * Inclui uma nota de 1 a 10 e um comentário opcional.
 * As classificações ficam guardadas no Recurso, identificadas pelo espectador.
 */
public class Classificacao implements Serializable {

    /** Nota atribuída (1 a 10). */
    private int nota;

    /** Comentário do espectador. */
    private String comentario;

    /** Nome do espectador que classificou (evita referência circular). */
    private String nomeEspectador;

    /**
     * Constrói uma classificação com nota, comentário e nome do espectador.
     *
     * @param espectador espectador que classifica
     * @param nota       nota de 1 a 10
     * @param comentario comentário opcional
     * @throws IllegalArgumentException se a nota não estiver entre 1 e 10
     */
    public Classificacao(Espectador espectador, int nota, String comentario) {
        if (nota < 1 || nota > 10) {
            throw new IllegalArgumentException("A nota deve estar entre 1 e 10.");
        }
        this.nomeEspectador = espectador.getNome();
        this.nota = nota;
        this.comentario = comentario;
    }

    /**
     * Devolve a nota da classificação.
     *
     * @return nota
     */
    public int getNota() {
        return nota;
    }

    /**
     * Devolve o nome do espectador que classificou.
     *
     * @return nome do espectador
     */
    public String getNomeEspectador() {
        return nomeEspectador;
    }

    /**
     * Verifica se esta classificação pertence ao espectador indicado.
     *
     * @param espectador espectador a verificar
     * @return true se pertencer, false caso contrário
     */
    public boolean pertenceA(Espectador espectador) {
        return this.nomeEspectador.equals(espectador.getNome());
    }

    @Override
    public String toString() {
        return String.format("%.1f/10 por %s%s", (double) nota, nomeEspectador,
                (comentario != null && !comentario.isEmpty()) ? " - \"" + comentario + "\"" : "");
    }
}
