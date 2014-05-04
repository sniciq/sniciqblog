package com.mini.cms.admin.controller.content;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mini.cms.admin.dao.entity.content.ContentDetailEty;
import com.mini.cms.admin.dao.mapper.content.ContentDetailDao;

@Controller
@RequestMapping(value="/mini/cms/admin/content/ContentConroller/")
public class ContentConroller {
	
	private Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private ContentDetailDao contentDetailDao;

	@RequestMapping(value="showContentInfo.sdo")
	public String showContentInfo(HttpServletRequest request, @RequestParam("itemId") int itemId) {
		ContentDetailEty ety = contentDetailDao.selectByItemId(itemId);
		if(ety != null) {
			request.setAttribute("content", ety.getDetail());
			request.setAttribute("id", ety.getId());
		}
		request.setAttribute("itemId", itemId);
		return "/admin/content/ContentEditor.jsp";
	}
	
	@RequestMapping(value="preview.sdo")
	public String preview(HttpServletRequest request, @RequestParam("itemId") int itemId) {
		ContentDetailEty ety = contentDetailDao.selectByItemId(itemId);
		if(ety != null) {
			request.setAttribute("content", ety.getDetail());
			request.setAttribute("id", ety.getId());
		}
		request.setAttribute("itemId", itemId);
		return "/admin/content/ContentPreview.jsp";
	}
	
	@RequestMapping(value="saveContent.sdo")
	public String saveContent(ContentDetailEty ety) {
		if(ety.getId() != null) {
			contentDetailDao.updateById(ety);
		}
		else {
			contentDetailDao.insert(ety);
		}
		return "redirect:/mini/cms/admin/content/ContentConroller/preview.sdo?itemId=" + ety.getItemId();
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
