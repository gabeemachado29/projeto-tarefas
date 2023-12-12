package ifpr.pgua.eic.tarefas.controllers;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.tarefas.App;
import ifpr.pgua.eic.tarefas.model.entities.Usuario;
import ifpr.pgua.eic.tarefas.model.repositories.repositorioUsuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;

public class Login {

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfSenha;

    Usuario usuario;

    private repositorioUsuario repositorio;

    public Login(repositorioUsuario repositorio) {
        this.repositorio = repositorio;
    }
    
    @FXML
    private void cadastroUsuario(ActionEvent event){
        App.pushScreen("CADASTROUSUARIO");
    }

    @FXML
    private void confirmaUsuario(){
        String email = tfEmail.getText();
        String senha = tfSenha.getText();
        
        Resultado resultado = repositorio.login(email, senha);
        Alert alert;

        if(resultado.foiSucesso()){
            alert = new Alert(AlertType.INFORMATION, resultado.getMsg());
            App.pushScreen("PRINCIPAL");
        }
        else{
            alert = new Alert(AlertType.ERROR, resultado.getMsg());
        }

        alert.showAndWait();
    }

}
