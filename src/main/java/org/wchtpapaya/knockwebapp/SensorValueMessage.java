package org.wchtpapaya.knockwebapp;

import lombok.Getter;

@Getter
public class SensorValueMessage implements Message {
    private final int value;

    public SensorValueMessage(int value) {
        this.value = value;
    }
}
