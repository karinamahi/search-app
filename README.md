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

Elasticsearch documentation provides a very helpful list of supported keywords. See the documentation [here](https://docs.spring.io/spring-data/elasticsearch/docs/4.1.5/reference/html/#elasticsearch.query-methods.criterions).

## Indexing the dataset

I created the `LoadDataHelper.java` to help me to index the Netflix dataset. Now that all netflix shows are indexed, the next step is to explore the search features.

## Search Similar - Spring Data Elasticsearch
The method `similarSearch` in Spring Data Elasticsearch allows us to find documents similar to a given document. It uses Elasticsearch's `more_like_this` query under the hood.

This can be useful for recommendations and related content suggestions.

We can specify the fields to be compared. In this case, I'm using the fields `title` and `description`. We also must specify the document/show to be used as reference.

```java
public PagedResponse<Show> searchSimilar(String id, int pageNumber, int pageSize) {
    Show show = findById(id);
    String[] fields = {"title", "description"};
    Page<Show> shows = repository.searchSimilar(show, fields, PageRequest.of(pageNumber, pageSize));
    return getPagedResponse(shows);
}
```
First I searched for `Squid Game`:

```json
//http://localhost:8080/shows?title=Squid%20Game&page=0&size=30
{
  "content": [
    {
      "id": "s34",
      "title": "Squid Game",
      "type": "TV Show",
      "directors": [],
      "cast": [
        "Lee Jung-jae",
        "Park Hae-soo",
        "Wi Ha-jun",
        "Oh Young-soo",
        "Jung Ho-yeon",
        "Heo Sung-tae",
        "Kim Joo-ryoung",
        "Tripathi Anupam",
        "You Seong-joo",
        "Lee You-mi"
      ],
      "country": [],
      "dateAdded": "September 17, 2021",
      "releaseYear": 2021,
      "rating": "TV-MA",
      "duration": "1 Season",
      "categories": [
        "International TV Shows",
        "TV Dramas",
        "TV Thrillers"
      ],
      "description": "Hundreds of cash-strapped players accept a strange invitation to compete in children's games. Inside, a tempting prize awaits — with deadly high stakes."
    }
  ],
  "page": 0,
  "size": 30,
  "totalItems": 1,
  "totalPages": 1
}
```
Then, I searched the similar shows using its id:
```json
//http://localhost:8080/shows/similar?id=s34&page=0&size=10
```
You can view the results [here](public/search-similar-title-and-description.json).

Comparing the results, I couldn't quite understand how Elasticsearch is matching the title and description - I thought I would at least see some similar words.

But let's continue exploring the `searchSimilar` method.

Now I'm imagining a list of recommendations based on country, like a section titled 'Other Korean shows you might like'.

So I'm using only the `country` field:
```java
String[] fields = {"country"};
```
Now it's not returning any results:
```json
{
  "content": [],
  "page": 0,
  "size": 10,
  "totalItems": 0,
  "totalPages": 0
}
```
I noticed that Squid Game doesn't have any values for field `country`. So I tried other shows making sure they have values for `country` but even though, nothing is returned.
I wonder if there is something wrong with `country` field specification. Currently it is like this:

```java
@Field(type = FieldType.Text)
private List<String> country;
```
I tried to use the fields `categories` and `cast`, and I had the zero results as well. So my guess is that there is something wrong related to lists.

I enabled the HTTP-level logging by adding this to `application.properties`:
```properties
logging.level.org.apache.http=DEBUG
logging.level.org.elasticsearch.client=DEBUG
```
Then, I got this from logs:
```text
{"from":0,"query":{"more_like_this":{"fields":["cast"],"like":[{"_id":"s784","_index":"shows"}]}},"size":10,"sort":[],"track_scores":false,"version":true}[\r][\n]

{"took":654,"timed_out":false,"_shards":{"total":1,"successful":1,"skipped":0,"failed":0},"hits":{"total":{"value":0,"relation":"eq"},"max_score":null,"hits":[]}}[\r][\n]
```
Looking at the [Elasticsearch documentation](https://www.elastic.co/docs/reference/query-languages/query-dsl/query-dsl-mlt-query), there is this note:

> Important
>
> The fields on which to perform MLT must be indexed and of type text or keyword. Additionally, when using like with documents, either _source must be enabled or the fields must be stored or store term_vector. In order to speed up analysis, it could help to store term vectors at index time.

Let's see how my documents are indexed:
```text
I'm using httpie on terminal
http GET http://localhost:9200/shows
```
Reading the note again, I don't see any problem with the mapping. See my mapping details [here](public/show-mapping.json).

I'm not sure the `more_like_this` query needs larger text to work properly or there is something I'm missing here. Maybe in the future I will return to understand this better.

## Frontend
To make this study project more visual, I decided to create a frontend module, providing a friendlier way to compare results and further explore search features and concepts.

- Vite + React
- project search-ui
- using gradle to build search-ui and send the generated files to search-api.

run the application using the terminal or IntelliJ.
```bash
gradle search-api:bootRun
```
http://localhost:8080/search-ui/index.html

This will be our first version of the frontend. It is quite simple, and we'll improve it according to our needs.
![image info](https://github.com/karinamahi/search-app/blob/main/public/search-page-v1.png)

## Next Steps
- Front-end module (In progress)
- Explore search (TODO)
- Explore Elasticsearch client (TODO)


## References

- [Spring Elasticsearch Version](https://docs.spring.io/spring-data/elasticsearch/reference/elasticsearch/versions.html)
- [Baeldung - Spring Data Elastic Search Tutorial](https://www.baeldung.com/spring-data-elasticsearch-tutorial)