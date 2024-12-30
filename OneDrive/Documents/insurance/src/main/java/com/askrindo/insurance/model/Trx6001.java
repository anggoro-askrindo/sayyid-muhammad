package com.askrindo.insurance.model;

import lombok.Data;
import jakarta.persistence.*;
import java.util.Date;

import jakarta.persistence.CascadeType;

@Entity
@Table(name = "trx_6001")
@Data
public class Trx6001 {

    @Id
    private String id; // Foreign key from Trx

    private String heirName;
    private Date heirDob;
    private String heirSms;
    private String relation;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "id", referencedColumnName = "id")
    private Trx trx;
}
