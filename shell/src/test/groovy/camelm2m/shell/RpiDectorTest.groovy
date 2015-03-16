package camelm2m.shell

import camelm2m.shell.utils.RpiDetector
import org.junit.Assert
import org.junit.Test

import static org.junit.Assume.assumeFalse

class RpiDectorTest extends Assert {

    def rpiDetector = new RpiDetector()

    @Test
    void shouldReachDevice() {
        // Given
        def addresses = rpiDetector.detectReachableAddresses()
        assumeFalse('The test should be executed only when the proper network interfaces are available.',
                addresses.isEmpty())

        // When
        def address = addresses.first()

        // Then
        assertTrue(address.isReachable(5000))
    }

    @Test
    void shouldNotReachPiDevice() {
        def addresses = rpiDetector.detectRpiAddresses()
        assertEquals(0, addresses.size())
    }

}
