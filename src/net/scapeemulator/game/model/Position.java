package net.scapeemulator.game.model;

public final class Position {

    private final int x, y, height;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
        this.height = 0;
    }

    public Position(int x, int y, int height) {
        this.x = x;
        this.y = y;
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getBaseLocalX() {
        return getBaseLocalX(getCentralRegionX());
    }

    public int getBaseLocalY() {
        return getBaseLocalY(getCentralRegionX());
    }

    public int getBaseLocalX(int centralRegionX) {
        return (centralRegionX - 6) * 8;
    }

    public int getBaseLocalY(int centralRegionY) {
        return (centralRegionY - 6) * 8;
    }

    public int getLocalX() {
        return getLocalX(getCentralRegionX());
    }

    public int getLocalY() {
        return getLocalY(getCentralRegionX());
    }

    public int getLocalX(int centralRegionX) {
        return x - ((centralRegionX - 6) * 8);
    }

    public int getLocalY(int centralRegionY) {
        return y - ((centralRegionY - 6) * 8);
    }

    public int getCentralRegionX() {
        return x / 8;
    }

    public int getCentralRegionY() {
        return y / 8;
    }

    public int getHeight() {
        return height;
    }

    public boolean isWithinDistance(Position position) {
        int deltaX = position.getX() - x;
        int deltaY = position.getY() - y;
        return deltaX >= -16 && deltaX <= 15 && deltaY >= -16 && deltaY <= 15;
    }

    public boolean isWithinScene(Position position) {
        int deltaX = position.getX() - x;
        int deltaY = position.getY() - y;
        return deltaX >= -52 && deltaX <= 51 && deltaY >= -52 && deltaY <= 51;
    }

    public int getDistance(Position other) {
        int deltaX = x - other.x;
        int deltaY = y - other.y;
        // TODO will rounding up interfere with other stuff?
        return (int) Math.floor(Math.sqrt(deltaX * deltaX + deltaY * deltaY));
    }

    public int toPackedInt() {
        return (height << 28) | (x << 14) | y;
    }

    public int blockHash() {
        return (x & 0x7) << 4 | y & 0x7;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + height;
        result = prime * result + x;
        result = prime * result + y;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Position other = (Position) obj;
        if (height != other.height) {
            return false;
        }
        if (x != other.x) {
            return false;
        }
        if (y != other.y) {
            return false;
        }
        return true;
    }

    public String toString() {
        return "position[x: " + x + ", y: " + y + ", height: " + height + "]";
    }
}
