package parking.framework

/**
 * Represents one screen in the application.
 * Should have toolbar and menu
 */
open class MenuScreen(
    var toolbar: Toolbar = Toolbar(),
    var menu: String = "",
    var content: String = "",
    val menuItemsSet: MutableSet<MenuItem> = mutableSetOf()
) {
    /**
     * Header of screen
     * It has title, by default is "Exit", and back button, by default is "<-"
     */
    class Toolbar(
        val backButton: String = "<-",
        val title: String = "Exit"
    )

    class MenuItem(
        val value: String,
        val description: String
    ) {
        /**
         * Set the clickListener to handle the click
         */
        var clickListener: MenuClickListener = object : MenuClickListener {
            override fun click() {
                //placeholder
            }
        }
    }

    interface MenuClickListener {
        fun click()
    }


    fun addMenuItems(vararg items: MenuItem) {
        // 1) add all menu items in the list
        menuItemsSet.addAll(items)

        val builder = StringBuilder()

        for (item in items) {
            builder.append("${item.value} - ${item.description}\n")
        }

        this.menu = builder.toString()
    }

    fun renderScreen(): String {
        val builder = StringBuilder()

        builder.append("___________________________________________\n")
        builder.append("${toolbar.backButton} ${toolbar.title}\n")
        builder.append("___________________________________________\n")
        builder.append("$menu\n")
        builder.append("$content\n")

        return builder.toString()
    }
}
