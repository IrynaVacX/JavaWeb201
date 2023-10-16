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
    <link rel="stylesheet" href="<%= context%>/css/site.css">
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
        <p>A bunch of text</p>
    </div>
    <div class="modal-footer">
        <a href="<%= context %>/signup" class="modal-close waves-effect waves-green btn-flat"  style="background: #8FBC8F">Реєстрація</a>
        <a href="#1" class="modal-close waves-effect waves-green btn-flat" style="background: #a7d9a7">Вхід</a>
    </div>
</div>

<!-- Compiled and minified JavaScript -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>
<script src="<%= context %>/js/site.js"></script>
</body>
</html>
