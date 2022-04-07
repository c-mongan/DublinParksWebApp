package com.example.dublinparkswebapp.council;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CouncilService {
    @Autowired private CouncilRepository councilRepository;

    public List<Council> listAll() {
        return (List<Council>) councilRepository.findAll();

    }

    public void save(Council council){
        councilRepository.save(council);


    }

    public Council get(Integer id) throws CouncilNotFoundException {
        Optional<Council> result = councilRepository.findById(id);
        if(result.isPresent()) {
            return result.get();
        }
        throw new CouncilNotFoundException("Could not find any councils with the ID " + id);
    }

    public void delete(Integer id) throws CouncilNotFoundException {
        Long count = councilRepository.countById(id);

        if (count == null || count == 0) {
            throw new CouncilNotFoundException("Could not find any councils with ID" + id);
        }
        councilRepository.deleteById(id);
    }
}


