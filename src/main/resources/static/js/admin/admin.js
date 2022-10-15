/* 무한 스크롤 */
let loading = false; //중복실행 체크
let page = 2;        //불러올 페이지
const totalPage = document.getElementById("totalPage").value;
let container = document.getElementById('container');
let toggleActive = true;
/* 모집중인 게시글만 보기 토글 버튼 이벤트 부여 */
const toggle = document.getElementById("recruitOntoggle");
toggle.addEventListener("change" ,()=>{
    toggleActive = toggle.checked;
    if (toggleActive) {
        $('#top-container').load(location.href+' #container',() => {
            container = document.getElementById('container');
            loading = false;
            page = 2;
        });
    }else {
        $('#top-container').load("http://localhost:8080/board/AllBoard"+' #container',() => {
            container = document.getElementById('container');
            loading = false;
            page = 2;
        });
    }
});
/* 게시글 호출 Function */
function nextPage(){
    if(loading) {
        return false;
    }
    loading = true;
    /* ajax */
    const baseUrl = "http://localhost:8080";
    /* XMLHttpRequest 객체 정의 */
    httpRequest = new XMLHttpRequest();

    /* POST 방식으로 요청 */
    httpRequest.open('GET', baseUrl + "/board/listJson/"+page+"/"+toggleActive);

    /* ResponseType Json */
    httpRequest.responseType = "json";

    /* 정의된 서버에 Json 형식의 요청 Data를 포함하여 요청을 전송 */
    httpRequest.send();

    /* httpRequest 상태 감지 */
    httpRequest.onreadystatechange = () => {
        /* readyState가 Done이고 응답 값이 200(ok) 일때 받아온 boolean으로 분기 */
        if (httpRequest.readyState === XMLHttpRequest.DONE) {
            if (httpRequest.status === 200) {
                let success = httpRequest.response;
                success.forEach((item,index) => {
                    /* 구조분해할당 */
                    let {id,title,writer,thumbnail,subcontent,view,created_date,user_id,picture,comment_count,like_count,hashTag,isfull} = item;
                    /* 날짜 포맷 변경 */
                    CustomDate = created_date.split('T')[0].split(/-/);
                    createdDate = CustomDate[0]+"년 "+CustomDate[1]+"월 "+CustomDate[2]+"일";

                    /* 썸네일 값 여부 */
                    if(thumbnail == null || thumbnail.trim() == ''){
                        thumbnail = '/img/panda.png';
                    }
                    /* 유저 이미지 여부 */
                    if(picture == null || picture.trim() == '') {
                        picture = '/img/userIcon.svg';
                    }
                    /* 해시태그 여부 */
                    if(!hashTag) {
                        hashTag = "";
                    }else {
                        hashTag += "#";
                    }
                    /* 댓글 개수 null 체크 */
                    if(comment_count == null) {
                        comment_count = 0;
                    }
                    /* 좋아요 null 체크 */
                    if (like_count == null) {
                        like_count = 0;
                    }


                    let full = ``;
                    /* 모집 마감 여부 */
                    if(isfull) {
                        isfull = "isFullPost";
                        full = `<div class="isFull">모집 마감</div>`;
                    }

                    const card = document.createElement('span');
                    /* 데이터 삽입 */
                    let postHtml = `<div class="card ${isfull}" >
                                  <div class="card-header">`
                        + full +
                        `<!-- 썸네일 -->
                                    <a href="/admin/read/${id}">
                                    <span>
                                       <img src="${thumbnail}" alt="썸네일" />
                                    </span>
                                    </a>
                                  </div>
                                  <!-- 몸통 -->
                                  <div class="card-body">
                                    <!-- 해시태그 -->
                                    <span class="tag tag-pink">${hashTag}</span>
                                    <!-- 제목 -->
                                    <a href="/board/post/read/${id}"><h4> ${title} </h4></a>
                                    <!-- 글 내용 -->
                                    <p>
                                      ${subcontent}
                                    </p>
                                    <!-- 좋아요 -->
                                    <div class="like">
                                      <svg xmlns="http://www.w3.org/2000/svg" width="15" height="15" class="bi bi-heart-fill" viewBox="0 0 24 24" fill="currentColor">
                                        <path fill-rule="evenodd" d="M18 1l-6 4-6-4-6 5v7l12 10 12-10v-7z" clip-rule="evenodd" />
                                      </svg>
                                      <b>&nbsp;️${like_count}</b> • ${view} views
                                    </div>
                                    <!-- 유저 정보 -->
                                    <div class="user">
                                      <img src="${picture}" alt="user" />
                                      <div class="user-info">
                                        <h5>${writer}</h5>
                                        <small> ${createdDate} • <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-chat-dots" viewBox="0 0 16 20">
                                          <path d="M5 8a1 1 0 1 1-2 0 1 1 0 0 1 2 0zm4 0a1 1 0 1 1-2 0 1 1 0 0 1 2 0zm3 1a1 1 0 1 0 0-2 1 1 0 0 0 0 2z"/>
                                          <path d="m2.165 15.803.02-.004c1.83-.363 2.948-.842 3.468-1.105A9.06 9.06 0 0 0 8 15c4.418 0 8-3.134 8-7s-3.582-7-8-7-8 3.134-8 7c0 1.76.743 3.37 1.97 4.6a10.437 10.437 0 0 1-.524 2.318l-.003.011a10.722 10.722 0 0 1-.244.637c-.079.186.074.394.273.362a21.673 21.673 0 0 0 .693-.125zm.8-3.108a1 1 0 0 0-.287-.801C1.618 10.83 1 9.468 1 8c0-3.192 3.004-6 7-6s7 2.808 7 6c0 3.193-3.004 6-7 6a8.06 8.06 0 0 1-2.088-.272 1 1 0 0 0-.711.074c-.387.196-1.24.57-2.634.893a10.97 10.97 0 0 0 .398-2z"/>
                                        </svg>&nbsp;${comment_count}</small>
                                      </div>
                                    </div>
                                  </div>
                                </div>`;
                    card.innerHTML = postHtml;
                    container.appendChild(card)
                });
                page++;
            } else {
                let error = httpRequest.response;
                console.log(error.message);
            }
        }

    }
}


/* 스크롤 이벤트 */
window.addEventListener("scroll", function () {
    const Scrolled_height = window.scrollY;
    const Window_height = window.innerHeight;
    const Doc_total_height = document.body.offsetHeight;
    const isBottom = Window_height + Scrolled_height + 100 >= Doc_total_height;
    if (isBottom) {
        nextPage();
    }
});

/* Cookie Set */
function setCookie(name, value, exp) {
    var date = new Date();
    date.setTime(date.getTime() + exp*24*60*60*1000);
    document.cookie = name + '=' + value + ';expires=' + date.toUTCString() + ';path=/';
}
/* Cookie GET */
function getCookie(name) {
    var value = document.cookie.match('(^|;) ?' + name + '=([^;]*)(;|$)');
    return value? value[2] : null;
}


/* 뒤로가기로 왔을 경우 스크롤 위치 유지 */
window.onpageshow = async function (e) {
    /* BackForward Cache로 브라우저가 로딩될 경우 혹은 브라우저 뒤로가기 했을 경우 */
    if (e.persisted || window.performance.getEntriesByType("navigation")[0].type === 'back_forward') {
        page = getCookie('page');
        /* 데이터 로드 */
        await loadPage();
        /* 스크롤 이동 */
        window.scrollTo(0, getCookie('scroll'));
    }
}
function loadPage(){
    document.getElementById('container').innerHTML =  sessionStorage.getItem('data');
}

/* 페이지를 벗어날때 스크롤 위치,데이터 기억 */
window.onbeforeunload = function (ev) {
    /* 스크롤위치 */
    setCookie('scroll',window.scrollY,1);

    /* 현재까지 로드된 데이터들 */
    window.sessionStorage.setItem('data',document.getElementById('container').innerHTML);

    /* AJAX 호출을 막기위한 PAGE */
    setCookie('page',page,1);
};



