package com.mini.cms.admin.controller.basic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mini.cms.admin.dao.entity.basic.ResourcesEty;
import com.mini.cms.admin.dao.entity.basic.UserEty;
import com.mini.cms.admin.dao.mapper.basic.UserDao;

@Controller
@RequestMapping(value="/mini/cms/admin/basic/LoginController/")
public class LoginController {

	private Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private UserDao userDao;
	
	/**
	 * 登录
	 * @param userEty
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="login.sdo")
	public @ResponseBody String login(HttpServletRequest request, UserEty userEty) throws Exception {
		System.out.println("login ...");
//		String validateCode = request.getParameter("validateCode");
//		String sessionVC = (String) request.getSession().getAttribute("validateCode");
//		if(!validateCode.equalsIgnoreCase(sessionVC)) {
//			JSONObject obj = new JSONObject();
//			obj.put("success",true);
//			obj.put("result","error");
//			obj.put("info", "验证码输入错误！");
//			return obj.toString();
//		}
		
		List<UserEty> userList = userDao.selectByEntity(userEty);
		if(userList.size() == 1) {
			UserEty sessionEty = userList.get(0);
			sessionEty.setPassword("");//不存密码，以防泄漏
			request.getSession().setAttribute("UserEty", sessionEty);
			
			JSONObject retObj = new JSONObject();
			retObj.put("success", true);
			retObj.put("result", "success");
			return retObj.toString();
		}
		else {
			throw new Exception("没有找到用户！");
		}
	}
	
	@RequestMapping("logout.sdo")
	public @ResponseBody String logout(HttpServletRequest request, HttpServletResponse response) {
		JSONObject obj = new JSONObject();
		obj.put("success", true);
		request.getSession().removeAttribute("UserEty");
		request.getSession().invalidate();
		obj.put("result", "success");
		return obj.toString();
	}
	
	@RequestMapping("getUserPermission.sdo")
	public @ResponseBody String getUserPermission(HttpServletRequest request, HttpServletResponse response) {
		UserEty userEty = (UserEty) request.getSession().getAttribute("UserEty");
		JSONObject obj = new JSONObject();
		obj.put("UserInfo", userEty);
		
		JSONObject userMenuTree = getUserMenuTree(userEty);
		obj.put("userTree", userMenuTree);
		return obj.toString();
	}
	
	/**
	 * 只查询两级
	 * @param userEty
	 * @return
	 */
	private JSONObject getUserMenuTree(UserEty userEty) {
		JSONObject retObj = new JSONObject();
		
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("pnodeId", 0);
		paraMap.put("userId", userEty.getId());
		List<ResourcesEty> buttons = userDao.selectUserTree(paraMap);
		JSONArray rsArr = new JSONArray();
		for(ResourcesEty ety : buttons) {
			JSONObject aObj = new JSONObject();
			aObj.put("mainClass", ety.getMainClass());
			aObj.put("menuName", ety.getMenuName());
			aObj.put("jsClassFile", ety.getJsClassFile());
			aObj.put("id", ety.getNodeId());
			aObj.put("icon", ety.getIcon());
			aObj.put("type", ety.getType());
			
			Map<String, Object> sparaMap = new HashMap<String, Object>();
			sparaMap.put("pnodeId", ety.getNodeId());
			sparaMap.put("userId", userEty.getId());
			List<ResourcesEty> nodes = userDao.selectUserTree(sparaMap);
			if(nodes.size() == 0) {
				aObj.put("leaf", true);
			}
			
			JSONArray array = new JSONArray();
			for (int i = 0; i < nodes.size(); i++) {
				ResourcesEty rsEty = nodes.get(i);
				JSONObject obj = new JSONObject();
				obj.put("id", rsEty.getNodeId());
				obj.put("text", rsEty.getMenuName());
				obj.put("icon", rsEty.getIcon());
				obj.put("type", rsEty.getType());

				if(rsEty.getActionPath() != null && !rsEty.getActionPath().trim().equals("") && rsEty.getType().equals("iframe")) {
					obj.put("leaf", true);
					obj.put("url", rsEty.getActionPath());
				}
				else if(rsEty.getJsClassFile() != null && !rsEty.getJsClassFile().trim().equals("") && rsEty.getType().equals("JSClass")) {
					obj.put("leaf", true);
					obj.put("jsUrl", rsEty.getJsClassFile());
					obj.put("mainClass", rsEty.getMainClass());
					obj.put("namespace", rsEty.getNamespace());
				}
				else {
					obj.put("leaf", false);
				}
				array.add(obj);
			}
			aObj.put("subMenu", array);
			rsArr.add(aObj);
		}
		retObj.put("userTree", rsArr);
		return retObj;
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
