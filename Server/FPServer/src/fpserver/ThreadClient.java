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

    private int playerIndex;
    private int ready;
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
            System.out.println("Masuk Thread");
            playerIndex=this.alThread.indexOf(this);
            System.out.println("playerIndex");
            this.roomStat.SetUsername(playerIndex,"Player "+playerIndex);
            ous = new ObjectOutputStream(sockClient.getOutputStream());
            ois = new ObjectInputStream(sockClient.getInputStream());
            SendServerMsg("Waiting for user...");
            SendUserList();
            CountUpdated();
            System.out.println("Berhasil mengirim user list");
            System.out.println("Client "+playerIndex+" connected\n");
            while(!this.sockClient.isClosed())
            {
                Object req;
                req=ois.readObject();
                if(req instanceof message)
                {
                    message msg;
                    msg = (message)req;
                    msg.setFrom(this.roomStat.Username[playerIndex]);
                    BroadcastMsg(msg);
                }
                else if(req instanceof protokol)
                {
                    protokol prot;
                    prot=(protokol)req;
                    switch(prot.getRequest())
                    {
                        case 0:
                            this.roomStat.SetUsername(this.alThread.indexOf(this), prot.getUsername());
                            SendServerMsg("Username changed to : "+prot.getUsername());
                            CountUpdated();
                            break;
                        case 1:
                            if(this.ready==0)
                            {
                                this.roomStat.IncrementReady();
                                BroadcastServerMsg("Player \'" + this.roomStat.Username[playerIndex] + "\' is ready");
                                ready=1;
                            }
                            else
                            {
                                BroadcastServerMsg("Player \'" + this.roomStat.Username[playerIndex] + "\' is requesting all player to be ready");
                            }
                            break;
                        case 2:
                            this.roomStat.HideTable(playerIndex, prot.getX(), prot.getY());
                            break;
                        case 3:
                            boolean hasil=this.roomStat.SeekTable(playerIndex, prot.getX(), prot.getY());
                            if(hasil)
                            {
                                this.SendServerMsg("Jawaban Anda Benar");
                            }
                            else
                            {
                                this.SendServerMsg("Jawaban Anda Salah");
                            }
                            break;
                        case 4:
                            SendUserList();
                            break;
                        case 5:
                        {
                            SendServerMsg("Disconnected from server.");
                            protokol prot1=new protokol();
                            prot1.setResponse(17);
                            SendStat(prot1);
                            ous.close();
                            ois.close();
                            this.sockClient.close();
                            break;
                        }
                        default:
                            break;
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ThreadClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ThreadClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Disconnected");
        this.alThread.remove(this);
    }
    
    public ThreadClient(Socket sockClient, ArrayList<ThreadClient> allThread, statistic roomStat)
    {
        this.ready=0;
        this.playerIndex=0;
        this.sockClient=sockClient;
        this.alThread=allThread;
        this.sa = sockClient.getRemoteSocketAddress();
        this.roomStat=roomStat;
    }
    
    public void StatusUpdated()
    {
        protokol prot = new protokol();
        message msg = new message();
        switch(this.roomStat.Status)
        {
            case 1:
                break;
            case 2:
                prot.setResponse(11);
                try {
                    BroadcastServerMsg("All Player are ready. Please choose a button to hide");
                    SendStat(prot);
                } catch (IOException ex) {
                    Logger.getLogger(ThreadClient.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case 3:
                {
                    try {
                        BroadcastServerMsg("All Player are have gone into hiding. Please wait for your turn");
                    } catch (IOException ex) {
                        Logger.getLogger(ThreadClient.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                TurnUpdated();
                break;
            case 4:
                int winner=this.roomStat.Winner;
                if(winner==this.alThread.indexOf(this))
                {
                     prot.setResponse(17);
                     prot.setPemenang(winner);
                    try {
                        SendStat(prot);
                    } catch (IOException ex) {
                        Logger.getLogger(ThreadClient.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
        }
    }
    public void TurnUpdated()
    {
        if (this.roomStat.Status==this.alThread.indexOf(this)+1)
        {
            protokol prot = new protokol();
            prot.setResponse(15);
            try {
                SendStat(prot);
            } catch (IOException ex) {
                Logger.getLogger(ThreadClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
            protokol prot = new protokol();
            prot.setResponse(14);
            prot.setTurn(this.roomStat.Turn);
            try {
                SendStat(prot);
            } catch (IOException ex) {
                Logger.getLogger(ThreadClient.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
    }
    
    public void CountUpdated()
    {
        protokol prot = new protokol();
        prot.setResponse(12);
        prot.setSkor(this.roomStat.Count);
        prot.setUser(this.roomStat.Username);
        try {
            BroadcastStat(prot);
        } catch (IOException ex) {
            Logger.getLogger(ThreadClient.class.getName()).log(Level.SEVERE, null, ex);
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
    public void SendUserList() throws IOException
    {
        protokol prot1=new protokol();
        prot1.setResponse(10);
        String User[]=this.roomStat.Username;
        prot1.setUser(User);
        BroadcastStat(prot1);
    }
    public void SendServerMsg(String MessageString) throws IOException
    {
        message msg=new message();
        msg.setFrom("Server");
        msg.setMessageString(MessageString);
        ous.writeObject(msg);
        ous.flush();
        ous.reset();
    }
    public void BroadcastServerMsg(String MessageString) throws IOException
    {
        for(int i=0;i<this.alThread.size();i++)
        {
            this.alThread.get(i).SendServerMsg(MessageString);
        }
    }
}
