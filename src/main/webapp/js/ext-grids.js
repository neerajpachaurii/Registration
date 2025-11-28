Ext.onReady(function(){

    var projectStore = new Ext.data.JsonStore({
        url: GET_PROJECTS_URL,
        root: 'result.rows',
        totalProperty: 'result.totalCount',
        fields: ['id','title','description','owner','allowedUsers']
    });

    new Ext.grid.GridPanel({
        renderTo: 'projects-grid',
        store: projectStore,
        width:'100%',
        height:300,
        frame:true,
        columns:[
            {header:'Title',dataIndex:'title',width:200},
            {header:'Description',dataIndex:'description',width:350},
            {header:'Owner',dataIndex:'owner',width:150,
                renderer:function(v){ return v ? v.name : ''; }},
            {header:'Assigned To',dataIndex:'allowedUsers',width:180,
                renderer:function(v){
                    return v ? v.map(x=>x.name).join(", ") : "";
                }}
        ],
        bbar:new Ext.PagingToolbar({
            pageSize:10,
            store:projectStore,
            displayInfo:true
        })
    });

    projectStore.load({params:{start:0,limit:10}});


    // EMPLOYEES GRID
    var empStore = new Ext.data.JsonStore({
        url: GET_EMPLOYEES_URL,
        root: 'result.rows',
        totalProperty: 'result.totalCount',
        fields: ['id','name','email','department','salary','status']
    });

    new Ext.grid.GridPanel({
        renderTo: 'employees-grid',
        store: empStore,
        width:'100%',
        height:300,
        frame:true,
        columns:[
            {header:'Name',dataIndex:'name',width:200},
            {header:'Email',dataIndex:'email',width:200},
            {header:'Dept',dataIndex:'department',width:140},
            {header:'Salary',dataIndex:'salary',width:100},
            {header:'Status',dataIndex:'status',width:100}
        ],
        bbar:new Ext.PagingToolbar({
            pageSize:10,
            store:empStore,
            displayInfo:true
        })
    });

    empStore.load({params:{start:0,limit:10}});
});
