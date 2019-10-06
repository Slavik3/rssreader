package com.edvantis.rssreader.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.edvantis.rssreader.model.Source;

@Repository
public interface SourceRepository extends MongoRepository<Source, String> {
	public Source findBySourceURL(String sourceURL);

}
