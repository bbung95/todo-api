const board_list = document.querySelector("#board_list");
const boardAddForm = document.querySelector('#boardAddForm');
const title = boardAddForm.querySelector("input[name='title']");

document.addEventListener("DOMContentLoaded", function() {

    if(localStorage.getItem("token")){

        getBoardList();
    }else{
        alert("로그인이 필요합니다.")
        location.href = "/login";
    }
})

const getBoardList = async () => {

    const res = await fetch("/api/board", {
        headers : {"Authorization" : localStorage.getItem("token")}
    });
    const result = res.json();
    if(res.ok){
        result.then((data) => {
            appendBoardListEl(data)
        })
    }
}

const appendBoardListEl = (data) => {

    let display = "";

    data.list.forEach(item => {

        display += `
                    <div class="board_el" >
                        <div onclick="location.href='/board/${item.id}'">
                            <div>제목 : ${item.title}</div>
                            <div>생성날짜 : ${item.createDate}</div>
                        </div>
                        <span>
                            <button>수정</button>
                            <button onclick="onClickBoardDelete(event)" data-id="${item.id}">삭제</button>
                        </span>
                    </div>
                    `
    })

    board_list.innerHTML = display;
}

const displayBoardAddForm = () => {

    if(boardAddForm.style.display == "none"){
        boardAddForm.style.display = "block";
    }else{
        boardAddForm.style.display = "none";
    }
}

const addBoard = async () => {

    let res = await fetch("/api/board", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization" : localStorage.getItem("token")
        },
        body: JSON.stringify({
            "title" : title.value,
        })
    });
    const data = res.json()
    if (res.ok) {
        data.then((item => appendBoardEl(item)))
    } else {
        data.then((item => errorMessage(item)))
    }
}

const appendBoardEl = (id) => {

    fetch(`/api/board/${id}`, {
        headers : {"Authorization" : localStorage.getItem("token")}
    })
    .then((response) => response.json())
    .then((data) => {
        let display = `
                    <div>
                        <div>제목 : ${data.title}</div>
                        <div>생성날짜 : ${data.createDate}</div>
                    </div>
                    <span>
                        <button>수정</button>
                        <button>삭제</button>
                    </span>
                    `
        let htmlElement = document.createElement("div");
        htmlElement.className = "board_el"
        htmlElement.innerHTML = display;
        htmlElement.onclick = location.href=`/board/${data.id}`;

        board_list.appendChild(htmlElement);
    })
    title.value = "";
}

const onClickBoardDelete = (e) => {

    const boardId = e.target.dataset.id;

    const res = fetch(`/api/board/${boardId}`, {
        method : "DELETE",
    });
    if(res.ok){
        alert("삭제되었습니다.");
    }else{
        const result = res.json();
        result.then(data => {
            errorMessage(data);
        })
    }
}

const errorMessage = (error) => {

    console.log(error)
    let message = "";

    error.messages.forEach(msg => {
        message += `${msg}\n`;
    })

    alert(message);
}
