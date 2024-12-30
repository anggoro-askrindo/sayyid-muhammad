package com.askrindo.insurance.service;

import com.askrindo.insurance.model.Lookup;
import com.askrindo.insurance.repository.LookupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LookupService {

    private final LookupRepository lookupRepository;

    public List<Lookup> getAllLookups() {
        log.info("Fetching all lookup records from the database");
        return lookupRepository.findAll();
    }

    public Lookup getLookupById(String lookupKey) {
        log.info("Fetching lookup record by id from the database");
        return lookupRepository.findById(lookupKey).orElse(null);
    }
}
