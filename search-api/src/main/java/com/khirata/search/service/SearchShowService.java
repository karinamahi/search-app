package com.khirata.search.service;

import com.khirata.search.domain.Show;
import com.khirata.search.web.PagedResponse;
import com.khirata.search.web.ShowResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.core.query.HighlightQuery;
import org.springframework.data.elasticsearch.core.query.highlight.Highlight;
import org.springframework.data.elasticsearch.core.query.highlight.HighlightField;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchShowService {

    private final ElasticsearchOperations operations;

    public SearchShowService(ElasticsearchOperations operations) {
        this.operations = operations;
    }

    public PagedResponse<ShowResponse> search(String userQuery, int page, int size) {

        PageRequest pageable = PageRequest.of(page, size);

        Highlight highlight = new Highlight(List.of(new HighlightField("title")));
        HighlightQuery highlightQuery = new HighlightQuery(highlight, Show.class);

        NativeQuery query = NativeQuery.builder()
                .withQuery(q -> q
                        .match(m -> m.field("title").query(userQuery)))
                .withExplain(true)
                .withPageable(pageable)
                .withHighlightQuery(highlightQuery)
                .build();

        SearchHits<Show> hits = operations.search(query, Show.class);
        SearchPage<Show> searchPage = SearchHitSupport.searchPageFor(hits, pageable);
        return getPagedResponse(searchPage);
    }

    private PagedResponse<ShowResponse> getPagedResponse(SearchPage<Show> searchPage) {

        List<ShowResponse> responses = searchPage.getSearchHits().stream()
                                            .map(hit -> new ShowResponse(hit.getContent(), hit.getScore(), hit.getHighlightFields(), hit.getExplanation()))
                                            .toList();
        PagedResponse<ShowResponse> pagedResponse = new PagedResponse<>();
        pagedResponse.setTotalPages(searchPage.getTotalPages());
        pagedResponse.setTotalItems(searchPage.getTotalElements());
        pagedResponse.setSize(searchPage.getSize());
        pagedResponse.setPage(searchPage.getNumber());
        pagedResponse.setContent(responses);
        return pagedResponse;
    }
}
