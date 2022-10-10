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
document.getElementById("ConnectMetaMask").addEventListener('click', async () => {
    await ethereum.request({ method: 'eth_requestAccounts' });
    await window.ethereum.request({
        method: "wallet_switchEthereumChain",
        params: [{ chainId: '0x5', }],
    });
});

/* 메타마스크에 토큰추가 */
const tokenAddress = '0xeCDE103BDd3Ffb291b5f24330018A3E65413B076';
const tokenSymbol = 'MGK';
const tokenDecimals = 18;
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
        "inputs": [
            {
                "internalType": "uint256",
                "name": "amount",
                "type": "uint256"
            }
        ],
        "name": "buyLand",
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

/* 출석체크 보상받기 - List Page */
if(document.getElementById("getTokenButton")){
    document.getElementById("getTokenButton").addEventListener('click',async ()=>{
        let loadingIcon = document.getElementById('banner2-loadingIcon');
        const account = await web3.eth.requestAccounts();
        const SmartContract = new web3.eth.Contract(abi,"0xeCDE103BDd3Ffb291b5f24330018A3E65413B076");
        await SmartContract.methods.getTokenOneDay().send({from:account.toString()},function (res,err) {
            loadingIcon.style.display = "block";
            if (err) {
                console.log(err);
                loadingIcon.style.display = "none";
                document.getElementById("contract-err").style.display = "block";
                const error = document.getElementById("contract-error");
                error.textContent = "하루에 한번만 받을수 있습니다.";
                error.style.color = "firebrick";
                return
            }else{
                loadingIcon.style.display = "none";
                console.log("Hash of the transaction: " + res)
            }
        });
    });
}
/* 가입 보상받기 - List Page */
if(document.getElementById("getWelcomeTokenButton")){
    document.getElementById("getWelcomeTokenButton").addEventListener('click',async ()=>{
        let loadingIcon = document.getElementById('banner3-loadingIcon');
        const account = await web3.eth.requestAccounts();
        const SmartContract = new web3.eth.Contract(abi,"0xeCDE103BDd3Ffb291b5f24330018A3E65413B076");
        await SmartContract.methods.getTokenOnce().send({from:account.toString()},function (res,err) {
            loadingIcon.style.display = "block";
            if (err) {
                console.log(err);
                loadingIcon.style.display = "none";
                document.getElementById("welcome-contract-err").style.display = "block";
                const error = document.getElementById("welcome-contract-error");
                error.textContent = '가입 토큰은 한번만 받을수 있습니다.';
                error.style.color = "firebrick";
                return
            }else {
                loadingIcon.style.display = "none";
                console.log("Hash of the transaction: " + res)
            }
        });
    });
}
/* 땅사기 - earth Page */
if(document.getElementById("buyLandButton")) {
    document.getElementById("buyLandButton").addEventListener('click',async ()=>{
        let loadingIcon = document.getElementById('buyDot-loadingIcon');
        // calculate ERC20 token amount
        const amount = web3.utils.toWei("1000");
        const account = await web3.eth.requestAccounts();
        const SmartContract = new web3.eth.Contract(abi,"0xeCDE103BDd3Ffb291b5f24330018A3E65413B076");
        console.log(SmartContract.methods.balanceOf(account.toString()).call());
        await SmartContract.methods.buyLand(amount).send({from:account.toString(),gas: 800000},function (err,res) {
            loadingIcon.style.display = "block";
            if (err) {
                console.log(err);
                loadingIcon.style.display = "none";
                document.getElementById("dotmap-contract-err").style.display = "block";
                const error = document.getElementById("dotmap-contract-error");
                error.textContent = '토큰이 모자릅니다.';
                error.style.color = "firebrick";
                return
            }else {
                loadingIcon.style.display = "none";
                const success = document.getElementById("dotmap-contract-success");
                success.style.display = "block";
                success.textContent = '트랜잭션 요청성공';
                success.style.color = "green";
                let receipt =  web3.eth.getTransactionReceipt(res).then((err,res)=>{
                    if(err) {
                        console.log(err);
                    }else {
                        waitBlock(res);
                        console.log(res);
                    }
                });
                /* 성공시 도트 구매 AJAX */
            }
        });
    });
}



// await sleep trick
// http://stackoverflow.com/questions/951021/what-is-the-javascript-version-of-sleep
function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}

// We need to wait until any miner has included the transaction
// in a block to get the address of the contract
async function waitBlock(contract_TransactionHash) {
    while (true) {
        let receipt = web3.eth.getTransactionReceipt(contract_TransactionHash);
        if (receipt && receipt.contractAddress) {
            console.log("Your contract has been deployed at http://testnet.etherscan.io/address/" + receipt.contractAddress);
            console.log("Note that it might take 30 - 90 sceonds for the block to propagate befor it's visible in etherscan.io");
            break;
        }
        console.log("Waiting a mined block to include your contract... currently in block " + web3.eth.blockNumber);
        await sleep(4000);
    }
}
