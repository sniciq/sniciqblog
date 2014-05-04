/*
 * comments
 */
Ext.namespace('com.mini.cms.admin.basic.UserPanel');
com.mini.cms.admin.basic.UserPanel=Ext.extend(Ext.Panel, {
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
				{xtype: 'textfield',name: 'userName', fieldLabel: '用户名',anchor : '95%', allowBlank: false},
				{xtype: 'textfield',name: 'password', fieldLabel: '密码',anchor : '95%', allowBlank: false},
				{xtype: 'textfield',name: 'realName', fieldLabel: '真实姓名',anchor : '95%', allowBlank: false},
				{xtype: 'textfield',name: 'email', fieldLabel: '邮箱',anchor : '95%'},
				new Ext.form.ComboBox({
					fieldLabel: '性别',
				    triggerAction: 'all',
				    mode: 'local',
				    anchor : '95%',
				    hiddenName: 'sex',
				    value: '男',
				    allowBlank: false,
				    store: new Ext.data.ArrayStore({
				        fields: ['value','text'],
				        data: [['1', '男'], ['0', '女']]
				    }),
				    valueField: 'value',
				    displayField: 'text'
				}),
				{xtype: 'datefield',format:'Y-m-d',name: 'birthday', fieldLabel: '出生日期',anchor : '95%'}
			],
			buttons: [
				{
					text: '保存'	,
					handler: function() {
						if(!editForm.getForm().isValid())
							return;
						
						editForm.form.doAction('submit', {
							url: myApp.ctx + '/mini/cms/admin/basic/UserController/save.sdo',
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
			height:290,
			closeAction:'hide',
			plain: true,
			layout: 'border',
			items: [editForm]
		});
		
		var roleReader = new Ext.data.JsonReader({
			totalProperty: 'total',
			idProperty:'id',
			root: 'invdata',
			fields: [
				{name: 'id'},
				{name: 'roleName'},
				{name: 'describle'}
			]
		});
		
		var rolesDS = new Ext.data.Store({
			proxy: new Ext.data.HttpProxy({url: myApp.ctx + '/mini/cms/admin/basic/UserRoleController/getUserUnhaveRoles.sdo'}),
			remoteSort: true,
			paramNames: {
				start : 'extLimit.start', 
			    limit : 'extLimit.limit', 
			    sort : 'extLimit.sort', 
			    dir : 'extLimit.dir'   
			},
			reader: roleReader
		});
		var rolesGrid = new Ext.grid.GridPanel({
			store: rolesDS,
			hideHeaders: true,
			colModel: new Ext.grid.ColumnModel([
				{header:'角色', dataIndex:'roleName', sortable:true,menuDisabled: true}
			]),
			sm:sm,
			viewConfig: {
				forceFit: true
			}
		});
		
		var userRolesDS = new Ext.data.Store({
			proxy: new Ext.data.HttpProxy({url: myApp.ctx + '/mini/cms/admin/basic/UserRoleController/getUserRoles.sdo'}),
			remoteSort: true,
			paramNames: {
				start : 'extLimit.start', 
			    limit : 'extLimit.limit', 
			    sort : 'extLimit.sort', 
			    dir : 'extLimit.dir'   
			},
			reader: roleReader
		});
		var userRolesGrid = new Ext.grid.GridPanel({
			store: userRolesDS,
			hideHeaders: true,
			colModel: new Ext.grid.ColumnModel([
				{header:'角色', dataIndex:'roleName', sortable:true,menuDisabled: true}
			]),
			sm:sm,
			viewConfig: {
				forceFit: true
			}
		});
		
		var userRoleWin = new Ext.Window({
			title: '编辑用户角色',
			modal: true,
			layout:'fit',
			width:500,
			height:320,
			closeAction:'hide',
			plain: true,
			layout: 'hbox',
			layoutConfig: {
                padding:'1',
                align:'stretch'
            },
            defaults:{margins:'0 5 0 0'},
			items: [
				{
					title: '可选角色',
					flex: 5,
					layout: 'fit',
					items: [rolesGrid]
				},
				{
					width: 40,
					layout: {
						type:'vbox',
						padding:'5',
						pack:'center',
						align:'center'
					},
					defaults:{margins:'0 0 10 0'},
					items:[
						{
						    xtype:'button',
						    width: 30,
							text: ' >> ',
							handler: function(){
				            	var rs = rolesGrid.getSelectionModel().getSelections();
				            	userRolesDS.add(rs);
				            	rolesDS.remove(rs);
				            }
						},
						{
						    xtype:'button',
						    width: 30,
							text: ' << ',
							handler: function(){
				            	var rs = userRolesGrid.getSelectionModel().getSelections();
				            	rolesDS.add(rs);
				            	userRolesDS.remove(rs);
				            }
						}
					]
				},
				{
					title: '已有角色',
					flex: 5,
			        layout: 'fit',
			        items: [userRolesGrid]
				}
			],
			buttons: [
				{
		            text: '确定',
		            handler: function(){
		            	Ext.MessageBox.confirm('提示', '确定保存 ?', function(btn) {
							if(btn != 'yes') {
								return;
							}
							
							var userRoleIds = '';
							userRolesDS.each(function(rec){
					    		userRoleIds += rec.data.id + ',';
						   	});
						   	
						   	var userIds = '';
						   	var sr = grid.getSelectionModel().getSelections();
						   	for(var i = 0; i < sr.length; i++) {
						   		userIds += sr[i].data.id + ',';
						   	}
							
							Ext.Ajax.request({
								method: 'post',
								url: myApp.ctx + '/mini/cms/admin/basic/UserRoleController/save.sdo',
								params: {userIds: userIds, roleIds: userRoleIds},
								success:function(resp){
									var obj=Ext.util.JSON.decode(resp.responseText);
									if(obj.result == 'success') {
										grid.getStore().reload();
										Ext.MessageBox.alert('提示', '保存成功！');
									}
									else {
										Ext.MessageBox.alert('报错了！！！', '保存失败！！！' + obj.info);
									}
									userRoleWin.hide();
								}
							});
						});
		            }
				},
				{
					text: '取消',
		            handler: function(){
		            	userRoleWin.hide();
		            }
				}
			]
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
								{xtype: 'textfield',name: 'name', fieldLabel: '用户名',anchor : '95%'}
							]
						},
						{
							columnWidth: .33, layout: 'form',
							items: [
								{xtype: 'textfield',name: 'username', fieldLabel: '真实姓名',anchor : '95%'}
							]
						},
						{
							columnWidth: .33, layout: 'form',
							items: [
								new Ext.form.ComboBox({
									fieldLabel: '用户类型',
									name: 'usertype',
									hiddenName: 'usertype',
									anchor : '95%',
									triggerAction: 'all', 
									editable: false,
									mode: 'local',
									allowBlank : false,
									valueField: 'value',
									displayField: 'text',
									value: '',
									store: new Ext.data.SimpleStore({
										fields: ['value', 'text'],
										data: [['', '全部'],['AE', 'AE'],['渠道销售', '渠道销售'],['直客销售', '直客销售'],['销售助理', '销售助理'],['默认', '默认']]
									})
								})
							]
						}
					]
				}
			],
			keys: [
				{
					key: [13],
					fn: function() {
						var fv = searchForm.getForm().getValues();
						ds.baseParams= fv;
						ds.load({params: {"extLimit.start":0, "extLimit.limit":25}});
					}
				}
			],
			buttons: [
				new Ext.Button({
					text: '查询',
					width: 70,
					handler: function() {
						var fv = searchForm.getForm().getValues();
						ds.baseParams= fv;
						ds.load({params: {"extLimit.start":0, "extLimit.limit":25}});
					}
				}),
				new Ext.Button({
					text: '清空',
					width: 70,
					handler: function() {
						searchForm.form.reset();
						ds.baseParams= {};
						ds.load({params: {"extLimit.start":0, "extLimit.limit":25}});
					}
				})
			]
		});
		
		var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:false});
		
		var cm = new Ext.grid.ColumnModel([
			sm,
			new Ext.grid.RowNumberer(),
			{header:'用户名', dataIndex:'userName', sortable:true},
			{header:'真实姓名', dataIndex:'realName', sortable:true},
			{header:'姓别', dataIndex:'sex', sortable:true, renderer:function(v){if(v=='0') return '女'; else return '男'}},
			{header:'Email', dataIndex:'email', sortable:true},
			{header:'出生日期', dataIndex:'birthDay', sortable:true},
			{header:'建立时间', dataIndex:'insertDate', sortable:true},
			{header:'备注', dataIndex:'description', sortable:true}
		]);
		
		var ds = new Ext.data.Store({
			proxy: new Ext.data.HttpProxy({url: myApp.ctx + '/mini/cms/admin/basic/UserController/search.sdo'}),
			remoteSort: true,
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
					{name: 'userName'},
					{name: 'realName'},
					{name: 'sex'},
					{name: 'email'},
					{name: 'birthDay'},
					{name: 'insertDate'},
					{name: 'description'}
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
							if(rs.data.realName == 'SysUser') {
								Ext.MessageBox.show({
					        		title: '提示',
				        		    msg: '不能删除系统管理员!',
				        		    buttons: Ext.Msg.OK,
				        		    fn: function(){},
				        		    icon: Ext.MessageBox.ERROR
					        	});
							}
							else {
								deleteResource(rs.data.id, rs.data.realName);
							}
						}
					},
					{
						text: '配置角色'	,
						iconCls: 'add',
						handler: function() {
							var rs = grid.getSelectionModel().getSelections();
							if(rs.length < 1) {
								Ext.MessageBox.show({
					        		title: '提示',
				        		    msg: '需要先选择用户！',
				        		    buttons: Ext.Msg.OK,
				        		    fn: function(){},
				        		    icon: Ext.MessageBox.ERROR
					        	});
							}
							else if(rs.length == 1) {
								rolesDS.load({params: {userId: rs[0].data.id}});
								userRolesDS.load({params: {userId: rs[0].data.id}});
								userRoleWin.show();
							}
							else if(rs.length > 1) {//选择多个用户时
								Ext.Msg.confirm('提示', '选择了多用户，配置角色时将删除选择用户之前配置的角色，是否继续？', function(btn, text) {
									if(btn == 'yes') {
										rolesDS.load();
										userRolesDS.removeAll();
										userRoleWin.show();
									}
								});
							}
						}
					}
				]
			}),
			bbar: new Ext.PagingToolbar({
				pageSize: 25,
				store: ds,
				displayInfo: true,
				displayMsg: '显示第{0}条到{1}条记录,一共{2}条',
				emptyMsg: '没有记录'
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
				url: myApp.ctx + '/mini/cms/admin/basic/UserController/getDetailInfo.sdo',
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
					url: myApp.ctx + '/mini/cms/admin/basic/UserController/delete.sdo',
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
		com.mini.cms.admin.basic.UserPanel.superclass.initComponent.apply(this, arguments);
	},
	
	initMethod: function() {
	}
});
