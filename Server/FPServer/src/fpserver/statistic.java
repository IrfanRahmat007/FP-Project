package fpserver;

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
        this.Turn=1;
        this.TableReady=0;
        this.TableIndex=new int[3];
    }
    
    public void UpdateStatus(int status)
    {
        this.Status=status;
        for (int i=0;i<allThread.size();i++)
        {
            allThread.get(i).StatusUpdated();
        }
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
        if(this.Count[playerIndex]==2)
        {
            UpdateStatus(4);
        }
    }
    public void AssignTable(int playerIndex,int X, int Y)
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
    public void SeekTable(int playerIndex, int X, int Y)
    {
        if(this.Table[TableIndex[playerIndex]][Y][X]==1)
        {
            UpdateWinCounter(playerIndex);
        }
    }
    public void SetNewTableIndex(int playerIndex)
    {
        TableIndex[playerIndex]++;
        if(TableIndex[playerIndex]==playerIndex)
        {
            TableIndex[playerIndex]++;
        }
    }
    public void ChangeTurn()
    {
        Turn++;
        if(Turn==3)
        {
            Turn=1;
        }
        for (int i=0;i<allThread.size();i++)
        {
            allThread.get(i).TurnUpdated();
        }
    }
}
