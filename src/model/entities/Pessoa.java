package model.entities;

import model.exceptions.RegraNegocioException;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;

public abstract class Pessoa {

    private String nome;
    private LocalDate dataNascimento;

    public Pessoa() {
    }

    public Pessoa(String nome) {
        this.nome = nome;
    }

    public Pessoa(String nome, LocalDate dataNascimento) {

        if (dataNascimento.isAfter(LocalDate.now())) {
            throw new RegraNegocioException("Data de nascimento não podem ser no futuro.\n");
        }
        this.dataNascimento = dataNascimento;
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public int getIdade() {
        LocalDate dataAgora = LocalDate.now();
        long dias = Period.between(getDataNascimento(), dataAgora).getYears();
        return (int) (dias);
    }
}
