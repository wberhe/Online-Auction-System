/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs545.onlineauctionsystem.mb;

import cs545.onlineauctionsystem.ejb.sellerService;
import cs545.onlineauctionsystem.entity.Seller;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Map;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author kokob
 */
@Named(value = "sellerBean")
@SessionScoped
public class SellerBean implements Serializable {

    @EJB
    private sellerService sellerService;

    private Seller seller;
    private boolean isLoggedIn;
    private String requestedUrl;

    public SellerBean() {
        seller = new Seller();
        isLoggedIn = false;
    }

    public boolean isIsLoggedIn() {
        return isLoggedIn;
    }

    public void setIsLoggedIn(boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }

    public String getRequestedUrl() {
        return requestedUrl;
    }

    public void setRequestedUrl(String requestedUrl) {
        this.requestedUrl = requestedUrl;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    /**
     * register sellers
     *
     * @return
     */
    public String registerSeller() {
        seller = new Seller();
        return "signUp";
    }

    /**
     * if he is logged in continue to the requested URL
     *
     * @return
     */
    public String continueToRequestedUrl() {

        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
        requestedUrl = params.get("url");

        if (isLoggedIn) {
            return requestedUrl;
        }
        
        return "loginSeller";
    }

    public String doLogin() throws Exception {
        Seller checkSeller = sellerService.authenticateSeller(seller);
        if (requestedUrl == null) {
            requestedUrl = "home";
        }

        if (checkSeller != null) {
            seller=checkSeller;
            isLoggedIn = true;
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("__seller",seller);
            return requestedUrl;
        } else {
            // SHOULD SHOW SOME ERROR MSG ONE LOGIN PAGE
            return "logInFail";
        }
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

    public String addAuction() {
        sellerService.saveinfo(seller);
        return null;
    }

    /**
     * Add buyer to Buyer table
     *
     * @return 
     * @throws java.lang.Exception
     */
    public String addSeller() throws Exception {
        if (sellerService.saveinfo(seller)) {
            return "login_seller";
        }

        return "error";
    }
}