package com.example.helloworld.messages;

public class MessageNotFoundException extends Throwable {
    public MessageNotFoundException(String message) {
        super(message);
    }
}
