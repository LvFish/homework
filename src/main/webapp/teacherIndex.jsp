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
    <title>教师</title>
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
            <a class="navbar-brand" href="#">教师管理</a>
        </div>


    </nav>
    <!--/. NAV TOP  -->
    <nav class="navbar-default navbar-side" role="navigation">
        <div class="sidebar-collapse">
            <ul class="nav" id="main-menu">

                <li><a class="active-menu" href="#"><i
                        class="fa fa-dashboard"></i> 首页</a></li>
                <li><a href="/teacher/publish"><i class="fa fa-desktop"></i>
                    查看作业</a></li>
                <li><a href="/teacher/correct"><i class="fa fa-bar-chart-o"></i>
                    批改作业</a></li>
                <li><a href="/teacher/dealQuestion"><i class="fa fa-paperclip"></i>
                    处理申诉</a></li>
                <li><a href="/teacher/change"><i class="fa fa-pencil"></i>
                    修改密码</a></li>

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
                        发布作业
                    </h4>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal" role="form">
                        <!--用户框-->

                        <div class="form-group">
                            <label for="add-name" class="col-sm-2 control-label">作业名</label>
                            <div class="col-sm-10">
                                <input type="hidden" disabled="disabled" class="form-control" id="add-id"  required="required">
                                <input type="text"  class="form-control" id="add-name"  required="required">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="add-percent" class="col-sm-2 control-label">评分所占比例</label>
                            <div class="col-sm-10">
                                <input type="number"  class="form-control" id="add-percent"  required="required">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="add-deadline" class="col-sm-2 control-label">截止时间</label>
                            <div class="col-sm-10">
                                <input type="datetime-local"  class="form-control" id="add-deadline"  required="required">
                            </div>
                        </div>

                    </form>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                    </button>
                    <button type="button" onclick="addHomework()" class="btn btn-primary">
                        发布
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
                        首页 <small>查看课程</small>
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
                        </div>
                        <div class="panel-body">

                            <div class="table-responsive">
                                <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                                    <thead>
                                    <tr>
                                        <th>课程名称</th>
                                        <th>选课人数</th>
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
    });

    $(".panel-body").on('click', 'button#detail', function () {

        var data = $("#dataTables-example").DataTable().row($(this).parents("tr")).data();
//        console.log(data.homeworkId);
        window.location.href="/teacher/courseDetail?courseId="+data.courseId;
    });

    $(".panel-body").on('click', 'button#appeal', function () {

        var data = $("#dataTables-example").DataTable().row($(this).parents("tr")).data();
        document.getElementById('add-id').value = data.courseId;
        document.getElementById('add-percent').value = "";
        document.getElementById('add-name').value = "";
        document.getElementById('add-deadline').value = "";
        /*  alert("Do you want delete " + data.username + "?"); */
        $('#myModal').modal('show');
    });

    function addHomework(){
        var id = document.getElementById('add-id').value;
        var name = document.getElementById('add-name').value;
        var date = document.getElementById('add-deadline').value;
        var d = new Date(date);
        var timestamp=Math.round(d.getTime());
        var percent = document.getElementById('add-percent').value;
        console.log(id+" "+name+" "+date+" "+percent);
        if(date.length<=0||name.length<=0||percent.length<=0||percent<0||percent>100){
            alert("请输入合法数据！");return;
        }
        $.ajax({
            url:"/teacher/addHomework",
            type:"post",
            data:{
                'courseId':id,
                'name':name,
                'deadline':timestamp,
                'percent':percent
            },
            async:false,
            success:function(res){
                alert("发布成功！");
                $('#myModal').modal('hide');
            }
        });

    }

    function getAccount(){
        $.ajax({  // ajax登陆请求
            url:"/teacher/getCourse",
            type:"post",
            data:{

            },
            async:false,
            success:function(res){
                var jsonDate =res;

//                        var jsonObj = eval( '(' + jsonDate + ')' );
                var msg = jsonDate.courseList;
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
                        {"mData" : "cnt"},
                        {"mData":""},
                        /*  { "mData": 234}, */
                    ],"columnDefs": [
                        //{
                        //    "targets": -2,//编辑
                        //    "data": null,
                        //    "defaultContent": "<button id='editrow' class='btn btn-primary' type='button'><i class='fa fa-edit'></i></button>"
                        //},
                        {
                            "targets": -1,//删除
                            "data": null,
                            "render": function(data, type, row, meta) {
                                var button ="<nobr><button style='margin-right: 10px;'  id='appeal' class='btn btn-primary' type='button'>发布作业</button>" +
                                    "<button style='margin-right: 10px;'  id='detail' class='btn btn-primary' type='button'>查看选课</button></nobr>";
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
