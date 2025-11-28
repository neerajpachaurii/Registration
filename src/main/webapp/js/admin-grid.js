Ext.onReady(function(){

    // Load stores first
    projectStore.load({params:{start:0, limit:10}});
    employeeStore.load({params:{start:0, limit:10}});

    // PROJECT GRID
    new Ext.grid.GridPanel({
        renderTo: "projects-grid",
        store: projectStore,
        width: "95%",
        height: 300,
        frame: true,
        stripeRows: true,

        columns: [
            {header: "Title", dataIndex: "title", width: 180},
            {header: "Description", dataIndex: "description", width: 250},
            {header: "Owner", dataIndex: "owner", width: 120,
             renderer: function(v){ return v ? v.name : ""; }},

            {header: "Assigned", dataIndex: "allowedUsers", width: 180,
             renderer: function(v){
                 if(!v) return "";
                 return v.map(u => u.name).join(", ");
             }}
        ],

        bbar: new Ext.PagingToolbar({
            store: projectStore,
            pageSize: 10,
            displayInfo: true
        })
    });


    // EMPLOYEE GRID
    new Ext.grid.GridPanel({
        renderTo: "employees-grid",
        store: employeeStore,
        width: "95%",
        height: 300,
        frame: true,
        stripeRows: true,

        columns: [
            {header: "Name", dataIndex: "name", width: 180},
            {header: "Email", dataIndex: "email", width: 200},
            {header: "Dept", dataIndex: "department", width: 120},
            {header: "Salary", dataIndex: "salary", width: 100},
            {header: "Status", dataIndex: "status", width: 100}
        ],

        bbar: new Ext.PagingToolbar({
            store: employeeStore,
            pageSize: 10,
            displayInfo: true
        })
    });

});
