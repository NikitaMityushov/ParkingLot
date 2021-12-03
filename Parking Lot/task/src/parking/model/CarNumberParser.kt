package parking.model

import parking.model.messages.ParseResultMessage
import parking.model.messages.ParseStatus

fun parseNumberFunction(str: String): ParseResultMessage {
    val parkRegex = "park*".toRegex()
    val leaveRegex = "leave*".toRegex()

    val listNumber = str.split(" ")

    if (listNumber[0].matches(parkRegex)) {
        return ParseResultMessage(
            parseStatus = ParseStatus.PARK,
            carColor = listNumber[2],
            carNumber = listNumber[1])
    }

    if (listNumber[0].matches(leaveRegex)) {
        return ParseResultMessage(
            parseStatus = ParseStatus.LEAVE,
            leaveSpot = listNumber[1].toInt())
    }

    return ParseResultMessage(ParseStatus.ERROR)
}