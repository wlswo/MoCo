/* 좋아요 */
function plzLogin() {
    const Toast = Swal.mixin({
        toast: true,
        position: 'top-end',
        showConfirmButton: false,
        timer: 3000,
        timerProgressBar: true,
        didOpen: (toast) => {
            toast.addEventListener('mouseenter', Swal.stopTimer)
            toast.addEventListener('mouseleave', Swal.resumeTimer)
        }
    })
    Toast.fire({
        icon: 'warning',
        background:'#e76876',
        title: '<h4 style="color: white;">로그인후 이용해주세요.</h4>'
    })
    return false;
}

/* CREATE */
function likeCheck(boardId) {
    /* isLiked == 0 좋아요를 누르지않은상태
     * isLiked == 1 좋아요를 누른상태
     */
    const isClick = document.getElementsByName('isClick').item(0).value;
    console.log(isClick);

    /* CREATE */
    if(isClick === 'noclick'){
        /* ajax */
        const baseUrl = "http://localhost:8080";
        /* XMLHttpRequest 객체 정의 */
        httpRequest = new XMLHttpRequest();

        /* POST 방식으로 요청 */
        httpRequest.open('POST', baseUrl + "/board/post/"+boardId+"/like");

        /* ResponseType Json */
        httpRequest.responseType = "json";

        /* 정의된 서버에 Json 형식의 요청 Data를 포함하여 요청을 전송 */
        httpRequest.send();

        /* httpRequest 상태 감지 */
        httpRequest.onreadystatechange = () => {
            /* readyState가 Done이고 응답 값이 200(ok) 일때 받아온 boolean으로 분기 */
            if (httpRequest.readyState === XMLHttpRequest.DONE) {
                if (httpRequest.status === 200) {
                    document.getElementsByName('isClick').item(0).value = 'clicked'
                    document.getElementsByClassName("new").item(0).textContent++;
                    document.getElementsByClassName("current").item(0).textContent++;
                    /* window.location.reload(); */
                } else {
                    let error = httpRequest.response;
                    console.log(error.message);
                }
            }

        }
    }
    /* DELETE */
    else {
        /* ajax */
        const baseUrl = "http://localhost:8080";
        /* XMLHttpRequest 객체 정의 */
        httpRequest = new XMLHttpRequest();

        /* DELETE 방식으로 요청 */
        httpRequest.open('DELETE', baseUrl + "/board/post/"+boardId+"/like");

        /* ResponseType Json */
        httpRequest.responseType = "json";

        /* 정의된 서버에 Json 형식의 요청 Data를 포함하여 요청을 전송 */
        httpRequest.send();

        /* httpRequest 상태 감지 */
        httpRequest.onreadystatechange = () => {
            /* readyState가 Done이고 응답 값이 200(ok) 일때 받아온 boolean으로 분기 */
            if (httpRequest.readyState === XMLHttpRequest.DONE) {
                if (httpRequest.status === 200) {
                    document.getElementsByName('isClick').item(0).value = 'noclick';
                    document.getElementsByClassName("new").item(0).textContent--;
                    document.getElementsByClassName("current").item(0).textContent--;
                } else {
                    let error = httpRequest.response;
                    console.log(error.message);
                }
            }

        }
    }
}



/* 댓글 */

/* CREATE */
function saveComment(parentId){
    /* 게시글 번호 */
    const boardId = document.getElementById("boardId").value;
    let comment;
    var isRecomment;
    /* 대댓글인지 체크 */
    if(typeof parentId == "undefined" || parentId == null || parentId == ""){
        /* 댓글일 경우 */
        comment = document.getElementById("comment").value;
        isRecomment = false;
    }else{
        /* 답글일 경우 */
        comment = document.getElementById('recomment-content-' + parentId).value;
        isRecomment = true;
    }

    /* 공백 및 빈 문자열 체크 */
    if(!comment || comment.trim() === "") {
        alert("공백 또는 빈 문자열은 입력하실수 없습니다.");
        return false;
    }else {
        /* ajax */
        const baseUrl = "http://localhost:8080";
        /* XMLHttpRequest 객체 정의 */
        httpRequest = new XMLHttpRequest();

        /* 입력된 데이터 Json 형식으로 변경 */
        var reqJson = new Object();
        reqJson.comment = comment;
        reqJson.boardId = boardId;

        /* POST 방식으로 요청 */
        if (isRecomment) {
            httpRequest.open('POST', baseUrl+"/board/recomment/"+boardId+"/"+parentId);
        }else {
            httpRequest.open('POST', baseUrl+"/board/comment/"+boardId);
        }
        /* 요청 Header에 컨텐츠 타입은 Json으로 사전 정의 */
        httpRequest.setRequestHeader('Content-Type', 'application/json');
        /* ResponseType Json */
        httpRequest.responseType = "text";

        /* 정의된 서버에 Json 형식의 요청 Data를 포함하여 요청을 전송 */
        httpRequest.send(JSON.stringify(reqJson));

        /* httpRequest 상태 감지 */
        httpRequest.onreadystatechange = () => {
            /* readyState가 Done이고 응답 값이 200(ok) 일때 받아온 boolean으로 분기 */
            if(httpRequest.readyState === XMLHttpRequest.DONE) {
                if(httpRequest.status === 200) {
                    if(isRecomment) {
                        $('#comment'+parentId).load(location.href+ ' #comment'+parentId);
                        console.log(parentId);
                    }else{
                        window.location.reload();
                    }
                }else{
                    let error = httpRequest.response;
                    console.log(error.message);
                }
            }
        }
    }
}
/* 댓글에 클릭 이벤트 부여 */
document.querySelectorAll("#btn-comment-update").forEach(function (item){
    item.addEventListener("click",function (){
        const form = this.closest('form'); /* btn의 가장 가까운 조상의 Element(form)을 반환(closet) */
        commentUpdate('comment',form);
    });
});

/* 대댓글에 클릭 이벤트 부여 */
document.querySelectorAll("#btn-recomment-update").forEach(function (item){
    item.addEventListener("click",function (){
        const form = this.closest('form'); /* btn의 가장 가까운 조상의 Element(form)을 반환(closet) */
        commentUpdate('recomment',form);
    });
});

/* UPDATE */
function commentUpdate(is_recomment,form) {
    var data;
    /* 댓글일 경우 */
    if(is_recomment === 'comment') {
        /* json data */
        data = {
            commentId : form.querySelector("#commentId").value,
            boardId : form.querySelector("#comment_boardId").value,
            comment : form.querySelector("#comment-content").value,
        }
    /* 대댓글일 경우 */
    }else {
        /* json data */
        data = {
            commentId : form.querySelector("#recommentId").value,
            boardId : form.querySelector("#recomment_boardId").value,
            comment : form.querySelector("#recomment-content").value,
        }
    }




    if(!data.comment || data.comment.trim() === ""){
        alert("댓글을 입력해주세요.");
        return false;
    }

    const comment_confirm = confirm("수정하시겠습니까?");

    if (comment_confirm) {
        /* ajax */
        const baseUrl = "http://localhost:8080";
        /* XMLHttpRequest 객체 정의 */
        httpRequest = new XMLHttpRequest();

        /* POST 방식으로 요청 */
        httpRequest.open('PUT', baseUrl+"/board/post/"+ data.boardId +"/comment/" + data.commentId);
        /* 요청 Header에 컨텐츠 타입은 Json으로 사전 정의 */
        httpRequest.setRequestHeader('Content-Type', 'application/json; charset=utf-8');
        /* ResponseType Json */
        httpRequest.responseType = "json";

        /* 정의된 서버에 Json 형식의 요청 Data를 포함하여 요청을 전송 */
        httpRequest.send(JSON.stringify(data));

        /* httpRequest 상태 감지 */
        httpRequest.onreadystatechange = () => {
            /* readyState가 Done이고 응답 값이 200(ok) 일때 받아온 boolean으로 분기 */
            if(httpRequest.readyState === XMLHttpRequest.DONE) {
                if(httpRequest.status === 200) {
                    window.location.reload();
                }else{
                    let error = httpRequest.response;
                    console.log(error.message);
                }
            }
        }
    }
}

/* DELETE */
function commentDelete(boardId, commentId) {
    const comment_confirm = confirm("정말 삭제하시겠습니까?");

    if(comment_confirm) {
        /* ajax */
        const baseUrl = "http://localhost:8080";
        /* XMLHttpRequest 객체 정의 */
        httpRequest = new XMLHttpRequest();

        /* POST 방식으로 요청 */
        httpRequest.open('DELETE', baseUrl + "/board/post/" + boardId + "/comment/" + commentId);
        /* 요청 Header에 컨텐츠 타입은 Json으로 사전 정의 */
        httpRequest.setRequestHeader('Content-Type', 'application/json; charset=utf-8');
        /* ResponseType Json */
        httpRequest.responseType = "json";

        /* 정의된 서버에 Json 형식의 요청 Data를 포함하여 요청을 전송 */
        httpRequest.send();

        /* httpRequest 상태 감지 */
        httpRequest.onreadystatechange = () => {
            /* readyState가 Done이고 응답 값이 200(ok) 일때 받아온 boolean으로 분기 */
            if (httpRequest.readyState === XMLHttpRequest.DONE) {
                if (httpRequest.status === 200) {
                    window.location.reload();
                } else {
                    let error = httpRequest.response;
                    console.log(error.message);
                }
            }

        }
    }
}


