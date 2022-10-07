const taskList = document.querySelector("#task_list");
const taskAddForm = document.querySelector('#taskAddForm');
const taskSearchForm = document.querySelector('#task_search_form');
const title = taskAddForm.querySelector("input[name='title']");
const contents = taskAddForm.querySelector("textarea[name='contents']");
const importance = taskAddForm.querySelector("select[name='importance']");

document.addEventListener("DOMContentLoaded", function() {

    if(localStorage.getItem("token")){
        getTaskList(getTaskSearchData());
    }else{
        alert("로그인이 필요합니다.")
        location.href = "/login";
    }
})

const getTaskList = async (queryData) => {

    const res = await fetch(`/api/task?boardId=${boardId}&${queryData}`, {
        headers : {"Authorization" : localStorage.getItem("token")}
    });
    const result = res.json();
    if(res.ok){
        result.then((data) => {
            appendList(data)
        })
    }
}

const appendList = (data) => {

    console.log(data);

    appendListEl(data.HIGH, "HIGH")
    appendListEl(data.MEDIUM, "MEDIUM")
    appendListEl(data.LOW, "LOW")
}

const appendListEl = (data, importance) => {

    let display = "";

    data.list.forEach(item => {

        display += `
                    <div class="task_el">
                        <div>제목 : ${item.title}</div>
                        <div>중요도 : ${item.importance}</div>
                        <div>상태 : ${item.status}</div>
                        <div>내용 : ${item.contents}</div>
                        <div>생성날짜 : ${item.createDate}</div>
                    </div>
                    `
    })

    taskList.querySelector(`#${importance}_count`).innerHTML = data.totalCount;
    taskList.querySelector("#task_" + importance).innerHTML = display;
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
            "importance" : importance.value,
            "boardId" : boardId
        })
    });
    const data = res.json()
    if (res.ok) {
        data.then((item => appendTaskEl(item)))
    } else {
        data.then((item => errorMessage(item)))
    }
}

const appendTaskEl = (id) => {

    fetch(`/api/task/${id}`, {
        headers : {"Authorization" : localStorage.getItem("token")}
    })
    .then((response) => response.json())
    .then((data) => {
        let display = `
                    <div>제목 : ${data.title}</div>
                    <div>중요도 : ${data.importance}</div>
                    <div>상태 : ${data.status}</div>
                    <div>내용 : ${data.contents}</div>
                    <div>생성날짜 : ${data.createDate}</div>
                    `
        let htmlElement = document.createElement("div");
        htmlElement.className = "task_el"
        htmlElement.innerHTML = display;

        taskList.querySelector('#task_' + data.importance).appendChild(htmlElement);
        const totalCountEl = taskList.querySelector(`#${data.importance}_count`);
        totalCountEl.innerHTML = Number(totalCountEl.textContent) + 1;
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

const onClickSearchTask = () => {
    const queryData = getTaskSearchData();

    console.log(queryData);

    getTaskList(queryData);
}

const getTaskSearchData = () => {

    const searchTypeSelect = taskSearchForm.querySelector("select[name='searchType']");
    const searchType = searchTypeSelect.querySelector('option:checked').value;
    const keyword = taskSearchForm.querySelector('input[name="keyword"]').value;
    const condition = taskSearchForm.querySelector('input[name="condition"]:checked').value;

    const param = {
        searchType,
        keyword,
        condition
    }

    return Object.keys(param)
        .map(k => encodeURIComponent(k) + '=' + encodeURIComponent(param[k]))
        .join('&');
}
