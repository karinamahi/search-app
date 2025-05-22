package com.khirata.search.helper;

import com.khirata.search.domain.Show;
import com.khirata.search.service.ShowService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class LoadShowsFromCSV {
	@Autowired
	private ShowService service;

	//@Test
	void test() throws Exception {
		Assertions.assertNotNull(service);
		List<Show> shows = LoadDataHelper.loadFromCSV("<your_filepath>/netflix_titles_copy.csv");
		shows.forEach(show -> {
			service.save(show);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		});
	}
}
