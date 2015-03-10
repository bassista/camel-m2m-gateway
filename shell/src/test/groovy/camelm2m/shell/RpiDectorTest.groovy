package camelm2m.shell

import org.junit.Assert
import org.junit.Test

class RpiDectorTest extends Assert {

    def rpiDetector = new RpiDetector()

    @Test
    void shouldReachDevice() {
        def address = rpiDetector.detectReachableAddresses().first()
        assertTrue(address.isReachable(5000))
    }

    @Test
    void shouldNotReachPiDevice() {
        def addresses = rpiDetector.detectRpiAddresses()
        assertEquals(0, addresses.size())
    }

}
