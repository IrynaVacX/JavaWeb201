// Script for SPA page + AUTH functions //
document.addEventListener("DOMContentLoaded", () => {
    // var instances =
    M.Modal.init( document.querySelectorAll('.modal'), {
        opacity: 0.6,
        inDuration: 200,
        outDuration: 200,
        onOpenStart: onModalOpens,
    });

    const authSignInButton = document.getElementById("auth-sign-in");
    if(authSignInButton)
    {
        authSignInButton.addEventListener("click", authSignInButtonClick);
    }
    else
    {
        console.error("#auth-sign-in not found");
    }
    // Token verification
    const spaTokenStatus = document.getElementById("spa-token-status");
    if(spaTokenStatus)
    {
        const token = window.localStorage.getItem("token");
        if(token)
        {
            const tokenObject = JSON.parse(atob(token));
            // TODO
            spaTokenStatus.innerText = "Token expires at " + tokenObject.exp;
            // const appContext = getAppContext();
            const appContext = window.location.pathname.split('/')[1] ;
            fetch(`/${appContext}/tpl/spa-auth.html`, {
                method: "GET",
                headers: {
                    "Authorization": `Bearer ${token}`
                }
            })
                    .then(r=>r.text())
                    .then(t =>
                        document.querySelector("auth-part").innerHTML = t);
                document.getElementById("spa-log-out")
                    .addEventListener("click", logoutClick);
        }
        else
        {
            spaTokenStatus.innerText = "Not installed";
        }
        const spaGetDataButton = document.getElementById("spa-get-data");
        if( spaGetDataButton ) {
            spaGetDataButton.addEventListener('click', spaGetDataClick );
        }
        const spaGetDataButton2 = document.getElementById("spa-get-data2");
        if (spaGetDataButton2) {
            spaGetDataButton2.addEventListener('click', spaGetDataClick2);
        }
        const spaGetDataButton3 = document.getElementById("spa-get-data3");
        if (spaGetDataButton3) {
            spaGetDataButton3.addEventListener('click', spaGetDataClick3);
        }
        const spaGetDataButton4 = document.getElementById("spa-get-data4");
        if (spaGetDataButton4) {
            spaGetDataButton4.addEventListener('click', spaGetDataClick4);
        }
    }
});

function getAppContext(){
    return '/' + window.location.pathname.split('/')[1];
}

function spaGetDataClick()
{
    fetch(`${getAppContext()}/tpl/data1.png`,{
        method: "GET",
        headers: {
            "Authorization": `Bearer ${window.localStorage.getItem("token")}`
        }
    })
        .then(r=>r.blob())
        .then(b => {
            const dt = document.getElementById("dt1");
            if(dt == undefined)
            {
                const blobUrl = URL.createObjectURL(b);
                document.querySelector("auth-part").innerHTML +=
                    `<div id="dt1" style="display: grid"><img src="${blobUrl}" width="200"/><i>data-1</i></div>`;
            }
            else
            {
                dt.remove();
            }
    });
}
function spaGetDataClick2()
{
    console.log("spaGetDataClick2");
    fetch(`${getAppContext()}/tpl/data2.png`,{
        method: "GET",
        headers: {
            "Authorization": `Bearer ${window.localStorage.getItem("token")}`
        }
    })
        .then(r=>r.blob())
        .then(b => {
            const dt2 = document.getElementById("dt2");
            if(dt2 == undefined)
            {
                const blobUrl = URL.createObjectURL(b);
                document.querySelector("auth-part").innerHTML +=
                    `<div id="dt2" style="display: grid"><img src="${blobUrl}" width="200"/><i>data-2</i></div>`;
            }
            else
            {
                dt2.remove();
            }
        });
}
function spaGetDataClick3()
{
    console.log("spaGetDataClick3");
    fetch(`${getAppContext()}/tpl/data3.png`,{
        method: "GET",
        headers: {
            "Authorization": `Bearer ${window.localStorage.getItem("token")}`
        }
    })
        .then(r=>r.blob())
        .then(b => {
            const dt = document.getElementById("dt3");
            if(dt == undefined)
            {
                const blobUrl = URL.createObjectURL(b);
                document.querySelector("auth-part").innerHTML +=
                    `<div id="dt3" style="display: grid"><img src="${blobUrl}" width="200"/><i>data-3</i></div>`;
            }
            else
            {
                dt.remove();
            }
        });
}
function spaGetDataClick4()
{
    console.log("spaGetDataClick4");
    fetch(`${getAppContext()}/tpl/`,{
        method: "GET",
        headers: {
            "Authorization": `Bearer ${window.localStorage.getItem('token')}`
        }
    }).then(r=>{
        if(r.status === 404)
        {
            document.querySelector("auth-part").innerHTML = `ERROR 404<br/>`;
        }
    })
}

function logoutClick()
{
    window.localStorage.removeItem("token");
    window.location.reload();
}
function onModalOpens()
{
    [authLogin, authPassword, authMessage] = getAuthElements();
    authLogin.value = "";
    authPassword.value = "";
    authMessage.innerText = "";
}

function authSignInButtonClick()
{
    [authLogin, authPassword, authMessage] = getAuthElements();

    if(authLogin.value.length === 0)
    {
        // authMessage.innerText = "Логін не може бути порожнім";
        authMessage.innerText = "Login cannot be empty";
    }
    const appContext = getAppContext();
    fetch(`${appContext}/auth?login=${authLogin.value}&password=${authPassword.value}`, {
        method:'GET'
    }).then(r => {
        if(r.status !== 200)
        {
            // authMessage.innerText = "Автентифікацію відхилено";
            authMessage.innerText = "Authentication refused";
        }
        else
        {
            r.text().then(base64encodedText => {
                console.log(base64encodedText);

                const token = JSON.parse(atob(base64encodedText));

                if (typeof token.jti === "undefined")
                {
                    // authMessage.innerText = "Помилка одержання токену";
                    authMessage.innerText = "Error getting token";
                    return;
                }
                // authMessage.innerText = "OK";
                window.localStorage.setItem("token", base64encodedText);
                if(appContext.includes("/spa"))
                {
                    window.location.reload();
                }
                else
                {
                    window.location.replace(getAppContext() + "/spa");
                }
            });
        }
    });
}

function getAuthElements()
{
    const authLogin = document.getElementById("auth-login");
    if(!authLogin) { throw "#auth-login not found"; }
    const authPassword = document.getElementById("auth-password");
    if(!authPassword) { throw "#auth-password not found"; }
    const authMessage = document.getElementById("auth-message");
    if(!authMessage) { throw "#auth-message not found"; }
    return [authLogin, authPassword, authMessage];
}
