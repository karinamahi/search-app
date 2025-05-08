package com.khirata.search.controller;

import com.khirata.search.domain.Show;
import com.khirata.search.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
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
}
