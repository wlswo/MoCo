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
        reject(new Error('web3 인스턴스가 주입되지 않았습니다.'));
    }
    if (web3) {
        currentAccount = await web3.eth.requestAccounts();
    }
});

/* 메타마스크 연결 */
if (document.getElementById("ConnectMetaMask")) {
    document.getElementById("ConnectMetaMask").addEventListener('click', async () => {
        await ethereum.request({ method: 'eth_requestAccounts' });
        await window.ethereum.request({
            method: "wallet_switchEthereumChain",
            params: [{ chainId: '0x5', }],
        });
    });
}

/* 메타마스크에 토큰추가 */
const getTokenInformation = async function (event){
    currentAccount = await web3.eth.requestAccounts().then(function(array) { return array[0] });
    web3.eth.getAccounts(console.log);
    console.log(currentAccount);
    try {
        const isAdded = await web3.currentProvider.request({
            method: 'wallet_watchAsset',
            params: {
                type: 'ERC20',
                options: {
                    address: '0x7451425A01Cd543946f7b7DfE99B5010553895Ec',
                    symbol: 'MoGaKKoin',
                    decimals: 18,
                    image: 'https://myzzbucket.s3.ap-northeast-2.amazonaws.com/tokenIcon.png', // if you have the image, it goes here
                },
            },
        });
    } catch (error) {
        console.log(error)
    }
}
if (document.getElementById("GetWelcomeTokenInfo")){
    document.getElementById("GetWelcomeTokenInfo").addEventListener("click",getTokenInformation);
}
if(document.getElementById("GetTokenInfo")){
    document.getElementById("GetTokenInfo").addEventListener("click", getTokenInformation);
}
/* 출석체크 모달창 생성시 이벤트 감지 */
$("#exampleModal").on('show.bs.modal', async function (e) {
        try {
            currentAccount = web3.eth.requestAccounts().then(function (accounts) {
                document.getElementById("wallet").value = accounts[0];
            });
            /* 네트워크 goerli 로 세팅 */
            await window.ethereum.request({
                method: "wallet_switchEthereumChain",
                params: [{ chainId: '0x5', }],
            });
            /* 계정 변경 감지 */
            window.ethereum.on("accountsChanged", async function () {
                // Time to reload your interface with accounts[0]!
                accounts = await web3.eth.getAccounts();
                currentAccount = accounts[0];
                document.getElementById("wallet").value = currentAccount;
            });
        } catch (e) {
            document.getElementById("wallet").value = "MetaMask 연동 되지 않았습니다.";
        }
});
/* 가입보상 모달창 생성시 이벤트 감지 */
$("#welcomeModal").on('show.bs.modal', async function (e) {
    try {
        currentAccount = web3.eth.requestAccounts().then(function (accounts) {
            document.getElementById("welcome-wallet").value = accounts[0];
        });
        /* 네트워크 goerli 로 세팅 */
        await window.ethereum.request({
            method: "wallet_switchEthereumChain",
            params: [{ chainId: '0x5', }],
        });
        /* 계정 변경 감지 */
        window.ethereum.on("accountsChanged", async function () {
            // Time to reload your interface with accounts[0]!
            accounts = await web3.eth.getAccounts();
            currentAccount = accounts[0];
            document.getElementById("welcome-wallet").value = currentAccount;
        });
    } catch (e) {
        document.getElementById("welcome-wallet").value = "MetaMask 연동 되지 않았습니다.";
    }
});
/* 도트구매 모달창 생성시 이벤트 감지 */
$("#buyDotModal").on('show.bs.modal', async function (e) {
    try {
        currentAccount = web3.eth.requestAccounts().then(function (accounts) {
            document.getElementById("buydot-wallet").value = accounts[0];
        });
        /* 네트워크 goerli 로 세팅 */
        await window.ethereum.request({
            method: "wallet_switchEthereumChain",
            params: [{ chainId: '0x5', }],
        });
        /* 계정 변경 감지 */
        window.ethereum.on("accountsChanged", async function () {
            // Time to reload your interface with accounts[0]!
            accounts = await web3.eth.getAccounts();
            currentAccount = accounts[0];
            document.getElementById("buydot-wallet").value = currentAccount;
        });
    } catch (e) {
        document.getElementById("buydot-wallet").value = "MetaMask 연동 되지 않았습니다.";
    }
});
/* 스마트 컨트랙트 abi 정보 */
const abi = [
    {
        "inputs": [],
        "stateMutability": "nonpayable",
        "type": "constructor"
    },
    {
        "anonymous": false,
        "inputs": [
            {
                "indexed": true,
                "internalType": "address",
                "name": "owner",
                "type": "address"
            },
            {
                "indexed": true,
                "internalType": "address",
                "name": "spender",
                "type": "address"
            },
            {
                "indexed": false,
                "internalType": "uint256",
                "name": "value",
                "type": "uint256"
            }
        ],
        "name": "Approval",
        "type": "event"
    },
    {
        "anonymous": false,
        "inputs": [
            {
                "indexed": true,
                "internalType": "address",
                "name": "from",
                "type": "address"
            },
            {
                "indexed": true,
                "internalType": "address",
                "name": "to",
                "type": "address"
            },
            {
                "indexed": false,
                "internalType": "uint256",
                "name": "value",
                "type": "uint256"
            }
        ],
        "name": "Transfer",
        "type": "event"
    },
    {
        "anonymous": false,
        "inputs": [
            {
                "indexed": true,
                "internalType": "address",
                "name": "from",
                "type": "address"
            },
            {
                "indexed": true,
                "internalType": "address",
                "name": "to",
                "type": "address"
            },
            {
                "indexed": false,
                "internalType": "uint256",
                "name": "tokens",
                "type": "uint256"
            }
        ],
        "name": "TransferToken",
        "type": "event"
    },
    {
        "inputs": [],
        "name": "Buy_Level1_Dot",
        "outputs": [
            {
                "internalType": "bool",
                "name": "",
                "type": "bool"
            }
        ],
        "stateMutability": "nonpayable",
        "type": "function"
    },
    {
        "inputs": [],
        "name": "Buy_Level2_Dot",
        "outputs": [
            {
                "internalType": "bool",
                "name": "",
                "type": "bool"
            }
        ],
        "stateMutability": "nonpayable",
        "type": "function"
    },
    {
        "inputs": [],
        "name": "Buy_Level3_Dot",
        "outputs": [
            {
                "internalType": "bool",
                "name": "",
                "type": "bool"
            }
        ],
        "stateMutability": "nonpayable",
        "type": "function"
    },
    {
        "inputs": [
            {
                "internalType": "address",
                "name": "owner",
                "type": "address"
            },
            {
                "internalType": "address",
                "name": "spender",
                "type": "address"
            }
        ],
        "name": "allowance",
        "outputs": [
            {
                "internalType": "uint256",
                "name": "",
                "type": "uint256"
            }
        ],
        "stateMutability": "view",
        "type": "function"
    },
    {
        "inputs": [
            {
                "internalType": "address",
                "name": "spender",
                "type": "address"
            },
            {
                "internalType": "uint256",
                "name": "amount",
                "type": "uint256"
            }
        ],
        "name": "approve",
        "outputs": [
            {
                "internalType": "bool",
                "name": "",
                "type": "bool"
            }
        ],
        "stateMutability": "nonpayable",
        "type": "function"
    },
    {
        "inputs": [
            {
                "internalType": "address",
                "name": "account",
                "type": "address"
            }
        ],
        "name": "balanceOf",
        "outputs": [
            {
                "internalType": "uint256",
                "name": "",
                "type": "uint256"
            }
        ],
        "stateMutability": "view",
        "type": "function"
    },
    {
        "inputs": [],
        "name": "decimals",
        "outputs": [
            {
                "internalType": "uint8",
                "name": "",
                "type": "uint8"
            }
        ],
        "stateMutability": "view",
        "type": "function"
    },
    {
        "inputs": [
            {
                "internalType": "address",
                "name": "spender",
                "type": "address"
            },
            {
                "internalType": "uint256",
                "name": "subtractedValue",
                "type": "uint256"
            }
        ],
        "name": "decreaseAllowance",
        "outputs": [
            {
                "internalType": "bool",
                "name": "",
                "type": "bool"
            }
        ],
        "stateMutability": "nonpayable",
        "type": "function"
    },
    {
        "inputs": [],
        "name": "getTokenOnce",
        "outputs": [
            {
                "internalType": "bool",
                "name": "",
                "type": "bool"
            }
        ],
        "stateMutability": "nonpayable",
        "type": "function"
    },
    {
        "inputs": [],
        "name": "getTokenOneDay",
        "outputs": [
            {
                "internalType": "bool",
                "name": "",
                "type": "bool"
            }
        ],
        "stateMutability": "nonpayable",
        "type": "function"
    },
    {
        "inputs": [
            {
                "internalType": "address",
                "name": "spender",
                "type": "address"
            },
            {
                "internalType": "uint256",
                "name": "addedValue",
                "type": "uint256"
            }
        ],
        "name": "increaseAllowance",
        "outputs": [
            {
                "internalType": "bool",
                "name": "",
                "type": "bool"
            }
        ],
        "stateMutability": "nonpayable",
        "type": "function"
    },
    {
        "inputs": [
            {
                "internalType": "address",
                "name": "",
                "type": "address"
            }
        ],
        "name": "isChecked",
        "outputs": [
            {
                "internalType": "uint8",
                "name": "",
                "type": "uint8"
            }
        ],
        "stateMutability": "view",
        "type": "function"
    },
    {
        "inputs": [],
        "name": "name",
        "outputs": [
            {
                "internalType": "string",
                "name": "",
                "type": "string"
            }
        ],
        "stateMutability": "view",
        "type": "function"
    },
    {
        "inputs": [],
        "name": "symbol",
        "outputs": [
            {
                "internalType": "string",
                "name": "",
                "type": "string"
            }
        ],
        "stateMutability": "view",
        "type": "function"
    },
    {
        "inputs": [],
        "name": "totalSupply",
        "outputs": [
            {
                "internalType": "uint256",
                "name": "",
                "type": "uint256"
            }
        ],
        "stateMutability": "view",
        "type": "function"
    },
    {
        "inputs": [
            {
                "internalType": "address",
                "name": "to",
                "type": "address"
            },
            {
                "internalType": "uint256",
                "name": "amount",
                "type": "uint256"
            }
        ],
        "name": "transfer",
        "outputs": [
            {
                "internalType": "bool",
                "name": "",
                "type": "bool"
            }
        ],
        "stateMutability": "nonpayable",
        "type": "function"
    },
    {
        "inputs": [
            {
                "internalType": "address",
                "name": "from",
                "type": "address"
            },
            {
                "internalType": "address",
                "name": "to",
                "type": "address"
            },
            {
                "internalType": "uint256",
                "name": "amount",
                "type": "uint256"
            }
        ],
        "name": "transferFrom",
        "outputs": [
            {
                "internalType": "bool",
                "name": "",
                "type": "bool"
            }
        ],
        "stateMutability": "nonpayable",
        "type": "function"
    },
    {
        "inputs": [
            {
                "internalType": "address",
                "name": "",
                "type": "address"
            }
        ],
        "name": "userTimeStamp",
        "outputs": [
            {
                "internalType": "uint256",
                "name": "",
                "type": "uint256"
            }
        ],
        "stateMutability": "view",
        "type": "function"
    }
];
const contractAddress = '0x7451425A01Cd543946f7b7DfE99B5010553895Ec';
/* 출석체크 보상받기 - List Page */
if(document.getElementById("getTokenButton")){
    document.getElementById("getTokenButton").addEventListener('click',async ()=>{
        const account = await web3.eth.requestAccounts();
        const SmartContract = new web3.eth.Contract(abi,contractAddress);
        await SmartContract.methods.getTokenOneDay().send({from:account.toString()},function (err,res) {
            const txHash = document.getElementById("oneDay-contract");
            txHash.style.display = 'block';
            const recipe = document.getElementById("oneDay-contract-text");

            if (err) {
                recipe.textContent = "다시 시도해 주세요.";
                recipe.style.color = "firebrick";
            }else{
                txHash.href = 'https://goerli.etherscan.io/tx/' + res;
                recipe.textContent = '트랜잭션 확인하기';
            }
        });
    });
}
/* 가입 보상받기 - List Page */
if(document.getElementById("getWelcomeTokenButton")){
    document.getElementById("getWelcomeTokenButton").addEventListener('click',async ()=>{
        let loadingIcon = document.getElementById('banner3-loadingIcon');
        const account = await web3.eth.requestAccounts();
        const SmartContract = new web3.eth.Contract(abi,contractAddress);
        await SmartContract.methods.getTokenOnce().send({from:account.toString()},function (err,res) {
            const txHash = document.getElementById("welcome-contract");
            txHash.style.display = "block";
            const recipe = document.getElementById('welcome-contract-text');
            if (err) {
                recipe.textContent = "다시 시도해 주세요.";
                recipe.style.color = "firebrick";
            }else{
                txHash.href = 'https://goerli.etherscan.io/tx/' + res;
                recipe.textContent = '트랜잭션 확인하기';
            }
        });
    });
}

/* 땅사기 - earth Page */
if(document.getElementById("buyLandButton")) {
    document.getElementById("buyLandButton").addEventListener('click',async ()=>{
        let loadingIcon = document.getElementById('buyDot-loadingIcon');
        const account = await web3.eth.requestAccounts();
        const SmartContract = new web3.eth.Contract(abi,contractAddress);
        let balance;
        await SmartContract.methods.balanceOf(account.toString()).call().then((e) =>  balance = web3.utils.fromWei(e));
        const level = document.getElementById('level').value;
        const contract = document.getElementById("dotmap-contract");
        const recipe = document.getElementById('dotmap-contract-text');
        let BuyDot;

        console.log(balance);
        console.log(typeof balance);
        const balanceErrMsg = () => {
            contract.style.display = "block";
            recipe.textContent = "토큰이 부족합니다.";
            recipe.style.color = "red";
        }
        if(level === '1' ) {
            if(balance < 500) {
                balanceErrMsg();
                return false;
            }
            BuyDot = await SmartContract.methods.Buy_Level1_Dot();
        }
        if(level === '2') {
            if(balance < 1000) {
                balanceErrMsg();
                return false;
            }
            BuyDot = await SmartContract.methods.Buy_Level2_Dot();
        }
        if(level === '3') {
            if(balance < 1500) {
                balanceErrMsg();
                return false;
            }
            BuyDot = await SmartContract.methods.Buy_Level3_Dot();
        }
        BuyDot.send({from:account.toString(),gasprice: 20000000000}, async function (err,res) {
            loadingIcon.style.display = "block";
            if (err) {
                console.log(err);
                loadingIcon.style.display = "none";
                contract.style.display = "block";
                recipe.textContent = '트랜잭션이 취소됐습니다.';
                recipe.style.color = "firebrick";
            }else {
                contract.style.display = "block";
                recipe.style.display = "block";
                recipe.textContent = '트랜잭션 성공후 땅이 구매됩니다.';
                recipe.style.color = "gray";
                console.log(res);
                const interval = setInterval(()=>{
                    console.log("트랜잭션 영수증을 기다리고있습니다...");
                    web3.eth.getTransactionReceipt(res, function(err, rec){
                        if (rec) {
                            if(rec.status === false) {
                                loadingIcon.style.display = "none";
                                contract.style.display = "block";
                                recipe.textContent = "구매에 실패했습니다.";
                                clearInterval(interval);
                                return false;
                            }
                            clearInterval(interval);
                            /* 성공시 도트 구매 AJAX */
                            let params = {
                                dotId       : $("#dotId").val(),
                                description : $("#description").val(),
                                color       : $("#dot-color").val(),
                                txHash      : rec.transactionHash.toString(),
                            }
                            $.ajax({
                                type : "POST",
                                url : "/earth/" + $("#userId").val(),
                                data : JSON.stringify(params),
                                contentType : 'application/json',
                                dataType : 'json',
                                success : function(res){
                                    location.reload();
                                },
                                error : function(err){
                                    alert('땅 구매에 실패했습니다. 다시 시도해주세요.');
                                }
                            });
                        } else {
                            console.log(err);
                            console.log(res);
                        }
                    });
                }, 1000);
            }
        });
    });
}
