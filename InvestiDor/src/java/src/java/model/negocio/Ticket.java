/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.java.model.negocio;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import src.java.model.validacao.PontoDecimal;

/**
 *
 * @author Izavan
 */
@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "O campo nome não pode ser nulo")
    @Size(min = 1, max = 100)
    private String nome;

    @NotNull(message = "O campo nome da empresa não pode ser nulo")
    @Size(min = 1, max = 200)
    private String nomeEmpresa;

    @PontoDecimal
    @NotNull(message = "O campo valor atual não pode ser nulo")
    @DecimalMin("0.01")
    @DecimalMax("99999.99")
    private double valorAtual;

    @NotNull(message = "O campo tipo do ticket não pode ser nulo")
    private String tipoTicket;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RendaVariavel> rendaVariavel = new ArrayList<>();

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

    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
    }

    public double getValorAtual() {
        return valorAtual;
    }

    public void setValorAtual(double valorAtual) {
        this.valorAtual = valorAtual;
    }

    public String getTipoTicket() {
        return tipoTicket;
    }

    public void setTipoTicket(String tipoTicket) {
        this.tipoTicket = tipoTicket;
    }

    public List<RendaVariavel> getRendaVariavel() {
        return rendaVariavel;
    }

    public void setRendaVariavel(List<RendaVariavel> rendaVariavel) {
        this.rendaVariavel = rendaVariavel;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Ticket other = (Ticket) obj;
        return Objects.equals(this.nome, other.nome)
                && Objects.equals(this.nomeEmpresa, other.nomeEmpresa)
                && this.valorAtual == other.valorAtual;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + Objects.hashCode(this.nome);
        hash = 41 * hash + Objects.hashCode(this.nomeEmpresa);
        hash = 41 * hash + (int) (Double.doubleToLongBits(this.valorAtual) ^ (Double.doubleToLongBits(this.valorAtual) >>> 32));
        return hash;
    }

}
