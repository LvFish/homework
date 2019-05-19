<%--
  Created by IntelliJ IDEA.
  User: liujiawang
  Date: 2019/5/14
  Time: 10:20 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>管理员</title>
    <!-- Bootstrap Styles-->
    <link href="/assets/css/bootstrap.css" rel="stylesheet" />
    <!-- FontAwesome Styles-->
    <link href="/assets/css/font-awesome.css" rel="stylesheet" />
    <!-- Morris Chart Styles-->
    <link href="/assets/js/morris/morris-0.4.3.min.css" rel="stylesheet" />
    <!-- Custom Styles-->
    <link href="/assets/css/custom-styles.css" rel="stylesheet" />
    <!-- Google Fonts-->
    <link href='https://fonts.googleapis.com/css?family=Open+Sans'
          rel='stylesheet' type='text/css' />

    <!-- JS Scripts-->
    <!-- jQuery Js -->
    <script src="/assets/js/jquery-1.10.2.js"></script>
    <!-- Bootstrap Js -->
    <script src="/assets/js/bootstrap.min.js"></script>
    <!-- Metis Menu Js -->
    <script src="/assets/js/jquery.metisMenu.js"></script>
    <!-- Morris Chart Js -->
    <script src="/assets/js/morris/raphael-2.1.0.min.js"></script>
    <script src="/assets/js/morris/morris.js"></script>
    <!-- Custom Js -->
    <script src="/assets/js/echarts.min.js"></script>
</head>

<body>
<div id="wrapper">
    <nav class="navbar navbar-default top-navbar" role="navigation">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse"
                    data-target=".sidebar-collapse">
                <span class="sr-only">Toggle navigation</span> <span
                    class="icon-bar"></span> <span class="icon-bar"></span>
                <span
                        class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">后台管理</a>
        </div>


    </nav>
    <!--/. NAV TOP  -->
    <nav class="navbar-default navbar-side" role="navigation">
        <div class="sidebar-collapse">
            <ul class="nav" id="main-menu">

                <li><a class="active-menu" href="#"><i
                        class="fa fa-dashboard"></i> 首页</a></li>
                <li><a href="/managers/teacher"><i class="fa fa-desktop"></i>
                    教师管理</a></li>
                <li><a href="/managers/student"><i class="fa fa-bar-chart-o"></i>
                    学生管理</a></li>



            </ul>

        </div>

    </nav>
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title" id="myModalLabel">
                            修改课程
                    </h4>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal" role="form">
                        <!--用户框-->

                        <div class="form-group">
                            <label for="edit-name" class="col-sm-2 control-label">课程名</label>
                            <div class="col-sm-10">
                                <input type="hidden" disabled="disabled" class="form-control" id="edit-id"  required="required">
                                <input type="text"  class="form-control" id="edit-name"  required="required">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="edit-teacher" class="col-sm-2 control-label">任课教师</label>
                            <div class="col-sm-10">
                                <select class="form-control" id="edit-teacher">
                                </select>
                            </div>
                        </div>

                    </form>

                </div>
                <div class="modal-footer">
                    <input type="hidden" disabled="disabled" class="form-control" id="edit-ictId"  required="required">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                    </button>

                    <button type="button" onclick="editCourse()" class="btn btn-primary">
                        修改
                    </button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
    </div>
    <div class="modal fade" id="myAddModal" tabindex="-1" role="dialog" aria-labelledby="myAddModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title" id="myAddModalLabel">
                        添加课程
                    </h4>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal" role="form">
                        <!--用户框-->

                        <div class="form-group">
                            <label for="add-name" class="col-sm-2 control-label">课程名</label>
                            <div class="col-sm-10">
                                <input type="text"  class="form-control" id="add-name"  required="required">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="add-teacher" class="col-sm-2 control-label">任课教师</label>
                            <div class="col-sm-10">
                                <select class="form-control" id="add-teacher">
                                </select>
                            </div>
                        </div>

                    </form>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                    </button>

                    <button type="button" onclick="addCourse()" class="btn btn-primary">
                        保存
                    </button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
    </div>
    <!-- /. NAV SIDE  -->
    <div id="page-wrapper">
        <div id="page-inner">
            <div class="row">
                <div class="col-md-12">
                    <h1 class="page-header">
                        首页 <small>开设课程管理</small>
                    </h1>
                </div>
            </div>
            <!-- /. ROW  -->
            <div class="row">
                <div class="col-md-12">
                    <!-- Advanced Tables -->
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            课程信息
                            <button data-toggle="modal" data-target="#myAddModal"
                                    style="background:none;border:none;float:right;outline:none;"><i class="fa fa-plus" aria-hidden="true"></i></button>
                        </div>
                        <div class="panel-body">

                            <div class="table-responsive">
                                <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                                    <thead>
                                    <tr>
                                        <th>课程名称</th>
                                        <th>教师名称</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>

                                </table>
                            </div>

                        </div>
                    </div>
                    <!--End Advanced Tables -->
                </div>
            </div>
            <!-- /. PAGE WRAPPER  -->
        </div>
    </div>
</div>

<script src="/assets/js/jquery-1.10.2.js"></script>
<!-- Bootstrap Js -->
<script src="/assets/js/bootstrap.min.js"></script>
<!-- Metis Menu Js -->
<script src="/assets/js/jquery.metisMenu.js"></script>
<!-- DATA TABLE SCRIPTS -->
<script src="/assets/js/dataTables/jquery.dataTables.js"></script>
<script src="/assets/js/dataTables/dataTables.bootstrap.js"></script>

<script type="text/javascript">
    $(document).ready(function () {
        getAccount();
        getTeacher();
    });
    $(".panel-body").on('click', 'button#button1', function () {
        var data = $("#dataTables-example").DataTable().row($(this).parents("tr")).data();
//        console.log(data.homeworkId);
        window.location.href="/managers/courseDetail?courseId="+data.courseId+"&courseName="+data.courseName;
    });
    $(".panel-body").on('click', 'button#delete', function () {

        var data = $("#dataTables-example").DataTable().row($(this).parents("tr")).data();
        var r=confirm("确认移除课程"+data.courseName+"？");
        if (r==true)
        {
            $.ajax({
                url:"/managers/deleteCourse",
                type:"post",
                data:{
                    'courseId':data.courseId,
                },
                async:false,
                success:function(res){
                    location.reload();
                }
            })
        }
        else
        {

        }
        /*  alert("Do you want delete " + data.username + "?"); */

    });
    $(".panel-body").on('click', 'button#edit', function () {

        var data = $("#dataTables-example").DataTable().row($(this).parents("tr")).data();
        document.getElementById('edit-id').value = data.courseId;
        document.getElementById('edit-ictId').value = data.ictId;
        document.getElementById('edit-name').value = data.courseName;

        $.ajax({
            url:"/managers/getAllTeacher",
            type:"post",
            data:{

            },
            async:false,
            success:function(res){
                var msg = res.list;
                var select = document.getElementById('edit-teacher');
                select.innerHTML = "";
                for (var i = 0; i < msg.length; i++){
                    var tem = msg[i];
                    console.log(tem.name+" "+tem.userId);
                    select.appendChild(new Option(tem.name,tem.userId));
                    if (tem.name == data.teacherName){
                        select.options[i].selected = true;
                    }
                }
                $('#myModal').modal('show');
            }
        })
        /*  alert("Do you want delete " + data.username + "?"); */

    });
    function getTeacher(){
        $.ajax({
            url:"/managers/getAllTeacher",
            type:"post",
            data:{

            },
            async:false,
            success:function(res){
                var msg = res.list;
                var select = document.getElementById('add-teacher');
                select.innerHTML = "";
                for (var i = 0; i < msg.length; i++){
                    var tem = msg[i];
                    select.appendChild(new Option(tem.name,tem.userId));

                }
            }
        })
    }

    function addCourse(){
        var courseName = document.getElementById('add-name').value;
        var objS = document.getElementById("add-teacher");
        var grade = objS.options[objS.selectedIndex];
        var teacherId = grade.value;
        console.log(courseName+" "+teacherId);
        if(courseName.length<=0){
            alert("请输入正确课程");return;
        }
        $.ajax({
            url:"/managers/addCourse",
            type:"post",
            data:{
                'courseName':courseName,
                'teacherId':teacherId,
            },
            async:false,
            success:function(res){
                $('#myAddModal').modal('hide');
                location.reload();
            }
        })

    }
    function editCourse(){
        var id = document.getElementById("edit-id").value;
        var ictId = document.getElementById("edit-ictId").value;
        var courseName = document.getElementById("edit-name").value;
        var objS = document.getElementById("edit-teacher");
        var grade = objS.options[objS.selectedIndex];
        var teacherId = grade.value;
        console.log(id+" "+teacherId);
        $.ajax({
            url:"/managers/editCourse",
            type:"post",
            data:{
                "id":id,
                "courseName":courseName,
                'teacherId':teacherId,
                'ictId':ictId,
            },
            async:false,
            success:function(res){
                $('#myModal').modal('hide');
                location.reload();
            }
        })
    }

    function getAccount(){
        $.ajax({  // ajax登陆请求
            url:"/managers/getCourse",
            type:"post",
            data:{

            },
            async:false,
            success:function(res){
                var jsonDate =res;

//                        var jsonObj = eval( '(' + jsonDate + ')' );
                var msg = jsonDate.list;
                $('#dataTables-example').dataTable().fnDestroy();//sample_1是table的id
                $('#dataTables-example').dataTable( {
                    searching : false,
                    "oLanguage" : { // 国际化配置
                        "sProcessing" : "正在获取数据，请稍后...",
                        "sLengthMenu" : "显示 _MENU_ 条",
                        "sZeroRecords" : "没有找到数据",
                        "sInfo" : "从 _START_ 到  _END_ 条记录 总记录数为 _TOTAL_ 条",
                        "sInfoEmpty" : "记录数为0",
                        "sInfoFiltered" : "(全部记录数 _MAX_ 条)",
                        "sInfoPostFix" : "",
                        "sSearch" : "查询",
                        "sUrl" : "",
                        "oPaginate" : {
                            "sFirst" : "第一页",
                            "sPrevious" : "上一页",
                            "sNext" : "下一页",
                            "sLast" : "最后一页"
                        }
                    },

                    "aaData":msg,

                    "aoColumns": [
                        // 按顺序来
                        {"mData" : "courseName"},
                        {"mData" : "teacherName"},
                        {"mData" : ''},
                        /*  { "mData": 234}, */
                    ],
                    "columnDefs": [
                        //{
                        //    "targets": -2,//编辑
                        //    "data": null,
                        //    "defaultContent": "<button id='editrow' class='btn btn-primary' type='button'><i class='fa fa-edit'></i></button>"
                        //},
                        {
                            "targets": -1,//删除
                            "data": null,
                            "render": function(data, type, row, meta) {
                                var button ="<button style='margin-right: 10px;'  id='edit' class='btn btn-primary' type='button'>修改</button>" +
                                    "<button style='margin-right: 10px;'  id='button1' class='btn btn-primary' type='button'>学生管理</button>" +
                                    "<button style='margin-right: 10px;'  id='delete' class='btn btn-primary' type='button'>移除</button>";
                                return button;
                            },

                        }

                    ] ,

                } );

            }
        });

    }
</script>


<!-- /. WRAPPER  -->
</body>
</html>
