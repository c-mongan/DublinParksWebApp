package com.example.dublinparkswebapp.park;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ParkRepository extends CrudRepository<Park,Integer> {
    public Long countById(Integer id);

    @Query("SELECT p from Park p WHERE p.Council_Name LIKE %?1%")
    public List<Park> findAllFingalParks(String councilName);


//    @Query("SELECT p from Park p WHERE p.Council_Name LIKE %?1%")
//    public List<Park> findAllDublinCityParks(String councilName);
//
//
//    @Query("SELECT p from Park p WHERE p.Council_Name LIKE %?1%")
//    public List<Park> findAllDLR(String councilName);


}
