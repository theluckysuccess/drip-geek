package drip.geek;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.Event;
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
public class TransparentUpgradeableProxy extends Contract {
    public static final String BINARY = "6080604052604051620010fd380380620010fd833981016040819052620000269162000614565b82816200005560017f360894a13ba1a3210667c828492db98dca3e2076cc3735a920a3ca505d382bbd620006f4565b600080516020620010b68339815191521462000075576200007562000733565b6200008c82826000640100000000620000f9810204565b50620000bc905060017fb53127684a568b3173ae13b9f8a6016e243e63b6e8ee1178d6a717850b5d6104620006f4565b6000805160206200109683398151915214620000dc57620000dc62000733565b620000f08264010000000062000176810204565b505050620007b5565b6200010d83640100000000620001e3810204565b604051600160a060020a038416907fbc7cd75a20ee27fd9adebab32041f755214dbc6bffa90cc0225b39da2e5c2d3b90600090a26000825111806200014f5750805b1562000171576200016f83836401000000006200027c620002d882021704565b505b505050565b7f7e644d79422f17c01e4894b5f4f588d331ebfa28653d42ae832dc59e38c9798f620001aa64010000000062000310810204565b60408051600160a060020a03928316815291841660208301520160405180910390a1620001e08164010000000062000349810204565b50565b620001fc81640100000000620002a86200040a82021704565b6200028e576040517f08c379a000000000000000000000000000000000000000000000000000000000815260206004820152602d60248201527f455243313936373a206e657720696d706c656d656e746174696f6e206973206e60448201527f6f74206120636f6e74726163740000000000000000000000000000000000000060648201526084015b60405180910390fd5b80620002b7600080516020620010b6833981519152640100000000620002246200041082021704565b8054600160a060020a031916600160a060020a039290921691909117905550565b6060620003098383604051806060016040528060278152602001620010d66027913964010000000062000413810204565b9392505050565b60006200033a60008051602062001096833981519152640100000000620002246200041082021704565b54600160a060020a0316919050565b600160a060020a038116620003e1576040517f08c379a000000000000000000000000000000000000000000000000000000000815260206004820152602660248201527f455243313936373a206e65772061646d696e20697320746865207a65726f206160448201527f6464726573730000000000000000000000000000000000000000000000000000606482015260840162000285565b80620002b760008051602062001096833981519152640100000000620002246200041082021704565b3b151590565b90565b606062000429846401000000006200040a810204565b620004b7576040517f08c379a000000000000000000000000000000000000000000000000000000000815260206004820152602660248201527f416464726573733a2064656c65676174652063616c6c20746f206e6f6e2d636f60448201527f6e74726163740000000000000000000000000000000000000000000000000000606482015260840162000285565b60008085600160a060020a031685604051620004d4919062000762565b600060405180830381855af49150503d806000811462000511576040519150601f19603f3d011682016040523d82523d6000602084013e62000516565b606091505b50915091506200053782828662000541640100000000026401000000009004565b9695505050505050565b606083156200055257508162000309565b825115620005635782518084602001fd5b816040517f08c379a000000000000000000000000000000000000000000000000000000000815260040162000285919062000780565b8051600160a060020a0381168114620005b157600080fd5b919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b60005b8381101562000602578181015183820152602001620005e8565b838111156200016f5750506000910152565b6000806000606084860312156200062a57600080fd5b620006358462000599565b9250620006456020850162000599565b9150604084015167ffffffffffffffff808211156200066357600080fd5b818601915086601f8301126200067857600080fd5b8151818111156200068d576200068d620005b6565b604051601f8201601f19908116603f01168101908382118183101715620006b857620006b8620005b6565b81604052828152896020848701011115620006d257600080fd5b620006e5836020830160208801620005e5565b80955050505050509250925092565b6000828210156200072e577f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b500390565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052600160045260246000fd5b6000825162000776818460208701620005e5565b9190910192915050565b6020815260008251806020840152620007a1816040850160208701620005e5565b601f01601f19169190910160400192915050565b6108d180620007c56000396000f3fe60806040526004361061006a577c010000000000000000000000000000000000000000000000000000000060003504633659cfe681146100815780634f1ef286146100a15780635c60da1b146100b45780638f283970146100e5578063f851a4401461010557610079565b366100795761007761011a565b005b61007761011a565b34801561008d57600080fd5b5061007761009c36600461075b565b610134565b6100776100af366004610776565b61017b565b3480156100c057600080fd5b506100c96101ec565b604051600160a060020a03909116815260200160405180910390f35b3480156100f157600080fd5b5061007761010036600461075b565b610227565b34801561011157600080fd5b506100c9610251565b6101226102ae565b61013261012d61036b565b610375565b565b61013c610399565b600160a060020a031633600160a060020a0316141561017357610170816040518060200160405280600081525060006103cc565b50565b61017061011a565b610183610399565b600160a060020a031633600160a060020a031614156101e4576101df8383838080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250600192506103cc915050565b505050565b6101df61011a565b60006101f6610399565b600160a060020a031633600160a060020a0316141561021c5761021761036b565b905090565b61022461011a565b90565b61022f610399565b600160a060020a031633600160a060020a03161415610173576101708161042b565b600061025b610399565b600160a060020a031633600160a060020a0316141561021c57610217610399565b60606102a183836040518060600160405280602781526020016108756027913961047f565b9392505050565b3b151590565b6102b6610399565b600160a060020a031633600160a060020a031614156101325760405160e560020a62461bcd02815260206004820152604260248201527f5472616e73706172656e745570677261646561626c6550726f78793a2061646d60448201527f696e2063616e6e6f742066616c6c6261636b20746f2070726f7879207461726760648201527f6574000000000000000000000000000000000000000000000000000000000000608482015260a4015b60405180910390fd5b600061021761056d565b3660008037600080366000845af43d6000803e808015610394573d6000f35b3d6000fd5b60007fb53127684a568b3173ae13b9f8a6016e243e63b6e8ee1178d6a717850b5d61035b54600160a060020a0316919050565b6103d583610595565b604051600160a060020a038416907fbc7cd75a20ee27fd9adebab32041f755214dbc6bffa90cc0225b39da2e5c2d3b90600090a26000825111806104165750805b156101df57610425838361027c565b50505050565b7f7e644d79422f17c01e4894b5f4f588d331ebfa28653d42ae832dc59e38c9798f610454610399565b60408051600160a060020a03928316815291841660208301520160405180910390a16101708161065d565b6060833b6104f85760405160e560020a62461bcd02815260206004820152602660248201527f416464726573733a2064656c65676174652063616c6c20746f206e6f6e2d636f60448201527f6e747261637400000000000000000000000000000000000000000000000000006064820152608401610362565b60008085600160a060020a0316856040516105139190610825565b600060405180830381855af49150503d806000811461054e576040519150601f19603f3d011682016040523d82523d6000602084013e610553565b606091505b5091509150610563828286610703565b9695505050505050565b60007f360894a13ba1a3210667c828492db98dca3e2076cc3735a920a3ca505d382bbc6103bd565b803b61060c5760405160e560020a62461bcd02815260206004820152602d60248201527f455243313936373a206e657720696d706c656d656e746174696f6e206973206e60448201527f6f74206120636f6e7472616374000000000000000000000000000000000000006064820152608401610362565b807f360894a13ba1a3210667c828492db98dca3e2076cc3735a920a3ca505d382bbc5b805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a039290921691909117905550565b600160a060020a0381166106dc5760405160e560020a62461bcd02815260206004820152602660248201527f455243313936373a206e65772061646d696e20697320746865207a65726f206160448201527f64647265737300000000000000000000000000000000000000000000000000006064820152608401610362565b807fb53127684a568b3173ae13b9f8a6016e243e63b6e8ee1178d6a717850b5d610361062f565b606083156107125750816102a1565b8251156107225782518084602001fd5b8160405160e560020a62461bcd0281526004016103629190610841565b8035600160a060020a038116811461075657600080fd5b919050565b60006020828403121561076d57600080fd5b6102a18261073f565b60008060006040848603121561078b57600080fd5b6107948461073f565b9250602084013567ffffffffffffffff808211156107b157600080fd5b818601915086601f8301126107c557600080fd5b8135818111156107d457600080fd5b8760208285010111156107e657600080fd5b6020830194508093505050509250925092565b60005b838110156108145781810151838201526020016107fc565b838111156104255750506000910152565b600082516108378184602087016107f9565b9190910192915050565b60208152600082518060208401526108608160408501602087016107f9565b601f01601f1916919091016040019291505056fe416464726573733a206c6f772d6c6576656c2064656c65676174652063616c6c206661696c6564a2646970667358221220293773cdc2936e39aac9410b400b1269468269c4207355f8e1b58fa2e2b5862364736f6c63430008090033b53127684a568b3173ae13b9f8a6016e243e63b6e8ee1178d6a717850b5d6103360894a13ba1a3210667c828492db98dca3e2076cc3735a920a3ca505d382bbc416464726573733a206c6f772d6c6576656c2064656c65676174652063616c6c206661696c6564";

    public static final String FUNC_ADMIN = "admin";

    public static final String FUNC_CHANGEADMIN = "changeAdmin";

    public static final String FUNC_IMPLEMENTATION = "implementation";

    public static final String FUNC_UPGRADETO = "upgradeTo";

    public static final String FUNC_UPGRADETOANDCALL = "upgradeToAndCall";

    public static final Event ADMINCHANGED_EVENT = new Event("AdminChanged", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}));
    ;

    public static final Event BEACONUPGRADED_EVENT = new Event("BeaconUpgraded", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}));
    ;

    public static final Event UPGRADED_EVENT = new Event("Upgraded", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}));
    ;

    @Deprecated
    protected TransparentUpgradeableProxy(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected TransparentUpgradeableProxy(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected TransparentUpgradeableProxy(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected TransparentUpgradeableProxy(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<AdminChangedEventResponse> getAdminChangedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(ADMINCHANGED_EVENT, transactionReceipt);
        ArrayList<AdminChangedEventResponse> responses = new ArrayList<AdminChangedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            AdminChangedEventResponse typedResponse = new AdminChangedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.previousAdmin = (Address) eventValues.getNonIndexedValues().get(0);
            typedResponse.newAdmin = (Address) eventValues.getNonIndexedValues().get(1);
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<AdminChangedEventResponse> adminChangedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, AdminChangedEventResponse>() {
            @Override
            public AdminChangedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(ADMINCHANGED_EVENT, log);
                AdminChangedEventResponse typedResponse = new AdminChangedEventResponse();
                typedResponse.log = log;
                typedResponse.previousAdmin = (Address) eventValues.getNonIndexedValues().get(0);
                typedResponse.newAdmin = (Address) eventValues.getNonIndexedValues().get(1);
                return typedResponse;
            }
        });
    }

    public Flowable<AdminChangedEventResponse> adminChangedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ADMINCHANGED_EVENT));
        return adminChangedEventFlowable(filter);
    }

    public List<BeaconUpgradedEventResponse> getBeaconUpgradedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(BEACONUPGRADED_EVENT, transactionReceipt);
        ArrayList<BeaconUpgradedEventResponse> responses = new ArrayList<BeaconUpgradedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            BeaconUpgradedEventResponse typedResponse = new BeaconUpgradedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.beacon = (Address) eventValues.getIndexedValues().get(0);
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<BeaconUpgradedEventResponse> beaconUpgradedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, BeaconUpgradedEventResponse>() {
            @Override
            public BeaconUpgradedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(BEACONUPGRADED_EVENT, log);
                BeaconUpgradedEventResponse typedResponse = new BeaconUpgradedEventResponse();
                typedResponse.log = log;
                typedResponse.beacon = (Address) eventValues.getIndexedValues().get(0);
                return typedResponse;
            }
        });
    }

    public Flowable<BeaconUpgradedEventResponse> beaconUpgradedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(BEACONUPGRADED_EVENT));
        return beaconUpgradedEventFlowable(filter);
    }

    public List<UpgradedEventResponse> getUpgradedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(UPGRADED_EVENT, transactionReceipt);
        ArrayList<UpgradedEventResponse> responses = new ArrayList<UpgradedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            UpgradedEventResponse typedResponse = new UpgradedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.implementation = (Address) eventValues.getIndexedValues().get(0);
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<UpgradedEventResponse> upgradedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, UpgradedEventResponse>() {
            @Override
            public UpgradedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(UPGRADED_EVENT, log);
                UpgradedEventResponse typedResponse = new UpgradedEventResponse();
                typedResponse.log = log;
                typedResponse.implementation = (Address) eventValues.getIndexedValues().get(0);
                return typedResponse;
            }
        });
    }

    public Flowable<UpgradedEventResponse> upgradedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(UPGRADED_EVENT));
        return upgradedEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> admin() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ADMIN, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> changeAdmin(Address newAdmin) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CHANGEADMIN, 
                Arrays.<Type>asList(newAdmin), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> implementation() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_IMPLEMENTATION, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> upgradeTo(Address newImplementation) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_UPGRADETO, 
                Arrays.<Type>asList(newImplementation), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> upgradeToAndCall(Address newImplementation, DynamicBytes data) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_UPGRADETOANDCALL, 
                Arrays.<Type>asList(newImplementation, data), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static TransparentUpgradeableProxy load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new TransparentUpgradeableProxy(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static TransparentUpgradeableProxy load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new TransparentUpgradeableProxy(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static TransparentUpgradeableProxy load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new TransparentUpgradeableProxy(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static TransparentUpgradeableProxy load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new TransparentUpgradeableProxy(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<TransparentUpgradeableProxy> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, Address _logic, Address admin_, DynamicBytes _data) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(_logic, admin_, _data));
        return deployRemoteCall(TransparentUpgradeableProxy.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<TransparentUpgradeableProxy> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, Address _logic, Address admin_, DynamicBytes _data) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(_logic, admin_, _data));
        return deployRemoteCall(TransparentUpgradeableProxy.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<TransparentUpgradeableProxy> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, Address _logic, Address admin_, DynamicBytes _data) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(_logic, admin_, _data));
        return deployRemoteCall(TransparentUpgradeableProxy.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<TransparentUpgradeableProxy> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, Address _logic, Address admin_, DynamicBytes _data) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(_logic, admin_, _data));
        return deployRemoteCall(TransparentUpgradeableProxy.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static class AdminChangedEventResponse extends BaseEventResponse {
        public Address previousAdmin;

        public Address newAdmin;
    }

    public static class BeaconUpgradedEventResponse extends BaseEventResponse {
        public Address beacon;
    }

    public static class UpgradedEventResponse extends BaseEventResponse {
        public Address implementation;
    }
}
