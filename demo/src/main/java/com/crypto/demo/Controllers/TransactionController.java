package com.crypto.demo.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import wf.bitcoin.javabitcoindrpcclient.*;
import wf.bitcoin.javabitcoindrpcclient.BitcoindRpcClient.Block;

/**
 * Class representing controller for API request from client-side.
 * It's purpuse is handling API request for transaction informations.
 */
@Controller
public class TransactionController {
    BitcoindRpcClient bitcClient = new BitcoinJSONRPCClient();

    /// TRANSACTION FUNCTIONALITIES ///


    
    
}
