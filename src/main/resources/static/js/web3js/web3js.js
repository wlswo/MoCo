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

if(document.getElementById("wallet")){
    try{
        currentAccount = web3.eth.requestAccounts().then(function(accounts) {
            document.getElementById("wallet").textContent = accounts[0];
        });
        /* 계정 변경 감지 */
        window.ethereum.on("accountsChanged", async function() {
            // Time to reload your interface with accounts[0]!
            accounts = await web3.eth.getAccounts();
            // accounts = await web3.eth.getAccounts();
            document.getElementById("wallet").textContent = accounts[0]
        });
    }catch (e) {
        document.getElementById("wallet").textContent = "MetaMask 연동을 하지 않으면 토큰을 받을수 없습니다.";
    }
}
