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

    const showAllButton = document.getElementById("db-show-all-button");
    if(showAllButton) showAllButton.addEventListener('click',showAllButtonClick);
});

function createButtonClick()
{
    fetch(window.location.href, {
        method: "PUT"
    }).then(r => r.json()).then(j => {
        console.log(j);
    });
}
function insertButtonClick()
{
    console.log("===========");
    const nameInput = document.querySelector('[name="user-name"]');
    if(!nameInput) throw '[name="user-name"] not found';
    console.log(nameInput);
    const phoneInput = document.querySelector('[name="user-phone"]');
    if(!phoneInput) throw '[name="user-phone"] not found';
    console.log(phoneInput);
    fetch(window.location.href, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            name: nameInput.value,
            phone: phoneInput.value
        })
    }).then(r => r.json()).then(j => {
        console.log(j);
    });
}
function readButtonClick()
{
    fetch(window.location.href,{
        method: "COPY"
    }).then(r => r.json()).then(showCalls);
}
function showAllButtonClick(e)
{
    fetch(window.location.href,{
        method: "PURGE"
    }).then(r => r.json()).then(showAllCalls);
}

function showCalls(j)
{
    var table = '<table class="material-table"><tr><th>id</th><th>name</th><th>phone</th><th>callMoment</th><th>delete</th></tr>';
    for (let call of j)
    {
        let d = `<button class="btn lighten-2" style="background-color: #8FBC8F" data-id="${call.id}" onclick="deleteClick(event)"><i class="material-icons light-text">delete</i></button>`;

        let m = ( typeof call.callMoment == "undefined" || call.callMoment == null ) ?
            `<button data-id="${call.id}" onclick="callClick(event)">call</button>` :  call.callMoment ;

        table += `<tr><td>${call.id}</td><td>${call.name}</td><td>${call.phone}</td><td>${m}</td><td>${d}</td></tr>`;
    }
    table+= "</table>";
    document.getElementById("calls-container").innerHTML = table;
}
function showAllCalls(j)
{
    var table = '<table class="material-table"><tr><th>id</th><th>name</th><th>phone</th><th>callMoment</th><th>deleteMoment</th><th>restore</th></tr>';
    for (let call of j)
    {
        let m = ( typeof call.callMoment == "undefined" || call.callMoment == null ) ?
            `<button data-id="${call.id}" onclick="callClickLink(event)">call</button>` :  call.callMoment ;

        let d = ( typeof call.deleteMoment == "undefined" || call.deleteMoment == null ) ?
            `<i class="material-icons">remove</i>` : call.deleteMoment;

        let r = ( typeof call.deleteMoment == "undefined" || call.deleteMoment == null ) ?
            "Not Deleted" : `<button class="btn lighten-2" style="background-color: #8FBC8F" data-id="${call.id}" onclick="restoreClick(event)"><i class="material-icons">restore</i></button>`;

        table += `<tr><td>${call.id}</td><td>${call.name}</td><td>${call.phone}</td><td>${m}</td><td>${d}</td><td>${r}</td></tr>`;
    }
    table+= "</table>";
    document.getElementById("calls-container").innerHTML = table;
}

function callClick(e)
{
    const callId = e.target.getAttribute("data-id");
    if(confirm(`Call to order by number id : ${callId} ?`))
    {
        fetch(window.location.href + "?call-id=" + callId, {
            method: "LINK",
        }).then(r => r.json()).then(j => {
            if(typeof j.callMoment != "undefined")
            {
                e.target.parentNode.innerHTML = j.callMoment;
            }
        });
    }
}
function deleteClick(e)
{
    const button = e.target.closest("button");
    const callId = button.getAttribute("data-id");
    if(confirm(`Delete order number with id : ${callId} ?` ))
    {
        fetch(window.location.href + "?call-id=" + callId, {
            method: "DELETE",
        }).then(r => {
            if(r.status === 202)
            {
                let tr =  button
                    .parentNode  //td
                    .parentNode; //tr
                tr.parentNode.removeChild(tr);
            }
            else
            {
                r.json().then(alert);
            }
        });
    }
}
function restoreClick(e)
{
    const button = e.target.closest("button");
    const callId = button.getAttribute("data-id");
    if(confirm(`Restore order number with id : ${callId} ?` ))
    {
        fetch(window.location.href + "?call-id=" + callId, {
            method: "MOVE",
        }).then(r => {
            if(r.status === 202)
            {
                let tr = button
                    .parentNode  //td
                    .parentNode; //tr
                tr.parentNode.removeChild(tr);
            }
            else
            {
                r.json().then(alert);
            }
        });
    }
}
