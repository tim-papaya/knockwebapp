package org.wchtpapaya.knockwebapp;

import lombok.Getter;
import lombok.Setter;

@Getter
public class StatusMessage {
    private final String statusName;
    private final int statusCode;

    public StatusMessage(String statusName, int statusCode) {
        this.statusName = statusName;
        this.statusCode = statusCode;
    }
}
