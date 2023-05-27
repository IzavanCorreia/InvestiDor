/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.java.model.validacao;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PontoDecimalValidator implements ConstraintValidator<PontoDecimal, Double> {
    @Override
    public void initialize(PontoDecimal constraintAnnotation) {
    }

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Valor nulo é considerado válido, outras anotações podem ser usadas para exigir valor não nulo
        }

        String valueString = String.valueOf(value);
        return valueString.matches("\\d+(\\.\\d+)?"); // Aceita valores inteiros ou com ponto decimal
    }
}

