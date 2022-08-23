package com.crypto.demo.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.json.JSONObject;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.web.servlet.ModelAndView;

import wf.bitcoin.javabitcoindrpcclient.BitcoinJSONRPCClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus ;
import wf.bitcoin.javabitcoindrpcclient.*;
import wf.bitcoin.javabitcoindrpcclient.BitcoindRpcClient.Block;
import wf.bitcoin.javabitcoindrpcclient.BitcoindRpcClient.Transaction;
import wf.bitcoin.javabitcoindrpcclient.BitcoindRpcClient.RawTransaction;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import org.json.JSONArray;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import 

com.fasterxml.jackson.databind.JsonMappingException 

;
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


    @GetMapping("/showGraphs")
    ModelAndView showMeGraphs() throws JsonProcessingException, JsonMappingException {
       
       // System.out.println(response.getBody());
        

        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl
        = "https://api.blockchain.info/charts/total-bitcoins?format=json";
        ResponseEntity<String> response
        = restTemplate.getForEntity(fooResourceUrl , String.class);
        if(response.getStatusCode().equals( HttpStatus.OK)){
 
        }
        JSONObject jsonObj = new JSONObject(response.getBody());
        String description = jsonObj.getString("description");
        System.out.println(jsonObj.get("values").getClass());
        JSONArray value   = jsonObj.getJSONArray("values");
        System.out.println("HHHHHHHHHHHHHHHHHHHHHHHHHHHH");
        List<HashMap<String,Float>> result = new ObjectMapper().readValue(value.toString(), new TypeReference<List<HashMap<String,Float>>>(){});

        List<Map<String, String>> listOfMaps =  new ArrayList<>();;
    List<String> valuesMatchingKey = listOfMaps.stream()
        .filter(map -> map.containsKey("x"))
        .map(map -> map.get("x"))
        .collect(Collectors.toList());
        System.out.println(valuesMatchingKey);
        ModelAndView mv = new ModelAndView("chart");
        mv.addObject("dataPointsList", result);
        mv.addObject("description", description);
        
        return mv;
    }

    @GetMapping("/chartData")
    @ResponseBody
    JSONArray chartData(){
         // https://api.blockchain.info/charts/total-bitcoins?format=json
       RestTemplate restTemplate = new RestTemplate();
       String fooResourceUrl
       = "https://api.blockchain.info/charts/total-bitcoins?format=json";
       ResponseEntity<String> response
       = restTemplate.getForEntity(fooResourceUrl , String.class);
       if(response.getStatusCode().equals( HttpStatus.OK)){

       }
       JSONObject jsonObj = new JSONObject(response.getBody());
       String description = jsonObj.getString("description");
       System.out.println(jsonObj.get("values").getClass());
       JSONArray value   = jsonObj.getJSONArray("values");
       System.out.println("HHHHHHHHHHHHHHHHHHHHHHHHHHHH");
       return value;
    }

    
    
}
