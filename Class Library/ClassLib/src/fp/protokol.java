/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fp;

import java.io.Serializable;

/**
 *
 * @author ASUS-PC
 */
public class protokol implements Serializable{
    private int X;
    private int Y;
    private int turn;
    private int pemenang;
    private int pemain;
    private String user[];
    private String username;
    private int Request;
    private int Response;
    private int Skor[];
    /*
    Request : 
    0. Username
    1. Ready
    2. Hide
    3. Seek
    4. User
    5. Disconnect
    */
    /*
    Response :
    10. New User
    11. Waiting for ready
    12. Skor
    13. Hide
    14. Wait
    15. Go
    16. Finish Kalah
    17. Finish Menang
    18. Disconnect
    19. Request Disconnect
    */

    /**
     * @return the X
     */
    public int getX() {
        return X;
    }

    /**
     * @param X the X to set
     */
    public void setX(int X) {
        this.X = X;
    }

    /**
     * @return the Y
     */
    public int getY() {
        return Y;
    }

    /**
     * @param Y the Y to set
     */
    public void setY(int Y) {
        this.Y = Y;
    }

    /**
     * @return the turn
     */
    public int getTurn() {
        return turn;
    }

    /**
     * @param turn the turn to set
     */
    public void setTurn(int turn) {
        this.turn = turn;
    }

    /**
     * @return the pemenang
     */
    public int getPemenang() {
        return pemenang;
    }

    /**
     * @param pemenang the pemenang to set
     */
    public void setPemenang(int pemenang) {
        this.pemenang = pemenang;
    }

    /**
     * @return the pemain
     */
    public int getPemain() {
        return pemain;
    }

    /**
     * @param pemain the pemain to set
     */
    public void setPemain(int pemain) {
        this.pemain = pemain;
    }

    /**
     * @return the user
     */
    public String[] getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(String[] user) {
        this.user = user;
    }

    /**
     * @return the Request
     * Request : 
     * 0. Username
     * 1. Ready
     * 2. Hide
     * 3. Seek
     * 4. User
     * 5. Disconnect
     */
    public int getRequest() {
        return Request;
    }

    /**
     * @param Request the Request to set
     * 0. Username
     * 1. Ready
     * 2. Hide
     * 3. Seek
     * 4. User
     * 5. Disconnect
     */
    public void setRequest(int Request) {
        this.Request = Request;
    }

    /**
     * @return the Response
     */
    public int getResponse() {
        return Response;
    }

    /**
     * @param Response the Response to set
     * 10. New User
     * 11. Waiting for ready
     * 12. Skor
     * 13. Hide
     * 14. Wait
     * 15. Go
     * 16. Finish Menang
     * 17. Finish Kalah
     */
    public void setResponse(int Response) {
        this.Response = Response;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the Skor
     */
    public int[] getSkor() {
        return Skor;
    }

    /**
     * @param Skor the Skor to set
     */
    public void setSkor(int[] Skor) {
        this.Skor = Skor;
    }
    
    
}
