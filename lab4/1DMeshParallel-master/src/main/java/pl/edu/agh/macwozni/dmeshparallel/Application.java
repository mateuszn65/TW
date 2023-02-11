package pl.edu.agh.macwozni.dmeshparallel;

import pl.edu.agh.macwozni.dmeshparallel.parallelism.ConcurentBlockRunner;

class Application {

    public static void main(String[] args) {

//        Executor e = new Executor(new ConcurentBlockRunner());
//        e.start();

        MatrixExecutor executor = new MatrixExecutor(
                new ConcurentBlockRunner(),
                args.length > 0 ? Integer.parseInt(args[0]) : 8
        );
        executor.start();
    }
}
