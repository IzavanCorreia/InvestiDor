/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.java.controllers;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import src.java.model.dao.ManagerDao;
import src.java.model.negocio.RendaFixa;
import src.java.model.negocio.RendaVariavel;
import src.java.model.negocio.Usuario;

/**
 *
 * @author Izavan
 */
@ManagedBean
@SessionScoped
public class UsuarioController {

    @Valid
    private Usuario usuario;
    @Valid
    private Usuario selusuario;
    private Validator validator;
    private static final String PASSWORD_REGEX = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[$&@#])[0-9a-zA-Z$&@#]{8,}$";
    private static final String ERROR_MESSAGE = "A senha deve conter pelo menos um dígito, uma letra minúscula, uma letra maiúscula, "
            + "um dos caracteres especiais ($, *, &, @, #) e ter no mínimo 8 caracteres.";

    @PostConstruct
    public void init() {
        this.usuario = new Usuario();
        this.selusuario = null;
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    public void inserir(String confirma) {
        

        String confirmaNova = sha512(confirma);
        String senhaQueVaiProBanco = sha512(this.usuario.getSenha());
        this.usuario.setSenha(senhaQueVaiProBanco);

        if (!confirmaNova.equals(this.usuario.getSenha())) {

            FacesContext.getCurrentInstance()
                    .addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    "Erro!", "A senha não bate com a confirmação"));

            return;

        }

        if (verificarExistenciaUsuarioEmail(usuario.getEmail())) {
            FacesContext.getCurrentInstance()
                    .addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    "Erro!", "o e-mail informado não está disponível"));

            return;
        }
        
        System.out.println(validatePassword(confirma));
        
          if (!validatePassword(confirma)) {
            FacesContext.getCurrentInstance()
                    .addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    "Erro!", ERROR_MESSAGE));

            return;
        }

        if (verificarExistenciaUsuarioCpf(usuario.getCpf())) {
            FacesContext.getCurrentInstance()
                    .addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    "Erro!", "o cpf informado não está disponível"));

            return;
        }

        List<String> mensagens = new ArrayList<>();
        if (!validator.validate(this.usuario).isEmpty()) {
            for (ConstraintViolation<Usuario> violation : validator.validate(this.usuario)) {
                mensagens.add(violation.getMessage());
            }

        }
        if (!mensagens.isEmpty()) {
            FacesContext.getCurrentInstance()
                    .addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    "Erro!", String.join("<br>", mensagens)));
            return;
        }

        ManagerDao.getCurrentInstance().insert(this.usuario);

        this.usuario = new Usuario();

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Usuario cadastrado com sucesso!"));

    }

    public boolean verificarExistenciaUsuarioEmail(String email) {
        String query = "SELECT u FROM Usuario u WHERE u.email = :email";
        List<Usuario> usuarios = ManagerDao.getCurrentInstance().read(query, Usuario.class, "email", email);

        return !usuarios.isEmpty();
    }

    public boolean verificarExistenciaUsuarioCpf(String cpf) {
        String query = "SELECT u FROM Usuario u WHERE u.cpf = :cpf";
        List<Usuario> usuarios = ManagerDao.getCurrentInstance().read(query, Usuario.class, "cpf", cpf);

        return !usuarios.isEmpty();
    }

    public void deletar() {
        ManagerDao.getCurrentInstance().delete(this.selusuario);

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("usuario deletado com sucesso!"));
    }

    public void clearSelection() {
        this.selusuario = null;

    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getSelUsuario() {
        return selusuario;
    }

    public void alterar(String senha) {

        if (!this.selusuario.getSenha().equals(senha)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Erro!", "A senha não confere"));

            return;
        }

        List<String> mensagens = new ArrayList<>();
        if (!validator.validate(this.selusuario).isEmpty()) {
            for (ConstraintViolation<Usuario> violation : validator.validate(this.selusuario)) {
                mensagens.add(violation.getMessage());
            }

        }
        if (!mensagens.isEmpty()) {
            FacesContext.getCurrentInstance()
                    .addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    "Erro!", String.join("<br>", mensagens)));
            return;
        }

        ManagerDao.getCurrentInstance().update(this.selusuario);

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Usuario alterado com sucesso!"));

    }

    public void setSelUsuario(Usuario selusuario) {
        this.selusuario = selusuario;
    }

    public List<Usuario> readAllUsuario() {

        return ManagerDao.getCurrentInstance()
                .read("select u from Usuario u", Usuario.class);
    }

    public List<RendaFixa> lerTodasRendasFixasPorUsuario(int id) {

        return ManagerDao.getCurrentInstance().
                read("select r from RendaFixa r where r.usuario.id = " + id
                        + " order by r.id desc",
                        RendaFixa.class);

    }

    public List<RendaVariavel> lerTodasRendasVariaveisPorUsuario(int id) {
        return ManagerDao.getCurrentInstance().read("select r from RendaVariavel r where r.usuario.id = " + id + " order by r.id desc", RendaVariavel.class);
    }

    public Double getValorTotalRendasFixasPorUsuario(int id) {
        Double valorTotalFixa = 0.0;

        List<RendaFixa> rendasFixas = lerTodasRendasFixasPorUsuario(id);
        for (RendaFixa rf : rendasFixas) {
            valorTotalFixa += rf.getValorTotalAtual();
        }

        return valorTotalFixa;

    }

    public Double getValorTotalRendasFixasPorUsuarioCompra(int id) {
        Double valorTotalFixaCompra = 0.0;

        List<RendaFixa> rendasFixas = lerTodasRendasFixasPorUsuario(id);
        for (RendaFixa rf : rendasFixas) {
            valorTotalFixaCompra += rf.getValorTotalCompra();
        }

        return valorTotalFixaCompra;

    }

    public Double getValorTotalRendasVariaveisPorUsuario(int id) {

        Double valorTotalVariavel = 0.0;

        List<RendaVariavel> rendasVariaveis = lerTodasRendasVariaveisPorUsuario(id);
        for (RendaVariavel rv : rendasVariaveis) {
            valorTotalVariavel += rv.getValorCompra();
        }

        return valorTotalVariavel;
    }

    public Double getValorTotalRendasVariaveisPorUsuarioCompra(int id) {

        Double valorTotalVariavelCompra = 0.0;

        List<RendaVariavel> rendasVariaveis = lerTodasRendasVariaveisPorUsuario(id);
        for (RendaVariavel rv : rendasVariaveis) {
            valorTotalVariavelCompra += rv.getValorCompra();
        }

        return valorTotalVariavelCompra;
    }

    public static String sha512(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            byte[] encodedHash = digest.digest(input.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedHash) {
                String hex = String.format("%02x", b);
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            // Tratar exceção caso o algoritmo SHA-512 não esteja disponível
            e.printStackTrace();
            return null;
        }
    }
    
    public static boolean validatePassword(String password) {
        return Pattern.matches(PASSWORD_REGEX, password);
    }

}
