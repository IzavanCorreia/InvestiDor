/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.java.model.negocio;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import src.java.model.validacao.PontoDecimal;

/**
 *
 * @author Izavan
 */
@Entity
public class RendaVariavel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @PontoDecimal
    @NotNull(message = "O campo nome não pode ser nulo")
    @DecimalMin("0.01")
    @DecimalMax("9999999999.99")
    private double valorCompra;

    @NotNull(message = "O campo nome não pode ser nulo")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataCompra;
    private String dataCompraFormatada;

    @NotNull(message = "O campo nome não pode ser nulo")
    @Min(value = 1, message = "A quantidade deve ser maior ou igual a um")
    @Max(value = 9999999999L, message = "A quantidade deve ser menor ou igual a 9999999999")
    private int quantidade;

    private double valorCompraTotal;
    private double valorAtualTotal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    private String valorCompraTotalFormatado;
    private String valorAtualTotalFormatado;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getValorCompra() {
        return valorCompra;
    }

    public void setValorCompra(double valorCompra) {
        this.valorCompra = valorCompra;
    }

    public double getValorCompraTotal() {
        return valorCompraTotal;
    }

    public void setValorCompraTotal() {
        this.valorCompraTotal = valorCompra * quantidade;
        setValorCompraTotalFormatado(valorCompraTotal);
    }

    public double getValorAtualTotal() {
        return valorAtualTotal;
    }

    public void setValorAtualTotal() {
        this.valorAtualTotal = ticket.getValorAtual() * quantidade;
        setValorAtualTotalFormatado(valorAtualTotal);
    }

    public Date getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(Date dataCompra) {
        this.dataCompra = dataCompra;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public String getDataCompraFormatada() {
        return dataCompraFormatada;
    }

    public void setDataCompraFormatada() {
        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
        Date data = getDataCompra();
        String dataFormatada = formatador.format(data);
        this.dataCompraFormatada = dataFormatada;
    }

    public String getValorCompraTotalFormatado() {
        return valorCompraTotalFormatado;
    }

    public void setValorCompraTotalFormatado(double valorCompraTotal) {
        DecimalFormat df = new DecimalFormat("#.00"); // Define o padrão de formatação com duas casas decimais
        this.valorCompraTotalFormatado = df.format(valorCompraTotal); // Formata o número original
    }

    public String getValorAtualTotalFormatado() {
        return valorAtualTotalFormatado;
    }

    public void setValorAtualTotalFormatado(double valorAtualTotal) {
        DecimalFormat df = new DecimalFormat("#.00"); // Define o padrão de formatação com duas casas decimais
        this.valorAtualTotalFormatado = df.format(valorAtualTotal);
    }

}
