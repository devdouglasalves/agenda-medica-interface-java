package model.enums;

public enum Especialidade {

    CLINICO_GERAL(100.0, "Clinico-Geral"),
    PEDIATRA(120.0, "Pediatra"),
    CARDIOLOGISTA(180.0, "Cardiologista"),
    ORTOPEDISTA(150.0, "Ortopedista");

    private final double taxaBase;
    private final String descricao;

    Especialidade(double taxaBase, String descricao) {
        this.taxaBase = taxaBase;
        this.descricao = descricao;
    }

    public double getTaxaBase() {
        return taxaBase;
    }

    public String getDescricao() {
        return descricao;
    }
}
