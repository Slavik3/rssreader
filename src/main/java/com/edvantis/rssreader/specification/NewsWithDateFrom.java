package com.edvantis.rssreader.specification;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.edvantis.rssreader.model.NewsItem;

public class NewsWithDateFrom implements Specification<NewsItem> {

	private Date dateFrom;
	
	public NewsWithDateFrom(Date dateFrom) {
		super();
		this.dateFrom = dateFrom;
	}
	
	@Override
	public Predicate toPredicate(Root<NewsItem> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		if (dateFrom == null) {
            return criteriaBuilder.isTrue(criteriaBuilder.literal(true)); 
        }
        return criteriaBuilder.greaterThan(root.get("pubDate"), this.dateFrom);
	}

}
