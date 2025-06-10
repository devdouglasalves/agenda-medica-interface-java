package model.services;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class PagamentoParticular implements PagamentoService {

    @Override
    public double calcularValorFinal(double valorBase, LocalDateTime dataConsulta) {

        boolean finalDeSemana = dataConsulta.getDayOfWeek() == DayOfWeek.SATURDAY || dataConsulta.getDayOfWeek() == DayOfWeek.SUNDAY;

        if (finalDeSemana) {
            return valorBase * 1.10;
        } else {
            return valorBase;
        }
    }
}
