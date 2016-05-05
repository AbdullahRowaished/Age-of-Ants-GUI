package antgame;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * This class represents the world being used for a game between any two given
 * players
 *
 * @author oscar
 */
public class World {

    Cell[][] layout; //Holds the cells which are in this world
    Cell[][] originalWorld;
    int sizeX; //Holds the width, in cell number, of the world
    int sizeY; //Holds the height, in cell number, of the world
    Game controller; //Holds the game object using this world
    String worldName; //Holds the name of this world, if randomsied generated incrementally, if loaded the name is the filename
    static int worldNum; //Holds the number of random worlds generated

    public void resetWorld() {
        for (int i = 0; i < layout.length; i++) {
            for (int j = 0; j < layout[0].length; j++) {
                layout[i][j] = originalWorld[i][j].copy();
            }
        }
    }

    /**
     * @param g the game object which is creating the world
     * @param worldName_ the name of the world, if any
     */
    public World(Game g, String worldName_) {
        worldName = worldName_;
        controller = g;
    }

    /**
     * @param worldName_ sets this worlds name
     */
    public void setName(String worldName_) {
        worldName = worldName_;
    }

    /**
     * Creates the initial layout of cells to the specified size, does not add
     * any rocks, ants, anthill or food
     */
    public void createWorld() {
        //Sets up the layout array
        layout = new Cell[sizeX][];
        originalWorld = new Cell[sizeX][];
        for (int i = 0; i < sizeX; i++) {
            originalWorld[i] = new Cell[sizeY];
            layout[i] = new Cell[sizeY];
            for (int j = 0; j < layout[0].length; j++) {
                layout[i][j] = new Cell(false, AntHill.NO_ANTHILL);
                originalWorld[i][j] = new Cell(false, AntHill.NO_ANTHILL);
            }
        }

    }

    /**
     * Randomises a world to the contest specification
     */
    public void randomWorld() {
        createWorld();
        worldName = "world" + worldNum++;

        //Place a ring of rocks around the world
        for (int x = 0; x < sizeX; x++) {
            addRock(x, 0, false);
            addRock(x, sizeY - 1, false);
        }
        for (int y = 1; y < sizeY - 1; y++) {
            addRock(0, y, false);
            addRock(sizeX - 1, y, false);
        }

        //Add the anthills
        addRandomRedAnthill();
        addRandomBlackAnthill();

        //Add 11 food blobs
        for (int foodCount = 0; foodCount < 11; foodCount++) {
            addRandomFood();
        }

        //Add 14 individual rocks
        for (int rockCount = 0; rockCount < 14; rockCount++) {
            addRandomRock();
        }

        for (int i = 0; i < layout.length; i++) {
            for (int j = 0; j < layout[0].length; j++) {
                originalWorld[i][j] = layout[i][j].copy();
            }
        }
    }

    /**
     * Adds an anthill randomly to this world object
     */
    public void addRandomRedAnthill() {
        boolean placed = false;
        while (!placed) {
            //Choose a random location and try and place the anthill there
            double temp = Math.random() * (sizeX - 7);
            int x = Math.round(Math.round(temp));
            temp = Math.random() * sizeY;
            int y = Math.round(Math.round(temp));
            placed = addRedAnthill(x, y);
        }
    }

    /**
     * Attempts to add a red anthill at the coordinates specified
     *
     * @param x position on x axis of the top left point of the anthill
     * @param y position on y axis of the top left point of the anthill
     * @return true if the anthill is able to be placed, false otherwise
     */
    public boolean addRedAnthill(int x, int y) {
        boolean placed = true;

        int offset = 0; //Holds the x-offset, needed due to the non-uniform layout of the rows

        //Check the cells and nearby cells for if there are any rocks or other anthills
        for (int height = -1; height < 14; height++) {
            int maxWidth = height < 7 ? 7 + height : 19 - height;
            for (int width = 0; width < maxWidth + 2; width++) {
                if (y + height < 0 || y + height > sizeY - 1 || x + width + offset < 0 || x + width + offset > sizeX - 1 || getCell(x + width + offset, y + height).isRocky()) //Need not check for anthills or food as they do not yet exist
                {
                    return false;
                }
            }
            if (height < 6) {
                offset = (height + y) % 2 == 0 ? offset - 1 : offset;
            } else {
                offset = (height + y) % 2 != 0 ? offset + 1 : offset;
            }
        }

        //Places the anthill in all of the cells
        if (placed) {
            for (int height = 0; height < 13; height++) {
                int maxWidth = height < 7 ? 7 + height : 19 - height;
                for (int width = 0; width < maxWidth; width++) {
                    layout[x + width + offset][y + height] = new Cell(false, AntHill.RED_ANTHILL);
                }
                if (height < 6) {
                    offset = (height + y) % 2 == 0 ? offset - 1 : offset;
                } else {
                    offset = (height + y) % 2 != 0 ? offset + 1 : offset;
                }
            }
        }
        return placed;
    }

    /**
     * Add a random black anthill to the world
     */
    public void addRandomBlackAnthill() {
        boolean placed = false;
        while (!placed) {
            //Choose a random location and try and place the anthill there
            placed = true;
            double temp = Math.random() * (sizeX - 7);
            int x = Math.round(Math.round(temp));
            temp = Math.random() * sizeY;
            int y = Math.round(Math.round(temp));
            placed = addBlackAnthill(x, y);
        }
    }

    /**
     * Attempts to add a black anthill at the coordinates specified
     *
     * @param x position on x axis of the top left point of the anthill
     * @param y position on y axis of the top left point of the anthill
     * @return true if the anthill is able to be placed, false otherwise
     */
    public boolean addBlackAnthill(int x, int y) {
        int offset = 0; //Holds the x-offset, needed due to the non-uniform layout of the rows
        boolean placed = true;

        //Check the cells and nearby cells for if there are any rocks or other anthills
        for (int height = -1; height < 14; height++) {
            int maxWidth = height < 7 ? 7 + height : 19 - height;
            for (int width = 0; width < maxWidth + 2; width++) {
                if (y + height < 0 || y + height > sizeY - 1 || x + width + offset < 0 || x + width + offset > sizeX - 1 || getCell(x + width + offset, y + height).isRocky()) //Need not check for anthills or food as they do not yet exist
                {
                    return false;
                }
            }
            if (height < 6) {
                offset = (height + y) % 2 == 0 ? offset - 1 : offset;
            } else {
                offset = (height + y) % 2 != 0 ? offset + 1 : offset;
            }
        }

        //Place the anthill in the cells
        if (placed) {
            for (int height = 0; height < 13; height++) {
                int maxWidth = height < 7 ? 7 + height : 19 - height;
                for (int width = 0; width < maxWidth; width++) {
                    layout[x + width + offset][y + height] = new Cell(false, AntHill.BLACK_ANTHILL);
                }
                if (height < 6) {
                    offset = (height + y) % 2 == 0 ? offset - 1 : offset;
                } else {
                    offset = (height + y) % 2 != 0 ? offset + 1 : offset;
                }
            }
        }
        return placed;
    }

    /**
     * Places a random food rectangle in this world
     */
    public void addRandomFood() {
        boolean placed = false;
        while (!placed) {
            //Chooses a random position in the world and attempts to place a food blob there
            double temp = Math.random() * (sizeX - 7);
            int x = Math.round(Math.round(temp));
            temp = Math.random() * sizeY;
            int y = Math.round(Math.round(temp));
            placed = addFood(x, y);
        }
    }

    /**
     * Adds a 5x5 food square to the world
     *
     * @param x location of the top left of the food
     * @param y location of the top left of the food
     * @return true if the food can be placed, false otherwise
     */
    public boolean addFood(int x, int y) {
        int offset = 0; //Holds the x-offset, needed due to the non-uniform layout of the rows
        boolean placed = true;

        //Check if all cells are free
        for (int height = -1; height < 6; height++) {
            if (y % 2 == 0) {
                for (int width = -1; width < 6; width++) {

                    if (y + height < 0 || y + height > sizeY - 1 || x + width + offset < 0 || x + width + offset > sizeX - 1 || getCell(x + width + offset, y + height).isRocky() || getCell(x + width + offset, y + height).hasAnthill(true) || getCell(x + width + offset, y + height).hasAnthill(false)) {
                        return false;
                    }

                }
            } else {
                for (int width = 0; width < 7; width++) {

                    if (y + height < 0 || y + height > sizeY - 1 || x + width + offset < 0 || x + width + offset > sizeX - 1 || getCell(x + width + offset, y + height).isRocky() || getCell(x + width + offset, y + height).hasAnthill(true) || getCell(x + width + offset, y + height).hasAnthill(false)) {
                        return false;
                    }

                }
            }
            offset = (height + y) % 2 == 0 ? offset - 1 : offset;
        }
        offset = 0;
        //Places the ant blob
        if (placed) {
            for (int height = 0; height < 5; height++) {

                for (int width = 0; width < 5; width++) {
                    if (!getCell(x + width + offset, y + height).isRocky() || !getCell(x + width + offset, y + height).hasAnthill(true) || getCell(x + width + offset, y + height).hasAnthill(false)) {
                        layout[x + width + offset][y + height].setFood(5);
                    }

                }
                offset = (height + y) % 2 == 0 ? offset - 1 : offset;
            }
        }
        return placed;
    }

    /**
     * Adds a random rock to this world
     */
    public void addRandomRock() {
        boolean placed = false;
        while (!placed) {
            //Chooses a random location and attempts to place a rock there
            double temp = Math.random() * (sizeX - 7);
            int x = Math.round(Math.round(temp));
            temp = Math.random() * sizeY;
            int y = Math.round(Math.round(temp));
            placed = addRock(x, y, true);
        }
    }

    /**
     * Attempts to place a rock at the specified location
     *
     * @param x axis location of the rock
     * @param y axis location of the rock
     * @param surroundCheck true if the method should check the surrounding area
     * for obstructions
     * @return true if the rock can be placed, false otherwise
     */
    public boolean addRock(int x, int y, boolean surroundCheck) {
        int offset = 0; //Holds the x-offset, needed due to the non-uniform layout of the rows
        boolean placed = true;

        //Check the neighbouring cells
        if (surroundCheck) {
            for (int height = -1; height < 2; height++) {
                int maxWidth = height != 0 ? 2 : 3;
                if (y % 2 != 0) {
                    for (int width = 0; width < maxWidth; width++) {
                        if (y + height < 0 || y + height > sizeY - 1 || x + width + offset < 0 || x + width + offset > sizeX - 1 || getCell(x + width + offset, y + height).isRocky() || getCell(x + width + offset, y + height).hasAnthill(true) || getCell(x + width + offset, y + height).hasAnthill(false)) {
                            return false;
                        }

                    }
                } else {
                    for (int width = -1; width < maxWidth - 1; width++) {
                        if (y + height < 0 || y + height > sizeY - 1 || x + width + offset < 0 || x + width + offset > sizeX - 1 || getCell(x + width + offset, y + height).isRocky() || getCell(x + width + offset, y + height).hasAnthill(true) || getCell(x + width + offset, y + height).hasAnthill(false)) {
                            return false;
                        }
                    }
                }
                if (height == -1) {
                    offset = (height + y) % 2 == 0 ? offset - 1 : offset;
                } else {
                    offset = (height + y) % 2 != 0 ? offset + 1 : offset;
                }
            }
        }

        //Places the rock
        if (placed && !(getCell(x, y).isRocky() || getCell(x, y).hasAnthill(true) || getCell(x, y).hasAnthill(false))) {
            layout[x][y] = new Cell(true, AntHill.NO_ANTHILL);
            placed = true;
        }
        return placed;
    }

    /**
     * Sets the worlds dimensions to be x and y
     *
     * @param x the width of the world
     * @param y the height of the world
     */
    public void setDimensions(int x, int y) {
        sizeX = x;
        sizeY = y;
    }

    /**
     * Place an ant on every anthill cell with its colour being that of the
     * anthill, increments the id with each ant placed
     */
    public void placeAnts() {
        int id = 0;
        //Iterate over all cells and place ants correspondingly
        for (int y = 0; y < layout.length; y++) {
            for (int x = 0; x < layout[0].length; x++) {
                if (layout[x][y].hasAnthill(true)) {
                    Ant a = new Ant(id++, x, y, true, this);
                    try {
                        layout[x][y].setAnt(a);
                    } catch (Exception ex) {
                        System.err.println(ex.getMessage());
                    }
                    controller.addAnt(a);
                } else if (layout[x][y].hasAnthill(false)) {
                    Ant a = new Ant(id++, x, y, false, this);
                    try {
                        layout[x][y].setAnt(a);
                    } catch (Exception ex) {
                        System.err.println(ex.getMessage());
                    }
                    controller.addAnt(a);
                }
            }
        }
    }

    /**
     * Returns the x and y coordinates of the cell which the ant a faces, should
     * never be out of bounds as the world is encircled by rocky terrain
     *
     * @param a the ant whose coordinates and direction are being used
     * @return the x and y coordinates of the faced cell
     */
    public int[] getFacedCell(Ant a) {
        int direction = a.getDirection();
        int x = a.getX();
        int y = a.getY();
        int nextX = x;
        int nextY = y;

        switch (direction) {
            case 0:
                nextX++;
                break;
            case 1:
                if (y % 2 == 0) {
                    nextY++;
                } else {
                    nextY++;
                    nextX++;
                }
                break;
            case 2:
                if (y % 2 == 0) {
                    nextY++;
                    nextX--;
                } else {
                    nextY++;
                }
                break;
            case 3:
                nextX--;
                break;
            case 4:
                if (y % 2 == 0) {
                    nextX--;
                    nextY--;
                } else {
                    nextY--;
                }
                break;
            case 5:
                if (y % 2 == 0) {
                    nextY--;
                } else {
                    nextY--;
                    nextX++;
                }
                break;
        }

        //Returns an array of size two with the first entry being the x value and the second being the y value
        int[] result = new int[2];
        result[0] = nextX;
        result[1] = nextY;
        return result;
    }

    /**
     * Returns the cell at the specified coordinates
     *
     * @param x the x value
     * @param y the y value
     * @return the cell at x, y
     */
    public Cell getCell(int x, int y) {
        return layout[x][y];
    }

    /**
     * @param world sets the world to have this layout
     */
    public void setWorld(Cell[][] world) {
        layout = world;
        originalWorld = layout;
    }

    /**
     * @return the name of this world
     */
    public String getName() {
        return worldName;
    }

    /**
     * Should only be used for debugging purposes, prints out an unformatted
     * world to the default output stream
     */
    public String printWorld() {
        String returnString = "";
        for (int y = 0; y < sizeX; y++) {
            if (y % 2 != 0) {
                returnString += " ";
            }
            for (int x = 0; x < sizeY; x++) {
                if (layout[x][y].isRocky()) {
                    returnString = "#";
                } else if (layout[x][y].getAnt() != null && layout[x][y].getAnt().isRed()) {
                   returnString += "[";
                } else if (layout[x][y].getAnt() != null && !layout[x][y].getAnt().isRed()) {
                    returnString += "]";
                } else if (layout[x][y].hasAnthill(true)) {
                    returnString += "+";
                } else if (layout[x][y].hasAnthill(false)) {
                    returnString += "-";
                } else if (layout[x][y].foodRemaining() > 0) {
                    returnString += layout[x][y].foodRemaining();
                } else {
                    returnString += ".";
                }
                returnString += " ";
            }
            returnString += "\n";
        }
        return returnString;
    }
}
