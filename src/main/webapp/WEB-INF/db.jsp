<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String connectionStatus = (String) request.getAttribute("connectionStatus");
%>
<h1>Робота з Базами Даних</h1>
<h5>JDBC</h5>
<p>
    <<strong>JDBC</strong> - Java DataBase Connectivity - технологія
    доступу до даних, аналогічна подібним ADO, PDO тощо.
    Головна ідея - створення підключення, формування запиту та передача
    його до СУБД, одержання результатів виконання запиту та даних, що
    ним повертаються.
</p>
<p>
    Технологія надає універсальний інтерфейс доступу до даних (однаковий
    для різних СУБД), конкретна реалізація здійснюється шляхом підключення
    Драйвер відповідної БД (які також називають конекторами)
</p>
<p>
    Статус підключення : <strong><%= connectionStatus %></strong>
</p>

<p>

</p>
<h4>Управління даними</h4>
<%--<p>--%>
<%--    <button id="id-create-button" class="waves-effect waves-light btn">--%>
<%--        <i class="material-icons right">cloud</i>--%>
<%--        create--%>
<%--    </button>--%>
<%--</p>--%>