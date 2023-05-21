/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.java.controllers;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import src.java.model.dao.ManagerDao;
import src.java.model.negocio.Usuario;

/**
 *
 * @author Izavan
 */
@ManagedBean
@SessionScoped
public class LoginController {

    private String tipoLogado;

    private Usuario usuarioLogado;

    @PostConstruct
    public void init() {
        this.tipoLogado = "";
        this.usuarioLogado = null;
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
