/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.Servicio;

import com.example.demo.Modelo.Machine;
import com.example.demo.Repositorio.MachineRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author carlo
 */
@Service
public class MachineServicio {
    @Autowired
    private MachineRepositorio machineRepositorio;
    
    public List<Machine> getAll() {
        return machineRepositorio.getAll();
    }
    
    public Optional<Machine> getMachine (int machineId){
        return machineRepositorio.getMachine(machineId);
    }
    
    public Machine save(Machine machine){
        if (machine.getId() == null) {
            return machineRepositorio.save(machine);
        }else {
            Optional<Machine> e = machineRepositorio.getMachine(machine.getId());
            if (!e.isPresent()) {
                return machine;
            }else {
                return machineRepositorio.save(machine);
            }
        }
    }
    
    public Machine update(Machine machine){
        if(machine.getId()!=null){
            Optional<Machine> e= machineRepositorio.getMachine(machine.getId());
            if(e.isPresent()){
                if(machine.getName()!=null){
                    e.get().setName(machine.getName());
                }
                if(machine.getBrand()!=null){
                    e.get().setBrand(machine.getBrand());
                }
                if(machine.getYear()!=null){
                    e.get().setYear(machine.getYear());
                }
                if(machine.getDescription()!=null){
                    e.get().setDescription(machine.getDescription());
                }
                if(machine.getCategory()!=null){
                    e.get().setCategory(machine.getCategory());
                }
                machineRepositorio.save(e.get());
                return e.get();
            }else{
                return machine;
            }
        }else{
            return machine;
        }
    }

        public boolean deleteMachine(int machineId){
        Boolean d=getMachine(machineId).map(machine -> {
            machineRepositorio.delete(machine);
            return true;
    }).orElse(false);
        return d;
    }
}

