package com.mini.cms.admin.controller.basic;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mini.cms.admin.dao.entity.basic.RoleEty;
import com.mini.cms.admin.dao.mapper.basic.RoleDao;
import com.mini.cms.admin.dao.mapper.basic.UserRoleDao;
import com.mini.cms.admin.services.basic.UserRoleService;
import com.mini.cms.admin.util.JSONGrid;

@Controller
@RequestMapping("/mini/cms/admin/basic/UserRoleController/")
public class UserRoleController {
	
	private Logger logger = Logger.getLogger(UserRoleController.class);
	
	@Autowired
	private UserRoleDao userRoleDao;
	
	@Autowired
	private RoleDao roleDao;
	
	@Resource(name="UserRoleService")
	private UserRoleService userRoleService;
	
	@RequestMapping("save.sdo")
	public @ResponseBody String save(@RequestParam("userIds") String userIds, @RequestParam("roleIds") String ids) throws Exception {
		JSONObject obj = new JSONObject();
		String[] userIdArr = StringUtils.split(userIds, ",");
		String[] roleIds = StringUtils.split(ids, ",");
		userRoleService.saveUserRole(userIdArr, roleIds);
		obj.put("success",true);
		obj.put("result","success");
		return obj.toString();
	}
	
	@RequestMapping("getUserUnhaveRoles.sdo")
	public @ResponseBody String getUserUnhaveRoles(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<RoleEty> list;
		String userIdStr = request.getParameter("userId");
		if(userIdStr != null && !userIdStr.trim().equals("")) {
			list = userRoleDao.selectUserUnhaveRoles(Integer.parseInt(userIdStr));
		}
		else {
			list = roleDao.selectByEntity(null);
		}
		
		JSONObject retObj = JSONGrid.toJSon(list, list.size());
		return retObj.toString();
	}
	
	@RequestMapping("getUserRoles.sdo")
	public @ResponseBody String getUserRoles(@RequestParam("userId") int userId) throws Exception {
		List<RoleEty> list = userRoleDao.selectUserRoles(userId);
		JSONObject retObj = JSONGrid.toJSon(list, list.size());
		return retObj.toString();
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
