package ifpr.pgua.eic.tarefas.model.repositories;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.tarefas.model.daos.CategoriasDAO;
import ifpr.pgua.eic.tarefas.model.daos.TarefasDAO;
import ifpr.pgua.eic.tarefas.model.entities.Categorias;
import ifpr.pgua.eic.tarefas.model.entities.Tarefas;

public class RepositorioTarefas {
    private ArrayList<Tarefas> tarefas;

    private TarefasDAO dao;
    private CategoriasDAO categoriasDAO;

    public RepositorioTarefas(TarefasDAO dao, CategoriasDAO categoriasDAO) {
        tarefas = new ArrayList<>();
        this.dao = dao;
        this.categoriasDAO = categoriasDAO;
    }

    public Resultado cadastrarTarefas(String titulo, String descricao, LocalDate dtprazo, Categorias categorias) {
        if (titulo.isEmpty() || titulo.isBlank()) {
            return Resultado.erro("Titulo inválido!");
        }

        if (descricao.isBlank() || descricao.isEmpty()) {
            return Resultado.erro("Descrição inválido!");
        }

        if (dtprazo.isBefore(LocalDate.now()) || dtprazo == null){
            return Resultado.erro("Data inválida!");
        }

        if (categorias == null){
            return Resultado.erro("Categoria inválida!");
        }

        Tarefas tarefas = new Tarefas(titulo, descricao, dtprazo, categorias);

        return dao.cadastrar(tarefas);
    }

    public Resultado listarTarefas() {
        
        Resultado resultado = dao.listar();

        if (resultado.foiSucesso()) {
            // iremos finalizar de montar os objetos
            List<Tarefas> lista = (List<Tarefas>) resultado.comoSucesso().getObj();

            for (Tarefas tarefas : lista) {
                
                Resultado r1 = montaTarefas(tarefas); 
                
                if(r1.foiErro()){
                    return r1;
                }
            }

        }
        return resultado;
    }

    private Resultado montaTarefas(Tarefas tarefas) {
        
        Resultado r1 = categoriasDAO.buscarCategorias(tarefas.getId());
        if (r1.foiErro()) {
            return r1;
        }
        Categorias categorias = (Categorias) r1.comoSucesso().getObj();
        tarefas.setCategoria(categorias);

        return Resultado.sucesso("Tarefa montada", tarefas);
    }

    public Resultado atualizarTarefa(int id, String titulo, String descricao, LocalDate dtprazo, Categorias categorias) {
        if (titulo.isEmpty() || titulo.isBlank()) {
            return Resultado.erro("Titulo inválido!");
        }

        if (descricao.isBlank() || descricao.isEmpty()) {
            return Resultado.erro("Descrição inválido!");
        }

        if (dtprazo.isBefore(LocalDate.now()) || dtprazo == null){
            return Resultado.erro("Data inválida!");
        }

        if (categorias == null){
            return Resultado.erro("Categoria inválida!");
        }
        
        Tarefas tarefas = new Tarefas(id, titulo, descricao, dtprazo, categorias);
        return dao.atualizar(id, tarefas);
    }

    public Resultado deletaTarefa(int id) {
        return dao.deletar(id);
    }
}
