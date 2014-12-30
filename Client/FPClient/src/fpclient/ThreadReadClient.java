/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpclient;

import fp.message;
import fp.protokol;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;

/**
 *
 * @author ASUS-PC
 */
public class ThreadReadClient extends Thread{
    private Socket sock;
    private ObjectInputStream ois;
    private JTextArea txtReceived;
    FrmMain MainForm;
    public ThreadReadClient(Socket sock, ObjectInputStream ois, FrmMain MainForm)
    {
        this.sock = sock;
        this.ois = ois;
        this.MainForm=MainForm;
    }
    @Override
    public void run()
    {
        System.out.println("Masuk Thread");
        
        while(!this.sock.isClosed())
        {
            Object res;
            try {
                res=ois.readObject();
                if(res instanceof message)
                {
                    System.out.println("ada message");
                    message msg=(message) res;
                    MainForm.WriteLog(msg.getFrom()+" : "+msg.getMessageString()+"\n");
                    
                }
                else if(res instanceof protokol)
                {
                    protokol prot=(protokol)res;
                    switch(prot.getResponse())
                    {
                        case 11:
                            MainForm.Status=2;
                            MainForm.StatusUpdated();
                            break;
                        case 12:
                            this.MainForm.UpdateScore(prot.getUser(), prot.getSkor());
                            break;
                        case 13:
                            break;
                        case 14:
                            break;
                        case 15:
                        case 17:
                            this.sock.close();
                            break;
                        case 10:
                            ListUser(prot);
                        default:
                            break;
                    }
                }
                else
                {
                    res=null;
                }
            } catch (IOException ex) {
                Logger.getLogger(ThreadReadClient.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ThreadReadClient.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
    public void ListUser(protokol prot)
    {
        String usernames;
        usernames="\nOnline User : \n";
        for(int i=0;i<prot.getUser().length;i++)
        {
            int index=i+1;
            usernames=usernames+index+". "+prot.getUser()[i]+"\n";
        }
        usernames=usernames+"\n";
        this.MainForm.WriteLog(usernames);
    }
}
