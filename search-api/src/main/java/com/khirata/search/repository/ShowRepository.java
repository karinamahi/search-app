package com.khirata.search.repository;

import com.khirata.search.domain.Show;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Optional;

public interface ShowRepository extends ElasticsearchRepository<Show, String> {

    Optional<Show> findById(String id);
    Show save(Show show);
    void deleteById(String id);
    Page<Show> findByTitle(String title, Pageable pageable);
    Page<Show> searchSimilar(Show show, String[] fields, Pageable pageable);
}
