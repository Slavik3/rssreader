package com.edvantis.rssreader.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.edvantis.rssreader.model.NewsItem;

@Repository
public interface RssRepository2 extends JpaRepository<NewsItem, Integer> {
	
	//@Query("SELECT t.html_body_detail FROM news_item t where t.id = :id") 
	public NewsItem findArticleById(@Param("id") Integer id);

}
