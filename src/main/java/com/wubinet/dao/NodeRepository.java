package com.wubinet.dao;

import com.wubinet.model.Node;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NodeRepository extends MongoRepository<Node, String> {

}
