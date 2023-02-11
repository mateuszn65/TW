package pl.edu.agh.macwozni.dmeshparallel.myProductions;

import pl.edu.agh.macwozni.dmeshparallel.MatrixExecutor;
import pl.edu.agh.macwozni.dmeshparallel.mesh.SquareElement;
import pl.edu.agh.macwozni.dmeshparallel.production.AbstractProduction;
import pl.edu.agh.macwozni.dmeshparallel.production.PDrawer;

import java.util.ArrayList;
import java.util.List;

public class PW extends AbstractProduction<SquareElement> {
    private int i;
    public PW(SquareElement _obj, PDrawer<SquareElement> _drawer, int i){
        super(_obj, _drawer);
        this.i = i;
    }

    @Override
    public SquareElement apply(SquareElement M) {
        System.out.println("->PW");
        SquareElement M2 = new SquareElement();
        M2.setEast(M);
        M.setWest(M2);
        return M2;
    }
    @Override
    public List<AbstractProduction<SquareElement>> getNext() {
        List<AbstractProduction<SquareElement>> list = new ArrayList<>();
        if (this.i + 1 < MatrixExecutor.N){
            PW pw = new PW(super.getObj(), super.getDrawer(), this.i + 1);
            list.add(pw);
        }
        PS ps = new PS(super.getObj(), super.getDrawer(), 1, this.i);
        list.add(ps);
        return list;
    }
}
