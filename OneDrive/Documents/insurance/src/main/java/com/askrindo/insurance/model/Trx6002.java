package com.askrindo.insurance.model;

import lombok.Data;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "trx_6002")
@Data
public class Trx6002 {

    @Id
    private String id; // Foreign key from Trx

    private String shipId;
    private String shipType;
    private String shipConstruction;
    private String shipPurpose;
    private BigDecimal shipPrice;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "id", referencedColumnName = "id")
    private Trx trx;
}
