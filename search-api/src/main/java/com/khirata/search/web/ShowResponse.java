package com.khirata.search.web;

import com.khirata.search.domain.Show;
public record ShowResponse (Show show, float score) {}
