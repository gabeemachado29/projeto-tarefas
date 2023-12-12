package ifpr.pgua.eic.tarefas.controllers;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.tarefas.App;
import ifpr.pgua.eic.tarefas.model.repositories.repositorioUsuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;

public class CadastroUsuario {
    @FXML
    private TextField tfEmail;
    
    @FXML
    private TextField tfSenha;

    private repositorioUsuario repositorio;

    public CadastroUsuario(repositorioUsuario repositorio){
        this.repositorio = repositorio;
    }

    @FXML
    void cadastrar(ActionEvent event) {
        String email = tfEmail.getText();
        String senha = tfSenha.getText();
        
        Resultado resultado = repositorio.cadastrarUsurio(email, senha);
        
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
}
