package com.edvantis.rssreader.specification;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.edvantis.rssreader.model.NewsItem;

public class NewsWithDateTo implements Specification<NewsItem> {

	private Date dateTo;

	public NewsWithDateTo(Date date) {
		super();
		this.dateTo = date;
	}

	@Override
	public Predicate toPredicate(Root<NewsItem> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		if (dateTo == null) {
			return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
		}
		return criteriaBuilder.lessThan(root.get("pubDate"), this.dateTo);
	}

}
