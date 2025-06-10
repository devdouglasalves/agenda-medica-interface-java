package model.entities;

import model.enums.Especialidade;

public class MedicoOrtopedista extends Profissional {

    public MedicoOrtopedista() {
        super();
    }

    public MedicoOrtopedista(String nome, String crm) {
        super(nome, crm, Especialidade.ORTOPEDISTA.getTaxaBase(), Especialidade.ORTOPEDISTA);
    }

    @Override
    public double getValorConsultaBase() {
        return getTaxaBase();
    }
}
