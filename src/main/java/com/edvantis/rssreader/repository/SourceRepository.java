package com.edvantis.rssreader.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.edvantis.rssreader.model.Source;

@Repository
public interface SourceRepository extends JpaRepository<Source, Long> {
	public Source findBySourceURL(String sourceURL);
	@Transactional
	void deleteById(int id);
	
	/*@Query("SELECT 	sourceURL FROM source WHERE sourceURL = :hostname")
	public Source findByEmailReturnStream(String hostname);*/

}
