/**
 * JS动态加载类
 */
 
Ext.namespace('yk.stat.office.JSLoader'); 
yk.stat.office.JSLoader = function() {
	this.JsToLoad = undefined;
	this.JsLoadCallBack = undefined;
	
	var JSLoader = this;
	
	this.loadJs = function(js, callback){
	　　JSLoader.JsToLoad = js;
	　　JSLoader.JsLoadCallBack = callback;
	　　JSLoader._loadJs();
	}
	
	this._loadJs = function(){
		var js = JSLoader.JsToLoad;
		var callback = JSLoader.JsLoadCallBack;
		　　　
		if (Ext.type(JSLoader.JsToLoad) != 'string') {
			if (JSLoader.JsToLoad.length == 1) {
				js = JSLoader.JsToLoad[0];
				callback = JSLoader.JsLoadCallBack;
			}
			else {
				js = JSLoader.JsToLoad.shift();
				callback = JSLoader._loadJs;
			}
		}
	　　　
		Ext.Ajax.request({
		    //url: '../model/ModelResourceAction/getJSModelResource.action',
		    url: '../'+js,
		    params: {file: js},
		    success: JSLoader._onLoadJs,
		    method: 'GET',
		    scope: callback
		});
	}
	
	this._onLoadJs = function(response){
		eval(response.responseText);
		this();
	}
}