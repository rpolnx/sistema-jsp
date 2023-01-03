package com.org.sistema_jsp.dao;

import com.org.sistema_jsp.connection.SingleConnectionBanco;
import com.org.sistema_jsp.model.ModelEquipamento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class DAOEquipamentoRepository {
    private Connection connection;

    public DAOEquipamentoRepository() {
        connection = SingleConnectionBanco.getConnection();
    }

    public ModelEquipamento gravarEquipamento(ModelEquipamento objeto) throws Exception {
        if (objeto.isNovoEquipamento()) {/* Grava um novo equipamento */

            String sql = "INSERT INTO equipamento(descricao, tipo_equipamento, data_nf, numero_nf, cod_objetocusto, solicitante_equipamento, usuario_cadastro, data_cadastro)  VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement preparedSql = connection.prepareStatement(sql);
            preparedSql.setString(1, objeto.getDescricao());
            preparedSql.setBigDecimal(2, objeto.getTipo_equipamento());

            preparedSql.setDate(3, java.sql.Date.valueOf(objeto.getData_nf()));

            preparedSql.setBigDecimal(4, objeto.getNumero_nf());
            preparedSql.setBigDecimal(5, objeto.getCod_objetocusto());
            preparedSql.setString(6, objeto.getSolicitante_equipamento());
            preparedSql.setBigDecimal(7, objeto.getUsuario_cadastro());

            preparedSql.setDate(8, java.sql.Date.valueOf(objeto.getData_cadastro()));
            preparedSql.execute();
            connection.commit();

        } else {
            String sql = "UPDATE equipamento SET descricao=?, tipo_equipamento=?, data_nf=?, numero_nf=?, cod_objetocusto=?, solicitante_equipamento=?, usuario_cadastro=?, data_cadastro=? WHERE id =  " + objeto.getId() + ";";
            PreparedStatement preparedSql = connection.prepareStatement(sql);
            preparedSql.setString(1, objeto.getDescricao());
            preparedSql.setBigDecimal(2, objeto.getTipo_equipamento());
            preparedSql.setDate(3, java.sql.Date.valueOf(objeto.getData_nf()));


            preparedSql.setBigDecimal(4, objeto.getNumero_nf());
            preparedSql.setBigDecimal(5, objeto.getCod_objetocusto());
            preparedSql.setString(6, objeto.getSolicitante_equipamento());
            preparedSql.setBigDecimal(7, objeto.getUsuario_cadastro());
            preparedSql.setDate(8, java.sql.Date.valueOf(objeto.getData_cadastro()));
            preparedSql.executeUpdate();
            connection.commit();
        }
        return this.consultaEquipamento(objeto.getDescricao());
    }

    public List<ModelEquipamento> consultaEquipamentoList(String descricao) throws Exception {
        List<ModelEquipamento> retorno = new ArrayList<ModelEquipamento>();
        String sql = "select * from equipamento where upper(descricao) like upper(?) ";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, "%" + descricao + "%");

        ResultSet resultado = statement.executeQuery();

        while (resultado.next()) { /* Percorrer as linhas de resultado do sql de busca */

            ModelEquipamento modelEquipamento = new ModelEquipamento();
            modelEquipamento.setId(resultado.getLong("id"));
            modelEquipamento.setDescricao(resultado.getString("descricao"));
            modelEquipamento.setTipo_equipamento(resultado.getBigDecimal("tipo_equipamento"));

            LocalDate dateNfLocalDate = Instant.ofEpochMilli(resultado.getDate("data_nf").getTime())
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            modelEquipamento.setData_nf(dateNfLocalDate);
            modelEquipamento.setNumero_nf(resultado.getBigDecimal("numero_nf"));
            modelEquipamento.setCod_objetocusto(resultado.getBigDecimal("cod_objetocusto"));
            modelEquipamento.setSolicitante_equipamento(resultado.getString("solicitante_equipamento"));
            modelEquipamento.setUsuario_cadastro(resultado.getBigDecimal("usuario_cadastro"));

            LocalDate dataCadastroLocalDate = Instant.ofEpochMilli(resultado.getDate("data_cadastro").getTime())
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            modelEquipamento.setData_cadastro(dataCadastroLocalDate);
            retorno.add(modelEquipamento);
        }
        return retorno;
    }

    public ModelEquipamento consultaEquipamento(String descricao) throws Exception {
        ModelEquipamento modelEquipamento = new ModelEquipamento();
        String sql = "select * from equipamento where upper(descricao) = upper('" + descricao + "')";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultado = statement.executeQuery();
        while (resultado.next()) /* Se tem resultado */ {
            modelEquipamento.setId(resultado.getLong("id"));
            modelEquipamento.setDescricao(resultado.getString("descricao"));
            modelEquipamento.setTipo_equipamento(resultado.getBigDecimal("tipo_equipamento"));
            //		modelEquipamento.setData_nf(resultado.getString("data_nf"));
            modelEquipamento.setNumero_nf(resultado.getBigDecimal("numero_nf"));
            modelEquipamento.setCod_objetocusto(resultado.getBigDecimal("cod_objetocusto"));
            modelEquipamento.setSolicitante_equipamento(resultado.getString("solicitante_equipamento"));
            modelEquipamento.setUsuario_cadastro(resultado.getBigDecimal("usuario_cadastro"));
            //		modelEquipamento.setData_cadastro(resultado.getString("data_cadastro"));
        }
        return modelEquipamento;
    }

    public ModelEquipamento consultaEquipamentoID(String id) throws Exception {
        ModelEquipamento modelEquipamento = new ModelEquipamento();
        String sql = "select * from equipamento where id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setLong(1, Long.parseLong(id));
        ResultSet resultado = statement.executeQuery();
        while (resultado.next()) /* Se tem resultado */ {
            modelEquipamento.setId(resultado.getLong("id"));
            modelEquipamento.setDescricao(resultado.getString("descricao"));
            modelEquipamento.setTipo_equipamento(resultado.getBigDecimal("tipo_equipamento"));
            //		modelEquipamento.setData_nf(resultado.getString("data_nf"));
            modelEquipamento.setNumero_nf(resultado.getBigDecimal("numero_nf"));
            modelEquipamento.setCod_objetocusto(resultado.getBigDecimal("cod_objetocusto"));
            modelEquipamento.setSolicitante_equipamento(resultado.getString("solicitante_equipamento"));
            modelEquipamento.setUsuario_cadastro(resultado.getBigDecimal("usuario_cadastro"));
        }
        return modelEquipamento;
    }

    public boolean validarEquipamento(String descricao) throws Exception {
        String sql = "select count(1) > 0 as existe from equipamento where upper(descricao) = upper('" + descricao + "');";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultado = statement.executeQuery();
        resultado.next();/* Pra ele entrar nos resultados do sql */
        return resultado.getBoolean("existe");
    }

    public void deletarEquipamento(String idUser) throws Exception {
        String sql = "DELETE FROM equipamento WHERE id = ?;";
        PreparedStatement prepareSql = connection.prepareStatement(sql);
        prepareSql.setLong(1, Long.parseLong(idUser));
        prepareSql.executeUpdate();
        connection.commit();
    }
}
