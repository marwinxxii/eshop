/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.ifmo.eshop;

import java.sql.SQLException;
//import ru.ifmo.eshop.storage.Genre;
import ru.ifmo.eshop.storage.StorageManager;

/**
 *
 * @author alex
 */
public abstract class Eshop {

    public static StorageManager getStorageManager()
            throws ClassNotFoundException, SQLException {
        return new StorageManager("benderhost", 1521, "eshop", "eshop");
    }

    /*public static void main(String[] args) throws ClassNotFoundException, SQLException {
        for (Genre g:Eshop.getStorageManager().getLastGenres()) {
            System.out.println(g.getId());
            System.out.println(g.getTitle());
            System.out.println(g.getDescription());
        }
    }*/
}
