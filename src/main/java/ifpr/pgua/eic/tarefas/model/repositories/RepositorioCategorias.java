package ifpr.pgua.eic.tarefas.model.repositories;

import java.util.ArrayList;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.tarefas.model.daos.CategoriasDAO;
import ifpr.pgua.eic.tarefas.model.entities.Categorias;

public class RepositorioCategorias {
    
    private ArrayList<Categorias> categorias;

    private CategoriasDAO dao;

    public RepositorioCategorias(CategoriasDAO dao) {
        categorias = new ArrayList<>();
        this.dao = dao;
    }

    public Resultado cadastrarCategorias(String nome, String descricao) {
        if (nome.isEmpty() || nome.isBlank()) {
            return Resultado.erro("Nome inválido!");
        }

        if (descricao.isBlank() || descricao.isEmpty()) {
            return Resultado.erro("Descrição inválido!");
        }

        Categorias categorias = new Categorias(nome, descricao);

        return dao.cadastrar(categorias);
    }

    public Resultado listarCategorias() {
        return dao.listar();
    }

    public Resultado atualizarCategoria(int id, String nome, String descricao) {
        if (nome.isEmpty() || nome.isBlank()) {
            return Resultado.erro("Nome inválido!");
        }

        if (descricao.isBlank() || descricao.isEmpty()) {
            return Resultado.erro("Descrição inválido!");
        }
        
        Categorias categorias = new Categorias(id, nome, descricao);
        return dao.atualizar(id, categorias);
    }

    public Resultado deletaCategoria(int id) {
        return dao.deletar(id);
    }
}
