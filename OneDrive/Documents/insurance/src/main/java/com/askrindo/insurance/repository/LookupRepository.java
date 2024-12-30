package com.askrindo.insurance.repository;

import com.askrindo.insurance.model.Lookup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LookupRepository extends JpaRepository<Lookup, String> {
}
