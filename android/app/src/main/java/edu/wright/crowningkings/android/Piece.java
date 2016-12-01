package edu.wright.crowningkings.android;

/**
 * Represents a checkers piece. Originally by awushensky. Modified by csmith on 11/25/2016
 */
public class Piece {

    private boolean king;
    private Team team;
    private Location location;

    public Piece(final Team team, final Location location) {
        this.team = team;
        this.location = location;

        this.king = false;
    }

    public boolean isKing() {
        return king;
    }

    public Team getTeam() {
        return team;
    }

    public Location getLocation() {
        return location;
    }

    void makeKing() {
        this.king = true;
    }

    void setLocation(final Location location) {
        this.location = location;
    }
}
