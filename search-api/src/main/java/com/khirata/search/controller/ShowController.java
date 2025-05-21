package com.khirata.search.controller;

import com.khirata.search.domain.Show;
import com.khirata.search.service.ShowService;
import com.khirata.search.web.PagedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shows")
public class ShowController {

    @Autowired
    private ShowService service;

    @GetMapping("/{id}")
    Show findById(@PathVariable("id") String id) {
        return service.findById(id);
    }

    @PostMapping
    Show save(@RequestBody Show show) {
        return service.save(show);
    }

    @DeleteMapping("{id}")
    void delete(@PathVariable("id") String id) {
        service.deleteById(id);
    }

    @GetMapping
    HttpEntity<PagedResponse<Show>> findByTitle(@RequestParam("title") String title,
                           @RequestParam(value = "page", defaultValue = "0") int page,
                           @RequestParam(value = "size", defaultValue = "30") int size) {
        PagedResponse<Show> shows = service.findByTitle(title, page, size);
        return ResponseEntity.ok(shows);
    }

    @GetMapping("/similar")
    HttpEntity<PagedResponse<Show>> searchSimilar(@RequestParam(value= "id") String id,
                                                @RequestParam(value = "page", defaultValue = "0") int page,
                                                @RequestParam(value = "size", defaultValue = "30") int size) {

        PagedResponse<Show> shows = service.searchSimilar(id, page, size);
        return ResponseEntity.ok(shows);
    }
}
