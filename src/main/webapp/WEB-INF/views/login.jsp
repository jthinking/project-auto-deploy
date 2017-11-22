
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
    <title>管理员登录 - 超级管理员后台 - 兆美商城</title>
    <!-- Bootstrap -->
    <link href="${pageContext.request.contextPath}/static/bootstrap/css/bootstrap.min.css" rel="stylesheet" media="screen">
    <link href="${pageContext.request.contextPath}/static/bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet" media="screen">
    <link href="${pageContext.request.contextPath}/static/assets/styles.css" rel="stylesheet" media="screen">
    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
    <script src="${pageContext.request.contextPath}/static/js/vendor/modernizr-2.6.2-respond-1.1.0.min.js"></script>
</head>
<body id="login">
<div class="container">

    <form class="form-signin" action="/user/login" method="post">
        <h2 class="form-signin-heading">管理员登录</h2>
        <input type="text" name="email" class="input-block-level" placeholder="邮箱地址">
        <input type="hidden" name="service" value="${service}">
        <button class="btn btn-large btn-primary" type="submit">登录</button>
    </form>

</div> <!-- /container -->
<script src="${pageContext.request.contextPath}/static/vendors/jquery-1.9.1.min.js"></script>
<script src="${pageContext.request.contextPath}/static/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>
