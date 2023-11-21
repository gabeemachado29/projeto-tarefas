package ifpr.pgua.eic.tarefas.model.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.tarefas.model.entities.Categorias;
import ifpr.pgua.eic.tarefas.utils.DBUtils;

public class JDBCCategoriasDAO implements CategoriasDAO {
    
    private FabricaConexoes fabrica;

    public JDBCCategoriasDAO(FabricaConexoes fabrica){
        this.fabrica = fabrica;
    }

    @Override
    public Resultado cadastrar(Categorias categorias) {
       
        try (Connection con = fabrica.getConnection()) {
            
            PreparedStatement pstm = con.prepareStatement("INSERT INTO categoria(nome,descricao) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);

            pstm.setString(1, categorias.getNome());
            pstm.setString(2, categorias.getDescricao());

            int ret = pstm.executeUpdate();

            if (ret == 1) {

                // se conseguiu inserir, vamos pegar o id criado
                int id = DBUtils.getLastId(pstm);

                categorias.setId(id);

                return Resultado.sucesso("Categoria cadastrada!", categorias);
            }
            return Resultado.erro("Erro desconhecido!");
       } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
       }
    }

    @Override
    public Resultado listar() {
        try (Connection con = fabrica.getConnection()) {
            PreparedStatement pstm = con.prepareStatement("SELECT * FROM categoria");

            ResultSet rs = pstm.executeQuery();

            ArrayList<Categorias> lista = new ArrayList<>();

            while(rs.next()){
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String descricao = rs.getString("descricao");

                Categorias categorias = new Categorias(id,nome,descricao);
                lista.add(categorias);

            }
            
            return Resultado.sucesso("Lista de categorias", lista);
        } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
        }
    
    }

    @Override
    public Resultado atualizar(int id, Categorias novo) {
        try(Connection con=fabrica.getConnection()){

            PreparedStatement pstm = con.prepareStatement("UPDATE categoria SET nome=?, descricao=? WHERE id=?");
            pstm.setString(1, novo.getNome());
            pstm.setString(2, novo.getDescricao());
            pstm.setInt(3, id);

            int ret = pstm.executeUpdate();

            if(ret==1){
                novo.setId(id);
                return Resultado.sucesso("Categoria editada!", novo);
            }
            return Resultado.erro("Vixe...!");

        }catch(SQLException e){
            return Resultado.erro(e.getMessage());
        }
    }

    @Override
    public Resultado deletar(int id) {
        try (Connection con = fabrica.getConnection()) {

            PreparedStatement pstm = con.prepareStatement( "DELETE FROM categoria WHERE id=?");

            pstm.setInt(1, id);

            int ret = pstm.executeUpdate();

            if (ret==1) {
                return Resultado.sucesso("Categoria excluída com sucesso!", con);
            } 
            return Resultado.erro("Nenhuma tarefa encontrada com o ID fornecido.");
        } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
        }
    }

    @Override
    public Resultado getById(int id) {
        try (Connection con = fabrica.getConnection()) {

            PreparedStatement pstm = con.prepareStatement("SELECT * FROM categoria WHERE id=?");

            pstm.setInt(1, id);

            ResultSet rs = pstm.executeQuery();
            
            if(rs.next()){
                String nome = rs.getString("nome");
                String descricao = rs.getString("descricao");

                Categorias categorias = new Categorias(id,nome,descricao);

                return Resultado.sucesso("Categoria encontrado", categorias);
            }else{
                return Resultado.erro("Categoria não encontrado!");
            }


        } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
        }

    }

    @Override
    public Resultado buscarCategorias(int tarefaId) {
        try (Connection con = fabrica.getConnection()) {

            PreparedStatement pstm = con.prepareStatement("SELECT categoria FROM tarefa WHERE id=?");

            pstm.setInt(1, tarefaId);

            ResultSet rs = pstm.executeQuery();
            rs.next();

            int categoriaId = rs.getInt("categoria");

            //podemos criar um método no artistaDAO que retorna um artista baseado no id
            return getById(categoriaId);


        } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
        }
    }
}

    
