package ifpr.pgua.eic.tarefas.model.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.tarefas.model.entities.Usuario;
import ifpr.pgua.eic.tarefas.utils.DBUtils;

public class JDBCUsuarioDAO implements UsuarioDAO {

    private FabricaConexoes fabrica;

    public JDBCUsuarioDAO(FabricaConexoes fabrica){
        this.fabrica = fabrica;
    }

    @Override
    public Resultado cadastro(Usuario usuario) {
        try (Connection con = fabrica.getConnection()) {
            
            PreparedStatement pstm = con.prepareStatement("INSERT INTO usuario(email,senha) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);

            pstm.setString(1, usuario.getEmail());
            pstm.setString(2, usuario.getSenha());

            int ret = pstm.executeUpdate();

            if (ret == 1) {

                // se conseguiu inserir, vamos pegar o id criado
                int id = DBUtils.getLastId(pstm);

                usuario.setId(id);

                return Resultado.sucesso("Usuario cadastrado!", usuario);
            }
            return Resultado.erro("Erro desconhecido!");
       } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
       }
    }

    @Override
    public Resultado login(String email, String senha) {
        try (Connection con = fabrica.getConnection()) {
            PreparedStatement pstm = con.prepareStatement("SELECT * FROM usuario WHERE email=? AND senha=?");

            pstm.setString(1, email);
            pstm.setString(2, senha);

            ResultSet rs = pstm.executeQuery();

            if(rs.next()){
                Usuario usuario = new Usuario(email, senha);

                return Resultado.sucesso("Usuario encontrado", usuario);
            }else{
                return Resultado.erro("Usuario não encontrado!");
            }
        }catch (SQLException e) {
            return Resultado.erro(e.getMessage());
        }
    }
    
}
