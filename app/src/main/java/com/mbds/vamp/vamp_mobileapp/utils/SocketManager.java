package com.mbds.vamp.vamp_mobileapp.utils;

import com.github.nkzawa.socketio.client.IO;

import java.net.URISyntaxException;

/**
 * Created by hamdigazzah on 18/02/2018.
 */

public class SocketManager {

    private com.github.nkzawa.socketio.client.Socket mSocket;

    ////////////////////////////////////////////////////////////////////////////////////////////
    // Connextion au serveur du véhicule
    ////////////////////////////////////////////////////////////////////////////////////////////

    public SocketManager() {
        try {
            this.mSocket = IO.socket(Config.SOCKET_SERVER_URL);
            this.mSocket.connect();
        }
        catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    public void lockCar() {
        mSocket.emit("lock", 1);
    }

    public void unlockCar() {
        mSocket.emit("lock", 0);
    }

    public void startCar() {
        mSocket.emit("start", 1);
    }

    public void stopCar() {
        mSocket.emit("start", 0);
    }

    public void disconnect() {
        mSocket.disconnect();
    }

}
