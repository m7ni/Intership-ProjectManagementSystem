package pt.isec.pa.model.data.memento;

public interface IMemento {
    default Object getSnapshot(){return null;}
}
