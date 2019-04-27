package com.mapa;

import com.mapa.service.AccountManager;
import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        var accountManager = AccountManager.getInstance();
        accountManager.Login();
    }
}
