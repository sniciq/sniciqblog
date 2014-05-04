package com.mini.cms.admin.dao.mapper.basic;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import com.mini.cms.admin.dao.basic.BaseDao;
import com.mini.cms.admin.dao.entity.basic.RoleEty;
import com.mini.cms.admin.dao.entity.basic.UserRoleEty;

public interface UserRoleDao extends BaseDao<UserRoleEty> {

	@Delete("delete from userrole where userId in (${value})")
	void deleteByUserIds(String userIds);

	@Select("select * from role r where not exists (select 1 from userrole where r.id = roleId and userId = #{value});")
	List<RoleEty> selectUserUnhaveRoles(int userId);

	@Select("select * from role r where exists (select 1 from userrole where r.id = roleId and userId = #{value});")
	List<RoleEty> selectUserRoles(int userId);

}
