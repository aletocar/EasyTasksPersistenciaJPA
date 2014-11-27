package com.easytasks.persistencia.entidades;

import com.easytasks.persistencia.entidades.Usuario;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-11-26T19:30:14")
@StaticMetamodel(Token.class)
public class Token_ { 

    public static volatile SingularAttribute<Token, Usuario> usuario;
    public static volatile SingularAttribute<Token, Long> id;
    public static volatile SingularAttribute<Token, String> token;

}