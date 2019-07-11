package com.stocker.telegram.data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "stock_follow", schema = "stocker")
public class StockFollow {

    @Id
    @Column(name="stockFollowId")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int stockFollowId;

    @JoinColumn(name="subscriptionId")
    @ManyToOne(cascade = CascadeType.ALL)
    private Subscription subscription;

    @Column(nullable = false)
    private String followSymbol;
    @Column(nullable = false)
    private Date startFollowDate;

    public int getStockFollowId() {
        return stockFollowId;
    }

    public void setStockFollowId(int stockFollowId) {
        this.stockFollowId = stockFollowId;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    public String getFollowSymbol() {
        return followSymbol;
    }

    public void setFollowSymbol(String followSymbol) {
        this.followSymbol = followSymbol;
    }

    public Date getStartFollowDate() {
        return startFollowDate;
    }

    public void setStartFollowDate(Date startFollowDate) {
        this.startFollowDate = startFollowDate;
    }
}
