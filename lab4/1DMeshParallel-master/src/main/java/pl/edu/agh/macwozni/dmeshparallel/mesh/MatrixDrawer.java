package pl.edu.agh.macwozni.dmeshparallel.mesh;

import pl.edu.agh.macwozni.dmeshparallel.production.PDrawer;

public class MatrixDrawer implements PDrawer<SquareElement> {

    @Override
    public void draw(SquareElement squareElement) {
        int down = 0;
        int leftOffset = 0;
        int count = 0;
        SquareElement currentElement = squareElement;
        SquareElement firstInNextLine = null;
        SquareElement prevFirstInNextLine = null;
        SquareElement lastInCurrentLine = null;
        //go up
        while(currentElement.getNorth() != null){
            currentElement = currentElement.getNorth();
        }
        //go right
        lastInCurrentLine = currentElement;
        while(lastInCurrentLine.getEast() != null){
            lastInCurrentLine = lastInCurrentLine.getEast();
        }
        //go left
        firstInNextLine = currentElement;
        while (firstInNextLine.getWest() != null){
            firstInNextLine = firstInNextLine.getWest();
        }
        //plot
        System.out.println();
        while (firstInNextLine != null){
            currentElement = firstInNextLine;
            prevFirstInNextLine = firstInNextLine;
            firstInNextLine = null;
            count = 0;
            while (count < leftOffset){
                count += 1;
                System.out.print("    ");
            }

            while (currentElement != lastInCurrentLine) {
                if (firstInNextLine == null && currentElement.getSouth() != null){
                    firstInNextLine = currentElement.getSouth();
                    leftOffset = count;
                }
                if (currentElement.getEast() != null){
                    System.out.print(SquareElement.label +"-=-");
                }else{
                    System.out.print(SquareElement.label + "-0-");
                }
                for (int i = 0; i < down; i++) {
                    currentElement = currentElement.getNorth();
                }
                currentElement = currentElement.getEast();
                for (int i = 0; i < down; i++) {
                    currentElement = currentElement.getSouth();
                }
                count +=1;
            }

            System.out.print(SquareElement.label + "\n");
            if (firstInNextLine == null && currentElement.getSouth() != null){
                firstInNextLine = currentElement.getSouth();
                leftOffset = count;
            }


            if (firstInNextLine != null){
                count = 0;
                while (count < leftOffset - 1){
                    count += 1;
                    System.out.print("    ");
                }

                currentElement = prevFirstInNextLine;
                while (currentElement != lastInCurrentLine){
                    if (currentElement.getSouth() != null){
                        System.out.print("|   ");
                    }else{
                        System.out.print("    ");
                    }
                    for (int i = 0; i < down; i++) {
                        currentElement = currentElement.getNorth();
                    }
                    currentElement = currentElement.getEast();
                    for (int i = 0; i < down; i++) {
                        currentElement = currentElement.getSouth();
                    }
                }
                System.out.print("|\n");
            }
            lastInCurrentLine = lastInCurrentLine.getSouth();
            down += 1;
        }
        System.out.println();
    }
}
