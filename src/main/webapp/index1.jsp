<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Welcome - ExtJS</title>

    <!-- EXTJS CSS -->
    <link rel="stylesheet" type="text/css" 
          href="https://cdnjs.cloudflare.com/ajax/libs/extjs/6.2.0/classic/theme-triton/resources/theme-triton-all.css">

    <!-- EXTJS SCRIPT -->
    <script type="text/javascript" 
            src="https://cdnjs.cloudflare.com/ajax/libs/extjs/6.2.0/ext-all.js"></script>

    <style>
        body {
            background: linear-gradient(to bottom right, #e8edf3, #f7f9fb);
            padding: 40px;
            font-family: Arial, sans-serif;
        }

        .custom-panel {
            border-radius: 8px !important;
            box-shadow: 0 4px 12px rgba(0,0,0,0.15) !important;
            background: #ffffff !important;
        }

        .panel-header {
            background: linear-gradient(to right, #42a5f5, #1e88e5) !important;
            color: white !important;
            font-size: 16px !important;
            font-weight: bold !important;
            border-radius: 8px 8px 0 0 !important;
            padding: 10px !important;
        }

        .custom-btn {
            background: #1e88e5 !important;
            color: white !important;
            font-size: 15px !important;
            border-radius: 6px !important;
        }

        .custom-btn:hover {
            background: #1565c0 !important;
        }
    </style>
</head>

<body>

<script type="text/javascript">

Ext.onReady(function () {

    Ext.create('Ext.container.Container', {
        renderTo: Ext.getBody(),
        layout: {
            type: 'vbox',
            align: 'center'
        },

        defaults: {
            width: 420,
            bodyPadding: 20,
            margin: "25 0",
            layout: { type: "vbox", align: "center" },
            cls: 'custom-panel'
        },

        items: [

            // USER PANEL
            {
                xtype: "form",
                title: "USER BUTTON",
                header: {
                    cls: 'panel-header'
                },
                items: [
                    {
                        xtype: "button",
                        text: "Login",
                        width: 240,
                        margin: "10 0",
                        cls: "custom-btn",
                        handler: function () {
                            window.location.href = "login.jsp";
                        }
                    },
                    {
                        xtype: "button",
                        text: "Register",
                        width: 240,
                        margin: "10 0",
                        cls: "custom-btn",
                        handler: function () {
                            window.location.href = "registration.jsp";
                        }
                    }
                ]
            },

            // EMPLOYEE PANEL
            {
                xtype: "form",
                title: "EMPLOYEE BUTTON",
                header: {
                    cls: 'panel-header'
                },
                items: [
                    {
                        xtype: "button",
                        text: "Register Employee",
                        width: 240,
                        margin: "10 0",
                        cls: "custom-btn",
                        handler: function () {
                            window.location.href = "addEmployee.action";
                        }
                    },
                    {
                        xtype: "button",
                        text: "View Employee",
                        width: 240,
                        margin: "10 0",
                        cls: "custom-btn",
                        handler: function () {
                            window.location.href = "viewEmployees.action";
                        }
                    },
                    {
                        xtype: "button",
                        text: "Edit Employee",
                        width: 240,
                        margin: "10 0",
                        cls: "custom-btn",
                        handler: function () {
                            window.location.href = "editEmployee.action";
                        }
                    },
                    {
                        xtype: "button",
                        text: "Login Employee",
                        width: 240,
                        margin: "10 0",
                        cls: "custom-btn",
                        handler: function () {
                            window.location.href = "loginEmployee.action";
                        }
                    }
                ]
            }
        ]
    });

});
</script>

</body>
</html>
