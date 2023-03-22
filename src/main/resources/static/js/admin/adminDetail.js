/* 좋아요 CREATE */
function likeCheck(boardId) {
    /* isLiked == 0 좋아요를 누르지않은상태
     * isLiked == 1 좋아요를 누른상태
     */
    const isClick = document.getElementsByName('isClick').item(0).value;
    console.log(isClick);

    /* CREATE */
    if(isClick === 'noclick'){
        /* ajax */
        /* XMLHttpRequest 객체 정의 */
        httpRequest = new XMLHttpRequest();

        /* POST 방식으로 요청 */
        httpRequest.open('POST', "/like/"+boardId);

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
        /* XMLHttpRequest 객체 정의 */
        httpRequest = new XMLHttpRequest();

        /* DELETE 방식으로 요청 */
        httpRequest.open('DELETE', "/like/"+boardId);

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

/* 참가 신청 */
function recruit(userid) {
    const boardId= document.getElementById("boardId").value;
    $.ajax({
        url: '/board/recruit/' + boardId + '/' + userid,
        type: 'POST',
        success: function (data) {
            notification('참가신청완료!');
            document.getElementById("currentJoin").textContent++;
        },
        error: function (error) {
            notification('이미 참가 되어있는 모집입니다.');
        }
    });
}
/* 참가 취소 */
function recruitCancel(userid) {
    const boardId= document.getElementById("boardId").value;
    $.ajax({
        url: '/board/recruit-cancel/' + boardId + '/' + userid,
        type: 'DELETE',
        success: function (data) {
            notification('참가취소완료!');
            document.getElementById("currentJoin").textContent--;
        },
        error: function (error) {
            notification('참가상태가 아닙니다.');
        }
    });
}
/* 마감하기 */
async function recruitClose(boardId) {
    const title = document.getElementById("recruitCloseButton").textContent;
    var isConfirm = await confirm('','정말로 ' + title +' 하시겠습니까?');
    if(isConfirm.isConfirmed) {
        $.ajax({
            url: '/board/recruitClose/' + boardId,
            type: 'PATCH',
            success: function (data) {
                if (data) {
                    SuccessAlert('모집마감되었습니다.');
                    document.getElementById("recruitCloseButton").innerText = "마감취소";
                    document.getElementById("recruitCloseButton").style.backgroundColor = "#94324b";
                } else {
                    notification('모집마감취소.');
                    document.getElementById("recruitCloseButton").innerText = "마감하기";
                    document.getElementById("recruitCloseButton").style.backgroundColor = "#74d65c";
                }
            },
            error: function (error) {
                notification('다시 시도해주세요.');
            }
        });
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
        /* 대댓글일 경우 */
        comment = document.getElementById('recomment-content-' + parentId).value;
        isRecomment = true;
    }

    /* 공백 및 빈 문자열 체크 */
    if(!comment || comment.trim() === "") {
        alert("공백 또는 빈 문자열은 입력하실수 없습니다.");
        return false;
    }else {
        /* ajax */
        /* XMLHttpRequest 객체 정의 */
        httpRequest = new XMLHttpRequest();

        /* 입력된 데이터 Json 형식으로 변경 */
        var reqJson = new Object();
        reqJson.comment = comment;
        reqJson.boardId = boardId;

        /* POST 방식으로 요청 */
        if (isRecomment) {
            httpRequest.open('POST', "/comment/"+boardId+"/"+parentId);
        }else {
            httpRequest.open('POST', "/comment/"+boardId);
        }
        /* 요청 Header에 컨텐츠 타입은 Json으로 사전 정의 */
        httpRequest.setRequestHeader('Content-Type', 'application/json');
        /* ResponseType Json */
        httpRequest.responseType = "json";

        /* 정의된 서버에 Json 형식의 요청 Data를 포함하여 요청을 전송 */
        httpRequest.send(JSON.stringify(reqJson));

        /* httpRequest 상태 감지 */
        httpRequest.onreadystatechange = () => {
            /* readyState가 Done이고 응답 값이 200(ok) 일때 받아온 boolean으로 분기 */
            if(httpRequest.readyState === XMLHttpRequest.DONE) {
                if(httpRequest.status === 200) {
                    /* 대댓글의 경우 */
                    if(isRecomment) {
                        $('#ul-'+parentId).load(location.href + ' #ul-'+parentId + ' li');
                        $('#recomment-content-'+parentId).val('');
                        /* 답글의 경우 */
                    }else{
                        $('#commentList').load(location.href + ' #commentList');
                        $('#comment').val('');
                    }
                }else{
                    console.log(httpRequest.response);
                }
            }
        }
    }
}


/* DELETE */
function commentDelete(commentId, is_recooment) {
    if(confirm("정말 삭제하시겠습니까?")) {
        const boardId = document.getElementById("boardId").value;
        /* ajax */
        /* XMLHttpRequest 객체 정의 */
        httpRequest = new XMLHttpRequest();

        /* POST 방식으로 요청 */
        httpRequest.open('DELETE', "/admin/comment/"+commentId+"/" + boardId);

        /* 정의된 서버에 Json 형식의 요청 Data를 포함하여 요청을 전송 */
        httpRequest.send();

        /* httpRequest 상태 감지 */
        httpRequest.onreadystatechange = () => {
            /* readyState가 Done이고 응답 값이 200(ok) 일때 받아온 boolean으로 분기 */
            if (httpRequest.readyState === XMLHttpRequest.DONE) {
                if (httpRequest.status === 200) {
                    if(is_recooment === 'comment') {
                        $('#commentList').load(location.href + ' #commentList');
                    }else{
                        const parentId = $('#multi-collapse-'+commentId).closest("ul").attr("id");
                        $('#'+parentId).load(location.href + ' #'+parentId + ' li');
                    }
                } else {
                    let error = httpRequest.response;
                    console.log(error.message);
                }
            }

        }
    }
}


