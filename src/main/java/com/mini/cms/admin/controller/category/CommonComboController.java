package com.mini.cms.admin.controller.category;

import java.util.List;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mini.cms.admin.dao.mapper.category.ContentDao;
import com.mini.cms.admin.util.JSONGrid;

@Controller
@RequestMapping(value="/mini/cms/admin/category/CommonComboController/")
public class CommonComboController {
	
	private Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private ContentDao contentDao;

	@RequestMapping(value="getContent.sdo")
	public @ResponseBody String getContent() throws Exception {
		List<CommonComboData> dataList = contentDao.selectAsCombo();
		return JSONGrid.toJSon(dataList).toString();
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
