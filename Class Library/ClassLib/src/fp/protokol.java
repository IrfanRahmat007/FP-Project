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
    int type;
    int X;
    int Y;
    int turn;
    int pemenang;
    int pemain;
    String user[];
    int Request;
    /*
    1. Ready
    2. Hide
    3. Seek
    4. Disconnect
    */
    int Response;
    /*
    1. Confirm
    2. Skor
    3. Izin
    4. Notifikasi
    3. Winner
    
    */
    
    
}
