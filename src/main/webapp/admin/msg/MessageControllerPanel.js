/*
 * comments
 */
Ext.namespace('com.ms.controller.MessageControllerPanel');
com.ms.controller.MessageControllerPanel=Ext.extend(Ext.Panel, {
	initComponent: function() {
		var editForm = new Ext.FormPanel({
			labelAlign: 'right',
			region: 'center',
			autoScroll:true, 
			labelWidth: 100,
			frame: true,
			xtype: 'fieldset',
			items: [
				{xtype: 'textfield',hidden: true,anchor: '100%',name:'id'},
				{xtype: 'textfield',name: 'name', fieldLabel: '名称',anchor : '95%', allowBlank : false},
				{xtype: 'textfield',name: 'phone', fieldLabel: '电话',anchor : '95%', allowBlank : false},
				{xtype: 'textfield',name: 'email', fieldLabel: '邮箱',anchor : '95%'},
				{xtype: 'textarea',name: 'message', fieldLabel: '内容',anchor : '95%'},
				{xtype: 'textfield',name: 'ip', fieldLabel: 'IP',anchor : '95%'},
				new Ext.form.ComboBox({
					fieldLabel: '回访',
					name: 'haveReBack',
					hiddenName: 'haveReBack',
					anchor : '95%',
					triggerAction: 'all', 
					editable: false,
					mode: 'local',
					allowBlank : false,
					valueField: 'value',
					displayField: 'text',
					store: new Ext.data.SimpleStore({
						fields: ['value', 'text'],
						data: [['是', '是'],['否', '否']]
					})
				})
			],
			buttons: [
				{
					text: '保存'	,
					handler: function() {
						if(!editForm.getForm().isValid())
							return;
						
						editForm.form.doAction('submit', {
							url: myApp.ctx + '/mini/cms/admin/msg/MessageController/save.sdo',
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
			height:300,
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
								{xtype: 'textfield',name: 'name', fieldLabel: '名称',anchor : '95%'}
							]
						},
						{
							columnWidth: .33, layout: 'form',
							items: [
								{xtype: 'textfield',name: 'phone', fieldLabel: '电话',anchor : '95%'}
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
			{header:'名称', dataIndex:'name', sortable:true},
			{header:'电话', dataIndex:'phone', sortable:true},
			{header:'邮箱', dataIndex:'email', sortable:true},
			{header:'内容', dataIndex:'message', sortable:true},
			{header:'IP', dataIndex:'ip', sortable:true},
			{header:'时间', dataIndex:'createDate', sortable:true},
			{header:'回访', dataIndex:'haveReBack', sortable:true}
		]);
		
		var ds = new Ext.data.Store({
			proxy: new Ext.data.HttpProxy({url: myApp.ctx + '/mini/cms/admin/msg/MessageController/search.sdo'}),
			remoteSort: true,
			sortInfo: {
			    field: 'createDate',
			    direction: 'DESC' // or 'DESC' (case sensitive for local sorting)
			},
			paramNames: {
				start : 'extLimit.start', 
			    limit : 'extLimit.limit', 
			    sort : 'extLimit.sort', 
			    dir : 'extLimit.dir'   
			},
			reader: new Ext.data.JsonReader({
				totalProperty: 'total',
				idProperty:'id',
				root: 'invdata',
				fields: [
					{name: 'id'},
					{name: 'name'},
					{name: 'phone'},
					{name: 'email'},
					{name: 'message'},
					{name: 'ip'},
					{name: 'createDate'},
					{name: 'haveReBack'}
				]
			})
		});
		ds.load({params: {"extLimit.start":0, "extLimit.limit":25}});
		
		var grid = new Ext.grid.GridPanel({
			region: 'center',
			store: ds,
			colModel: cm,
			sm:sm,
			viewConfig: {
				forceFit: true
			},
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
//					{
//						text: '新增',
//						iconCls: 'add',
//						handler: function() {
//							editForm.form.reset();
//							editWin.show();
//						}
//					},
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
					}
				]
			}),
			bbar: new Ext.PagingToolbar({
				pageSize: 25,
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
		            	deleteResource(rs.data.id, rs.data.menuName);
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
				url: myApp.ctx + '/mini/cms/admin/msg/MessageController/getDetailInfo.sdo',
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
					url: myApp.ctx + '/mini/cms/admin/msg/MessageController/delete.sdo',
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
        com.ms.controller.MessageControllerPanel.superclass.initComponent.apply(this, arguments);
	},
	
	initMethod: function() {
	}
});
