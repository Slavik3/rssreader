package com.edvantis.rssreader.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.edvantis.rssreader.model.NewsItem;

@Repository
public interface RssRepository extends JpaRepository<NewsItem, Integer> {

	
	public NewsItem findArticleById(@Param("id") Integer id);

	public Page<NewsItem> findAll(Specification<NewsItem> spec, Pageable page);

}
