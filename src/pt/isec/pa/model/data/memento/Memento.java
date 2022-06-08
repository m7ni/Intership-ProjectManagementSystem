package pt.isec.pa.model.data.memento;


import pt.isec.pa.model.data.AppData;
import pt.isec.pa.model.fsm.AppContext;
import pt.isec.pa.model.fsm.IAppState;

import java.io.*;

public class Memento implements IMemento{
    private byte[] snapshot = null;

    @Override
    public Object getSnapshot() {
        if (snapshot == null) return null;
        try (ByteArrayInputStream bais =
                     new ByteArrayInputStream(snapshot);
             ObjectInputStream ois =
                     new ObjectInputStream(bais)) {
            return ois.readObject();
        } catch (Exception e) { return null; }
    }

    public Memento(Object obj) {
        try (ByteArrayOutputStream baos =
                     new ByteArrayOutputStream();
             ObjectOutputStream oos =
                     new ObjectOutputStream(baos)) {
            oos.writeObject(obj);
            snapshot = baos.toByteArray();
        } catch (Exception e) { snapshot = null; }
    }


}
