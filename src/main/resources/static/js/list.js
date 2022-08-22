/* 무한 스크롤 */
var loading = false; //중복실행 체크
var page = 2;        //불러올 페이지
const totalPage = document.getElementById("totalPage").value;
const Articles = document.querySelector('#articles');

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
    httpRequest.open('GET', baseUrl + "/board/listJson/?page="+page);

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
                    let {id,title,writer,content,thumbnail,view,createdDate,modifiedDate,usetId,comments,likes} = item;
                    /* 날짜 포맷 변경 */
                    CustomDate = createdDate.toString().split('T')[0].split(/-/);
                    createdDate = CustomDate[0]+"년 "+CustomDate[1]+"월 "+CustomDate[2]+"일";
                    /* 데이터 삽입 */
                    const Article = document.createElement('article');
                    postHtml = `<a href="/board/post/read/${id}"><figure>
                                   <img src="${thumbnail}" alt="" />
                                </figure></a>
                                   <div class="article-body">
                                    <a href="/board/post/read/${id}">${title}</a>
                                    <span>
                                      ${createdDate} • ${comments.length}개의 댓글
                                    </span>
                                   </div>
                                   <div class="article-bottom">
                                    <div class="read-more">
                                        by : <b> ${writer}</b> &ensp;view:${view}
                                        <div class="like">
                                          <svg xmlns="http://www.w3.org/2000/svg" width="15" height="15" class="bi bi-heart-fill" viewBox="0 0 24 24" fill="currentColor">
                                            <path fill-rule="evenodd" d="M18 1l-6 4-6-4-6 5v7l12 10 12-10v-7z" clip-rule="evenodd" />
                                          </svg>
                                          <b>&nbsp;️${likes.length}</b>
                                        </div>
                                    </div>
                                   </div>`;

                    page++;
                    Article.innerHTML = postHtml;
                    Articles.appendChild(Article);
                });
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
    document.getElementById('articles').innerHTML =  sessionStorage.getItem('data');
}

/* 페이지를 벗어날때 스크롤 위치,데이터 기억 */
window.onbeforeunload = function (ev) {
    /* 스크롤위치 */
    setCookie('scroll',window.scrollY,1);

    /* 현재까지 로드된 데이터들 */
    window.sessionStorage.setItem('data',document.getElementById('articles').innerHTML);

    /* AJAX 호출을 막기위한 PAGE */
    setCookie('page',page,1);
};