package com.login.user.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 

import org.springframework.stereotype.Repository;

import com.login.user.model.Country;
import com.login.user.model.Gender;
 
@Repository
public class CountryDAO {
 
    private static final Map<String, Country> COUNTRIES_MAP = new HashMap<>();
    private static final Map<String, String> GENDER_MAP = new HashMap<>();
    static {
        initDATA();
    }
 
    private static void initDATA() {
 
        Country vn = new Country("VN", "Vietnam");
        Country en = new Country("EN", "England");
        Country fr = new Country("FR", "France");
        Country us = new Country("US", "US");
        Country ru = new Country("RU", "Russia");
        Country in = new Country("IN", "India");
        Country ml = new Country("ML", "Maliasia");
        Country kr = new Country("KR", "Korea");
 
        COUNTRIES_MAP.put(vn.getCountryCode(), vn);
        COUNTRIES_MAP.put(en.getCountryCode(), en);
        COUNTRIES_MAP.put(fr.getCountryCode(), fr);
        COUNTRIES_MAP.put(us.getCountryCode(), us);
        COUNTRIES_MAP.put(ru.getCountryCode(), ru);
        COUNTRIES_MAP.put(in.getCountryCode(), in);
        COUNTRIES_MAP.put(ml.getCountryCode(), ml);
        COUNTRIES_MAP.put(kr.getCountryCode(), kr);
        
       
       
        
    }
 
    public Country findCountryByCode(String countryCode) {
        return COUNTRIES_MAP.get(countryCode);
    }
 
    public List<Country> getCountries() {
        List<Country> list = new ArrayList<>();
        list.addAll(COUNTRIES_MAP.values());
        return list;
    }
    
}