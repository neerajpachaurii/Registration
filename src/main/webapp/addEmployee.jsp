<%-- 
 
 <%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add Employee</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container mt-5">
    <div class="card shadow p-4" style="max-width: 600px; margin: auto;">
        <h3 class="text-center mb-3 text-primary">Register Employee</h3>

        <s:form action="addEmployee" method="post" theme="simple">

  <s:fielderror/>
            <div class="mb-3">
                <label>Username</label>
                <s:textfield name="employee.username" cssClass="form-control" required="true"/>
            </div>

            

            <div class="mb-3">
                <label>Name</label>
                <s:textfield name="employee.name" cssClass="form-control" required="true"/>
            </div>

            <div class="mb-3">
                <label>Email</label>
                <s:textfield name="employee.email" cssClass="form-control" required="true"/>
            </div>

            <div class="mb-3">
                <label>Department</label>
                <s:textfield name="employee.department" cssClass="form-control" required="true"/>
            </div>

            <div class="mb-3">
                <label>Salary</label>
                <s:textfield name="employee.salary" cssClass="form-control" required="true"/>
            </div>
            
            <div class="mb-3">
                <label class="form-label">Address</label>
                <s:textfield name="employee.address" cssClass="form-control" required="true"/>
            </div>
            
            <div class="mb-3">
                <label class="form-label">Country</label>
                <s:textfield name="employee.country" cssClass="form-control" required="true"/>
            </div>
            
            <div class="mb-3">
                <label class="form-label">Expenses</label>
                <s:textfield name="employee.expenses" cssClass="form-control" required="true"/>
            </div>
            
            <div class="mb-3">
                <label class="form-label">Father_Name</label>
                <s:textfield name="employee.father_name" cssClass="form-control" required="true"/>
            </div>
            
            <div class="mb-3">
                <label class="form-label">Mother_name</label>
                <s:textfield name="employee.mother_name" cssClass="form-control" required="true"/>
            </div>
            
            <div class="mb-3">
                <label>Password</label>
                <s:password name="employee.password" cssClass="form-control" required="true"/>
            </div>
            

            <button type="submit" class="btn btn-primary w-100">Register</button>
        </s:form>
        <div style="text-align:center; margin-top:10px;">
    <a href="loginEmployee.jsp" class="btn btn-secondary">Already have an account? Login</a>
</div>
    </div>
</div>
</body>
</html>
  --%>
  
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add Employee - ExtJS</title>

    <!-- EXTJS CSS -->
    <link rel="stylesheet" type="text/css"
        href="https://cdnjs.cloudflare.com/ajax/libs/extjs/6.2.0/classic/theme-triton/resources/theme-triton-all.css">

    <!-- EXTJS SCRIPT -->
    <script type="text/javascript"
        src="https://cdnjs.cloudflare.com/ajax/libs/extjs/6.2.0/ext-all.js"></script>

    <style>
        body {
            background: #eaeaea;
            padding: 40px;
            font-family: Arial, sans-serif;
        }

        .custom-panel {
            border-radius: 8px !important;
            box-shadow: 0 4px 12px rgba(0,0,0,0.15) !important;
            background: #f3faff !important;
        }

        .panel-header {
            background: #1976d2 !important;
            color: white !important;
            font-size: 20px !important;
            font-weight: bold !important;
            text-align: center !important;
            padding: 12px !important;
            border-radius: 8px 8px 0 0 !important;
        }

        .custom-btn {
            background: #1976d2 !important;
            color: white !important;
            font-size: 17px !important;
            border-radius: 6px !important;
        }

        .custom-btn:hover {
            background: #125ca0 !important;
        }

        .link-btn {
            margin-top: 15px;
            color: gray;
            text-decoration: none;
            font-size: 15px;
        }
    </style>

</head>

<body>

<script type="text/javascript">

Ext.onReady(function () {

    Ext.create('Ext.form.Panel', {
        renderTo: Ext.getBody(),
        width: 600,
        bodyPadding: 20,
        url: 'addEmployee',
        cls: 'custom-panel',
        layout: { type: 'vbox' },

        title: "Register Employee",
        header: {
            cls: 'panel-header'
        },

        defaults: {
            xtype: 'textfield',
            width: '100%',
            allowBlank: false,
            labelAlign: 'top',
            labelStyle: 'font-weight:bold;'
        },

        items: [
            { fieldLabel: 'Username', name: 'employee.username' },
            { fieldLabel: 'Name', name: 'employee.name' },
            { fieldLabel: 'Email', name: 'employee.email', vtype: 'email' },
            { fieldLabel: 'Department', name: 'employee.department' },
            { fieldLabel: 'Salary', name: 'employee.salary' },
            { fieldLabel: 'Address', name: 'employee.address' },
            { fieldLabel: 'Country', name: 'employee.country' },
            { fieldLabel: 'Expenses', name: 'employee.expenses' },
            { fieldLabel: 'Father Name', name: 'employee.father_name' },
            { fieldLabel: 'Mother Name', name: 'employee.mother_name' },
            { fieldLabel: 'Password', name: 'employee.password', inputType: 'password' }
        ],

        buttons: [
            {
                text: 'Register',
                cls: 'custom-btn',
                width: '100%',
                handler: function () {
                    this.up('form').submit({
                        success: function () {
                            Ext.Msg.alert('Success', 'Employee Registered Successfully');
                        },
                        failure: function () {
                            Ext.Msg.alert('Error', 'Something went wrong!');
                        }
                    });
                }
            },
            {
                xtype: 'box',
                html: '<div style="text-align:center; margin-top:10px;">' +
                      '<a href="loginEmployee" class="link-btn">Already have an account? Login</a>' +
                      '</div>'
            }
        ]
    });

});
</script>

</body>
</html>
  