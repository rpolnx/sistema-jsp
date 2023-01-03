package com.org.sistema_jsp.backend.dao;

import com.org.sistema_jsp.backend.connection.SingleConnectionBanco;
import com.org.sistema_jsp.backend.model.ModelLogin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DAOLoginRepository {
	
	private Connection connection;
	
	public DAOLoginRepository() {
		connection = SingleConnectionBanco.getConnection();
	}
	
	public boolean validarAutenticacao(ModelLogin modelLogin) throws Exception {
		
		String sql = "select * from model_login where upper(login) = upper(?) and upper(senha) = upper(?) ";
		/*String sql = "select * from model_login where login = ? and senha = ? ";*/
		
		
		PreparedStatement statement = connection.prepareStatement(sql);
		
		statement.setString(1, modelLogin.getLogin());
		statement.setString(2, modelLogin.getSenha());
		
		ResultSet resultSet = statement.executeQuery();
		
		if (resultSet.next()) {
			return true;/*Autenticado*/
		}
		
		return false; /*Não autenticado*/
	}

}