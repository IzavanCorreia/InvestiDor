/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.java.model.negocio;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import src.java.model.validacao.CPF;

/**
 *
 * @author Izavan
 */
@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "O CPF não pode ser nulo")
    @NotEmpty(message = "O campo não pode estar vazio")
    @CPF
    private String cpf;

    @NotNull(message = "O nome não pode ser nulo")
    @NotEmpty(message = "O nome não pode estar vazio")
    @Size(min = 2, max = 30, message = "O nome deve ter entre 2 e 50 caracteres")
    private String nome;

    @NotNull(message = "O sobrenome não pode ser nulo")
    @NotEmpty(message = "O nome não pode estar vazio")
    @Size(min = 2, max = 50, message = "O nome deve ter entre 2 e 50 caracteres")
    private String sobrenome;

    @NotNull(message = "O telefone não pode ser nulo")
    @NotEmpty(message = "O telefone não pode estar vazio")
    @Pattern(regexp = "^(\\d{2} \\d{9}|\\d{2} \\d{5}-\\d{4}|\\(\\d{2}\\) \\d{9})$", message = "Formato de telefone inválido")
    private String telefone;

    @NotNull(message = "O e-mail não pode ser nulo")
    @NotEmpty(message = "O e-mail não pode estar vazio")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "E-mail inválido")
    private String email;

    @NotNull(message = "A senha não pode ser nula")
    @NotEmpty(message = "A senha não pode estar vazia")
    private String senha;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RendaFixa> rendaFixa = new ArrayList<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RendaVariavel> rendaVariavel = new ArrayList<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Meta> metas = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public List<RendaFixa> getRendaFixa() {
        return rendaFixa;
    }

    public void setRendaFixa(List<RendaFixa> rendaFixa) {
        this.rendaFixa = rendaFixa;
    }

    public List<RendaVariavel> getRendaVariavel() {
        return rendaVariavel;
    }

    public void setRendaVariavel(List<RendaVariavel> rendaVariavel) {
        this.rendaVariavel = rendaVariavel;
    }

    public List<Meta> getMetas() {
        return metas;
    }

    public void setMetas(List<Meta> metas) {
        this.metas = metas;
    }

}
