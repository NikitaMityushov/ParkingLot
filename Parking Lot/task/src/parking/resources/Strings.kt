package parking.resources

class Strings {
    companion object {
        // 1) Framework Strings
        const val INPUT_IS_NOT_A_NUMBER = "The input is not a number"
        const val NO_SUCH_COMMAND = "No such command!!!"
        const val ENTER_THE_COMMAND = "Please enter the command:"

        // 2) Application strings
        // 2.1) menu items
        const val CREATE_PARKING = "Create parking"
        const val STATUS = "Status"
        const val PARK_CAR = "Park a car"
        const val LEAVE_SPOT = "Leave the spot"
        const val PRINT_REGISTRATION_NUMBER_BY_COLOR = "Print registration numbers in the parking by color"
        const val PRINT_SPOT_BY_COLOR = "Print the spot by color"
        const val PRINT_SPOTS_BY_REGISTRATION_NUMBER = "Print the spots by registration number"
        const val INFO = "Info"

        // 2.2 input
        const val ENTER_NUMBER_OF_SPOTS = "Please enter the number of spots:"
        const val ENTER_CAR_NUMBER = "Please enter the car number:"
        const val ENTER_COLOR = "Please enter a color of the car:"
        const val PLEASE_ENTER_SPOT = "Please enter the number of spot:"

        // 2.3 controller
        const val PARKING_IS_NOT_CREATED = "Sorry, a parking lot has not been created."
        const val PARKING_IS_FULL = "Sorry, the parking lot is full."
        const val INFO_CONTENT = """The parking lot.
JetBrains academy Kotlin Developer Course.
December 2021.

        """
    }
}