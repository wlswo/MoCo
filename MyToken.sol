// SPDX-License-Identifier: MIT
pragma solidity ^0.8.7;
import "@openzeppelin/contracts/token/ERC20/ERC20.sol";

contract Mogakkoin is ERC20 {
    mapping (address => uint8) public isChecked;
    /* 토큰 10진수 */
    uint8 constant _decimals = 18;
    /* 토큰 개수  */
    uint256 constant _totalSupply = 100 * 10000 * 10**_decimals;  // 100m tokens for distribution
    address minter;
    /* 전송 이벤트 */
    event TransferToken(address indexed from, address indexed to, uint tokens);

    /* 
        foucet 이벤트 시간 기록 
        출석체크 보상용 
        마지막으로 받아간 시간 기록
    */
    mapping (address => uint256) public userTimeStamp;

    constructor() public ERC20("Mogakkoin", "MGK") {   
        _mint(address(this), _totalSupply);
    }


    /* 현재시간을 가져오는 함수 */
    function getTimeStamp() private view returns (uint256){
        return block.timestamp;
    }

    /* 하루 한번 토큰 받기 */
    function getTokenOneDay() public returns(bool){
        /* 하루가 지난경우만 */
        require(block.timestamp >= userTimeStamp[msg.sender] + 1 days,"Tokens can be received once per day.");

        userTimeStamp[msg.sender] = block.timestamp;

        _transfer(address(this), msg.sender, 300 * 10 **_decimals);

        emit TransferToken(address(this), msg.sender, 300 * 10 **_decimals);
        return true;
    }

    /* 계정당 한번 토큰 지급 */
    function getTokenOnce() public returns(bool){
        require(isChecked[msg.sender] == 0,"Tokens can be received only once.");
        isChecked[msg.sender]++;

         _transfer(address(this), msg.sender, 1500 * 10 **_decimals);

        emit TransferToken(address(this), msg.sender, 1500 * 10 **_decimals);
        return true;
    }


    /* 500토큰 구매 */
    function Buy_Level1_Dot() public returns(bool) {
        uint _amount = 500 * 10 **_decimals;

        require(balanceOf(msg.sender) >= _amount, "Not Enough Token");

        _transfer(msg.sender, address(this), _amount);

        emit TransferToken(msg.sender,address(this), _amount);
        return true;
    }

    /* 1000토큰 구매 */
    function Buy_Level2_Dot() public returns(bool) {
        uint _amount = 1000 * 10 **_decimals;

        require(balanceOf(msg.sender) >= _amount, "Not Enough Token");

        _transfer(msg.sender, address(this), _amount);

        emit TransferToken(msg.sender,address(this), _amount);
        return true;
    }

    /* 1500토큰 구매 */
    function Buy_Level3_Dot() public returns(bool) {
        uint _amount = 1500 * 10 **_decimals;

        require(balanceOf(msg.sender) >= _amount, "Not Enough Token");

        _transfer(msg.sender, address(this), _amount);

        emit TransferToken(msg.sender,address(this), _amount);
        return true;
    }
}

