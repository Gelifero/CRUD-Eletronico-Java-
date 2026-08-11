package com.template.controller.service;

import com.template.model.EletronicoDTO;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class Layout {

    public static void carregarCampos(EletronicoDTO selecionado, TextField txtId, TextField txtNome, TextField txtModelo, ComboBox<String> cbxCor, Label lblMensagem) {

        if (selecionado != null) {
            txtId.setText(String.valueOf(selecionado.getId()));
            txtNome.setText(selecionado.getNomeEletronico());
            txtModelo.setText(selecionado.getModelo());
            cbxCor.setValue(selecionado.getCor());

            // A mensagem verde entra aqui
            lblMensagem.setText("Modo de Edição ativado.");
            lblMensagem.setStyle("-fx-text-fill: #27ae60; -fx-font-weight: bold;");
        }
    }

    public static void limparCampos(TextField txtId, TextField txtNome, TextField txtModelo, ComboBox<String> cbxCor, TableView<EletronicoDTO> tblEletronico, Label lblMensagem) {

        txtId.clear();
        txtNome.clear();
        txtModelo.clear();
        cbxCor.getSelectionModel().clearSelection();
        tblEletronico.getSelectionModel().clearSelection();

        if (lblMensagem.getText().equals("Modo de Edição ativado.")) {
            lblMensagem.setText("");
        }
    }

    public static void exibirMensagem(Label lblMensagem, String texto, boolean sucesso) {
        lblMensagem.setText(texto);
        if (sucesso) {
            lblMensagem.setStyle("-fx-text-fill: #27ae60; -fx-font-weight: bold;");
        } else {
            lblMensagem.setStyle("-fx-text-fill: #c0392b; -fx-font-weight: bold;");
        }
    }
}