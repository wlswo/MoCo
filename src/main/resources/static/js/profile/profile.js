/* 지갑주소 표시 */
if(document.getElementById("wallet")){
    try{
        currentAccount = web3.eth.requestAccounts().then(function(accounts) {
            document.getElementById("wallet").textContent = accounts[0];
            document.getElementById("wallet").style.color = "#6f42c1c7";
        });
        /* 계정 변경 감지 */
        window.ethereum.on("accountsChanged", async function() {
            // Time to reload your interface with accounts[0]!
            accounts = await web3.eth.getAccounts();
            // accounts = await web3.eth.getAccounts();
            document.getElementById("wallet").textContent = accounts[0]
            document.getElementById("wallet").style.color = "#6f42c1c7";
        });
    }catch (e) {
        document.getElementById("wallet").textContent = "MetaMask 연동되지 않은 상태입니다.";
        document.getElementById("wallet").style.color = "#e76876";
    }
}

/* 이름변경 ajax */
function settingConfirm() {
    const nickname = document.getElementById("nickname").value;
    const userid = document.getElementById("userid").value;
    console.log(nickname);

    //특수문자를 제외한 2~10 글자 사이
    const regExp = /[\{\}\[\]\/?.,;:|\)*~`!^\-_+<>@\#$%&\\\=\(\'\"]/g;

    if(nickname === '' | !nickname) {
        notification('별명을 입력해주세요.');
        return false;
    }
    if(regExp.test(nickname)) {
        notification('특수문자는 입력하실수 없습니다.')
        return false;
    }
    if(nickname.length < 2 || nickname.length > 10) {
        notification('2~10글자 사이로 입력해주세요.');
        return false;
    }
    $.ajax({
        url: 'http://localhost:8080/profile/change/' + nickname,
        type: 'PATCH',
        data: {"userid":userid},
        success: function (data) {
            window.location.href = "/board/list";
        },
        error: function (error) {
            notification('이미 존재하는 별명입니다.');
        }
    });
}
/* 회원탈퇴 */
async function deleteUser() {
    const userid = document.getElementById("userid").value;
    var isConfirm = await confirm('','정말로 탈퇴 하시겠습니까?');
    if(isConfirm.isConfirmed) {
        $.ajax({
            url: 'http://localhost:8080/profile/delete',
            type: 'DELETE',
            success: function (data) {
                window.location.href = "/logout";
            },
            error: function (error) {
                notification('이미 존재하는 별명입니다.');
            }
        });
    }
}


/* 경고창 */
function notification(message) {
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
        title: '<h4 style="color: white;">' + message + '</h4>'
    })
    return false;
}
/* 확인창 */
function confirm(text, title) {
    return new Promise(function(resolve, reject) {
        Swal.fire({
            title: title,
            text: text,
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: '확인',
            cancelButtonText: '취소'
        }).then((result) => {
            resolve(result);
        })
    })
}

let currentAccount ;
let web3;
/* 메타마스크 연결 */
window.addEventListener('load', async () => {
    if (window.ethereum) {
        web3 = new Web3(window.ethereum);
        await window.ethereum.request({
            method: "wallet_switchEthereumChain",
            params: [{ chainId: '0x5', }],
        });
    } else if (typeof window.web3 !== 'undefined') {
        web3 = new Web3(window.web3.currentProvider);
    } else {
        reject(new Error('No web3 instance injected, using local web3.'))
    }
    if (web3) {
        currentAccount = await web3.eth.requestAccounts();
    }
});

/* 메타마스크 연결 */
document.getElementById("ConnectMetaMask").addEventListener('click', async () => {
    await ethereum.request({ method: 'eth_requestAccounts' });
    await window.ethereum.request({
        method: "wallet_switchEthereumChain",
        params: [{ chainId: '0x5', }],
    });
});