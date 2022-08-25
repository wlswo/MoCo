let idCheck = false;
let passCheck = false;
let nameCheck = false;

/* id 중복체크 */
function checkemailDuplication(){
    const idvaild = document.getElementById('idAvailable');
    const idnotvaild = document.getElementById('idNotAvailable');
    let id = document.getElementById("email").value;

    /* 검증에 사용할 정규식 변수 regExp에 저장 */
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
/* 별명중복체크 */
function checknameDuplication(){
    const namevaild = document.getElementById('nameAvailable');
    const namenotvaild = document.getElementById('nameNotAvailable');
    let nickname = document.getElementById("name").value;

    //특수문자를 제외한 2~10 글자 사이
    const regExp = /^([a-zA-Z0-9ㄱ-ㅎ|ㅏ-ㅣ|가-힣]).{1,10}$/;

    if(!nickname) {
        namevaild.style.display = "none";
        namenotvaild.style.display = "block";
        namenotvaild.innerText = "아이디를 입력하세요."
        nameCheck = false;
        return false;
    }

    if(!regExp.test(nickname)) {
        namevaild.style.display = "none";
        namenotvaild.style.display = "block";
        namenotvaild.innerText = "닉네임 형식이 올바르지 않습니다. 2~10 글자 사이로 입력해주세요.(특수문자 제외)"
        nameCheck = false;
        return false;
    }

    const baseUrl = "http://localhost:8080";
    /* XMLHttpRequest 객체 정의 */
    httpRequest = new XMLHttpRequest();

    /* GET 방식으로 요청 */
    httpRequest.open('GET', baseUrl+"/name/check?nickname="+nickname);
    /* ResponseType text */
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
                namenotvaild.style.display = "none";
                namevaild.style.display = "block";
                namevaild.innerText = result;
                nameCheck = true;
            }else{
                let error = JSON.parse(httpRequest.response);
                console.log(error.message);
                // 아이디 중복검사 실패 시(중복된 아이디가 존재할 시) Exception객체에 담긴 해당 아이디 사용 불가능 안내 문구 출력
                namevaild.style.display = "none";
                namenotvaild.style.display = "block";
                namenotvaild.innerText = error.message;
                nameCheck = false;
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

/* 최종 체크 */
function LastCheck() {
    var nameCheck = document.getElementById("name").value;

    if(idCheck === false) {
        alert("아이디 중복체크를 하지 않으셨습니다.");
        return false;
    }
    if (nameCheck === false) {
        alert("별명 중복체크를 하지 않으셨습니다.");
    }
    if(passCheck === false) {
        alert("비밀번호가 틀립니다.");
        return false;
    }
    if(!nameCheck) {
        alert("이름을 입력하세요.");
        return false;
    }
    return true;
}
