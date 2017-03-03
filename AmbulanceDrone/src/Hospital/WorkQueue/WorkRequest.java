/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Hospital.WorkQueue;

import Hospital.UserAccount.UserAccount;
import java.util.Date;

/**
 *
 * @author Vasanti
 */
public abstract class WorkRequest {

    private String message;
    private UserAccount sender;
    private UserAccount receiver;
    private String status;
    
    
    public WorkRequest(){
        
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserAccount getSender() {
        return sender;
    }

    public void setSender(UserAccount sender) {
        this.sender = sender;
    }

    public UserAccount getReceiver() {
        return receiver;
    }

    public void setReceiver(UserAccount receiver) {
        this.receiver = receiver;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return sender.getEmployee().getName();
    }

    

   
}
