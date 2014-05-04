package com.mini.cms.admin.controller.category;

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

import com.mini.cms.admin.dao.entity.category.ContentGroupEty;
import com.mini.cms.admin.dao.mapper.category.ContentGroupDao;
import com.mini.cms.admin.util.DateJsonValueProcessor;
import com.mini.cms.admin.util.JSONGrid;

@Controller
@RequestMapping("/mini/cms/admin/category/ContentGroupController/")
public class ContentGroupController {
	
	private Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private ContentGroupDao contentgroupDao;

	@RequestMapping(value = "search.sdo")
	public @ResponseBody
	String search(HttpServletRequest request, HttpServletResponse response, ContentGroupEty contentGroupEty) throws Exception {
		int count = contentgroupDao.selectLimitCount(contentGroupEty);
		List<ContentGroupEty> list = contentgroupDao.selectByLimit(contentGroupEty);
		JSONObject retObj = JSONGrid.toJSon(list, count);
		return retObj.toString();
	}

	@RequestMapping(value = "save.sdo")
	public @ResponseBody String save(ContentGroupEty contentGroupEty) {
		JSONObject obj = new JSONObject();
		obj.put("success", true);
		if (contentGroupEty.getId() == null) {
			contentgroupDao.insert(contentGroupEty);
		} else {
			contentgroupDao.updateById(contentGroupEty);
		}
		obj.put("result", "success");
		return obj.toString();
	}

	@RequestMapping(value = "delete.sdo")
	public @ResponseBody String delete(@RequestParam("id") int id) {
		JSONObject obj = new JSONObject();
		obj.put("success", true);
		contentgroupDao.deleteById(id);
		obj.put("result", "success");
		return obj.toString();
	}

	@RequestMapping(value = "getDetailInfo.sdo")
	public @ResponseBody String getDetailInfo(@RequestParam("id") int id) {
		JSONObject obj = new JSONObject();
		obj.put("success", true);
		ContentGroupEty contentGroupEty = (ContentGroupEty) contentgroupDao.selectById(id);
		JsonConfig config = new JsonConfig();
		config.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
		JSONObject dataObj = JSONObject.fromObject(contentGroupEty, config);
		obj.put("data", dataObj);
		return obj.toString();
	}

	@ExceptionHandler
	public @ResponseBody String handle(Exception e) {
		logger.error("", e);
		JSONObject obj = new JSONObject();
		obj.put("success", true);
		obj.put("result", "error");
		obj.put("info", e.getMessage());
		return obj.toString();
	}
}