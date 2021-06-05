package me.amarpandey.urlshortener.repository;

import me.amarpandey.urlshortener.models.Url;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlShortenerRepository extends MongoRepository<Url, String> {

    @Query("{'url' : ?0}")
    Url findByUrl(String longUrl);

    @Query("{'shorthand' : ?0}")
    Url findByShorthand(String shorthand);
}
