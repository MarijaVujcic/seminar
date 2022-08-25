package com.crypto.demo.Controllers;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
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
    ModelAndView welcomePage(String valueToChange){
        
        // BlockChainInfo
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

        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl
        = "https://api.blockchain.info/ticker";
              ResponseEntity<String> response
        = restTemplate.getForEntity(fooResourceUrl , String.class);
        if(response.getStatusCode().equals( HttpStatus.OK)){
            JSONObject jsonObj = new JSONObject(response.getBody());
            mv.addObject("val", jsonObj);

        }
        if(valueToChange != null){
            String change = "https://blockchain.info/tobtc?currency=HRK&value="+valueToChange.toString();
                ResponseEntity<String> response2
            = restTemplate.getForEntity(change , String.class);
            if(response2.getStatusCode().equals( HttpStatus.OK)){
                mv.addObject("change", response2.getBody());

        }
        }
       
        return mv;
    }

    
  


}
