package org.example;

import java.util.ArrayList;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Demo {

    private  String s1="apple";
    private  String s2=s1;

    private  String [] first3LettersABC= {"A","B","C"};

    public List<String> getArrayList() {
        return arrayList;
    }

    private List<String> arrayList=List.of( getFirst3LettersABC());


    public String[] getFirst3LettersABC() {
        return first3LettersABC;
    }

    public String getS1() {
        return s1;
    }

    public String getS2() {
        return s2;
    }




    public  int add(int a, int b){
        return  a+b;
    }

    public Object checkNull(Object o){
        if(o!=null)return o;
        return null;
    }

    public boolean isGreater(int x,int y){
        return x>y;
    }

    public String throwException(int x) throws Exception{
        if(x<0){
                throw new Exception("exception");
        }
        return "val is greater than zero";
    }

    public  void checkTimeout() throws InterruptedException{

        Thread.sleep(2000);
    }


}