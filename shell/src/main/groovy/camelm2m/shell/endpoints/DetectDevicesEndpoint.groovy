package camelm2m.shell.endpoints

import camelm2m.shell.utils.RpiDetector
import org.springframework.boot.actuate.endpoint.AbstractEndpoint

class DetectDevicesEndpoint extends AbstractEndpoint<List<String>> {

    private static final int INDEX_OF_LEADING_SLASH = 1

    DetectDevicesEndpoint() {
        super("detect-devices", false, true)
    }

    @Override
    List<String> invoke() {
        new RpiDetector().detectReachableAddresses().collect {
            it.toString().substring(INDEX_OF_LEADING_SLASH)
        }
    }
}
