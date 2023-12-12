package ifpr.pgua.eic.tarefas.model.daos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import com.github.hugoperlin.results.Resultado;


import ifpr.pgua.eic.tarefas.model.entities.Tarefas;
import ifpr.pgua.eic.tarefas.utils.DBUtils;

public class JDBCTarefasDAO implements TarefasDAO {

    private FabricaConexoes fabrica;

    public JDBCTarefasDAO(FabricaConexoes fabrica){
        this.fabrica = fabrica;
    }

    @Override
    public Resultado cadastrar(Tarefas tarefas) {

        try (Connection con = fabrica.getConnection()) {
            
            PreparedStatement pstm = con.prepareStatement("INSERT INTO tarefa(titulo,descricao,dtprazo,categoria) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

            Date sqlDate = Date.valueOf(tarefas.getDtprazo());

            pstm.setString(1, tarefas.getTitulo());
            pstm.setString(2, tarefas.getDescricao());
            pstm.setDate(3, sqlDate);
            pstm.setInt(4, tarefas.getCategoria().getId());

            int ret = pstm.executeUpdate();

            if (ret == 1) {

                // se conseguiu inserir, vamos pegar o id criado
                int id = DBUtils.getLastId(pstm);

                tarefas.setId(id);

                return Resultado.sucesso("Tarefa cadastrada!", tarefas);
            }

            return Resultado.erro("Erro desconhecido!");

       } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
       }
    }

    @Override
    public Resultado listar() {
        
        try (Connection con = fabrica.getConnection()) {
            PreparedStatement pstm = con.prepareStatement("SELECT * FROM tarefa");

            ResultSet rs = pstm.executeQuery();

            ArrayList<Tarefas> lista = new ArrayList<>();
            while(rs.next()){
                int id = rs.getInt("id");
                String titulo = rs.getString("titulo");
                String descricao = rs.getString("descricao");
                Date dt = rs.getDate("dtprazo");
                LocalDate dtprazo = dt.toLocalDate();

                //iremos buscar artista e genero através do repositório
                Tarefas tarefas = new Tarefas(id,titulo, descricao, dtprazo, null);

                lista.add(tarefas);
            }

            return Resultado.sucesso("Ta listadas", lista);

        } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
        }
    }

    @Override
    public Resultado getById(int id){
        try (Connection con = fabrica.getConnection()) {
            PreparedStatement pstm = con.prepareStatement("SELECT * FROM tarefa WHERE id=?");

            pstm.setInt(1, id);

            ResultSet rs = pstm.executeQuery();


            if(rs.next()){
                String titulo = rs.getString("nome");
                String descricao = rs.getString("descricao");
                Date dt = rs.getDate("dtprazo");
                LocalDate dtprazo = dt.toLocalDate();

                Tarefas tarefas = new Tarefas(id,titulo, descricao, dtprazo, null);

                return Resultado.sucesso("Tarefas listadas", tarefas);
            }else{
                return Resultado.erro("Tarefa não encontrada!");
                
            }
        } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
        }
    
    }

    @Override
    public Resultado atualizar(int id, Tarefas novo) {
        try(Connection con=fabrica.getConnection()){

            Date sqlDate = Date.valueOf(novo.getDtprazo());

            PreparedStatement pstm = con.prepareStatement("UPDATE tarefa SET titulo=?, descricao=?, dtprazo=?, categoria=? WHERE id=?");
            pstm.setString(1, novo.getTitulo());
            pstm.setString(2, novo.getDescricao());
            pstm.setDate(3, sqlDate);
            pstm.setInt(4, novo.getCategoria().getId());
            pstm.setInt(5, id);

            int ret = pstm.executeUpdate();

            if(ret==1){
                novo.setId(id);
                return Resultado.sucesso("Tarefa editada!", novo);
            }
            return Resultado.erro("Vixe...!");

        }catch(SQLException e){
            return Resultado.erro(e.getMessage());
        }
    }

    @Override
    public Resultado deletar(int id) {
        try (Connection con = fabrica.getConnection()) {

            PreparedStatement pstm = con.prepareStatement( "DELETE FROM tarefa WHERE id=?");

            pstm.setInt(1, id);

            int ret = pstm.executeUpdate();

            if (ret==1) {
                return Resultado.sucesso("Tarefa excluída com sucesso!", id);
            } 
            return Resultado.erro("Nenhuma tarefa encontrada com o ID fornecido.");
        } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
        }
    }
    
}
