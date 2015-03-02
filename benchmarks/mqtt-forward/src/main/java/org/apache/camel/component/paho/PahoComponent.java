package org.apache.camel.component.paho;

import org.apache.camel.Endpoint;
import org.apache.camel.impl.DefaultComponent;

import java.util.Map;

public class PahoComponent extends DefaultComponent {

    @Override
    protected Endpoint createEndpoint(String uri, String s1, Map<String, Object> map) throws Exception {
        return new PahoEndpoint(uri, this);
    }

}