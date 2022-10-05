const task_List = document.querySelector("#task_list");
const title = document.querySelector("input[name='title']");
const contents = document.querySelector("input[name='contents']");
const importance = document.querySelector("select[name='importance']");

const getList = () => {

    fetch("/api/task", {
        method : "GET",
        }
    )
    .then((response) => response.json())
    .then((data) => appendList(data))
}

const appendList = (data) => {

    let display = "";

    data.forEach(item => {

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

const addTask = async () => {

    let res = await fetch("/api/task", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
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

    fetch(`/api/task/${id}`)
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

    let message = "";

    error.forEach(msg => {
        message += `${msg}\n`;
    })

    alert(message);
}

getList();
