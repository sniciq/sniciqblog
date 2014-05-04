/*
 * comments
 */
Ext.namespace('com.mini.cms.admin.basic.RolePanel');
com.mini.cms.admin.basic.RolePanel=Ext.extend(Ext.Panel, {
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
				{xtype: 'textfield',name: 'roleName', fieldLabel: '角&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;色',anchor : '95%'},
				{xtype: 'textfield',name: 'describle', fieldLabel: '描&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;述',anchor : '95%'}
			]
		});
		
		var editWin = new Ext.Window({
			title: '编辑',
			modal: true,
			layout:'fit',
			width:500,
			height:180,
			closeAction:'hide',
			plain: true,
			layout: 'border',
			items: [editForm],
			buttons: [
				{
					text: '保存'	,
					handler: function() {
						if(!editForm.getForm().isValid())
							return;
						
						editForm.form.doAction('submit', {
							url: myApp.ctx + '/mini/cms/admin/basic/RoleController/save.sdo',
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
		
		var root = new Ext.tree.TreeNode({
			id : 0,
			node: 0,
			text: 'Root'
		});
		var treeLoader = new Ext.tree.TreeLoader({dataUrl: myApp.ctx + '/mini/cms/admin/basic/RoleAuthorityController/getRoleResource.sdo'})
		
		function getUnChecked(root, a, startNode){
	        startNode = startNode || root;
	        var r = [];
	        var f = function(){
	            if(this.attributes.checked == false){
	                r.push(!a ? this : (a == 'id' ? this.id : this.attributes[a]));
	            }
	        };
	        startNode.cascade(f);
	        return r;
	    }
		
		var allResourceTree = new Ext.tree.TreePanel({
			title: '所有资源',
			region: 'center',
			useArrows:true,
	        autoScroll:true,
	        animate:true,
	        enableDD:true,
	        rootVisible: false,
	        containerScroll: true,
	        frame: true,
	        loader: treeLoader,
	        listeners: {
	        	'checkchange': function(node, checked){
	                if(checked){//选中时自动选中父结点
	                	if(node.parentNode && node.parentNode.attributes.checked == false) {
	                		node.parentNode.ui.toggleCheck(true);
	                		node.parentNode.attributes.checked=true;
	                	}
	                }else{//取消时，自动取消所有子结点
	                	var chs = node.childNodes;
	                	for(var i = 0; i < chs.length; i++) {
	                		chs[i].ui.toggleCheck(false);
							chs[i].attributes.checked=false;
	                	}
	                }
	            }
	        }
		});
		allResourceTree.setRootNode(root);
		
		var roleResourceWin = new Ext.Window({
			title: '配置资源',
			modal: true,
			width:400,
			height:400,
			closeAction:'hide',
			plain: true,
			layout: 'border',
			items: [allResourceTree],
			buttons: [{
	            text: '确定',
	            handler: function(){
	                var msg = '', selNodes = allResourceTree.getChecked();
	                Ext.each(selNodes, function(node){
	                    if(msg.length > 0){
	                        msg += ',';
	                    }
	                    msg += node.id;
	                });
	                
	                var unChks = '', 
	                unSels = getUnChecked(root);
	                Ext.each(unSels, function(node){
	                    if(unChks.length > 0){
	                        unChks += ',';
	                    }
	                    unChks += node.id;
	                });
	                
	                var roleId = grid.getSelectionModel().getSelected().data.id;
	                Ext.Ajax.request({
						method: 'post',
						url: myApp.ctx + '/mini/cms/admin/basic/RoleAuthorityController/saveRoleAuthority.sdo',
						params: {roleId:roleId, resourceNodes: msg, unCheckNodes: unChks},
						success:function(resp){
							var obj=Ext.util.JSON.decode(resp.responseText);
							if(obj.result == 'success') {
								grid.getStore().reload();
								Ext.MessageBox.alert('提示', '保存成功！',function(){roleResourceWin.hide();});
							}
							else {
								Ext.MessageBox.alert('报错了！！！', '保存失败！！！' + obj.info,function(){roleResourceWin.hide();});
							}
						}
					});
	            }
	        },
	        {
	            text: '取消',
	            handler: function(){
	            	roleResourceWin.hide();
	            }
	        }]
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
								{ xtype: 'textfield', name: 'roleName', fieldLabel: '角色',anchor : '95%'}
							]
						},
						{
							columnWidth: .33, layout: 'form',
							items: [
								{ xtype: 'textfield', name: 'describle', fieldLabel: '描述',anchor : '95%'}
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
			{header:'角色', dataIndex:'roleName', sortable:true, width: 20},
			{header:'描述', dataIndex:'describle', sortable:true}
		]);
		
		var ds = new Ext.data.Store({
			proxy: new Ext.data.HttpProxy({url: myApp.ctx + '/mini/cms/admin/basic/RoleController/search.sdo'}),
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
					{name: 'roleName'},
					{name: 'describle'}
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
							deleteResource(rs.data.id, rs.data.roleName);
						}
					},
					{
						text: '配置资源与权限',
						iconCls: 'add',
						handler: function() {
							var rs = grid.getSelectionModel().getSelected();
							editRoleResource(rs.data.id, rs.data.roleName);
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
							grid.exportToExcel();
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
				url: myApp.ctx + '/mini/cms/admin/basic/RoleController/getDetailInfo.sdo',
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
					url: myApp.ctx + '/mini/cms/admin/basic/RoleController/delete.sdo',
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
		
		function editRoleResource(id, roleName) {
			roleResourceWin.show();
			treeLoader.baseParams = {roleId: id};
			treeLoader.load(root);
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
        com.mini.cms.admin.basic.RolePanel.superclass.initComponent.apply(this, arguments);
	},
	
	initMethod: function() {
	}
});
