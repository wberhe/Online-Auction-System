/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs545.onlineauctionsystem.ejb;

import cs545.onlineauctionsystem.entity.Auction;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author weldu
 */
@Stateless
@LocalBean
public class AuctionService {

    @PersistenceContext
    private EntityManager em;

    public AuctionService() {

    }

    //find auction by ID

    public Auction get(int id) {
        return em.find(Auction.class, id);
    }

    /**
     * Saving an auction in to a auction data base
     *
     * @param auction
     * @return
     */
    public boolean save(Auction auction) {
        boolean saved = false;
        try {
            em.persist(auction);
            saved = true;
        } catch (Exception e) {
            //
        }
        return saved;
    }

    //delete Auction
    public void deleteAuction(int id) {
        Auction a = em.find(Auction.class, id);
        em.remove(a);
    }

    //get all auctions to display on home page
    public List<Auction> getAll() {
        TypedQuery<Auction> query = em.createNamedQuery("Auction.list", Auction.class);
        List<Auction> list = query.getResultList();
        return list;
    }

    //Search Auction by name
    public List<Auction> searchByAuctionName(String aname) {
        TypedQuery<Auction> a = em.createNamedQuery("findAuctionByName", Auction.class);
        String auctions = "%" + aname + "%";
        a.setParameter("aname", auctions);
        return a.getResultList();

    }

}
