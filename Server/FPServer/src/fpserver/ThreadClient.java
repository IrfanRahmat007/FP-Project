/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpserver;

import fp.message;
import fp.protokol;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ASUS-PC
 */
public class ThreadClient implements Runnable {

    private Socket sockClient;
    private ArrayList<ThreadClient> alThread;
    private BufferedReader br = null;
    private BufferedOutputStream bos = null;
    private DataInputStream dis = null;
    private SocketAddress sa = null;
    private statistic roomStat = null;
    private ObjectInputStream ois=null;
    private ObjectOutputStream ous=null;
    @Override
    public void run() {
        try {
            ous = new ObjectOutputStream(sockClient.getOutputStream());
            ois = new ObjectInputStream(sockClient.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(ThreadClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public ThreadClient(Socket sockClient, ArrayList<ThreadClient> allThread, statistic roomStat)
    {
        this.sockClient=sockClient;
        this.alThread=alThread;
        this.sa = sockClient.getRemoteSocketAddress();
        this.roomStat=roomStat;
    }
    
    public void StatusUpdated()
    {
        
    }
    public void TurnUpdated()
    {
        if (this.roomStat.Status==this.alThread.indexOf(this)+1);
        {
            
        }
    }
    
    public void BroadcastStat(protokol prot) throws IOException
    {
        for(int i=0;i<this.alThread.size();i++)
        {
            this.alThread.get(i).SendStat(prot);
        }
    }
    
    public void SendStat(protokol prot) throws IOException
    {
        ous.writeObject(prot);
        ous.flush();
        ous.reset();

    }
    public void BroadcastMsg(message msg) throws IOException
    {
        for(int i=0;i<this.alThread.size();i++)
        {
            this.alThread.get(i).SendMsg(msg);
        }
    }
        
    public void SendMsg(message msg) throws IOException
    {
        ous.writeObject(msg);
        ous.flush();
        ous.reset();
    }
}
