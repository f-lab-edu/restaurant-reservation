package com.restaurantreservation.service;

import com.restaurantreservation.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

public class StoreServiceTest {

    @Autowired StoreRepository storeRepository;
    @Autowired EntityManager em;

}