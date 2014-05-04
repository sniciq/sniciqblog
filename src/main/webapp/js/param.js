urlinfo = window.location.href;  //获取当前页面的url
len = urlinfo.length;//获取url的长度
offset = urlinfo.indexOf("?");//设置参数字符串开始的位置
var allParams = urlinfo.substr(offset+1,len)//取出参数字符串 这里会获得类似“id=1”这样的字符串

var paramArr = allParams.split("&");
var paramObj = {};
for(var i = 0; i < paramArr.length; i++) {
	var aParam = paramArr[i];
	var aParamArr = aParam.split("=");
	paramObj[aParamArr[0]] = aParamArr[1];
}