package com.mini.cms.admin.controller.basic;
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

import com.mini.cms.admin.dao.entity.basic.RoleEty;
import com.mini.cms.admin.dao.mapper.basic.RoleDao;
import com.mini.cms.admin.util.DateJsonValueProcessor;
import com.mini.cms.admin.util.JSONGrid;

@Controller
@RequestMapping("/mini/cms/admin/basic/RoleController/")
@SuppressWarnings("rawtypes")
public class RoleController {
	
	private Logger logger = Logger.getLogger(RoleController.class);
	
	@Autowired
	private RoleDao roleDao;

	@RequestMapping(value="search.sdo")
	public @ResponseBody String search(HttpServletRequest request, HttpServletResponse response, RoleEty roleEty) throws Exception {
		int count = roleDao.selectLimitCount(roleEty);
		List list = roleDao.selectByLimit(roleEty);
		JSONObject retObj = JSONGrid.toJSon(list, count);
		return retObj.toString();
	}

	@RequestMapping(value="save.sdo")
	public @ResponseBody String save(RoleEty roleEty) {
		JSONObject obj = new JSONObject();
		obj.put("success",true);
		if(roleEty.getId() == null) {
			roleDao.insert(roleEty);
		} else { 
			roleDao.updateById(roleEty);
		}
		obj.put("result","success");		
		return obj.toString();
	}


	@RequestMapping(value="delete.sdo")
	public @ResponseBody String delete(@RequestParam("id") int id) {
		JSONObject obj = new JSONObject();
		obj.put("success",true);
		roleDao.deleteById(id);
		obj.put("result","success");
		return obj.toString();
	}


	@RequestMapping(value="getDetailInfo.sdo")
	public @ResponseBody String getDetailInfo(@RequestParam("id") int id) {
		JSONObject obj = new JSONObject();
		obj.put("success",true);
		RoleEty roleEty = (RoleEty) roleDao.selectById(id);
		JsonConfig config = new JsonConfig();
		config.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
		JSONObject dataObj = JSONObject.fromObject(roleEty, config);
		obj.put("data", dataObj);
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