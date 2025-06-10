package model.entities;

import model.exceptions.RegraNegocioException;

import java.time.LocalDate;

public class Paciente extends Pessoa {

    private String cpf;

    public Paciente() {
        super();
    }

    public Paciente(String nome, LocalDate dataNascimento, String cpf) {
        super(nome, dataNascimento);

        if (getIdade() < 18) {
            throw new RegraNegocioException("Paciente é menor de idade, não pode realizar agendamentos.\n");
        }
        this.cpf = cpf;
    }

    public String getCpf() {
        return cpf;
    }
}
