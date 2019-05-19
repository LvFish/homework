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

                <li><a href="/teacher/index"><i
                        class="fa fa-dashboard"></i> 首页</a></li>
                <li><a href="/teacher/publish"><i class="fa fa-desktop"></i>
                    查看作业</a></li>
                <li><a class="active-menu" href="#"><i class="fa fa-bar-chart-o"></i>
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
                        打分
                    </h4>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal" role="form">
                        <!--用户框-->

                        <div class="form-group">
                            <label for="upload-grade" class="col-sm-2 control-label">打分</label>
                            <div class="col-sm-10">
                                <input type="hidden" disabled="disabled" class="form-control" id="upload-id"  required="required">
                                <input type="number"  class="form-control" id="upload-grade"  required="required">
                            </div>
                        </div>


                    </form>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                    </button>
                    <button type="button" onclick="addGrade()" class="btn btn-primary">
                        确认
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
                        批改作业
                    </h1>
                </div>
            </div>
            <!-- /. ROW  -->
            <div class="row">
                <div class="col-md-12">
                    <!-- Advanced Tables -->
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            待批改作业
                        </div>
                        <div class="panel-body">

                            <div class="table-responsive">
                                <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                                    <thead>
                                    <tr>
                                        <th>作业名</th>
                                        <th>学生</th>
                                        <th>所属课程</th>
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
        getHomework();
    });

    $(".panel-body").on('click', 'button#upload', function () {

        var data = $("#dataTables-example").DataTable().row($(this).parents("tr")).data();
        document.getElementById('upload-id').value = data.id;
        document.getElementById('upload-grade').value = "";
        /*  alert("Do you want delete " + data.username + "?"); */
        $('#myModal').modal('show');
    });

    $(".panel-body").on('click', 'button#download', function () {

        var data = $("#dataTables-example").DataTable().row($(this).parents("tr")).data();
        var fileName = data.fileUrl;
        var url = "/student/downloadFile";
        var form = $("<form></form>").attr("action", url).attr("method", "post");
        form.append($("<input></input>").attr("type", "hidden").attr("name", "fileName").attr("value", fileName));
        form.appendTo('body').submit().remove();
    });
    $(".panel-body").on('click', 'button#pass', function () {

        var data = $("#dataTables-example").DataTable().row($(this).parents("tr")).data();
        var id = data.id;
        $.ajax({
            url:"/teacher/deleteCorrect",
            type:"post",
            data:{
                "id":id,
            },
            async:false,
            success:function() {
                location.reload();
            }
        });
    });



    function addGrade(){
        var id = document.getElementById('upload-id').value;
        var grade = document.getElementById('upload-grade').value;
//        console.log(grade);
        if(grade.length<=0||grade<0||grade>100) {
            alert("请输入合法数据");
            return;
        }
        $.ajax({
            url:"/teacher/addGrade",
            type:"post",
            data:{
                "id":id,
                "grade":grade,
            },
            async:false,
            success:function() {
                alert("评分成功");
                $('#myModal').modal('hide');
                location.reload();
            }
        });

    }

    function getHomework(){
        $.ajax({  // ajax登陆请求
            url:"/teacher/getCorrectHomework",
            type:"post",
            data:{

            },
            async:false,
            success:function(res){
                var jsonDate =res;

//                        var jsonObj = eval( '(' + jsonDate + ')' );
                var msg = jsonDate.correctList;
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
                        {"mData" : "homeworkName"},
                        {"mData" : "studentName"},
                        {"mData" : "courseName"},
                        {"mData" : ""},
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
                                var button ="<button style='margin-right: 10px;' id='download' class='btn btn-primary' type='button'>查看</button>" +
                                    "<button style='margin-right: 10px;' id='upload' class='btn btn-primary' type='button'>打分</button>" +
                                    "<button style='margin-right: 10px;' id='pass' class='btn btn-primary' type='button'>跳过</button>";
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
