# Search API
Goal: Learn about Elasticsearch, Spring Data Elasticsearch, and about Search concepts.

## Tech List
- Java 21
- Gradle
- Elasticsearch 8.15.5
- Spring Data Elasticsearch 5.4.5

## Dataset
I will use the dataset of Netflix Movies and TV Shows available on [Kaggle](https://www.kaggle.com/datasets/rahulvyasm/netflix-movies-and-tv-shows?resource=download).

## Starting Elasticsearch Instance

For this project, I'm running Elasticsearch 8.15.5 using Docker/Docker Compose, and with security is disabled.
```shell
docker-compose up -d
```


## Spring Data Elasticsearch
Spring Data automatically creates the Elasticsearch index and defines the document schema based on the mappings specified in our Java class, which is very convinient.

```java
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
    private String duration;
    @Field(type = FieldType.Keyword)
    private List<String> categories;
    private String description;
    // getters and setters
```

### Basic API
The idea is to use Spring Data to implement the basic operations such as `save`, `findById` and `deleteById`. Then, create REST endpoints for them.

POST /shows
```json
{
  "id": "s1",
  "title": "Dick Johnson Is Dead",
  "type": "Movie",
  "directors": [
    "Kirsten Johnson"
  ],
  "cast": [],
  "country": [
    "United States"
  ],
  "dateAdded": "September 26, 2021",
  "releaseYear": 2020,
  "duration": "90 min",
  "categories": [
    "Documentaries"
  ],
  "description": "As her father nears the end of his life, filmmaker Kirsten Johnson stages his death in inventive and comical ways to help them both face the inevitable."
}
```

Swagger: http://localhost:8080/swagger-ui/index.html

## Next Steps
- Index more/all documents/shows
- Explore search 
- Explore Elasticsearch client


## References

- [Spring Elasticsearch Version](https://docs.spring.io/spring-data/elasticsearch/reference/elasticsearch/versions.html)
- [Baeldung - Spring Data Elastic Search Tutorial](https://www.baeldung.com/spring-data-elasticsearch-tutorial)