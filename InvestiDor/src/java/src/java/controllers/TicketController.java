package src.java.controllers;

import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import src.java.model.dao.ManagerDao;
import src.java.model.negocio.Ticket;

@ManagedBean
@SessionScoped
public class TicketController {

    private Ticket ticket;
    private Ticket selticket;
    private Validator validator;

    @PostConstruct
    public void init() {
        this.ticket = new Ticket();
        this.selticket = null;

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    public void inserir() {
        Set<ConstraintViolation<Ticket>> violations = validator.validate(ticket);
        if (violations.isEmpty()) {
            ManagerDao.getCurrentInstance().insert(this.ticket);
            this.ticket = new Ticket();

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Ticket cadastrado com sucesso!"));
        } else {
            for (ConstraintViolation<Ticket> violation : violations) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(violation.getMessage()));
            }
        }
    }

    public void clearSelection() {
        this.selticket = null;
    }

    public List<Ticket> readAllTicket() {
        return ManagerDao.getCurrentInstance()
                .read("select t from Ticket t", Ticket.class);
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public Ticket getSelTicket() {
        return selticket;
    }

    public void setSelTicket(Ticket selticket) {
        this.selticket = selticket;
    }

    public void alterar() {
        Set<ConstraintViolation<Ticket>> violations = validator.validate(selticket);
        if (violations.isEmpty()) {
            ManagerDao.getCurrentInstance().update(this.selticket);

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Tipo de ticket, alterado com sucesso!"));
        } else {
            for (ConstraintViolation<Ticket> violation : violations) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(violation.getMessage()));
            }
        }
    }

    public void deletar() {
        ManagerDao.getCurrentInstance().delete(this.selticket);

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Tipo de ticket, deletado com sucesso!"));
    }
}
