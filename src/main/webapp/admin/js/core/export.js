function doExport(grid, url, otherInfo) {
	var cms = grid.getColumnModel();
	var strColoumnNames = "";
	var strColoumnIndexs = "";
	
	var sortInfo = grid.getStore().sortInfo;
	var sort = '';
	var dir = '';
	if(sortInfo) {
		sort = sortInfo.field || '';
		dir = sortInfo.direction || '';
	}
	
	for(var i = 0; i < cms.getColumnCount(); i++) {
		if(cms.getColumnHeader(i) == '' || cms.getColumnHeader(i) == '操作')
			continue;
			
		if (!cms.isHidden(i)) {
			strColoumnIndexs += cms.getDataIndex(i) + ',';
			strColoumnNames += cms.getColumnHeader(i) + ',';
		}
	}
	
	window.location.href = url + '?exp_name=data.xls&exp_column_names=' + encodeURI(encodeURI(strColoumnNames)) 
		+ '&exp_column_indexs=' + strColoumnIndexs
		+ '&sort=' + sort + '&dir=' + dir
		+ '&' + otherInfo;
}
