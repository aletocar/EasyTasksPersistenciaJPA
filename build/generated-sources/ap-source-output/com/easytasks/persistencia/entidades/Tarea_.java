package com.easytasks.persistencia.entidades;

import com.easytasks.persistencia.entidades.Realizable;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-11-21T20:10:03")
@StaticMetamodel(Tarea.class)
public class Tarea_ extends Realizable_ {

    public static volatile SingularAttribute<Tarea, Date> fechaLimite;
    public static volatile SingularAttribute<Tarea, Date> fechaCreacion;
    public static volatile ListAttribute<Tarea, Realizable> subtareas;

}