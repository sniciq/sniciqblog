package org.mybatis.plug.anno;

import java.lang.reflect.Field;

/**
 * 自动生成基本的: <br>
 * insert,
 * updateById,
 * deleteById,
 * selectByEntity,
 * selectById
 * selectByLimit
 * selectLimitCount
 *
 */
public class AutoCreateBaseSql {
	
	public static void main(String[] as) {
		try {
			Class<?> c = Class.forName("org.mybatis.plug.anno.test.TestEty");
			if(c.isAnnotationPresent(Table.class)) {
				Table table = c.getAnnotation(Table.class);
				System.out.println(table.tableName());
				
				Field[] fs = c.getDeclaredFields();
				for(Field f : fs) {
					if(f.isAnnotationPresent(Column.class)) {
						Column cl = f.getAnnotation(Column.class);
						String fn = f.getName();
						System.out.println(fn + " " + cl.columnName());
					}					
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public String createInsertSql(String tableName, String parameterType, 
			String[] fieldNames, String dbcolumns) {
		
//		<insert id="insert" parameterType="com.mini.cms.admin.dao.entity.content.ContentDetailEty">
//			insert into contentdetail(
//				id,itemId,detail)
//			values (
//				#{id},#{itemId},#{detail}
//			)
//		</insert>
		
		return "";
	}

}
