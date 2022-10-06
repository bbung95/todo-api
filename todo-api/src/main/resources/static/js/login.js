const loginForm = document.querySelector("#loginForm");
const username = loginForm.querySelector("input[name='username']");
const password = loginForm.querySelector("input[name='password']");

document.addEventListener("DOMContentLoaded", function() {

    if(localStorage.getItem("token")){
        location.href = "/";
    }
})

const login = async () => {

    let res = await fetch("/api/user/login", {
        method : "POST",
        headers : {"Content-type" : "application/json"},
        body : JSON.stringify({
            "username" : username.value,
            "password" : password.value
        })
    });

    const result = res.json();
    if(res.ok){
        result.then(data => {
            console.log(data)
            localStorage.setItem("token", data.token);
            location.href = "/";
        })
    }else{
        result.then(error => errorMessage(error));
    }

}

const errorMessage = (error) => {

    console.log(error);
    let message = "";

    error.messages.forEach(msg => {
        message += `${msg}\n`;
    })

    alert(message);
}