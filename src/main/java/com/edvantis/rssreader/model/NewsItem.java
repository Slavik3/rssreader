package com.edvantis.rssreader.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class NewsItem implements Comparable<NewsItem> {

	
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String title;
	private String description;
    private String link;
    private Date pub_date;
    private String source;
	private Date creation_date = new Date();
	
	public NewsItem(){
		
	}

	public NewsItem(int id, String title, String description, String link, Date pubDate, String source,
			Date creation_date) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.link = link;
		this.pub_date = pubDate;
		this.source = source;
		this.creation_date = creation_date;
	}


	public int getId() {
		return id;
	}

	public void setItemId(int id) {
		this.id = id;
	}

	public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

	public Date getCreationDate() {
		return creation_date;
	}

	public void setCreationDate(Date creationDate) {
		this.creation_date = creationDate;
	}
	
	public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Date getPubDate() {
        return pub_date;
    }

    public void setPubDate(Date pubDate) {
        this.pub_date = pubDate;
    }
    
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

	@Override
	public String toString() {
		return "ItemGen [id=" + id + ", title=" + title + ", description=" + description + ", link=" + link
				+ ", pubDate=" + pub_date + ", source=" + source + ", creationDate=" + creation_date + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((link == null) ? 0 : link.hashCode());
		result = prime * result + ((pub_date == null) ? 0 : pub_date.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NewsItem other = (NewsItem) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (link == null) {
			if (other.link != null)
				return false;
		} else if (!link.equals(other.link))
			return false;
		if (pub_date == null) {
			if (other.pub_date != null)
				return false;
		} else if (!pub_date.equals(other.pub_date))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
    
	@Override
	public int compareTo(NewsItem o) {
		return getPubDate().compareTo(o.getPubDate());
	}
    
}
