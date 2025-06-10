package model.entities;

import model.enums.Especialidade;

public abstract class Profissional extends Pessoa {

    private String crm;
    private Double taxaBase;

    private Especialidade especialidade;

    public Profissional() {
        super();
    }


    public Profissional(String nome, String crm, Double taxaBase, Especialidade especialidade) {
        super(nome);
        this.crm = crm;
        this.especialidade = especialidade;
        this.taxaBase = taxaBase;
    }

    public String getCrm() {
        return crm;
    }

    public Double getTaxaBase() {
        return taxaBase;
    }

    public void setTaxaBase(Double taxaBase) {
        this.taxaBase = taxaBase;
    }

    public Especialidade getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(Especialidade especialidade) {
        this.especialidade = especialidade;
    }

    public abstract double getValorConsultaBase();
}
