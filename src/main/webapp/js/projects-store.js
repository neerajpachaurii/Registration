var projectStore;

Ext.onReady(function () {

    projectStore = new Ext.data.JsonStore({
        url: "getProjectsJson.action",
        root: "result.rows",
        totalProperty: "result.totalCount",
        fields: [
            "id",
            "title",
            "description",
            "owner",
            "allowedUsers"
        ]
    });

});
