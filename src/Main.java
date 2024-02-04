import com.engeto.restaurant.*;

import java.math.BigDecimal;
import java.time.LocalTime;

public class Main {
    public static void main(String[] args) {


        Dish dish1 = null;
        try {
            dish1 = new Dish(10, "Boloňské špagety", BigDecimal.valueOf(179), LocalTime.of(0, 45),
                    "bolonske_spagety_01");
        } catch (RestaurantException e) {
            System.err.println ("Chyba při zadání pokrmu: " + e.getLocalizedMessage());
        }

        Dish dish2 = null;
        try {
            dish2 = new Dish(1,"Minerální voda", BigDecimal.valueOf(29), LocalTime.of(0, 5),
                    "mineralni_voda_01");
        } catch (RestaurantException e) {
            System.err.println ("Chyba při zadání pokrmu: " + e.getLocalizedMessage());
        }


        System.out.println(dish1);

        Cookbook cookbook = new Cookbook();
        cookbook.addDish(dish1);
        cookbook.addDish(dish2);

////        TEST odebrani podle cisla
//        System.out.println("+++++++++ \n Neodebrane jidlo" + cookbook);
//
//        cookbook.removeDish(10);
//
//        System.out.println("odebrane jidlo" + cookbook + "\n ++++++++++");
//
//        cookbook.addDish(dish1);

        dish1.setPrice(BigDecimal.valueOf(209));

        System.out.println("změna " + cookbook.getDish(10));

        Order order1 = new Order(2,1, 2, cookbook);
        OrdersList ordersList = new OrdersList();
        ordersList.addOrder(order1);
        Order order2 = new Order(5, 10, 1, cookbook);
        ordersList.addOrder(order2);
        Order order3 = new Order(8, 10, 3, cookbook);
        order3.sortedOut();
        ordersList.addOrder(order3);

        System.out.println(order1.getFulfilmentTime());
        System.out.println(order1.getOrderDishName());
        System.out.println(order1.getTable());
        System.out.println(order1.getOrderedTime());
        System.out.println(order1.getIsPaid());
        System.out.println(order1);

        System.out.println("\n trvání objednávky 4 : " + ordersList.averageSortedOutTime());

        order1.sortedOut();
        order1.pay();

        System.out.println(order1);

        try {
            cookbook.saveToFile("cookbook.txt");
            System.out.println("Uloženo.");
        } catch (RestaurantException e) {
            System.err.println("Chyba při zápisu do souboru: " + e.getLocalizedMessage());
        }

        Cookbook cookbook2 = new Cookbook();
        try {
            cookbook2 = Cookbook.loadFromFile("readCookbook.txt");
            System.out.println("Načteno.");
        } catch (RestaurantException e) {
            System.err.println("Chyba při čtení ze souboru: " + e.getLocalizedMessage());
        }

        System.out.println(cookbook2);

        System.out.println("-----------------");

        try {
            ordersList.saveToFile("orderslist.txt");
            System.out.println("Uloženo.");
        } catch (RestaurantException e) {
            System.err.println("Chyba při zápisu do soubou: " + e.getLocalizedMessage());
        }

        OrdersList ordersList2 = new OrdersList();
        try {
            ordersList2 = OrdersList.loadFromFile("readOrderslist.txt", cookbook2);
            System.out.println("Načteno.");
        } catch (RestaurantException e) {
            System.err.println("Chyba při čtení ze souboru: " + e.getLocalizedMessage());
        }

        System.out.println(ordersList2);
        System.out.println(ordersList);

        Order order6 = new Order(5,11,2,cookbook2);
        ordersList2.addOrder(order6);
        System.out.println("**************\n" + ordersList2);

        System.out.println("Aktuálně je " + ordersList2.countNotSortedOrder() +
                " rozpracovaných a " + ordersList2.countNotPaid() + " nezaplacených.");

        ordersList2.sortByOrderedTime();
        System.out.println("Seřazený: \n" + ordersList2);

        System.out.println("\n -------------- \n" + ordersList2.averageSortedOutTime());
        System.out.println("velikost " + ordersList2.getSize());

        System.out.println(ordersList2.writeDayOrderedDish());


////          OPAKOVANY TEST
//        try {
//            OrdersList.saveToFile("orderslist2.txt", ordersList2);
//            System.out.println("Uloženo.");
//        } catch (RestaurantException e) {
//            System.err.println("Chyba při zápisu do soubou: " + e.getLocalizedMessage());
//        }
//
//        try {
//            ordersList2 = OrdersList.loadFromFile("readOrderslist2.txt", cookbook2);
//            System.out.println("Načteno.");
//        } catch (RestaurantException e) {
//            System.err.println("Chyba při čtení ze souboru: " + e.getLocalizedMessage());
//        }
//
//
//
//        Order order8 = new Order(1, 1, 2, cookbook2);
//        ordersList2.addOrder(order8);
//        System.out.println(ordersList2);

    }
}