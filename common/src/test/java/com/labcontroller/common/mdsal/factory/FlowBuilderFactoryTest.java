package com.labcontroller.common.mdsal.factory;

import org.testng.annotations.Test;

/**
 * @author xietaojie1992
 */
public class FlowBuilderFactoryTest {

    @Test
    public void test() {
        System.out.println(FlowBuilderFactory.createLldpFlowBuilder("000", 1234, false).build().toString());
    }
}
