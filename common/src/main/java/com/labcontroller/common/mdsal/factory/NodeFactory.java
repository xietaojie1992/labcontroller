package com.labcontroller.common.mdsal.factory;

import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeRef;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.Nodes;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.nodes.Node;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.nodes.NodeBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.nodes.NodeKey;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;

/**
 * @author xietaojie1992
 */
public class NodeFactory {

    public static NodeBuilder createNode(String dpid) {
        return new NodeBuilder().setId(new NodeId(dpid)).setKey(new NodeKey(new NodeId(dpid)));
    }

    public static NodeRef createNodeRef(String dpid) {
        return new NodeRef(InstanceIdentifier.create(Nodes.class).child(Node.class, new NodeKey(new NodeId(dpid))));
    }

    public static NodeRef createNodeRef(NodeBuilder nodeBuilder) {
        return new NodeRef(InstanceIdentifier.create(Nodes.class).child(Node.class, nodeBuilder.getKey()));
    }

    public static InstanceIdentifier<Node> createNodeIID(final NodeBuilder node) {
        return InstanceIdentifier.create(Nodes.class).child(Node.class, node.getKey());
    }

    public static InstanceIdentifier<Node> createNodeIID(String dpid) {
        return InstanceIdentifier.create(Nodes.class).child(Node.class, new NodeKey(new NodeId(dpid)));
    }
}
