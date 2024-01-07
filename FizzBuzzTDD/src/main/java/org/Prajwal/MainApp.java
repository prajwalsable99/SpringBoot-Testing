package org.Prajwal;

public class MainApp {

    public static void main(String[] args) {

        FizzBuzz fizzBuzz = new FizzBuzz();

        for (int i = 1; i <=100; i++) {

            System.out.println(i+"==>"+fizzBuzz.compute(i));
        }
    }
}
