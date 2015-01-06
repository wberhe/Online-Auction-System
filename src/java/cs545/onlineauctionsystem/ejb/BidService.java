/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs545.onlineauctionsystem.ejb;

import cs545.onlineauctionsystem.entity.Auction;
import cs545.onlineauctionsystem.entity.Bid;
import cs545.onlineauctionsystem.entity.Seller;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Faruque
 */
@Stateless
@LocalBean
public class BidService {
    
    @PersistenceContext
    private EntityManager em;
    
    public boolean save(Bid bid) {
        boolean saved = false;
        try {
            em.persist(bid);
            saved = true;
        } catch (Exception e) {
            //
        }
        return saved;
    }

    public Double getHighestBid()  {
//        TypedQuery query = em.createNamedQuery("Bid.getHighest", Bid.class);
        String var;
        var = (String)em.createQuery("select max(u.id) from Bid u").getSingleResult();
        System.out.println(var);
        return 0.0;
    }

    public List<Bid> findByAuctionId(Integer id) {
        TypedQuery<Bid> query = em.createNamedQuery("Bid.findByAuctionId", Bid.class).setParameter("aBid", id);
        List<Bid> list = query.getResultList();
        return list;
    }
}
