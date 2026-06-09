package com.template;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.ResultSet;



public class EletronicoDAO {

    private static final Logger logger = Logger.getLogger(EletronicoDAO.class.getName());

    public ArrayList<EletronicoDTO> listarEletronicos() {
        ArrayList<EletronicoDTO> lista = new ArrayList<>();
        String sql = "select * from eletronicos";

        try (Connection conexao = new Conexao().conectaBD();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            // Percorre as linhas retornadas pelo banco
            while(rs.next()) {
                EletronicoDTO obj = new EletronicoDTO();
                obj.setId(rs.getInt("id"));
                obj.setNomeEletronico(rs.getString("nome_eletronico"));
                obj.setModelo(rs.getString("modelo"));
                obj.setCor(rs.getString("cor"));
                lista.add(obj);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao listar os eletronicos");
        }

        return lista;
    }

    public void cadastrarEletronico(EletronicoDTO eletronico)
    {
        String sql = "insert into eletronicos (nome_eletronico, modelo, cor) values (?, ?, ?)";
        try (Connection conexao = new Conexao().conectaBD();
             PreparedStatement stmt = conexao.prepareStatement(sql))
        //Interrogação = ao número de colunas
        {
            stmt.setString(1, eletronico.getNomeEletronico());
            stmt.setString(2, eletronico.getModelo());
            stmt.setString(3, eletronico.getCor());
            stmt.executeUpdate();
        } catch (SQLException e)
        {
            logger.log(Level.SEVERE, "Erro ao cadastrar usuario");
        }
    }

    public void atualizarEletronico(EletronicoDTO eletronico)
    {
        String sql = "update eletronicos set nome_eletronico = ?, modelo = ?, cor = ? where id = ?";
        try (Connection conexao = new Conexao().conectaBD();
             PreparedStatement stmt = conexao.prepareStatement(sql))
        {
            stmt.setString(1, eletronico.getNomeEletronico());
            stmt.setString(2, eletronico.getModelo());
            stmt.setString(3, eletronico.getCor());
            stmt.setInt(4, eletronico.getId());
            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                logger.log(Level.SEVERE, "Eletronico atualizado com sucesso");
            } else {
                logger.log(Level.SEVERE, "Nenhum eletronico encontrado");
            }
        } catch (SQLException e)
        {
            logger.log(Level.SEVERE, "Erro ao atualizar eletronico");
        }
    }
    public void excluirEletronico(int id)
    {
        String sql = "delete from eletronicos where id = ?";
        try (Connection conexao = new Conexao().conectaBD();
             PreparedStatement stmt = conexao.prepareStatement(sql))
        {
            stmt.setInt(1, id);
            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                logger.log(Level.SEVERE, "Eletronico excluido com sucesso");
            } else {
                logger.log(Level.SEVERE, "Nenhum eletronico encontrado");
            }
        } catch (SQLException e)
        {
            logger.log(Level.SEVERE, "Erro ao excluir eletronico");
        }
    }
}
