/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs545.onlineauctionsystem.mb;

import cs545.onlineauctionsystem.ejb.BuyerService;
import cs545.onlineauctionsystem.entity.Buyer;
import java.io.Serializable;
import java.util.Map;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author Weldu
 */
@Named(value = "buyerBean")
@SessionScoped
public class BuyerBean implements Serializable {
    @EJB
    private BuyerService buyerService;

    public String getTest()  {
        return "bla bla bla";
    }

    private Buyer buyer;
    private boolean isLoggedIn;
    private boolean selected = true;

    private String requestedUrl;

    public BuyerBean() {
        isLoggedIn = false;
        buyer = new Buyer();
    }

    public boolean isIsLoggedIn() {
        return isLoggedIn;
    }

    public void setIsLoggedIn(boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

    public boolean getSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getRequestedUrl() {
        return requestedUrl;
    }

    public void setRequestedUrl(String requestedUrl) {
        this.requestedUrl = requestedUrl;
    }

    /**
     * register buyers
     *
     * @return
     */
    public String registerBuyer() {
        buyer = new Buyer();
        return "signUp";
    }

    /**
     * check user for login
     *
     * @return
     * @throws Exception
     */
    public String doLogin() throws Exception {
        Buyer checkBuyer = buyerService.authenticateBuyer(buyer);

        if (requestedUrl == null) {
            requestedUrl = "home";
        }

        if (checkBuyer != null) {
            buyer=checkBuyer;
            isLoggedIn = true;
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("__buyer",buyer);
            return requestedUrl;
        } else {
            // SHOULD SHOW SOME ERROR MSG ONE LOGIN PAGE
            return "logInFail";
        }
    }

    /**
     * Add buyer to Buyer table
     *
     * @return 
     * @throws java.lang.Exception
     */
    public String addBuyer() throws Exception {
        if (buyerService.SaveBuyer(buyer)) {
            return "login_buyer";
        }
        return null;
    }

    /**
     * logout
     * @return 
     */
    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        isLoggedIn = false;
        return "logout";
    }

    public String checkLogin() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params
                = fc.getExternalContext().getRequestParameterMap();
        requestedUrl = params.get("url");
        if (isLoggedIn) {
            return requestedUrl;
        } else {
            FacesMessage msg=new FacesMessage("You must be logged in to do this action.");
            FacesContext.getCurrentInstance().addMessage("position1", msg);
            return "login_buyer";
        }
    }

    public void check_selection() {
        int i = 0;
        System.out.println(++i);
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params
                = fc.getExternalContext().getRequestParameterMap();
        String selection = params.get("selection");

        this.selected = selection.equals("buyer");
    }
}