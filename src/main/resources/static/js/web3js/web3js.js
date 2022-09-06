console.log(web3);

/* Web3가 브라우저에 주입되었는지 확인(Mist/MetaMask) */
if (typeof web3 !== 'undefined') {
    /* Mist/MetaMask의 프로바이더 사용 */
    web3 = new Web3(web3.currentProvider);
    alert('web3가 주입되었습니다.');
} else {
    /* 사용자가 Metamask를 설치하지 않은 경우에 대해 처리 */
    /* 사용자들에게 Metamask를 설치하라는 등의 메세지를 보여줄 것 */
    web3 = new Web3(new Web3.providers.HttpProvider("http://localhost:8545"));
}


/* 사용자가 사용 중 이라고 브라우저가 인식하는 계정 */
let userAccount;

let checkAccountChange = setInterval(async function() {
    /* 계정이 바뀌었는지 확인 */
    let currentAccount = await web3.eth.requestAccounts().then(function(array) { return array[0] });
    /* 현재 유저가 들고있는 계정(currentAccount)가 브라우저가 인식하는 계정(userAccount)와 다르다면 */
    if (currentAccount !== userAccount) {
        /* 계정을 업데이트 해준다 */
        userAccount = currentAccount;
        /* 새 계정에 대한 UI로 업데이트하기 위한 함수 호출 및 메시지 알림 */
        alert('Your account is ' + userAccount);
    }
}, 1000);     /* 1초 마다 계정 확인 */