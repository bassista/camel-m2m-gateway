package camelm2m.shell.endpoints

import org.springframework.boot.actuate.endpoint.AbstractEndpoint

class DeployEndpoint extends AbstractEndpoint {

    DeployEndpoint() {
        super("deploy")
    }

    @Override
    Object invoke() {
        return null
    }
}
