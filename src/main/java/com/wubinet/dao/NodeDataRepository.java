package com.wubinet.dao;

import com.wubinet.model.NodeData;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

public interface NodeDataRepository extends MongoRepository<NodeData, String> {

	List<NodeData> findLast50ByAddressOrderByTimestampAsc(String nodeAddress);

	List<NodeData> findAllByAddressAndTimestampGreaterThan(String address, Date startDate);
}
