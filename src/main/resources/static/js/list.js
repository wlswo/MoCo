/* 무한 스크롤 */
var loading = false; //중복실행 체크
var page = 2;        //불러올 페이지
const totalPage = document.getElementById("totalPage").value;
const Articles = document.querySelector('#articles');

/* 게시글 호출 Function */
function nextPage(){
    if(!loading) {
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
                let succes = httpRequest.response;
                succes.forEach((item,index) => {
                    const {id,title,writer,content,view,createdDate,modifiedDate,usetId,comments,likes} = item;
                    const Article = document.createElement('article');
                    postHtml = `<figure>
                                   <img src="https://velog.velcdn.com/images/lilseongwon/post/abb71e17-4d0c-465b-a9c7-418cca3dbb51/image.png" alt="" />
                                </figure>
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
        console.log(totalPage)
        if(page > totalPage) {
            return false;
        }

        nextPage();
    }
});