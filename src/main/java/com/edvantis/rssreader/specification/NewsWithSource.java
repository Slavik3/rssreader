package com.edvantis.rssreader.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.edvantis.rssreader.model.NewsItem;

public class NewsWithSource implements Specification<NewsItem> {

	private String source;
	
	
	public NewsWithSource(String source) {
		super();
		this.source = source;
	}



	@Override
	public Predicate toPredicate(Root<NewsItem> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		if (source.equals("undefined")) {
            return criteriaBuilder.isTrue(criteriaBuilder.literal(true)); 
        }
        return criteriaBuilder.equal(root.get("source"), this.source);
	}

}
