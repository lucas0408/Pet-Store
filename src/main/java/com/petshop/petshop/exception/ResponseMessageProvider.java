package com.petshop.petshop.exception;

public interface ResponseMessageProvider {
    String getSuccessCreateMessage();
    String getSuccessUpdateMessage();
    String getSuccessDeleteMessage();
    String getSuccessRetrieveMessage();
    String getSuccessListMessage();
}
