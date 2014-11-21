package com.easytasks.persistencia.entidades;

import com.easytasks.persistencia.entidades.Contexto;
import com.easytasks.persistencia.entidades.Usuario;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-11-21T11:11:12")
@StaticMetamodel(Proyecto.class)
public class Proyecto_ { 

    public static volatile SingularAttribute<Proyecto, Usuario> responsable;
    public static volatile SingularAttribute<Proyecto, Date> fechaInicio;
    public static volatile SingularAttribute<Proyecto, Long> id;
    public static volatile ListAttribute<Proyecto, Usuario> usuarios;
    public static volatile SingularAttribute<Proyecto, String> nombre;
    public static volatile SingularAttribute<Proyecto, Date> fechaFin;
    public static volatile SingularAttribute<Proyecto, Contexto> contexto;

}