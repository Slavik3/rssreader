package com.edvantis.rssreader.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.edvantis.rssreader.model.ItemGen;
import com.edvantis.rssreader.model.unews.com.ua.Item;
import com.edvantis.rssreader.model.unews.com.ua.Rss;

import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Element;

public class Util {
	
    public static String getDomainName(String url) {
        URI uri = null;
		try {
			uri = new URI(url);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
        String domain = uri.getHost();
        return domain.startsWith("www.") ? domain.substring(4) : domain;
    }
    
    
    public static List<ItemGen> getNews(String url) {
    	RestTemplate restTemplate = new RestTemplate();
		Rss forObject = restTemplate.getForObject(url, Rss.class);
		Item[] item = forObject.getChannel().getItem();
		List<ItemGen> news = new ArrayList<ItemGen>();
		for(int i=0; i<forObject.getChannel().getItem().length; i++){
			ItemGen ig = new ItemGen();
			ig.setTitle(item[i].getTitle());
			ig.setDescription(item[i].getDescription());
			ig.setLink(item[i].getLink());
			ig.setPubDate(item[i].getPubDate());
			ig.setSource(getDomainName(url));
			news.add(ig);
		}
		return news;
    }

    public static List<ItemGen> getNews_(String url) {
        List<ItemGen> items = new ArrayList<ItemGen>();
        try {
            System.out.println(url);
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(response.toString())));
            NodeList errNodes = doc.getElementsByTagName("item");
            if (errNodes.getLength() > 0) {
                for(int i=0; i<errNodes.getLength(); i++) {
                	ItemGen item = new ItemGen();
                    Element element = (Element) errNodes.item(i);
                    item.setTitle(element.getElementsByTagName("title").item(0).getTextContent());
                    item.setDescription(element.getElementsByTagName("description").item(0).getTextContent());
                    //item.setFullText(element.getElementsByTagName("yandex:full-text").item(0).getTextContent());
                    item.setLink(element.getElementsByTagName("link").item(0).getTextContent());
                    item.setPubDate(element.getElementsByTagName("pubDate").item(0).getTextContent());
                    item.setSource(getDomainName(url));
                    items.add(item);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return items;
    }
    
}
