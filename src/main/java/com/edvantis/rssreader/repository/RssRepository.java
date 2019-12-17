package com.edvantis.rssreader.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.edvantis.rssreader.model.NewsItem;

@Repository
public interface RssRepository extends JpaRepository<NewsItem, String> {

	public List<NewsItem> findBySource(String source);
	
	/*@Query("SELECT t.html_body_detail FROM news_item t where t.id = :id") 
	public String findArticleById(@Param("id") String id);*/
}
