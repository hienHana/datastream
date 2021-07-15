package com.meraki.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meraki.model.DataStat;
import com.meraki.model.DataStat_ID;

public interface DataStatRepository extends JpaRepository<DataStat, DataStat_ID> {

}
