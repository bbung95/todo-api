const task_List = document.querySelector("#task_list");;
const taskAddForm = document.querySelector('#taskAddForm');
const title = taskAddForm.querySelector("input[name='title']");
const contents = taskAddForm.querySelector("textarea[name='contents']");
const importance = taskAddForm.querySelector("select[name='importance']");

document.addEventListener("DOMContentLoaded", function() {

    if(localStorage.getItem("token")){
        getList();
    }else{
        alert("로그인이 필요합니다.")
        location.href = "/login";
    }
})

const getList = async () => {

    const res = await fetch("/api/task", {
        headers : {"Authorization" : localStorage.getItem("token")}
    });
    const result = res.json();
    if(res.ok){
        result.then((data) => appendList(data))
    }
}

const appendList = (data) => {

    let display = "";

    data.list.forEach(item => {

        display += `
                    <div class="task_el">
                        <div>${item.title}</div>
                        <div>${item.importance}</div>
                        <div>${item.status}</div>
                        <div>${item.createDate}</div>
                    <div>
                    `
    })

    task_List.innerHTML = display;
}

const displayAddForm = () => {

    if(taskAddForm.style.display == "none"){
        taskAddForm.style.display = "block";
    }else{
        taskAddForm.style.display = "none";
    }

}

const addTask = async () => {

    let res = await fetch("/api/task", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization" : localStorage.getItem("token")
        },
        body: JSON.stringify({
            "title" : title.value,
            "contents" : contents.value,
            "importance" : importance.value
        })
    });
    const data = res.json()
    if (res.ok) {
        data.then((item => appendTask(item)))
    } else {
        data.then((item => errorMessage(item)))
    }
}

const appendTask = (id) => {

    fetch(`/api/task/${id}`, {
        headers : {"Authorization" : localStorage.getItem("token")}
    })
        .then((response) => response.json())
        .then((data) => {
            let display = `
                        <div>${data.title}</div>
                        <div>${data.importance}</div>
                        <div>${data.status}</div>
                        <div>${data.createDate}</div>
                        `
            let htmlElement = document.createElement("div");
            htmlElement.className = "task_el"
            htmlElement.innerHTML = display;

            task_List.appendChild(htmlElement);
        })
    title.value = "";
    contents.value = "";
}


const errorMessage = (error) => {

    console.log(error)
    let message = "";

    error.messages.forEach(msg => {
        message += `${msg}\n`;
    })

    alert(message);
}
