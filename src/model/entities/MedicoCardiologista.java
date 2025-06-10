package model.entities;

import model.enums.Especialidade;

public class MedicoCardiologista extends  Profissional {

    public MedicoCardiologista() {
        super();
    }

    public MedicoCardiologista(String nome, String crm) {
        super(nome, crm, Especialidade.CARDIOLOGISTA.getTaxaBase(), Especialidade.CARDIOLOGISTA);
    }

    @Override
    public double getValorConsultaBase() {
        return getTaxaBase();
    }
}
