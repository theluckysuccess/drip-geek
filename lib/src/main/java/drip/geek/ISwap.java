package drip.geek;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.9.0.
 */
@SuppressWarnings("rawtypes")
public class ISwap extends Contract {
    public static final String BINARY = "";

    public static final String FUNC_ADDLIQUIDITY = "addLiquidity";

    public static final String FUNC_GETINPUTPRICE = "getInputPrice";

    public static final String FUNC_GETLIQUIDITYTORESERVEINPUTPRICE = "getLiquidityToReserveInputPrice";

    public static final String FUNC_GETOUTPUTPRICE = "getOutputPrice";

    public static final String FUNC_GETTOKENTOTRXINPUTPRICE = "getTokenToTrxInputPrice";

    public static final String FUNC_GETTOKENTOTRXOUTPUTPRICE = "getTokenToTrxOutputPrice";

    public static final String FUNC_GETTRXTOLIQUIDITYINPUTPRICE = "getTrxToLiquidityInputPrice";

    public static final String FUNC_GETTRXTOTOKENINPUTPRICE = "getTrxToTokenInputPrice";

    public static final String FUNC_GETTRXTOTOKENOUTPUTPRICE = "getTrxToTokenOutputPrice";

    public static final String FUNC_REMOVELIQUIDITY = "removeLiquidity";

    public static final String FUNC_TOKENADDRESS = "tokenAddress";

    public static final String FUNC_TOKENBALANCE = "tokenBalance";

    public static final String FUNC_TOKENTOTRXSWAPINPUT = "tokenToTrxSwapInput";

    public static final String FUNC_TOKENTOTRXSWAPOUTPUT = "tokenToTrxSwapOutput";

    public static final String FUNC_TRONBALANCE = "tronBalance";

    public static final String FUNC_TRXTOTOKENSWAPINPUT = "trxToTokenSwapInput";

    public static final String FUNC_TRXTOTOKENSWAPOUTPUT = "trxToTokenSwapOutput";

    public static final String FUNC_TXS = "txs";

    @Deprecated
    protected ISwap(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected ISwap(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected ISwap(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected ISwap(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<TransactionReceipt> addLiquidity(Uint256 min_liquidity, Uint256 max_tokens) {
        final Function function = new Function(
                FUNC_ADDLIQUIDITY, 
                Arrays.<Type>asList(min_liquidity, max_tokens), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Uint256> getInputPrice(Uint256 input_amount, Uint256 input_reserve, Uint256 output_reserve) {
        final Function function = new Function(FUNC_GETINPUTPRICE, 
                Arrays.<Type>asList(input_amount, input_reserve, output_reserve), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteFunctionCall<Tuple2<Uint256, Uint256>> getLiquidityToReserveInputPrice(Uint256 amount) {
        final Function function = new Function(FUNC_GETLIQUIDITYTORESERVEINPUTPRICE, 
                Arrays.<Type>asList(amount), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple2<Uint256, Uint256>>(function,
                new Callable<Tuple2<Uint256, Uint256>>() {
                    @Override
                    public Tuple2<Uint256, Uint256> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple2<Uint256, Uint256>(
                                (Uint256) results.get(0), 
                                (Uint256) results.get(1));
                    }
                });
    }

    public RemoteFunctionCall<Uint256> getOutputPrice(Uint256 output_amount, Uint256 input_reserve, Uint256 output_reserve) {
        final Function function = new Function(FUNC_GETOUTPUTPRICE, 
                Arrays.<Type>asList(output_amount, input_reserve, output_reserve), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteFunctionCall<Uint256> getTokenToTrxInputPrice(Uint256 tokens_sold) {
        final Function function = new Function(FUNC_GETTOKENTOTRXINPUTPRICE, 
                Arrays.<Type>asList(tokens_sold), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteFunctionCall<Uint256> getTokenToTrxOutputPrice(Uint256 trx_bought) {
        final Function function = new Function(FUNC_GETTOKENTOTRXOUTPUTPRICE, 
                Arrays.<Type>asList(trx_bought), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteFunctionCall<Uint256> getTrxToLiquidityInputPrice(Uint256 trx_sold) {
        final Function function = new Function(FUNC_GETTRXTOLIQUIDITYINPUTPRICE, 
                Arrays.<Type>asList(trx_sold), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteFunctionCall<Uint256> getTrxToTokenInputPrice(Uint256 trx_sold) {
        final Function function = new Function(FUNC_GETTRXTOTOKENINPUTPRICE, 
                Arrays.<Type>asList(trx_sold), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteFunctionCall<Uint256> getTrxToTokenOutputPrice(Uint256 tokens_bought) {
        final Function function = new Function(FUNC_GETTRXTOTOKENOUTPUTPRICE, 
                Arrays.<Type>asList(tokens_bought), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteFunctionCall<TransactionReceipt> removeLiquidity(Uint256 amount, Uint256 min_trx, Uint256 min_tokens) {
        final Function function = new Function(
                FUNC_REMOVELIQUIDITY, 
                Arrays.<Type>asList(amount, min_trx, min_tokens), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Address> tokenAddress() {
        final Function function = new Function(FUNC_TOKENADDRESS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteFunctionCall<Uint256> tokenBalance() {
        final Function function = new Function(FUNC_TOKENBALANCE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteFunctionCall<TransactionReceipt> tokenToTrxSwapInput(Uint256 tokens_sold, Uint256 min_trx) {
        final Function function = new Function(
                FUNC_TOKENTOTRXSWAPINPUT, 
                Arrays.<Type>asList(tokens_sold, min_trx), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> tokenToTrxSwapOutput(Uint256 trx_bought, Uint256 max_tokens) {
        final Function function = new Function(
                FUNC_TOKENTOTRXSWAPOUTPUT, 
                Arrays.<Type>asList(trx_bought, max_tokens), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Uint256> tronBalance() {
        final Function function = new Function(FUNC_TRONBALANCE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteFunctionCall<TransactionReceipt> trxToTokenSwapInput(Uint256 min_tokens) {
        final Function function = new Function(
                FUNC_TRXTOTOKENSWAPINPUT, 
                Arrays.<Type>asList(min_tokens), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> trxToTokenSwapOutput(Uint256 tokens_bought) {
        final Function function = new Function(
                FUNC_TRXTOTOKENSWAPOUTPUT, 
                Arrays.<Type>asList(tokens_bought), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Uint256> txs(Address owner) {
        final Function function = new Function(FUNC_TXS, 
                Arrays.<Type>asList(owner), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    @Deprecated
    public static ISwap load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new ISwap(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static ISwap load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new ISwap(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static ISwap load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new ISwap(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static ISwap load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new ISwap(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<ISwap> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(ISwap.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<ISwap> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(ISwap.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<ISwap> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(ISwap.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<ISwap> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(ISwap.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }
}
