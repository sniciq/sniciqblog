package com.mini.cms.front.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mini.cms.admin.dao.entity.category.ContentEty;
import com.mini.cms.admin.dao.entity.category.ContentItemEty;
import com.mini.cms.admin.dao.entity.content.ContentDetailEty;
import com.mini.cms.admin.dao.entity.msg.MessageEty;
import com.mini.cms.admin.dao.mapper.category.ContentDao;
import com.mini.cms.admin.dao.mapper.category.ContentItemDao;
import com.mini.cms.admin.dao.mapper.content.ContentDetailDao;
import com.mini.cms.admin.dao.mapper.msg.MessageDao;
import com.mini.cms.admin.util.DateJsonValueProcessor;
import com.mini.cms.admin.util.ExtLimit;
import com.mini.cms.admin.util.JSONGrid;

@Controller
@RequestMapping(value="/front/cc/")
public class CommonController {
	
	private Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private ContentDetailDao contentDetailDao;
	
	@Autowired
	private ContentItemDao contentItemDao;
	
	@Autowired
	private ContentDao contentDao;
	
	@Autowired
	private MessageDao messageDao;
	
	@RequestMapping(value="dtail.sdo")
	public @ResponseBody String getContentDetail(@RequestParam("itemId") int itemId) {
		ContentDetailEty ety = contentDetailDao.selectByItemId(itemId);
		if(ety != null)
			return ety.getDetail();
		return "没有找到正文...";
	}
	
	@RequestMapping(value="dtailN.sdo")
	public @ResponseBody String getContentDetailN(@RequestParam("itemId") int itemId) {
		ContentDetailEty ety = contentDetailDao.selectByItemId(itemId);
		if(ety != null)
			return JSONObject.fromObject(ety).toString();
		return new JSONObject().toString();
	}
	
	@RequestMapping(value="getleftSide.sdo")
	public @ResponseBody String getleftSide(@RequestParam("cids") String cids) {
		JSONObject retObj = new JSONObject();
		
		JSONArray sidesArr = new JSONArray();
		
		String[] cidArr = StringUtils.split(cids, ",");
		
		ExtLimit limit = new ExtLimit();
		limit.setStart(0);
		limit.setLimit(6);
		
		JsonConfig config = new JsonConfig();
		config.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor(new SimpleDateFormat("yyyy-MM-dd")));
		
		for(String cidStr : cidArr) {
			ContentEty contentEty = contentDao.selectById(Integer.valueOf(cidStr));
			if(contentEty == null)
				continue;
			
			ContentItemEty cEty = new ContentItemEty();
			cEty.setContentId(Integer.valueOf(cidStr));
			limit.setSort("itemOrder");
			limit.setDir("DESC");
			cEty.setExtLimit(limit);
			List<ContentItemEty> dataList = contentItemDao.selectByLimit(cEty);
			
			JSONObject aSideData = new JSONObject();
			JSONArray sideData = new JSONArray();
			sideData.addAll(dataList, config);
			aSideData.put("id", cidStr);
			aSideData.put("name", contentEty.getName());
			aSideData.put("items", sideData);
			
			sidesArr.add(aSideData);
		}
		
		retObj.put("sides", sidesArr);
		return retObj.toString();
	}
	
	/**
	 * 查询新闻列表，参数中需要有contentId
	 * @param searchEty
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="getContentItemList.sdo")
	public @ResponseBody String getContentItemList(ContentItemEty searchEty) throws Exception {
		ContentEty conentEty = contentDao.selectById(searchEty.getContentId());
		int count = contentItemDao.selectLimitCount(searchEty);
		List<ContentItemEty> dataList = contentItemDao.selectByLimit(searchEty);
		return JSONGrid.toJSon(dataList,count, new SimpleDateFormat("yyyy-MM-dd"), conentEty.getName()).toString();
	}
	
	@RequestMapping(value="msg.sdo")
	public @ResponseBody String msg(HttpServletRequest request, MessageEty message) {
		String validateCode = request.getParameter("validateCode");
		String sessionVC = (String) request.getSession().getAttribute("validateCode");
		if(!validateCode.equalsIgnoreCase(sessionVC)) {
			JSONObject obj = new JSONObject();
			obj.put("success",true);
			obj.put("result","error");
			obj.put("info", "验证码输入错误！");
			return obj.toString();
		}
		
		String ip = getVisitIP(request);
		message.setCreateDate(new Date());
		message.setIp(ip);
		message.setHaveReBack("否");
		messageDao.insert(message);
		JSONObject obj = new JSONObject();
		obj.put("success",true);
		obj.put("result","success");
		return obj.toString();
	}
	
	private String getVisitIP(HttpServletRequest request) {
		try {
			String ip = request.getHeader("X-Forwarded-For");
			if (ip == null || ip.equals("")) {
				ip = request.getRemoteAddr();
			}
			if (ip.indexOf(",") != -1) {
				String tmp[] = ip.split(",");
				ip = tmp[tmp.length - 1].trim();
			}
			return ip;
		}
		catch (Exception e) {
			return "";
		}
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
