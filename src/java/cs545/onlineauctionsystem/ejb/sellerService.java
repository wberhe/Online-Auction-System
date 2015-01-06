/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs545.onlineauctionsystem.ejb;

import cs545.onlineauctionsystem.entity.Seller;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Faruque
 */
@Stateless
public class sellerService {

    @PersistenceContext
    private EntityManager em;

    public boolean saveinfo(Seller seller) {
        boolean saved = false;
        try {
            em.persist(seller);
            saved = true;
        } catch (Exception e) {
            //
        }
        return saved;
    }

    /*
     * 
     Check for authontication(registered or not)
     */
    public Seller authenticateSeller(Seller seller) {

        String username = seller.getUserName();
        String passw = seller.getUserPassword();

        Query query = em.createNamedQuery("checkSeller");
        query.setParameter("uname", username);
        query.setParameter("upass", passw);

        if (!query.getResultList().isEmpty()) {
            Seller sellerAuthenticated = (Seller) query.getSingleResult();
            return sellerAuthenticated;
        }
        return null;
    }

    /**
     * Find Buyers by id
     */
    public Seller findById(int id) {
        return em.find(Seller.class, id);
    }

    /**
     * Find Buyer by userName
     */
    public Seller findBYuserNameId(String username) {
        Query query = em.createNamedQuery("findSellerByuserName");
        query.setParameter("uname", username);
        if (!query.getResultList().isEmpty()) {
            Seller registeredSeller = (Seller) query.getSingleResult();
            return registeredSeller;
        }
        return null;
    }

}
