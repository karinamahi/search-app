package com.khirata.search.helper;

import com.khirata.search.domain.Show;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class LoadDataHelper {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH);

    public static List<Show> loadFromCSV(String filename) throws CsvValidationException, IOException {
        List<Show> result = new ArrayList<>();

        CSVReader reader = new CSVReader(new FileReader(filename));
        reader.readNext(); // skip header
        String[] row;

        while ((row = reader.readNext()) != null) {
            Show show = new Show();
            show.setId(row[0]);
            show.setType(row[1]);
            show.setTitle(row[2]);
            show.setDirectors(toList(row[3]));
            show.setCast(toList(row[4]));
            show.setCountry(toList(row[5]));
            show.setDateAdded(parseDate(row[6]));
            show.setReleaseYear(parseInt(row[7]));
            show.setRating(row[8]);
            show.setDuration(row[9]);
            show.setCategories(toList(row[10]));
            show.setDescription(row[11]);
            result.add(show);
        }

        return result;
    }

    private static List<String> toList(String input) {
        if (input == null || input.trim().isEmpty()) return Collections.emptyList();
        return Arrays.stream(input.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();
    }

    private static LocalDate parseDate(String date) {
        try {
            return date != null && !date.trim().isEmpty() ? LocalDate.parse(date.trim(), DATE_FORMATTER) : null;
        } catch (Exception e) {
            return null;
        }
    }

    private static int parseInt(String number) {
        try {
            return Integer.parseInt(number.trim());
        } catch (Exception e) {
            return 0;
        }
    }
}
