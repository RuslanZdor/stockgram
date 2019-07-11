package com.stocker.dao;

import com.stocker.telegram.data.Subscription;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Calendar;

@Repository
public class SQLSubscriptionDAOImpl {

    private static final Logger logger = LoggerFactory.getLogger(SQLSubscriptionDAOImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    public void save(Subscription subscription) {
        Session session = this.sessionFactory.getCurrentSession();
        session.saveOrUpdate(subscription);
    }

    public void add(Subscription subscription) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(subscription);
    }

    public Subscription read(long chatId) {
        Session session = this.sessionFactory.getCurrentSession();
        Subscription subscription = (Subscription) session.createNamedQuery("get_subscription_by_chatid")
                .setParameter("chatId", chatId)
                .getSingleResult();

        if (subscription == null) {
            subscription = new Subscription();
            subscription.setChatId(chatId);
            subscription.setJoinDate(Calendar.getInstance().getTime());
            subscription.setLastMessageDate(Calendar.getInstance().getTime());
            add(subscription);
        }
        return subscription;
    }
}
