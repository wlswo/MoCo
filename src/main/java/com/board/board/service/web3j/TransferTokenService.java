package com.board.board.service.web3j;

import com.mytoken.contract.BambooToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.FastRawTransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.tx.response.PollingTransactionReceiptProcessor;

import java.math.BigInteger;

@Service
public class TransferTokenService {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Value("${metamask.key}")
    String USER_PRIVATE_KEY;

    @Value("${metamask.api-key}")
    String APIkey;

    public void transfer(String receive_address) {
        /* 스마트 컨트랙트 주소 */
        String ERC20_CONTRACT_ADDRESS = "0x1F06dd241b5527ff74ffE4CFF97ba66e424E718E";

        /* ERC20을 보내는 사용자 */
        String USER_ADDRESS = "0x0924Dfa9cA977e6A956f0399F959c495E54152cb";

        /* ERC20을 받는 사용자 */
        String RECEIVER_ADDRESS = receive_address;

        // 트랜잭션 처리에 대한 변수값 -> 기본적으로 3000, 3을 할당한다.
        // 네트워크 상황에 따라 변경가능하다.
        long TX_END_CHECK_DURATION = 3000;
        int TX_END_CHECK_RETRY = 3;

        // 이전에 만든 프라이빗 네트워크에서 사용한 CHAIN ID가 필요하다.
        // 트랜잭션을 블록에 작성하는 경우에는 필수적이나, 조회하는 경우에는 써도 되고 안써도 된다.
        long CHAIN_ID = 5;

        /* 보낼 토큰 개수 */
        String amount = "100000000000000000"; //0.1 BmB

        /* goerli 테스트넷 에서 진행 */
        Web3j web3j = Web3j.build(new HttpService(APIkey));
        Credentials credential = Credentials.create(USER_PRIVATE_KEY);
        ContractGasProvider gasProvider = new DefaultGasProvider();
        FastRawTransactionManager manager = new FastRawTransactionManager(
                web3j,
                credential,
                CHAIN_ID,
                new PollingTransactionReceiptProcessor(web3j, TX_END_CHECK_DURATION, TX_END_CHECK_RETRY)
        );

        BambooToken erc20 = BambooToken.load(ERC20_CONTRACT_ADDRESS, web3j, manager, gasProvider);
        try{
            erc20.transfer(RECEIVER_ADDRESS, new BigInteger(amount)).send();
        }catch (Exception e) {
            log.info(e.getMessage());
        }
    }
}
