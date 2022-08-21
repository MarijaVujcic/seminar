package com.crypto.demo.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import wf.bitcoin.javabitcoindrpcclient.BitcoinJSONRPCClient;
import wf.bitcoin.javabitcoindrpcclient.BitcoindRpcClient;
import wf.bitcoin.javabitcoindrpcclient.BitcoindRpcClient.BlockChainInfo;
import java.math.BigDecimal;

@Controller
public class HomeController {
    
    BitcoindRpcClient bitcClient = new BitcoinJSONRPCClient();

    /**
     * Route representing welcome page
     * @return view index.html
     */
    @GetMapping("/")
    String welcomePage(){
        // BlockChainInfo
        // TODO: POGLEDATI KAKO DODATI OVO NA MAIN PAGE PLUS GRAF
        String bestBlockHash = bitcClient.getBestBlockHash();
        BigDecimal balance = bitcClient.getBalance();
        BlockChainInfo blockchainInfo = bitcClient.getBlockChainInfo();
        bitcClient.getDifficulty();
        bitcClient.getMiningInfo();
        bitcClient.getNetworkInfo();
        bitcClient.getNetTotals();
        return "index";
    }

    
  


}
