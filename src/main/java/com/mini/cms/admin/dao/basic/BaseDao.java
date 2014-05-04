package com.mini.cms.admin.dao.basic;

import java.util.List;

/**
 * DAO基础接口
 * @author yangHanguang
 *
 */
public interface BaseDao<T> {
	
	/**
	 * 插入
	 * @param po
	 */
	
	public Integer insert(T t);
	
	/**
	 * @param obj
	 */
	public Integer updateById(T t);
	
	/**
	 * @param id
	 */
	public void deleteById(Integer id);
	
	/**
	 * @param obj
	 */
	public T selectById(Integer id);
	
	/**
	 * 根据实体对象查询
	 * @param po
	 * @return
	 */
	public List<T> selectByEntity(T t);
	
	/**
	 * @param object
	 * @return
	 */
	public List<T> selectByLimit(T t);
	
	
	/**
	 * 为分页查询出记录总数
	 * @param object
	 * @return
	 */
	public Integer selectLimitCount(T t);
}
