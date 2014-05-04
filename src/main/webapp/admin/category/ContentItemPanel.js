/*
 * comments
 */
Ext.namespace('com.mini.cms.admin.category.ContentItemPanel');
com.mini.cms.admin.category.ContentItemPanel=Ext.extend(Ext.Panel, {
	initComponent: function() {
		
		this.contentWin = new Ext.Window({
			title: '内容编辑',
	        layout:'fit',
	        width:900,
	    	height:520,
	        closeAction:'hide',
	        plain: true
	    });
		
		var contentComboStore = new Ext.data.Store({
			proxy: new Ext.data.HttpProxy({url: myApp.ctx + '/mini/cms/admin/category/CommonComboController/getContent.sdo'}),
			reader: new Ext.data.JsonReader({
				totalProperty: 'total',
				idProperty:'id',
				root: 'invdata',
				fields: [
					{name: 'id'},
					{name: 'name'}
				]
			})
		});
		contentComboStore.load();
		
		var editForm = new Ext.FormPanel({
			labelAlign: 'right',
			region: 'center',
			autoScroll:true, 
			labelWidth: 100,
			frame: true,
			xtype: 'fieldset',
			items: [
				{xtype: 'textfield',hidden: true,anchor: '100%',name:'id'},
				
				{xtype: 'textfield',name: 'name', fieldLabel: '名&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;称',anchor : '95%'},
				
				new Ext.form.ComboBox({
					fieldLabel: '所属项目',
					name: 'contentId',
					hiddenName: 'contentId',
					triggerAction: 'all', 
					forceSelection: true,
					editable: false,
					hideTrigger: false,
					anchor : '95%',
					mode: 'local',
					valueField: 'id',
					displayField: 'name',
					store: contentComboStore
				}),
				
				new Ext.form.ComboBox({
					fieldLabel: '类&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;型',
					name: 'itemType',
					hiddenName: 'itemType',
					anchor : '95%',
					triggerAction: 'all', 
					editable: false,
					mode: 'local',
					allowBlank : false,
					valueField: 'value',
					displayField: 'text',
					value: '1',
					store: new Ext.data.SimpleStore({
						fields: ['value', 'text'],
						data: [['1', 'HTML']]
					})
				}),
				{xtype: 'numberfield',name: 'itemOrder', fieldLabel: '排&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;序',anchor : '95%'},
				{xtype: 'textfield',name: 'remark', fieldLabel: '备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注',anchor : '95%'}
			],
			buttons: [
				{
					text: '保存'	,
					handler: function() {
						if(!editForm.getForm().isValid())
							return;
						
						editForm.form.doAction('submit', {
							url: '../mini/cms/admin/category/ContentItemController/save.sdo',
							method: 'post',
							waitTitle:'请等待',
							waitMsg: '正在提交...',
							params: '',
							success: function(form, action) {
								if(action.result.result == 'success') {
									Ext.MessageBox.alert('结果', '保存成功！');
									form.reset();
									grid.getStore().reload();
									editWin.hide();
								}
								else {
									var info = '' || action.result.info;
									Ext.MessageBox.show({
						        		title: '保存错误!',
					        		    msg: info,
					        		    buttons: Ext.Msg.OK,
					        		    icon: Ext.MessageBox.ERROR
						        	});
								}
							}
						});
					}
				},
				{
					text: '取消'	,
					handler: function() {
						editForm.form.reset();
						editWin.hide();
					}
				}
			]
		});
		
		var editWin = new Ext.Window({
			title: '编辑',
			modal: true,
			layout:'fit',
			width:500,
			height:220,
			closeAction:'hide',
			plain: true,
			layout: 'border',
			items: [editForm]
		});
	
		var searchForm = new Ext.FormPanel({
			frame: true,
			title: '查询',
			collapsible : true,
			collapsed: true,
			autoHeight:true,
			collapseMode:'mini',
			region: 'north',
			split: true,
			labelAlign: 'right',
			items: [
				{
					layout: 'column',
					items: [
						{
							columnWidth: .33, layout: 'form',
							items: [
								{xtype: 'textfield',name: 'name', fieldLabel: '名&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;称',anchor : '95%'}
							]
						},
						{
							columnWidth: .33, layout: 'form',
							items: [
								new Ext.form.ComboBox({
									fieldLabel: '所属项目',
									name: 'contentId',
									hiddenName: 'contentId',
									triggerAction: 'all', 
									forceSelection: true,
									editable: false,
									hideTrigger: false,
									anchor : '95%',
									mode: 'local',
									valueField: 'id',
									displayField: 'name',
									store: contentComboStore
								})
							]
						}
					]
				}
			],
			buttons: [
				new Ext.Button({
					text: '查询',
					width: 70,
					handler: function() {
						var fv = searchForm.getForm().getValues();
						ds.baseParams= fv;
						ds.load({params: {"extLimit.start":0, "extLimit.limit":20}});
					}
				}),
				new Ext.Button({
						text: '清空',
						width: 70,
						handler: function() {
							searchForm.form.reset();
							ds.baseParams= {};
							ds.load({params: {"extLimit.start":0, "extLimit.limit":20}});
						}
				})
			]
		});
		
		var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:true});
		var cm = new Ext.grid.ColumnModel([
			sm,
			new Ext.grid.RowNumberer(),
			{header:'ID', dataIndex:'id', sortable:true, width:50},
			{header:'名称', dataIndex:'name', sortable:true, width:400},
			{header:'内容项', dataIndex:'contentName', sortable:true, width:80},
			{header:'类型', dataIndex:'itemType', sortable:true, renderer: function(v){if(v==1) return 'HTML';}, width:50},
			{header:'排序', dataIndex:'itemOrder', sortable:true, width:120},
			{header:'创建日期', dataIndex:'createDate', sortable:true, width:120},
			{header:'备注', dataIndex:'remark', sortable:true}
		]);
		
		var ds = new Ext.data.Store({
			proxy: new Ext.data.HttpProxy({url: '../mini/cms/admin/category/ContentItemController/search.sdo'}),
			remoteSort: true,
			paramNames: {
				start : 'extLimit.start', 
			    limit : 'extLimit.limit', 
			    sort : 'extLimit.sort', 
			    dir : 'extLimit.dir'   
			},
			sortInfo: {
				field: 'createDate',
				direction: 'desc'
			},
			reader: new Ext.data.JsonReader({
				totalProperty: 'total',
				idProperty:'id',
				root: 'invdata',
				fields: [
					{name: 'id'},
					{name: 'name'},
					{name: 'contentId'},
					{name: 'contentName'},
					{name: 'itemType'},
					{name: 'itemOrder'},
					{name: 'remark'},
					{name: 'createDate'}
				]
			})
		});
		ds.load({params: {"extLimit.start":0, "extLimit.limit":20}});
		
		var grid = new Ext.grid.GridPanel({
			region: 'center',
			store: ds,
			colModel: cm,
			sm:sm,
			loadMask: true,
//			viewConfig: {
//				forceFit: true
//			},
			tbar: new Ext.Toolbar({
				buttons: [
					{
						text: '查询'	,
						iconCls: 'find',
						handler: function() {
							if(searchForm.collapsed)
								searchForm.expand();
							else
								searchForm.collapse();
						}
					},
					{
						text: '新增',
						iconCls: 'add',
						handler: function() {
							editForm.form.reset();
							editWin.show();
						}
					},
					{
						text: '修改'	,
						iconCls: 'edit',
						handler: function() {
							var rs = grid.getSelectionModel().getSelected();
							showInfo(rs.data.id);
						}
					},
					{
						text: '删除'	,
						iconCls: 'del',
						handler: function() {
							var rs = grid.getSelectionModel().getSelected();
							deleteResource(rs.data.id, rs.data.name);
						}
					},
					{
						text: '编辑内容'	,
						iconCls: 'edit',
						scope: this,
						handler: function() {
							var rs = grid.getSelectionModel().getSelected();
							this.showContentEditWin(rs.data.id, rs.data.name);
						}
					},
					{
						text: '查看'	,
						iconCls: 'edit',
						scope: this,
						handler: function() {
							var rs = grid.getSelectionModel().getSelected();
							this.showContentPreview(rs.data.id, rs.data.name);
						}
					}
				]
			}),
			bbar: new Ext.PagingToolbar({
				pageSize: 20,
				store: ds,
				displayInfo: true,
				displayMsg: '显示第{0}条到{1}条记录,一共{2}条',
				emptyMsg: '没有记录',
				items: [
					'-',
					{
						text: '导出(前台)'	,
						iconCls: 'export',
						handler: function() {
							var vExportContent = grid.getExcelXml();
							document.location = 'data:application/vnd.ms-excel;base64,' + Base64.encode(vExportContent);  
						}
					}
				]
			})
		});
		
		var contextMenu = new Ext.menu.Menu({
	        items: [
		        {
		            text: '修改',
		            iconCls: 'edit',
		            scope: this,
		            handler: function() {
		            	var rs = grid.getSelectionModel().getSelected();
		            	showInfo(rs.data.id);
		            }
		        },
		        {
		            text: '删除',
		            iconCls: 'del',
		            scope: this,
		            handler: function() {
		            	var rs = grid.getSelectionModel().getSelected();
		            	deleteResource(rs.data.id, rs.data.name);
		            }
		        }
			]
	    });
	    
	    grid.on('rowcontextmenu', function(grid, index, event) {
			event.stopEvent();
			grid.getSelectionModel().selectRow(index);
			contextMenu.showAt(event.getXY());
		});
		
		grid.addListener('rowdblclick', function(grid, rowindex, e) {
			var record = grid.getStore().getAt(rowindex);
			showInfo(record.data.id);
		});
		
		function showInfo(id) {
			editForm.load({
				url: '../mini/cms/admin/category/ContentItemController/getDetailInfo.sdo',
				params: {id: id},
				success:function(form,action){
				}
			});
			editWin.show();
		}
		
		function deleteResource(id, info) {
			Ext.MessageBox.confirm('提示', '确定删除  ' + info + ' ?', function(btn) {
				if(btn != 'yes') {
					return;
				}
				Ext.Ajax.request({
					method: 'post',
					url: '../mini/cms/admin/category/ContentItemController/delete.sdo',
					params: {id: id},
					success:function(resp){
						var obj=Ext.util.JSON.decode(resp.responseText);
						if(obj.result == 'success') {
							grid.getStore().reload();
							Ext.MessageBox.alert('提示', '删除成功！');
						}
						else {
							Ext.MessageBox.alert('报错了！！！', '删除失败！！！' + obj.info);
						}
					}
				});
			});
		}
		
		Ext.apply(this, {  
            iconCls: 'tabs',  
            autoScroll: false,  
            closable: true,
            layout: 'border',
            items:[
            	searchForm,grid
            ]
        });  
        //调用父类构造函数（必须）  
        com.mini.cms.admin.category.ContentItemPanel.superclass.initComponent.apply(this, arguments);
	},
	
	initMethod: function() {
	},
	
	showContentEditWin: function(id, name) {
		var url = myApp.ctx + '/mini/cms/admin/content/ContentConroller/showContentInfo.sdo?itemId='+id;
		var item = {
    		title: '内容编辑--' + name,
    		id: 'ContentDetail_' + id,
    		closable: true,
			layout: 'fit',
			html: '<iframe scrolling="auto" frameborder="0" width="100%" height="100%" src="' + url + '"></iframe>'
    	};
    	parent.addTab(item);
	},
	
	showContentPreview: function(id, name) {
		var url = myApp.ctx + '/mini/cms/admin/content/ContentConroller/preview.sdo?itemId='+id;
		var item = {
    		title: '预览--' + name,
    		id: 'ContentDetailPreview_' + id,
    		closable: true,
			layout: 'fit',
			html: '<iframe scrolling="auto" frameborder="0" width="100%" height="100%" src="' + url + '"></iframe>'
    	};
    	parent.addTab(item);
	} 
});
