package edu.wright.crowningkings.base.ServerMessage;

import edu.wright.crowningkings.base.BaseClient;

/**
 * Created by csmith on 11/3/16.
 */

public class _207BoardState extends AbstractServerMessage {
    public _207BoardState(String message) {
        String[] param = message.split(" ");
        String msgID = param[0];
        String table = param[1];
        String state = param[2];
        String[] fullParam = {msgID, table, state};
        setParameters(fullParam);
    }

    public void run(BaseClient client) {
        System.out.println("_207BoardState.run(BaseClient)");
        String[] p = getParameters();

//        String[][] board = new String[8][8];
//        int counter = 0;
//        for (int x = 0; x < 8; x++) {
//            for (int y = 0; y < 8; y++) {
//                //System.out.println(p[2]);
//                if (p[2].charAt(counter) == '1') {
//                    board[x][y] = "B";
//                } else if (p[2].charAt(counter) == '2') {
//                    board[x][y] = "R";
//                } else if (p[2].charAt(counter) == '3') {
//                    board[x][y] = "BK";
//                } else if (p[2].charAt(counter) == '4') {
//                    board[x][y] = "RK";
//                } else {
//                    board[x][y] = " ";
//                }
//                counter++;
//            }
//        }
        client.boardState(p[2]);
    }
}
