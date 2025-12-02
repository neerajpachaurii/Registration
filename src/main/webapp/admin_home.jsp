<%-- <%@ taglib prefix="s" uri="/struts-tags"%>

<%
com.example.model.Employee emp = (com.example.model.Employee) session.getAttribute("loggedEmployee");
if (emp == null) {
    response.sendRedirect("loginEmployee.jsp");
    return;
}
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin Dashboard</title>

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css"
	rel="stylesheet" />

<style>
#sidebar {
	width: 250px;
	min-height: 100vh;
	background: #0d6efd;
	color: white;
	padding: 20px;
	position: fixed;
	top: 0;
	left: 0;
}

#content {
	margin-left: 260px;
}

.logout-btn {
	position: absolute;
	bottom: 20px;
	left: 20px;
	right: 20px;
	font-weight: bold;
	border-radius: 30px;
}
</style>

</head>
<body class="bg-light">

	<!-- ========== SIDEBAR ========== -->
	<div id="sidebar">
		<h4 class="mb-4">Admin Panel</h4>

		<p>
			<strong>Welcome:</strong><br><%=emp.getName()%></p>
		<p>
			<strong>Role:</strong><br> ADMIN
		</p>

		<div class="d-grid gap-2">
			<a href="adminDashboard" class="btn btn-light">Dashboard</a> <a
				href="addProject" class="btn btn-light">Add Project</a> <a
				href="viewProjects" class="btn btn-light">View Projects</a> <a
				href="viewEmployees" class="btn btn-light">Employees</a>
		</div>

		<a href="index.jsp" class="btn btn-outline-light logout-btn">Logout</a>
	</div>

	<!-- ========== MAIN CONTENT ========== -->
	<div id="content" class="p-4">

		<div class="d-flex justify-content-between align-items-center mb-3">


			<h2 class="text-primary m-0">Admin Dashboard</h2>


			<div class="d-flex gap-2">


				<a href="add_Project.jsp" class="btn btn-success"> + Add Project
				</a>

				<!-- Download Employees (Visible only if admin & data exists) -->
				<s:if
					test="%{#session.loggedEmployee != null 
                && #session.loggedEmployee.role == 'ADMIN' 
                && employees != null && employees.size() > 0}">
					<a href="exportEmployees" class="btn btn-success btn-sm"> <i
						class="bi bi-file-earmark-excel"></i> Download Data
					</a>
				</s:if>

			</div>
		</div>


		<div class="row mt-4">
			<div class="col-md-4">
				<div class="card p-3 shadow">
					<h5>Total Employees</h5>
					<h2>
						<s:property value="employees.size()" />
					</h2>
				</div>
			</div>

			<div class="col-md-4">
				<div class="card p-3 shadow">
					<h5>Total Projects</h5>
					<h2>
						<s:property value="projects.size()" />
					</h2>
				</div>
			</div>
		</div>


		<!-- SEARCH PANEL -->
		<div class="card p-3 mt-4 shadow">
			<form action="adminDashboard" method="get" class="row g-3">

				<div class="col-md-3">
					<label>Project Title</label>
					<s:textfield name="searchTitle" cssClass="form-control" />
				</div>

				<div class="col-md-3">
					<label>Project Owner</label>
					<s:textfield name="searchOwner" cssClass="form-control" />
				</div>

				<div class="col-md-3">
					<label>Employee Department</label>
					<s:textfield name="searchDept" cssClass="form-control" />
				</div>

				<div class="col-md-3">
					<label>Employee Email</label>
					<s:textfield name="searchEmail" cssClass="form-control" />
				</div>

				<div class="col-12">
					<button class="btn btn-primary">Search</button>
					<a href="adminDashboard" class="btn btn-secondary">Reset</a>
				</div>

			</form>
		</div>


		<!-- ========== PROJECT TABLE ========== -->
		<div class="card p-3 mt-4 shadow">
			<h4>Projects</h4>

			<table class="table table-bordered table-striped mt-3">
				<thead class="table-dark">
					<tr>
						<th>Title</th>
						<th>Description</th>
						<th>Owner</th>
						<!-- <th>File</th> -->
						<th>Assigned To</th>
						<th>Assign</th>
					</tr>
				</thead>

				<tbody>
					<s:iterator value="projects" var="p">
						<tr>
							<td><s:property value="#p.title" /></td>

							<td style="max-width: 300px; white-space: pre-wrap;"><s:property
									value="#p.description" /></td>

							<td><s:property value="#p.owner.name" /></td>

							<td><s:if test="#p.filepath != null">
									<a
										href="<%=request.getContextPath()%>/<s:property value='#p.filepath'/>"
										target="_blank">Download</a>
								</s:if></td>

							<!-- SHOW ASSIGNED USERS -->
							<td><s:iterator value="#p.allowedUsers">
									<span class="badge bg-primary me-1"> <s:property
											value="name" />
									</span>
								</s:iterator></td>


							<td style="min-width: 200px;">
								<form action="assignProjectAccess" method="post" class="d-flex">
									<input type="hidden" name="projectId"
										value="<s:property value='#p.id'/>" /> <select name="userIds"
										class="form-select form-select-sm me-2">
										<s:iterator value="employees" var="e">
											<option value="<s:property value='#e.id'/>">
												<s:property value="#e.name" /> (
												<s:property value="#e.department" />)
											</option>
										</s:iterator>
									</select>

									<button class="btn btn-sm btn-success">Assign</button>
								</form>
							</td>
						</tr>
					</s:iterator>
				</tbody>
			</table>

			<!-- PAGINATION -->
			<div class="mt-3">
				<s:if test="totalPages > 1">

					<a class="btn btn-sm btn-outline-primary"
						href="adminDashboard?page=1">First</a>

					<a class="btn btn-sm btn-outline-primary"
						href="adminDashboard?page=<s:property value='page-1'/>"> Prev
					</a>

					<span class="mx-2"> Page <s:property value="page" /> / <s:property
							value="totalPages" />
					</span>

					<a class="btn btn-sm btn-outline-primary"
						href="adminDashboard?page=<s:property value='page+1'/>"> Next
					</a>

					<a class="btn btn-sm btn-outline-primary"
						href="adminDashboard?page=<s:property value='totalPages'/>">
						Last </a>

				</s:if>
			</div>

			<!-- ========== EMPLOYEE TABLE ========== -->
			<div class="card p-3 mt-4 shadow">
				<h4>Employees</h4>

				<table class="table table-bordered table-striped mt-3">
					<thead class="table-dark">
						<tr>
							<th>Name</th>
							<th>Department</th>
							<th>Email</th>
							<th>Salary</th>
							<th>Status</th>
							<th>Action</th>
						</tr>
					</thead>

					<tbody>
						<s:iterator value="employees" var="e">
							<tr>
								<td><s:property value="#e.name" /></td>
								<td><s:property value="#e.department" /></td>
								<td><s:property value="#e.email" /></td>
								<td><s:property value="#e.salary" /></td>

								<td><s:if test="#e.status == 'ACTIVE'">
										<span class="badge bg-success"> <s:property
												value="#e.status" />
										</span>
									</s:if> <s:else>
										<span class="badge bg-danger"> <s:property
												value="#e.status" />
										</span>
									</s:else></td>

								<td><s:if test="#e.status == 'ACTIVE'">
										<form action="updateEmployeeStatus" method="post"
											style="display: inline;">
											<input type="hidden" name="id"
												value="<s:property value='#e.id'/>" /> <input type="hidden"
												name="status" value="INACTIVE" />
											<button type="submit" class="btn btn-sm btn-danger"
												onclick="return confirm('Are you sure you want to deactivate this user?');">
												Inactivate</button>
										</form>
									</s:if> <s:else>
										<form action="updateEmployeeStatus" method="post"
											style="display: inline;">
											<input type="hidden" name="id"
												value="<s:property value='#e.id'/>" /> <input type="hidden"
												name="status" value="ACTIVE" />
											<button type="submit" class="btn btn-sm btn-success"
												onclick="return confirm('Are you sure you want to activate this user?');">
												Activate</button>
										</form>
									</s:else></td>
							</tr>
						</s:iterator>

					</tbody>
				</table>

			</div>

		</div>
</body>
</html> --%>


 
 <%@ taglib prefix="s" uri="/struts-tags"%>
<%
    com.example.model.Employee emp = (com.example.model.Employee) session.getAttribute("loggedEmployee");
    if (emp == null) {
        response.sendRedirect("loginEmployee.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet"/>

    <link rel="stylesheet" type="text/css"
          href="https://cdnjs.cloudflare.com/ajax/libs/extjs/6.2.0/classic/theme-triton/resources/theme-triton-all.css"/>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/extjs/6.2.0/ext-all.js"></script>

    <script src="<%=request.getContextPath()%>/dwr/engine.js"></script>
    <script src="<%=request.getContextPath()%>/dwr/util.js"></script>
    <script src="<%=request.getContextPath()%>/dwr/interface/ProjectDwr.js"></script>
    <script src="<%=request.getContextPath()%>/dwr/interface/EmployeeDwr.js"></script>

    <style>
        #sidebar { width:250px; min-height:100vh; background:#0d6efd; color:white; padding:20px; position:fixed; }
        #content { margin-left:270px; }
        .big-badge { font-size:18px; padding:10px 18px; border-radius:8px; }
        
        .x-grid-cell-inner {
            white-space: normal !important;
            line-height: 18px !important;
            padding-top: 6px !important;
            padding-bottom: 6px !important;
        }
    </style>
</head>

<body class="bg-light">

<div id="sidebar">
    <h4>Admin Panel</h4>
    <p><strong>Welcome:</strong> <%=emp.getName()%></p>
    <p><strong>Role:</strong> ADMIN</p>

    <div class="d-grid gap-2">
        <a href="adminDashboard.action" class="btn btn-light">Dashboard</a>
        <a href="addProject.action" class="btn btn-light">Add Project</a>
        <a href="viewProjects.action" class="btn btn-light">View Projects</a>
        <a href="viewEmployees.action" class="btn btn-light">Employees</a>
    </div>

    <a href="logoutEmployee" class="btn btn-outline-light mt-5">Logout</a>
</div>

<div id="content" class="p-4">

    <h2 class="text-primary">Admin Dashboard</h2>

    <div class="mb-3">
        <a href="exportProjects.action" class="btn btn-outline-success btn-sm">Export Projects</a>
        <a href="exportEmployees.action" class="btn btn-outline-success btn-sm">Export Employees</a>
        <a href="employeeReport.action" class="btn btn-outline-primary btn-sm">Print Employee Report</a>
        

        <span class="badge bg-primary big-badge ms-3">
            Projects: <span id="totalProjects">0</span>
        </span>

        <span class="badge bg-success big-badge ms-2">
            Employees: <span id="totalEmployees">0</span>
        </span>
    </div>


    <!-- PROJECTS -->
    <div class="card p-3 mb-4">
        <h4>Projects</h4>
        <div id="projectsGrid" style="height:360px;"></div>

        <div class="mt-3 text-end">
            <button id="pFirst" class="btn btn-sm btn-outline-primary">First</button>
            <button id="pPrev" class="btn btn-sm btn-outline-primary">Prev</button>
            <span class="mx-2" id="pPageInfo"></span>
            <button id="pNext" class="btn btn-sm btn-outline-primary">Next</button>
            <button id="pLast" class="btn btn-sm btn-outline-primary">Last</button>
        </div>
    </div>

    <!-- EMPLOYEES -->
    <div class="card p-3 mb-4">
        <h4>Employees</h4>
        <div id="employeesGrid" style="height:360px;"></div>

        <div class="mt-3 text-end">
            <button id="eFirst" class="btn btn-sm btn-outline-primary">First</button>
            <button id="ePrev" class="btn btn-sm btn-outline-primary">Prev</button>
            <span class="mx-2" id="ePageInfo"></span>
            <button id="eNext" class="btn btn-sm btn-outline-primary">Next</button>
            <button id="eLast" class="btn btn-sm btn-outline-primary">Last</button>
        </div>
    </div>

</div>


<script>

Ext.onReady(function(){

    var PAGE_SIZE = 5;
    var pPage = 1, pTotalPages = 1;
    var ePage = 1, eTotalPages = 1;
    var allProjects = [];
    var allEmployees = [];

    var projectStore = Ext.create('Ext.data.Store', {
        fields: ['id','title','description','owner','allowedUsers'],
        data: []
    });

    var employeeStore = Ext.create('Ext.data.Store', {
        fields: ['id','name','email','department','salary','status'],
        data: []
    });


    /* -------- PROJECT GRID -------- */
    Ext.create('Ext.grid.Panel',{
        renderTo: 'projectsGrid',
        store: projectStore,
        height: 360,
        width: '100%',
        columns: [

            { text:'Title', dataIndex:'title', flex:1 },

            { text:'Description', dataIndex:'description', flex:2 },

            {
                text:'Owner',
                dataIndex:'owner',
                width:150,
                renderer:function(v){
                    if(v && v.name){ return v.name; }
                    return "";
                }
            },

            {
                text:'Assigned To',
                dataIndex:'allowedUsers',
                width:250,
                renderer:function(v){
                    if(!v || v.length === 0) return "None";
                    return v.map(x => x.name).join("<br>");
                }
            },

            {
                text:'Assign Access',
                width:250,
                renderer:function(val, meta, rec){

                    var projectId = rec.data.id;

                    var options = "";
                    for (var i = 0; i < allEmployees.length; i++) {
                        var e = allEmployees[i];
                        options += "<option value='" + e.id + "'>" + e.name + "</option>";
                    }

                    return ""
                        + "<select class='form-select form-select-sm' id='assign_" + projectId + "'>"
                        + options
                        + "</select>"
                        + "<button onclick='assignAccess(" + projectId + ")' "
                        + "class='btn btn-sm btn-success mt-1 w-100'>Assign</button>";
                }
            }

        ]
    });


    /* ---------- EMPLOYEE GRID --------- */
    Ext.create('Ext.grid.Panel',{
        renderTo: 'employeesGrid',
        store: employeeStore,
        height: 360,
        width: '100%',
        columns: [

            { text:'Name', dataIndex:'name', flex:1 },
            { text:'Email', dataIndex:'email', width:220 },
            { text:'Department', dataIndex:'department', width:150 },
            { text:'Salary', dataIndex:'salary', width:100 },

            {
                text:'Status',
                dataIndex:'status',
                width:100,
                renderer:function(v){
                    return v === 'ACTIVE'
                        ? "<span class='badge bg-success'>ACTIVE</span>"
                        : "<span class='badge bg-danger'>INACTIVE</span>";
                }
            },

            {
                text:'Action',
                width:140,
                renderer:function(v, meta, rec){

                    var id = rec.data.id;
                    var st = rec.data.status;

                    if(st === 'ACTIVE'){
                        return "<button onclick='inactivateEmployee("+id+")' class='btn btn-sm btn-danger'>Inactivate</button>";
                    }
                    return "<button onclick='activateEmployee("+id+")' class='btn btn-sm btn-success'>Activate</button>";
                }
            }
        ]
    });



    /* -------- COUNTER UPDATE ---------- */
    function updateCounters(){
        document.getElementById("totalProjects").innerHTML = allProjects.length;
        document.getElementById("totalEmployees").innerHTML = allEmployees.length;
    }


    /* -------- PAGINATION ---------- */
    function paginateProjects(){
        pTotalPages = Math.ceil(allProjects.length / PAGE_SIZE);
        var start = (pPage - 1) * PAGE_SIZE;
        projectStore.loadData(allProjects.slice(start, start + PAGE_SIZE));
        document.getElementById("pPageInfo").innerHTML =
            "Page " + pPage + " / " + pTotalPages;
    }

    function paginateEmployees(){
        eTotalPages = Math.ceil(allEmployees.length / PAGE_SIZE);
        var start = (ePage - 1) * PAGE_SIZE;
        employeeStore.loadData(allEmployees.slice(start, start + PAGE_SIZE));
        document.getElementById("ePageInfo").innerHTML =
            "Page " + ePage + " / " + eTotalPages;
    }


    /* -------- AJAX ASSIGN ACCESS (NO REDIRECT) ------- */
    window.assignAccess = function(projectId) {

        var selectedEmployeeId =
            document.getElementById("assign_" + projectId).value;

        Ext.Ajax.request({
            url: "assignProjectAccess",
            method: "POST",
            params: {
                projectId: projectId,
                userIds: selectedEmployeeId
            },

            success: function(response) {
                alert("Employee assigned successfully!");

                ProjectDwr.getProjects(function(data){
                    allProjects = data || [];
                    pPage = 1;
                    paginateProjects();
                    updateCounters();
                });

                EmployeeDwr.getEmployees(function(data){
                    allEmployees = data || [];
                    ePage = 1;
                    paginateEmployees();
                    updateCounters();
                });
            },

            failure: function() {
                alert("Failed to assign access!");
            }
        });
    };


    /* -------- LOAD DWR DATA -------- */
    function loadData(){

        ProjectDwr.getProjects(function(data){
            allProjects = data || [];
            pPage = 1;
            paginateProjects();
            updateCounters();
        });

        EmployeeDwr.getEmployees(function(data){
            allEmployees = data || [];
            ePage = 1;
            paginateEmployees();
            updateCounters();
        });
    }

    loadData();


    /* -------- PAGINATION BUTTONS -------- */
    document.getElementById("pFirst").onclick = function(){ pPage=1; paginateProjects(); };
    document.getElementById("pPrev").onclick  = function(){ if(pPage>1){ pPage--; paginateProjects(); } };
    document.getElementById("pNext").onclick  = function(){ if(pPage<pTotalPages){ pPage++; paginateProjects(); } };
    document.getElementById("pLast").onclick  = function(){ pPage=pTotalPages; paginateProjects(); };

    document.getElementById("eFirst").onclick = function(){ ePage=1; paginateEmployees(); };
    document.getElementById("ePrev").onclick  = function(){ if(ePage>1){ ePage--; paginateEmployees(); } };
    document.getElementById("eNext").onclick  = function(){ if(ePage<eTotalPages){ ePage++; paginateEmployees(); } };
    document.getElementById("eLast").onclick  = function(){ ePage=eTotalPages; paginateEmployees(); };

});


/* -------- EMPLOYEE STATUS -------- */
function inactivateEmployee(id){
    if(confirm("Deactivate this employee?")){
        window.location = "updateEmployeeStatus?id=" + id + "&status=INACTIVE";
    }
}

function activateEmployee(id){
    if(confirm("Activate this employee?")){
        window.location = "updateEmployeeStatus?id=" + id + "&status=ACTIVE";
    }
}

</script>

</body>
</html>


