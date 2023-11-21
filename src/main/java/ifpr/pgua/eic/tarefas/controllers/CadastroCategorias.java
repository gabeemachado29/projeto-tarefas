package ifpr.pgua.eic.tarefas.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.tarefas.App;
import ifpr.pgua.eic.tarefas.model.entities.Categorias;
import ifpr.pgua.eic.tarefas.model.repositories.RepositorioCategorias;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

public class CadastroCategorias implements Initializable{

    @FXML
    private TextField tfNome;
    
    @FXML
    private TextField tfDescricao;

    private RepositorioCategorias repositorio;

    private Categorias antigo;

    public CadastroCategorias(RepositorioCategorias repositorio){
        this.repositorio = repositorio;
    }
     public CadastroCategorias(RepositorioCategorias repositorio, Categorias categorias){
        this.repositorio = repositorio;
        this.antigo = categorias;
    }
    @FXML
    void cadastrar(ActionEvent event) {
        String nome = tfNome.getText();
        String descricao = tfDescricao.getText();
        
        Resultado resultado;
        if(antigo == null){
            resultado = repositorio.cadastrarCategorias(nome, descricao);
        }
        else{
            resultado = repositorio.atualizarCategoria(antigo.getId(), nome, descricao);
        }

        Alert alert;

        if (resultado.foiErro()) {
            alert = new Alert(AlertType.ERROR, resultado.getMsg());
        } else {
            alert = new Alert(AlertType.INFORMATION, resultado.getMsg());
        }

        alert.showAndWait();
    }

    @FXML
    void voltar(ActionEvent event) {
        App.popScreen();
    }
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        
        if(antigo != null){
            tfNome.setText(antigo.getNome());
            tfDescricao.setText(antigo.getDescricao());
        }
    }
}
