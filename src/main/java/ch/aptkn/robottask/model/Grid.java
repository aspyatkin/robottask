package ch.aptkn.robottask.model;

import ch.aptkn.robottask.exception.PositionOutOfGridBoundsException;

public class Grid {
    private final int width;
    private final int height;

    public Grid(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void checkBounds(int x, int y) throws PositionOutOfGridBoundsException {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            throw new PositionOutOfGridBoundsException("Position (%d; %d) is not within the %dx%d grid bounds".formatted(x, y, width, height));
        }
    }
}
