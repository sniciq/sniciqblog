package com.mini.cms.admin.controller.basic;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mini.cms.admin.dao.entity.basic.ResourcesEty;
import com.mini.cms.admin.dao.mapper.basic.ResourcesDao;
import com.mini.cms.admin.util.EntityReflect;
import com.mini.cms.admin.util.ExtLimit;
import com.mini.cms.admin.util.JSONGrid;

@Controller
@RequestMapping("/mini/cms/admin/basic/ResourcesController/")
@SuppressWarnings("rawtypes")
public class ResourcesController {
	
	private Logger logger = Logger.getLogger(ResourcesController.class);
	
	@Autowired
	private ResourcesDao resourcesDao;
	
	@RequestMapping("search.sdo")
	public @ResponseBody String search(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject data = null;
		ResourcesEty resourcesEty = EntityReflect.createObjectFromRequest(request, ResourcesEty.class);
		ExtLimit limit = EntityReflect.createObjectFromRequest(request, ExtLimit.class);
		resourcesEty.setExtLimit(limit);
		int count = resourcesDao.selectLimitCount(resourcesEty);
		List list = resourcesDao.selectByLimit(resourcesEty);
		data = JSONGrid.toJSon(list, count);
		return data.toString();
	}

	@RequestMapping("save.sdo")
	public @ResponseBody String save(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject obj = new JSONObject();
		obj.put("success",true);
		ResourcesEty resourcesEty = EntityReflect.createObjectFromRequest(request, ResourcesEty.class);
		if(resourcesEty.getId() == null) {
			resourcesDao.insert(resourcesEty);
		} else { 
			resourcesDao.updateById(resourcesEty);
		}
		obj.put("result","success");		
		return obj.toString();
	}

	@RequestMapping("delete.sdo")
	public @ResponseBody String delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject obj = new JSONObject();
		obj.put("success",true);
		String id = request.getParameter("id");
		resourcesDao.deleteById(Integer.parseInt(id));
		obj.put("result","success");
		return obj.toString();
	}

	@RequestMapping("getDetailInfo.sdo")
	public @ResponseBody String getDetailInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject obj = new JSONObject();
		obj.put("success",true);
		String id = request.getParameter("id");
		ResourcesEty resourcesEty = resourcesDao.selectById(Integer.parseInt(id));
		obj.put("data", resourcesEty);
		return obj.toString();
	}
	
	@ExceptionHandler
	public @ResponseBody String handle(Exception e) {
		logger.error(e.getMessage(), e);
		JSONObject obj = new JSONObject();
		obj.put("success",true);
		obj.put("result","error");
		obj.put("info",e.getMessage());
		return obj.toString();
	}
}