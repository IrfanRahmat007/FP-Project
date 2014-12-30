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
        ArrayList <ThreadClient> allThread = null;
        statistic roomStat=null;
        try {
            // TODO code application logic here
            
            ServerSocket server = new ServerSocket(6060);
            Socket socket = server.accept();
            System.out.println("Menunggu panggilan...");
            while(true)
            {
                if(allThread==null)
                {
                    allThread=new ArrayList();
                    roomStat=new statistic(allThread);
                    rooms.add(allThread);
                }
                else
                {
                    if (allThread.size()==3)
                    {
                        allThread=new ArrayList();
                        roomStat=new statistic(allThread);
                        rooms.add(allThread);
                    }
                }
                Socket sockClient = server.accept();
                System.out.println(sockClient.getInetAddress().toString()+" masuk\r\n");
                
                synchronized(allThread)
                {
                    ThreadClient tc = new ThreadClient(sockClient,allThread,roomStat);
                    allThread.add(tc);
                    Thread t = new Thread(tc);
                    t.start();
                }
            }

        } catch (IOException ex) {
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
