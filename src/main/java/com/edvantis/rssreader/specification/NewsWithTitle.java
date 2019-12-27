package com.edvantis.rssreader.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.edvantis.rssreader.model.NewsItem;

public class NewsWithTitle implements Specification<NewsItem> {
	private String title;
	
	
	public NewsWithTitle(String title) {
		super();
		this.title = title;
	}


	@Override
	public Predicate toPredicate(Root<NewsItem> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		if (title.equals("undefined")) {
            return criteriaBuilder.isTrue(criteriaBuilder.literal(true)); 
        }
        return criteriaBuilder.like(root.get("title"), this.title);
	}

}
