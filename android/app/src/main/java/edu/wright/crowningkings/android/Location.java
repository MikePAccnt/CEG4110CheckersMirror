package edu.wright.crowningkings.android;

/**
 * Represents a location on the board. Originally by awushensky. Modified by csmith on 11/25/2016
 */
public class Location {

    private int x;
    private int y;

    public Location(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isBetween(final Location location1, final Location location2) {
        final int greaterX = Math.max(location1.getX(), location2.getX());
        final int greaterY = Math.max(location1.getY(), location2.getY());
        final int lesserX = Math.min(location1.getX(), location2.getX());
        final int lesserY = Math.min(location1.getY(), location2.getY());

        return this.x == lesserX + 1
                && this.x == greaterX - 1
                && this.y == lesserY + 1
                && this.y == greaterY - 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Location location = (Location) o;

        if (x != location.x) return false;
        if (y != location.y) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}
