package com.mini.cms.admin.dao.basic;

import java.io.File;
import java.io.FileInputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.binding.MapperRegistry;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.MappedStatement.Builder;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.core.io.Resource;

import com.mini.cms.admin.dao.entity.category.ContentItemEty;
import com.mini.cms.admin.dao.mapper.test.TestDao;

public class BaseSql {
	
	private SqlSessionFactory sqlSessionFactory;
	
	private Resource configLocation;
	
	public void test() {
		try {
			Configuration config = sqlSessionFactory.getConfiguration();
			MappedStatement ms = config.getMappedStatement("com.mini.cms.admin.dao.mapper.category.ContentItemDao.selectById");
			BoundSql bs = ms.getBoundSql(new Object[]{1});
			System.out.println(bs.getSql());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void test2() {
		try {
			Configuration config = sqlSessionFactory.getConfiguration();
			MappedStatement ms = config.getMappedStatement("com.mini.cms.admin.dao.mapper.test.TestDao.selectById", false);
			BoundSql bs = ms.getBoundSql(new Object[]{1});
			System.out.println(bs.getSql());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void build_code() {
		try {
			Configuration allConfig = sqlSessionFactory.getConfiguration();
			System.out.println(allConfig.getMappedStatements().size());
			SqlSource sqlSource = new StaticSqlSource(allConfig, "select * from contentitem where id=40;");
			List<ResultMap> resultMaps = new ArrayList<ResultMap>();
			ResultMap rm = new org.apache.ibatis.mapping.ResultMap.Builder(allConfig, "com.mini.cms.admin.dao.mapper.test.TestDao.selectById", ContentItemEty.class, new ArrayList<ResultMapping>(), true).build();
			resultMaps.add(rm);
			MappedStatement ms = new Builder(allConfig, "com.mini.cms.admin.dao.mapper.test.TestDao.selectById", sqlSource, SqlCommandType.SELECT).resultMaps(resultMaps).build();
			allConfig.addMapper(TestDao.class);
			allConfig.addMappedStatement(ms);
			System.out.println(allConfig.getMappedStatements().size());
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void build() {
		try {
			Configuration allConfig = sqlSessionFactory.getConfiguration();
			XMLConfigBuilder xmlConfigBuilder = null;
			xmlConfigBuilder = new XMLConfigBuilder(this.configLocation.getInputStream(), null, null);
			Configuration configuration = xmlConfigBuilder.getConfiguration();
		    xmlConfigBuilder.parse();
		    new SqlSessionFactoryBuilder().build(configuration);
			for(MappedStatement ms : configuration.getMappedStatements()) {
				if(allConfig.getMappedStatementNames().contains(ms.getId()))
					continue;
				
				allConfig.addMappedStatement(ms);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getSql() {
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
		sb.append("<!DOCTYPE mapper    PUBLIC \"-//ibatis.apache.org//DTD Mapper 3.0//EN\"    \"http://ibatis.apache.org/dtd/ibatis-3-mapper.dtd\">");
		sb.append("<mapper namespace=\"com.mini.cms.admin.dao.mapper.test.TestDao\">");
		sb.append("<select id=\"selectById\" parameterType=\"int\" resultType=\"com.mini.cms.admin.dao.entity.category.ContentItemEty\">");
		sb.append("select * from contentitem where id=#{id}");
		sb.append("</select>");
		sb.append("</mapper>");
		return sb.toString();
	}
	

	public SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}

	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}

	public void setConfigLocation(Resource configLocation) {
	    this.configLocation = configLocation;
	}
}
