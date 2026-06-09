package com.template;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import java.util.ArrayList;
import java.util.Optional;

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

    @FXML
    private void initialize() {
        //Conecta as colunas da tabela com os atributos dos objetos
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nomeEletronico"));
        colModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        colCor.setCellValueFactory(new PropertyValueFactory<>("cor"));

        //Preenche as opções do ComboBox (UX)
        cbxCor.getItems().addAll("Preto", "Branco", "Prata", "Cinza Escuro", "Azul", "Vermelho");

        //Jeito de criar uma função resumida
        tblEletronico.getSelectionModel().selectedItemProperty().addListener((obs, selecaoAntiga, selecaoNova) -> {
            if (selecaoNova != null) {
                btnAtualizar.setDisable(false);
                btnExcluir.setDisable(false);
                btnSalvar.setDisable(true); // Bloqueia o salvar enquanto edita
            } else {
                btnAtualizar.setDisable(true);
                btnExcluir.setDisable(true);
                btnSalvar.setDisable(false); // Libera o salvar para novos itens
            }
        });

        carregarEletronicos();
    }

    // Método auxiliar para colorir as mensagens do Label (UI)
    private void exibirMensagem(String texto, boolean sucesso) {
        lblMensagem.setText(texto);
        if (sucesso) {
            lblMensagem.setStyle("-fx-text-fill: #27ae60; -fx-font-weight: bold;"); // Verde
        } else {
            lblMensagem.setStyle("-fx-text-fill: #c0392b; -fx-font-weight: bold;"); // Vermelho
        }
    }

    // Método auxiliar de validação (UX)
    private boolean camposValidos() {
        if (txtNome.getText().isEmpty() || txtModelo.getText().isEmpty() || cbxCor.getValue() == null) {
            exibirMensagem("Erro: Preencha todos os campos obrigatórios!", false);
            return false;
        }
        return true;
    }

    @FXML
    private void btnSalvarAction(ActionEvent event) {
        if (!camposValidos()) return;

        EletronicoDTO obj = new EletronicoDTO();
        obj.setNomeEletronico(txtNome.getText());
        obj.setModelo(txtModelo.getText());
        obj.setCor(cbxCor.getValue());

        EletronicoDAO dao = new EletronicoDAO();
        dao.cadastrarEletronico(obj);

        exibirMensagem("Eletrônico cadastrado com sucesso!", true);
        btnLimparAction(null);
        carregarEletronicos();
    }

    @FXML
    private void btnAtualizarAction(ActionEvent event) {
        if (txtId.getText().isEmpty() || !camposValidos()) return;

        EletronicoDTO obj = new EletronicoDTO();
        obj.setId(Integer.parseInt(txtId.getText()));
        obj.setNomeEletronico(txtNome.getText());
        obj.setModelo(txtModelo.getText());
        obj.setCor(cbxCor.getValue());

        EletronicoDAO dao = new EletronicoDAO();
        dao.atualizarEletronico(obj);

        exibirMensagem("Eletrônico atualizado com sucesso!", true);
        btnLimparAction(null);
        carregarEletronicos();
    }

    @FXML
    private void btnExcluirAction(ActionEvent event) {
        EletronicoDTO selecionado = tblEletronico.getSelectionModel().getSelectedItem();

        if (selecionado != null) {
            // Regra de UX: Solicitar confirmação antes de excluir
            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
            alerta.setTitle("Confirmar Exclusão");
            alerta.setHeaderText("Você está prestes a excluir o eletrônico: " + selecionado.getNomeEletronico());
            alerta.setContentText("Tem certeza que deseja continuar?");

            Optional<ButtonType> resultado = alerta.showAndWait();
            if (resultado.isPresent() && resultado.get() == ButtonType.OK) {

                EletronicoDAO dao = new EletronicoDAO();
                dao.excluirEletronico(selecionado.getId());

                exibirMensagem("Eletrônico excluído com sucesso!", true);
                btnLimparAction(null);
                carregarEletronicos();
            } else {
                exibirMensagem("Exclusão cancelada.", false);
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

        // Só limpa a mensagem se ela não for de sucesso/erro após uma ação
        if (lblMensagem.getText().equals("Modo de Edição ativado.")) {
            lblMensagem.setText("");
        }
    }

    @FXML
    private void carregarEletronicos() {
        EletronicoDAO dao = new EletronicoDAO();
        ArrayList<EletronicoDTO> lista = dao.listarEletronicos();
        tblEletronico.setItems(FXCollections.observableArrayList(lista));
    }
}