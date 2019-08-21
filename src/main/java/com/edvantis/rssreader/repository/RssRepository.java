package com.edvantis.rssreader.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.edvantis.rssreader.model.ItemGen;

@Repository
public interface RssRepository extends MongoRepository<ItemGen, String> {
	
	public List<ItemGen> findBySource(String source);
}
