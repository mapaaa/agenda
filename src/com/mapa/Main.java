package com.mapa;

import com.mapa.service.AccountManager;

public class Main {

    public static void main(String[] args) {
        var accountManager = AccountManager.getInstance();
      //  accountManager.Login();
        accountManager.Register();
    }
}
