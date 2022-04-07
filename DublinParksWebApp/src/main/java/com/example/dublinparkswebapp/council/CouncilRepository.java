package com.example.dublinparkswebapp.council;
import org.springframework.data.repository.CrudRepository;

public interface CouncilRepository extends CrudRepository<Council,Integer> {
    public Long countById(Integer id);
}
