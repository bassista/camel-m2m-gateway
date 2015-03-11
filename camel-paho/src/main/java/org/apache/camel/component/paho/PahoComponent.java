package org.apache.camel.component.paho;

import org.apache.camel.Endpoint;
import org.apache.camel.impl.DefaultComponent;
import org.apache.camel.util.IntrospectionSupport;

import java.util.Map;

public class PahoComponent extends DefaultComponent {

    @Override
    protected Endpoint createEndpoint(String uri, String s1, Map<String, Object> map) throws Exception {
        PahoEndpoint pahoEndpoint = new PahoEndpoint(uri, this);
        IntrospectionSupport.setProperties(this.getCamelContext().getTypeConverter(), pahoEndpoint, map);
        return pahoEndpoint;
    }

}