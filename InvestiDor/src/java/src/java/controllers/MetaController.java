/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.java.controllers;

import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import src.java.model.dao.ManagerDao;
import src.java.model.negocio.Meta;
import src.java.model.negocio.RendaFixa;
import src.java.model.negocio.Ticket;
import src.java.model.negocio.Usuario;

/**
 *
 * @author Izavan
 */
@ManagedBean
@SessionScoped
public class MetaController {

    private Meta meta;
    private Meta metasel;
    private Validator validator;

    private UsuarioController usuarioController;

    @PostConstruct
    public void init() {
        this.meta = new Meta();
        this.metasel = null;

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();

        usuarioController = new UsuarioController();
    }

    public void cadastrar() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Usuario usuario = ((LoginController) ((HttpSession) (FacesContext.
                getCurrentInstance().getExternalContext().getSession(true))).
                getAttribute("loginController")).getUsuarioLogado();

        meta.setUsuario(usuario);

        Set<ConstraintViolation<Meta>> violations = validator.validate(meta);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<Meta> violation : violations) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, violation.getMessage(), null);
                if (!FacesContext.getCurrentInstance().getMessageList().contains(message)) {
                    FacesContext.getCurrentInstance().addMessage(null, message);
                }
            }
            return;
        }

        ManagerDao.getCurrentInstance().insert(meta);

        usuarioController.getValorTotalRendasPorUsuario(usuario.getId());

        this.meta = new Meta();

        FacesMessage successMessage = new FacesMessage("Investimento inserido com sucesso");
        if (!FacesContext.getCurrentInstance().getMessageList().contains(successMessage)) {
            FacesContext.getCurrentInstance().addMessage(null, successMessage);
        }
    }

    public void clearSelection() {
        this.metasel = null;
    }

    public List<Meta> readAllMeta() {
        return ManagerDao.getCurrentInstance()
                .read("select m from Meta m", Meta.class);
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public Meta getSelMeta() {
        return metasel;
    }

    public void setSelMeta(Meta metasel) {
        this.metasel = metasel;
    }

    public Meta getMetasel() {
        return metasel;
    }

    public void setMetasel(Meta metasel) {
        this.metasel = metasel;
    }

    public void alterar() {

        Usuario usuario = ((LoginController) ((HttpSession) (FacesContext.
                getCurrentInstance().getExternalContext().getSession(true))).
                getAttribute("loginController")).getUsuarioLogado();

        metasel.setUsuario(usuario);

        Set<ConstraintViolation<Meta>> violations = validator.validate(metasel);
        if (violations.isEmpty()) {
            ManagerDao.getCurrentInstance().update(this.metasel);
            usuarioController.getValorTotalRendasPorUsuario(usuario.getId());

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Meta alterada com sucesso!"));
        } else {
            for (ConstraintViolation<Meta> violation : violations) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(violation.getMessage()));
            }
        }
    }

    public void deletar() {

        Usuario usuario = ((LoginController) ((HttpSession) (FacesContext.
                getCurrentInstance().getExternalContext().getSession(true))).
                getAttribute("loginController")).getUsuarioLogado();

        ManagerDao.getCurrentInstance().delete(this.metasel);
        usuarioController.getValorTotalRendasPorUsuario(usuario.getId());

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("A meta foi apagada com sucesso!"));
    }

    public List<Meta> lerTodasMetasPorUsuario(int id) {

        return ManagerDao.getCurrentInstance().
                read("select m from Meta m where m.usuario.id = " + id
                        + " order by m.id desc",
                        Meta.class);
    }

}
