package com.enjoyu.admin.component.jmx;

import org.springframework.context.annotation.Bean;
import org.springframework.jmx.export.MBeanExporter;
import org.springframework.jmx.export.annotation.AnnotationJmxAttributeSource;
import org.springframework.jmx.export.assembler.MBeanInfoAssembler;
import org.springframework.jmx.export.assembler.MetadataMBeanInfoAssembler;
import org.springframework.jmx.export.metadata.JmxAttributeSource;
import org.springframework.jmx.export.naming.MetadataNamingStrategy;
import org.springframework.jmx.export.naming.ObjectNamingStrategy;

public class JmxServer {

    private static final Integer jmxPort = 8082;

    /**
     * api直接使用
     *
     * @throws Exception
     */
    public void initHtmlAdaptorServer() throws Exception {

//        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
//        HtmlAdaptorServer adapter = new HtmlAdaptorServer();
//
//        ObjectName adapterName = new ObjectName("system:name=test");
//        mbs.registerMBean(adapter, adapterName);
//        adapter.setMBeanServer(mbs);
//        adapter.setPort(jmxPort);
//        adapter.setMaxActiveClientCount(5);
//        adapter.start();
    }

    @Bean
    public JmxAttributeSource annotationJmxAttributeSource() {
        return new AnnotationJmxAttributeSource();
    }

    /**
     * will create management interface using annotation metadata
     *
     * @param annotationJmxAttributeSource
     * @return
     */
    @Bean
    public MBeanInfoAssembler assembler(JmxAttributeSource annotationJmxAttributeSource) {
        return new MetadataMBeanInfoAssembler(annotationJmxAttributeSource);
    }

    /**
     * will pick up ObjectName from annotation
     *
     * @param annotationJmxAttributeSource
     * @return
     */
    @Bean
    public ObjectNamingStrategy namingStrategy(JmxAttributeSource annotationJmxAttributeSource) {
        return new MetadataNamingStrategy(annotationJmxAttributeSource);
    }

    /**
     * 使用响应注解暴漏mbean
     * <code>
     *
     * @ManagedResource(objectName="CLARK:name=HtmlServerBean", description="Example for HtmlAdaptorServer")
     * public class HtmlServerBean {
     * @ManagedOperation(description="First Method for testing HtmlAdaptorServer")
     * public void methodOne(String param) {
     * }
     * @ManagedOperation(description="Secon Method for testing HtmlAdaptorServer")
     * public void methodTwo(String param1, String param2) {
     * }
     * }
     * </code>
     */
    @Bean
    public MBeanExporter exporter(MBeanInfoAssembler assembler, ObjectNamingStrategy namingStrategy) {
        MBeanExporter mBeanExporter = new MBeanExporter();
        mBeanExporter.setAssembler(assembler);
        mBeanExporter.setNamingStrategy(namingStrategy);
        mBeanExporter.setAutodetect(true);
        return mBeanExporter;
    }
}
