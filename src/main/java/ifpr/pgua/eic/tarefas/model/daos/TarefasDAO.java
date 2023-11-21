package ifpr.pgua.eic.tarefas.model.daos;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.tarefas.model.entities.Tarefas;

public interface TarefasDAO {
    Resultado cadastrar(Tarefas tarefas);

    Resultado listar();

    Resultado getById(int id);

    Resultado atualizar(int id, Tarefas novo);

    Resultado deletar(int id);
}
