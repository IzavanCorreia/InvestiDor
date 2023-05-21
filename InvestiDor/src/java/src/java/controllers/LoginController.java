/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.java.controllers;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
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

        String query = "select u from Usuario u where u.email = :email and u.senha = :senha";
        List<Usuario> usuarios = ManagerDao.getCurrentInstance().read(query, Usuario.class, "email", login, "senha", senha);

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

}
