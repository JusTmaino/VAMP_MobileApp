package com.mbds.vamp.vamp_mobileapp.utils;

import android.util.Log;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.mbds.vamp.vamp_mobileapp.controllers.fragments.LocationFragment;

import java.net.URISyntaxException;

/**
 * Created by hamdigazzah on 18/02/2018.
 */

public class SocketManager {

    private com.github.nkzawa.socketio.client.Socket mSocket;
    public String position = "";

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

    public void lockCar(int isClicked) {
        mSocket.emit("lock", isClicked);
    }

    public void unlockCar(int isClicked) {
        mSocket.emit("unlock", isClicked);
    }

    public void startCar(int isClicked) {
        mSocket.emit("start", isClicked);
    }

    public void stopCar(int isClicked) {
        mSocket.emit("stop", isClicked);
    }

    public void horn(int isClicked) {
        mSocket.emit("horn", isClicked);
    }

    public void allWindowUp(int isClicked) {
        mSocket.emit("all_window_up", isClicked);
    }

    public void allWindowDown(int isClicked) {
        mSocket.emit("all_window_down", isClicked);
    }

    public void frontLeftWindowUp(int isClicked) {
        mSocket.emit("fl_window_up", isClicked);
    }

    public void frontLeftWindowDown(int isClicked) {
        mSocket.emit("fl_window_down", isClicked);
    }

    public void frontRightWindowUp(int isClicked) {
        mSocket.emit("fr_window_up", isClicked);
    }

    public void frontRightWindowDown(int isClicked) {
        mSocket.emit("fr_window_down", isClicked);
    }

    public void backLeftWindowUp(int isClicked) {
        mSocket.emit("bl_window_up", isClicked);
    }

    public void backLeftWindowDown(int isClicked) {
        mSocket.emit("bl_window_down", isClicked);
    }

    public void backRightWindowUp(int isClicked) {
        mSocket.emit("br_window_up", isClicked);
    }

    public void backRightWindowDown(int isClicked) {
        mSocket.emit("br_window_down", isClicked);
    }

    public void disconnect() {
        mSocket.disconnect();
    }

    /*public void getCarPosition() {
        mSocket.on("langLat", onNewMessage );
    }

    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            position = (String) args[0];
            Log.d("hello", position);

        }
    };*/

}
