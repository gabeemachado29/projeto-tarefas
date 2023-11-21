package ifpr.pgua.eic.tarefas.controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.tarefas.App;
import ifpr.pgua.eic.tarefas.model.entities.Categorias;
import ifpr.pgua.eic.tarefas.model.entities.Tarefas;
import ifpr.pgua.eic.tarefas.model.repositories.RepositorioCategorias;
import ifpr.pgua.eic.tarefas.model.repositories.RepositorioTarefas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;

public class CadastroTarefas implements Initializable{
    @FXML
    private TextField tfTitulo;
    
    @FXML
    private TextField tfDescricao;

    @FXML
    private DatePicker dpDtprazo;

    @FXML
    private ComboBox<Categorias> cbCategoria;

    private Tarefas antigo;

    private RepositorioTarefas repositorio;
    private RepositorioCategorias repositorioCategorias;

    public CadastroTarefas(RepositorioTarefas repositorio, RepositorioCategorias repositorioCategorias){
        this.repositorio = repositorio;
        this.repositorioCategorias = repositorioCategorias;
    }

     public CadastroTarefas(RepositorioTarefas repositorio, RepositorioCategorias repositorioCategorias, Tarefas tarefas){
        this.repositorio = repositorio;
        this.repositorioCategorias = repositorioCategorias;
        this.antigo = tarefas;
    }

    @FXML
    void cadastrar(ActionEvent event) {
        String titulo = tfTitulo.getText();
        String descricao = tfDescricao.getText();
        LocalDate dtprazo = dpDtprazo.getValue();
        Categorias categorias = cbCategoria.getSelectionModel().getSelectedItem();

        Resultado resultado;
        if(antigo == null){
            resultado = repositorio.cadastrarTarefas(titulo, descricao, dtprazo, categorias);
        }
        else{
            resultado = repositorio.atualizarTarefa(antigo.getId(), titulo, descricao, dtprazo, categorias);
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
            tfTitulo.setText(antigo.getTitulo());
            tfDescricao.setText(antigo.getDescricao());
            dpDtprazo.setValue(antigo.getDtprazo());
            cbCategoria.getItems().addAll(antigo.getCategoria());
        }

        Resultado resultado = repositorioCategorias.listarCategorias();
        
        if(resultado.foiSucesso()){
            List<Categorias> listacategoria = (List<Categorias>)resultado.comoSucesso().getObj();
            cbCategoria.getItems().addAll(listacategoria);
        }
        else{
            Alert alert = new Alert(AlertType.ERROR, resultado.getMsg());
            alert.showAndWait();
        }
        

        
    }
}
