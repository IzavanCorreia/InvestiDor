/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.java.model.negocio;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 *
 * @author Izavan
 */
@Entity
public class RendaFixa {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    private String nome;
    private String indexador;
    private double quantidade;
    private double valorUnitarioCompra;
    private double valorUnitarioAtual;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataInicial;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataFinal;
    private String tipo;
    private Boolean imposto;
    
    private String dataInicialString;
    private String dataFinalString;

    @ManyToOne
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
    
    

}
