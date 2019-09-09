package com.edvantis.rssreader.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.edvantis.rssreader.model.NewsItem;

@Repository
public interface RssRepository extends MongoRepository<NewsItem, String> {

	public List<NewsItem> findBySource(String source);
}
