package parking.view

import parking.controller.Controller
import parking.framework.MenuActivity
import parking.framework.MenuScreen
import parking.resources.Strings
/**
 * Represents View in MVC app architecture
 * You should create @MenuItem instances to init a menu,
 * after set @MenuClickListener on each other.
 * @App extends @MenuActivity, which represents a Framework
 * for creating CLI applications with Menu.
 */
class App : MenuActivity() {
    private val controller: Controller = Controller()

    override fun onCreate() {
        super.onCreate()
        // 1) Create menu items
        val menuItemCreateParking = MenuScreen.MenuItem("1", Strings.CREATE_PARKING)
        val menuItemStatus = MenuScreen.MenuItem("2", Strings.STATUS)
        val menuItemParkCar = MenuScreen.MenuItem("3", Strings.PARK_CAR)
        val menuItemLeaveSpot = MenuScreen.MenuItem("4", Strings.LEAVE_SPOT)
        val menuItemRegByColor = MenuScreen.MenuItem("5", Strings.PRINT_REGISTRATION_NUMBER_BY_COLOR)
        val menuItemSpotByColor = MenuScreen.MenuItem("6", Strings.PRINT_SPOT_BY_COLOR)
        val menuItemSpotByReg = MenuScreen.MenuItem("7", Strings.PRINT_SPOTS_BY_REGISTRATION_NUMBER)
        val menuItemInfo = MenuScreen.MenuItem("8", Strings.INFO)
        // 2) Set the click listeners
        menuItemCreateParking.clickListener = object : MenuScreen.MenuClickListener {
            override fun click() {
                println(Strings.ENTER_NUMBER_OF_SPOTS)
                try {
                    val input = readLine()!!
                    println(controller.create(input.toInt()))
                } catch (e: Exception) {
                    println(Strings.INPUT_IS_NOT_A_NUMBER)
                }
            }
        }

        menuItemStatus.clickListener = object : MenuScreen.MenuClickListener {
            override fun click() {
                println(controller.status())
            }
        }

        menuItemParkCar.clickListener = object : MenuScreen.MenuClickListener {
            override fun click() {
                println(Strings.ENTER_CAR_NUMBER)
                val inputNumber = readLine()!!.toString()

                println(Strings.ENTER_COLOR)
                val inputColor = readLine()!!.toString()

                val message = "park $inputNumber $inputColor"

                println(controller.getControllerResponce(message))
            }
        }

        menuItemLeaveSpot.clickListener = object : MenuScreen.MenuClickListener {
            override fun click() {
                try {
                    println(Strings.PLEASE_ENTER_SPOT)
                    val inputSpot = readLine()!!

                    val message = "leave $inputSpot"
                    controller.getControllerResponce(message)
                } catch (e: Exception) {
                    println(Strings.INPUT_IS_NOT_A_NUMBER)
                }

            }
        }

        menuItemRegByColor.clickListener = object : MenuScreen.MenuClickListener {
            override fun click() {
                println(Strings.ENTER_COLOR)
                val inputColor = readLine()!!.toString()
                controller.regByColor(inputColor)
            }
        }

        menuItemSpotByColor.clickListener = object : MenuScreen.MenuClickListener {
            override fun click() {
                println(Strings.ENTER_COLOR)
                val inputColor = readLine()!!.toString()
                controller.spotByColor(inputColor)
            }
        }

        menuItemSpotByReg.clickListener = object : MenuScreen.MenuClickListener {
            override fun click() {
                println(Strings.ENTER_CAR_NUMBER)
                val inputNumber = readLine()!!.toString()
                controller.spotByReg(inputNumber)
            }
        }

        menuItemInfo.clickListener = object : MenuScreen.MenuClickListener {
            override fun click() {
                println(controller.info())
            }
        }
        // 3) add menu items in the menu
        this.screen?.addMenuItems(
            menuItemCreateParking,
            menuItemStatus,
            menuItemParkCar,
            menuItemLeaveSpot,
            menuItemRegByColor,
            menuItemSpotByColor,
            menuItemSpotByReg,
            menuItemInfo
        )
    }

}
