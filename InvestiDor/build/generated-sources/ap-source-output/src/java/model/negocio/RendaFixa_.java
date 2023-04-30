package src.java.model.negocio;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import src.java.model.negocio.Usuario;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2023-04-27T20:45:57")
@StaticMetamodel(RendaFixa.class)
public class RendaFixa_ { 

    public static volatile SingularAttribute<RendaFixa, Double> valorUnitarioCompra;
    public static volatile SingularAttribute<RendaFixa, String> tipo;
    public static volatile SingularAttribute<RendaFixa, Double> valorUnitarioAtual;
    public static volatile SingularAttribute<RendaFixa, Boolean> imposto;
    public static volatile SingularAttribute<RendaFixa, String> nome;
    public static volatile SingularAttribute<RendaFixa, Usuario> usuario;
    public static volatile SingularAttribute<RendaFixa, Integer> id;
    public static volatile SingularAttribute<RendaFixa, Date> dataInicial;
    public static volatile SingularAttribute<RendaFixa, String> indexador;
    public static volatile SingularAttribute<RendaFixa, Double> quantidade;
    public static volatile SingularAttribute<RendaFixa, Date> dataFinal;

}