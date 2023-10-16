<%@ page contentType="text/html;charset=UTF-8" %>
<h1>JSP</h1>
<p>
    <b>JavaServer Pages (JSP)</b> — це технологія, що дозволяє розробникам створювати
    динамічний веб-контент на основі Java. Вона забезпечує змогу вставляти
    Java код безпосередньо в HTML-сторінки
</p>
<h5>Ось декілька можливостей JSP :</h5>
<ul class="collection">
    <li class="collection-item">
        Вбудований Java-код: JSP дозволяє вставляти Java код безпосередньо
        в HTML за допомогою спеціальних тегів, таких як <\% ... %>
    </li>
    <li class="collection-item">
        JSP-тегів бібліотек: Це набори користувацьких тегів, які допомагають
        спрощувати код JSP і зробити його більш читабельним
    </li>
    <li class="collection-item">
        EL (Expression Language): Спрощений синтаксис для доступу до
        атрибутів об'єктів. Наприклад, \${sessionScope.user.name}
        замість старого синтаксису
        session.getAttribute("user").getName()
    </li>
    <li class="collection-item">
        MVC архітектура: Часто JSP використовуються як частина моделі MVC
        (Model-View-Controller), де JSP виступає як вид (View), а
        сервлети або інші Java класи виступають як контролери
    </li>
    <li class="collection-item">
        Вбудована підтримка сесій: JSP має вбудовану підтримку сесій, яка
        дозволяє розробникам легко зберігати дані про користувача
        між запитами
    </li>
    <li class="collection-item">
        Вбудована підтримка кукі (cookies): Це забезпечує можливість
        легко зберігати та отримувати дані на стороні клієнта
    </li>
</ul>
