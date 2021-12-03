package parking.model.messages
/**
 * Represents a responce message of Parking Service with status of transaction and number of lot
 */
data class ParkingResponceMessage(
    val transactionResponce: TransactionResponce,
    val lot: Int = -1,
)

/**
 * Represents
 */
enum class TransactionResponce {
    DONE, NO_AVAILABLE_SPOTS
}