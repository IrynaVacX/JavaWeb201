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
        const jti = window.localStorage.getItem("jti");
        // spaTokenStatus.innerText = (!!jti) ? "Встановлено " + jti : "Не встановлено";
        spaTokenStatus.innerText = (!!jti) ? "Installed " + jti : "Not installed";
        if(jti)
        {
            fetch('tpl/spa-auth.html')
                .then(r=>r.text())
                .then(t =>
                document.querySelector('auth-part')
                    .innerHTML = t);
            document.getElementById("spa-log-out")
                .addEventListener("click", logoutClick);
        }
    }
});
function spaGetDataClick()
{
    console.log("spaGetDataClick");
}

function logoutClick()
{
    window.localStorage.removeItem("jti");
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
            r.json().then(j => {
                if (typeof j.jti === "undefined")
                {
                    // authMessage.innerText = "Помилка одержання токену";
                    authMessage.innerText = "Error getting token";
                    return;
                }
                authMessage.innerText = "OK";
                console.log(j);
                window.localStorage.setItem("jti", j.jti);
            });
            console.log(r);
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