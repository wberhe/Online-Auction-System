/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs545.onlineauctionsystem.mb;

import cs545.onlineauctionsystem.ejb.AuctionService;
import cs545.onlineauctionsystem.ejb.BidService;
import cs545.onlineauctionsystem.entity.Auction;
import cs545.onlineauctionsystem.entity.Bid;
import cs545.onlineauctionsystem.entity.Seller;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
//import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.event.FileUploadEvent;

/**
 *
 * @author weldu
 */
@Named(value = "auctionBean")
@SessionScoped
public class AuctionBean implements Serializable {

    @EJB
    private AuctionService auctionservice;

    @EJB
    private BidService bidService;

    private Auction auction;
    private List<Auction> auctions;
    private String findNameKey;
    private List<Bid> bids;

    public AuctionBean() {
        auction = new Auction();
        auctions = new ArrayList<>();
//        List<Auction> result = auctionservice.getAll();
//        if (result != null) {
//            auctions = result;
//        }
    }

    public String findAuctionList() {
        auctions = auctionservice.getAll();
        return "/index.xhtml";
    }

    public Auction getAuction() {
        return auction;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }

    public List<Auction> getAuctions() {
        return auctions;
    }

    public void setAuctions(List<Auction> auctions) {
        this.auctions = auctions;
    }

    public String getFindNameKey() {
        return findNameKey;
    }

    public void setFindNameKey(String findNameKey) {
        this.findNameKey = findNameKey;
    }

    //add auction
    public String addAuction() {
        Seller seller = (Seller) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("__seller");
//        System.out.println(seller.getFirstName() + " <-- object is here");

        /*
         if possible. 
         1. Check seller "__seller" with instance_of 
         2  Check login again if null or something wrong
         3. ......
         */
        auction.setSeller(seller);
        auctionservice.save(auction);
        return "home";
    }

    //view auction detail
    public String viewAuctionDetail(Auction currentAuction) {
        auction = currentAuction;
        bids = bidService.findByAuctionId(currentAuction.getId());
        if (auction != null) {
            return "/jsfpages/auction_detail";
        } else {
            return null;
        }
    }

    //make a bid
    public String makeBid(Auction auction) {
        return "make_bid";
    }

    //Search Auction by name(name-as-key)
    public String searchAuction() {
        auctions = new ArrayList<>();
        List<Auction> result = auctionservice.searchByAuctionName(findNameKey);
        auctions = result;
        return "home";
    }

    //Search Auction by name(name-as-key)
    public String displayAll() {
        if (this.findNameKey != null) {
            auctions = new ArrayList<>();
            List<Auction> result = auctionservice.getAll();
            auctions = result;
        }
        return "home";
    }

    public List<Bid> getBids() {
        return bids;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }

    ////// image task
    private String id;
    int count;

    public String getId() {
        count++;
        return String.valueOf(count);
    }

    public void setId(String newValue) {
        id = newValue;
    }

    public void handleFileUpload(FileUploadEvent event) {

        try {

            String folderName = "c:\\etc";

            File targetFolder = new File(folderName);
            InputStream inputStream = event.getFile().getInputstream();
            OutputStream out = new FileOutputStream(new File(targetFolder,
                    event.getFile().getFileName()));
            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = inputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            inputStream.close();
            out.flush();
            out.close();

            //String fileName=folderName+"\\"+event.getFile().getFileName();
            //fileName=fileName.replaceAll('\',(char) 92);
            auction.setItemImage(folderName + "\\" + event.getFile().getFileName());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
