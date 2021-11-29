package org.wchtpapaya.knockwebapp;

import java.util.TimerTask;

public class ResetTimer extends TimerTask {

    private Controller controller;

    public ResetTimer(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void run() {
        controller.resetTimer();
    }
}
