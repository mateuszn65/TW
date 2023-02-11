package pl.edu.agh.macwozni.dmeshparallel.myProductions;

import pl.edu.agh.macwozni.dmeshparallel.mesh.SquareElement;
import pl.edu.agh.macwozni.dmeshparallel.production.AbstractProduction;
import pl.edu.agh.macwozni.dmeshparallel.production.PDrawer;

public class PJ extends AbstractProduction<SquareElement> {
    public PJ(SquareElement _obj, PDrawer<SquareElement> _drawer){
        super(_obj, _drawer);
    }

    @Override
    public SquareElement apply(SquareElement M) {
        System.out.println("->PJ");
        SquareElement M2 = M.getNorth().getEast().getSouth();
        M2.setWest(M);
        M.setEast(M2);
        return M;
    }
}
