package ifpr.pgua.eic.tarefas.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.tarefas.App;
import ifpr.pgua.eic.tarefas.model.entities.Tarefas;
import ifpr.pgua.eic.tarefas.model.repositories.RepositorioCategorias;
import ifpr.pgua.eic.tarefas.model.repositories.RepositorioTarefas;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;

public class ListarTarefas implements Initializable {

    @FXML
    private TableView<Tarefas> tbTarefas;

    @FXML
    private TableColumn<Tarefas, String> tbcTitulo;

    @FXML
    private TableColumn<Tarefas, String> tbcDescricao;

    @FXML
    private TableColumn<Tarefas, String> tbcDtprazo;

    @FXML
    private TableColumn<Tarefas, String> tbcCategoria;

    @FXML
    private TableColumn<Tarefas, String> tbcId;
    
    private RepositorioTarefas repositorio;

    private RepositorioCategorias repositorioCategorias;
    
    public ListarTarefas(RepositorioTarefas repositorio, RepositorioCategorias repositorioCategorias){
        this.repositorio = repositorio;
        this.repositorioCategorias = repositorioCategorias;
    }

    @FXML
    void voltar(ActionEvent event) {
        App.popScreen();
    }

    @FXML
    private void editar(){
        Tarefas tarefas = tbTarefas.getSelectionModel().getSelectedItem();
        
        if(tarefas != null){
            App.pushScreen("CADASTROTAREFAS", o -> new CadastroTarefas(repositorio, repositorioCategorias, tarefas));
        }
    }

    @FXML
    private void deletar(){
        Tarefas tarefas = tbTarefas.getSelectionModel().getSelectedItem();
        Resultado resultado = repositorio.deletaTarefa(tarefas.getId());
        Alert alert;
        if(tarefas != null){
            alert = new Alert(AlertType.INFORMATION, resultado.getMsg());
            repositorio.deletaTarefa(tarefas.getId());
        }
    }
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        
        Resultado resultado = repositorio.listarTarefas();

        tbcId.setCellValueFactory(celula->new SimpleStringProperty(celula.getValue().getId()+""));
        tbcTitulo.setCellValueFactory(celula->new SimpleStringProperty(celula.getValue().getTitulo()));
        tbcDescricao.setCellValueFactory(celula->new SimpleStringProperty(celula.getValue().getDescricao()));
        tbcDtprazo.setCellValueFactory(celula->new SimpleStringProperty(celula.getValue().getDtprazo()+""));
        tbcCategoria.setCellValueFactory(celula->new SimpleStringProperty(celula.getValue().getCategoria().getNome()));
        

        if(resultado.foiErro()){
            Alert alert = new Alert(AlertType.ERROR, resultado.getMsg());
            alert.showAndWait();
        }else{
            List<Tarefas> lista = (List)resultado.comoSucesso().getObj();
            tbTarefas.getItems().addAll(lista);
        }
        
    }
    
}
