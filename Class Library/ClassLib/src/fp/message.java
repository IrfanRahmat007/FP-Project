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
public class message implements Serializable {
    private String MessageString;
    private String From;

    /**
     * @return the MessageString
     */
    public String getMessageString() {
        return MessageString;
    }

    /**
     * @param MessageString the MessageString to set
     */
    public void setMessageString(String MessageString) {
        this.MessageString = MessageString;
    }

    /**
     * @return the From
     */
    public String getFrom() {
        return From;
    }

    /**
     * @param From the From to set
     */
    public void setFrom(String From) {
        this.From = From;
    }
}
