package pl.edu.agh.macwozni.dmeshparallel.myProductions;

import pl.edu.agh.macwozni.dmeshparallel.mesh.SquareElement;
import pl.edu.agh.macwozni.dmeshparallel.production.AbstractProduction;
import pl.edu.agh.macwozni.dmeshparallel.production.PDrawer;

import java.util.ArrayList;
import java.util.List;

public class PI extends AbstractProduction<SquareElement> {
    public PI(SquareElement _obj, PDrawer<SquareElement> _drawer) {
        super(_obj, _drawer);
    }
    @Override
    public SquareElement apply(SquareElement M) {
        System.out.println("PI");
        return M;
    }

    @Override
    public List<AbstractProduction<SquareElement>> getNext() {
        List<AbstractProduction<SquareElement>> list = new ArrayList<>();
        PW pw1 = new PW(super.getObj(), super.getDrawer(), 1);
        PS ps10 = new PS(super.getObj(), super.getDrawer(), 1, 0);
        list.add(pw1);
        list.add(ps10);
        return list;
    }
}
