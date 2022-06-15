package pt.isec.pa.model.memory;

import pt.isec.pa.model.fsm.AppContext;

import java.io.*;

public class MemoryManager implements Serializable {

    public boolean save(File fl, AppContext app){
        try(ObjectOutputStream oos =
                    new ObjectOutputStream(
                            new FileOutputStream(fl)))
        {
            oos.writeObject(app);
        } catch (Exception e) {
            System.err.println("Error saving data");
        }
        return false;
    }

    public boolean load(File fl, AppContext app){
        try(ObjectInputStream ois =
                    new ObjectInputStream(
                            new FileInputStream(fl)))
        {
            app =  (AppContext) ois.readObject();

        } catch (Exception e) {
            System.err.println("Error loading data");
        }
        return false;
    }
}
