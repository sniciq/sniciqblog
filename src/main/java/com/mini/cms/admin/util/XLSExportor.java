package com.mini.cms.admin.util;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import com.mini.cms.admin.dao.basic.BaseDao;
import com.mini.cms.admin.dao.basic.BaseEntity;


@SuppressWarnings("rawtypes")
public class XLSExportor {

	private static Logger logger = Logger.getLogger(XLSExportor.class);
	
	public static void doExport(HttpServletRequest request, HttpServletResponse response, BaseDao baseDao, String countMethod, String dataMethod, BaseEntity paramObj, String title, String fileName,
			ExtLimit limit) throws Exception {
		String[] exp_column_names = limit.getExp_column_names().split(",");
		String[] exp_column_indexs = limit.getExp_column_indexs().split(",");
		doExport(request, response, baseDao, countMethod, dataMethod, paramObj, title, fileName, exp_column_names, exp_column_indexs);
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param baseDao
	 * @param countMethod 记录数方法名称
	 * @param dataMethod 分页查询方法名称
	 * @param paramObj
	 * @param title
	 * @param exp_column_names
	 * @param exp_column_indexs
	 */
	public static void doExport(HttpServletRequest request, HttpServletResponse response, BaseDao baseDao, String countMethod, String dataMethod, BaseEntity paramObj, String title, String fileName,
			String[] exp_column_names, String[] exp_column_indexs) throws Exception {
		try {
			if (exp_column_names == null || exp_column_names.length == 0 || exp_column_indexs == null || exp_column_indexs.length == 0 || exp_column_indexs.length != exp_column_names.length)
				return;

			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet(title);
			createHeader(sheet, wb.createCellStyle(), exp_column_names);
			
			int count = (Integer) EntityReflect.invokeMethod(baseDao, countMethod, paramObj);
			int startOffset = 0;
			int pageSize = 200;
			if(paramObj.getExtLimit() == null) {
				ExtLimit limit = new ExtLimit();
				paramObj.setExtLimit(limit);
			}
			paramObj.getExtLimit().setLimit(pageSize);

			int rowIndex = 1;
			// 生成数据
			while (startOffset < count) {
				paramObj.getExtLimit().setStart(startOffset);
				List dataList = (List) EntityReflect.invokeMethod(baseDao, dataMethod, paramObj);
				addDataToSheet(sheet, dataList, exp_column_indexs, rowIndex);
				rowIndex += dataList.size();
				startOffset += dataList.size();
				
			}
			exportWorkbook(response, wb, fileName);
		} catch (Exception e) {
			logger.error("导出数据错误！", e);
			throw e;
		}
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param baseDao
	 * @param paramObj
	 * @param title
	 * @param exp_column_names 指定列头
	 * @param exp_column_indexs 列头对应的属性名称
	 */
	public static void doExport(HttpServletRequest request, HttpServletResponse response, BaseDao baseDao, BaseEntity paramObj, String title, String fileEnName, String[] exp_column_names,
			String[] exp_column_indexs) throws Exception {
		try {
			if (exp_column_names == null || exp_column_names.length == 0 || exp_column_indexs == null || exp_column_indexs.length == 0 || exp_column_indexs.length != exp_column_names.length)
				return;

			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet(title);
			createHeader(sheet, wb.createCellStyle(), exp_column_names);

			int count = baseDao.selectLimitCount(paramObj);
			int startOffset = 0;
			int pageSize = 200;
			paramObj.getExtLimit().setLimit(pageSize);

			int rowIndex = 1;
			// 生成数据
			while (startOffset < count) {
				paramObj.getExtLimit().setStart(startOffset);
				List dataList = baseDao.selectByLimit(paramObj);
				addDataToSheet(sheet, dataList, exp_column_indexs, rowIndex);
				rowIndex += dataList.size();
				startOffset += dataList.size();
			}
			exportWorkbook(response, wb, fileEnName);
		} catch (Exception e) {
			logger.error("导出数据错误！", e);
			throw e;
		}
	}

	/**
	 * 导出大数据<br>
	 * 该方法导出时，将通过分页查询的方式多次查询数据库，每次查询页大小默认为200<br>
	 * 所以，在基础DAO中，必须实现好selectLimitCount和selectByLimit两个方法，否则无法正常导出！
	 * 
	 * @param request
	 * @param response
	 * @param service BaseServiceIface
	 * @param paramObj BaseEntity
	 * @param title
	 * @param limit
	 */
	public static void doExport(HttpServletRequest request, HttpServletResponse response, BaseDao baseDao, BaseEntity paramObj, String title, ExtLimit limit) throws Exception {
		try {
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet(title);

			String[] exp_column_names = limit.getExp_column_names().split(",");
			String[] exp_column_indexs = limit.getExp_column_indexs().split(",");

			createHeader(sheet, wb.createCellStyle(), exp_column_names);

			int count = baseDao.selectLimitCount(paramObj);
			int startOffset = 0;
			int pageSize = 200;
			limit.setLimit(pageSize);

			int rowIndex = 1;
			// 生成数据
			while (startOffset < count) {
				limit.setStart(startOffset);
				paramObj.setExtLimit(limit);
				List dataList = baseDao.selectByLimit(paramObj);
				addDataToSheet(sheet, dataList, exp_column_indexs, rowIndex);
				rowIndex += dataList.size();
				startOffset += dataList.size();
			}

			exportWorkbook(response, wb, limit.getExp_name());
		} catch (Exception e) {
			logger.error("导出数据错误！", e);
			throw e;
		}
	}

	/**
	 * 导出数据<br>
	 * 该方法适用于小数据量导出，如果数据量超过2000条，不宜使用，否则会造成内存溢出
	 * 
	 * @param request
	 * @param response
	 * @param dataList 导出的数据列表
	 * @param title 标题
	 * @param limit 导出的表头以及数据列信息
	 */
	public static void doExport(HttpServletRequest request, HttpServletResponse response, List dataList, String title, ExtLimit limit) throws Exception {
		try {
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet(title);

			String[] exp_column_names = limit.getExp_column_names().split(",");
			String[] exp_column_indexs = limit.getExp_column_indexs().split(",");

			createHeader(sheet, wb.createCellStyle(), exp_column_names);
			// 生成数据
			addDataToSheet(sheet, dataList, exp_column_indexs, 1);
			exportWorkbook(response, wb, limit.getExp_name());
		} catch (Exception e) {
			logger.error("导出数据错误！", e);
			throw e;
		}
	}

	private static HSSFRow createHeader(HSSFSheet sheet, HSSFCellStyle style, String[] exp_column_names) {
		HSSFRow row = sheet.createRow(0);
		style.setFillForegroundColor(HSSFColor.LIME.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		for (int j = 0; j < exp_column_names.length; j++) {
			HSSFCell cell = row.createCell((short)j);
			cell.setCellStyle(style);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(exp_column_names[j]);
		}
		return row;
	}

	private static int addDataToSheet(HSSFSheet sheet, List dataList, String[] exp_column_indexs, int startRowIndex) {
		for (int i = 0; i < dataList.size(); i++) {
			HSSFRow row = sheet.createRow(startRowIndex);

			Object dataObj = dataList.get(i);

			for (int j = 0; j < exp_column_indexs.length; j++) {
				Object cellValue = EntityReflect.getObjectProperty(dataObj, exp_column_indexs[j]);
				HSSFCell cell = row.createCell((short)j);
				// cell.setEncoding(HSSFCell.ENCODING_UTF_16);
				if (cellValue == null) {
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue("");
				} else {
					if (cellValue instanceof java.util.Date) {
						cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						cell.setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(cellValue));
					} 
					else if(cellValue instanceof java.lang.Double) {
						cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						cell.setCellValue((Double)cellValue);
					}
					else if(cellValue instanceof java.lang.Integer) {
						cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						cell.setCellValue((Integer)cellValue);
					}
					else if(cellValue instanceof java.lang.Float) {
						cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						cell.setCellValue((Float)cellValue);
					}
					else {
						cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						cell.setCellValue(cellValue.toString());
					}
				}
			}

			startRowIndex++;
		}
		return startRowIndex;
	}

	private static void exportWorkbook(HttpServletResponse response, HSSFWorkbook wb, String fileName) throws Exception {
		if(fileName == null) {
			fileName = "数据表";
		}
		
		response.setHeader("Content-Disposition", "attachment;filename=\"" + new String(fileName.getBytes("GBK"),"ISO8859_1") + "\"");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("application/vnd.ms-excel" + ";charset=UTF-8");
		wb.write(response.getOutputStream());
		response.getOutputStream().flush();
		response.getOutputStream().close();
	}
}
