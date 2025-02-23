package gui.helper;

import java.util.ArrayList;

public class Utility {
    public static<E> void printArrayList(ArrayList<E> arrayList){
        for(E obj : arrayList){
            System.out.println(obj);
        }
    }
}
