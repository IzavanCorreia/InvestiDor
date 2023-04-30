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
import src.java.model.dao.ManagerDao;
import src.java.model.negocio.Usuario;

/**
 *
 * @author Izavan
 */
@ManagedBean
@SessionScoped
public class UsuarioController {

    private Usuario usuario;
    private Usuario selusuario;

    @PostConstruct
    public void init() {
        this.usuario = new Usuario();
        this.selusuario = null;
    }

    public void inserir(String confirma) {

        if (!confirma.equals(this.usuario.getSenha())) {

            FacesContext.getCurrentInstance()
                    .addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    "Erro!", "A senha não bate com a confirmação"));

            return;

        }

        ManagerDao.getCurrentInstance().insert(this.usuario);

        this.usuario = new Usuario();

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Usuario cadastrado com sucesso!"));

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

}
