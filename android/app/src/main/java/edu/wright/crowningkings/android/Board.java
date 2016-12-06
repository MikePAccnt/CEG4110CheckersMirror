package edu.wright.crowningkings.android;

import android.util.Log;

import java.util.HashSet;
import java.util.Set;

//import edu.unlv.sudo.checkers.model.exception.InvalidBoardException;

/**
 * Represents a checkers game board. Originally by awushensky. Modified by csmith on 11/25/2016
 */
public class Board {

    private int spacesPerSide;
    private Set<Piece> pieces;

    public Board(final Set<Piece> pieces) {

//        if (this.spacesPerSide % 2 != 0) {
//            throw new InvalidBoardException(spacesPerSide);
//        }

        this.spacesPerSide = 8;
        this.pieces = pieces;
    }

    public static Set<Piece> initializePieces() {
        int spacesPerSide = 8;
        final Set<Piece> pieces = new HashSet<>(spacesPerSide * spacesPerSide / 2 - (2 * spacesPerSide));

        for (int y = 0; y < spacesPerSide / 2 - 1; y++) {
            for (int x = (y + 1) % 2; x < spacesPerSide; x += 2) {
                final Location location = new Location(x, y);
                pieces.add(new Piece(Team.BLACK, location));
            }
        }

        for (int y = spacesPerSide - 1; y >= spacesPerSide / 2 + 1; y--) {
            for (int x = (y + 1) % 2; x < spacesPerSide; x += 2) {
                final Location location = new Location(x, y);
                pieces.add(new Piece(Team.RED, location));
            }
        }
        return pieces;
    }

    public Set<Piece> getPieces() {
        return pieces;
    }

    public void setPieces(final Set<Piece> newPieces) {
        this.pieces = newPieces;
    }

    public int getSpacesPerSide() {
        return spacesPerSide;
    }

    public Piece getPieceAtLocation(final Location location) {
        Log.d("Board", "getPieceAtLocation");
        for (Piece piece : pieces) {
            if (piece.getLocation().equals(location)) {
                return piece;
            }
        }

        return null;
    }
}
