package com.template;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.ResultSet;


public class EletronicoDAO {
    public ArrayList<EletronicoDTO> listarEletronicos()
    {
        ArrayList<EletronicoDTO> lista = new ArrayList<>();
        String sql = "select * from eletronicos";
        try (Connection conexao = new Conexao().conectaBD();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery())//guarda o resultado da query
        // try-with-resources garante o fechamento automático da conexão e recursos
        {
            //Percorre e armazena todas as linhas retornadas pela consulta SQL
            while(rs.next())
            {
                System.out.println(
                        "#" + rs.getInt("id") +
                                " | Nome: " + rs.getString("nome_eletronico") +
                                " | Modelo: " + rs.getString("modelo") +
                                " | Cor: " + rs.getString("cor")
                );
            }
        } catch (SQLException e)
        {
            System.out.println("Erro ao listar eletronico" + e.getMessage());
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
            System.out.println("Eletronico cadastrado com sucesso");
        } catch (SQLException e)
        {
            System.out.println("Erro ao cadastrar eletronico" + e.getMessage());
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
                System.out.println("\n Eletrônico atualizado com sucesso!");
            } else {
                System.out.println("\n Nenhum registro encontrado.");
            }
        } catch (SQLException e)
        {
            System.out.println("Erro ao atualizar o eletronico" + e.getMessage());
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
                System.out.println("\n Eletronico excluido com sucesso!");
            } else {
                System.out.println("\n Nenhum registro encontrado para exclusao.");
            }
        } catch (SQLException e)
        {
            System.out.println("Erro ao excluir eletronico" + e.getMessage());
        }
    }
}
