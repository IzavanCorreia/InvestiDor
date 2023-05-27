
package src.java.model.validacao;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PontoDecimalValidator.class)
@Documented
public @interface PontoDecimal {
    String message() default "O valor deve usar ponto como divisor decimal";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
