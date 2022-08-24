package com.crypto.demo.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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


    ///// BITCOIN IN TIME 
    @GetMapping("/showGraphsAllTime")
    ModelAndView showMeGraphs() throws JsonProcessingException, JsonMappingException {
        ModelAndView mv = new ModelAndView("chart");
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl
        = "https://api.blockchain.info/charts/total-bitcoins?timespan=all&format=json";
              ResponseEntity<String> response
        = restTemplate.getForEntity(fooResourceUrl , String.class);
        if(response.getStatusCode().equals( HttpStatus.OK)){
            JSONObject jsonObj = new JSONObject(response.getBody());

            String description = jsonObj.getString("description");

            JSONArray value   = jsonObj.getJSONArray("values");
            List<HashMap<String,Float>> result = new ObjectMapper().readValue(value.toString(), new TypeReference<List<HashMap<String,Float>>>(){});
    
    
            mv.addObject("dataPointsList", result);
            mv.addObject("description", description);
        }
        
        
        return mv;
    }

    @GetMapping("/showGraphLast30")
    ModelAndView showMeGraphMonth() throws JsonProcessingException, JsonMappingException {
        ModelAndView mv = new ModelAndView("chart");
        RestTemplate restTemplate = new RestTemplate();
               String fooResourceUrlMonth = "https://api.blockchain.info/charts/total-bitcoins?timespan=30days&format=json";

        ResponseEntity<String> responseMonth 
        = restTemplate.getForEntity(fooResourceUrlMonth , String.class);
        if(responseMonth.getStatusCode().equals( HttpStatus.OK)){
            JSONObject jsonObjMonth = new JSONObject(responseMonth.getBody());
            System.out.println("USLOO");
    
            JSONArray value2   = jsonObjMonth.getJSONArray("values");
            List<HashMap<String,Float>> result2 = new ObjectMapper().readValue(value2.toString(), new TypeReference<List<HashMap<String,Float>>>(){});
    
            mv.addObject("dataPointsList3", result2);
            mv.addObject("description3", "The total number of bitcoin that have been mined in last 30 days");
        }   
        return mv;
    }
    @GetMapping("/showGraph60days")
    ModelAndView showMeGraph60Days() throws JsonProcessingException, JsonMappingException {

        ModelAndView mv = new ModelAndView("chart");
        RestTemplate restTemplate = new RestTemplate();
               String fooResourceUrlMonth = "https://api.blockchain.info/charts/total-bitcoins?timespan=60days&format=json";

        ResponseEntity<String> responseMonth 
        = restTemplate.getForEntity(fooResourceUrlMonth , String.class);
        if(responseMonth.getStatusCode().equals( HttpStatus.OK)){
            JSONObject jsonObjMonth = new JSONObject(responseMonth.getBody());
            System.out.println("USLOO");
    
            JSONArray value2   = jsonObjMonth.getJSONArray("values");
            List<HashMap<String,Float>> result2 = new ObjectMapper().readValue(value2.toString(), new TypeReference<List<HashMap<String,Float>>>(){});
    
            mv.addObject("dataPointsList3", result2);
            mv.addObject("description3", "The total number of bitcoin that have been mined in last 60 days");
        }   
        return mv;
    }
   
    @GetMapping("/showGraph180days")
    ModelAndView showMeGraph180Days() throws JsonProcessingException, JsonMappingException {

        ModelAndView mv = new ModelAndView("chart");
        RestTemplate restTemplate = new RestTemplate();
               String fooResourceUrlMonth = "https://api.blockchain.info/charts/total-bitcoins?timespan=180days&format=json";

        ResponseEntity<String> responseMonth 
        = restTemplate.getForEntity(fooResourceUrlMonth , String.class);
        if(responseMonth.getStatusCode().equals( HttpStatus.OK)){
            JSONObject jsonObjMonth = new JSONObject(responseMonth.getBody());
    
            JSONArray value2   = jsonObjMonth.getJSONArray("values");
            List<HashMap<String,Float>> result2 = new ObjectMapper().readValue(value2.toString(), new TypeReference<List<HashMap<String,Float>>>(){});
    
            mv.addObject("dataPointsList3", result2);
            mv.addObject("description3", "The total number of bitcoin that have been mined in last 180 days");
        }   
        return mv;
    }
    @GetMapping("/showGraphLast3Years")
    ModelAndView showMeGraph3Years() throws JsonProcessingException, JsonMappingException {
        ModelAndView mv = new ModelAndView("chart");
        RestTemplate restTemplate = new RestTemplate();
               String fooResourceUrlMonth = "https://api.blockchain.info/charts/total-bitcoins?timespan=3years&format=json";

        ResponseEntity<String> responseMonth 
        = restTemplate.getForEntity(fooResourceUrlMonth , String.class);
        if(responseMonth.getStatusCode().equals( HttpStatus.OK)){
            JSONObject jsonObjMonth = new JSONObject(responseMonth.getBody());
            System.out.println("USLOO");
    
            JSONArray value2   = jsonObjMonth.getJSONArray("values");
            List<HashMap<String,Float>> result2 = new ObjectMapper().readValue(value2.toString(), new TypeReference<List<HashMap<String,Float>>>(){});
    
            mv.addObject("dataPointsList3", result2);
            mv.addObject("description3", "The total number of bitcoin that have been mined in last 3 years");
        }   
        return mv;
    }
    

    //////////////////////////////////////////////////

    /////////////////////// MARKET PRICE
    @GetMapping("/showMarketPlaceAllTime")
    //https://api.blockchain.info/charts/market-price?timespan=30days&format=json
    ModelAndView showMeGraphsMarketAll() throws JsonProcessingException, JsonMappingException {
        ModelAndView mv = new ModelAndView("chart2");
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl
        = "https://api.blockchain.info/charts/market-price?timespan=all&format=json";
              ResponseEntity<String> response
        = restTemplate.getForEntity(fooResourceUrl , String.class);
        if(response.getStatusCode().equals( HttpStatus.OK)){
            JSONObject jsonObj = new JSONObject(response.getBody());

            String description = jsonObj.getString("description");

            JSONArray value   = jsonObj.getJSONArray("values");
            List<HashMap<String,Float>> result = new ObjectMapper().readValue(value.toString(), new TypeReference<List<HashMap<String,Float>>>(){});
    
    
            mv.addObject("dataPointsList", result);
            mv.addObject("description", description);
        }
        
        
        return mv;
    }

    @GetMapping("/showMarketPlacelast30")
    ModelAndView showMeGraphsMarket30() throws JsonProcessingException, JsonMappingException {
        ModelAndView mv = new ModelAndView("chart2");
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl
        = "https://api.blockchain.info/charts/market-price?timespan=30days&format=json";
              ResponseEntity<String> response
        = restTemplate.getForEntity(fooResourceUrl , String.class);
        if(response.getStatusCode().equals( HttpStatus.OK)){
            JSONObject jsonObj = new JSONObject(response.getBody());


            JSONArray value   = jsonObj.getJSONArray("values");
            List<HashMap<String,Float>> result = new ObjectMapper().readValue(value.toString(), new TypeReference<List<HashMap<String,Float>>>(){});
    
            mv.addObject("dataPointsList3", result);
            mv.addObject("description3", "Average market price in last 30 days");
        }
        
        
        return mv;
    }

    @GetMapping("/showMarketPlacelast60")
    ModelAndView showMeGraphsMarket600() throws JsonProcessingException, JsonMappingException {
        ModelAndView mv = new ModelAndView("chart2");
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl
        = "https://api.blockchain.info/charts/market-price?timespan=60days&format=json";
              ResponseEntity<String> response
        = restTemplate.getForEntity(fooResourceUrl , String.class);
        if(response.getStatusCode().equals( HttpStatus.OK)){
            JSONObject jsonObj = new JSONObject(response.getBody());


            JSONArray value   = jsonObj.getJSONArray("values");
            List<HashMap<String,Float>> result = new ObjectMapper().readValue(value.toString(), new TypeReference<List<HashMap<String,Float>>>(){});
    
            mv.addObject("dataPointsList3", result);
            mv.addObject("description3", "Average market price in last 30 days");
        }
        
        
        return mv;
    }

    @GetMapping("/showMarketPlacelast180")
    ModelAndView showMeGraphsMarket180() throws JsonProcessingException, JsonMappingException {
        ModelAndView mv = new ModelAndView("chart2");
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl
        = "https://api.blockchain.info/charts/market-price?timespan=180days&format=json";
              ResponseEntity<String> response
        = restTemplate.getForEntity(fooResourceUrl , String.class);
        if(response.getStatusCode().equals( HttpStatus.OK)){
            JSONObject jsonObj = new JSONObject(response.getBody());


            JSONArray value   = jsonObj.getJSONArray("values");
            List<HashMap<String,Float>> result = new ObjectMapper().readValue(value.toString(), new TypeReference<List<HashMap<String,Float>>>(){});
    
            mv.addObject("dataPointsList3", result);
            mv.addObject("description3", "Average market price in last 30 days");
        }
        
        
        return mv;
    }
    @GetMapping("/showMarketPlacelast1Year")
    ModelAndView showMeGraphsMarket1y() throws JsonProcessingException, JsonMappingException {
        ModelAndView mv = new ModelAndView("chart2");
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl
        = "https://api.blockchain.info/charts/market-price?timespan=1year&format=json";
              ResponseEntity<String> response
        = restTemplate.getForEntity(fooResourceUrl , String.class);
        if(response.getStatusCode().equals( HttpStatus.OK)){
            JSONObject jsonObj = new JSONObject(response.getBody());


            JSONArray value   = jsonObj.getJSONArray("values");
            List<HashMap<String,Float>> result = new ObjectMapper().readValue(value.toString(), new TypeReference<List<HashMap<String,Float>>>(){});
    
            mv.addObject("dataPointsList3", result);
            mv.addObject("description3", "Average market price in last 30 days");
        }
        
        
        return mv;
    }

    /////////////////////////////////////////////
    //////////// CONFIRMED TRANSACTION
    // <a class="dropdown-item" href="/showConfirmedTransaction">Confirmed transactions</a>
    
    @GetMapping("/showConfirmedTransaction")
    ModelAndView showMeGraphConfirmedTxAll() throws JsonProcessingException, JsonMappingException {
        ModelAndView mv = new ModelAndView("chart3");
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl
        = "https://api.blockchain.info/charts/n-transactions?timespan=all&format=json";
              ResponseEntity<String> response
        = restTemplate.getForEntity(fooResourceUrl , String.class);
        if(response.getStatusCode().equals( HttpStatus.OK)){
            JSONObject jsonObj = new JSONObject(response.getBody());

            String description = jsonObj.getString("description");

            JSONArray value   = jsonObj.getJSONArray("values");
            List<HashMap<String,Float>> result = new ObjectMapper().readValue(value.toString(), new TypeReference<List<HashMap<String,Float>>>(){});
    
    
            mv.addObject("dataPointsList", result);
            mv.addObject("description", description);
        }
        
        
        return mv;
    }

    @GetMapping("/showConfirmedTransaction30")
    ModelAndView showMeGraphConfirmedTx30() throws JsonProcessingException, JsonMappingException {
        ModelAndView mv = new ModelAndView("chart3");
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl
        = "https://api.blockchain.info/charts/n-transactions?timespan=30days&format=json";
              ResponseEntity<String> response
        = restTemplate.getForEntity(fooResourceUrl , String.class);
        if(response.getStatusCode().equals( HttpStatus.OK)){
            JSONObject jsonObj = new JSONObject(response.getBody());


            JSONArray value   = jsonObj.getJSONArray("values");
            List<HashMap<String,Float>> result = new ObjectMapper().readValue(value.toString(), new TypeReference<List<HashMap<String,Float>>>(){});
    
            mv.addObject("dataPointsList3", result);
            mv.addObject("description3", "The total number of confirmed transactions in last 30 days");
        }
        
        
        return mv;
    }

    @GetMapping("/showConfirmedTransaction60")
    ModelAndView showMeGraphConfirmedTx600() throws JsonProcessingException, JsonMappingException {
        ModelAndView mv = new ModelAndView("chart3");
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl
        = "https://api.blockchain.info/charts/n-transactions?timespan=60days&format=json";
              ResponseEntity<String> response
        = restTemplate.getForEntity(fooResourceUrl , String.class);
        if(response.getStatusCode().equals( HttpStatus.OK)){
            JSONObject jsonObj = new JSONObject(response.getBody());


            JSONArray value   = jsonObj.getJSONArray("values");
            List<HashMap<String,Float>> result = new ObjectMapper().readValue(value.toString(), new TypeReference<List<HashMap<String,Float>>>(){});
    
            mv.addObject("dataPointsList3", result);
            mv.addObject("description3", "The total number of confirmed transactions in last 60 days");
        }
        
        
        return mv;
    }

    @GetMapping("/showConfirmedTransaction180")
    ModelAndView showMeGraphConfirmedTx180() throws JsonProcessingException, JsonMappingException {
        ModelAndView mv = new ModelAndView("chart3");
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl
        = "https://api.blockchain.info/charts/n-transactions?timespan=180days&format=json";
              ResponseEntity<String> response
        = restTemplate.getForEntity(fooResourceUrl , String.class);
        if(response.getStatusCode().equals( HttpStatus.OK)){
            JSONObject jsonObj = new JSONObject(response.getBody());


            JSONArray value   = jsonObj.getJSONArray("values");
            List<HashMap<String,Float>> result = new ObjectMapper().readValue(value.toString(), new TypeReference<List<HashMap<String,Float>>>(){});
    
            mv.addObject("dataPointsList3", result);
            mv.addObject("description3", "The total number of confirmed transactions in last 180 days");
        }
        
        
        return mv;
    }
    
    @GetMapping("/showConfirmedTransaction1Year")
    ModelAndView showMeGraphConfirmedTx1y() throws JsonProcessingException, JsonMappingException {
        ModelAndView mv = new ModelAndView("chart3");
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl
        = "https://api.blockchain.info/charts/n-transactions?timespan=1year&format=json";
              ResponseEntity<String> response
        = restTemplate.getForEntity(fooResourceUrl , String.class);
        if(response.getStatusCode().equals( HttpStatus.OK)){
            JSONObject jsonObj = new JSONObject(response.getBody());


            JSONArray value   = jsonObj.getJSONArray("values");
            List<HashMap<String,Float>> result = new ObjectMapper().readValue(value.toString(), new TypeReference<List<HashMap<String,Float>>>(){});
    
            mv.addObject("dataPointsList3", result);
            mv.addObject("description3", "The total number of confirmed transactions in last year");
        }
        
        return mv;
    }




    ////////////////////////////////////////////////////
    ///////////////////showSizeOfBlockchain
    @GetMapping("/showSizeOfBlockchain")
    ModelAndView showMeGraphBlochchain() throws JsonProcessingException, JsonMappingException {
        ModelAndView mv = new ModelAndView("chart4");
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl
        = "https://api.blockchain.info/charts/blocks-size?timespan=all&format=json";
              ResponseEntity<String> response
        = restTemplate.getForEntity(fooResourceUrl , String.class);
        if(response.getStatusCode().equals( HttpStatus.OK)){
            JSONObject jsonObj = new JSONObject(response.getBody());

            String description = jsonObj.getString("description");

            JSONArray value   = jsonObj.getJSONArray("values");
            List<HashMap<String,Float>> result = new ObjectMapper().readValue(value.toString(), new TypeReference<List<HashMap<String,Float>>>(){});
    
    
            mv.addObject("dataPointsList", result);
            mv.addObject("description", description);
        }
        else{
            mv.setViewName("redirect:/index.html");
        }
        return mv;
    }

    @GetMapping("/showSizeOfBlockchain30days")
    ModelAndView showMeGraphBlochchain30() throws JsonProcessingException, JsonMappingException {
        ModelAndView mv = new ModelAndView("chart4");
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl
        = "https://api.blockchain.info/charts/blocks-size?timespan=30days&format=json";
              ResponseEntity<String> response
        = restTemplate.getForEntity(fooResourceUrl , String.class);
        if(response.getStatusCode().equals( HttpStatus.OK)){
            JSONObject jsonObj = new JSONObject(response.getBody());

            JSONArray value   = jsonObj.getJSONArray("values");
            List<HashMap<String,Float>> result = new ObjectMapper().readValue(value.toString(), new TypeReference<List<HashMap<String,Float>>>(){});
    
    
            mv.addObject("dataPointsList3", result);
            mv.addObject("description3", "Blockchain size during last 30 days");
        }
        return mv;
    }
    @GetMapping("/showSizeOfBlockchain60days")
    ModelAndView showMeGraphBlochchain60() throws JsonProcessingException, JsonMappingException {
        ModelAndView mv = new ModelAndView("chart3");
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl
        = "https://api.blockchain.info/charts/blocks-size?timespan=60days&format=json";
              ResponseEntity<String> response
        = restTemplate.getForEntity(fooResourceUrl , String.class);
        if(response.getStatusCode().equals( HttpStatus.OK)){
            JSONObject jsonObj = new JSONObject(response.getBody());

            JSONArray value   = jsonObj.getJSONArray("values");
            List<HashMap<String,Float>> result = new ObjectMapper().readValue(value.toString(), new TypeReference<List<HashMap<String,Float>>>(){});
    
    
            mv.addObject("dataPointsList3", result);
            mv.addObject("description3", "Blockchain size during last 60 days");
        }
        return mv;
    }
    @GetMapping("/showSizeOfBlockchain180days")
    ModelAndView showMeGraphBlochchain180() throws JsonProcessingException, JsonMappingException {
        ModelAndView mv = new ModelAndView("chart4");
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl
        = "https://api.blockchain.info/charts/blocks-size?timespan=180days&format=json";
              ResponseEntity<String> response
        = restTemplate.getForEntity(fooResourceUrl , String.class);
        if(response.getStatusCode().equals( HttpStatus.OK)){
            JSONObject jsonObj = new JSONObject(response.getBody());


            JSONArray value   = jsonObj.getJSONArray("values");
            List<HashMap<String,Float>> result = new ObjectMapper().readValue(value.toString(), new TypeReference<List<HashMap<String,Float>>>(){});
    
    
            mv.addObject("dataPointsList3", result);
            mv.addObject("description3", "Blockchain size during last 180 days");
        }
        return mv;
    }
    @GetMapping("/showSizeOfBlockchain1Year")
    ModelAndView showMeGraphBlochchain1y() throws JsonProcessingException, JsonMappingException {
        ModelAndView mv = new ModelAndView("chart4");
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl
        = "https://api.blockchain.info/charts/blocks-size?timespan=1year&format=json";
              ResponseEntity<String> response
        = restTemplate.getForEntity(fooResourceUrl , String.class);
        if(response.getStatusCode().equals( HttpStatus.OK)){
            JSONObject jsonObj = new JSONObject(response.getBody());
            JSONArray value   = jsonObj.getJSONArray("values");
            List<HashMap<String,Float>> result = new ObjectMapper().readValue(value.toString(), new TypeReference<List<HashMap<String,Float>>>(){});
    
    
            mv.addObject("dataPointsList3", result);
            mv.addObject("description3", "Block size in last 1 year");
        }
        return mv;
    }
}
