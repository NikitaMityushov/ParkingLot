package parking.view

import parking.controller.Controller

class UI {
    private val controller: Controller = Controller()

    fun start() {
        var input = readLine()!!.toString()
        var inputLineList = input.split(" ")
        var command = inputLineList[0]

        while (!command.equals("exit", true)) {
            // menu
            when (command.toLowerCase()) {
                "create" -> printResponce(controller.create(inputLineList[1].toInt()))

                "status" -> printResponce(controller.status())

                "reg_by_color" -> printResponce(controller.regByColor(inputLineList[1]))

                "spot_by_color" -> printResponce(controller.spotByColor(inputLineList[1]))

                "spot_by_reg" -> printResponce(controller.spotByReg(inputLineList[1]))

                else -> printResponce(controller.getControllerResponce(input))
            }

            input = readLine()!!.toString()
            inputLineList = input.split(" ")
            command = inputLineList[0]
        }
    }

    private fun printResponce(response: String) {
        println(response)
    }
}