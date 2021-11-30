package parking.model

import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock

interface ParkingInterface {
    fun park(parseNumberMessage: ParseResultMessage): ParkingResponceMessage
    fun leave(lot: Int): Boolean
    fun isEmpty(): Boolean
    fun create(numberOfSpots: Int): Boolean
    fun isCreated(): Boolean
    fun status(): String
    fun regByColor(color: String): List<String>
    fun spotByColor(color: String): List<Int>
    fun spotByReg(carNumber: String): List<Int>
}

enum class ParkingStatus {
    FREE, OCCUPIED
}

enum class TransactionResult {
    DONE, NO_AVAILABLE_SPOTS
}

// dto
data class ParkingResponceMessage(
    val transactionResult: TransactionResult,
    val lot: Int = 0,
)

data class Spot(
    val parkingStatus: ParkingStatus,
    val carNumber: String = "",
    val carColor: String = ""
)

class ParkingService: ParkingInterface {

    private val spots = mutableMapOf<Int, Spot>()
    @Volatile
    private var isCreated: Boolean = false

    // ref counter
    @Volatile
    private var occupied: Int = 0

    private val lock: Lock = ReentrantLock()

    init {
    }

    override fun park(parseNumberMessage: ParseResultMessage): ParkingResponceMessage {
        synchronized(lock) {
            for(lot in spots) {
                if (lot.value.parkingStatus == ParkingStatus.FREE) {
                    spots[lot.key] = Spot(
                        parkingStatus = ParkingStatus.OCCUPIED,
                        carNumber = parseNumberMessage.carNumber,
                        carColor = parseNumberMessage.carColor
                    )

                    ++occupied

                    return ParkingResponceMessage(
                        transactionResult = TransactionResult.DONE,
                        lot = lot.key
                    )

                }
            }
        }

        return ParkingResponceMessage(
            transactionResult = TransactionResult.NO_AVAILABLE_SPOTS
        )
    }

    override fun leave(lot: Int): Boolean {
        synchronized(lock) {
            if (spots[lot]?.parkingStatus != ParkingStatus.FREE) {
                spots[lot] = Spot(
                    parkingStatus = ParkingStatus.FREE,
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
                    parkingStatus = ParkingStatus.FREE
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
        spots.filter { it.value.parkingStatus != ParkingStatus.FREE }
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