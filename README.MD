Parking Lot.

JetBrains Academy CLI project of Kotlin Developer Course.
This is a parking lot management program that keeps track of vacant spots and parked cars.
Application has MVC architecture.
Application interface is a Menu with Toolbar:
___________________________________________
<- Exit                                         <----Toolbar
___________________________________________
1 - Create parking                                       __ |
2 - Status                                                  |
3 - Park a car                                              |
4 - Leave the spot                                          | -- Menu
5 - Print registration numbers in the parking by color      |
6 - Print the spot by color                                 |
7 - Print the spots by registration number                  |
8 - Info                                                 __ |

1..8 is the input commands.
"Create parking" ... "Info" is a description of commands.

The heart of Application is a Framework(the classes in the Framework folder)
It contains the next classes:
    1) Class MenuActivity - controller of application. It represents the Template Method design pattern.
        
    2) Class Screen, which contains the static classes Toolbar, MenuItem, interface MenuClickListener.
        Method renderScreen() returns a String with screen representation.

