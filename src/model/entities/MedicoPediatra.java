package model.entities;

import model.enums.Especialidade;

public class MedicoPediatra extends Profissional{

    public MedicoPediatra() {
        super();
    }

    public MedicoPediatra(String nome, String crm) {
        super(nome, crm, Especialidade.PEDIATRA.getTaxaBase(), Especialidade.PEDIATRA);
    }

    @Override
    public double getValorConsultaBase() {
        return getTaxaBase();
    }
}
