package com.mini.cms.admin.controller.task;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mini.cms.admin.services.task.ITNewsTasker;

@Controller
@RequestMapping(value="/mini/cms/admin/task/TaskController/")
public class TaskController {
	
	@Autowired
	private ITNewsTasker itNewsTasker;

	@RequestMapping("testTask.sdo")
	public @ResponseBody String testTask() {
		JSONObject retObj = new JSONObject();
		retObj.put("success", true);
		retObj.put("result", "success");
		try {
			itNewsTasker.doGenerate();
		} catch (Exception e) {
			e.printStackTrace();
			retObj.put("result", "error");
			retObj.put("info", e.getMessage());
		}
		return retObj.toString();
	}
}
