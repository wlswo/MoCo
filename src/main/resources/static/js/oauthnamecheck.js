var nameCheck = false;

function nameLastCheck() {

    if(!nameCheck) {
        alert("별명 중복체크를 해주세요.");
        return false;
    }

}

//바닐라 자바스크립트 ajax , name 중복체크
function checknameDuplication(){
    const namevaild = document.getElementById('nameAvailable');
    const namenotvaild = document.getElementById('nameNotAvailable');
    let nickname = document.getElementById("name").value;

    //특수문자를 제외한 2~10 글자 사이
    const regExp = /^([a-zA-Z0-9ㄱ-ㅎ|ㅏ-ㅣ|가-힣]).{2,10}$/;

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