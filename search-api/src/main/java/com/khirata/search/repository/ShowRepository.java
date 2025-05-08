package com.khirata.search.repository;

import com.khirata.search.domain.Show;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Optional;

public interface ShowRepository extends ElasticsearchRepository<Show, String> {

    Optional<Show> findById(String id);
    Show save(Show show);
    void deleteById(String id);

}
