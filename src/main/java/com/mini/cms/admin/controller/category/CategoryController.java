package com.mini.cms.admin.controller.category;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mini.cms.admin.dao.entity.category.CategoryEty;
import com.mini.cms.admin.dao.mapper.category.CategoryDao;
import com.mini.cms.admin.util.DateJsonValueProcessor;
import com.mini.cms.admin.util.JSONGrid;

@Controller
@RequestMapping("/mini/cms/admin/category/CategoryController/")
public class CategoryController {
	
	private Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private CategoryDao categoryDao;

	@RequestMapping(value = "search.sdo")
	public @ResponseBody String search(HttpServletRequest request, HttpServletResponse response, CategoryEty categoryEty) throws Exception {
		int count = categoryDao.selectLimitCount(categoryEty);
		List<CategoryEty> list = categoryDao.selectByLimit(categoryEty);
		JSONObject retObj = JSONGrid.toJSon(list, count);
		return retObj.toString();
	}
	
	@RequestMapping(value = "getSubCategory.sdo")
	public @ResponseBody String getSubCategory(@RequestParam("node") int pId) throws Exception {
		CategoryEty ety = new CategoryEty();
		ety.setParentId(pId);
		List<CategoryEty> list = categoryDao.selectByEntity(ety);
		JSONArray retArr = new JSONArray();
		retArr.addAll(list);
		return retArr.toString();
	} 

	@RequestMapping(value = "save.sdo")
	public @ResponseBody String save(CategoryEty categoryEty) {
		JSONObject obj = new JSONObject();
		obj.put("success", true);
		if (categoryEty.getId() == null) {
			categoryDao.insert(categoryEty);
		} else {
			categoryDao.updateById(categoryEty);
		}
		obj.put("result", "success");
		return obj.toString();
	}

	@RequestMapping(value = "delete.sdo")
	public @ResponseBody
	String delete(@RequestParam("id") int id) {
		JSONObject obj = new JSONObject();
		obj.put("success", true);
		categoryDao.deleteById(id);
		obj.put("result", "success");
		return obj.toString();
	}

	@RequestMapping(value = "getDetailInfo.sdo")
	public @ResponseBody String getDetailInfo(@RequestParam("id") int id) {
		JSONObject obj = new JSONObject();
		obj.put("success", true);
		CategoryEty categoryEty = (CategoryEty) categoryDao.selectById(id);
		JsonConfig config = new JsonConfig();
		config.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
		JSONObject dataObj = JSONObject.fromObject(categoryEty, config);
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