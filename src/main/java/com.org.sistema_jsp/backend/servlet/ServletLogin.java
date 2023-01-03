package com.org.sistema_jsp.backend.servlet;

import java.io.IOException;

import com.org.sistema_jsp.backend.dao.DAOLoginRepository;
import com.org.sistema_jsp.backend.model.ModelLogin;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = { "/principal/ServletLogin", "/ServletLogin" })
public class ServletLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DAOLoginRepository daoRepository = new DAOLoginRepository();

	public ServletLogin() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String acao = request.getParameter("acao");
		
		if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("logout")) {
			request.getSession().invalidate(); /*invalide a sessao*/
			RequestDispatcher redirecionar = request.getRequestDispatcher("index.jsp");
			redirecionar.forward(request, response);
		}else {
			doPost(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String url = request.getParameter("url");
		try {
			if (request.getParameter("login") != null && !request.getParameter("login").isEmpty()
					&& request.getParameter("senha") != null && !request.getParameter("senha").isEmpty()) {
				ModelLogin login = new ModelLogin();
				login.setLogin(request.getParameter("login"));
				login.setSenha(request.getParameter("senha"));

				if (daoRepository.validarAutenticacao(login)) {
					request.getSession().setAttribute("usuario", login.getLogin());
					if (url == null || url.equals("null")) {

						url = "/principal/principal.jsp";
					}
					RequestDispatcher redirecionar = request.getRequestDispatcher(url);
					redirecionar.forward(request, response);
				} else {
					RequestDispatcher redirecionar = request.getRequestDispatcher("/index.jsp");
					request.setAttribute("msg", "Login ou senha inválido!");
					redirecionar.forward(request, response);
				}

			} else {
				RequestDispatcher redirecionar = request.getRequestDispatcher("/index.jsp");
				request.setAttribute("msg", "Login ou senha inválido!");
				redirecionar.forward(request, response);
			}

		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher redirecionar = request.getRequestDispatcher("/erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);			
		}
	}
}