package com.crypto.demo.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
 
import wf.bitcoin.javabitcoindrpcclient.BitcoinJSONRPCClient;
import wf.bitcoin.javabitcoindrpcclient.BitcoindRpcClient;
import wf.bitcoin.javabitcoindrpcclient.BitcoindRpcClient.BlockChainInfo;
import wf.bitcoin.javabitcoindrpcclient.BitcoindRpcClient.MiningInfo;
import wf.bitcoin.javabitcoindrpcclient.BitcoindRpcClient.NetworkInfo;
import wf.bitcoin.javabitcoindrpcclient.BitcoindRpcClient.NetTotals;


import java.math.BigDecimal;

@Controller
public class HomeController {
    
    BitcoindRpcClient bitcClient = new BitcoinJSONRPCClient();

    /**
     * Route representing welcome page
     * @return view index.html
     */
    @GetMapping("/")
    ModelAndView welcomePage(){
        // BlockChainInfo
        // TODO: POGLEDATI KAKO DODATI OVO NA MAIN PAGE PLUS GRAF
        BigDecimal balance = bitcClient.getBalance();
        BlockChainInfo blockchainInfo = bitcClient.getBlockChainInfo();
        
        MiningInfo miningInfo = bitcClient.getMiningInfo();
        NetworkInfo netInfo = bitcClient.getNetworkInfo();
        NetTotals netTotals = bitcClient.getNetTotals();
        ModelAndView mv = new ModelAndView("index");
        mv.addObject("blockchainInfo", blockchainInfo);
        mv.addObject("miningInfo", miningInfo);
        mv.addObject("netInfo", netInfo);
        mv.addObject("netTotals", netTotals);
        mv.addObject("balance", balance);
        return mv;
    }

    
  


}
