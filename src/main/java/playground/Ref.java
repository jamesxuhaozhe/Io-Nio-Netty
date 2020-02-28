package playground;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class Ref {

    public static void main(String[] args) {
        Car car = new Car("Red", "Honda");

        //WeakReference<Car> ref = new WeakReference<>(car);
        SoftReference<Car> ref = new SoftReference<>(car);
        int i = 0;
        List<Car> mem = new ArrayList<>();
        while (true) {
            mem.add(new Car("red", "haha"));
            if (ref.get() != null) {
                System.out.println("car is still alive for " + i + " iterations " + ref.get());
            } else {
                System.out.println("car is gone for " + i + " iterations.");
                break;
            }
            i++;
        }
    }

    static class Car {
        private String color;

        private String make;

        public Car(String color, String make) {
            this.color = color;
            this.make = make;
        }

        @Override
        public String toString() {
            return "Car{" +
                    "color='" + color + '\'' +
                    ", make='" + make + '\'' +
                    '}';
        }
    }
}
