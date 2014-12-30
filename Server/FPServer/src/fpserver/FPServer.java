/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ASUS-PC
 */
public class FPServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ObjectInputStream ois=null;
        ObjectOutputStream ous=null;
        ArrayList <ArrayList<ThreadClient>> rooms=new ArrayList();
        ArrayList <ThreadClient> allThread = new ArrayList();
        statistic roomStat=new statistic(allThread);
        try 
        {
            System.out.println("Menunggu panggilan...");
            ServerSocket server = new ServerSocket(6060);
            while(true)
            {
                // TODO code application logic here
                Socket sockClient = server.accept();
                System.out.println("Ada yg masuk");
                if (allThread.equals(null)) {
                    System.out.println("Allthread null");
                    allThread = new ArrayList();
                    roomStat = new statistic(allThread);
                    rooms.add(allThread);
                } else {
                    System.out.println("Allthread !Null");
                    if (allThread.size() == 3) {
                        allThread = new ArrayList();
                        roomStat = new statistic(allThread);
                        rooms.add(allThread);
                    }
                }
                System.out.println(sockClient.getInetAddress().toString() + " masuk\r\n");

                synchronized (allThread) {
                    System.out.println("Bikin Thread");
                    ThreadClient tc = new ThreadClient(sockClient, allThread, roomStat);
                    System.out.println("Sukses Bikin Thread");
                    allThread.add(tc);
                    System.out.println("Tambah thread ke allThread");
                    Thread t = new Thread(tc);
                    t.start();
                }   
            }
        } 
        catch (IOException ex) {
            Logger.getLogger(FPServer.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                ois.close();
            } catch (IOException ex) {
                Logger.getLogger(FPServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
