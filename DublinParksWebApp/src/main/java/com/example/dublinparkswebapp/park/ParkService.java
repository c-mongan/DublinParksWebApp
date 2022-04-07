package com.example.dublinparkswebapp.park;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParkService {
    @Autowired private ParkRepository parkRepository;

    public List<Park> listAll() {
        return (List<Park>) parkRepository.findAll();

    }


    public List<Park> findAllFingal(String councilName) {

        if (councilName != null){
            return (List<Park>) parkRepository.findAllFingalParks(councilName);
    }
     return (List<Park>) parkRepository.findAll();

    }



    public List<Park> findAllDublinCity(String councilName) {

        if (councilName != null){
            return (List<Park>) parkRepository.findAllFingalParks(councilName);
        }
        return (List<Park>) parkRepository.findAll();

    }



    public List<Park> findAllDLR(String councilName) {

        if (councilName != null){
            return (List<Park>) parkRepository.findAllFingalParks(councilName);
        }
        return (List<Park>) parkRepository.findAll();

    }

    public void save(Park park){
        parkRepository.save(park);


    }

    public Park get(Integer id) throws ParkNotFoundException {
        Optional<Park> result = parkRepository.findById(id);
        if(result.isPresent()) {
            return result.get();
        }
        throw new ParkNotFoundException("Could not find any parks with the ID " + id);
    }




    public void delete(Integer id) throws ParkNotFoundException {
        Long count = parkRepository.countById(id);

        if (count == null || count == 0) {
            throw new ParkNotFoundException("Could not find any parks with ID" + id);
        }
        parkRepository.deleteById(id);
    }
}


