package ifpr.pgua.eic.tarefas;

import ifpr.pgua.eic.tarefas.controllers.CadastroCategorias;
import ifpr.pgua.eic.tarefas.controllers.CadastroTarefas;
import ifpr.pgua.eic.tarefas.controllers.ListarCategorias;
import ifpr.pgua.eic.tarefas.controllers.ListarTarefas;
import ifpr.pgua.eic.tarefas.controllers.Principal;
import ifpr.pgua.eic.tarefas.model.daos.CategoriasDAO;
import ifpr.pgua.eic.tarefas.model.daos.FabricaConexoes;
import ifpr.pgua.eic.tarefas.model.daos.JDBCCategoriasDAO;
import ifpr.pgua.eic.tarefas.model.daos.JDBCTarefasDAO;
import ifpr.pgua.eic.tarefas.model.daos.TarefasDAO;
import ifpr.pgua.eic.tarefas.model.repositories.RepositorioCategorias;
import ifpr.pgua.eic.tarefas.model.repositories.RepositorioTarefas;
import io.github.hugoperlin.navigatorfx.BaseAppNavigator;
import io.github.hugoperlin.navigatorfx.ScreenRegistryFXML;

/**
 * JavaFX App
 */
public class App extends BaseAppNavigator {

    private CategoriasDAO categoriasDAO = new JDBCCategoriasDAO(FabricaConexoes.getInstance());
    private RepositorioCategorias repositorioCategorias = new RepositorioCategorias(categoriasDAO);
    private TarefasDAO tarefasDAO = new JDBCTarefasDAO(FabricaConexoes.getInstance());
    private RepositorioTarefas repositorioTarefas = new RepositorioTarefas(tarefasDAO, categoriasDAO);
    
    public static void main(String[] args) {
        launch();
    }

    @Override
    public String getHome() {
        // TODO Auto-generated method stub
        return "PRINCIPAL";
    }


    @Override
    public String getAppTitle() {
        // TODO Auto-generated method stub
        return "Coleção de Tarefas";
    }

    @Override
    public void registrarTelas() {
        registraTela("PRINCIPAL", new ScreenRegistryFXML(App.class, "principal.fxml", o -> new Principal()));
                registraTela("CADASTROCATEGORIAS",
                                new ScreenRegistryFXML(App.class,
                                                "cadastrar_categorias.fxml",
                                                o -> new CadastroCategorias(repositorioCategorias)));
    
                registraTela("LISTARCATEGORIAS",
                                new ScreenRegistryFXML(App.class,
                                                "listar_categorias.fxml",
                                                o -> new ListarCategorias(repositorioCategorias))); 
                                                
                registraTela("CADASTROTAREFAS",
                                new ScreenRegistryFXML(App.class,
                                                "cadastrar_tarefas.fxml",
                                                o -> new CadastroTarefas(repositorioTarefas, repositorioCategorias)));

                registraTela("LISTARTAREFAS",
                                new ScreenRegistryFXML(App.class,
                                                "listar_tarefas.fxml",
                                                o -> new ListarTarefas(repositorioTarefas, repositorioCategorias)));
    }

}