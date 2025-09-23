package com.khirata.search.web;

import com.khirata.search.domain.Show;
import org.springframework.data.elasticsearch.core.document.Explanation;

import java.util.List;
import java.util.Map;

public record ShowResponse (
        Show show,
        float score,
        Map<String, List<String>> highlightFields,
        Explanation explanation)
{}
