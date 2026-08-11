package com.template.validator;

import com.template.util.DialogUtil;

public class EletronicoValidator {

    public static boolean validarCamposObrigatorios(String nome, String modelo, String cor) {

        if (nome == null || nome.trim().isEmpty() ||
                modelo == null || modelo.trim().isEmpty() ||
                cor == null || cor.trim().isEmpty()) {

            DialogUtil.showWarning("Atenção", "Preencha todos os campos obrigatórios!");
            return false;
        }

        return true;
    }
}