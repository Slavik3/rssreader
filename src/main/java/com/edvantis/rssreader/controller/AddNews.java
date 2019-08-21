package com.edvantis.rssreader.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.edvantis.rssreader.model.ItemGen;
import com.edvantis.rssreader.repository.RssRepository;
import com.edvantis.rssreader.services.Util;

public class AddNews extends TimerTask {
	
	private final RssRepository rssRepository;

	public AddNews(RssRepository rssRepository) {
		this.rssRepository = rssRepository;
	}
	
	public void run() {
		//System.out.println("Hello World!");
		
	}

	public static void main(String[] args) {
		
	}
}