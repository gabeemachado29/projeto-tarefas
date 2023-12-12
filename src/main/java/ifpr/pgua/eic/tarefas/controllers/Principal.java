package ifpr.pgua.eic.tarefas.controllers;

import ifpr.pgua.eic.tarefas.App;
import javafx.fxml.FXML;

public class Principal {
    @FXML
    private void cadastrarCategorias(){
        App.pushScreen("CADASTROCATEGORIAS");
    }

    @FXML
    private void listarCategorias(){
        App.pushScreen("LISTARCATEGORIAS");
    }

    @FXML
    private void cadastrarTarefas(){
        App.pushScreen("CADASTROTAREFAS");
    }

    @FXML
    private void listarTarefas(){
        App.pushScreen("LISTARTAREFAS");
    }

    @FXML
    private void login(){
        App.pushScreen("LOGIN");
    }
}
