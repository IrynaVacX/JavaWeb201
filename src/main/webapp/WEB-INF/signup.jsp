<%@ page import="step.learning.dto.models.RegFormModel" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<%
    RegFormModel model = (RegFormModel) request.getAttribute("reg-model");
    String loginValue = model == null ? "" : model.getLogin();
    String nameValue = model == null ? "" : model.getName();
    String emailValue = model == null ? "" : model.getEmail();
    String birthdateValue = model == null ? "" : model.getBirthdateAsString();
    Map<String, String> errors = model == null ? new HashMap<String, String>() : (HashMap) model.getErrorMessages();

    String nameClass = model == null ? "validate" :
            ( errors.containsKey("name") ? "invalid" : "valid" ) ;
    String loginClass = model == null ? "validate" :
            ( errors.containsKey("login") ? "invalid" : "valid" ) ;
    String regMessage = (String) request.getAttribute( "reg-message" ) ;
    if( regMessage == null ) { regMessage = "" ; }
%>

<h2>Реєстрація користувача</h2>
<p><%= regMessage %></p>
<div class="row">
    <form class="col s12" method="post" action="" enctype="multipart/form-data">>
        <div class="row">
            <div class="input-field col s6">
                <i class="material-icons prefix">badge</i>
                <input value="<%= loginValue %>" name="reg-login" id="reg-login" type="text" class="<%= loginClass %>">
                <label for="reg-login">Логін на сайті</label>
                <% if(errors.containsKey("login")) { %>
                <span class="helper-text" data-error="wrong"><%= errors.get("login") %></span>
                <% } %>
            </div>
            <div class="input-field col s6">
                <i class="material-icons prefix">person</i>
                <input value="<%= nameValue %>" name="reg-name" id="reg-name" type="text" class="<%= nameClass %>">
                <label for="reg-name">Реальне ім'я</label>
                <% if(errors.containsKey("name")) { %>
                <span class="helper-text" data-error="wrong"><%= errors.get("name") %></span>
                <% } %>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s6">
                <i class="material-icons prefix">lock</i>
                <input name="reg-password" id="reg-password" type="password" class="validate">
                <label for="reg-password">Пароль</label>
            </div>
            <div class="input-field col s6">
                <i class="material-icons prefix">lock_open</i>
                <input name="reg-repeat" id="reg-repeat" type="password" class="validate">
                <label for="reg-repeat">Повторіть пароль</label>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s6">
                <i class="material-symbols-outlined prefix">mail</i>
                <input value="<%= emailValue %>" name="reg-email" id="reg-email" type="email" class="validate">
                <label for="reg-email">E-mail</label>
            </div>
            <div class="input-field col s6">
                <i class="material-icons prefix">cake</i>
                <input value="<%= birthdateValue %>" name="reg-birthdate" id="reg-birthdate" type="date" class="validate">
                <label for="reg-birthdate">Дата народження</label>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s6">
                <i class="material-icons prefix">fact_check</i>
                <label>&emsp;
                    <input name="reg-rules" id="reg-rules" type="checkbox" class="filled-in validate">
                    <span>Не буду нічого порушувати</span>
                </label>
            </div>
            <div class="file-field input-field col s6">
                <div class="btn light-green lighten-1">
                    <i class="material-icons">account_box</i>
                    <input type="file" name="reg-avatar">
                </div>
                <div class="file-path-wrapper">
                    <input class="file-path validate" type="text"
                           placeholder="Зображення-аватарка">
                </div>
            </div>
        </div>
<%--        <div class="row">--%>
<%--            <div class="input-field col s6">--%>

<%--            </div>--%>
<%--            <div class="input-field col s6 right-align">--%>
<%--                <button class="waves-effect waves-light btn darken-1" style="background: #8FBC8F"><i class="material-icons right">how_to_reg</i>Реєстрація</button>--%>
<%--            </div>--%>
<%--        </div>--%>

        <div class="input-field row right-align">
            <button class="waves-effect waves-light btn darken-1" style="background: #8FBC8F"><i class="material-icons right">how_to_reg</i>Реєстрація</button>
        </div>
    </form>
</div>
<p class="gray-text">
    Передача файлів через форми.
    По-перше, передача файлів можлива лише методом POST та з кодуванням
    пакету <code>multipart/form-data</code> (за замовчанням, форма передається
    з іншим кодуванням <code>application/x-www-form-urlencoded</code> ).
    Також переконуємось у наявності атрибута name у файловому інпуті.
    <br/>
    По-друге, приймання таких пакетів з боку сервера вимагає окремої обробки.
    Для цього вживаються додаткові модулі-залежності. Наприклад,
    <a href="https://mvnrepository.com/artifact/commons-fileupload/commons-fileupload">Apache Commons FileUpload</a>
    <br/>
    В ASP для роботи з файлами є інтерфейс IFormFile, його аналог в обраному
    пакеті - FileItem. У формі, окрім файлів, також передаються інші поля (у
    текстовому вигляді). Відповідно, результат розбору (парсингу) форми є
    дві колекції - файлів та полів. Для повернення єдиного результату (з двох
    колекцій) слід зробити спільний інтерфейс.
</p>
