/* ajax
function checkemailDuplication(){
        var baseUrl = "http://localhost:8080"

        $.ajax({
        url: baseUrl + '/id/check',
        type: 'GET',
        contentType: 'application/json',
        headers: {
        // 스프링 시큐리티 헤더 설정
        "X-CSRF-TOKEN": $("meta[name='_csrf']").attr("content")
    },
        data: {
        email: $('#email').val()
    },
        success: function (result) {
        // 아이디 중복검사 성공 시(중복된 아이디가 존재하지 않을 시) 해당 아이디 사용 가능 문구 출력
        $('#idNotAvailable').hide();
        $('#idAvailable').show().text(result)
    }, error: function(error) {
        // 아이디 중복검사 실패 시(중복된 아이디가 존재할 시) Exception객체에 담긴 해당 아이디 사용 불가능 안내 문구 출력
        $('#idAvailable').hide();
        $('#idNotAvailable').show().text(error.responseJSON['message'])
    }
    });
}
 */
//바닐라 자바스크립트
function test(){
    let csrf = document.getElementsByTagName('meta').item(name='_csrf').getAttribute("content");

    /* 현재 헤더 인스턴스 생성 */
    const myHeaders = new Headers();
    myHeaders.set("X-CSRF-TOKEN",csrf);

    const baseUrl = "http://localhost:8080";
    let id = document.getElementById("email").value;
    /* XMLHttpRequest 객체 정의 */
    httpRequest = new XMLHttpRequest();

    /* GET 방식으로 요청 */
    httpRequest.open('GET', baseUrl+"/id/check?email="+id);
    /* ResponseType Json */
    httpRequest.responseType = "text";
    httpRequest.send();

    const vaild = document.getElementById('idAvailable');
    const notvaild = document.getElementById('idNotAvailable');

    /* httpRequest 상태 감지 */
    httpRequest.onreadystatechange = () => {
        /* readyState가 Done이고 응답 값이 200(ok) 일때 받아온 boolean으로 분기 */
        if(httpRequest.readyState === XMLHttpRequest.DONE) {
            if(httpRequest.status === 200) {
                let result = httpRequest.response;
                console.log(result)
                // 아이디 중복검사 성공 시(중복된 아이디가 존재하지 않을 시) 해당 아이디 사용 가능 문구 출력
                notvaild.style.display = "none";
                vaild.style.display = "block";
                vaild.innerText = result;
            }else{
                let error = JSON.parse(httpRequest.response);
                console.log(error.message);
                // 아이디 중복검사 실패 시(중복된 아이디가 존재할 시) Exception객체에 담긴 해당 아이디 사용 불가능 안내 문구 출력
                vaild.style.display = "none";
                notvaild.style.display = "block";
                notvaild.innerText = error.message;
            }
        }
    }

}