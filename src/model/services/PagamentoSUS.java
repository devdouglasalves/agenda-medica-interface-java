package model.services;

import java.time.LocalDateTime;

public class PagamentoSUS implements PagamentoService {

    @Override
    public double calcularValorFinal(double valorBase, LocalDateTime dataConsulta) {
        return 0;
    }
}
