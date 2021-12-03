package parking.model.messages

data class ParseResultMessage(
    val parseStatus: ParseStatus,
    val carColor: String = "",
    val carNumber: String = "",
    val leaveSpot: Int = 0
)

enum class ParseStatus {
    ERROR, PARK, LEAVE
}