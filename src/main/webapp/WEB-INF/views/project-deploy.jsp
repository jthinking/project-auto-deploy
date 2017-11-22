
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html>

<head>
    <title>项目部署 - 超级管理员后台</title>
    <meta charset="utf-8">
    <!-- Bootstrap -->
    <link href="${pageContext.request.contextPath}/static/bootstrap/css/bootstrap.min.css" rel="stylesheet" media="screen">
    <link href="${pageContext.request.contextPath}/static/bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet" media="screen">
    <link href="${pageContext.request.contextPath}/static/assets/styles.css" rel="stylesheet" media="screen">
    <link href="${pageContext.request.contextPath}/static/webuploader/css/webuploader.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/static/webuploader/css/style.css" rel="stylesheet" type="text/css">
    <script src="${pageContext.request.contextPath}/static/vendors/modernizr-2.6.2-respond-1.1.0.min.js"></script>
</head>

<body>
<div class="navbar navbar-fixed-top">
    <div class="navbar-inner">
        <div class="container-fluid">
            <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse"> <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </a>
            <a class="brand" href="#">超级管理员后台</a>
            <div class="nav-collapse collapse">
                <ul class="nav pull-right">
                    <li class="dropdown">
                        <a href="#" role="button" class="dropdown-toggle" data-toggle="dropdown"> <i class="icon-user"></i> ${username} <i class="caret"></i>

                        </a>
                        <ul class="dropdown-menu">
                            <li>
                                <a tabindex="-1" href="#">Profile</a>
                            </li>
                            <li class="divider"></li>
                            <li>
                                <a tabindex="-1" href="${pageContext.request.contextPath}/user/logout">Logout</a>
                            </li>
                        </ul>
                    </li>
                </ul>
                <ul class="nav">
                    <li class="active">
                        <a href="#">部署</a>
                    </li>
                </ul>
            </div>
            <!--/.nav-collapse -->
        </div>
    </div>
</div>
<div class="container-fluid">
    <div class="row-fluid">
        <div class="span3" id="sidebar">
            <ul class="nav nav-list bs-docs-sidenav nav-collapse collapse">
                <li class="active">
                    <a href="#"><i class="icon-chevron-right"></i> 项目部署</a>
                </li>
                <li>
                    <a href="#"><i class="icon-chevron-right"></i> 项目部署</a>
                </li>
            </ul>
        </div>
        <!--/span-->
        <div class="span9" id="content">

            <!-- morris stacked chart -->
            <div class="row-fluid">
                <!-- block -->
                <div class="block">
                    <div class="navbar navbar-inner block-header">
                        <div class="muted pull-left">上传项目</div>
                        <div class="pull-right">
                            <span class="badge badge-warning">了解更多</span>
                        </div>
                    </div>
                    <div class="block-content collapse in">

                        <div class="span12">
                            <form class="form-horizontal">
                                <fieldset>
                                    <legend>环境变量设置</legend>
                                    <div class="control-group">
                                        <label class="control-label" for="projectName">项目名</label>
                                        <div class="controls">
                                            <input class="input-xlarge focused" id="projectName" type="text" value="mp-mall">
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <label class="control-label" for="backupDir">项目备份目录</label>
                                        <div class="controls">
                                            <input class="input-xlarge focused" id="backupDir" type="text" value="/root/Backup/Project">
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <label class="control-label" for="unzipDir">压缩包解压目录</label>
                                        <div class="controls">
                                            <input class="input-xlarge focused" id="unzipDir" type="text" value="/root/Temp/Project">
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <label class="control-label" for="uploadDir">上传保存目录</label>
                                        <div class="controls">
                                            <input class="input-xlarge focused" id="uploadDir" type="text" value="/root/Upload/Project">
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <label class="control-label" for="tomcatHomes">Tomcat根目录</label>
                                        <div class="controls">
                                            <input class="input-xlarge focused" id="tomcatHomes" type="text" value="/usr/local/tomcats/tomcat-01,/usr/local/tomcats/tomcat-02">
                                            <span class="help-inline">多个目录用英文逗号分隔</span>
                                        </div>
                                    </div>


                                </fieldset>
                            </form>

                        </div>

                        <legend>选择文件</legend>
                        <div class="span12">
                            <div id="uploader" class="wu-example">
                                <!--用来存放文件信息-->
                                <div id="thelist" class="uploader-list"></div>
                                <div class="btns">
                                    <div id="picker">选择文件</div>
                                    <button id="ctlBtn" class="btn btn-default">开始上传</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- /block -->
            </div>

            <!-- morris graph chart -->
            <div class="row-fluid section">
                <!-- block -->
                <div class="block">
                    <div class="navbar navbar-inner block-header">
                        <div class="muted pull-left">实时监控 <small></small></div>
                        <div class="pull-right"><span class="badge badge-warning">了解更多</span>

                        </div>
                    </div>
                    <div class="block-content collapse in">
                        <div id="console" class="span12" style="height:300px;overflow-y:scroll;background:black;color:white;font-weight:bold;">

                        </div>
                    </div>
                </div>
                <!-- /block -->
            </div>

        </div>
    </div>
    <hr>
    <footer>
        <p>&copy; jthinking.com 2015-2017</p>
    </footer>
</div>
<!--/.fluid-container-->

<script src="${pageContext.request.contextPath}/static/vendors/jquery-1.9.1.min.js"></script>
<script src="${pageContext.request.contextPath}/static/vendors/jquery.knob.js"></script>
<script src="${pageContext.request.contextPath}/static/vendors/raphael-min.js"></script>

<script src="${pageContext.request.contextPath}/static/bootstrap/js/bootstrap.min.js"></script>

<script src="${pageContext.request.contextPath}/static/assets/scripts.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/static/webuploader/js/webuploader.js"></script>
<script>
    var BASE_URL = "${pageContext.request.contextPath}";
    var WEB_SOCKET_URL = "${WEB_SOCKET_URL}";
    var FILE_UPLOAD_URL = "${FILE_UPLOAD_URL}";
    var PROJECT_NAME = "mp-mall";
    var MODULE_NAME = "deploy";
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/webuploader/js/getting-started.js"></script>
<script src="${pageContext.request.contextPath}/static/js/websocket.js"></script>
</body>

</html>
