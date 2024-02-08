import com.engeto.restaurant.*;

import java.math.BigDecimal;
import java.time.LocalTime;

public class Main {
    public static void main(String[] args) {

//      Jiří K.
//      Úkol č.1

        Cookbook cookbook = new Cookbook();
        try {
            cookbook = Cookbook.loadFromFile("cookbook.txt");
        } catch (RestaurantException e) {
            System.err.println("Chyba při načítaní kuchařky: " + e.getLocalizedMessage());
        }

        OrdersList ordersList = new OrdersList();
        try {
            ordersList = OrdersList.loadFromFile("ordersList.txt", cookbook);
        } catch (RestaurantException e) {
            System.err.println("Chyba při načítaní listu objednávek: " + e.getLocalizedMessage());
        }

//      Úkol č.2

        Dish dish1 = null;
        try {
            dish1 = new Dish(1, "Kuřecí řízek obalovaný 150 g",
                    BigDecimal.valueOf(150), LocalTime.of(0, 30), "rizek_01");
        } catch (RestaurantException e ) {
            System.err.println("Chyba při zadání jídla: " + e.getLocalizedMessage());
        }
        cookbook.addDish(dish1);

        Dish dish2 = null;
        try {
            dish2 = new Dish(2, "Hranolky 150 g",
                    BigDecimal.valueOf(79), LocalTime.of(0,15), "hranolky_01");
        } catch (RestaurantException e) {
            System.err.println("Chyba při zadání jídla: " + e.getLocalizedMessage());
        }
        cookbook.addDish(dish2);

        Dish dish3 = null;
        try {
            dish3 = new Dish(3, "Pstruh na víně 200 g",
                    BigDecimal.valueOf(170), LocalTime.of(0,25), "pstruh_01");
        } catch (RestaurantException e) {
            System.err.println("Chyba při zadání jídla: " + e.getLocalizedMessage());
        }
        cookbook.addDish(dish3);

        Dish dish4 = null;
        try {
            dish4 = new Dish(4, "Kofola 0,5 l",
                    BigDecimal.valueOf(35), LocalTime.of(0,5), "kofola_01");
        } catch (RestaurantException e) {
            System.err.println("Chyba při zadání jídla: " + e.getLocalizedMessage());
        }
        cookbook.addDish(dish4);

        Order order1 = null;
        try {
            order1 = new Order(15, 1, 2, cookbook);
        } catch (RestaurantException e) {
            System.err.println("Chyba při zadání objednávky: " + e.getLocalizedMessage());
        }
        ordersList.addOrder(order1);

        Order order2 = null;
        try {
            order2 = new Order(15, 2, 2, cookbook);
        } catch (RestaurantException e) {
            System.err.println("Chyba při zadání objednávky: " + e.getLocalizedMessage());
        }
        ordersList.addOrder(order2);

        Order order3 = null;
        try {
            order3 = new Order(15, 4, 2, cookbook);
        } catch (RestaurantException e) {
            System.err.println("Chyba při zadání objednávky: " + e.getLocalizedMessage());
        }
        ordersList.addOrder(order3);

        order3.sortedOut();

        Order order4 = null;
        try {
            order4 = new Order(2, 4, 2, cookbook);
        } catch (RestaurantException e ) {
            System.err.println("Chyba při zadání objednávky: " + e.getLocalizedMessage());
        }
        ordersList.addOrder(order4);

        order4.sortedOut();
        order4.pay();


//      Úkol č.3

        try {
        ordersList.writeTableFinalPrice(15);
        } catch (RestaurantException e) {
            System.err.println("Chyba při výpisu celkové ceny pro stůl: " + e.getLocalizedMessage());
        }

//      Úkol č.4

        System.out.println("Počet objednávek které zbývá odbavit: " + ordersList.countNotSortedOrder());

        System.out.println("Počet objednávek které nejsou zaplacené: " + ordersList.countNotPaid());

        ordersList.writeAverageSortedOutTime();

        ordersList.writeDayOrderedDish();

        try {
            ordersList.writeOrderForTable(15);
        } catch (RestaurantException e) {
            System.err.println("Chyba při výpisu objednávek pro stůl: " + e.getLocalizedMessage());
        }

//      Úkol č.5

        try {
            cookbook.saveToFile("cookbook.txt");
            System.out.println("Kuchařka je uložena.");
        } catch (RestaurantException e) {
            System.err.println("Chyba při zápisu do souboru: " + e.getLocalizedMessage());
        }

        try {
            ordersList.saveToFile("ordersList.txt");
            System.out.println("Objednávkový list je uložen.");
        } catch (RestaurantException e) {
            System.err.println("Chyba při zápisu do souboru: " + e.getLocalizedMessage());
        }

    }
}