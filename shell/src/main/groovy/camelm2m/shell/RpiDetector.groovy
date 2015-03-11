package camelm2m.shell

import org.slf4j.Logger
import zed.ssh.client.SshClient

import java.util.stream.Collectors

import static java.net.NetworkInterface.networkInterfaces
import static org.slf4j.LoggerFactory.getLogger

class RpiDetector {

    private static final int DEFAULT_PING_TIMEOUT = 500

    private final static Logger LOG = getLogger(RpiDetector.class)

    private final int timeout

    RpiDetector(int timeout) {
        this.timeout = timeout
    }

    RpiDetector() {
        this(DEFAULT_PING_TIMEOUT)
    }

    List<Inet4Address> detectReachableAddresses() {
        def networkInterface = networkInterfaces.toList().find { it.displayName.startsWith('wlan') }
        def networkAddress = networkInterface.interfaceAddresses.find { it.address.getHostAddress().length() < 15 }
        def broadcast = networkAddress.broadcast
        def address = broadcast.hostAddress
        def lastDot = address.lastIndexOf('.') + 1
        def addressBase = address.substring(0, lastDot)
        def addressesNumber = address.substring(lastDot).toInteger()
        (1..addressesNumber).collect { Inet4Address.getByName(addressBase + it) }.parallelStream().filter() {
            it.isReachable(timeout)
        }.collect(Collectors.toList())
    }

    List<Inet4Address> detectRpiAddresses() {
        detectReachableAddresses().findAll {
            try {
                new SshClient(it.hostAddress, 22, 'pi', 'raspberry').command('echo ping')
                return true
            } catch (Exception ex) {
                LOG.debug("Can't connect to the Raspberry Pi device ${it.hostAddress}.", ex)
                return false
            }
        }
    }

}
