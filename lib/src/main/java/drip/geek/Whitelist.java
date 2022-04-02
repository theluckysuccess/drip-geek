package drip.geek;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
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
public class Whitelist extends Contract {
    public static final String BINARY = "608060405260008054600160a060020a03191633179055610607806100256000396000f3006080604052600436106100825763ffffffff7c010000000000000000000000000000000000000000000000000000000060003504166324953eaa8114610087578063286dd3f5146100f05780637b9417c8146101115780638da5cb5b146101325780639b19251a14610163578063e2ec6ec314610184578063f2fde38b146101d9575b600080fd5b34801561009357600080fd5b50604080516020600480358082013583810280860185019096528085526100dc953695939460249493850192918291850190849080828437509497506101fc9650505050505050565b604080519115158252519081900360200190f35b3480156100fc57600080fd5b506100dc600160a060020a0360043516610294565b34801561011d57600080fd5b506100dc600160a060020a0360043516610364565b34801561013e57600080fd5b50610147610438565b60408051600160a060020a039092168252519081900360200190f35b34801561016f57600080fd5b506100dc600160a060020a0360043516610447565b34801561019057600080fd5b50604080516020600480358082013583810280860185019096528085526100dc9536959394602494938501929182918501908490808284375094975061045c9650505050505050565b3480156101e557600080fd5b506101fa600160a060020a03600435166104ee565b005b600080548190600160a060020a0316331461024f576040805160e560020a62461bcd02815260206004820152600a60248201526000805160206105bc833981519152604482015290519081900360640190fd5b5060005b825181101561028e5761027c838281518110151561026d57fe5b90602001906020020151610294565b1561028657600191505b600101610253565b50919050565b60008054600160a060020a031633146102e5576040805160e560020a62461bcd02815260206004820152600a60248201526000805160206105bc833981519152604482015290519081900360640190fd5b600160a060020a03821660009081526001602052604090205460ff161561035f57600160a060020a038216600081815260016020908152604091829020805460ff19169055815192835290517ff1abf01a1043b7c244d128e8595cf0c1d10743b022b03a02dffd8ca3bf729f5a9281900390910190a15060015b919050565b60008054600160a060020a031633146103b5576040805160e560020a62461bcd02815260206004820152600a60248201526000805160206105bc833981519152604482015290519081900360640190fd5b600160a060020a03821660009081526001602052604090205460ff16151561035f57600160a060020a038216600081815260016020818152604092839020805460ff1916909217909155815192835290517fd1bba68c128cc3f427e5831b3c6f99f480b6efa6b9e80c757768f6124158cc3f9281900390910190a1506001919050565b600054600160a060020a031681565b60016020526000908152604090205460ff1681565b600080548190600160a060020a031633146104af576040805160e560020a62461bcd02815260206004820152600a60248201526000805160206105bc833981519152604482015290519081900360640190fd5b5060005b825181101561028e576104dc83828151811015156104cd57fe5b90602001906020020151610364565b156104e657600191505b6001016104b3565b600054600160a060020a0316331461053e576040805160e560020a62461bcd02815260206004820152600a60248201526000805160206105bc833981519152604482015290519081900360640190fd5b600160a060020a038116151561055357600080fd5b60008054604051600160a060020a03808516939216917f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e091a36000805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a039290921691909117905556006f6e6c79206f776e657200000000000000000000000000000000000000000000a165627a7a72305820c881b919550a68022bc151fe76c087e522e48fff3ee2f3a3f8b69ee63ccac1490029";

    public static final String FUNC_REMOVEADDRESSESFROMWHITELIST = "removeAddressesFromWhitelist";

    public static final String FUNC_REMOVEADDRESSFROMWHITELIST = "removeAddressFromWhitelist";

    public static final String FUNC_ADDADDRESSTOWHITELIST = "addAddressToWhitelist";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_WHITELIST = "whitelist";

    public static final String FUNC_ADDADDRESSESTOWHITELIST = "addAddressesToWhitelist";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final Event WHITELISTEDADDRESSADDED_EVENT = new Event("WhitelistedAddressAdded", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
    ;

    public static final Event WHITELISTEDADDRESSREMOVED_EVENT = new Event("WhitelistedAddressRemoved", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
    ;

    public static final Event OWNERSHIPTRANSFERRED_EVENT = new Event("OwnershipTransferred", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    @Deprecated
    protected Whitelist(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Whitelist(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Whitelist(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Whitelist(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<TransactionReceipt> removeAddressesFromWhitelist(DynamicArray<Address> addrs) {
        final Function function = new Function(
                FUNC_REMOVEADDRESSESFROMWHITELIST, 
                Arrays.<Type>asList(addrs), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> removeAddressFromWhitelist(Address addr) {
        final Function function = new Function(
                FUNC_REMOVEADDRESSFROMWHITELIST, 
                Arrays.<Type>asList(addr), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> addAddressToWhitelist(Address addr) {
        final Function function = new Function(
                FUNC_ADDADDRESSTOWHITELIST, 
                Arrays.<Type>asList(addr), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Address> owner() {
        final Function function = new Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteFunctionCall<Bool> whitelist(Address param0) {
        final Function function = new Function(FUNC_WHITELIST, 
                Arrays.<Type>asList(param0), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteFunctionCall<TransactionReceipt> addAddressesToWhitelist(DynamicArray<Address> addrs) {
        final Function function = new Function(
                FUNC_ADDADDRESSESTOWHITELIST, 
                Arrays.<Type>asList(addrs), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> transferOwnership(Address newOwner) {
        final Function function = new Function(
                FUNC_TRANSFEROWNERSHIP, 
                Arrays.<Type>asList(newOwner), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public List<WhitelistedAddressAddedEventResponse> getWhitelistedAddressAddedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(WHITELISTEDADDRESSADDED_EVENT, transactionReceipt);
        ArrayList<WhitelistedAddressAddedEventResponse> responses = new ArrayList<WhitelistedAddressAddedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            WhitelistedAddressAddedEventResponse typedResponse = new WhitelistedAddressAddedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.addr = (Address) eventValues.getNonIndexedValues().get(0);
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<WhitelistedAddressAddedEventResponse> whitelistedAddressAddedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, WhitelistedAddressAddedEventResponse>() {
            @Override
            public WhitelistedAddressAddedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(WHITELISTEDADDRESSADDED_EVENT, log);
                WhitelistedAddressAddedEventResponse typedResponse = new WhitelistedAddressAddedEventResponse();
                typedResponse.log = log;
                typedResponse.addr = (Address) eventValues.getNonIndexedValues().get(0);
                return typedResponse;
            }
        });
    }

    public Flowable<WhitelistedAddressAddedEventResponse> whitelistedAddressAddedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(WHITELISTEDADDRESSADDED_EVENT));
        return whitelistedAddressAddedEventFlowable(filter);
    }

    public List<WhitelistedAddressRemovedEventResponse> getWhitelistedAddressRemovedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(WHITELISTEDADDRESSREMOVED_EVENT, transactionReceipt);
        ArrayList<WhitelistedAddressRemovedEventResponse> responses = new ArrayList<WhitelistedAddressRemovedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            WhitelistedAddressRemovedEventResponse typedResponse = new WhitelistedAddressRemovedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.addr = (Address) eventValues.getNonIndexedValues().get(0);
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<WhitelistedAddressRemovedEventResponse> whitelistedAddressRemovedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, WhitelistedAddressRemovedEventResponse>() {
            @Override
            public WhitelistedAddressRemovedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(WHITELISTEDADDRESSREMOVED_EVENT, log);
                WhitelistedAddressRemovedEventResponse typedResponse = new WhitelistedAddressRemovedEventResponse();
                typedResponse.log = log;
                typedResponse.addr = (Address) eventValues.getNonIndexedValues().get(0);
                return typedResponse;
            }
        });
    }

    public Flowable<WhitelistedAddressRemovedEventResponse> whitelistedAddressRemovedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(WHITELISTEDADDRESSREMOVED_EVENT));
        return whitelistedAddressRemovedEventFlowable(filter);
    }

    public List<OwnershipTransferredEventResponse> getOwnershipTransferredEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, transactionReceipt);
        ArrayList<OwnershipTransferredEventResponse> responses = new ArrayList<OwnershipTransferredEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.previousOwner = (Address) eventValues.getIndexedValues().get(0);
            typedResponse.newOwner = (Address) eventValues.getIndexedValues().get(1);
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<OwnershipTransferredEventResponse> ownershipTransferredEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, OwnershipTransferredEventResponse>() {
            @Override
            public OwnershipTransferredEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, log);
                OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
                typedResponse.log = log;
                typedResponse.previousOwner = (Address) eventValues.getIndexedValues().get(0);
                typedResponse.newOwner = (Address) eventValues.getIndexedValues().get(1);
                return typedResponse;
            }
        });
    }

    public Flowable<OwnershipTransferredEventResponse> ownershipTransferredEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(OWNERSHIPTRANSFERRED_EVENT));
        return ownershipTransferredEventFlowable(filter);
    }

    @Deprecated
    public static Whitelist load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Whitelist(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Whitelist load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Whitelist(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Whitelist load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Whitelist(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Whitelist load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Whitelist(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Whitelist> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Whitelist.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Whitelist> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Whitelist.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<Whitelist> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Whitelist.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Whitelist> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Whitelist.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class WhitelistedAddressAddedEventResponse extends BaseEventResponse {
        public Address addr;
    }

    public static class WhitelistedAddressRemovedEventResponse extends BaseEventResponse {
        public Address addr;
    }

    public static class OwnershipTransferredEventResponse extends BaseEventResponse {
        public Address previousOwner;

        public Address newOwner;
    }
}
