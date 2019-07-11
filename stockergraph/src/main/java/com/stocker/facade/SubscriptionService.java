package com.stocker.facade;

import com.stocker.dao.SQLSubscriptionDAOImpl;
import com.stocker.telegram.data.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class SubscriptionService {

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private SQLSubscriptionDAOImpl sqlSubscriptionDAO;

    private String CACHE = "subscription";

    @Transactional
    public void save(Subscription subscription) {
        sqlSubscriptionDAO.save(subscription);
    }

    @Transactional
    public void read(long chatId) {
        sqlSubscriptionDAO.read(chatId);
    }
}
