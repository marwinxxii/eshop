/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.ifmo.eshop;

import java.sql.SQLException;
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
}
