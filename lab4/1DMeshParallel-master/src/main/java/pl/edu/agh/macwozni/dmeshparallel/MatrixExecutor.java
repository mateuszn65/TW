package pl.edu.agh.macwozni.dmeshparallel;

import pl.edu.agh.macwozni.dmeshparallel.mesh.GraphDrawer;
import pl.edu.agh.macwozni.dmeshparallel.mesh.MatrixDrawer;
import pl.edu.agh.macwozni.dmeshparallel.mesh.SquareElement;
import pl.edu.agh.macwozni.dmeshparallel.mesh.Vertex;
import pl.edu.agh.macwozni.dmeshparallel.myProductions.*;
import pl.edu.agh.macwozni.dmeshparallel.parallelism.BlockRunner;
import pl.edu.agh.macwozni.dmeshparallel.production.AbstractProduction;
import pl.edu.agh.macwozni.dmeshparallel.production.PDrawer;

import java.util.ArrayList;
import java.util.List;


public class MatrixExecutor extends Thread {
    public static int N = 10;

    private final BlockRunner runner;

    public MatrixExecutor(BlockRunner _runner){
        this.runner = _runner;
    }
    public MatrixExecutor(BlockRunner _runner, int n){
        this.runner = _runner;
        N = n;
    }
    @Override
    public void run() {

        PDrawer drawer = new MatrixDrawer();
        //axiom
        SquareElement s = new SquareElement();

        //pi
        PI pi = new PI(s, drawer);

        List<AbstractProduction<SquareElement>> list = new ArrayList<>();
        list.add(pi);
        List<AbstractProduction<SquareElement>> tmp = new ArrayList<>();

        while (list.size() > 0){
            tmp.clear();
            for (AbstractProduction<SquareElement> production: list) {
                tmp.add(production);
                this.runner.addThread(production);
            }
            this.runner.startAll();
            list.clear();
            for (AbstractProduction<SquareElement> production: tmp) {
                list.addAll(production.getNext());
            }
            drawer.draw(pi.getObj());
        }


        //done
        System.out.println("done");
        drawer.draw(pi.getObj());

    }
}
