package model.entities;

import model.enums.TipoPagamento;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Agendamento {

    private static DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private Paciente paciente;
    private Profissional profissional;
    private LocalDateTime dataConsulta;
    private Double valorFinal;
    private TipoPagamento tipoPagamento;

    public Agendamento() {
    }

    public Agendamento(Paciente paciente, Profissional profissional, LocalDateTime dataConsulta, Double valorFinal, TipoPagamento tipoPagamento) {
        this.paciente = paciente;
        this.profissional = profissional;
        this.dataConsulta = dataConsulta;
        this.valorFinal = valorFinal;
        this.tipoPagamento = tipoPagamento;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Profissional getProfissional() {
        return profissional;
    }

    public void setProfissional(Profissional profissional) {
        this.profissional = profissional;
    }

    public LocalDateTime getDataConsulta() {
        return dataConsulta;
    }

    public void setDataConsulta(LocalDateTime dataConsulta) {
        this.dataConsulta = dataConsulta;
    }

    public Double getValorFinal() {
        return valorFinal;
    }

    public void setValorFinal(Double valorFinal) {
        this.valorFinal = valorFinal;
    }

    public TipoPagamento getTipoPagamento() {
        return tipoPagamento;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Paciente: " + getPaciente().getNome() + " (CPF: " + getPaciente().getCpf() + ")\n");
        sb.append("Médico: " + getProfissional().getNome() + " (" + getProfissional().getEspecialidade().getDescricao() + " - CRM: " + getProfissional().getCrm() + ")\n");
        sb.append("Data: " + fmt.format(getDataConsulta()) + "\n");
        sb.append("Valor final: " + String.format("%.2f", getValorFinal()));

        return sb.toString();
    }

    public static String tituloArquivoCsv() {
        return "NomePaciente,CPF,Profissional,CRM,DataConsulta,TipoPagamento";
    }

    public String formatoCsv() {
        StringBuilder sb = new StringBuilder();
        sb.append(getPaciente().getNome() + ","
                + getPaciente().getCpf() + ","
                + getProfissional().getNome() + ","
                + getProfissional().getCrm() + ","
                + fmt.format(getDataConsulta()) + ","
                + tipoPagamento.getDescricao());
        return sb.toString();
    }
}
