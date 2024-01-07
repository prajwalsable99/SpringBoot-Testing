package org.Prajwal;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class FizzBuzz {

    public String compute(int x){

        if(DivisibleByThree(x) && DivisibleByFive(x)){
            return "FizzBuzz";
        } else if (DivisibleByThree(x)) {
            return "Fizz";
        } else if (DivisibleByFive(x)) {
            return "Buzz";
        }else {
            return Integer.toString(x);
        }

    }
    public boolean DivisibleByThree(int x){

        return x % 3 == 0;
    }

    public boolean DivisibleByFive(int x){

        return x % 5 == 0;
    }

}