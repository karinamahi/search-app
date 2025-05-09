package com.khirata.search.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDate;
import java.util.List;

@Document(indexName = "shows")
public class Show {

    @Id
    private String id;
    private String title;
    private String type;
    @Field(type = FieldType.Text)
    private List<String> directors;
    @Field(type = FieldType.Text)
    private List<String> cast;
    @Field(type = FieldType.Text)
    private List<String> country;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MMMM dd, yyyy")
    @Field(type = FieldType.Date)
    private LocalDate dateAdded;
    private Integer releaseYear;
    private String rating;
    private String duration;
    @Field(type = FieldType.Keyword)
    private List<String> categories;
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getDirectors() {
        return directors;
    }

    public void setDirectors(List<String> directors) {
        this.directors = directors;
    }

    public List<String> getCast() {
        return cast;
    }

    public void setCast(List<String> cast) {
        this.cast = cast;
    }

    public List<String> getCountry() {
        return country;
    }

    public void setCountry(List<String> country) {
        this.country = country;
    }

    public LocalDate getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(LocalDate dateAdded) {
        this.dateAdded = dateAdded;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    @Override
    public String toString() {
        return "Show{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", directors=" + directors +
                ", cast=" + cast +
                ", country=" + country +
                ", dateAdded=" + dateAdded +
                ", releaseYear=" + releaseYear +
                ", rating='" + rating + '\'' +
                ", duration='" + duration + '\'' +
                ", categories=" + categories +
                ", description='" + description + '\'' +
                '}';
    }
}
