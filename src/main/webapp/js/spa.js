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
            const appContext = window.location.pathname.split('/')[1];
            fetch(`/${appContext}/tpl/spa-auth.html`)
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
        if( spaGetDataButton )
            spaGetDataButton.addEventListener('click', spaGetDataClick );
    }
});
function spaGetDataClick()
{
    console.log("spaGetDataClick");
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
    const appContext = window.location.pathname.split('/')[1] ;
    fetch(`/${appContext}/auth?login=${authLogin.value}&password=${authPassword.value}`, {
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
                window.location.reload();
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
