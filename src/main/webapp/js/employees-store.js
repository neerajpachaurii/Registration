var employeeStore;

Ext.onReady(function () {

    employeeStore = new Ext.data.JsonStore({
        url: "getEmployeesJson.action",
        root: "result.rows",
        totalProperty: "result.totalCount",
        fields: [
            "id",
            "name",
            "email",
            "department",
            "salary",
            "status"
        ]
    });

});
