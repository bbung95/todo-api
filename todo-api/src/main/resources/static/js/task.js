const task_List = document.querySelector("#task_list");

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

const addTask = () => {

    const title = document.querySelector("input[name='title']").value;
    const contents = document.querySelector("input[name='contents']").value;
    const importance = document.querySelector("select[name='importance']").value;

    fetch("/api/task",{
        method : "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body : JSON.stringify({
            title,
            contents,
            importance
        })
    })
    .then((response) => response.json())
    .then((data) => appendTask(data))
    .catch((error) => console.log(error))
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
}

getList();
