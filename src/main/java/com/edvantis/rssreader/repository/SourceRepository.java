package com.edvantis.rssreader.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edvantis.rssreader.model.Source;

@Repository
public interface SourceRepository extends JpaRepository<Source, String> {
	public Source findBySourceURL(String sourceURL);

}
