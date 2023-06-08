package src.java.model.validacao;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CPFValidator implements ConstraintValidator<CPF, String> {
    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext context) {
        if (cpf == null || cpf.isEmpty()) {
            return true; // Permite valores nulos ou vazios, use @NotNull ou @NotEmpty se necessário
        }

        cpf = cpf.replaceAll("[^0-9]", ""); // Remove caracteres não numéricos

        if (cpf.length() != 11) {
            return false;
        }

        // Verifica se todos os dígitos são iguais (caso contrário, não é um CPF válido)
        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        int digitoVerificador1 = Integer.parseInt(cpf.substring(9, 10));
        int digitoVerificador2 = Integer.parseInt(cpf.substring(10, 11));

        // Calcula o primeiro dígito verificador
        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += Integer.parseInt(cpf.substring(i, i + 1)) * (10 - i);
        }
        int resto = 11 - (soma % 11);
        int calculoDigito1 = (resto >= 10) ? 0 : resto;

        // Calcula o segundo dígito verificador
        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += Integer.parseInt(cpf.substring(i, i + 1)) * (11 - i);
        }
        resto = 11 - (soma % 11);
        int calculoDigito2 = (resto >= 10) ? 0 : resto;

        return (digitoVerificador1 == calculoDigito1 && digitoVerificador2 == calculoDigito2);
    }
}