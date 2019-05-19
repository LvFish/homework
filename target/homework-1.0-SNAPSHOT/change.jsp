<%--
  Created by IntelliJ IDEA.
  User: liujiawang
  Date: 2019/5/14
  Time: 10:20 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>学生</title>
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
            <a class="navbar-brand" href="#">学生管理</a>
        </div>


    </nav>
    <!--/. NAV TOP  -->
    <nav class="navbar-default navbar-side" role="navigation">
        <div class="sidebar-collapse">
            <ul class="nav" id="main-menu">

                <li><a href="/student/index"><i
                        class="fa fa-dashboard"></i> 首页</a></li>
                <li><a href="/student/homework"><i class="fa fa-desktop"></i>
                    查看作业</a></li>
                <li><a href="/student/correct"><i class="fa fa-bar-chart-o"></i>
                    批改作业</a></li>
                <li><a class="active-menu" href="#"><i class="fa fa-pencil"></i>
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
                        修改密码
                    </h1>
                </div>
            </div>
            <!-- /. ROW  -->
            <div class="row">
                <div class="col-md-3">
                </div>
                <div class="col-md-6">
                    <!-- Advanced Tables -->

                    <div class="form-bottom">
                        <form role="form" class="login-form" action="/student/change" method="post">
                            <div class="form-group">
                                <label class="sr-only" for="form-username">旧密码</label>
                                <input type="password" id="username" name="password" placeholder="旧密码..." class="form-username form-control" id="form-username">
                            </div>
                            <div class="form-group">
                                <label class="sr-only" for="form-password1">新密码</label>
                                <input type="password" id="password" name="password1" placeholder="新密码..." class="form-password form-control" id="form-password1">
                            </div>
                            <div class="form-group">
                                <label class="sr-only" for="form-password2">重复</label>
                                <input type="password" id="password2" name="password2" placeholder="重复..." class="form-password form-control" id="form-password2">
                            </div>
                            <c:if test="${changeMessage!=null}">
                                <div class="form-group">
                                    <p>${changeMessage}</p>
                                </div>
                            </c:if>
                            <div class="form-group">
                                <button type="submit" class="btn btn-primary" style="width: 100%;">修改密码</button>
                            </div>


                        </form>

                    </div>
                    <div class="col-md-3">
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

    });


</script>


<!-- /. WRAPPER  -->
</body>
</html>
