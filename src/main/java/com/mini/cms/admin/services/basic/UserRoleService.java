package com.mini.cms.admin.services.basic;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mini.cms.admin.dao.entity.basic.UserRoleEty;
import com.mini.cms.admin.dao.mapper.basic.UserRoleDao;

@Service(value = "UserRoleService")
@Scope(value = "singleton")
public class UserRoleService {

	@Autowired
	private UserRoleDao userRoleDao;
	
	/**
	 * 虽然该方法使用了使用的处理，但是要注意数据库引擎，MyISAM不支持事务处理！
	 * @param userIdArr
	 * @param roleIds
	 */
	@Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor=java.lang.Exception.class)
	public void saveUserRole(String[] userIdArr, String[] roleIds) {
		String userIds = StringUtils.join(userIdArr, ",");
		userRoleDao.deleteByUserIds(userIds);
		
		for(String userId: userIdArr) {
			for(String roleId : roleIds) {
				UserRoleEty ety = new UserRoleEty();
				ety.setUserId(Integer.parseInt(userId));
				ety.setRoleId(Integer.parseInt(roleId));
				userRoleDao.insert(ety);
			}
		}
	}

}
