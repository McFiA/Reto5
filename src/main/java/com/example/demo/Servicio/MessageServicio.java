/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.Servicio;

import com.example.demo.Modelo.Message;
import com.example.demo.Repositorio.MessageRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author carlo
 */
@Service
public class MessageServicio {
    @Autowired
    private MessageRepositorio messageRepositorio;
    
    public List<Message> getAll() {
        return messageRepositorio.getAll();
    }
    
    public Optional<Message> getMessage (int messageId){
        return messageRepositorio.getMessage(messageId);
    }
    
    public Message save(Message message){
        if (message.getIdMessage() == null) {
            return messageRepositorio.save(message);
        }else {
            Optional<Message> e = messageRepositorio.getMessage(message.getIdMessage());
            if (!e.isPresent()) {
                return message;
            }else {
                return messageRepositorio.save(message);
            }
        }
    }
    
    public Message update(Message message){
        if(message.getIdMessage()!=null){
            Optional<Message> e= messageRepositorio.getMessage(message.getIdMessage());
            if(e.isPresent()){
                if(message.getMessageText()!=null){
                    e.get().setMessageText(message.getMessageText());
                }
                messageRepositorio.save(e.get());
                return e.get();
            }else{
                return message;
            }
        }else{
            return message;
        }
    }

        public boolean deleteMessage(int messageId){
        Boolean d=getMessage(messageId).map(message -> {
            messageRepositorio.delete(message);
            return true;
    }).orElse(false);
        return d;
    }
}

