let currentAccount ;

/* Web3가 브라우저에 주입되었는지 확인(Mist/MetaMask) */
if (typeof web3 !== 'undefined') {
    /* Mist/MetaMask의 프로바이더 사용 */
    web3 = new Web3(web3.currentProvider);
    console.log('web3가 주입되었습니다.');
} else {
    /* 사용자가 Metamask를 설치하지 않은 경우에 대해 처리 */
    /* 사용자들에게 Metamask를 설치하라는 등의 메세지를 보여줄 것 */
    web3 = new Web3(new Web3.providers.HttpProvider("https://goerli.infura.io"));
}

/* 메타마스크 연결 */
if(document.getElementById("ConnectMetaMask")){
    const tokenAddress = '0x1F06dd241b5527ff74ffE4CFF97ba66e424E718E';
    const tokenSymbol = 'BmB';
    const tokenDecimals = 18;

    document.getElementById("ConnectMetaMask").addEventListener("click", async function (event){
        currentAccount = await web3.eth.requestAccounts().then(function(array) { return array[0] });
        console.log(currentAccount);
        try {
            const isAdded = await web3.currentProvider.request({
                method: 'wallet_watchAsset',
                params: {
                    type: 'ERC20',
                    options: {
                        address: tokenAddress,
                        symbol: tokenSymbol,
                        decimals: tokenDecimals,
                        // image: tokenImage, // if you have the image, it goes here
                    },
                },
            });
        } catch (error) {
            console.log(error)
        }
    });

}

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
