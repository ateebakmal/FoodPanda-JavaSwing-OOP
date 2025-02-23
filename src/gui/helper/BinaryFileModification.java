package gui.helper;

import Classes.Restaurant;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class BinaryFileModification {

    public static <E> ArrayList<E> readObjectsFromFile(String fileName, Class<E> type) {
        File file = new File(fileName);

        if (!file.exists()) {
            System.out.println("File Doesn't Exist");
            return new ArrayList<>(); // Return an empty ArrayList
        }

        ArrayList<E> objects = new ArrayList<>();

        // Ensure the file isn't empty before attempting to read
        if (file.length() == 0) {
            System.out.println("File is empty.");
            return objects; // Return empty ArrayList
        }

        try (ObjectInputStream objReader = new ObjectInputStream(new FileInputStream(file))) {
            while (true) {
                try {
                    Object obj = objReader.readObject();
                    if (type.isInstance(obj)) {
                        objects.add(type.cast(obj)); // Safer casting using Class.cast()
                    } else {
                        System.out.println("Skipped an object due to type mismatch.");
                    }
                } catch (EOFException e) {
                    // End of file reached, exit loop
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Error while reading file: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Error while casting objects from file: " + e.getMessage());
        }

        return objects;
    }


    public static<E> void writeObjectsToFile(ArrayList<E> objects, String fileName){
        File file = new File(fileName);

        //if file doesnt exist it creates it
        if(!file.exists()){
            try {
                file.createNewFile();
            }catch (IOException exception){
                System.out.println("Some Error Occurred While Creating This File");
            }
        }

        try(ObjectOutputStream objWriter = new ObjectOutputStream(new FileOutputStream(file,true))) {
            for (E obj : objects) {
                objWriter.writeObject(obj);
            }
        }catch (IOException e){
            System.err.println("Error while writing to file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static<E> void writeObjectsToFile(E object, String fileName){
        File file = new File(fileName);

        //if file doesnt exist it creates it
        if(!file.exists()){
            try {
                file.createNewFile();
            }catch (IOException exception){
                System.out.println("Some Error Occurred While Creating This File");
            }
        }

        try (FileOutputStream fos = new FileOutputStream(file, true);
             ObjectOutputStream oos = file.length() == 0 ? new ObjectOutputStream(fos)
                     : new AppendableObjectOutputStream(fos)) {
            oos.writeObject(object);
            System.out.println("Written Successfully");
        } catch (IOException e) {
            System.err.println("Error while writing to file: " + e.getMessage());
            e.printStackTrace();
        }


    }

    public static<E> void printData(String fileName, Class<E> type){
        //This method reads all the data from the text file and prints on the console for better debugging and understanding
        File file = new File(fileName);

        if(!file.exists()){
            System.out.println("File doesn't exist");
            return;
        }

        try(ObjectInputStream objReader= new ObjectInputStream(new FileInputStream(fileName))){
            while(true) {
                try {
                    E obj = (E) objReader.readObject();
                    System.out.println(obj);
                }catch (EOFException e){
                    break;
                }
            }
        }catch (IOException e){
            e.printStackTrace();
            System.err.println("Error while reading the file: " + e.getMessage());
        }catch (ClassNotFoundException e){
            System.err.println("Error while casting objects from file: " + e.getMessage());
        }
    }

    public static <T> void writeObjectsToFile(String filePath, ArrayList<T> objects) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            for (T obj : objects) {
                oos.writeObject(obj);
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exceptions as needed
        }
    }
}
