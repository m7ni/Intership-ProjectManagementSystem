package pt.isec.pa.model.Memory;

import pt.isec.pa.model.data.AppData;

import java.io.*;

public class MemoryManager implements Serializable {

    public boolean save(String fileName, AppData app){
        try(ObjectOutputStream oos =
                    new ObjectOutputStream(
                            new FileOutputStream(fileName)))
        {
            oos.writeObject(app);
        } catch (Exception e) {
            System.err.println("Error saving data");
        }
        return false;
    }

    public AppData load(String fileName){
        try(ObjectInputStream ois =
                    new ObjectInputStream(
                            new FileInputStream(fileName)))
        {
            return (AppData) ois.readObject();


        } catch (Exception e) {
            System.err.println("Error loading data");
        }
        return null;
    }
}
