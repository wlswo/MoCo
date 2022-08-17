var idCheck = false;
var passCheck = false;

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
//바닐라 자바스크립트 ajax , id 중복체크
function checkemailDuplication(){
    const idvaild = document.getElementById('idAvailable');
    const idnotvaild = document.getElementById('idNotAvailable');
    let id = document.getElementById("email").value;

    // 검증에 사용할 정규식 변수 regExp에 저장
    let regExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;

    if(!id) {
        idvaild.style.display = "none";
        idnotvaild.style.display = "block";
        idnotvaild.innerText = "아이디를 입력하세요."
        idCheck = false;
        return false;
    }

    if(id.match(regExp) == null) {
        idvaild.style.display = "none";
        idnotvaild.style.display = "block";
        idnotvaild.innerText = "이메일 형식이 올바르지 않습니다."
        idCheck = false;
        return false;
    }

    const baseUrl = "http://localhost:8080";
    /* XMLHttpRequest 객체 정의 */
    httpRequest = new XMLHttpRequest();

    /* GET 방식으로 요청 */
    httpRequest.open('GET', baseUrl+"/id/check?email="+id);
    /* ResponseType Json */
    httpRequest.responseType = "text";
    httpRequest.send();

    /* httpRequest 상태 감지 */
    httpRequest.onreadystatechange = () => {
        /* readyState가 Done이고 응답 값이 200(ok) 일때 받아온 boolean으로 분기 */
        if(httpRequest.readyState === XMLHttpRequest.DONE) {


            if(httpRequest.status === 200) {
                let result = httpRequest.response;
                console.log(result)
                // 아이디 중복검사 성공 시(중복된 아이디가 존재하지 않을 시) 해당 아이디 사용 가능 문구 출력
                idnotvaild.style.display = "none";
                idvaild.style.display = "block";
                idvaild.innerText = result;
                idCheck = true;
            }else{
                let error = JSON.parse(httpRequest.response);
                console.log(error.message);
                // 아이디 중복검사 실패 시(중복된 아이디가 존재할 시) Exception객체에 담긴 해당 아이디 사용 불가능 안내 문구 출력
                idvaild.style.display = "none";
                idnotvaild.style.display = "block";
                idnotvaild.innerText = error.message;
                idCheck = false;
            }
        }
    }
}
/* password 체크 */
function repassCheck() {
    var password = document.getElementById("pass").value;
    var repassword = document.getElementById("re_pass").value;

    const pwvaild = document.getElementById('pwAvailable');
    const pwnotvaild = document.getElementById('pwNotAvailable');

    if(password != repassword) {
        pwvaild.style.display = "none";
        pwnotvaild.style.display = "block";
        pwnotvaild.innerText = "비밀번호가 다릅니다.";
        passCheck = false;
    }
    else if(!repassword){
        pwvaild.style.display = "none";
        pwnotvaild.style.display = "block";
        pwnotvaild.innerText = "비밀번호를 입력하세요.";
        passCheck = false;
    }
    else{
        pwnotvaild.style.display = "none";
        pwvaild.style.display = "block";
        pwvaild.innerText = "비밀번호가 같습니다.";
        passCheck = true;
    }
}


function LastCheck() {
    var nameCheck = document.getElementById("name").value;

    if(idCheck === false) {
        alert("아이디 중복체크를 하지 않으셨습니다.");
        return false;
    }

    if(passCheck === false) {
        alert("비밀번호가 틀립니다.")
        return false
    }
    if(!nameCheck) {
        alert("이름을 입력하세요.");
        return false;
    }
    return true;
}

function aa() {
    console.log("ss")
}