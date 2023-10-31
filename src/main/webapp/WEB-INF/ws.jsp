<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<h1>WebSocket</h1>
<div class="row">
    <div class="col s3">
        <strong><%= request.getAttribute("user") %></strong>
        <input id="user-message" type="text" />
        <button onclick="sendClick()">Send</button>
        <ul id="chat-container" class="collection"> <!-- Chat Messages Place --> </ul>
    </div>
    <div class="col s9">
        <p>
            WebSocket - протокол (приблизно рівня HTTP), відомий схемами
            <b>ws://</b> та <b>wss://</b>. Для прикладного програмування
            головна відмінність - у дуплексмому режимі передавання даних
            за якого як і клієнт може ініціювати передачу, так і сервер.
            Реалізується це механізмом подій та іх утворення як на клієнті,
            так і на сервері за однаковими принципами.
        </p>
        <p>
            Оскільки це інший протокол, він реалізується окремим сервером,
            який існує або окремо, або паралельно з основним (HTTP). Веб-
            сервери (на кщталт Tomcat) здатні обслуговувати обидва типи
            протоколів.
        </p>
        <p>
            Для роботи з вкбсокетом додаємо залежність (java.websocket-api),
            оголошуємо клас серверу (див. WebsocketServer). Оскільки сервер
            забезпечує дуплексний зв'язок ...
            ...
        </p>
    </div>
</div>

<script>
    document.addEventListener("DOMContentLoaded", () => {
        initWebsocket();
    })
    function addMessage(txt)
    {
        const li = document.createElement("li");
        li.className = "collection-item";
        li.appendChild(document.createTextNode(txt));
        document.getElementById("chat-container").appendChild(li);
    }
    function sendClick()
    {
        window.websocket.send(
            document.getElementById("user-message").value
        )
    }
    function initWebsocket()
    {
        let host = window.location.host + getAppContext();
        window.websocket = new WebSocket(`ws://${host}/chat`);
        window.websocket.onopen = onWsOpen;
        window.websocket.onclose = onWsClose;
        window.websocket.onmessage = onWsMessage;
        window.websocket.onerror = onWsError;
    }
    function onWsOpen(e)
    {
        // console.log("onWsOpen", e);
        addMessage("Chat activated");
    }
    function onWsClose(e)
    {
        // console.log("onWsClose", e);
        addMessage("Chat closed");
    }
    function onWsMessage(e)
    {
        // console.log("onWsMessage", e);
        addMessage(e.data);
    }
    function onWsError(e)
    {
        // console.log("onWsError", e);
        addMessage("Chat error : " + e.data);
    }
    function getAppContext()
    {
        return '/' + window.location.pathname.split('/')[1];
    }
</script>
