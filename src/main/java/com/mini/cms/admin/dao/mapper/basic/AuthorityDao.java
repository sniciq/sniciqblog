package com.mini.cms.admin.dao.mapper.basic;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import com.mini.cms.admin.controller.basic.RoleAuthorityData;
import com.mini.cms.admin.dao.basic.BaseDao;
import com.mini.cms.admin.dao.entity.basic.AuthorityEty;

public interface AuthorityDao extends BaseDao<AuthorityEty> {

	@Select("SELECT c.*,(select count(1) from resource where isValiDate=1 and parantNodeId = c.nodeId) childCount," +
				"exists (select 1 from roleresource " +
					"where roleId = #{roleId} and nodeId = c.nodeId) hasAuthority " +
			"FROM resource c where isValiDate=1 and parantNodeId = #{pnode} order by menuOrder;")
	List<RoleAuthorityData> selectRoleResource(Map<String, Object> pMap);
	
	
}