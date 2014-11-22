package com.easytasks.persistencia.entidades;

import com.easytasks.persistencia.entidades.Etiqueta;
import com.easytasks.persistencia.entidades.Usuario;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-11-22T15:15:58")
@StaticMetamodel(Realizable.class)
public class Realizable_ { 

    public static volatile ListAttribute<Realizable, Etiqueta> listaEtiquetas;
    public static volatile SingularAttribute<Realizable, Long> id;
    public static volatile SingularAttribute<Realizable, String> nombre;
    public static volatile SingularAttribute<Realizable, Boolean> completado;
    public static volatile ListAttribute<Realizable, Usuario> listaResponsables;
    public static volatile SingularAttribute<Realizable, Integer> prioridad;

}