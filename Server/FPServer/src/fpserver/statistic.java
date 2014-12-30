package fpserver;

import static java.lang.Math.random;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ASUS-PC
 */
public class statistic {
    int Table[][][];
    int TableIndex[];
    int TableReady;
    int Turn;
    int Count[];
    int Winner;
    String Username[];
    ArrayList <ThreadClient> allThread;
    int Ready;
    int Status;
    //1. Waiting for player
    //2. Pick a hiding place
    //3. Seek
    //4. Game Over
    public statistic(ArrayList<ThreadClient> allThread)
    {
        this.allThread=allThread;
        this.Table= new int[3][3][3];
        this.Count = new int[3];
        this.Status=1;
        this.Ready=0;
        this.Turn=(int)random()%2;
        this.TableReady=0;
        this.Username=new String[3];
        this.TableIndex=new int[3];
        this.TableIndex[0]=0;
        this.TableIndex[1]=0;
        this.TableIndex[2]=0;
        this.Winner=-1;
    }
    
    public void UpdateStatus(int status)
    {
        this.Status=status;
        for (int i=0;i<allThread.size();i++)
        {
            allThread.get(i).StatusUpdated();
        }
    }
    public void SetUsername(int playerIndex, String Username)
    {
        this.Username[playerIndex]=Username;
    }
    public void IncrementReady()
    {
        this.Ready++;
        if(this.Ready==3)
        {
            UpdateStatus(this.Status+1);
        }
    }
    public void UpdateWinCounter(int playerIndex)
    {
        this.Count[playerIndex]++;
        for (int i=0;i<allThread.size();i++)
        {
            allThread.get(i).CountUpdated();
        }
        if(this.Count[playerIndex]==2)
        {
            this.Winner=playerIndex;
            UpdateStatus(4);
            
        }
    }
    public void HideTable(int playerIndex,int X, int Y)
    {
        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                this.Table[playerIndex][i][j]=0;
            }
        }
        this.Table[playerIndex][Y][X]=1;
        TableReady++;
        if(TableReady==3)
        {
            UpdateStatus(3);
        }
    }
    public boolean SeekTable(int playerIndex, int X, int Y)
    {
        if(this.Table[TableIndex[playerIndex]][Y][X]==1)
        {
            UpdateWinCounter(playerIndex);
            SetNewTableIndex(playerIndex);
            return true;
        }
        ChangeTurn();
        return false;
    }
    public void SetNewTableIndex(int playerIndex)
    {
        TableIndex[playerIndex]++;
        if(TableIndex[playerIndex]==3)
        {
            TableIndex[playerIndex]=0;
        }
    }
    public void ChangeTurn()
    {
        Turn++;
        if(Turn==3)
        {
            Turn=0;
        }
        for (int i=0;i<allThread.size();i++)
        {
            allThread.get(i).TurnUpdated();
        }
    }
}
