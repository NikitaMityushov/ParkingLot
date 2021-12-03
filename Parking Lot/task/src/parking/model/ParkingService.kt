package parking.model

import parking.model.interfaces.ParkingInterface
import parking.model.messages.ParkingResponceMessage
import parking.model.messages.ParseResultMessage
import parking.model.messages.TransactionResponce
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock

/**
 * Parking service, extends ParkingInterface
 *
 */
class ParkingService: ParkingInterface {
    /**
     * Represents a map of spots numbers and spots itself
     */
    private val spots = ConcurrentHashMap<Int, Spot>()

    /**
     * Represents flag for checking of ParkingService creation
     */
    @Volatile
    private var isCreated: Boolean = false
    // ref counter
    /**
     * Represents number of occupied spots
     */
    @Volatile
    private var occupied: Int = 0

    /**
     * Standard lock for synchronization
     */
    private val lock: Lock = ReentrantLock()

    override fun park(parseNumberMessage: ParseResultMessage): ParkingResponceMessage {
        synchronized(lock) {
            for(lot in spots) {
                if (lot.value.spotStatus == SpotStatus.FREE) {
                    spots[lot.key] = Spot(
                        spotStatus = SpotStatus.OCCUPIED,
                        carNumber = parseNumberMessage.carNumber,
                        carColor = parseNumberMessage.carColor
                    )

                    ++occupied

                    return ParkingResponceMessage(
                        transactionResponce = TransactionResponce.DONE,
                        lot = lot.key
                    )

                }
            }
        }

        return ParkingResponceMessage(
            transactionResponce = TransactionResponce.NO_AVAILABLE_SPOTS
        )
    }

    override fun leave(lot: Int): Boolean {
        synchronized(lock) {
            if (spots[lot]?.spotStatus != SpotStatus.FREE) {
                spots[lot] = Spot(
                    spotStatus = SpotStatus.FREE,
                )
                --occupied
                return true
            }
            return false
        }
    }

    override fun isEmpty(): Boolean {
        synchronized(lock) {
            return occupied == 0
        }
    }

    override fun create(numberOfSpots: Int): Boolean {
        synchronized(lock) {
            // check if no positive
            if (numberOfSpots < 1) {
                return false
            }

            spots.clear()
            occupied = 0

            for (i in 1..numberOfSpots) {
                spots[i] = Spot(
                    spotStatus = SpotStatus.FREE
                )
            }
            isCreated = true
            return true
        }
    }

    override fun isCreated(): Boolean {
        synchronized(lock) {
            return isCreated
        }
    }

    override fun status(): String {
        if (!isCreated) {
            return "Sorry, a parking lot has not been created."
        }
        if (isEmpty()) {
            return "Parking lot is empty."
        }
        val builder = StringBuilder()
        spots.filter { it.value.spotStatus != SpotStatus.FREE }
            .forEach {
                builder.append("${it.key} ${it.value.carNumber} ${it.value.carColor}\n")
            }
        return builder.toString().trim()
    }

    override fun regByColor(color: String): List<String> {
        val response = mutableListOf<String>()

        spots.filter {
            it.value.carColor.equals(color, ignoreCase = true)
        }.forEach {
            response.add(it.value.carNumber)
        }

        return response
    }

    override fun spotByColor(color: String): List<Int> {
        val response = mutableListOf<Int>()

        spots.filter {
            it.value.carColor.equals(color, ignoreCase = true)
        }.forEach {
            response.add(it.key)
        }

        return response
    }

    override fun spotByReg(carNumber: String): List<Int> {
        val response = mutableListOf<Int>()

        spots.filter {
            it.value.carNumber == carNumber
        }.forEach {
            response.add(it.key)
        }

        return response
    }
}

// TODO: 01.12.2021 (Make full thread safe)