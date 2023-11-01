<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String pageBody = (String) request.getAttribute( "page-body" ) ;
    String context = request.getContextPath();
%>
<!doctype html>
<html>
<head>
    <title>Java web</title>
    <!--Import Google Icon Font-->
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <!-- Compiled and minified CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">
    <!-- Site CSS -->
    <link rel="stylesheet" href="<%= context %>/css/site.css?time=<%= new Date().getTime() %>" />
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />
</head>
<body>
<nav>
    <div class="nav-wrapper" style="background: #8FBC8F">
        <!-- Modal Trigger -->
        <a class="right modal-trigger auth-icon" href="#auth-modal">
            <span class="material-symbols-outlined">login</span>
        </a>

        <a href="<%= context %>/" class="right site-logo">Java 201</a> <!-- brand-logo -->
        <ul id="nav-mobile" >
            <li <%= pageBody.equals("about.jsp") ? "class='active'" : "" %>
            ><a href="<%= context %>/jsp">JSP</a></li>

            <li <%= pageBody.equals("filters.jsp") ? "class='active'" : "" %>
            ><a href="<%= context %>/filters">Filters</a></li>

            <li <%= pageBody.equals("ioc.jsp") ? "class='active'" : "" %>
            ><a href="<%= context %>/ioc">IoC</a></li>

            <li <%= pageBody.equals("db.jsp") ? "class='active'" : "" %>
            ><a href="<%= context %>/db">DB</a></li>

            <li <%= pageBody.equals("spa.jsp") ? "class='active'" : "" %>
            ><a href="<%= context %>/spa">SPA</a></li>

            <li <%= pageBody.equals("ws.jsp") ? "class='active'" : "" %>
            ><a href="<%= context %>/ws">WS</a></li>
        </ul>
    </div>
</nav>

<div class="container">
    <!-- Page Content goes here -->
    <jsp:include page="<%= pageBody %>"/>
</div>

<footer class="page-footer" style="background: #8FBC8F">
    <div class="container">
        <div class="row">
            <div class="col l6 s12">
                <h5 class="white-text">Step Learning</h5>
                <p class="grey-text text-lighten-4">Open your mind</p>
            </div>
            <div class="col l4 offset-l2 s12">
                <h5 class="white-text">Good to visit</h5>
                <ul>
                    <li><a class="grey-text text-lighten-3" href="https://materializecss.com/">Materialize CSS</a></li>
                    <li><a class="grey-text text-lighten-3" href="https://fonts.google.com/icons">Materialize Icons</a></li>
                    <li><a class="grey-text text-lighten-3" href="https://planetscale.com/">PlanetScale platform</a></li>
                </ul>
            </div>
        </div>
    </div>
</footer>

<!-- Modal Structure -->
<div id="auth-modal" class="modal">
    <div class="modal-content">
        <h4>Автентифікація на сайті</h4>
        <div class="row">
            <form class="col s12" method="post" action="" enctype="multipart/form-data">
                <div class="row">
                    <div class="input-field col s6">
                        <i class="material-icons prefix">badge</i>
                        <input name="reg-login" id="auth-login" type="text" class="validate">
                        <label for="auth-login">Логін на сайті</label>
                    </div>
                    <div class="input-field col s6">
                        <i class="material-icons prefix">lock</i>
                        <input name="auth-password" id="auth-password" type="password" class="validate">
                        <label for="auth-password">Пароль</label>
                    </div>
                </div>
            </form>
        </div>
    </div>
    <div class="modal-footer">
        <b id="auth-message"></b>
        <a href="<%= context %>/signup" class="modal-close waves-effect waves-green btn-flat"  style="background: #8FBC8F">Реєстрація</a>
        <a id="auth-sign-in"
           class="waves-effect waves-green btn-flat" style="background: #a7d9a7">Вхід</a>
    </div>
</div>



<!-- Compiled and minified JavaScript -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>
<script src="<%= context %>/js/site.js"></script>
<script src="<%= context %>/js/spa.js?time=<%= new Date().getTime() %>"></script>
</body>
</html>
