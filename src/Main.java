import com.engeto.restaurant.*;

import java.time.LocalTime;

public class Main {
    public static void main(String[] args) {


        Dish dish1 = null;
        try {
            dish1 = new Dish("Boloňské špagety", 179, LocalTime.of(0, 45),
                    "bolonske_spagety_01");
        } catch (RestaurantException e) {
            System.err.println ("Chyba při zadání pokrmu: " + e.getLocalizedMessage());
        }

        Dish dish2 = null;
        try {
            dish2 = new Dish("Minerální voda", 29, LocalTime.of(0, 5),
                    "mineralni_voda_01");
        } catch (RestaurantException e) {
            System.err.println ("Chyba při zadání pokrmu: " + e.getLocalizedMessage());
        }

        System.out.println(dish1);

        CookBook cookBook = new CookBook();
        cookBook.addDish(dish1);
        cookBook.addDish(dish2);

        dish1.setPrice(209);

        System.out.println("změna " + cookBook.getDish(0));

        Order order1 = new Order(2,cookBook.getDish(0), 1);
        OrdersList ordersList = new OrdersList();
        ordersList.addOrder(order1);

        System.out.println(order1.getFulfilmentTime());
        System.out.println(order1.getOrderDish());
        System.out.println(order1.getTable());
        System.out.println(order1.getOrderedTime());
        System.out.println(order1.getIsPaid());
        System.out.println(order1);

        order1.pay();

        System.out.println(order1);



    }
}