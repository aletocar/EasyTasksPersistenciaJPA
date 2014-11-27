package com.easytasks.persistencia.entidades;

import com.easytasks.persistencia.entidades.Etiqueta;
import com.easytasks.persistencia.entidades.Proyecto;
import com.easytasks.persistencia.entidades.Tarea;
import com.easytasks.persistencia.entidades.Usuario;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-11-27T10:31:05")
@StaticMetamodel(Tarea.class)
public class Tarea_ { 

    public static volatile ListAttribute<Tarea, Etiqueta> listaEtiquetas;
    public static volatile SingularAttribute<Tarea, Date> fechaLimite;
    public static volatile SingularAttribute<Tarea, Proyecto> proyecto;
    public static volatile SingularAttribute<Tarea, Date> fechaCreacion;
    public static volatile ListAttribute<Tarea, Tarea> subtareas;
    public static volatile SingularAttribute<Tarea, Long> id;
    public static volatile SingularAttribute<Tarea, String> nombre;
    public static volatile SingularAttribute<Tarea, Boolean> completado;
    public static volatile SingularAttribute<Tarea, Usuario> realizador;
    public static volatile ListAttribute<Tarea, Usuario> listaResponsables;
    public static volatile SingularAttribute<Tarea, Integer> prioridad;

}