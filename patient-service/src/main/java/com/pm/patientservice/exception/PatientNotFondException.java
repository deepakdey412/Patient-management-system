package com.pm.patientservice.exception;

public class PatientNotFondException extends RuntimeException{
    public PatientNotFondException(String message){
        super(message);
    }
}
