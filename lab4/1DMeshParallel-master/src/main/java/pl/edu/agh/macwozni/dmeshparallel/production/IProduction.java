package pl.edu.agh.macwozni.dmeshparallel.production;

import pl.edu.agh.macwozni.dmeshparallel.mesh.SquareElement;
import pl.edu.agh.macwozni.dmeshparallel.parallelism.MyLock;

import java.util.Collection;
import java.util.List;

public interface IProduction<P> {

    public P apply(P _p);

    public void join() throws InterruptedException;

    public void start();

    public void injectRefs(MyLock _lock);

    public P getObj();

    List<AbstractProduction<SquareElement>> getNext();
}
