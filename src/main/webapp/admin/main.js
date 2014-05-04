Ext.BLANK_IMAGE_URL = '../ExtJS/resources/images/default/s.gif';

Ext.onReady(function() {
	Ext.QuickTips.init();
	
	Ext.Ajax.defaultHeaders = {'Request-By': 'Ext'};
	Ext.Ajax.timeout = 60000;//请求超时时间改为60秒
	
	myApp = new yk.stat.office.app();
	myApp.ctx = globalCtx;
	myApp.on('loadUserInfoOver', function(obj) {
		myApp.createUserMenu();
		myApp.addIndexPanel();
		myApp.showHelp();
	});
	myApp.initTheme('blue');
	myApp.loadUserInfo();
	window.addTab = function(item) {
		myApp.addTab(item);
	}
	
	window.findTab =  function(id) {
		return myApp.findTab(id);
	},
	
	window.addClassTab = function(id, moduleStr, jsFiles, attr, fv) {
		myApp.addModuleTab(id, moduleStr, jsFiles, attr, fv);
	}
	
});
