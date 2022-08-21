package com.crypto.demo.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import wf.bitcoin.javabitcoindrpcclient.BitcoinJSONRPCClient;

import wf.bitcoin.javabitcoindrpcclient.*;
import wf.bitcoin.javabitcoindrpcclient.BitcoindRpcClient.Block;
import wf.bitcoin.javabitcoindrpcclient.BitcoindRpcClient.Transaction;
import wf.bitcoin.javabitcoindrpcclient.BitcoindRpcClient.RawTransaction;
import java.util.ArrayList;
import java.util.List;
/**
 * Class representing controller for API request from client-side.
 * It's purpuse is handling API request for block/transaction informations.
 */
@Controller
public class ChainController {

    BitcoindRpcClient bitcClient = new BitcoinJSONRPCClient();

    @PostMapping("/getBlockOrTx")
    ModelAndView showMeInfoBlock(@Validated String whatToShow){
        // TODO: PROVJERITI ZASTO NE VADI TX
        Block b;
        Transaction tx = null;
        RawTransaction rawTx = null; 
        List<String> txs = new ArrayList<String>();
        try{
            if(whatToShow.length() < 64)
            {
                b = bitcClient.getBlock(Integer.valueOf(whatToShow));
            }
            else{
                b = bitcClient.getBlock(whatToShow);
            }
            txs = b.tx();
        }
        catch (Exception e){
            System.out.println("Error getting block");
            b = null;
        }
        if(b==null){
            
            try{
                System.out.println("USLOOO U TX");
                tx = bitcClient.getTransaction(whatToShow);
                rawTx = tx.raw();
                
            }
            catch (Exception e){
                System.out.println("Error getting transaction");
                tx = null;
            }
        }
    
        ModelAndView m = new ModelAndView("blockInformations");
        m.addObject("block", b);
        m.addObject("txs", txs);
        m.addObject("tx", tx);
        m.addObject("rawTx", rawTx);
        return m;
    }


    
    
}
