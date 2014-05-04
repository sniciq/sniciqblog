
Ext.namespace('com.mini.cms.admin.content.CategoryPanel');
com.mini.cms.admin.content.CategoryPanel=Ext.extend(Ext.Panel, {
	initComponent: function() {
		
		var editForm = this.editForm = new Ext.FormPanel({
			labelAlign: 'right',
			region: 'center',
			autoScroll:true, 
			labelWidth: 100,
			frame: true,
			xtype: 'fieldset',
			items: [
				{xtype: 'textfield',hidden: true,anchor: '100%',name:'id'},
				{xtype: 'textfield',name: 'parentId', fieldLabel: '父分类ID',anchor : '95%',editable: false},
				{xtype: 'textfield',name: 'name', fieldLabel: '名&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;称',anchor : '95%'},
				{xtype: 'textfield',name: 'sort', fieldLabel: '排&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;序',anchor : '95%'}
			],
			buttons: [
				{
					text: '保存'	,
					scope: this,
					handler: function() {
						if(!editForm.getForm().isValid())
							return;
						
						editForm.form.doAction('submit', {
							url: myApp.ctx + '/mini/cms/admin/category/CategoryController/save.sdo',
							method: 'post',
							waitTitle:'请等待',
							waitMsg: '正在提交...',
							params: '',
							scope: this,
							success: function(form, action) {
								if(action.result.result == 'success') {
									Ext.MessageBox.alert('结果', '保存成功！');
									form.reset();
									this.reLoadNode(this.treeGrid.getSelectionModel().getSelectedNode(), this.treeGrid.root);
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
		
		var editWin = this.editWin = new Ext.Window({
			title: '编辑',
			modal: true,
			layout:'fit',
			width:400,
			height:200,
			closeAction:'hide',
			plain: true,
			layout: 'border',
			items: [editForm]
		});
		
		var rootNode = new Ext.tree.AsyncTreeNode({text: 'Root', id:'0'});
		var loader =  new Ext.tree.TreeLoader({dataUrl: myApp.ctx + '/mini/cms/admin/category/CategoryController/getSubCategory.sdo',baseParams:{}});
		
		var treeGrid = this.treeGrid = new Ext.ux.tree.TreeGrid({
			region: 'center',
	        autoScroll: true,
	        enableDD: true,
	        enableSort: false,
	        columns:[
		        {header: '分类名称',dataIndex: 'name',width: 200},
		        {header: '排序',dataIndex: 'sort',align:'center',width: 50},
		        {header: '父分类ID',dataIndex: 'parentId',align:'center',width: 50},
		        {header: '分类ID',dataIndex: 'id',align:'center',width: 50},
		        {header: '分类地址',dataIndex: 'url',width: 400}
		    ],
	        root: rootNode,
	        loader: loader,
	        tbar: new Ext.Toolbar({
	        	items: [
					{
						text: '新建', 
						iconCls: 'add',
						scope: this,
					    handler: function() {
					    	var parentId = 0;
							if(this.treeGrid.getSelectionModel().getSelectedNode()) {
								parentId = this.treeGrid.getSelectionModel().getSelectedNode().attributes.id;
							}
							this.editForm.getForm().findField('parentId').setValue(parentId);
					    	this.editWin.show();
					    }
					},
	        		{
	        			text: '编辑', 
	        			iconCls: 'edit',
	        			scope: this,
			            handler: function() {
			            	if(this.treeGrid.getSelectionModel().getSelectedNode()) {
			            		this.editCategory(this.treeGrid.getSelectionModel().getSelectedNode().attributes.id);
			            	}
			            }
	        		},
	        		{
	        			text: '删除', 
	        			iconCls: 'del',
	        			scope: this,
			            handler: function() {
			            	if(this.treeGrid.getSelectionModel().getSelectedNode()) {
			            		this.deleteCategory(this.treeGrid.getSelectionModel().getSelectedNode());
			            	}
			            }
	        		}
				]
	        })
		});
		
		Ext.apply(this, {  
            iconCls: 'tabs',  
            autoScroll: false,  
            closable: true,
            layout: 'border',
            items:[treeGrid]
        });  
        //调用父类构造函数（必须）  
        com.mini.cms.admin.content.CategoryPanel.superclass.initComponent.apply(this, arguments);
	},

	editCategory: function(id) {
		this.editWin.show();
		
		this.editForm.load({
			url: myApp.ctx + '/mini/cms/admin/category/CategoryController/getDetailInfo.sdo',
			params: {id: id},
			success:function(form,action){
			}
		});
	},
	
	deleteCategory: function(node) {
		Ext.MessageBox.confirm('提示', '确定删除  ' + node.attributes.name + ' ? ', function(btn) {
			if(btn != 'yes') {
				return;
			}
			
			Ext.Ajax.request({
				method: 'post',
				url: myApp.ctx + '/mini/cms/admin/category/CategoryController/delete.sdo',
				params: {id: node.attributes.id},
				scope: this,
				success:function(resp){
					var obj=Ext.util.JSON.decode(resp.responseText);
					if(obj.result == 'success') {
						Ext.MessageBox.alert('提示', '删除成功！');
						this.reLoadNode(this.treeGrid.getSelectionModel().getSelectedNode(), this.treeGrid.root);
					}
					else {
						Ext.MessageBox.alert('报错了！！！', '添加失败！！！' + obj.info);
					}
				}
			});
		}, this);
	},
	
	reLoadNode: function(node, treeRoot) {
		if(node) {
			var pNode = node.parentNode;
			if(pNode) {
				this.treeGrid.loader.load(pNode);
				pNode.expand();
			}
			else {
				this.treeGrid.loader.load(treeRoot);
				treeRoot.expand();
			}
		}
		else {
			this.treeGrid.loader.load(treeRoot);
			treeRoot.expand();
		}
	},
	
	initMethod: function() {
	}
});
