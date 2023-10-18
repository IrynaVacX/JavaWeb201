document.addEventListener('DOMContentLoaded', function() {
    // var instances =
    M.Modal.init( document.querySelectorAll('.modal'), {
        opacity: 0.6,
        inDuration: 200,
        outDuration: 200
    });

    // db.jsp
    const createButton = document.getElementById("db-create-button");
    if(createButton) createButton.addEventListener('click', createButtonClick);
    const insertButton = document.getElementById("db-insert-button");
    if(insertButton) insertButton.addEventListener('click', insertButtonClick);

    const readButton = document.getElementById("db-read-button");
    if(readButton) readButton.addEventListener('click', readButtonClick);
});

function createButtonClick() {
    fetch(window.location.href, {
        method: 'PUT'
    }).then(r => r.json()).then(j => {
        console.log(j);
    });
}
function readButtonClick() {
    fetch(window.location.href, {
        method: "COPY"
    }).then(r => r.json()).then(j => showCalls(j));
}
function insertButtonClick()
{
    const nameInput = document.querySelector('[name="user-name"]');
    if( ! nameInput ) throw '[name="user-name"] not found' ;
    const phoneInput = document.querySelector('[name="user-phone"]');
    if( ! phoneInput ) throw '[name="user-phone"] not found' ;

    fetch(window.location.href, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            name: nameInput.value,
            phone: phoneInput.value
        })
    }).then(r => r.json()).then(j => {
        console.log(j);
    });
}

function showCalls(j)
{
    var table = "<table><tr><th>id</th><th>name</th><th>phone</th><th>callMoment</th></tr>";
    for(let call of j)
    {
        let m = (typeof call.callMoment == 'undefined' || call.callMoment == null) ?
            '---' : call.callMoment;
        table += `<tr><td>${call.id}</td><td>${call.name}</td><td>${call.phone}</td><td>${m}</td></tr>`;
    }
    table += "</table>";
    document.getElementById("calls-container").innerHTML = table;
}
