Ext.namespace("yk.stat.office.DataViewMenuPanel"); 
yk.stat.office.DataViewMenuPanel = function(config) {
	Ext.apply(this, {
		items: []
	});
	
	yk.stat.office.DataViewMenuPanel.superclass.constructor.apply(this, arguments);
	this.addEvents('menuClick');
	this.initMenuEvent();
};

Ext.extend(yk.stat.office.DataViewMenuPanel, Ext.Panel, {
	
	initMenuEvent: function() {
	},
	
	loadMenuTree: function() {
	},
	
	onRender: function() {
		yk.stat.office.DataViewMenuPanel.superclass.onRender.apply(this, arguments);
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
				header: false,
				headerCfg: {
			        tag: 'div',
			        cls: 'x-panel-header',
			        children: [
			            { tag: 'div', cls: 'panel_header_icon', 'html': '<img height="22px" src="' + treeData[i].icon + '"/>'},
			            { tag: 'div', cls: 'panel_header_main', 'html': '<b style="font-size:15">'+treeData[i].menuName + '</b>' }
			        ]
			    },
				layout : "fit",
				items: [
					new Ext.DataView({
						store: new Ext.data.JsonStore({
						    fields: ['id', 'text','icon', 'jsUrl', 'leaf', 'mainClass', 'type', 'url'],
						    idProperty: 'id',
						    data: children
						}),
						tpl: new Ext.XTemplate(
						    '<ul>',
							    '<tpl for=".">',
							        '<li class="phone">',
							            '<img width="32" height="32" src="{icon}" />',
							            '<strong><b>{text}</b></strong>',
							        '</li>',
							    '</tpl>',
							'</ul>'
						),
						plugins : [
						   new Ext.ux.DataViewTransition({
						       duration  : 550,
						       idProperty: 'id'
						   })
						],
						cls: 'phones',
						itemSelector: 'li.phone',
						overClass   : 'phone-hover',
						singleSelect: true,
						multiSelect : false,
						autoScroll  : true,
						listeners: {
							click: {
								scope: this,
								fn:  function(dv, index, b, c) {
						    		this.fireEvent('menuClick', dv.getSelectedRecords()[0].data);
						    		dv.clearSelections(true);
						    		dv.select(index);
						    	}
							}
						}
					})
				 ]
			}
			this.add(cp);
		}
		this.doLayout();
	},
	
	selectTreeNode: function(mainpane, tab) {
		var id = tab.id;
		if(this.layout.activeItem) {
			var dv = this.layout.activeItem.items.items[0];
			var index = dv.store.indexOfId(id);
			if(index > -1) {
				dv.select(index);
			}
		}
	}
});

