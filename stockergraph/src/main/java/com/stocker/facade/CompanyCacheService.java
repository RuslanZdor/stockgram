package com.stocker.facade;

import com.stocker.data.sql.SQLCompanyDAOImpl;
import com.stocker.exception.NoCompanyException;
import com.stocker.symbol.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@Service
public class CompanyCacheService {

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private SQLCompanyDAOImpl sqlCompanyDAO;

    private String COMPANY_CACHE = "company";

    @Transactional
    public Company read(String symbol) throws NoCompanyException {
        if (cacheManager.getCache(COMPANY_CACHE).get(symbol) != null) {
            return (Company) cacheManager.getCache(COMPANY_CACHE).get(symbol).get();
        }
        Company company = sqlCompanyDAO.read(symbol);
        cacheManager.getCache(COMPANY_CACHE).put(symbol, company);
        return company;
    }

    @Transactional
    public void save(Company company) {
        sqlCompanyDAO.save(company);
        cacheManager.getCache(COMPANY_CACHE).put(company.getSymbol(), company);
    }

    @Transactional
    public List<Company> allCompanies() {
        return sqlCompanyDAO.readAll();
    }
}
