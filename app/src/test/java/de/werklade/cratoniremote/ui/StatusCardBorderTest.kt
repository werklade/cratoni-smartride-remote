package de.werklade.cratoniremote.ui

import de.werklade.cratoniremote.ble.BleConnectionState
import de.werklade.cratoniremote.data.PendingConfirmation
import de.werklade.cratoniremote.data.RemoteState
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/** The success edge of the status card only marks a confirmed, connected remote. */
class StatusCardBorderTest {

    private val connected = RemoteState(connection = BleConnectionState.Connected)

    @Test
    fun confirmedAndConnectedGetsTheSuccessEdge() {
        assertTrue(isConnectedAndConfigured(remoteConfigured = true, state = connected))
    }

    @Test
    fun awaitingKeyPressIsNotShownAsSuccess() {
        val pending = connected.copy(
            pendingConfirmation = PendingConfirmation(associationId = 1, label = "Remote", deadlineElapsedMs = 30_000L),
        )
        assertFalse(isConnectedAndConfigured(remoteConfigured = true, state = pending))
    }

    @Test
    fun notSetUpOrWaitingForBluetoothIsNotShownAsSuccess() {
        assertFalse(isConnectedAndConfigured(remoteConfigured = false, state = connected))
        assertFalse(isConnectedAndConfigured(remoteConfigured = true, state = connected.copy(waitingForBluetooth = true)))
    }
}
