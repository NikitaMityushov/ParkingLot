package parking.controller

import parking.model.*

class Controller {
    companion object {
        // Strings
        private const val PARKING_IS_NOT_CREATED = "Sorry, a parking lot has not been created."
        private const val PARKING_IS_FULL = "Sorry, the parking lot is full."
    }
    private val parking = ParkingService()

    fun getControllerResponce(input: String): String {
        val parseResponse = parseNumberFunction(input)
        return handleParseResponce(parseResponse)
    }

    private fun handleParseResponce(parseResponse: ParseResultMessage): String {
        // 1) if parking is not created
        if (!parking.isCreated()) {
            return PARKING_IS_NOT_CREATED
        }
        // 2) if park
        if (parseResponse.parseStatus == ParseStatus.PARK) {
            val parkingResponce = parking.park(parseResponse)

            return if (parkingResponce.transactionResult == TransactionResult.DONE) {
                "${parseResponse.carColor.capitalize()} car parked in spot ${parkingResponce.lot}."
            } else {
                PARKING_IS_FULL
            }
            // 3) if leave
        } else if (parseResponse.parseStatus == ParseStatus.LEAVE) {
            val parkingResponce = parking.leave(parseResponse.leaveSpot)
            return if (parkingResponce) {
                "Spot ${parseResponse.leaveSpot} is free."
            } else {
                "There is no car in spot ${parseResponse.leaveSpot}."
            }
        }
        return "ERROR! No responce"
    }

    fun create(numberOfSpots: Int): String {
        val result = parking.create(numberOfSpots)
        return if (result) {
            "Created a parking lot with $numberOfSpots spots."
        } else {
            PARKING_IS_NOT_CREATED
        }
    }

    fun status(): String = parking.status()

    fun regByColor(color: String): String {

        if (checkIfParkingIsNotCreated()) {
            return PARKING_IS_NOT_CREATED
        }

        val response = parking.regByColor(color)

        if (response.isNotEmpty()) {
            return buildStringResponce(response)
        }
        return "No cars with color $color were found."
    }

    fun spotByColor(color: String): String {

        if (checkIfParkingIsNotCreated()) {
            return PARKING_IS_NOT_CREATED
        }

        val response = parking.spotByColor(color)
        if (response.isNotEmpty()) {
            return buildStringResponce(response)
        }
        return "No cars with color $color were found."
    }

    fun spotByReg(carNumber: String): String {

        if (checkIfParkingIsNotCreated()) {
            return PARKING_IS_NOT_CREATED
        }

        val response = parking.spotByReg(carNumber)

        if (response.isNotEmpty()) {
            return buildStringResponce(response)
        }

        return "No cars with registration number $carNumber were found."
    }

    private fun <T> buildStringResponce(response: List<T>): String {
        val builder = StringBuilder()

        for (item in response) {
            builder.append(", $item")
        }

        val pattern = ", ".toRegex()

        return builder.replaceFirst(regex = pattern, "")
    }

    private fun checkIfParkingIsNotCreated(): Boolean {
        return !parking.isCreated()
    }
}
