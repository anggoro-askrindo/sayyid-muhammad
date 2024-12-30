package com.askrindo.insurance.controller;

import com.askrindo.insurance.model.Lookup;
import com.askrindo.insurance.service.LookupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/lookups")
@RequiredArgsConstructor
@Slf4j
public class LookupController {

    private final LookupService lookupService;

    @GetMapping("/all")
    public ResponseEntity<List<Lookup>> getAllLookups() {
        log.info("Received request to get all lookups");
        List<Lookup> lookups = lookupService.getAllLookups();
        return ResponseEntity.ok(lookups);
    }

    @GetMapping("/id")
    public ResponseEntity<Lookup> getLookupById(@RequestParam String lookupKey) {
        log.info("Received request to get lookup by id");
        Lookup lookup = lookupService.getLookupById(lookupKey);
        return ResponseEntity.ok(lookup);
    }
    
}
