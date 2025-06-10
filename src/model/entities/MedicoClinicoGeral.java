package model.entities;

import model.enums.Especialidade;

public class MedicoClinicoGeral extends Profissional {

    public MedicoClinicoGeral() {
        super();
    }

    public MedicoClinicoGeral(String nome, String crm) {
        super(nome, crm, Especialidade.CLINICO_GERAL.getTaxaBase(), Especialidade.CLINICO_GERAL);
    }

    @Override
    public double getValorConsultaBase() {
        return getTaxaBase();
    }
}
