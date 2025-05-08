# Search API
Goal: Learn about Elastic Search, Spring Data Elastic Search and about Search Concepts.





run elastic search instance using docker.
the security is disabled (docker-compose.yml) 

```shell
docker-compose up -d
```

delete index if needed:
```
curl -X DELETE http://localhost:9200/shows
```

Swagger

http://localhost:8080/swagger-ui/index.html

## References
[Spring Elasticsearch Version](https://docs.spring.io/spring-data/elasticsearch/reference/elasticsearch/versions.html)
[Baeldung - Spring Data Elastic Search Tutorial](https://www.baeldung.com/spring-data-elasticsearch-tutorial)