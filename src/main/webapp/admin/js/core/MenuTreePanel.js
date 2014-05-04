Ext.namespace("yk.stat.office.MenuTreePanel"); 
yk.stat.office.MenuTreePanel = function(config) {
	Ext.apply(this, {
		items: []
	});
	
	yk.stat.office.MenuTreePanel.superclass.constructor.apply(this, arguments);
	this.addEvents('menuClick');
	this.initMenuEvent();
};

Ext.extend(yk.stat.office.MenuTreePanel, Ext.Panel, {
	
	initMenuEvent: function() {
	},
	
	loadMenuTree: function() {
	},
	
	onRender: function() {
		yk.stat.office.MenuTreePanel.superclass.onRender.apply(this, arguments);
		this.loadMenuTree();
	},
	
	/**
	 * 一次生成左侧菜单，不用动态, 菜单级数少时适合用此方法
	 * @param {} treeData
	 */
	createMenu: function(treeData) {
		this.removeAll();
		for(var i = 0; i < treeData.length; i++) {
			var children = [];
			for(var j = 0; j < treeData[i].subMenu.length; j++) {
				children.push(treeData[i].subMenu[j]);
			}
			var cp = {
				title : treeData[i].menuName,
//				iconCls : 'default_menu',
				iconCls: treeData[i].icon,
				layout : "fit",
				items: [{
					xtype: 'treepanel',
					rootVisible:false,
					animate: true,
					autoScroll: true,
					loader: new Ext.tree.TreeLoader(),
			        root: new Ext.tree.AsyncTreeNode({
			            expanded: true,
			            children: children
			        }),
			        listeners: {
			        	click: {
			        		scope: this,
			        		fn:  function(node, event) {
				        		this.fireEvent('menuClick', node.attributes);
				        	}
			        	}
			        }
				}]
			}
			this.add(cp);
		}
		this.doLayout();
	},
	
	/**
	 * 动态多级树, 多级菜单时使用
	 * @param {} instanceTree
	 * @param {} sysMenuId
	 */
	loadTree: function(instanceTree, sysMenuId) {
		instanceTree.removeAll();
		
		Ext.Ajax.request({
			method: 'post',
			url: '../basic/LoginAction/getUserButtons.action',
			params: {sysMenuId:sysMenuId},
		   	success:function(resp){
		    	var obj=Ext.util.JSON.decode(resp.responseText);
		    	createLeftOpPanel(obj);
		    	instanceTree.doLayout();
		   	},
		   	fail: function() {
		   	}
		});
		
		function createLeftOpPanel(obj) {
			for(var i = 0; i < obj.length; i++) {
				if(obj[i].actionPath != '') {
					instanceTree.add({
						title : obj[i].menuName
					});
				}
				else {
					var menuTree = createMenuTree(obj[i].nodeId, obj[i].menuName);
		    		instanceTree.add({
		    			title : obj[i].menuName,
		    			iconCls : 'default_menu',
						layout : "fit",
						items: [menuTree],
						listeners:{  
						  'activate':function(obj){  
						   }
						}
		    		});
				}
			}
		};
		
		function createMenuTree(menuId, menuName) {
			var menuRoot = new Ext.tree.AsyncTreeNode({
				id : menuId,
				text: menuName
			});
			
			var treeLoader = new Ext.tree.TreeLoader({
				dataUrl: '../basic/LoginAction/getUserTree.action',
				baseParams:{parantNodeId:menuId, menuName:menuName}
			});
		
			var menuTree = new Ext.tree.TreePanel({
				rootVisible:false,
				animate: true,
				autoScroll: true,
				bodyStyle: "background-color:#FDFDFD; border-width: 0px 0px 0px 0px;",
				loader: treeLoader
			});
			
			menuTree.setRootNode(menuRoot);
			
			menuTree.addListener("click", function(node, event) {
				instanceTree.fireEvent('menuClick', node.attributes);
			});
			
			return menuTree;
		};
	},
	
	selectTreeNode: function(mainpane, tab) {
		var id = tab.id;
		if(this.layout.activeItem) {
			var tree = this.layout.activeItem.items.items[0];
			var node = tree.getNodeById(id);
			if(node) {
				tree.getSelectionModel().select(node);
			}
			else {
				tree.getSelectionModel().clearSelections();
			}
		}
	}
});

