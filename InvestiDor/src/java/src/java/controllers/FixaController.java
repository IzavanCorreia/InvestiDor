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
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Usuario usuario = ((LoginController) ((HttpSession) (FacesContext.
                getCurrentInstance().getExternalContext().getSession(true))).
                getAttribute("loginController")).getUsuarioLogado();

        rendafixa.setUsuario(usuario);
        rendafixa.setDataInicialString();
        rendafixa.setDataFinalString();
        rendafixa.setValorTotalCompra();
        rendafixa.setValorTotalAtual();

        Set<ConstraintViolation<RendaFixa>> violations = validator.validate(rendafixa);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<RendaFixa> violation : violations) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, violation.getMessage(), null);
                if (!FacesContext.getCurrentInstance().getMessageList().contains(message)) {
                    FacesContext.getCurrentInstance().addMessage(null, message);
                }
            }
            return;
        }

        ManagerDao.getCurrentInstance().insert(rendafixa);

        this.rendafixa = new RendaFixa();

        FacesMessage successMessage = new FacesMessage("Investimento inserido com sucesso");
        if (!FacesContext.getCurrentInstance().getMessageList().contains(successMessage)) {
            FacesContext.getCurrentInstance().addMessage(null, successMessage);
        }
    }

    public void deletar() {
        if (rendafixasel == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nenhum item selecionado para deletar!", null));
            return;
        }

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
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        rendafixasel.setDataInicialString();
        rendafixasel.setDataFinalString();
        rendafixasel.setValorTotalCompra();
        rendafixasel.setValorTotalAtual();

        Set<ConstraintViolation<RendaFixa>> violations = validator.validate(rendafixasel);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<RendaFixa> violation : violations) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, violation.getMessage(), null);
                if (!FacesContext.getCurrentInstance().getMessageList().contains(message)) {
                    FacesContext.getCurrentInstance().addMessage(null, message);
                }
            }
            return;
        }

        ManagerDao.getCurrentInstance().update(rendafixasel);

        FacesMessage successMessage = new FacesMessage("Renda fixa atualizada com sucesso!");
        if (!FacesContext.getCurrentInstance().getMessageList().contains(successMessage)) {
            FacesContext.getCurrentInstance().addMessage(null, successMessage);
        }
    }

    public List<RendaFixa> readAllRendaFixa() {

        return ManagerDao.getCurrentInstance()
                .read("select r from RendaFixa r", RendaFixa.class);
    }

}
