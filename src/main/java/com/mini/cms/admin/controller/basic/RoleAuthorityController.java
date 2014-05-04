package com.mini.cms.admin.controller.basic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mini.cms.admin.dao.entity.basic.RoleResourceEty;
import com.mini.cms.admin.dao.mapper.basic.AuthorityDao;
import com.mini.cms.admin.dao.mapper.basic.RoleResourceDao;

@Controller
@RequestMapping("/mini/cms/admin/basic/RoleAuthorityController/")
public class RoleAuthorityController {
	
	private Logger logger = Logger.getLogger(RoleAuthorityController.class);
	
	@Autowired
	private AuthorityDao authorityDao;
	
	@Autowired
	private RoleResourceDao roleResourceDao;
	
	@RequestMapping("getRoleResource.sdo")
	public @ResponseBody String getRoleResource(@RequestParam("roleId") int roleId, @RequestParam("node") String pnode) {
		JSONArray retObj = new JSONArray();
		
		Map<String, Object> pMap = new HashMap<String, Object>();
		pMap.put("roleId", roleId);
		pMap.put("pnode", pnode);
		
		List<RoleAuthorityData> list = authorityDao.selectRoleResource(pMap);
		
		for(RoleAuthorityData ety : list) {
			JSONObject aNode = new JSONObject();
			aNode.put("id", ety.getNodeId());
			aNode.put("text", ety.getMenuName());
			
			if(ety.getHasAuthority())
				aNode.put("checked", true);
			else
				aNode.put("checked", false);
			if(ety.getChildCount() > 0)
				aNode.put("leaf", false);
			else
				aNode.put("leaf", true);
			
			retObj.add(aNode);
		}
		
		return retObj.toString();
	}
	
	@RequestMapping("saveRoleAuthority.sdo")
	public @ResponseBody String saveRoleAuthority(@RequestParam("roleId") int roleId, @RequestParam("resourceNodes") String resourceStr, @RequestParam("unCheckNodes") String unCheckNodes) {
		if(!unCheckNodes.trim().equals("")) {
			Map<String, Object> pMap = new HashMap<String, Object>();
			pMap.put("roleId", roleId);
			pMap.put("allNodes", unCheckNodes);
			roleResourceDao.deleteByIds(pMap);
		}
		
		String[] resourceNodes = StringUtils.split(resourceStr, ",");
		for(String resource: resourceNodes) {
			RoleResourceEty ety = new RoleResourceEty();
			ety.setRoleId(roleId);
			ety.setNodeId(Integer.parseInt(resource));
			roleResourceDao.insert(ety);
		}
		JSONObject obj = new JSONObject();
		obj.put("success",true);
		obj.put("result","success");
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
