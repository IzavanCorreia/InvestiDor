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
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import static javax.servlet.RequestDispatcher.ERROR_MESSAGE;
import javax.validation.ConstraintViolation;
import src.java.model.dao.ManagerDao;
import src.java.model.negocio.Usuario;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import static src.java.controllers.UsuarioController.sha512;
import static src.java.controllers.UsuarioController.validatePassword;

/**
 *
 * @author Izavan
 */
@ManagedBean
@SessionScoped
public class LoginController {

    private String tipoLogado;

    @Valid
    private Usuario usuarioLogado;
    private Validator validator;
    
    private static final String PASSWORD_REGEX = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[$&@#])[0-9a-zA-Z$&@#]{8,}$";
    private static final String ERROR_MESSAGE = "A senha deve conter pelo menos um dígito, uma letra minúscula, uma letra maiúscula, "
            + "um dos caracteres especiais ($, *, &, @, #) e ter no mínimo 8 caracteres.";

    @PostConstruct
    public void init() {
        this.tipoLogado = "";
        this.usuarioLogado = null;
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    public String logar(String login, String senha) {
        String senhaQueVaiProBanco = sha512(senha);
        String query = "select u from Usuario u where u.email = :email and u.senha = :senha";
        List<Usuario> usuarios = ManagerDao.getCurrentInstance().read(query, Usuario.class, "email", login, "senha", senhaQueVaiProBanco);

        if (!usuarios.isEmpty()) {
            this.usuarioLogado = usuarios.get(0);
            this.tipoLogado = "usuario";
            return "indexUsuario";
        }

        FacesContext.getCurrentInstance()
                .addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_ERROR, "Erro ao logar",
                        "Login ou Senha não conferem"));

        return null;

    }

    public void logout() {

        this.usuarioLogado = null;
        this.tipoLogado = "";

    }

    public String getTipoLogado() {
        return tipoLogado;
    }

    public void setTipoLogado(String tipoLogado) {
        this.tipoLogado = tipoLogado;
    }

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public void alterarSenha(String senhaAtual, String senhaNova, String confirma) {
        
        System.out.println(senhaAtual);
        String confirmaSenhaAtual = sha512(senhaAtual);
        String senhaQueVaiProBanco = sha512(senhaNova);
        String confirmaSenhaNova = sha512(confirma);
        
        System.out.println(confirmaSenhaAtual);
        System.out.println(this.usuarioLogado.getSenha());
        
        if (!confirmaSenhaAtual.equals(this.usuarioLogado.getSenha())) {

            FacesContext.getCurrentInstance()
                    .addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    "Erro!", "A senha informada não é sua senha atual"));

            return;

        }
        
         if (!confirmaSenhaNova.equals(senhaQueVaiProBanco)) {

            FacesContext.getCurrentInstance()
                    .addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    "Erro!", "A senha não bate com a confirmação"));

            return;

        }
         
          if (!validatePassword(confirma)) {
            FacesContext.getCurrentInstance()
                    .addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    "Erro!", ERROR_MESSAGE));

            return;
        }
         

        List<String> mensagens = new ArrayList<>();
        if (!validator.validate(this.usuarioLogado).isEmpty()) {
            for (ConstraintViolation<Usuario> violation : validator.validate(this.usuarioLogado)) {
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
        
        this.usuarioLogado.setSenha(senhaQueVaiProBanco);

        ManagerDao.getCurrentInstance().update(this.usuarioLogado);

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Sua senha foi alterada com sucesso!"));

    }
    
     public void alterarPerfil() {    

        List<String> mensagens = new ArrayList<>();
        if (!validator.validate(this.usuarioLogado).isEmpty()) {
            for (ConstraintViolation<Usuario> violation : validator.validate(this.usuarioLogado)) {
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

        ManagerDao.getCurrentInstance().update(this.usuarioLogado);

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Seu perfil foi editado com sucesso!"));

    }

    public String deletar() {
        ManagerDao.getCurrentInstance().delete(this.usuarioLogado);

        FacesContext facesContext = FacesContext.getCurrentInstance();
        Flash flash = facesContext.getExternalContext().getFlash();
        flash.setKeepMessages(true);
        facesContext.addMessage(null, new FacesMessage("Sua conta foi apagada com sucesso!"));

        ExternalContext externalContext = facesContext.getExternalContext();
        externalContext.invalidateSession(); 

        return "index.xhtml?faces-redirect=true";
    }
    
     public static boolean validatePassword(String password) {
        return Pattern.matches(PASSWORD_REGEX, password);
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
}
