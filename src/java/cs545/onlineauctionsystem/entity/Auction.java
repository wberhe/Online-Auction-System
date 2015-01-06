/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs545.onlineauctionsystem.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;

/**
 *
 * @author weldu
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Auction.list", query = "SELECT a FROM Auction a"),
    @NamedQuery(name = "findAuctionByName", query = "SELECT a FROM Auction a WHERE a.name LIKE :aname")})
public class Auction implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    private String itemImage;
    private Double reservedPrice;
    
    @OneToMany(mappedBy = "auction",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<Bid> bestBid=new ArrayList<>();

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date startDate;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date endDate;
    @ManyToOne
    private Seller seller;

    public Auction()  {
        
    }

    public List<Bid> getBestBid() {
        return bestBid;
    }

    public void setBestBid(List<Bid> bestBid) {
        this.bestBid = bestBid;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public Double getReservedPrice() {
        return reservedPrice;
    }

    public void setReservedPrice(Double reservedPrice) {
        this.reservedPrice = reservedPrice;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return itemImage;
    }

    public void setImage(String image) {
        this.itemImage = image;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
