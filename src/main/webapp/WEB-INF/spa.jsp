<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<h1>SPA</h1>
<p>
    Автентифікація та авторизація за допомогою токенів здійснюється
    наступним чином: <br/>
    - користувач вводить логін та пароль, формується асинхронний запит до АРТ авторизації, у відповідь отримується токен.<br/> токен перевіряється на цілісність та зберігається у локальному сховищі. Подальші запити включають одержаний токен до
    заголовків.
</p>
<p>
    Наявність токену на сторінці : <b id="spa-token-status"></b>
</p>
<button class="btn" style="background-color: #8FBC8F; margin: 10px;" id="spa-get-data">
    <i class="material-icons">folder_special</i></button>

<button class="btn" style="background-color: #8FBC8F; margin: 10px;" id="spa-get-data2">
    <i class="material-icons">folder_special</i></button>
<button class="btn" style="background-color: #8FBC8F; margin: 10px;" id="spa-get-data3">
    <i class="material-icons">folder_special</i></button>
<button class="btn" style="background-color: #8FBC8F; margin: 10px;" id="spa-get-data4">
    <i class="material-icons">error</i></button>

<button class="btn" style="background-color: #8FBC8F; margin: 10px" id="spa-log-out">
    <i class="material-icons">Вихід</i></button>
<br/>
<auth-part></auth-part>
