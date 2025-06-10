package model.services;

import model.entities.Agendamento;
import model.entities.Paciente;
import model.entities.Profissional;
import model.enums.TipoPagamento;
import model.exceptions.RegraNegocioException;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class AgendamentoService {


    public Agendamento agendarConsultar(Paciente paciente, Profissional profissional, LocalDateTime data, TipoPagamento tipoPagamento) {

        if (data.isBefore(LocalDateTime.now())) {
            throw new RegraNegocioException("Data da consulta está no passado.\n");
        }

        LocalTime abertura = LocalTime.of(8,0 );
        LocalTime fechamento = LocalTime.of(18, 0);
        LocalTime inicio = data.toLocalTime();
        LocalTime fim = data.toLocalTime().plusMinutes(60);

        if (inicio.isBefore(abertura) || fechamento.isBefore(fim)) {
            throw new RegraNegocioException("Consulta está ultrapassando o horário comercial (08:00 - 18:00).");
        }

        double precoMedico = profissional.getValorConsultaBase();

        PagamentoService pagamento = switch (tipoPagamento) {
            case PARTICULAR -> new PagamentoParticular();
            case CONVENIO -> new PagamentoConvenio();
            case SUS -> new PagamentoSUS();
        };

        double valorFinal = pagamento.calcularValorFinal(precoMedico, data);

        return new Agendamento(paciente, profissional, data, valorFinal, tipoPagamento);
    }
}
