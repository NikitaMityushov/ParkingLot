package parking.framework

import parking.resources.Strings
/**
 * Represents Template Method Design Pattern
 */
open class MenuActivity {

    var screen: MenuScreen? = null

    open fun onCreate() {
        this.screen = MenuScreen(
            content = Strings.ENTER_THE_COMMAND
        )
    }

    open fun onStart() {
        while (true) {
            // 1) render screen and wait an input
            println(screen?.renderScreen())
            val input = readLine()!!.toString()
            // 2) check if an input is back button
            if (input.equals(screen?.toolbar?.backButton, ignoreCase = true)) {
                break
            }
            // 3) receive a Screen.MenuItem
            val menuItem = checkIfCommandIsExistsAndReturnMenuItem(input)
            // 4) if menuItem is null - no such command in the menu
            if (menuItem == null) {
               println(Strings.NO_SUCH_COMMAND)
            } else {
                // handle the item click
                onItemMenuSelected(menuItem)
            }
        }
        println("Buy!")
    }

    open fun onItemMenuSelected(menuItem: MenuScreen.MenuItem) {
        menuItem.clickListener.click()
    }

    open fun onDestroy() {
        screen = null
    }

    /**
     * Template method
     */
    open fun performStart() {
        onCreate()
        onStart()
        onDestroy()
    }
    /**
     * Check if an input is an existing command and return the appropriate Screen.MenuItem, if not - return null
     */
    private fun checkIfCommandIsExistsAndReturnMenuItem(command: String): MenuScreen.MenuItem? {
        for (item in screen?.menuItemsSet!!) {
            if (item.value.equals(command, ignoreCase = true)) {
                return item
            }
        }
        return null
    }
}
