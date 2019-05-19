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

                <li><a class="active-menu" href="/managers/index"><i
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
                        添加学生
                    </h4>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal" role="form">
                        <!--用户框-->

                        <div class="form-group">
                            <label for="add-student" class="col-sm-2 control-label">选择学生</label>
                            <div class="col-sm-10">
                                <input type="hidden" disabled="disabled" class="form-control" id="add-courseId"  required="required">
                                    <select class="form-control" id="add-student">
                                    </select>
                            </div>
                        </div>


                    </form>

                </div>
                <div class="modal-footer">
                    <input type="hidden" disabled="disabled" class="form-control" id="edit-ictId"  required="required">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                    </button>

                    <button type="button" onclick="addStudent()" class="btn btn-primary">
                        添加
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
                            选课信息
                            <button data-toggle="modal" data-target="#myModal"
                                    style="background:none;border:none;float:right;outline:none;"><i class="fa fa-plus" aria-hidden="true"></i></button>
                        </div>
                        <div class="panel-body">

                            <div class="table-responsive">
                                <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                                    <thead>
                                    <tr>
                                        <th>选课学生</th>
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
        var courseId = GetQueryString('courseId');
        document.getElementById('add-courseId').value = courseId;
        var courseName = GetQueryString('courseName');
        getCourseDetail(courseId,courseName);
        getStudent(courseId);
    });
    function addStudent(){
        var courseId = document.getElementById('add-courseId').value;
        var objS = document.getElementById("add-student");
        var grade = objS.options[objS.selectedIndex];
        var userId = grade.value;
        $.ajax({
            url:"/managers/addStudentCourse",
            type:"post",
            data:{
                'courseId':courseId,
                'studentId':userId,
            },
            async:false,
            success:function(res){
                $('#myModal').modal('hide');
                location.reload();
            }
        })
    };
    function getStudent(courseId){
        $.ajax({
            url:"/managers/getStudentNotInCourse",
            type:"post",
            data:{
                'courseId':courseId
            },
            async:false,
            success:function(res){
                var msg = res.list;
                var select = document.getElementById('add-student');
                select.innerHTML = "";
                for (var i = 0; i < msg.length; i++){
                    var tem = msg[i];
                    select.appendChild(new Option(tem.name,tem.userId));

                }
            }
        })
    }
    function getCourseDetail(courseId,courseName){
        $.ajax({  // ajax登陆请求
            url:"/managers/getCourseDetail",
            type:"post",
            data:{
                'courseId':courseId,
                'courseName':courseName
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
                        {"mData" : "studentName"},
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
                                var button ="<button style='margin-right: 10px;'  id='delete' class='btn btn-primary' type='button'>移除</button>";
                                return button;
                            },

                        }

                    ] ,

                } );

            }
        });

    $(".panel-body").on('click', 'button#delete', function () {

        var data = $("#dataTables-example").DataTable().row($(this).parents("tr")).data();
        var r=confirm("确认移除学生"+data.studentName+"？");
        if (r==true)
        {
            $.ajax({
                url:"/managers/deleteStudentCourse",
                type:"post",
                data:{
                    'icsId':data.icsId,
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




    }
    function GetQueryString(name)
    {
        var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if(r!=null){
            return unescape(r[2]);
        }else{
            return null;
        }
    }
</script>


<!-- /. WRAPPER  -->
</body>
</html>
