package com.template.validator;

public interface Validator<T> {
    boolean validar (T valor);
    String getMesageErro();
    T getValor();
}
