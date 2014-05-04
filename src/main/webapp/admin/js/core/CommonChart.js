/**
 * 通用图表类
 */
 
Ext.namespace('yk.stat.office.CommonChart'); 
yk.stat.office.CommonChart = function() {
};

Ext.extend(yk.stat.office.CommonChart, Ext.util.Observable, {
	
	/**
	 * 饼图, json数据
	 * @param {} title 表头名称
	 * @param {} data 数据：json数据,如: [{name:'页面',value:25},{name:'种子',value:25},...]
	 * @param {} renderPanel Ext.Container对象
	 */
	drawPieChart_JSONData: function(title, data, renderPanel) {
		var chartData = [];
		var otherValue = 0;//值小于3%的都算到其它中
		for(var i = 0; i < data.length; i++) {
			if(data[i].value < 3.0 || data[i].name == '其它') {
				otherValue += data[i].value;
			}
			else {
				chartData.push([data[i].name, data[i].value]);
			}
			
			otherValue = parseFloat(otherValue.toFixed(2));
		}
		
		if(otherValue > 0)
			chartData.push(['其它', otherValue]);
		this.drawPieChart(title,chartData,renderPanel);
	},
	
	/**
	 * 饼图
	 * @param {} title 表头名称
	 * @param {} data 数据：数组形数据,如: [['页面', 25],['种子', 25], ..]
	 * @param {} renderPanel Ext.Container对象
	 */
	drawPieChart: function(title, data, renderPanel) {
		new Highcharts.Chart({
			chart: {
				renderTo: renderPanel.el.dom,
				height: renderPanel.getHeight(),
				plotBackgroundColor: null,
				plotBorderWidth: null,
				plotShadow: false,
				zoomType: 'xy'
			},
			title: {
				text: title
			},
			tooltip: {
				formatter: function() {
					return '<b>'+ this.point.name +'</b>: '+ this.y +' %';
				}
			},
			plotOptions: {
				pie: {
					allowPointSelect: true,
					cursor: 'pointer',
					showInLegend: true,
					dataLabels: {
						enabled: true,
						color: '#000000',
						connectorColor: '#000000',
						connectorWidth: 1,
						distance: 30,
						softConnector: false,
						formatter: function() {
							return '<b>'+ this.point.name +'</b>:<br> '+ this.y +' %';
						}
					}
				}
			},
		    series: [
		    	{
					type: 'pie',
					name: 'Browser share',
					data: data
				}
		    ],
			credits: {
				text:"优酷网",
				href:"#"
			},
			exporting:{
			    filename:'class-booking-chart',
			    url:'../util/HighChartExportAction/doExport.action',
			    buttons: {
			    	exportButton: {
			    		menuItems: [
				    		{
				    			text: '下载  JPG 格式',
			                    onclick: function() {
			                        this.exportChart({type: 'image/jpeg'});
			                    }
			                    
			                }, 
			                {
			                	text: '下载  PNG 格式',
			                    onclick: function() {
			                        this.exportChart({});
			                    }
			                },
			                null, 
			                null
		                ]
			    	}
			    }
			}
		});
    },
    
    /**
     * 画柱状图
     * @param {} contentPanel
     * @param {} title
     * @param {} yAxisName
     * @param {} categoriesData 名称数组
     * @param {} chartData  值数组
     */
    drawColumnChart: function(contentPanel, title, yAxisName, categoriesData, chartData) {
		chart = new Highcharts.Chart({
			chart: {
				renderTo: contentPanel.el.dom,
				plotBackgroundColor: null,
				plotBorderWidth: null,
				height: contentPanel.getHeight(),
				plotShadow: false
			},
			title: {
				text: title
			},
			tooltip: {
				formatter: function() {
					return '<b>季度：'+ this.x + '</b><br><b>金额: ' + rmbMoneyRender(this.y) + '</b>';
				}
			},
			xAxis: {
				categories: categoriesData
			},
			yAxis: {
				min: 0,
				title: {
					text: yAxisName
				},
				labels: {
		            formatter: function() {
		                return rmbMoneyRender(this.value);
		            }
		        }
			},
		    series: [
		    	{
					type: 'column',
					name: yAxisName,
					data: chartData
				},
				{
			    	type: 'line',
			    	name: yAxisName,
					data: chartData
			    }
		    ],
			credits: {
				text:"优酷网",
				href:"#"
			},
			exporting:{
			    filename:'class-booking-chart',
			    url:'../util/HighChartExportAction/doExport.action',
			    buttons: {
			    	exportButton: {
			    		menuItems: [
				    		{
				    			text: '下载  JPG 格式',
			                    onclick: function() {
			                        this.exportChart({type: 'image/jpeg'});
			                    }
			                    
			                }, 
			                {
			                	text: '下载  PNG 格式',
			                    onclick: function() {
			                        this.exportChart({});
			                    }
			                },
			                null, 
			                null
		                ]
			    	}
			    }
			}
		});
	}
});