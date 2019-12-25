package com.edvantis.rssreader.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.edvantis.rssreader.model.NewsItem;

@Repository
public interface RssRepository extends JpaRepository<NewsItem, Integer> {

	public Page<NewsItem> findBySource(String source, Pageable pageable);

	public Page<NewsItem> findBySourceAndTitle(String source, String title, Pageable pageable);

	Page<NewsItem> findAllByPubDateBetween(Date publicationTimeStart, Date publicationTimeEnd, Pageable pageable);

	Page<NewsItem> findAll(Pageable pageable);

	// @Query("SELECT t.html_body_detail FROM news_item t where t.id = :id")
	public NewsItem findArticleById(@Param("id") Integer id);

}
