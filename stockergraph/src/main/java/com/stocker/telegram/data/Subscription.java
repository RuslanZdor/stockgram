package com.stocker.telegram.data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@NamedNativeQueries({
        @NamedNativeQuery(name = "get_subscription_by_chatid", query = "select * from EMPLOYEE where chatId=:chatId"),
})

@Entity
@Table(name = "subscription", schema = "stocker")
public class Subscription {
    @Id
    @Column(name="subscriptionId")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int subscriptionId;

    @Column(nullable = false)
    private long chatId;
    @Column(nullable = false)
    private Date joinDate;
    @Column(nullable = false)
    private Date lastMessageDate;

    @OneToMany(mappedBy = "subscription")
    private List<StockFollow> stockFollows;

    public int getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(int subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public Date getLastMessageDate() {
        return lastMessageDate;
    }

    public void setLastMessageDate(Date lastMessageDate) {
        this.lastMessageDate = lastMessageDate;
    }

    public List<StockFollow> getStockFollows() {
        return stockFollows;
    }

    public void setStockFollows(List<StockFollow> stockFollows) {
        this.stockFollows = stockFollows;
    }
}
