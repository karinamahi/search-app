package com.khirata.search.service;

import com.khirata.search.domain.Show;
import com.khirata.search.repository.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShowService {

    @Autowired
    private ShowRepository repository;

    public Show findById(String id) {
        return repository.findById(id).orElse(null);
    }

    public Show save(Show show) {
        return repository.save(show);
    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }
}
