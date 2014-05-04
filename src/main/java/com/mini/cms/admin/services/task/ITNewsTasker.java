package com.mini.cms.admin.services.task;

import java.util.Date;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mini.cms.admin.dao.entity.category.ContentEty;
import com.mini.cms.admin.dao.entity.category.ContentItemEty;
import com.mini.cms.admin.dao.entity.content.ContentDetailEty;
import com.mini.cms.admin.dao.mapper.category.ContentDao;
import com.mini.cms.admin.dao.mapper.category.ContentItemDao;
import com.mini.cms.admin.dao.mapper.content.ContentDetailDao;

@Service
public class ITNewsTasker {
	
	@Autowired
	private ContentDao contentDao;
	
	@Autowired
	private ContentItemDao contentItemDao;
	
	@Autowired
	private ContentDetailDao contentDetailDao;
	
	public void doGenerate() throws Exception {
		try {
			int contentId = 1;
			ContentEty contentEty = new ContentEty();
			contentEty.setName("新闻动态");
			List<ContentEty> contentList = contentDao.selectByEntity(contentEty);
			if(contentList.size() > 0) {
				contentId = contentList.get(0).getId();
			}
			
			Date nowDate = new Date();
			Document doc = Jsoup.connect("http://it.sohu.com/").get();
			Elements news = doc.select("#con_tab_1 li");
//			Elements news = newsHeadlines.get(0).children();
			for(int i = 0; i < news.size(); i++) {
				Element el = news.get(i);
				Elements links = el.select("a");
				if(links.size() < 1)
					continue;
				
				Element theLink = links.get(0);
				String title = theLink.html();
				String href = theLink.attr("href");
				
				ContentItemEty searchEty = new ContentItemEty();
				searchEty.setName(title);
				int c = contentItemDao.selectDataLimitCount(searchEty);
				if(c > 0)
					continue;
				
				ContentItemEty ety = new ContentItemEty();
				ety.setName(title);
				ety.setRemark(href);
				ety.setContentId(contentId);
				ety.setCreateDate(nowDate);
				long lOrder = new Date().getTime();
				ety.setItemOrder((double) lOrder);
				ety.setItemType(1);
				contentItemDao.insert(ety);
				
				String detail = getNewsDetail(href);
				ContentDetailEty detailEty = new ContentDetailEty();
				detailEty.setItemId(ety.getId());
				detailEty.setDetail(detail);
				contentDetailDao.insert(detailEty);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	private String getNewsDetail(String href) throws Exception {
		Document doc = Jsoup.connect(href).get();
		Elements els = doc.select("div.content-box");
		if(els.size() > 0)
			return els.get(0).html();
		return "";
	}
	
	public void doGenerate_old() {
		try {
			int contentId = 1;
			ContentEty contentEty = new ContentEty();
			contentEty.setName("新闻动态");
			List<ContentEty> contentList = contentDao.selectByEntity(contentEty);
			if(contentList.size() > 0) {
				contentId = contentList.get(0).getId();
			}
			
			Date nowDate = new Date();
			Document doc = Jsoup.connect("http://it.sohu.com/internet.shtml").get();
			Elements newsHeadlines = doc.select("div.lcolumn>div.lcolumn_01>div.lc>ul");
			Elements news = newsHeadlines.get(0).children();
			for(int i = 0; i < news.size(); i++) {
				Element el = news.get(i);
				Elements links = el.select("a");
				if(links.size() < 1)
					continue;
				
				Element theLink = links.get(0);
				String title = theLink.html();
				String href = theLink.attr("href");
				
				ContentItemEty searchEty = new ContentItemEty();
				searchEty.setName(title);
				int c = contentItemDao.selectDataLimitCount(searchEty);
				if(c > 0)
					continue;
				
				ContentItemEty ety = new ContentItemEty();
				ety.setName(title);
				ety.setRemark(href);
				ety.setContentId(contentId);
				ety.setCreateDate(nowDate);
				long lOrder = new Date().getTime();
				ety.setItemOrder((double) lOrder);
				ety.setItemType(1);
				contentItemDao.insert(ety);
				
				String detail = getNewsDetail_Old(href);
				ContentDetailEty detailEty = new ContentDetailEty();
				detailEty.setItemId(ety.getId());
				detailEty.setDetail(detail);
				contentDetailDao.insert(detailEty);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private String getNewsDetail_Old(String href) throws Exception {
		Document doc = Jsoup.connect(href).get();
		Elements els = doc.select("#contentText");
		if(els.size() > 0)
			return els.get(0).html();
		return "";
	}
	
}
