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
import javax.persistence.TemporalType;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.DecimalMin;
import src.java.model.validacao.PontoDecimal;

/**
 *
 * @author Izavan
 */
@Entity
public class RendaFixa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "O campo nome não pode ser nulo")
    @Size(min = 1, max = 100)
    private String nome;

    @NotNull(message = "O campo indexador não pode ser nulo")
    @Size(min = 1, max = 100)
    private String indexador;

    @PontoDecimal
    @NotNull(message = "O campo quantidade não pode ser nulo")
    @DecimalMin("0.01")
    @DecimalMax("99999.99")
    private double quantidade;

    @PontoDecimal
    @NotNull(message = "O campo valor unitário de compra não pode ser nulo")
    @DecimalMin("0.01")
    @DecimalMax("9999999999.99")
    private double valorUnitarioCompra;

    private double valorTotalCompra;

    @PontoDecimal
    @NotNull(message = "O campo valor unitário atual não pode ser nulo")
    @DecimalMin("0.01")
    @DecimalMax("9999999999.99")
    private double valorUnitarioAtual;

    private double valorTotalAtual;

    @NotNull(message = "O campo data inicial não pode ser nulo")
    @Temporal(TemporalType.DATE)
    private Date dataInicial;

    @NotNull(message = "O campo data final não pode ser nulo")
    @Temporal(TemporalType.DATE)
    private Date dataFinal;

    @NotNull(message = "O campo tipo não pode ser nulo")
    @Size(min = 1, max = 100)
    private String tipo;

    private boolean imposto;

    private String dataInicialString;
    private String dataFinalString;

    private String valorCompraTotalFormatado;
    private String valorAtualTotalFormatado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIndexador() {
        return indexador;
    }

    public void setIndexador(String indexador) {
        this.indexador = indexador;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }

    public double getValorUnitarioCompra() {
        return valorUnitarioCompra;
    }

    public void setValorUnitarioCompra(double valorUnitarioCompra) {
        this.valorUnitarioCompra = valorUnitarioCompra;
    }

    public double getValorUnitarioAtual() {
        return valorUnitarioAtual;
    }

    public void setValorUnitarioAtual(double valorUnitarioAtual) {
        this.valorUnitarioAtual = valorUnitarioAtual;
    }

    public double getValorTotalCompra() {
        return valorTotalCompra;
    }

    public void setValorTotalCompra() {
        this.valorTotalCompra = getValorUnitarioCompra() * quantidade;
        setValorCompraTotalFormatado(valorTotalCompra);
    }

    public double getValorTotalAtual() {
        return valorTotalAtual;
    }

    public void setValorTotalAtual() {
        this.valorTotalAtual = getValorUnitarioAtual() * quantidade;
        setValorAtualTotalFormatado(valorTotalAtual);
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Date getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(Date dataInicial) {
        this.dataInicial = dataInicial;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Boolean getImposto() {
        return imposto;
    }

    public void setImposto(Boolean imposto) {
        this.imposto = imposto;
    }

    public String getDataInicialString() {
        return dataInicialString;
    }

    public void setDataInicialString() {
        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
        Date data = getDataInicial();
        String dataFormatada = formatador.format(data);
        this.dataInicialString = dataFormatada;
    }

    public String getDataFinalString() {
        return dataFinalString;
    }

    public void setDataFinalString() {
        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
        Date data = getDataFinal();
        String dataFormatada = formatador.format(data);
        this.dataFinalString = dataFormatada;
    }

    @AssertTrue(message = "A data final deve ser maior que a data inicial")
    public boolean isDataFinalMaiorQueInicial() {
        return dataFinal.after(dataInicial);
    }

    public String getValorCompraTotalFormatado() {
        return valorCompraTotalFormatado;
    }

    public void setValorCompraTotalFormatado(double valorTotalCompra) {
        DecimalFormat df = new DecimalFormat("#.00"); // Define o padrão de formatação com duas casas decimais
        this.valorCompraTotalFormatado = df.format(valorTotalCompra); // Formata o número original
    }

    public String getValorAtualTotalFormatado() {
        return valorAtualTotalFormatado;
    }

    public void setValorAtualTotalFormatado(double valorTotalAtual) {
        DecimalFormat df = new DecimalFormat("#.00"); // Define o padrão de formatação com duas casas decimais
        this.valorAtualTotalFormatado = df.format(valorTotalAtual);
    }
}
