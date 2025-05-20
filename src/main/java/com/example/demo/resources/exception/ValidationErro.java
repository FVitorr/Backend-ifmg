package com.example.demo.resources.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.FieldError;

public class ValidationErro extends StandartErro{

    private List<FieldMessage> erros = new ArrayList<FieldMessage>();
    
    public ValidationErro(){}

    public List<FieldMessage> getFieldMessage (List<FieldMessage> fieldMessage){
        return erros;
    }

    public void setFieldMessage(List<FieldMessage> fieldMessage){
        this.erros = fieldMessage;
    }

    public void addFieldMessage(String field, String message){
        this.erros.add(new FieldMessage(field, message));
    }
    
}
