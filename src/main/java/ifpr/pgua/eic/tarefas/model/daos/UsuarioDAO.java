package ifpr.pgua.eic.tarefas.model.daos;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.tarefas.model.entities.Usuario;

public interface UsuarioDAO {
    Resultado cadastro(Usuario usuario);

    Resultado login(String email, String senha);
}