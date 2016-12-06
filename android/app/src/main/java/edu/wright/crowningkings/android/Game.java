package edu.wright.crowningkings.android;

/**
 * This class represents a game of checkers. Originally by awushensky. Modified by csmith on 11/25/2016
 */
public class Game {

    private String id;
    private Board board;
    private Team turn;


    public Game(final String id, final Board board, final Team turn) {

        this.id = id;
        this.board = board;
        //this.turn = turn;
    }

    public Board getBoard() {
        return board;
    }

    public Team getTurn() {
        return turn;
    }

    public String getId() {
        return id;
    }

    public void clearTurn() {
        this.turn = null;
    }

    public void setTurn(Team turn) {
        this.turn = turn;
    }
}
