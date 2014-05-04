package com.mini.cms.admin.controller.basic;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mini.cms.admin.dao.entity.category.ContentItemEty;
import com.mini.cms.admin.dao.mapper.category.ContentItemDao;
import com.mini.cms.admin.dao.mapper.test.TestDao;

@Controller
@RequestMapping(value="/mini/cms/admin/basic/test/")
public class TestController {

	@Autowired
	private TestDao testDao;
	
	@Autowired
	private ContentItemDao contentItemDao;
	
	@RequestMapping(value="test.sdo")
	public @ResponseBody String test() {
		ContentItemEty ety = testDao.selectById(12);
		System.out.println(ety.getName());
		
		JSONObject retObj = new JSONObject();
		retObj.put("success", true);
		retObj.put("result", "success");
		return retObj.toString();
	}
	
	@RequestMapping(value="test2.sdo")
	public @ResponseBody String test2() {
		ContentItemEty ety = contentItemDao.selectById(40);
		ContentItemEty ety2 = contentItemDao.testSelectById(48);
		JSONObject retObj = new JSONObject();
		retObj.put("success", true);
		retObj.put("result", "success");
		retObj.put("name1", ety.getName());
		retObj.put("name2", ety2.getName());
		
		return retObj.toString();
	}
}
