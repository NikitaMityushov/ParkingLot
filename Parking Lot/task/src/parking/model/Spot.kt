package parking.model

/**
 * Represents information about one spot in the Parking
 * 1) Spot status should be FREE or OCCUPIED constant of SpotStatus enum
 * 2) Car color and car number is a String
 */
data class Spot(
    val spotStatus: SpotStatus,
    val carNumber: String = "",
    val carColor: String = ""
)

/**
 *  Represents a spot status: free or occupied
 */
enum class SpotStatus {
    FREE, OCCUPIED
}

// TODO: 01.12.2021(Add car number validator(regex) by delegation)
// TODO: 01.12.2021 (Add color enum) 