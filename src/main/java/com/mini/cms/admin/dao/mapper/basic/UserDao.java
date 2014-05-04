package com.mini.cms.admin.dao.mapper.basic;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import com.mini.cms.admin.dao.basic.BaseDao;
import com.mini.cms.admin.dao.entity.basic.ResourcesEty;
import com.mini.cms.admin.dao.entity.basic.UserEty;

public interface UserDao extends BaseDao<UserEty> {

	@Select("SELECT * FROM resource c " +
			"where isValiDate=1 and parantNodeId = #{pnodeId} " +
			"and exists (" +
				"select 1 from roleresource r " +
				"where exists (select 1 from userrole where userId =#{userId} and roleId = r.roleId )" + 
				" and r.nodeId = c.nodeId" +
			") order by menuOrder;")
	List<ResourcesEty> selectUserTree(Map<String, Object> paraMap);

}
