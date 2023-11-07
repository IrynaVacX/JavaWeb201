<%@ page import="step.learning.dto.entities.CallMe" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String connectionStatus = (String) request.getAttribute("connectionStatus");
%>
<%--<%--%>
<%--    CallMe model = (CallMe) request.getAttribute("call-model");--%>
<%--    String phoneValue = model == null ? "" : model.getPhone();--%>
<%--    String nameValue = model == null ? "" : model.getName();--%>
<%--    Map<String, String> errors = model == null ? new HashMap<String, String>() : (HashMap) model.getErrorMessages();--%>

<%--    String nameClass = model == null ? "validate" :--%>
<%--            ( errors.containsKey("name") ? "invalid" : "valid" );--%>
<%--    String phoneClass = model == null ? "validate" :--%>
<%--            ( errors.containsKey("phone") ? "invalid" : "valid" );--%>

<%--    String regMessage = (String) request.getAttribute( "call-message" );--%>
<%--    if( regMessage == null ) { regMessage = "" ; }--%>
<%--%>--%>
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
    драйвер відповідної БД (які також називають конекторами).
    Налаштування підключення здійснюється шляхом реєстрації драйвера
    та надсилання запиту до СУБД щодо підключення (автентифікації).
    Нагадуємо, що паролі до БД слід зберігати в окремому файлі (конфігурації)
    який вилучено з репозиторію (у .gitignore).
</p>
<p>
    Оскільки дані про підключення можуть знадобитись у
    різних частинах проєкту найбільш доцільно створювати
    його у вигляді окремого сервісу.
</p>
<p>
    Статус підключення : <strong><%= connectionStatus %></strong>
</p>
<p>
    При роботі кількох сервісів з однією БД вживається підхід з
    розрізнення таблиць за допомогою схем або префіксів. Не всі
    БД підтримують схеми (MS SQL - так, MySQL - ні)
</p>
<h2>Управління даними</h2>
<p>
    <button id="db-create-button"
            class="waves-effect waves-light btn" style="background-color: #8FBC8F">
        <i class="material-icons right">cloud</i>
        create</button>

    <input name="user-name" id="user-name" placeholder="Ім'я">
<%--    <% if( errors.containsKey( "name" ) ) { %>--%>
<%--    <span class="helper-text" data-error="<%= errors.get("name") %>"></span>--%>
<%--    <% } %>--%>
    <input name="user-phone" id="user-phone" placeholder="Телефон">
<%--    <% if( errors.containsKey( "phone" ) ) { %>--%>
<%--    <span class="helper-text" data-error="<%= errors.get("phone") %>"></span>--%>
<%--    <% } %>--%>
    <button id="db-insert-button"
            class="waves-effect waves-light btn" style="background-color: #8FBC8F">
        <i class="material-icons right">phone_iphone</i>
        Замовити дзвінок</button>
    <br/>
    <u id="out"></u>
</p>
<div class="row">
    <button id="db-read-button"
            class="waves-effect waves-light btn" style="background-color: #8FBC8F">
        <i class="material-icons right">view_list</i>
    Переглянути замовлення</button>
    <button id="db-show-all-button"
            class="waves-effect waves-light btn lighten-2" style="background-color: #a7d9a7">
        <i class="material-icons right">view_list</i>
        Переглянути усі замовлення</button>
</div>
<div id="calls-container"></div>
