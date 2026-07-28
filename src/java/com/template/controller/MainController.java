package com.template.controller;

import com.template.model.EletronicoDAO;
import com.template.model.EletronicoDTO;
import com.template.util.DialogUtil;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import java.util.ArrayList;

public class MainController {

    @FXML private Button btnSalvar;
    @FXML private Button btnAtualizar;
    @FXML private Button btnExcluir;
    @FXML private Button btnLimpar;

    @FXML private TableView<EletronicoDTO> tblEletronico;
    @FXML private TableColumn<EletronicoDTO, Integer> colId;
    @FXML private TableColumn<EletronicoDTO, String> colNome;
    @FXML private TableColumn<EletronicoDTO, String> colCor;
    @FXML private TableColumn<EletronicoDTO, String> colModelo;

    @FXML private TextField txtId;
    @FXML private TextField txtNome;
    @FXML private TextField txtModelo;
    @FXML private ComboBox<String> cbxCor;
    @FXML private Label lblMensagem;

    // Mantivemos este método para mensagens sutis e rápidas na interface (UX)
    private void exibirMensagem(String texto, boolean sucesso) {
        lblMensagem.setText(texto);
        if (sucesso) {
            lblMensagem.setStyle("-fx-text-fill: #27ae60; -fx-font-weight: bold;");
        } else {
            lblMensagem.setStyle("-fx-text-fill: #c0392b; -fx-font-weight: bold;");
        }
    }

    private boolean camposValidos() {
        if (txtNome.getText().isEmpty() || txtModelo.getText().isEmpty() || cbxCor.getValue() == null) {
            DialogUtil.showWarning("Atenção", "Preencha todos os campos obrigatórios!");
            return false;
        }
        return true;
    }

    @FXML
    private void btnSalvarAction(ActionEvent event) {
        if (!camposValidos()) return;

        try {
            EletronicoDTO obj = new EletronicoDTO();
            obj.setNomeEletronico(txtNome.getText());
            obj.setModelo(txtModelo.getText());
            obj.setCor(cbxCor.getValue());

            EletronicoDAO dao = new EletronicoDAO();
            dao.cadastrarEletronico(obj);

            DialogUtil.showInfo("Eletrônico cadastrado com sucesso!");
            btnLimparAction(null);
            carregarEletronicos();
            exibirMensagem("Pronto para um novo cadastro.", true);

        } catch (Exception e) {
            DialogUtil.showError("Erro ao Salvar", "Ocorreu um erro no banco de dados.\nDetalhes: " + e.getMessage());
        }
    }

    @FXML
    private void btnAtualizarAction(ActionEvent event) {
        if (txtId.getText().isEmpty() || !camposValidos()) return;

        try {
            EletronicoDTO obj = new EletronicoDTO();
            obj.setId(Integer.parseInt(txtId.getText()));
            obj.setNomeEletronico(txtNome.getText());
            obj.setModelo(txtModelo.getText());
            obj.setCor(cbxCor.getValue());

            EletronicoDAO dao = new EletronicoDAO();
            dao.atualizarEletronico(obj);

            DialogUtil.showInfo("Eletrônico atualizado com sucesso!");
            btnLimparAction(null);
            carregarEletronicos();

        } catch (Exception e) {
            DialogUtil.showError("Erro ao Atualizar", "Não foi possível atualizar o registro.\nDetalhes: " + e.getMessage());
        }
    }

    @FXML
    private void btnExcluirAction(ActionEvent event) {
        EletronicoDTO selecionado = tblEletronico.getSelectionModel().getSelectedItem();

        if (selecionado != null) {
            boolean confirmou = DialogUtil.showConfirmation(
                    "Confirmar Exclusão",
                    "Você está prestes a excluir o eletrônico:\n" + selecionado.getNomeEletronico() + "\n\nTem certeza que deseja continuar?"
            );

            if (confirmou) {
                try {
                    EletronicoDAO dao = new EletronicoDAO();
                    dao.excluirEletronico(selecionado.getId());

                    DialogUtil.showInfo("Eletrônico excluído com sucesso!");
                    btnLimparAction(null);
                    carregarEletronicos();
                } catch (Exception e) {
                    DialogUtil.showError("Erro ao Excluir", "Houve um problema ao excluir o item.\nDetalhes: " + e.getMessage());
                }
            } else {
                exibirMensagem("Exclusão cancelada pelo usuário.", false);
            }
        }
    }

    @FXML
    private void carregarCampos(MouseEvent event) {
        EletronicoDTO selecionado = tblEletronico.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            txtId.setText(String.valueOf(selecionado.getId()));
            txtNome.setText(selecionado.getNomeEletronico());
            txtModelo.setText(selecionado.getModelo());
            cbxCor.setValue(selecionado.getCor());
            exibirMensagem("Modo de Edição ativado.", true);
        }
    }

    @FXML
    private void btnLimparAction(ActionEvent event) {
        txtId.clear();
        txtNome.clear();
        txtModelo.clear();
        cbxCor.getSelectionModel().clearSelection();
        tblEletronico.getSelectionModel().clearSelection();

        if (lblMensagem.getText().equals("Modo de Edição ativado.")) {
            lblMensagem.setText("");
        }
    }

    @FXML
    private void carregarEletronicos() {
        try {
            EletronicoDAO dao = new EletronicoDAO();
            ArrayList<EletronicoDTO> lista = dao.listarEletronicos();
            tblEletronico.setItems(FXCollections.observableArrayList(lista));
        } catch (Exception e) {
            DialogUtil.showError("Erro de Conexão", "Não foi possível carregar a lista de eletrônicos do banco de dados.");
        }
    }

    @FXML
    private void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nomeEletronico"));
        colModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        colCor.setCellValueFactory(new PropertyValueFactory<>("cor"));

        cbxCor.getItems().addAll("Preto", "Branco", "Prata", "Cinza Escuro", "Azul", "Vermelho");

        tblEletronico.getSelectionModel().selectedItemProperty().addListener((obs, selecaoAntiga, selecaoNova) -> {
            if (selecaoNova != null) {
                btnAtualizar.setDisable(false);
                btnExcluir.setDisable(false);
                btnSalvar.setDisable(true);
            } else {
                btnAtualizar.setDisable(true);
                btnExcluir.setDisable(true);
                btnSalvar.setDisable(false);
            }
        });

        carregarEletronicos();
    }
}