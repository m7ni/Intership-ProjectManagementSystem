package pt.isec.pa.model.data.memento;

public interface IOriginator {
    IMemento save();
    void restore(IMemento memento);
}
