const signForm = document.querySelector("#signForm");
const username = signForm.querySelector("input[name='username']");
const password = signForm.querySelector("input[name='password']");
const nickname = signForm.querySelector("input[name='nickname']");

document.addEventListener("DOMContentLoaded", function() {

    if(localStorage.getItem("token")){
        location.href = "/";
    }
})

const sign = async () => {

    let res = await fetch("/api/user", {
        method : "POST",
        headers : {"Content-type" : "application/json"},
        body : JSON.stringify({
            "username" : username.value,
            "password" : password.value,
            "nickname" : nickname.value
        })
    });

    const result = res.json();
    if(res.ok){
        location.href = "/login";
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