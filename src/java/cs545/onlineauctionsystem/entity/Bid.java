package cs545.onlineauctionsystem.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
/**
 *
 * @author Faruque
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Bid.getHighest", query = "SELECT MAX(p.amount)  FROM Bid p"),
    @NamedQuery(name = "Bid.findByAuctionId", query = "select b from Bid b where b.auction.id = :aBid order by b.amount desc")
})
public class Bid implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Double amount;
//    private Date date; // making complicated hate this
    @ManyToOne
    private Buyer buyer;
    @ManyToOne
    @JoinColumn
    private Auction auction;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
    /*
     public Date getDate() {
     return date;
     }

     public void setDate(Date date) {
     this.date = date;
     }
     */

    public Buyer getBuyer() {
        return buyer;
    }

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

    public Auction getAuction() {
        return auction;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bid)) {
            return false;
        }
        Bid other = (Bid) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cs545.Waa.Group4.Entity.Bid[ id=" + id + " ]";
    }

}
