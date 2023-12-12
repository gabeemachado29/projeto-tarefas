package ifpr.pgua.eic.tarefas.model.repositories;

import java.time.LocalDate;
import java.util.ArrayList;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.tarefas.model.daos.UsuarioDAO;
import ifpr.pgua.eic.tarefas.model.entities.Categorias;
import ifpr.pgua.eic.tarefas.model.entities.Tarefas;
import ifpr.pgua.eic.tarefas.model.entities.Usuario;

public class repositorioUsuario {
    private ArrayList<Usuario> usuarios;

    private UsuarioDAO dao;

    public repositorioUsuario(UsuarioDAO dao) {
        usuarios = new ArrayList<>();
        this.dao = dao;
    }

    public Resultado cadastrarUsurio(String email, String senha) {
        if (email.isEmpty() || email.isBlank()) {
            return Resultado.erro("Email inválido!");
        }
        if (senha.isBlank() || senha.isEmpty()) {
            return Resultado.erro("Senha inválida!");
        }

        Usuario usuarios = new Usuario(email, senha);

        return dao.cadastro(usuarios);
    }

    public Resultado login(String email, String senha) {
        return dao.login(email, senha);
    }
}
