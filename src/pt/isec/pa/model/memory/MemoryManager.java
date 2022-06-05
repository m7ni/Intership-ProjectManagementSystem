package pt.isec.pa.model.memory;

import pt.isec.pa.model.fsm.AppContext;

import java.io.*;

public class MemoryManager implements Serializable {

    public boolean save(String fileName, AppContext app){
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

    public Boolean load(String fileName, AppContext app){
        try(ObjectInputStream ois =
                    new ObjectInputStream(
                            new FileInputStream(fileName)))
        {
            app =  (AppContext) ois.readObject();

        } catch (Exception e) {
            System.err.println("Error loading data");
        }
        return false;
    }
}
