package org.apache.camel.component.paho;

import org.apache.camel.Endpoint;
import org.apache.camel.impl.DefaultComponent;

import java.util.Map;

public class PahoComponent extends DefaultComponent {

    @Override
    protected Endpoint createEndpoint(String uri, String s1, Map<String, Object> options) throws Exception {
        PahoEndpoint pahoEndpoint = new PahoEndpoint(uri, this);
        setProperties(pahoEndpoint, options);
        return pahoEndpoint;
    }

}