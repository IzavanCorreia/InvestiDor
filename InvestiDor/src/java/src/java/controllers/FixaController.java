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
import javax.servlet.http.HttpSession;
import src.java.model.dao.ManagerDao;
import src.java.model.negocio.RendaFixa;
import src.java.model.negocio.Usuario;

/**
 *
 * @author Izavan
 */
@ManagedBean
@SessionScoped
public class FixaController {

    private RendaFixa rendafixa;
    private RendaFixa rendafixasel;

    @PostConstruct
    public void init() {
        this.rendafixa = new RendaFixa();
        this.rendafixasel = null;
    }

    public void cadastrar() {

        Usuario usuario = ((LoginController) ((HttpSession) (FacesContext.
                getCurrentInstance().getExternalContext().getSession(true))).
                getAttribute("loginController")).getUsuarioLogado();

        rendafixa.setUsuario(usuario);
        rendafixa.setDataInicialString();
        rendafixa.setDataFinalString();
        rendafixa.setValorTotalCompra();
        rendafixa.setValorTotalAtual();

        ManagerDao.getCurrentInstance().insert(rendafixa);

        this.rendafixa = new RendaFixa();

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Investimento inserido com sucesso"));
    }

    public void deletar() {
        ManagerDao.getCurrentInstance().delete(this.rendafixasel);

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Renda fixa deletada com sucesso!"));

    }

    public void clearSelection() {
        this.rendafixasel = null;

    }

    public RendaFixa getRendafixasel() {
        return rendafixasel;
    }

    public void setRendafixasel(RendaFixa rendafixasel) {
        this.rendafixasel = rendafixasel;
    }

    public RendaFixa getRendafixa() {
        return rendafixa;
    }

    public void setRendafixa(RendaFixa rendafixa) {
        this.rendafixa = rendafixa;
    }

    public void alterar() {

        this.rendafixasel.setDataInicialString();
        this.rendafixasel.setDataFinalString();
        this.rendafixasel.setValorTotalCompra();
        this.rendafixasel.setValorTotalAtual();

        ManagerDao.getCurrentInstance().update(this.rendafixasel);

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Renda fixa atualizada com sucesso!"));

    }

    public List<RendaFixa> readAllRendaFixa() {

        return ManagerDao.getCurrentInstance()
                .read("select r from RendaFixa r", RendaFixa.class);
    }

}
