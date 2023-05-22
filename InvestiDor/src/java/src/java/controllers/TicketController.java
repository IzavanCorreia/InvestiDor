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
import src.java.model.negocio.Ticket;

/**
 *
 * @author Izavan
 */

@ManagedBean
@SessionScoped
public class TicketController {
    
    private Ticket ticket;
    private Ticket selticket;
    
    @PostConstruct
    public void init() {

        this.ticket = new Ticket();
        this.selticket = null;

    }

    public void inserir() {

        ManagerDao.getCurrentInstance().insert(this.ticket);

        this.ticket = new Ticket();

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Ticket cadastrado com sucesso!"));

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

        ManagerDao.getCurrentInstance().update(this.selticket);

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Tipo de ticket, alterado com sucesso!"));

    }

    public void deletar() {
        ManagerDao.getCurrentInstance().delete(this.selticket);

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Tipo de ticket, deletado com sucesso!"));
    }

    
}
