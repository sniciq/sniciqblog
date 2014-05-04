package com.mini.cms.admin.controller.category;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mini.cms.admin.dao.entity.category.ContentItemEty;
import com.mini.cms.admin.dao.mapper.category.ContentItemDao;
import com.mini.cms.admin.util.DateJsonValueProcessor;
import com.mini.cms.admin.util.JSONGrid;

@Controller
@RequestMapping("/mini/cms/admin/category/ContentItemController/")
public class ContentItemController {
	
	private Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private ContentItemDao contentItemDao;


	@RequestMapping(value="search.sdo")
	public @ResponseBody String search(HttpServletRequest request, HttpServletResponse response, ContentItemEty contentItemEty) throws Exception {
		int count = contentItemDao.selectDataLimitCount(contentItemEty);
		List<ContentItemData> list = contentItemDao.selectDataByLimit(contentItemEty);
		JSONObject retObj = JSONGrid.toJSon(list, count, new SimpleDateFormat("yyyy-MM-dd HH:mm"));
		return retObj.toString();
	}


	@RequestMapping(value="save.sdo")
	public @ResponseBody String save(ContentItemEty contentItemEty) {
		JSONObject obj = new JSONObject();
		obj.put("success",true);
		if(contentItemEty.getId() == null) {
			contentItemEty.setCreateDate(new Date());
			contentItemDao.insert(contentItemEty);
		} else { 
			contentItemDao.updateById(contentItemEty);
		}
			
		obj.put("result","success");		
			
		return obj.toString();
	}


	@RequestMapping(value="delete.sdo")
	public @ResponseBody String delete(@RequestParam("id") int id) {
		JSONObject obj = new JSONObject();
		obj.put("success",true);
		contentItemDao.deleteById(id);
		obj.put("result","success");
		return obj.toString();
	}


	@RequestMapping(value="getDetailInfo.sdo")
	public @ResponseBody String getDetailInfo(@RequestParam("id") int id) {
		JSONObject obj = new JSONObject();
		obj.put("success",true);
		ContentItemEty contentItemEty = (ContentItemEty) contentItemDao.selectById(id);
		JsonConfig config = new JsonConfig();
		config.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
		JSONObject dataObj = JSONObject.fromObject(contentItemEty, config);		obj.put("data", dataObj);
		return obj.toString();
	}

	@ExceptionHandler
	public @ResponseBody String handle(Exception e) {
		logger.error("", e);
		JSONObject obj = new JSONObject();
		obj.put("success",true);
		obj.put("result","error");
		obj.put("info",e.getMessage());
		return obj.toString();
	}
}