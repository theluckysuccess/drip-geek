package drip.geek;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tuples.generated.Tuple3;
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
public class DripToken extends Contract {
    public static final String BINARY = "60806040526005805460ff191690553480156200001b57600080fd5b50604051602080620021f8833981016040525160038054600160a060020a0319163317908190556200005f90600160a060020a0316640100000000620000b4810204565b506003546200008b90600160a060020a0316670de0b6b3a76400008302640100000000620001b5810204565b50600354620000ac90600160a060020a0316640100000000620002d4810204565b505062000573565b600354600090600160a060020a031633146200013157604080517f08c379a000000000000000000000000000000000000000000000000000000000815260206004820152600a60248201527f6f6e6c79206f776e657200000000000000000000000000000000000000000000604482015290519081900360640190fd5b600160a060020a03821660009081526004602052604090205460ff161515620001b057600160a060020a038216600081815260046020908152604091829020805460ff19166001179055815192835290517fd1bba68c128cc3f427e5831b3c6f99f480b6efa6b9e80c757768f6124158cc3f9281900390910190a15060015b919050565b6000811580620001e3575060085460001990620001e1908464010000000062001941620003d082021704565b115b15620001f257506000620002ce565b6200020c83836401000000006200194e620003de82021704565b506008546200022a908364010000000062001941620003d082021704565b6008819055600019141562000270576005805460ff191660011790556040517fae5184fba832cb2b1f702aca6117b8d265eaf03ad33eb133f19dde0f5920fa0890600090a15b600160a060020a03831660009081526009602052604090205415156200029a576007805460010190555b50600160a060020a038216600090815260096020526040902080546001908101825590810180548301905560068054820190555b92915050565b600354600090600160a060020a031633146200035157604080517f08c379a000000000000000000000000000000000000000000000000000000000815260206004820152600a60248201527f6f6e6c79206f776e657200000000000000000000000000000000000000000000604482015290519081900360640190fd5b600160a060020a03821660009081526004602052604090205460ff1615620001b057600160a060020a038216600081815260046020908152604091829020805460ff19169055815192835290517ff1abf01a1043b7c244d128e8595cf0c1d10743b022b03a02dffd8ca3bf729f5a9281900390910190a1506001919050565b81810182811015620002ce57fe5b3360009081526004602052604081205460ff1615156200045f57604080517f08c379a000000000000000000000000000000000000000000000000000000000815260206004820152600f60248201527f6e6f742077686974656c69737465640000000000000000000000000000000000604482015290519081900360640190fd5b60055460ff16156200047057600080fd5b600160a060020a03831615156200048657600080fd5b600154620004a3908364010000000062001941620003d082021704565b600155600160a060020a038316600090815260208190526040902054620004d9908364010000000062001941620003d082021704565b600160a060020a03841660008181526020818152604091829020939093558051858152905191927f0f6798a560793a54c3bcfe86a93cde1e73087d944c0ea20544137d412139688592918290030190a2604080518381529051600160a060020a038516916000917fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef9181900360200190a350600192915050565b611c7580620005836000396000f3006080604052600436106101d75763ffffffff7c010000000000000000000000000000000000000000000000000000000060003504166305d2035b81146101dc57806306fdde0314610205578063095ea7b31461028f578063098d3228146102b357806315cfc405146102da57806318160ddd1461031957806323b872dd1461032e57806324953eaa14610358578063286dd3f5146103ad5780632a1eafd9146102b3578063313ce567146103ce578063349f0b90146103f9578063355274ea1461040e5780633cef28d21461042357806340c10f191461044457806341adae6014610468578063430bf08a14610491578063537a39bb146104c257806366188463146104d757806370a08231146104fb57806371dd97321461051c57806379a5b2271461053d5780637b9417c81461057a5780637d64bcb41461059b57806385535cc5146105b05780638da5cb5b146105d157806395d89b41146105e65780639b19251a146105fb578063a9059cbb1461061c578063c1bd8cf914610640578063cba0e99614610655578063d73dd62314610676578063dc0b35641461069a578063dd62ed3e146106af578063e2ec6ec3146106d6578063f2cc0c181461072b578063f2fde38b1461074c578063f84354f11461076d575b600080fd5b3480156101e857600080fd5b506101f161078e565b604080519115158252519081900360200190f35b34801561021157600080fd5b5061021a610797565b6040805160208082528351818301528351919283929083019185019080838360005b8381101561025457818101518382015260200161023c565b50505050905090810190601f1680156102815780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b34801561029b57600080fd5b506101f1600160a060020a03600435166024356107ce565b3480156102bf57600080fd5b506102c8610835565b60408051918252519081900360200190f35b3480156102e657600080fd5b506102fb600160a060020a036004351661083b565b60408051938452602084019290925282820152519081900360600190f35b34801561032557600080fd5b506102c8610876565b34801561033a57600080fd5b506101f1600160a060020a036004358116906024351660443561087c565b34801561036457600080fd5b50604080516020600480358082013583810280860185019096528085526101f1953695939460249493850192918291850190849080828437509497506109889650505050505050565b3480156103b957600080fd5b506101f1600160a060020a0360043516610a22565b3480156103da57600080fd5b506103e3610af4565b6040805160ff9092168252519081900360200190f35b34801561040557600080fd5b506102c8610af9565b34801561041a57600080fd5b506102c8610b17565b34801561042f57600080fd5b506102c8600160a060020a0360043516610b1d565b34801561045057600080fd5b506101f1600160a060020a0360043516602435610b3b565b34801561047457600080fd5b5061048f600160a060020a036004351660ff60243516610c30565b005b34801561049d57600080fd5b506104a6610d33565b60408051600160a060020a039092168252519081900360200190f35b3480156104ce57600080fd5b506102c8610d42565b3480156104e357600080fd5b506101f1600160a060020a0360043516602435610d48565b34801561050757600080fd5b506102c8600160a060020a0360043516610e38565b34801561052857600080fd5b5061048f600160a060020a0360043516610e53565b34801561054957600080fd5b50610561600160a060020a0360043516602435610ec4565b6040805192835260208301919091528051918290030190f35b34801561058657600080fd5b506101f1600160a060020a0360043516610f46565b3480156105a757600080fd5b506101f161101b565b3480156105bc57600080fd5b5061048f600160a060020a0360043516611084565b3480156105dd57600080fd5b506104a6611103565b3480156105f257600080fd5b5061021a611112565b34801561060757600080fd5b506101f1600160a060020a0360043516611149565b34801561062857600080fd5b506101f1600160a060020a036004351660243561115e565b34801561064c57600080fd5b506102c8611261565b34801561066157600080fd5b506101f1600160a060020a0360043516611267565b34801561068257600080fd5b506101f1600160a060020a0360043516602435611285565b3480156106a657600080fd5b506102c861131e565b3480156106bb57600080fd5b506102c8600160a060020a0360043581169060243516611324565b3480156106e257600080fd5b50604080516020600480358082013583810280860185019096528085526101f19536959394602494938501929182918501908490808284375094975061134f9650505050505050565b34801561073757600080fd5b5061048f600160a060020a03600435166113e3565b34801561075857600080fd5b5061048f600160a060020a0360043516611517565b34801561077957600080fd5b5061048f600160a060020a03600435166115e5565b60055460ff1681565b60408051808201909152600a81527f4452495020546f6b656e00000000000000000000000000000000000000000000602082015281565b336000818152600260209081526040808320600160a060020a038716808552908352818420869055815186815291519394909390927f8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b925928290030190a35060015b92915050565b60001981565b600080600061084984610e38565b600160a060020a039490941660009081526009602052604090208054600190910154949590949350915050565b60015490565b600080600061088b8685610ec4565b91509150600081111561090957600a546108b0908790600160a060020a0316836117b8565b15156108bb57600080fd5b600a5460408051600160a060020a03808a1682529092166020830152818101839052517ff65b9db0f011dccfffefbe3d143fd6d52e538c858a0442072d763a05418b49a69181900360600190a15b6109148686846117b8565b151561091f57600080fd5b600160a060020a0385166000908152600960205260409020541515610948576007805460010190555b50505050600160a060020a039081166000908152600960205260408082208054600190810190915593909216815220805482019055600680548201905590565b6003546000908190600160a060020a031633146109dd576040805160e560020a62461bcd02815260206004820152600a6024820152600080516020611c2a833981519152604482015290519081900360640190fd5b5060005b8251811015610a1c57610a0a83828151811015156109fb57fe5b90602001906020020151610a22565b15610a1457600191505b6001016109e1565b50919050565b600354600090600160a060020a03163314610a75576040805160e560020a62461bcd02815260206004820152600a6024820152600080516020611c2a833981519152604482015290519081900360640190fd5b600160a060020a03821660009081526004602052604090205460ff1615610aef57600160a060020a038216600081815260046020908152604091829020805460ff19169055815192835290517ff1abf01a1043b7c244d128e8595cf0c1d10743b022b03a02dffd8ca3bf729f5a9281900390910190a15060015b919050565b601281565b6000610b1260085460001961192f90919063ffffffff16565b905090565b60001990565b600160a060020a031660009081526009602052604090206001015490565b6000811580610b5e575060085460001990610b5c908463ffffffff61194116565b115b15610b6b5750600061082f565b610b75838361194e565b50600854610b89908363ffffffff61194116565b60088190556000191415610bce576005805460ff191660011790556040517fae5184fba832cb2b1f702aca6117b8d265eaf03ad33eb133f19dde0f5920fa0890600090a15b600160a060020a0383166000908152600960205260409020541515610bf7576007805460010190555b50600160a060020a0382166000908152600960205260409020805460019081018255908101805483019055600680548201905592915050565b600354600160a060020a03163314610c80576040805160e560020a62461bcd02815260206004820152600a6024820152600080516020611c2a833981519152604482015290519081900360640190fd5b60008160ff1610158015610c98575060648160ff1611155b1515610cee576040805160e560020a62461bcd02815260206004820152601260248201527f496e76616c69642074617820616d6f756e740000000000000000000000000000604482015290519081900360640190fd5b600160a060020a03919091166000908152600c602090815260408083208054600160ff1991821617909155600b909252909120805490911660ff909216919091179055565b600a54600160a060020a031681565b60065481565b336000908152600260209081526040808320600160a060020a038616845290915281205480831115610d9d57336000908152600260209081526040808320600160a060020a0388168452909152812055610dd2565b610dad818463ffffffff61192f16565b336000908152600260209081526040808320600160a060020a03891684529091529020555b336000818152600260209081526040808320600160a060020a0389168085529083529281902054815190815290519293927f8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b925929181900390910190a35060019392505050565b600160a060020a031660009081526020819052604090205490565b600354600160a060020a03163314610ea3576040805160e560020a62461bcd02815260206004820152600a6024820152600080516020611c2a833981519152604482015290519081900360640190fd5b600160a060020a03166000908152600c60205260409020805460ff19169055565b600160a060020a0382166000908152600d6020526040812054829190819060ff161515610f3e5750600160a060020a0384166000908152600c6020526040902054600a9060ff1615610f2e5750600160a060020a0384166000908152600b602052604090205460ff165b610f388482611ab5565b90935091505b509250929050565b600354600090600160a060020a03163314610f99576040805160e560020a62461bcd02815260206004820152600a6024820152600080516020611c2a833981519152604482015290519081900360640190fd5b600160a060020a03821660009081526004602052604090205460ff161515610aef57600160a060020a038216600081815260046020908152604091829020805460ff19166001179055815192835290517fd1bba68c128cc3f427e5831b3c6f99f480b6efa6b9e80c757768f6124158cc3f9281900390910190a1506001919050565b600354600090600160a060020a0316331461106e576040805160e560020a62461bcd02815260206004820152600a6024820152600080516020611c2a833981519152604482015290519081900360640190fd5b60055460ff161561107e57600080fd5b50600090565b600354600160a060020a031633146110d4576040805160e560020a62461bcd02815260206004820152600a6024820152600080516020611c2a833981519152604482015290519081900360640190fd5b600a805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a0392909216919091179055565b600354600160a060020a031681565b60408051808201909152600481527f4452495000000000000000000000000000000000000000000000000000000000602082015281565b60046020526000908152604090205460ff1681565b600080600061116d3385610ec4565b9150915060008111156111e757600a5461119090600160a060020a031682611b0a565b151561119b57600080fd5b600a5460408051338152600160a060020a039092166020830152818101839052517ff65b9db0f011dccfffefbe3d143fd6d52e538c858a0442072d763a05418b49a69181900360600190a15b6111f18583611b0a565b15156111fc57600080fd5b600160a060020a0385166000908152600960205260409020541515611225576007805460010190555b50505050600160a060020a0316600090815260096020526040808220805460019081019091553383529120805482019055600680548201905590565b60085490565b600160a060020a03166000908152600d602052604090205460ff1690565b336000908152600260209081526040808320600160a060020a03861684529091528120546112b9908363ffffffff61194116565b336000818152600260209081526040808320600160a060020a0389168085529083529281902085905580519485525191937f8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b925929081900390910190a350600192915050565b60075481565b600160a060020a03918216600090815260026020908152604080832093909416825291909152205490565b6003546000908190600160a060020a031633146113a4576040805160e560020a62461bcd02815260206004820152600a6024820152600080516020611c2a833981519152604482015290519081900360640190fd5b5060005b8251811015610a1c576113d183828151811015156113c257fe5b90602001906020020151610f46565b156113db57600191505b6001016113a8565b600354600160a060020a03163314611433576040805160e560020a62461bcd02815260206004820152600a6024820152600080516020611c2a833981519152604482015290519081900360640190fd5b600160a060020a0381166000908152600d602052604090205460ff16156114a4576040805160e560020a62461bcd02815260206004820152601b60248201527f4163636f756e7420697320616c7265616479206578636c756465640000000000604482015290519081900360640190fd5b600160a060020a03166000818152600d60205260408120805460ff19166001908117909155600e805491820181559091527fbb7b4a454dc3493923482f07822329ed19e8244eff582cc204f8554c3620c3fd01805473ffffffffffffffffffffffffffffffffffffffff19169091179055565b600354600160a060020a03163314611567576040805160e560020a62461bcd02815260206004820152600a6024820152600080516020611c2a833981519152604482015290519081900360640190fd5b600160a060020a038116151561157c57600080fd5b600354604051600160a060020a038084169216907f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e090600090a36003805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a0392909216919091179055565b600354600090600160a060020a03163314611638576040805160e560020a62461bcd02815260206004820152600a6024820152600080516020611c2a833981519152604482015290519081900360640190fd5b600160a060020a0382166000908152600d602052604090205460ff1615156116aa576040805160e560020a62461bcd02815260206004820152601b60248201527f4163636f756e7420697320616c7265616479206578636c756465640000000000604482015290519081900360640190fd5b5060005b600e548110156117b45781600160a060020a0316600e828154811015156116d157fe5b600091825260209091200154600160a060020a031614156117ac57600e805460001981019081106116fe57fe5b600091825260209091200154600e8054600160a060020a03909216918390811061172457fe5b6000918252602080832091909101805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a039485161790559184168152600d90915260409020805460ff19169055600e8054600019810190811061178157fe5b6000918252602090912001805473ffffffffffffffffffffffffffffffffffffffff191690556117b4565b6001016116ae565b5050565b6000600160a060020a03831615156117cf57600080fd5b600160a060020a0384166000908152602081905260409020548211156117f457600080fd5b600160a060020a038416600090815260026020908152604080832033845290915290205482111561182457600080fd5b600160a060020a03841660009081526020819052604090205461184d908363ffffffff61192f16565b600160a060020a038086166000908152602081905260408082209390935590851681522054611882908363ffffffff61194116565b600160a060020a038085166000908152602081815260408083209490945591871681526002825282812033825290915220546118c4908363ffffffff61192f16565b600160a060020a03808616600081815260026020908152604080832033845282529182902094909455805186815290519287169391927fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef929181900390910190a35060019392505050565b60008282111561193b57fe5b50900390565b8181018281101561082f57fe5b3360009081526004602052604081205460ff1615156119b7576040805160e560020a62461bcd02815260206004820152600f60248201527f6e6f742077686974656c69737465640000000000000000000000000000000000604482015290519081900360640190fd5b60055460ff16156119c757600080fd5b600160a060020a03831615156119dc57600080fd5b6001546119ef908363ffffffff61194116565b600155600160a060020a038316600090815260208190526040902054611a1b908363ffffffff61194116565b600160a060020a03841660008181526020818152604091829020939093558051858152905191927f0f6798a560793a54c3bcfe86a93cde1e73087d944c0ea20544137d412139688592918290030190a2604080518381529051600160a060020a038516916000917fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef9181900360200190a350600192915050565b600080611adc6064611ad08660ff871663ffffffff611beb16565b9063ffffffff611c1416565b9050611b016064611ad0611af460648760ff1661192f565b879063ffffffff611beb16565b91509250929050565b6000600160a060020a0383161515611b2157600080fd5b33600090815260208190526040902054821115611b3d57600080fd5b33600090815260208190526040902054611b5d908363ffffffff61192f16565b3360009081526020819052604080822092909255600160a060020a03851681522054611b8f908363ffffffff61194116565b600160a060020a038416600081815260208181526040918290209390935580518581529051919233927fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef9281900390910190a350600192915050565b6000821515611bfc5750600061082f565b50818102818382811515611c0c57fe5b041461082f57fe5b60008183811515611c2157fe5b04939250505056006f6e6c79206f776e657200000000000000000000000000000000000000000000a165627a7a72305820588663f9e6394049399c48c60a53b16165f1ddf1ff52ed3b0fe05fa4cd07de470029";

    public static final String FUNC_MINTINGFINISHED = "mintingFinished";

    public static final String FUNC_NAME = "name";

    public static final String FUNC_APPROVE = "approve";

    public static final String FUNC_MAX_INT = "MAX_INT";

    public static final String FUNC_STATSOF = "statsOf";

    public static final String FUNC_TOTALSUPPLY = "totalSupply";

    public static final String FUNC_TRANSFERFROM = "transferFrom";

    public static final String FUNC_REMOVEADDRESSESFROMWHITELIST = "removeAddressesFromWhitelist";

    public static final String FUNC_REMOVEADDRESSFROMWHITELIST = "removeAddressFromWhitelist";

    public static final String FUNC_TARGETSUPPLY = "targetSupply";

    public static final String FUNC_DECIMALS = "decimals";

    public static final String FUNC_REMAININGMINTABLESUPPLY = "remainingMintableSupply";

    public static final String FUNC_CAP = "cap";

    public static final String FUNC_MINTEDBY = "mintedBy";

    public static final String FUNC_MINT = "mint";

    public static final String FUNC_SETACCOUNTCUSTOMTAX = "setAccountCustomTax";

    public static final String FUNC_VAULTADDRESS = "vaultAddress";

    public static final String FUNC_TOTALTXS = "totalTxs";

    public static final String FUNC_DECREASEAPPROVAL = "decreaseApproval";

    public static final String FUNC_BALANCEOF = "balanceOf";

    public static final String FUNC_REMOVEACCOUNTCUSTOMTAX = "removeAccountCustomTax";

    public static final String FUNC_CALCULATETRANSFERTAXES = "calculateTransferTaxes";

    public static final String FUNC_ADDADDRESSTOWHITELIST = "addAddressToWhitelist";

    public static final String FUNC_FINISHMINTING = "finishMinting";

    public static final String FUNC_SETVAULTADDRESS = "setVaultAddress";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_SYMBOL = "symbol";

    public static final String FUNC_WHITELIST = "whitelist";

    public static final String FUNC_TRANSFER = "transfer";

    public static final String FUNC_MINTEDSUPPLY = "mintedSupply";

    public static final String FUNC_ISEXCLUDED = "isExcluded";

    public static final String FUNC_INCREASEAPPROVAL = "increaseApproval";

    public static final String FUNC_PLAYERS = "players";

    public static final String FUNC_ALLOWANCE = "allowance";

    public static final String FUNC_ADDADDRESSESTOWHITELIST = "addAddressesToWhitelist";

    public static final String FUNC_EXCLUDEACCOUNT = "excludeAccount";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final String FUNC_INCLUDEACCOUNT = "includeAccount";

    public static final Event TAXPAYED_EVENT = new Event("TaxPayed", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event MINT_EVENT = new Event("Mint", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event MINTFINISHED_EVENT = new Event("MintFinished", 
            Arrays.<TypeReference<?>>asList());
    ;

    public static final Event WHITELISTEDADDRESSADDED_EVENT = new Event("WhitelistedAddressAdded", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
    ;

    public static final Event WHITELISTEDADDRESSREMOVED_EVENT = new Event("WhitelistedAddressRemoved", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
    ;

    public static final Event OWNERSHIPTRANSFERRED_EVENT = new Event("OwnershipTransferred", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event APPROVAL_EVENT = new Event("Approval", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event TRANSFER_EVENT = new Event("Transfer", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected DripToken(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected DripToken(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected DripToken(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected DripToken(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<Bool> mintingFinished() {
        final Function function = new Function(FUNC_MINTINGFINISHED, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteFunctionCall<Utf8String> name() {
        final Function function = new Function(FUNC_NAME, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteFunctionCall<TransactionReceipt> approve(Address _spender, Uint256 _value) {
        final Function function = new Function(
                FUNC_APPROVE, 
                Arrays.<Type>asList(_spender, _value), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Uint256> MAX_INT() {
        final Function function = new Function(FUNC_MAX_INT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteFunctionCall<Tuple3<Uint256, Uint256, Uint256>> statsOf(Address player) {
        final Function function = new Function(FUNC_STATSOF, 
                Arrays.<Type>asList(player), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple3<Uint256, Uint256, Uint256>>(function,
                new Callable<Tuple3<Uint256, Uint256, Uint256>>() {
                    @Override
                    public Tuple3<Uint256, Uint256, Uint256> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<Uint256, Uint256, Uint256>(
                                (Uint256) results.get(0), 
                                (Uint256) results.get(1), 
                                (Uint256) results.get(2));
                    }
                });
    }

    public RemoteFunctionCall<Uint256> totalSupply() {
        final Function function = new Function(FUNC_TOTALSUPPLY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteFunctionCall<TransactionReceipt> transferFrom(Address _from, Address _to, Uint256 _value) {
        final Function function = new Function(
                FUNC_TRANSFERFROM, 
                Arrays.<Type>asList(_from, _to, _value), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
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

    public RemoteFunctionCall<Uint256> targetSupply() {
        final Function function = new Function(FUNC_TARGETSUPPLY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteFunctionCall<Uint8> decimals() {
        final Function function = new Function(FUNC_DECIMALS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteFunctionCall<Uint256> remainingMintableSupply() {
        final Function function = new Function(FUNC_REMAININGMINTABLESUPPLY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteFunctionCall<Uint256> cap() {
        final Function function = new Function(FUNC_CAP, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteFunctionCall<Uint256> mintedBy(Address player) {
        final Function function = new Function(FUNC_MINTEDBY, 
                Arrays.<Type>asList(player), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteFunctionCall<TransactionReceipt> mint(Address _to, Uint256 _amount) {
        final Function function = new Function(
                FUNC_MINT, 
                Arrays.<Type>asList(_to, _amount), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setAccountCustomTax(Address account, Uint8 taxRate) {
        final Function function = new Function(
                FUNC_SETACCOUNTCUSTOMTAX, 
                Arrays.<Type>asList(account, taxRate), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Address> vaultAddress() {
        final Function function = new Function(FUNC_VAULTADDRESS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteFunctionCall<Uint256> totalTxs() {
        final Function function = new Function(FUNC_TOTALTXS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteFunctionCall<TransactionReceipt> decreaseApproval(Address _spender, Uint256 _subtractedValue) {
        final Function function = new Function(
                FUNC_DECREASEAPPROVAL, 
                Arrays.<Type>asList(_spender, _subtractedValue), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Uint256> balanceOf(Address _owner) {
        final Function function = new Function(FUNC_BALANCEOF, 
                Arrays.<Type>asList(_owner), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteFunctionCall<TransactionReceipt> removeAccountCustomTax(Address account) {
        final Function function = new Function(
                FUNC_REMOVEACCOUNTCUSTOMTAX, 
                Arrays.<Type>asList(account), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Tuple2<Uint256, Uint256>> calculateTransferTaxes(Address _from, Uint256 _value) {
        final Function function = new Function(FUNC_CALCULATETRANSFERTAXES, 
                Arrays.<Type>asList(_from, _value), 
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

    public RemoteFunctionCall<TransactionReceipt> addAddressToWhitelist(Address addr) {
        final Function function = new Function(
                FUNC_ADDADDRESSTOWHITELIST, 
                Arrays.<Type>asList(addr), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> finishMinting() {
        final Function function = new Function(
                FUNC_FINISHMINTING, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setVaultAddress(Address _newVaultAddress) {
        final Function function = new Function(
                FUNC_SETVAULTADDRESS, 
                Arrays.<Type>asList(_newVaultAddress), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Address> owner() {
        final Function function = new Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteFunctionCall<Utf8String> symbol() {
        final Function function = new Function(FUNC_SYMBOL, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteFunctionCall<Bool> whitelist(Address param0) {
        final Function function = new Function(FUNC_WHITELIST, 
                Arrays.<Type>asList(param0), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteFunctionCall<TransactionReceipt> transfer(Address _to, Uint256 _value) {
        final Function function = new Function(
                FUNC_TRANSFER, 
                Arrays.<Type>asList(_to, _value), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Uint256> mintedSupply() {
        final Function function = new Function(FUNC_MINTEDSUPPLY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteFunctionCall<Bool> isExcluded(Address account) {
        final Function function = new Function(FUNC_ISEXCLUDED, 
                Arrays.<Type>asList(account), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteFunctionCall<TransactionReceipt> increaseApproval(Address _spender, Uint256 _addedValue) {
        final Function function = new Function(
                FUNC_INCREASEAPPROVAL, 
                Arrays.<Type>asList(_spender, _addedValue), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Uint256> players() {
        final Function function = new Function(FUNC_PLAYERS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteFunctionCall<Uint256> allowance(Address _owner, Address _spender) {
        final Function function = new Function(FUNC_ALLOWANCE, 
                Arrays.<Type>asList(_owner, _spender), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteFunctionCall<TransactionReceipt> addAddressesToWhitelist(DynamicArray<Address> addrs) {
        final Function function = new Function(
                FUNC_ADDADDRESSESTOWHITELIST, 
                Arrays.<Type>asList(addrs), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> excludeAccount(Address account) {
        final Function function = new Function(
                FUNC_EXCLUDEACCOUNT, 
                Arrays.<Type>asList(account), 
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

    public RemoteFunctionCall<TransactionReceipt> includeAccount(Address account) {
        final Function function = new Function(
                FUNC_INCLUDEACCOUNT, 
                Arrays.<Type>asList(account), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public List<TaxPayedEventResponse> getTaxPayedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(TAXPAYED_EVENT, transactionReceipt);
        ArrayList<TaxPayedEventResponse> responses = new ArrayList<TaxPayedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TaxPayedEventResponse typedResponse = new TaxPayedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.from = (Address) eventValues.getNonIndexedValues().get(0);
            typedResponse.vault = (Address) eventValues.getNonIndexedValues().get(1);
            typedResponse.amount = (Uint256) eventValues.getNonIndexedValues().get(2);
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<TaxPayedEventResponse> taxPayedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, TaxPayedEventResponse>() {
            @Override
            public TaxPayedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(TAXPAYED_EVENT, log);
                TaxPayedEventResponse typedResponse = new TaxPayedEventResponse();
                typedResponse.log = log;
                typedResponse.from = (Address) eventValues.getNonIndexedValues().get(0);
                typedResponse.vault = (Address) eventValues.getNonIndexedValues().get(1);
                typedResponse.amount = (Uint256) eventValues.getNonIndexedValues().get(2);
                return typedResponse;
            }
        });
    }

    public Flowable<TaxPayedEventResponse> taxPayedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TAXPAYED_EVENT));
        return taxPayedEventFlowable(filter);
    }

    public List<MintEventResponse> getMintEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(MINT_EVENT, transactionReceipt);
        ArrayList<MintEventResponse> responses = new ArrayList<MintEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            MintEventResponse typedResponse = new MintEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.to = (Address) eventValues.getIndexedValues().get(0);
            typedResponse.amount = (Uint256) eventValues.getNonIndexedValues().get(0);
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<MintEventResponse> mintEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, MintEventResponse>() {
            @Override
            public MintEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(MINT_EVENT, log);
                MintEventResponse typedResponse = new MintEventResponse();
                typedResponse.log = log;
                typedResponse.to = (Address) eventValues.getIndexedValues().get(0);
                typedResponse.amount = (Uint256) eventValues.getNonIndexedValues().get(0);
                return typedResponse;
            }
        });
    }

    public Flowable<MintEventResponse> mintEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(MINT_EVENT));
        return mintEventFlowable(filter);
    }

    public List<MintFinishedEventResponse> getMintFinishedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(MINTFINISHED_EVENT, transactionReceipt);
        ArrayList<MintFinishedEventResponse> responses = new ArrayList<MintFinishedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            MintFinishedEventResponse typedResponse = new MintFinishedEventResponse();
            typedResponse.log = eventValues.getLog();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<MintFinishedEventResponse> mintFinishedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, MintFinishedEventResponse>() {
            @Override
            public MintFinishedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(MINTFINISHED_EVENT, log);
                MintFinishedEventResponse typedResponse = new MintFinishedEventResponse();
                typedResponse.log = log;
                return typedResponse;
            }
        });
    }

    public Flowable<MintFinishedEventResponse> mintFinishedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(MINTFINISHED_EVENT));
        return mintFinishedEventFlowable(filter);
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

    public List<ApprovalEventResponse> getApprovalEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(APPROVAL_EVENT, transactionReceipt);
        ArrayList<ApprovalEventResponse> responses = new ArrayList<ApprovalEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ApprovalEventResponse typedResponse = new ApprovalEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.owner = (Address) eventValues.getIndexedValues().get(0);
            typedResponse.spender = (Address) eventValues.getIndexedValues().get(1);
            typedResponse.value = (Uint256) eventValues.getNonIndexedValues().get(0);
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ApprovalEventResponse> approvalEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, ApprovalEventResponse>() {
            @Override
            public ApprovalEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(APPROVAL_EVENT, log);
                ApprovalEventResponse typedResponse = new ApprovalEventResponse();
                typedResponse.log = log;
                typedResponse.owner = (Address) eventValues.getIndexedValues().get(0);
                typedResponse.spender = (Address) eventValues.getIndexedValues().get(1);
                typedResponse.value = (Uint256) eventValues.getNonIndexedValues().get(0);
                return typedResponse;
            }
        });
    }

    public Flowable<ApprovalEventResponse> approvalEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(APPROVAL_EVENT));
        return approvalEventFlowable(filter);
    }

    public List<TransferEventResponse> getTransferEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(TRANSFER_EVENT, transactionReceipt);
        ArrayList<TransferEventResponse> responses = new ArrayList<TransferEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TransferEventResponse typedResponse = new TransferEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.from = (Address) eventValues.getIndexedValues().get(0);
            typedResponse.to = (Address) eventValues.getIndexedValues().get(1);
            typedResponse.value = (Uint256) eventValues.getNonIndexedValues().get(0);
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<TransferEventResponse> transferEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, TransferEventResponse>() {
            @Override
            public TransferEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(TRANSFER_EVENT, log);
                TransferEventResponse typedResponse = new TransferEventResponse();
                typedResponse.log = log;
                typedResponse.from = (Address) eventValues.getIndexedValues().get(0);
                typedResponse.to = (Address) eventValues.getIndexedValues().get(1);
                typedResponse.value = (Uint256) eventValues.getNonIndexedValues().get(0);
                return typedResponse;
            }
        });
    }

    public Flowable<TransferEventResponse> transferEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TRANSFER_EVENT));
        return transferEventFlowable(filter);
    }

    @Deprecated
    public static DripToken load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new DripToken(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static DripToken load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new DripToken(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static DripToken load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new DripToken(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static DripToken load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new DripToken(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<DripToken> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, Uint256 _initialMint) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(_initialMint));
        return deployRemoteCall(DripToken.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<DripToken> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, Uint256 _initialMint) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(_initialMint));
        return deployRemoteCall(DripToken.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<DripToken> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, Uint256 _initialMint) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(_initialMint));
        return deployRemoteCall(DripToken.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<DripToken> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, Uint256 _initialMint) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(_initialMint));
        return deployRemoteCall(DripToken.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static class TaxPayedEventResponse extends BaseEventResponse {
        public Address from;

        public Address vault;

        public Uint256 amount;
    }

    public static class MintEventResponse extends BaseEventResponse {
        public Address to;

        public Uint256 amount;
    }

    public static class MintFinishedEventResponse extends BaseEventResponse {
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

    public static class ApprovalEventResponse extends BaseEventResponse {
        public Address owner;

        public Address spender;

        public Uint256 value;
    }

    public static class TransferEventResponse extends BaseEventResponse {
        public Address from;

        public Address to;

        public Uint256 value;
    }
}
