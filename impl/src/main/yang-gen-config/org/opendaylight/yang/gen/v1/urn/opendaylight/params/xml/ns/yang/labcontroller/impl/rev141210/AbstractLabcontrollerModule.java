/*
* Generated file
*
* Generated from: yang module name: labcontroller-impl yang module local name: labcontroller
* Generated by: org.opendaylight.controller.config.yangjmxgenerator.plugin.JMXGenerator
* Generated at: Wed Mar 21 13:56:02 CST 2018
*
* Do not modify this file unless it is present under src/main directory
*/
package org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.labcontroller.impl.rev141210;
@org.opendaylight.yangtools.yang.binding.annotations.ModuleQName(namespace = "urn:opendaylight:params:xml:ns:yang:labcontroller:impl", name = "labcontroller-impl", revision = "2014-12-10")

public abstract class AbstractLabcontrollerModule extends org.opendaylight.controller.config.spi.AbstractModule<AbstractLabcontrollerModule> implements org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.labcontroller.impl.rev141210.LabcontrollerModuleMXBean {
    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.labcontroller.impl.rev141210.AbstractLabcontrollerModule.class);

    //attributes start

    public static final org.opendaylight.controller.config.api.JmxAttribute rpcRegistryJmxAttribute = new org.opendaylight.controller.config.api.JmxAttribute("RpcRegistry");
    private javax.management.ObjectName rpcRegistry; // mandatory

    public static final org.opendaylight.controller.config.api.JmxAttribute niciraExtensionCodecRegistratorJmxAttribute = new org.opendaylight.controller.config.api.JmxAttribute("NiciraExtensionCodecRegistrator");
    private javax.management.ObjectName niciraExtensionCodecRegistrator; // mandatory

    public static final org.opendaylight.controller.config.api.JmxAttribute openflowPluginExtensionRegistryJmxAttribute = new org.opendaylight.controller.config.api.JmxAttribute("OpenflowPluginExtensionRegistry");
    private javax.management.ObjectName openflowPluginExtensionRegistry; // mandatory

    public static final org.opendaylight.controller.config.api.JmxAttribute dataBrokerJmxAttribute = new org.opendaylight.controller.config.api.JmxAttribute("DataBroker");
    private javax.management.ObjectName dataBroker; // optional

    public static final org.opendaylight.controller.config.api.JmxAttribute brokerJmxAttribute = new org.opendaylight.controller.config.api.JmxAttribute("Broker");
    private javax.management.ObjectName broker; // mandatory

    public static final org.opendaylight.controller.config.api.JmxAttribute notificationServiceJmxAttribute = new org.opendaylight.controller.config.api.JmxAttribute("NotificationService");
    private javax.management.ObjectName notificationService; // mandatory

    public static final org.opendaylight.controller.config.api.JmxAttribute openflowSwitchConnectionProviderJmxAttribute = new org.opendaylight.controller.config.api.JmxAttribute("OpenflowSwitchConnectionProvider");
    private javax.management.ObjectName openflowSwitchConnectionProvider; // mandatory

    //attributes end

    public AbstractLabcontrollerModule(org.opendaylight.controller.config.api.ModuleIdentifier identifier,org.opendaylight.controller.config.api.DependencyResolver dependencyResolver) {
        super(identifier, dependencyResolver);
    }

    public AbstractLabcontrollerModule(org.opendaylight.controller.config.api.ModuleIdentifier identifier,org.opendaylight.controller.config.api.DependencyResolver dependencyResolver,AbstractLabcontrollerModule oldModule,java.lang.AutoCloseable oldInstance) {
        super(identifier, dependencyResolver, oldModule, oldInstance);
    }

    @Override
    public void validate() {
        dependencyResolver.validateDependency(org.opendaylight.controller.config.yang.md.sal.binding.RpcProviderRegistryServiceInterface.class, rpcRegistry, rpcRegistryJmxAttribute);
        dependencyResolver.validateDependency(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.openflowplugin.ofjava.nx.api.config.rev140711.NiciraExtensionCodecRegistratorServiceInterface.class, niciraExtensionCodecRegistrator, niciraExtensionCodecRegistratorJmxAttribute);
        dependencyResolver.validateDependency(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.openflowplugin.extension.api.rev150425.OpenFlowPluginExtensionRegistratorProviderServiceInterface.class, openflowPluginExtensionRegistry, openflowPluginExtensionRegistryJmxAttribute);
        if(dataBroker != null) {
            dependencyResolver.validateDependency(org.opendaylight.controller.config.yang.md.sal.binding.DataBrokerServiceInterface.class, dataBroker, dataBrokerJmxAttribute);
        }
        dependencyResolver.validateDependency(org.opendaylight.controller.config.yang.md.sal.binding.BindingAwareBrokerServiceInterface.class, broker, brokerJmxAttribute);
        dependencyResolver.validateDependency(org.opendaylight.controller.config.yang.md.sal.binding.NotificationProviderServiceServiceInterface.class, notificationService, notificationServiceJmxAttribute);
        dependencyResolver.validateDependency(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.openflow._switch.connection.provider.rev140328.SwitchConnectionProviderServiceInterface.class, openflowSwitchConnectionProvider, openflowSwitchConnectionProviderJmxAttribute);

        customValidation();
    }

    protected void customValidation() {
    }

    private org.opendaylight.controller.sal.binding.api.RpcProviderRegistry rpcRegistryDependency;
    protected final org.opendaylight.controller.sal.binding.api.RpcProviderRegistry getRpcRegistryDependency(){
        return rpcRegistryDependency;
    }private org.opendaylight.openflowjava.nx.api.NiciraExtensionCodecRegistrator niciraExtensionCodecRegistratorDependency;
    protected final org.opendaylight.openflowjava.nx.api.NiciraExtensionCodecRegistrator getNiciraExtensionCodecRegistratorDependency(){
        return niciraExtensionCodecRegistratorDependency;
    }private org.opendaylight.openflowplugin.extension.api.OpenFlowPluginExtensionRegistratorProvider openflowPluginExtensionRegistryDependency;
    protected final org.opendaylight.openflowplugin.extension.api.OpenFlowPluginExtensionRegistratorProvider getOpenflowPluginExtensionRegistryDependency(){
        return openflowPluginExtensionRegistryDependency;
    }private org.opendaylight.controller.md.sal.binding.api.DataBroker dataBrokerDependency;
    protected final org.opendaylight.controller.md.sal.binding.api.DataBroker getDataBrokerDependency(){
        return dataBrokerDependency;
    }private org.opendaylight.controller.sal.binding.api.BindingAwareBroker brokerDependency;
    protected final org.opendaylight.controller.sal.binding.api.BindingAwareBroker getBrokerDependency(){
        return brokerDependency;
    }private org.opendaylight.controller.sal.binding.api.NotificationProviderService notificationServiceDependency;
    protected final org.opendaylight.controller.sal.binding.api.NotificationProviderService getNotificationServiceDependency(){
        return notificationServiceDependency;
    }private org.opendaylight.openflowjava.protocol.spi.connection.SwitchConnectionProvider openflowSwitchConnectionProviderDependency;
    protected final org.opendaylight.openflowjava.protocol.spi.connection.SwitchConnectionProvider getOpenflowSwitchConnectionProviderDependency(){
        return openflowSwitchConnectionProviderDependency;
    }

    protected final void resolveDependencies() {
        rpcRegistryDependency = dependencyResolver.resolveInstance(org.opendaylight.controller.sal.binding.api.RpcProviderRegistry.class, rpcRegistry, rpcRegistryJmxAttribute);
        openflowPluginExtensionRegistryDependency = dependencyResolver.resolveInstance(org.opendaylight.openflowplugin.extension.api.OpenFlowPluginExtensionRegistratorProvider.class, openflowPluginExtensionRegistry, openflowPluginExtensionRegistryJmxAttribute);
        if (dataBroker!=null) {
            dataBrokerDependency = dependencyResolver.resolveInstance(org.opendaylight.controller.md.sal.binding.api.DataBroker.class, dataBroker, dataBrokerJmxAttribute);
        }
        niciraExtensionCodecRegistratorDependency = dependencyResolver.resolveInstance(org.opendaylight.openflowjava.nx.api.NiciraExtensionCodecRegistrator.class, niciraExtensionCodecRegistrator, niciraExtensionCodecRegistratorJmxAttribute);
        openflowSwitchConnectionProviderDependency = dependencyResolver.resolveInstance(org.opendaylight.openflowjava.protocol.spi.connection.SwitchConnectionProvider.class, openflowSwitchConnectionProvider, openflowSwitchConnectionProviderJmxAttribute);
        brokerDependency = dependencyResolver.resolveInstance(org.opendaylight.controller.sal.binding.api.BindingAwareBroker.class, broker, brokerJmxAttribute);
        notificationServiceDependency = dependencyResolver.resolveInstance(org.opendaylight.controller.sal.binding.api.NotificationProviderService.class, notificationService, notificationServiceJmxAttribute);
    }

    public boolean canReuseInstance(AbstractLabcontrollerModule oldModule){
        // allow reusing of old instance if no parameters was changed
        return isSame(oldModule);
    }

    public java.lang.AutoCloseable reuseInstance(java.lang.AutoCloseable oldInstance){
        // implement if instance reuse should be supported. Override canReuseInstance to change the criteria.
        return oldInstance;
    }

    public boolean isSame(AbstractLabcontrollerModule other) {
        if (other == null) {
            throw new IllegalArgumentException("Parameter 'other' is null");
        }
        if (!java.util.Objects.deepEquals(rpcRegistry, other.rpcRegistry)) {
            return false;
        }
        if(rpcRegistry!= null) {
            if (!dependencyResolver.canReuseDependency(rpcRegistry, rpcRegistryJmxAttribute)) { // reference to dependency must be reusable as well
                return false;
            }
        }
        if (!java.util.Objects.deepEquals(niciraExtensionCodecRegistrator, other.niciraExtensionCodecRegistrator)) {
            return false;
        }
        if(niciraExtensionCodecRegistrator!= null) {
            if (!dependencyResolver.canReuseDependency(niciraExtensionCodecRegistrator, niciraExtensionCodecRegistratorJmxAttribute)) { // reference to dependency must be reusable as well
                return false;
            }
        }
        if (!java.util.Objects.deepEquals(openflowPluginExtensionRegistry, other.openflowPluginExtensionRegistry)) {
            return false;
        }
        if(openflowPluginExtensionRegistry!= null) {
            if (!dependencyResolver.canReuseDependency(openflowPluginExtensionRegistry, openflowPluginExtensionRegistryJmxAttribute)) { // reference to dependency must be reusable as well
                return false;
            }
        }
        if (!java.util.Objects.deepEquals(dataBroker, other.dataBroker)) {
            return false;
        }
        if(dataBroker!= null) {
            if (!dependencyResolver.canReuseDependency(dataBroker, dataBrokerJmxAttribute)) { // reference to dependency must be reusable as well
                return false;
            }
        }
        if (!java.util.Objects.deepEquals(broker, other.broker)) {
            return false;
        }
        if(broker!= null) {
            if (!dependencyResolver.canReuseDependency(broker, brokerJmxAttribute)) { // reference to dependency must be reusable as well
                return false;
            }
        }
        if (!java.util.Objects.deepEquals(notificationService, other.notificationService)) {
            return false;
        }
        if(notificationService!= null) {
            if (!dependencyResolver.canReuseDependency(notificationService, notificationServiceJmxAttribute)) { // reference to dependency must be reusable as well
                return false;
            }
        }
        if (!java.util.Objects.deepEquals(openflowSwitchConnectionProvider, other.openflowSwitchConnectionProvider)) {
            return false;
        }
        if(openflowSwitchConnectionProvider!= null) {
            if (!dependencyResolver.canReuseDependency(openflowSwitchConnectionProvider, openflowSwitchConnectionProviderJmxAttribute)) { // reference to dependency must be reusable as well
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean equals(Object o) {
    if (this == o) { return true; }
    if (o == null || getClass() != o.getClass()) { return false; }
        AbstractLabcontrollerModule that = (AbstractLabcontrollerModule) o;
        return identifier.equals(that.identifier);
    }

    @Override
    public int hashCode() {
        return identifier.hashCode();
    }

    // getters and setters
    @Override
    public javax.management.ObjectName getRpcRegistry() {
        return rpcRegistry;
    }

    @Override
    @org.opendaylight.controller.config.api.annotations.RequireInterface(value = org.opendaylight.controller.config.yang.md.sal.binding.RpcProviderRegistryServiceInterface.class)
    public void setRpcRegistry(javax.management.ObjectName rpcRegistry) {
        this.rpcRegistry = rpcRegistry;
    }

    @Override
    public javax.management.ObjectName getNiciraExtensionCodecRegistrator() {
        return niciraExtensionCodecRegistrator;
    }

    @Override
    @org.opendaylight.controller.config.api.annotations.RequireInterface(value = org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.openflowplugin.ofjava.nx.api.config.rev140711.NiciraExtensionCodecRegistratorServiceInterface.class)
    public void setNiciraExtensionCodecRegistrator(javax.management.ObjectName niciraExtensionCodecRegistrator) {
        this.niciraExtensionCodecRegistrator = niciraExtensionCodecRegistrator;
    }

    @Override
    public javax.management.ObjectName getOpenflowPluginExtensionRegistry() {
        return openflowPluginExtensionRegistry;
    }

    @Override
    @org.opendaylight.controller.config.api.annotations.RequireInterface(value = org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.openflowplugin.extension.api.rev150425.OpenFlowPluginExtensionRegistratorProviderServiceInterface.class)
    public void setOpenflowPluginExtensionRegistry(javax.management.ObjectName openflowPluginExtensionRegistry) {
        this.openflowPluginExtensionRegistry = openflowPluginExtensionRegistry;
    }

    @Override
    public javax.management.ObjectName getDataBroker() {
        return dataBroker;
    }

    @Override
    @org.opendaylight.controller.config.api.annotations.RequireInterface(value = org.opendaylight.controller.config.yang.md.sal.binding.DataBrokerServiceInterface.class)
    public void setDataBroker(javax.management.ObjectName dataBroker) {
        this.dataBroker = dataBroker;
    }

    @Override
    public javax.management.ObjectName getBroker() {
        return broker;
    }

    @Override
    @org.opendaylight.controller.config.api.annotations.RequireInterface(value = org.opendaylight.controller.config.yang.md.sal.binding.BindingAwareBrokerServiceInterface.class)
    public void setBroker(javax.management.ObjectName broker) {
        this.broker = broker;
    }

    @Override
    public javax.management.ObjectName getNotificationService() {
        return notificationService;
    }

    @Override
    @org.opendaylight.controller.config.api.annotations.RequireInterface(value = org.opendaylight.controller.config.yang.md.sal.binding.NotificationProviderServiceServiceInterface.class)
    public void setNotificationService(javax.management.ObjectName notificationService) {
        this.notificationService = notificationService;
    }

    @Override
    public javax.management.ObjectName getOpenflowSwitchConnectionProvider() {
        return openflowSwitchConnectionProvider;
    }

    @Override
    @org.opendaylight.controller.config.api.annotations.RequireInterface(value = org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.openflow._switch.connection.provider.rev140328.SwitchConnectionProviderServiceInterface.class)
    public void setOpenflowSwitchConnectionProvider(javax.management.ObjectName openflowSwitchConnectionProvider) {
        this.openflowSwitchConnectionProvider = openflowSwitchConnectionProvider;
    }

    public org.slf4j.Logger getLogger() {
        return LOGGER;
    }

}
