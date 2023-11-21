package ifpr.pgua.eic.tarefas.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.tarefas.App;
import ifpr.pgua.eic.tarefas.model.entities.Categorias;
import ifpr.pgua.eic.tarefas.model.entities.Tarefas;
import ifpr.pgua.eic.tarefas.model.repositories.RepositorioCategorias;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

public class ListarCategorias implements Initializable{
    
    @FXML
    private ListView<Categorias> lstCategorias;

    private RepositorioCategorias repositorio;

    public ListarCategorias(RepositorioCategorias repositorio){
        this.repositorio = repositorio;
    }

    @FXML
    void voltar(ActionEvent event) {
        App.popScreen();
    }

    @FXML
    private void editar(){
        Categorias categorias = lstCategorias.getSelectionModel().getSelectedItem();

        if(categorias != null){
            App.pushScreen("CADASTROCATEGORIAS", o -> new CadastroCategorias(repositorio, categorias));
        }
    }

    @FXML
    private void deletar(){
        Categorias categorias = lstCategorias.getSelectionModel().getSelectedItem();

        if(categorias != null){
            repositorio.deletaCategoria(categorias.getId());
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        lstCategorias.getItems().clear();
        Resultado resultado = repositorio.listarCategorias();

        if(resultado.foiErro()){
            Alert alert = new Alert(AlertType.ERROR, resultado.getMsg());
            alert.showAndWait();
        }else{
            List lista = (List)resultado.comoSucesso().getObj();
            lstCategorias.getItems().addAll(lista);
        }
    
    }

}
