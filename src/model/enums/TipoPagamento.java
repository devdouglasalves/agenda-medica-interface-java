package model.enums;

public enum TipoPagamento {

    PARTICULAR("Particular"),
    CONVENIO("Convenio"),
    SUS("SUS");

    private final String descricao;

    TipoPagamento(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}
