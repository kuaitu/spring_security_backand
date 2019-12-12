package com.lmk.springsecuritydemo.ws.service.impl;

import com.lmk.springsecuritydemo.ws.model.Country;
import com.lmk.springsecuritydemo.ws.model.Currency;
import com.lmk.springsecuritydemo.ws.service.CountryRepository;
import org.springframework.stereotype.Service;

@Service
public class CountryRepositoryImpl implements CountryRepository {
    @Override
    public Country findCountry(String name) {
        Country item = new Country();
        item.setName(name);
        item.setCapital("哈哈");
        item.setPopulation(100);
        item.setCurrency(Currency.EUR);
        return item;
    }
}
