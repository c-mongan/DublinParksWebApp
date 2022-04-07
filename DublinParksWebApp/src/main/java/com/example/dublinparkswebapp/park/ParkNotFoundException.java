package com.example.dublinparkswebapp.park;

public class ParkNotFoundException extends Throwable {
    public ParkNotFoundException(String message) {
        super(message);
    }
}
