package com.askrindo.insurance.service;

import com.askrindo.insurance.model.Trx;
import com.askrindo.insurance.model.Trx6001;
import com.askrindo.insurance.model.Trx6002;
import com.askrindo.insurance.repository.TrxRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import com.askrindo.insurance.repository.Trx6001Repository;
import com.askrindo.insurance.repository.Trx6002Repository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrxService {

    private final TrxRepository trxRepository;
    private final Trx6001Repository trx6001Repository;
    private final Trx6002Repository trx6002Repository;

    @PersistenceContext
    private EntityManager entityManager;

    private String generateSertificate(String productCode) {
        String sequence = String.format("%05d", getNextCertificateSequence());
        String month = getRomanMonth();
        String year = new SimpleDateFormat("yyyy").format(new Date());
        return String.format("%s/%s/%s/%s", sequence, productCode, month, year);
    }

    public Long getNextCertificateSequence() {
        return ((Number) entityManager
            .createNativeQuery("SELECT nextval('trx_certificate_seq')")
            .getSingleResult())
            .longValue();
    }

    private String getRomanMonth() {
        String[] romanMonths = {
            "I", "II", "III", "IV", "V", "VI",
            "VII", "VIII", "IX", "X", "XI", "XII"
        };
        Calendar calendar = Calendar.getInstance();
        return romanMonths[calendar.get(Calendar.MONTH)];
    }

    // function to count premi
    public BigDecimal countPremi(BigDecimal premi, long diff) {
        premi =  new BigDecimal(( 40000 * diff ) / 365);

        return premi;
    }

    @Transactional
    public void createTrx6001(Trx trx, Trx6001 trx6001) {
        String id = UUID.randomUUID().toString();
        trx.setId(id);
        trx.setSertificate(generateSertificate(trx.getProductCode()));

        // get package data from trx
        String packageInfo = trx.getPackageInfo() != null ? trx.getPackageInfo() : "Silver";
        BigDecimal premi = BigDecimal.ZERO;
        // get difference of days between initTerm and endTerm
        long diff = trx.getEndTerm().getTime() - trx.getInitTerm().getTime();
        diff = diff / (1000 * 60 * 60 * 24);

        switch (packageInfo) {
            case "Silver": 
                premi =  countPremi(new BigDecimal(40000), diff);
                break;
            case "Gold":   
                premi =  countPremi(new BigDecimal(50000), diff);
                break;  
            case "Platinum":  
                premi =  countPremi(new BigDecimal(60000), diff);
                break;
            default:
                break;
        }
        trx.setPremi(premi);
        trx6001.setTrx(trx);
        trx6001Repository.save(trx6001);
    }

    @Transactional
    public void createTrx6002(Trx trx, Trx6002 trx6002) {
        String id = UUID.randomUUID().toString();
        trx.setId(id);
        trx.setSertificate(generateSertificate(trx.getProductCode()));

        String packageInfo = trx.getPackageInfo() != null ? trx.getPackageInfo() : "Silver";
        BigDecimal premi = BigDecimal.ZERO;
        // get difference of days between initTerm and endTerm
        long diff = trx.getEndTerm().getTime() - trx.getInitTerm().getTime();
        // convert diff to number of days
        diff = diff / (1000 * 60 * 60 * 24);
        log.info("diff: {}", diff);

        switch (packageInfo) {
            case "Silver": 
                premi =  countPremi(new BigDecimal(70000), diff);
                break;
            case "Gold":   
                premi =  countPremi(new BigDecimal(80000), diff);
                break;  
            case "Platinum":  
                premi =  countPremi(new BigDecimal(90000), diff);
                break;
            default:
                break;
        }
        trx.setPremi(premi);
        trx6002.setTrx(trx); 
        trx6002Repository.save(trx6002);
    }

    public Iterable<Trx> getAllTrx() {
        log.info("Fetching all trx records from the database");
        return trxRepository.findAll();
    }

    public Iterable<Trx6001> getTrx6001() {
        log.info("Fetching all trx6001 records from the database");
        return trx6001Repository.findAll();
    }

    public Iterable<Trx6002> getTrx6002() {
        log.info("Fetching all trx6002 records from the database");
        return trx6002Repository.findAll();
    }
}
