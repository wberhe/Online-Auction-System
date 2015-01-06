/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs545.onlineauctionsystem.mb;

import cs545.onlineauctionsystem.ejb.AuctionService;
import cs545.onlineauctionsystem.ejb.BidService;
import cs545.onlineauctionsystem.ejb.BuyerService;
import cs545.onlineauctionsystem.entity.Auction;
import cs545.onlineauctionsystem.entity.Bid;
import cs545.onlineauctionsystem.entity.Buyer;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Faruque
 */
@Named(value = "bidBean")
@SessionScoped
public class BidBean implements Serializable {

    @EJB
    private BidService bidService;
    private Bid bid;

    @EJB
    private AuctionService auctionService;
    private Auction auction;

    @EJB
    private BuyerService buyerService;
    private Buyer buyer;

    @PostConstruct
    public void init() {
        HttpSession activeSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        buyer = (Buyer) activeSession.getAttribute("isLoggedIn");
    }

    public BidBean() {
        bid = new Bid();
        auction = new Auction();
        buyer = new Buyer();
    }

    public Bid getBid() {
        return bid;
    }

    public void setBid(Bid bid) {
        this.bid = bid;
    }

    public Auction getAuction() {
        return auction;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

    /**
     * adding what
     *
     * @param auction
     * @return 
     */
    public String addToBid(Auction auction) {
        try {
            buyer=(Buyer)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("__buyer");

            if(buyer ==null)  {
                return "error";
            }

            this.auction = auction;
            bid.setAuction(auction);
            bid.setBuyer(buyer);
            bidService.save(bid);
            return "bid_confirmation";
        } catch (Exception e) {
        }
        return null;
    }

}
