package com.khirata.search.service;

import com.khirata.search.domain.Show;
import com.khirata.search.repository.ShowRepository;
import com.khirata.search.web.PagedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    public PagedResponse<Show> findByTitle(String title, int pageNumber, int pageSize) {
        Page<Show> shows = repository.findByTitle(title, PageRequest.of(pageNumber, pageSize));
        return getPagedResponse(shows);
    }

    public PagedResponse<Show> searchSimilar(String id, int pageNumber, int pageSize) {
        Show show = findById(id);
        String[] fields = {"categories"};
        Page<Show> shows = repository.searchSimilar(show, fields, PageRequest.of(pageNumber, pageSize));
        return getPagedResponse(shows);
    }

    private PagedResponse<Show> getPagedResponse(Page<Show> shows) {
        PagedResponse<Show> response = new PagedResponse<>();
        response.setContent(shows.getContent());
        response.setPage(shows.getNumber());
        response.setSize(shows.getSize());
        response.setTotalItems(shows.getTotalElements());
        response.setTotalPages(shows.getTotalPages());
        return response;
    }
}
