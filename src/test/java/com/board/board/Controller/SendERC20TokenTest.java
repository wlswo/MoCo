package com.board.board.Controller;


import com.mytoken.contract.BambooToken;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.FastRawTransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.tx.response.PollingTransactionReceiptProcessor;

import java.math.BigInteger;

@RunWith(SpringRunner.class)
@SpringBootTest
@DisplayName("토큰잔액 테스트")
public class SendERC20TokenTest {
    @Test
    public void balanceCheck() throws Exception {

        /* 스마트컨트랙트 주소 */
        String ERC20_CONTRACT_ADDRESS = "0x1F06dd241b5527ff74ffE4CFF97ba66e424E718E";
        /* 배포자 주소  */
        String USER_ADDRESS = "0x0924Dfa9cA977e6A956f0399F959c495E54152cb";
        /* 배포자 개인키 */
        String USER_PRIVATE_KEY = "";
        long TX_END_CHECK_DURATION = 3000;
        int TX_END_CHECK_RETRY = 3;

        // 로컬 환경에서 프라이빗 네트워크를 띄운 상태에서 진행한다.
        Web3j web3j = Web3j.build(new HttpService("https://goerli.infura.io/v3/3e30d114055b48d08a0a975647b612f2"));
        Credentials credential = Credentials.create(USER_PRIVATE_KEY);
        ContractGasProvider gasProvider = new DefaultGasProvider();
        FastRawTransactionManager manager = new FastRawTransactionManager(
                web3j,
                credential,
                new PollingTransactionReceiptProcessor(web3j, TX_END_CHECK_DURATION, TX_END_CHECK_RETRY)
        );

        // 위에서 생성한 Wrapper의 이름이 MyERC20이라고 가정한다.
        BambooToken bambooToken = BambooToken.load(ERC20_CONTRACT_ADDRESS, web3j, manager, gasProvider);
        BigInteger balance = bambooToken.balanceOf(USER_ADDRESS).send();

        System.out.println(balance);

    }
}
