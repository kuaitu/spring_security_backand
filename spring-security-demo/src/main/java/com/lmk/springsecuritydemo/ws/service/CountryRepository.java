package com.lmk.springsecuritydemo.ws.service;

import com.lmk.springsecuritydemo.ws.model.Country;

public interface CountryRepository {
    Country findCountry(String name);
}
