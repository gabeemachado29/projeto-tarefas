package ifpr.pgua.eic.tarefas.model.daos;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.tarefas.model.entities.Categorias;

public interface CategoriasDAO {
    Resultado cadastrar(Categorias categorias);

    Resultado listar();

    Resultado getById(int id);

    Resultado atualizar(int id, Categorias novo);

    Resultado deletar(int id);

    Resultado buscarCategorias(int id);
}
