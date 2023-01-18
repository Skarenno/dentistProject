package com.example.dentist.Casi;

import java.util.Arrays;

public class Caso implements Comparable{
    String nomeCaso;

    String descrizione;


    int diffFromAnswers = 0;
    int[] answerArray;

    public Caso(String nomeCaso, String descrizione, int[] answerArray) {
        this.nomeCaso = nomeCaso;
        this.descrizione = descrizione;
        this.answerArray = answerArray;
    }

    public String getNomeCaso() {
        return nomeCaso;
    }

    public void setNomeCaso(String nomeCaso) {
        this.nomeCaso = nomeCaso;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public int getDiffFromAnswers() {
        return diffFromAnswers;
    }

    public void setDiffFromAnswers(int diffFromAnswers) {
        this.diffFromAnswers = diffFromAnswers;
    }

    public int[] getAnswerArray() {
        return answerArray;
    }

    public void setAnswerArray(int[] answerArray) {
        this.answerArray = answerArray;
    }

    public void fillAnswerArray(int[] answerArray){
        this.answerArray = answerArray;
    }

    @Override
    public String toString() {
        return "Caso{" +
                "nomeCaso='" + nomeCaso + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", diffFromAnswers=" + diffFromAnswers +
                ", answerArray=" + Arrays.toString(answerArray) +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        Caso c = (Caso) o;
        return this.diffFromAnswers - c.diffFromAnswers;
    }
}
