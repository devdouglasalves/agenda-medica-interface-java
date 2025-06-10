package model.services;

import java.time.LocalDateTime;

public interface PagamentoService {

    double calcularValorFinal(double valorBase, LocalDateTime dataConsulta);
}
