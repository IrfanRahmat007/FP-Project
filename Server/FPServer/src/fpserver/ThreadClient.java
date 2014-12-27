/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpserver;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;

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

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public ThreadClient(Socket sockClient, ArrayList<ThreadClient> alThread)
    {
        this.sockClient=sockClient;
        this.alThread=alThread;
        this.sa = sockClient.getRemoteSocketAddress();
    }
}
