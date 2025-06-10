package model.services;

import java.time.LocalDateTime;

public class PagamentoConvenio implements PagamentoService {

    @Override
    public double calcularValorFinal(double valorBase, LocalDateTime dataConsulta) {
        return valorBase - (valorBase * 0.20);
    }
}
