package com.mini.cms.admin.dao.mapper.basic;

import java.util.List;
import java.util.Map;

import com.mini.cms.admin.dao.basic.BaseDao;
import com.mini.cms.admin.dao.entity.basic.ResourcesEty;
import com.mini.cms.admin.dao.entity.basic.RoleResourceEty;

public interface RoleResourceDao extends BaseDao<RoleResourceEty> {

	void deleteByIds(Map<String, Object> pMap);

	List<ResourcesEty> selectRoleResources(RoleResourceEty ety);

}
