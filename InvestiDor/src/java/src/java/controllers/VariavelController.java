/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.java.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import src.java.model.dao.ManagerDao;
import src.java.model.negocio.RendaVariavel;
import src.java.model.negocio.Ticket;
import src.java.model.negocio.Usuario;

/**
 *
 * @author Izavan
 */
@ManagedBean
@SessionScoped
public class VariavelController {

    private RendaVariavel rendavariavel;
    private RendaVariavel rendavariavelsel;
    private Ticket ticket;
    private Set<Ticket> tickets;  // Lista de tickets disponíveis
    private Ticket ticketSelecionado;  // Ticket selecionado
    private UsuarioController usuarioController;

    @PostConstruct
    public void init() {
        this.rendavariavel = new RendaVariavel();
        this.rendavariavelsel = new RendaVariavel();
        this.tickets = new HashSet<>();

        // Recupere os tickets do banco de dados e adicione à lista
        List<Ticket> ticketsFromDatabase = ManagerDao.getCurrentInstance().read("SELECT t FROM Ticket t", Ticket.class);
        tickets.addAll(ticketsFromDatabase);

        usuarioController = new UsuarioController();
    }

    public void cadastrar() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Usuario usuario = ((LoginController) ((HttpSession) (FacesContext.
                getCurrentInstance().getExternalContext().getSession(true))).
                getAttribute("loginController")).getUsuarioLogado();

        rendavariavel.setUsuario(usuario);
        rendavariavel.setDataCompraFormatada();
        rendavariavel.setValorCompraTotal();
        rendavariavel.setValorAtualTotal();

        Set<ConstraintViolation<RendaVariavel>> violations = validator.validate(rendavariavel);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<RendaVariavel> violation : violations) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, violation.getMessage(), null);
                if (!FacesContext.getCurrentInstance().getMessageList().contains(message)) {
                    FacesContext.getCurrentInstance().addMessage(null, message);
                }
            }
            return;
        }

        ManagerDao.getCurrentInstance().insert(rendavariavel);

        usuarioController.getValorTotalRendasPorUsuario(usuario.getId());

        this.rendavariavel = new RendaVariavel();

        FacesMessage successMessage = new FacesMessage("Investimento inserido com sucesso");
        if (!FacesContext.getCurrentInstance().getMessageList().contains(successMessage)) {
            FacesContext.getCurrentInstance().addMessage(null, successMessage);
        }
    }

    public void deletar() {

        Usuario usuario = ((LoginController) ((HttpSession) (FacesContext.
                getCurrentInstance().getExternalContext().getSession(true))).
                getAttribute("loginController")).getUsuarioLogado();

        if (rendavariavelsel == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nenhum investimento selecionado para deletar!", null));
            return;
        }

        ManagerDao.getCurrentInstance().delete(this.rendavariavelsel);
        
        usuarioController.getValorTotalRendasPorUsuario(usuario.getId());

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Renda variavel deletada com sucesso!"));

    }

    public void clearSelection() {
        this.rendavariavelsel = null;

    }

    public RendaVariavel getRendavariavelsel() {
        return rendavariavelsel;
    }

    public void setRendavariavelsel(RendaVariavel rendavariavelsel) {
        this.rendavariavelsel = rendavariavelsel;
    }

    public RendaVariavel getRendavariavel() {
        return rendavariavel;
    }

    public void setRendavariavel(RendaVariavel rendavariavel) {
        this.rendavariavel = rendavariavel;
    }

    public void alterar() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Usuario usuario = ((LoginController) ((HttpSession) (FacesContext.
                getCurrentInstance().getExternalContext().getSession(true))).
                getAttribute("loginController")).getUsuarioLogado();

        rendavariavelsel.setDataCompraFormatada();
        rendavariavelsel.setValorCompraTotal();
        rendavariavelsel.setValorAtualTotal();

        ticketSelecionado = rendavariavelsel.getTicket();

        rendavariavel.setUsuario(usuario);

        Set<ConstraintViolation<RendaVariavel>> violations = validator.validate(rendavariavelsel);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<RendaVariavel> violation : violations) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, violation.getMessage(), null);
                if (!FacesContext.getCurrentInstance().getMessageList().contains(message)) {
                    FacesContext.getCurrentInstance().addMessage(null, message);
                }
            }
            return;
        }

        ManagerDao.getCurrentInstance().update(rendavariavelsel);

        usuarioController.getValorTotalRendasPorUsuario(usuario.getId());

        FacesMessage successMessage = new FacesMessage("Renda variavel atualizada com sucesso!");
        if (!FacesContext.getCurrentInstance().getMessageList().contains(successMessage)) {
            FacesContext.getCurrentInstance().addMessage(null, successMessage);
        }
    }

    public List<RendaVariavel> readAllRendaVariavel() {

        return ManagerDao.getCurrentInstance()
                .read("select r from RendaVariavel r", RendaVariavel.class);
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public List<Ticket> getTicketsDisponiveis() {
        List<Ticket> listaTickets = new ArrayList<>();

        for (Ticket ticket : tickets) {
            if (rendavariavelsel == null || !ticket.equals(rendavariavelsel.getTicket())) {
                listaTickets.add(ticket);
            }
        }

        if (rendavariavelsel != null && rendavariavelsel.getTicket() != null) {
            listaTickets.add(rendavariavelsel.getTicket());
        }

        return listaTickets;
    }

    public Set<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Set<Ticket> tickets) {
        this.tickets = tickets;
    }

    public Ticket getTicketSelecionado() {
        return ticketSelecionado;
    }

    public void setTicketSelecionado(Ticket ticketSelecionado) {
        this.ticketSelecionado = ticketSelecionado;
    }

}
