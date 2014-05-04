
Ext.grid.RowSpanGrid = Ext.extend(Ext.grid.GridPanel, {
    
    
    aggregator: 'sum',
    
    
    renderer: undefined,
    
    //inherit docs
    initComponent: function() {
        Ext.grid.RowSpanGrid.superclass.initComponent.apply(this, arguments);
        
        this.initAxes();
        
        //no resizing of columns is allowed yet in PivotGrid
        this.enableColumnResize = false;
        
        this.viewConfig = Ext.apply(this.viewConfig || {}, {
            forceFit: true
        });
        
        //TODO: dummy col model that is never used - GridView is too tightly integrated with ColumnModel
        //in 3.x to remove this altogether.
        this.colModel = new Ext.grid.ColumnModel({});
    },
    
    getAggregator: function() {
        if (typeof this.aggregator == 'string') {
            return Ext.grid.PivotAggregatorMgr.types[this.aggregator];
        } else {
            return this.aggregator;
        }
    },
    
    setAggregator: function(aggregator) {
        this.aggregator = aggregator;
    },
    
    
/**
     * Sets the field name to use as the Measure in this Pivot Grid
     * @param {String} measure The field to make the measure
     */
    setMeasure: function(measure) {
        this.measure = measure;
    },
    
    
/**
     * Sets the left axis of this pivot grid. Optionally refreshes the grid afterwards.
     * @param {Ext.grid.RowSpanGridAxis} axis The pivot axis
     * @param {Boolean} refresh True to immediately refresh the grid and its axes (defaults to false)
     */
    setLeftAxis: function(axis, refresh) {
        
/**
         * The configured {@link Ext.grid.RowSpanGridAxis} used as the left Axis for this Pivot Grid
         * @property leftAxis
         * @type Ext.grid.RowSpanGridAxis
         */
        this.leftAxis = axis;
        
        if (refresh) {
            this.view.refresh();
        }
    },
    
    
/**
     * Sets the top axis of this pivot grid. Optionally refreshes the grid afterwards.
     * @param {Ext.grid.RowSpanGridAxis} axis The pivot axis
     * @param {Boolean} refresh True to immediately refresh the grid and its axes (defaults to false)
     */
    setTopAxis: function(axis, refresh) {
        
/**
         * The configured {@link Ext.grid.RowSpanGridAxis} used as the top Axis for this Pivot Grid
         * @property topAxis
         * @type Ext.grid.RowSpanGridAxis
         */
        this.topAxis = axis;
        
        if (refresh) {
            this.view.refresh();
        }
    },
    
    /**
     * @private
     * Creates the top and left axes. Should usually only need to be called once from initComponent
     */
    initAxes: function() {
        var PivotAxis = Ext.grid.RowSpanGridAxis;
        
        if (!(this.leftAxis instanceof PivotAxis)) {
            this.setLeftAxis(new PivotAxis({
                orientation: 'vertical',
                dimensions : this.leftAxis || [],
                store      : this.store
            }));
        };
        
        if (!(this.topAxis instanceof PivotAxis)) {
            this.setTopAxis(new PivotAxis({
                orientation: 'horizontal',
                dimensions : this.topAxis || [],
                store      : this.store
            }));
        };
    },
    
    /**
     * @private
     * @return {Array} 2-dimensional array of cell data
     */
    extractData: function() {
        var records  = this.store.data.items,
            recCount = records.length,
            cells    = [],
            record, i, j, k;
        
        if (recCount == 0) {
            return [];
        }
        
        var leftTuples = this.leftAxis.getTuples(),
            leftCount  = leftTuples.length,
            topTuples  = this.topAxis.getTuples(),
            topCount   = topTuples.length,
            aggregator = this.getAggregator();
        
        for (i = 0; i < recCount; i++) {
            record = records[i];
            
            for (j = 0; j < leftCount; j++) {
                cells[j] = cells[j] || [];
                
                if (leftTuples[j].matcher(record) === true) {
                    for (k = 0; k < topCount; k++) {
                        cells[j][k] = cells[j][k] || [];
                        
                        if (topTuples[k].matcher(record)) {
                            cells[j][k].push(record);
                        }
                    }
                }
            }
        }
        
        var rowCount = cells.length,
            colCount, row;
        
        for (i = 0; i < rowCount; i++) {
            row = cells[i];
            colCount = row.length;
            
            for (j = 0; j < colCount; j++) {
                cells[i][j] = aggregator(cells[i][j], this.measure);
            }
        }
        
        return cells;
    },
    
    
/**
     * Returns the grid's GridView object.
     * @return {Ext.grid.PivotGridView} The grid view
     */
    getView: function() {
        if (!this.view) {
            this.view = new Ext.grid.RowSpanGridView(this.viewConfig);
        }
        
        return this.view;
    }
});

Ext.reg('pivotgridmy', Ext.grid.RowSpanGrid);