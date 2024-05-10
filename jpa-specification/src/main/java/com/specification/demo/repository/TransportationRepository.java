package com.specification.demo.repository;

import com.specification.demo.model.TransportationRequestModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TransportationRepository
    extends JpaRepository<TransportationRequestModel, Long>,
        JpaSpecificationExecutor<TransportationRequestModel> {}
