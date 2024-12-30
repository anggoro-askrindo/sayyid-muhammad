package com.askrindo.insurance.controller;

import com.askrindo.insurance.model.Trx;
import com.askrindo.insurance.model.Trx6001;
import com.askrindo.insurance.model.Trx6002;
import com.askrindo.insurance.service.TrxService;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Slf4j
public class TrxController {

    private final TrxService trxService;

    // DTO for product code 6001
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static  class Trx6001Request {
        private String heirName;
        private Date heirDob;
        private String heirSms;
        private String relation;
    }

    // DTO for product code 6002
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static  class Trx6002Request {
        private String shipId;
        private String shipType;
        private String shipConstruction;
        private String shipPurpose;
        private BigDecimal shipPrice;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createProduct(@RequestBody Map<String, Object> payload) {
        // Deserialize the common Trx object
        Trx trx = new ObjectMapper().convertValue(payload.get("trx"), Trx.class);
        log.info("trx: {}", trx);

        if ("6001".equals(trx.getProductCode())) {
            // Handle Trx6001 specific fields
            Trx6001Request trx6001Request = new ObjectMapper().convertValue(payload.get("trx6001"), Trx6001Request.class);
            Trx6001 trx6001 = new Trx6001();
            log.info("trx6001Request: {}", trx6001Request);
            trx6001.setHeirName(trx6001Request.getHeirName());
            trx6001.setHeirDob(trx6001Request.getHeirDob());
            trx6001.setHeirSms(trx6001Request.getHeirSms());
            trx6001.setRelation(trx6001Request.getRelation());
            
            // Call the service to save Trx6001 and the associated Trx
            trxService.createTrx6001(trx, trx6001);
        } else if ("6002".equals(trx.getProductCode())) {
            // Handle Trx6002 specific fields
            Trx6002Request trx6002Request = new ObjectMapper().convertValue(payload.get("trx6002"), Trx6002Request.class);
            Trx6002 trx6002 = new Trx6002();
            trx6002.setShipId(trx6002Request.getShipId());
            trx6002.setShipType(trx6002Request.getShipType());
            trx6002.setShipPrice(trx6002Request.getShipPrice());
            trx6002.setShipConstruction(trx6002Request.getShipConstruction());
            trx6002.setShipPurpose(trx6002Request.getShipPurpose());
            
            // Call the service to save Trx6002 and the associated Trx
            trxService.createTrx6002(trx, trx6002);
        } else {
            return ResponseEntity.badRequest().body("Invalid product_code");
        }

        return ResponseEntity.ok("Product created successfully!");
    }

    // get all trx
    @GetMapping("/all")
    public ResponseEntity<Iterable<Trx>> getAllTrx() {
        log.info("Received request to get all trx");
        try {
            Iterable<Trx> trx = trxService.getAllTrx();
            return ResponseEntity.ok(trx);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // get product code 6001
    @GetMapping("/6001")
    public ResponseEntity<Iterable<Trx6001>> getTrx6001() {
        log.info("Received request to get trx 6001");
        try {
            Iterable<Trx6001> trx6001 = trxService.getTrx6001();
            return ResponseEntity.ok(trx6001);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // get product code 6002
    @GetMapping("/6002")
    public ResponseEntity<Iterable<Trx6002>> getTrx6002() {
        log.info("Received request to get trx 6002");
        try {
            Iterable<Trx6002> trx6002 = trxService.getTrx6002();
            return ResponseEntity.ok(trx6002);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }



}
