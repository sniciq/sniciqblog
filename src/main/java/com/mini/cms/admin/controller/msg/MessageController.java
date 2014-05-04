package com.mini.cms.admin.controller.msg;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mini.cms.admin.dao.entity.msg.MessageEty;
import com.mini.cms.admin.dao.mapper.msg.MessageDao;
import com.mini.cms.admin.util.DateJsonValueProcessor;
import com.mini.cms.admin.util.JSONGrid;

@Controller
@RequestMapping("/mini/cms/admin/msg/MessageController/")
public class MessageController {

private Logger logger = Logger.getLogger(MessageController.class);

	@Autowired
	private MessageDao messageDao;

	@InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

	@RequestMapping(value="search.sdo")
	public @ResponseBody String search(HttpServletRequest request, HttpServletResponse response, MessageEty messageEty) throws Exception {
		int count = messageDao.selectLimitCount(messageEty);
		List<MessageEty> list = messageDao.selectByLimit(messageEty);
		JSONObject retObj = JSONGrid.toJSon(list, count, new SimpleDateFormat("yyyy-MM-dd HH:mm"));
		return retObj.toString();
	}


	@RequestMapping(value="save.sdo")
	public @ResponseBody String save(MessageEty messageEty) {
		JSONObject obj = new JSONObject();
		obj.put("success",true);
		if(messageEty.getId() == null) {
			messageDao.insert(messageEty);
		} else { 
			messageDao.updateById(messageEty);
		}
		obj.put("result","success");
		return obj.toString();
	}


	@RequestMapping(value="delete.sdo")
	public @ResponseBody String delete(@RequestParam("id") int id) {
		JSONObject obj = new JSONObject();
		obj.put("success",true);
		messageDao.deleteById(id);
		obj.put("result","success");
		return obj.toString();
	}


	@RequestMapping(value="getDetailInfo.sdo")
	public @ResponseBody String getDetailInfo(@RequestParam("id") int id) {
		JSONObject obj = new JSONObject();
		obj.put("success",true);
		MessageEty messageEty = (MessageEty) messageDao.selectById(id);
		JsonConfig config = new JsonConfig();
		config.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
		JSONObject dataObj = JSONObject.fromObject(messageEty, config);		
		obj.put("data", dataObj);
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