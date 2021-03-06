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
                <li><a class="active-menu" href="#"><i class="fa fa-desktop"></i>
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

    <!-- /. NAV SIDE  -->
    <div id="page-wrapper">
        <div id="page-inner">
            <div class="row">
                <div class="col-md-12">
                    <h1 class="page-header">
                        查看作业
                    </h1>
                </div>
            </div>
            <!-- /. ROW  -->
            <div class="row">
                <div class="col-md-12">
                    <!-- Advanced Tables -->
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            作业信息
                        </div>
                        <div class="panel-body">

                            <div class="table-responsive">
                                <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                                    <thead>
                                    <tr>
                                        <th>作业名称</th>
                                        <th>课程名称</th>
                                        <th>截止时间</th>
                                        <th>完成情况</th>
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


    $(".panel-body").on('click', 'button#detail', function () {

        var data = $("#dataTables-example").DataTable().row($(this).parents("tr")).data();
//        console.log(data.homeworkId);
        window.location.href="/teacher/detail?homeworkId="+data.homeworkId;
    });

    $(".panel-body").on('click', 'button#delete', function () {

        var data = $("#dataTables-example").DataTable().row($(this).parents("tr")).data();
//        console.log(data.homeworkId);
        var r=confirm("确认移除作业"+data.name+"？");
        if (r==true)
        {
            $.ajax({
                url:"/teacher/deleteHomework",
                type:"post",
                data:{
                    'homeworkId':data.homeworkId,
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
    });


    function getHomework(){
        $.ajax({  // ajax登陆请求
            url:"/teacher/getHomework",
            type:"post",
            data:{

            },
            async:false,
            success:function(res){
                var jsonDate =res;

//                        var jsonObj = eval( '(' + jsonDate + ')' );
                var msg = jsonDate.homeworkList;
                $('#dataTables-example').dataTable().fnDestroy();//sample_1是table的id
                $('#dataTables-example').dataTable( {
                    "bAutoWidth" : false,
                    "bScrollInfinite" : false,
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
                        {"mData" : "name"},
                        {"mData" : "courseName"},
                        {"mData" : function(obj){
                            return getMyDate(obj.deadline.time)//通过调用函数来格式化所获取的时间戳
                        }},
                        {"mData" : function(obj){
                            return obj.unFinished+"/"+obj.allNumber;//通过调用函数来格式化所获取的时间戳
                        }},
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
                                var button ="<nobr><button style='margin-right: 10px;'  id='detail' class='btn btn-primary' type='button'>查看详情</button>" +
                                    "<button style='margin-right: 10px;'  id='delete' class='btn btn-primary' type='button'>删除</button></nobr>";
                                return button;
                            },

                        }

                    ] ,


                } );

            }
        });
        function getMyDate(time){
            var oDate = new Date(time),
                oYear = oDate.getFullYear(),
                oMonth = oDate.getMonth()+1,
                oDay = oDate.getDate(),
                oHour = oDate.getHours(),
                oMin = oDate.getMinutes(),
                oSec = oDate.getSeconds();
            oTime = oYear +'-'+ getzf(oMonth) +'-'+ getzf(oDay)+" "+getzf(oHour)+":"+getzf(oMin)+":"+getzf(oSec);//最后拼接时间
            return oTime;
        };

        //补0操作,当时间数据小于10的时候，给该数据前面加一个0
        function getzf(num){
            if(parseInt(num) < 10){
                num = '0'+num;
            }
            return num;
        }

    }
</script>


<!-- /. WRAPPER  -->
</body>
</html>
