/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.Servicio;

import com.example.demo.Modelo.Reservation;
import com.example.demo.Repositorio.CountClient;
import com.example.demo.Repositorio.ReservationRepositorio;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author carlo
 */
@Service
public class ReservationServicio {
    @Autowired
    private ReservationRepositorio reservationRepositorio;
    
    public List<Reservation> getAll() {
        return reservationRepositorio.getAll();
    }
    
    public Optional<Reservation> getReservation (int reservationId){
        return reservationRepositorio.getReservation(reservationId);
    }
    
    public Reservation save(Reservation reservation){
        if (reservation.getIdReservation() == null) {
            return reservationRepositorio.save(reservation);
        }else {
            Optional<Reservation> e = reservationRepositorio.getReservation(reservation.getIdReservation());
            if (!e.isPresent()) {
                return reservation;
            }else {
                return reservationRepositorio.save(reservation);
            }
        }
    }
    
    public Reservation update(Reservation reservation){
        if(reservation.getIdReservation()!=null){
            Optional<Reservation> e= reservationRepositorio.getReservation(reservation.getIdReservation());
            if(e.isPresent()){

                if(reservation.getStartDate()!=null){
                    e.get().setStartDate(reservation.getStartDate());
                }
                if(reservation.getDevolutionDate()!=null){
                    e.get().setDevolutionDate(reservation.getDevolutionDate());
                }
                if(reservation.getStatus()!=null){
                    e.get().setStatus(reservation.getStatus());
                }
                reservationRepositorio.save(e.get());
                return e.get();
            }else{
                return reservation;
            }
        }else{
            return reservation;
        }
    }

        public boolean deleteReservation(int reservationId){
        Boolean d=getReservation(reservationId).map(reservation -> {
            reservationRepositorio.delete(reservation);
            return true;
    }).orElse(false);
        return d;
    }
///////////////Informe de reto 5

    public Status getReservationStatusReport(){
        List <Reservation> completed = reservationRepositorio.getReservationByStatus("completed");
        List <Reservation> cancelled = reservationRepositorio.getReservationByStatus("cancelled");
        return new Status (completed.size(), cancelled.size());
    } 
    public List<Reservation> informePeriodoTiempoReservas(String datoA, String datoB){
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-mm-dd");
        Date a = new Date();
        Date b = new Date();
        
        try {
        
            a=parser.parse(datoA);
            b=parser.parse(datoB);
        }catch(ParseException e){
            e.printStackTrace();
        }
        if(a.before(b)){
            return reservationRepositorio.informePeriodoTiempoReservas(a, b);
        }else{
            return new ArrayList<>();
        }
    }
    
    public List<CountClient> getTopClients(){
        return reservationRepositorio.getTopClient();
    }
}
