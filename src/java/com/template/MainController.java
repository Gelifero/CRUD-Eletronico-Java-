package com.template;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.event.ActionEvent;
import java.util.ArrayList;


public class MainController
{
    @FXML private Button btnSalvar;
    @FXML private Button btnEditar;
    @FXML private Button btnExcluir;
    @FXML private Button btnAtualizar;
    @FXML private TableView<EletronicoDTO> tblEletronico;
    @FXML private TableColumn<EletronicoDTO, Integer> colId;
    @FXML private TableColumn<EletronicoDTO, String> colNome;
    @FXML private TableColumn<EletronicoDTO, String> colCor;
    @FXML private TableColumn<EletronicoDTO, String> colModelo;
    @FXML private TextField txtId;
    @FXML private TextField txtNome;
    @FXML private TextField txtCor;
    @FXML private TextField txtModelo;

    @FXML
    private void carregarEletronicos()
    {
        EletronicoDAO objEletronicoDAO = new EletronicoDAO();
        ArrayList<EletronicoDTO> listarEletronicos = objEletronicoDAO.listarEletronicos();
        tblEletronico.setItems(FXCollections.observableArrayList(listarEletronicos));
    }

    @FXML
    private void btnSalvarAction(ActionEvent event)
    {
        String nome = txtNome.getText();
        String modelo = txtModelo.getText();
        String cor = txtCor.getText();

        EletronicoDTO objEletronicoDTO = new EletronicoDTO();
        objEletronicoDTO.setNomeEletronico(nome);
        objEletronicoDTO.setCor(cor);
        objEletronicoDTO.setModelo(modelo);

        EletronicoDAO objEletronicoDAO = new EletronicoDAO();
        objEletronicoDAO.cadastrarEletronico(objEletronicoDTO);

        carregarEletronicos();
    }

    @FXML
    private void btnEditarAction(ActionEvent event) {
        EletronicoDTO selecionado = tblEletronico.getSelectionModel().getSelectedItem();

        if (selecionado != null) {
            txtId.setText(String.valueOf(selecionado.getId()));
            txtNome.setText(selecionado.getNomeEletronico());
            txtModelo.setText(selecionado.getModelo());
            txtCor.setText(selecionado.getCor());
        }
    }

    @FXML
    private void btnAtualizarAction(ActionEvent event) {
        if (txtId.getText().isEmpty()) return;

        EletronicoDTO objEletronicoDTO = new EletronicoDTO();
        objEletronicoDTO.setId(Integer.parseInt(txtId.getText()));
        objEletronicoDTO.setNomeEletronico(txtNome.getText());
        objEletronicoDTO.setModelo(txtModelo.getText());
        objEletronicoDTO.setCor(txtCor.getText());

        EletronicoDAO objEletronicoDAO = new EletronicoDAO();
        objEletronicoDAO.atualizarEletronico(objEletronicoDTO);

        carregarEletronicos();
    }

    @FXML
    private void btnExcluirAction(ActionEvent event) {
        EletronicoDTO selecionado = tblEletronico.getSelectionModel().getSelectedItem();

        if (selecionado != null) {
            EletronicoDAO objEletronicoDAO = new EletronicoDAO();
            objEletronicoDAO.excluirEletronico(selecionado.getId());

            carregarEletronicos();
        }
    }

    @FXML
    private void initialize()
    {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nomeEletronico"));
        colModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        colCor.setCellValueFactory(new PropertyValueFactory<>("cor"));
        carregarEletronicos();
    }

    @FXML
    private void carregarCampos()
    {
        EletronicoDTO objEletronicoDTO = tblEletronico.getSelectionModel().getSelectedItem();
        if (objEletronicoDTO != null)
        {
            txtNome.setText(objEletronicoDTO.getNomeEletronico());
            txtModelo.setText(objEletronicoDTO.getModelo());
            txtCor.setText(objEletronicoDTO.getCor());
        }
    }
}