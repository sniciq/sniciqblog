var markup = [
  	'<div>',
  		'<div class="side_header">',
     			'<div class="caption">${name}</div>',
     			'<div class="extend"><a charset="106302-52086-999-2" href="newslist.html?id=${id}">更多</a></div>',
     	'</div>',
     	'<div>',
 			'<table class="table">',
 				'{{each items}}',
 					'<tr>',
 						'<td><a  href="news_detail.html?id=${$value.id}">${$value.name}</a></td>',
 						//'<td><a href="#" onclick="loadContent(${$value.id})">${$value.name}</a></td>',
 						'<td style="width:70px;">${$value.createDate}</td>',
 					'</tr>',
 				'{{/each}}',
 			'</table>',
 		'</div>',
 	'</div>'	
];

function createDefauleSide() {
  	createSide("9,7,8,1");
}

function createSide(sides) {
  	$j.post('../front/cc/getleftSide.sdo?cids='+sides, {}, function(data) {
  		if(!data.sides || data.sides.length == 0)
  			return;
  		$j("#side").html('');
  		$j.template("leftSideTemplate", markup.join(''));
  		$j.tmpl("leftSideTemplate", data.sides).appendTo("#side");
  	}, 'json');
}

function getMenuId(id) {
	if(id==9) return 1;
	else if(id==7) return 2;
	else if(id==8) return 3;
	else if(id==1) return 4;
}

//创建导航条
function createTopBar() {
	var topHtml = [
		'<li><a href="index.html"><span>首页</span></a></li>',
		'<li><a href="newslist.html?id=9"><span>我的博客</span></a></li>',
		'<li><a href="newslist.html?id=7"><span>产品中心</span></a></li>',
		'<li><a href="newslist.html?id=8"><span>解决方案</span></a></li>',
		'<li><a href="newslist.html?id=1"><span>科技动态</span></a></li>'
    ];
	
	$j("#menu").html(topHtml.join(''));
}

function createPageFooter() {
	$j("#pagefooter").html('<div style="text-align: center;">无我仙人的个人网站<br/>邮箱：sniciq@gmail.com<br/>地址： 北京中关村<br/></div>');
}

jQuery(function() {
	createTopBar();
	createPageFooter();
	jQuery("#menu>li").removeClass("active");
	
	if(!paramObj.id) {
		jQuery(jQuery("#menu>li")[0]).addClass("active");
	}
	else {
		jQuery(jQuery("#menu>li")[getMenuId(paramObj.id)]).addClass("active");
	}
	
	jQuery("#webName").html('无我仙人的个人网站');
});

