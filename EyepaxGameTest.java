import java.util.*;

public class EyepaxGameTest {



    static int cols = 15;
    static int rows = 16;
    static int colors = 3;

    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    public static void main(String[] args) {

        List<ColorCell> colorCells = createGame(rows, cols);
        Map<String , ColorCell> largestConnectingBlocks = new HashMap<>();

        Map<String , ColorCell> cellCoordinates  = new HashMap<>();

        colorCells.forEach(cell -> {
            cellCoordinates.put(cell.x + ":" + cell.y , cell);
        });

        System.out.println("************* Generated Color Grid *************");
        printColorGrid(cellCoordinates);

        for (ColorCell cell : colorCells){
            Map<String , ColorCell> tempGrid = new HashMap<>();
            findNeighbourCells(cell , cellCoordinates , tempGrid);

            if (largestConnectingBlocks.keySet().size() < tempGrid.keySet().size()){
                largestConnectingBlocks = tempGrid;
            }
        }

        System.out.println("\n\n************* Largest Color Grid *************");
        printColorGrid(largestConnectingBlocks);

    }

    private static void findNeighbourCells(ColorCell cell , Map<String , ColorCell> cellMap , Map<String , ColorCell> ar ) {

        String cellKey = cell.x + ":" + cell.y;

        boolean cellReviewed = false;

        if (null != ar.get(cellKey)){
            cellReviewed = ar.get(cellKey).reviewed;
            ar.get(cellKey).reviewed = true;
        }

        //Left
        if (!cellReviewed && 0 < cell.y){
            String key = cell.x + ":" + (cell.y -1);

            ColorCell cellInMap = cellMap.get(key);

            if (cellInMap.color == cell.color){
                ar.put(cellKey , cell);
                ar.put(key , cellInMap);

                findNeighbourCells(cellInMap , cellMap , ar);
            }
        }

        //Right
        if (!cellReviewed && cols > cell.y + 1){
            String key = cell.x + ":" + (cell.y  + 1);
            ColorCell cellInMap = cellMap.get(key);
            if (cellInMap.color == cell.color){
                ar.put(cellKey , cell);
                ar.put(key , cellInMap);

                findNeighbourCells(cellInMap , cellMap , ar);
            }
        }

        //Top
        if (!cellReviewed && 0 < cell.x){
            String key = (cell.x - 1) + ":" + cell.y;
            ColorCell cellInMap = cellMap.get(key);
            if (cellInMap.color == cell.color){
                ar.put(cellKey , cell);
                ar.put(key , cellInMap);

                findNeighbourCells(cellInMap , cellMap , ar);
            }
        }

        //Bottom
        if (!cellReviewed && rows > cell.x + 1){
            String key = (cell.x  + 1) + ":" + cell.y;
            ColorCell cellInMap = cellMap.get(key);
            if (cellInMap.color == cell.color){
                ar.put(cellKey , cell);
                ar.put(key , cellInMap);

                findNeighbourCells(cellInMap , cellMap , ar);
            }
        }


    }


    static List<ColorCell> createGame(int col, int row) {
        int cells = col*row;

        List<ColorCell> colorCells = new ArrayList<>();

        for (int i = 0 ; i < cells ; i++){
            int x = i % col;
            int y = i / col;

            ColorCell cel = new ColorCell();
            cel.x = x;
            cel.y = y;
            cel.color = new Random().nextInt(colors)+1;

            colorCells.add(cel);
        }

        return colorCells;
    }


    private static void printColorGrid(Map<String , ColorCell> cellMap){
        int [][] cells = new int[rows][cols];
        cellMap.forEach((key , cell ) -> {
            cells[cell.x][cell.y] = cell.color;
        });

        for (int xInd = 0 ; xInd < cells.length ; xInd++){
            for (int yInd = 0 ; yInd < cells[xInd].length ; yInd++){

                System.out.print(getColor(cells[xInd][yInd])+ " * ");
            }
            System.out.println(ANSI_BLACK_BACKGROUND + "");
        }
    }

    public static String getColor(int code){
        switch (code){
            case 0 :
                return ANSI_BLACK_BACKGROUND;
            case 1 :
                return ANSI_RED_BACKGROUND;
            case 2 :
                return ANSI_BLUE_BACKGROUND;
            case 3 :
                return ANSI_YELLOW_BACKGROUND;
            case 4 :
                return ANSI_CYAN_BACKGROUND;
            case 5 :
                return ANSI_WHITE_BACKGROUND;
            case 6 :
                return ANSI_PURPLE_BACKGROUND;
            case 7 :
                return ANSI_GREEN_BACKGROUND;
            default:
                return code + "";
        }
    }

    public static class ColorCell{
        public int x;
        public int y;
        public int color;
        public boolean reviewed = false;
    }

}

