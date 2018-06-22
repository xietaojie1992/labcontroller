package com.xietaojie.lab.impl.listeners;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.DataChangeListener;
import org.opendaylight.controller.md.sal.binding.api.ReadOnlyTransaction;
import org.opendaylight.controller.md.sal.common.api.data.AsyncDataBroker.DataChangeScope;
import org.opendaylight.controller.md.sal.common.api.data.AsyncDataChangeEvent;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.FlowCapableNode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.FlowCapableNodeConnector;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.port.rev130925.flow.capable.port.State;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.Nodes;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.node.NodeConnector;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.nodes.Node;
import org.opendaylight.yang.gen.v1.urn.tbd.params.xml.ns.yang.network.topology.rev131021.NetworkTopology;
import org.opendaylight.yang.gen.v1.urn.tbd.params.xml.ns.yang.network.topology.rev131021.network.topology.Topology;
import org.opendaylight.yang.gen.v1.urn.tbd.params.xml.ns.yang.network.topology.rev131021.network.topology.topology.Link;
import org.opendaylight.yangtools.concepts.ListenerRegistration;
import org.opendaylight.yangtools.yang.binding.DataObject;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;

/**
 * @author xietaojie1992
 */
public class DataStoreListener implements DataChangeListener, AutoCloseable {

    private Logger logger = LoggerFactory.getLogger(DataStoreListener.class);
    private DataBroker                               dataBroker;
    private ListenerRegistration<DataChangeListener> nodeRegistration;
    private ListenerRegistration<DataChangeListener> flowCapableNodeRegister;
    private ListenerRegistration<DataChangeListener> linkRegistration;
    private ListenerRegistration<DataChangeListener> stateRegistration;

    public DataStoreListener(DataBroker dataBroker) {
        this.dataBroker = dataBroker;
    }

    public void init() {
        // Listen to inventory node
        InstanceIdentifier<Node> nodeIID = InstanceIdentifier.builder(Nodes.class).child(Node.class).build();
        nodeRegistration = dataBroker.registerDataChangeListener(LogicalDatastoreType.OPERATIONAL, nodeIID, this, DataChangeScope.BASE);

        // Listen to flowCapableNode
        InstanceIdentifier<FlowCapableNode> flowCapableNodeIID = InstanceIdentifier.builder(Nodes.class).child(Node.class).augmentation(
                FlowCapableNode.class).build();
        flowCapableNodeRegister = dataBroker.registerDataChangeListener(LogicalDatastoreType.OPERATIONAL, flowCapableNodeIID, this,
                DataChangeScope.BASE);

        // Listen to link
        InstanceIdentifier<Link> linkIID = InstanceIdentifier.builder(NetworkTopology.class).child(Topology.class).child(Link.class)
                .build();
        linkRegistration = dataBroker.registerDataChangeListener(LogicalDatastoreType.OPERATIONAL, linkIID, this, DataChangeScope.BASE);

        // listen to port state
        InstanceIdentifier<State> portStateIID = InstanceIdentifier.create(Nodes.class).child(Node.class).child(NodeConnector.class)
                .augmentation(FlowCapableNodeConnector.class).child(State.class);
        stateRegistration = dataBroker.registerDataChangeListener(LogicalDatastoreType.OPERATIONAL, portStateIID, this,
                DataChangeScope.ONE);

        InstanceIdentifier<NodeConnector> connectorInstanceIdentifier = InstanceIdentifier.create(Nodes.class).child(Node.class).child(
                NodeConnector.class);
        stateRegistration = dataBroker.registerDataChangeListener(LogicalDatastoreType.OPERATIONAL, connectorInstanceIdentifier, this,
                DataChangeScope.ONE);
    }

    @Override
    public void onDataChanged(AsyncDataChangeEvent<InstanceIdentifier<?>, DataObject> change) {
        if (change == null) {
            logger.info("onDataChanged() ===>>> No Processing done as change event is null");
            return;
        }
        try {
            handleCreatedDate(change);
            handleUpdatedData(change);
            handleRemovedData(change);
        } catch (Exception e) {
            logger.error("DataChange process error", e);
        }
    }

    private void handleCreatedDate(AsyncDataChangeEvent<InstanceIdentifier<?>, DataObject> change) {

        Map<InstanceIdentifier<?>, DataObject> createdData = change.getCreatedData();
        for (InstanceIdentifier<?> key : createdData.keySet()) {
            if (FlowCapableNode.class == key.getTargetType()) {
                InstanceIdentifier<Node> nodeIID = key.firstIdentifierOf(Node.class);
                try {
                    Node node = dataBroker.newReadOnlyTransaction().read(LogicalDatastoreType.OPERATIONAL, nodeIID).get().get();
                    logger.info("Create node : " + node.getId().getValue());
                    FlowCapableNode flowCapableNode = node.getAugmentation(FlowCapableNode.class);
                } catch (Exception e) {
                    logger.error("Create node, get node info error", e);
                }
            } else if (Link.class == key.getTargetType()) {
                InstanceIdentifier<Link> linkIID = key.firstIdentifierOf(Link.class);
                Link link = (Link) createdData.get(linkIID);
                logger.info("Create link : " + link.getSource().getSourceTp().getValue() + " to " + link.getDestination().getDestTp()
                        .getValue());
            } else if (NodeConnector.class == key.getTargetType()) {
                InstanceIdentifier<NodeConnector> connectorInstanceIdentifier = key.firstIdentifierOf(NodeConnector.class);
                NodeConnector nodeConnector = (NodeConnector) createdData.get(connectorInstanceIdentifier);
                logger.info("Create port: {} {}", nodeConnector.getId().getValue(), (isPortLinkDown(nodeConnector) ? " Down" : " Up"));
            }
        }
    }

    private void handleUpdatedData(AsyncDataChangeEvent<InstanceIdentifier<?>, DataObject> change) {
        Map<InstanceIdentifier<?>, DataObject> updatedData = change.getUpdatedData();
        for (InstanceIdentifier<?> key : updatedData.keySet()) {
            if (State.class == key.getTargetType()) {
                InstanceIdentifier<Node> nodeIID = key.firstIdentifierOf(Node.class);
                InstanceIdentifier<NodeConnector> nodeConnectorIID = key.firstIdentifierOf(NodeConnector.class);
                try {
                    ReadOnlyTransaction transaction = dataBroker.newReadOnlyTransaction();
                    Node node = transaction.read(LogicalDatastoreType.OPERATIONAL, nodeIID).get().get();
                    NodeConnector nodeConnector = transaction.read(LogicalDatastoreType.OPERATIONAL, nodeConnectorIID).get().get();
                    logger.info("Update port : " + nodeConnector.getId().getValue() + (isPortLinkDown(nodeConnector) ? " Down" : " Up"));
                } catch (Exception e) {
                    logger.error("update port get info error", e);
                }
            }
        }
    }

    private void handleRemovedData(AsyncDataChangeEvent<InstanceIdentifier<?>, DataObject> change) {
        Map<InstanceIdentifier<?>, DataObject> originalData = change.getOriginalData();
        Set<InstanceIdentifier<?>> removedData = change.getRemovedPaths();
        for (InstanceIdentifier<?> key : removedData) {
            if (Node.class == key.getTargetType()) {
                InstanceIdentifier<Node> nodeIID = key.firstIdentifierOf(Node.class);
                Node node = (Node) originalData.get(nodeIID);
                logger.info("Remove node : " + node.getId().getValue());
            } else if (Link.class == key.getTargetType()) {
                InstanceIdentifier<Link> linkIID = key.firstIdentifierOf(Link.class);
                Link link = (Link) originalData.get(linkIID);
                logger.info("Remove link : " + link.getSource().getSourceTp().getValue() + " to " + link.getDestination().getDestTp()
                        .getValue());
            } else if (NodeConnector.class == key.getTargetType()) {
                InstanceIdentifier<NodeConnector> connectorInstanceIdentifier = key.firstIdentifierOf(NodeConnector.class);
                NodeConnector nodeConnector = (NodeConnector) originalData.get(connectorInstanceIdentifier);
                logger.info("Remove port: {}", nodeConnector.getId().getValue());
            }
        }
    }

    private boolean isPortLinkDown(NodeConnector nodeConnector) {
        FlowCapableNodeConnector flowCapableNodeConnector = nodeConnector.getAugmentation(FlowCapableNodeConnector.class);
        boolean isLinkDown = flowCapableNodeConnector.getState().isLinkDown();
        boolean isPortDown = flowCapableNodeConnector.getConfiguration().isPORTDOWN();
        logger.info(
                "Port:" + nodeConnector.getId().getValue() + " status: " + (isPortDown ? "PortDown " : "PortUp ") + (isLinkDown ? "LinkDown"
                        : "LinkUp"));
        return isLinkDown || isPortDown;
    }

    @Override
    public void close() throws Exception {
        if (nodeRegistration != null) {
            nodeRegistration.close();
        }
        if (flowCapableNodeRegister != null) {
            flowCapableNodeRegister.close();
        }
        if (linkRegistration != null) {
            linkRegistration.close();
        }
        if (stateRegistration != null) {
            stateRegistration.close();
        }
    }
}
