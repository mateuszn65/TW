package pl.edu.agh.macwozni.dmeshparallel.myProductions;

import pl.edu.agh.macwozni.dmeshparallel.MatrixExecutor;
import pl.edu.agh.macwozni.dmeshparallel.mesh.SquareElement;
import pl.edu.agh.macwozni.dmeshparallel.production.AbstractProduction;
import pl.edu.agh.macwozni.dmeshparallel.production.PDrawer;

import java.util.ArrayList;
import java.util.List;

public class PS extends AbstractProduction<SquareElement> {
    private int i;
    private int j;
    public PS(SquareElement _obj, PDrawer<SquareElement> _drawer, int i, int j){
        super(_obj, _drawer);
        this.i = i;
        this.j = j;
    }

    @Override
    public SquareElement apply(SquareElement M) {
        System.out.println("->PS");
        SquareElement M2 = new SquareElement();
        M2.setNorth(M);
        M.setSouth(M2);
        return M2;
    }
    @Override
    public List<AbstractProduction<SquareElement>> getNext() {
        List<AbstractProduction<SquareElement>> list = new ArrayList<>();
        if (this.i + 1 < MatrixExecutor.N){
            PS ps = new PS(super.getObj(), super.getDrawer(), this.i + 1, this.j);
            list.add(ps);;
        }
        if (this.j != 0){
            PJ pj = new PJ(super.getObj(), super.getDrawer());
            list.add(pj);
        }
        return list;
    }
}
