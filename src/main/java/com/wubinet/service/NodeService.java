package com.wubinet.service;

import com.wubinet.dao.NodeDataRepository;
import com.wubinet.dao.NodeRepository;
import com.wubinet.model.Node;
import com.wubinet.model.NodeData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class NodeService {

	@Autowired private NodeRepository nodeRepository;
	@Autowired private NodeDataRepository nodeDataRepository;

	public List<Node> loadNodes() {
		List<Node> nodes = nodeRepository.findAll();
		List<Node> sortedNodes = nodes.stream().
				sorted(Comparator.comparing(node -> node.getAddress())).
				collect(toList());
		return sortedNodes;
	}

	public Node loadNode(String address) {
		return nodeRepository.findOne(address);
	}

	@Transactional
	public void save(Node node, NodeData nodeData) {
		nodeRepository.save(node);
		nodeDataRepository.save(nodeData);
	}
}
