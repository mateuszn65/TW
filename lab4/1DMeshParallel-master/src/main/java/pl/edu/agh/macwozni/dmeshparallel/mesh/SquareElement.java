package pl.edu.agh.macwozni.dmeshparallel.mesh;

public class SquareElement {
    public static String label = "M";
    private SquareElement North = null;
    private SquareElement East = null;
    private SquareElement South = null;
    private SquareElement West = null;
    public SquareElement(){}

    public void setEast(SquareElement east) {
        East = east;
    }

    public void setNorth(SquareElement north) {
        North = north;
    }

    public void setSouth(SquareElement south) {
        South = south;
    }

    public void setWest(SquareElement west) {
        West = west;
    }

    public SquareElement getEast() {
        return East;
    }

    public SquareElement getNorth() {
        return North;
    }

    public SquareElement getSouth() {
        return South;
    }

    public SquareElement getWest() {
        return West;
    }
}
