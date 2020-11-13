package com.atar.host.app.beans;

/**
 * @author：atar
 * @date: 2020/11/6
 * @description:
 */
public class MenuItemBean {
    private String itemName;
    private int itemID;

    public MenuItemBean(int itemID, String itemName) {
        this.itemName = itemName;
        this.itemID = itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
