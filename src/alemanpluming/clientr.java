/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package alemanpluming;

import java.util.Date;

/**
 *
 * @author dsoto
 */
public class clientr {
    
    private String OrderBy;
    private String Address;
    private Date date;

    public clientr(String OrderBy, String Address, Date date) {
        this.OrderBy = OrderBy;
        this.Address = Address;
        this.date = date;
    }

    public clientr() {
    }

    public String getOrderBy() {
        return OrderBy;
    }

    public void setOrderBy(String OrderBy) {
        this.OrderBy = OrderBy;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    
}
