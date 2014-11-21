package com.easytasks.persistencia.entidades;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class Actividad extends Realizable implements Serializable{ 

    @NotNull
    private boolean automatica;

    public boolean isAutomatica() {
        return automatica;
    }

    public void setAutomatica(boolean automatica) {
        this.automatica = automatica;
    }

    //TODO: Chequear que en el contstructor no haya que hacer nada.
    public Actividad() {
    }

    @Override
    public String toString() {
        return getNombre();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Actividad)) {
            return false;
        }
        Actividad other = (Actividad) obj;
        return other.getId() == this.getId();
    }

    @Override
    public int hashCode() {
        return this.getId() != null ? this.getId().intValue() : null;
    }
}
