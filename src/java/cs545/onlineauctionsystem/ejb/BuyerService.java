/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cs545.onlineauctionsystem.ejb;

import cs545.onlineauctionsystem.entity.Buyer;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author weldu
 */
@Stateless
public class BuyerService {
  
    @PersistenceContext
    private EntityManager em;
    
    public boolean SaveBuyer(Buyer buyer){
        boolean saved =false;
        try {
            em.persist(buyer);
            saved= true;
        } catch (Exception e) {
        }
        
        return saved;
    }
    
    /*
     * 
      Check for authontication(registered or not)
    */
    public Buyer authenticateBuyer(Buyer buyer) {

        String username = buyer.getUserName();
        String passw = buyer.getUserPassword();

        Query query = em.createNamedQuery("checkBuyer");
        query.setParameter("uname", username);
        query.setParameter("upass", passw);

        if (!query.getResultList().isEmpty()) {
            Buyer buyerAuthenticated = (Buyer) query.getSingleResult();
            return buyerAuthenticated;
        }
        return null;
    }
    
    
    /**
     * Find Buyers by id
     */
    public Buyer findById(int id) {
        return em.find(Buyer.class, id);
    }
    
    
    /**
     * Find Buyer by userName
     */
    public Buyer findBYuserNameId(String username) {
        Query query = em.createNamedQuery("findBuyerByuserName");
        query.setParameter("uname", username);
        if (!query.getResultList().isEmpty()) {
            Buyer registeredBuyer=(Buyer) query.getSingleResult();
            return registeredBuyer;
        }
        return null;
    }
    
}
