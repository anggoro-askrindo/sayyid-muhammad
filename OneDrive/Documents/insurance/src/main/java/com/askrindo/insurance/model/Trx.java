package com.askrindo.insurance.model;

import lombok.Data;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "trx")
@Data
public class Trx {

    @Id
    private String id; // Generated random string ID

    private String sertificate; // Auto-generated format
    private String insuredName;
    private String legalId;
    private String sms;
    private String email;
    private String productCode;

    private Date initTerm;
    private Date endTerm;
    private String ownerInfo;
    private String address;

    private String packageInfo;
    private BigDecimal premi;
}
