package com.crypto.demo.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import wf.bitcoin.javabitcoindrpcclient.*;
import wf.bitcoin.javabitcoindrpcclient.BitcoindRpcClient.Block;

/**
 * Class representing controller for API request from client-side.
 * It's purpuse is handling API request for block informations.
 */
@Controller
public class BlockController {
    BitcoindRpcClient bitcClient = new BitcoinJSONRPCClient();

    /**
     * Route representing welcome page
     * @return view index.html
     */
    @GetMapping("/")
    String welcomePage(){
        return "index";
    }

    /// BLOCK FUNCTIONALITIES ///

    /**
     * Route for block count API request
     * @return number of blocks in blockchain
     */
    @GetMapping("/blockHeight")
    @ResponseBody
    String blockCount(){
        return String.valueOf(bitcClient.getBlockCount());
    }

    /**
     * Route for block hash on given height
     * @return hash value of block on height
     */
    @GetMapping("/blockHash")
    @ResponseBody
    String blockHash(@RequestParam("height") String height){
        return String.valueOf(bitcClient.getBlockHash(Integer.parseInt(height)));
    }

    @GetMapping("/blockRaw")
    @ResponseBody
    String blockRaw(@RequestParam String blockHash){
        return bitcClient.getRawBlock(blockHash);
    }

    @GetMapping("/blockRawByHeight")
    @ResponseBody
    String blockRawByHeight(int height){
        String bHash = bitcClient.getBlockHash(height);
        String rawB = bitcClient.getRawBlock(bHash);

        return rawB;
    }

    @GetMapping("/block")
    @ResponseBody
    Block block(@RequestParam String blockHash){
        Block bLock = bitcClient.getBlock(blockHash);
        return bLock;
    }
    
}
