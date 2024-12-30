package com.askrindo.insurance.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "m_lookup", schema = "public")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lookup {

    @Id
    @Column(name = "lookup_key", nullable = false, length = 150)
    private String lookupKey;

    @Column(name = "lookup_group", nullable = false, length = 50)
    private String lookupGroup;

    @Column(name = "key_only", nullable = false, length = 50)
    private String keyOnly;

    @Column(name = "label", nullable = false, length = 255)
    private String label;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @Column(name = "version", nullable = false)
    private int version;

    @Column(name = "created_by", nullable = false, length = 100)
    private String createdBy;

    @CreationTimestamp
    @Column(name = "created_date", nullable = false, updatable = false)
    private Timestamp createdDate;

    @Column(name = "modified_by", length = 100)
    private String modifiedBy;

    @UpdateTimestamp
    @Column(name = "modified_date")
    private Timestamp modifiedDate;
}
