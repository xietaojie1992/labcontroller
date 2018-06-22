package com.xietaojie.lab.impl.listeners;

import org.opendaylight.controller.sal.binding.api.NotificationProviderService;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.nodes.Node;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.nodes.NodeKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.node.error.service.rev140410.BadActionErrorNotification;
import org.opendaylight.yang.gen.v1.urn.opendaylight.node.error.service.rev140410.BadInstructionErrorNotification;
import org.opendaylight.yang.gen.v1.urn.opendaylight.node.error.service.rev140410.BadMatchErrorNotification;
import org.opendaylight.yang.gen.v1.urn.opendaylight.node.error.service.rev140410.BadRequestErrorNotification;
import org.opendaylight.yang.gen.v1.urn.opendaylight.node.error.service.rev140410.ExperimenterErrorNotification;
import org.opendaylight.yang.gen.v1.urn.opendaylight.node.error.service.rev140410.FlowModErrorNotification;
import org.opendaylight.yang.gen.v1.urn.opendaylight.node.error.service.rev140410.GroupModErrorNotification;
import org.opendaylight.yang.gen.v1.urn.opendaylight.node.error.service.rev140410.HelloFailedErrorNotification;
import org.opendaylight.yang.gen.v1.urn.opendaylight.node.error.service.rev140410.MeterModErrorNotification;
import org.opendaylight.yang.gen.v1.urn.opendaylight.node.error.service.rev140410.NodeErrorListener;
import org.opendaylight.yang.gen.v1.urn.opendaylight.node.error.service.rev140410.PortModErrorNotification;
import org.opendaylight.yang.gen.v1.urn.opendaylight.node.error.service.rev140410.QueueOpErrorNotification;
import org.opendaylight.yang.gen.v1.urn.opendaylight.node.error.service.rev140410.RoleRequestErrorNotification;
import org.opendaylight.yang.gen.v1.urn.opendaylight.node.error.service.rev140410.SwitchConfigErrorNotification;
import org.opendaylight.yang.gen.v1.urn.opendaylight.node.error.service.rev140410.TableFeaturesErrorNotification;
import org.opendaylight.yang.gen.v1.urn.opendaylight.node.error.service.rev140410.TableModErrorNotification;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.common.types.rev130731.BadActionCode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.common.types.rev130731.BadInstructionCode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.common.types.rev130731.BadMatchCode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.common.types.rev130731.BadRequestCode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.common.types.rev130731.FlowModFailedCode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.common.types.rev130731.GroupModFailedCode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.common.types.rev130731.HelloFailedCode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.common.types.rev130731.MeterModFailedCode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.common.types.rev130731.PortModFailedCode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.common.types.rev130731.QueueOpFailedCode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.common.types.rev130731.RoleRequestFailedCode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.common.types.rev130731.SwitchConfigFailedCode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.common.types.rev130731.TableFeaturesFailedCode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.common.types.rev130731.TableModFailedCode;
import org.opendaylight.yangtools.concepts.ListenerRegistration;
import org.opendaylight.yangtools.yang.binding.NotificationListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xietaojie1992
 */
public class ErrorListener implements AutoCloseable, NodeErrorListener {

    private Logger logger = LoggerFactory.getLogger(ErrorListener.class);
    private NotificationProviderService                notificationProviderService;
    private ListenerRegistration<NotificationListener> errorListenerRegistration;

    public ErrorListener(NotificationProviderService notificationProviderService) {
        this.notificationProviderService = notificationProviderService;
    }

    public void init() {
        errorListenerRegistration = notificationProviderService.registerNotificationListener(this);
    }

    @Override
    public void close() throws Exception {
        errorListenerRegistration.close();
    }

    @Override
    public void onBadActionErrorNotification(BadActionErrorNotification notification) {
        NodeKey nodeKey = notification.getNode().getValue().firstKeyOf(Node.class);
        logger.error("node={}, TransactionId={}, Error=[type={}, code={}]", nodeKey.getId().getValue(),
                notification.getTransactionId().getValue(), notification.getType().name(),
                BadActionCode.forValue(notification.getCode()).name());
    }

    @Override
    public void onBadInstructionErrorNotification(BadInstructionErrorNotification notification) {
        NodeKey nodeKey = notification.getNode().getValue().firstKeyOf(Node.class);
        logger.error("node={}, TransactionId={}, Error=[type={}, code={}]", nodeKey.getId().getValue(),
                notification.getTransactionId().getValue(), notification.getType().name(),
                BadInstructionCode.forValue(notification.getCode()).name());
    }

    @Override
    public void onBadMatchErrorNotification(BadMatchErrorNotification notification) {
        NodeKey nodeKey = notification.getNode().getValue().firstKeyOf(Node.class);
        logger.error("node={}, TransactionId={}, Error=[type={}, code={}]", nodeKey.getId().getValue(),
                notification.getTransactionId().getValue(), notification.getType().name(),
                BadMatchCode.forValue(notification.getCode()).name());
    }

    @Override
    public void onBadRequestErrorNotification(BadRequestErrorNotification notification) {
        NodeKey nodeKey = notification.getNode().getValue().firstKeyOf(Node.class);
        logger.error("node={}, TransactionId={}, Error=[type={}, code={}]", nodeKey.getId().getValue(),
                notification.getTransactionId().getValue(), notification.getType().name(),
                BadRequestCode.forValue(notification.getCode()).name());
    }

    @Override
    public void onExperimenterErrorNotification(ExperimenterErrorNotification notification) {
        NodeKey nodeKey = notification.getNode().getValue().firstKeyOf(Node.class);
        logger.error("node={}, TransactionId={}, Error=[type={}, code={}]", nodeKey.getId().getValue(),
                notification.getTransactionId().getValue(), notification.getType().name(), notification.getCode());
    }

    @Override
    public void onFlowModErrorNotification(FlowModErrorNotification notification) {
        NodeKey nodeKey = notification.getNode().getValue().firstKeyOf(Node.class);
        logger.error("node={}, TransactionId={}, Error=[type={}, code={}]", nodeKey.getId().getValue(),
                notification.getTransactionId().getValue(), notification.getType().name(),
                FlowModFailedCode.forValue(notification.getCode()).name());
    }

    @Override
    public void onGroupModErrorNotification(GroupModErrorNotification notification) {
        NodeKey nodeKey = notification.getNode().getValue().firstKeyOf(Node.class);
        logger.error("node={}, TransactionId={}, Error=[type={}, code={}]", nodeKey.getId().getValue(),
                notification.getTransactionId().getValue(), notification.getType().name(),
                GroupModFailedCode.forValue(notification.getCode()).name());
    }

    @Override
    public void onHelloFailedErrorNotification(HelloFailedErrorNotification notification) {
        NodeKey nodeKey = notification.getNode().getValue().firstKeyOf(Node.class);
        logger.error("node={}, TransactionId={}, Error=[type={}, code={}]", nodeKey.getId().getValue(),
                notification.getTransactionId().getValue(), notification.getType().name(),
                HelloFailedCode.forValue(notification.getCode()).name());
    }

    @Override
    public void onMeterModErrorNotification(MeterModErrorNotification notification) {
        NodeKey nodeKey = notification.getNode().getValue().firstKeyOf(Node.class);
        logger.error("node={}, TransactionId={}, Error=[type={}, code={}]", nodeKey.getId().getValue(),
                notification.getTransactionId().getValue(), notification.getType().name(),
                MeterModFailedCode.forValue(notification.getCode()).name());
    }

    @Override
    public void onPortModErrorNotification(PortModErrorNotification notification) {
        NodeKey nodeKey = notification.getNode().getValue().firstKeyOf(Node.class);
        logger.error("node={}, TransactionId={}, Error=[type={}, code={}]", nodeKey.getId().getValue(),
                notification.getTransactionId().getValue(), notification.getType().name(),
                PortModFailedCode.forValue(notification.getCode()).name());
    }

    @Override
    public void onQueueOpErrorNotification(QueueOpErrorNotification notification) {
        NodeKey nodeKey = notification.getNode().getValue().firstKeyOf(Node.class);
        logger.error("node={}, TransactionId={}, Error=[type={}, code={}]", nodeKey.getId().getValue(),
                notification.getTransactionId().getValue(), notification.getType().name(),
                QueueOpFailedCode.forValue(notification.getCode()).name());
    }

    @Override
    public void onRoleRequestErrorNotification(RoleRequestErrorNotification notification) {
        NodeKey nodeKey = notification.getNode().getValue().firstKeyOf(Node.class);
        logger.error("node={}, TransactionId={}, Error=[type={}, code={}]", nodeKey.getId().getValue(),
                notification.getTransactionId().getValue(), notification.getType().name(),
                RoleRequestFailedCode.forValue(notification.getCode()).name());
    }

    @Override
    public void onSwitchConfigErrorNotification(SwitchConfigErrorNotification notification) {
        NodeKey nodeKey = notification.getNode().getValue().firstKeyOf(Node.class);
        logger.error("node={}, TransactionId={}, Error=[type={}, code={}]", nodeKey.getId().getValue(),
                notification.getTransactionId().getValue(), notification.getType().name(),
                SwitchConfigFailedCode.forValue(notification.getCode()).name());
    }

    @Override
    public void onTableFeaturesErrorNotification(TableFeaturesErrorNotification notification) {
        NodeKey nodeKey = notification.getNode().getValue().firstKeyOf(Node.class);
        logger.error("node={}, TransactionId={}, Error=[type={}, code={}]", nodeKey.getId().getValue(),
                notification.getTransactionId().getValue(), notification.getType().name(),
                TableFeaturesFailedCode.forValue(notification.getCode()).name());
    }

    @Override
    public void onTableModErrorNotification(TableModErrorNotification notification) {
        NodeKey nodeKey = notification.getNode().getValue().firstKeyOf(Node.class);
        logger.error("node={}, TransactionId={}, Error=[type={}, code={}]", nodeKey.getId().getValue(),
                notification.getTransactionId().getValue(), notification.getType().name(),
                TableModFailedCode.forValue(notification.getCode()).name());
    }
}
