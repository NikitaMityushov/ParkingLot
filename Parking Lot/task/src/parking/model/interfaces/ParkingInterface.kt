package parking.model.interfaces

import parking.model.messages.ParkingResponceMessage
import parking.model.messages.ParseResultMessage

/**
 * API for interaction with Parking Services
 */
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