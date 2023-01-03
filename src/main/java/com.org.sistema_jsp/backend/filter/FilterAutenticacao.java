package com.org.sistema_jsp.backend.filter;

import com.org.sistema_jsp.backend.connection.SingleConnectionBanco;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;


@WebFilter(urlPatterns = {"/principal/*"})
public class FilterAutenticacao implements Filter {


    private static Connection connection;

    public FilterAutenticacao() {
    }

    /*Encerra os processo quando o servidor e parado*/
    /*Mataria os processo de conexao com banco. */
    public void destroy() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            HttpServletRequest req = (HttpServletRequest) request;
            HttpSession session = req.getSession();

            String usuarioLogado = (String) session.getAttribute("usuario");

            String urlParaAutenticar = req.getServletPath();

            if (usuarioLogado == null &&
                    !urlParaAutenticar.contains("/principal/ServletLogin")) {

                RequestDispatcher redireciona = request.getRequestDispatcher("/index.jsp?url=" + urlParaAutenticar);
                request.setAttribute("msg", "Realize o logon!");
                redireciona.forward(request, response);
                return; /* Para a execucao e redireciona para o login*/

            } else {
                chain.doFilter(request, response);
            }

            connection.commit(); /* dando tudo certo, grava as alteracoes no banco */

        } catch (Exception e) {
            e.printStackTrace();

            RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
            request.setAttribute("msg", e.getMessage());
            redirecionar.forward(request, response);

            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    /*Inicia os processo ou recursos quando o servidor sobre o projeto*/
    /*inicia a conexao com banco*/
    public void init(FilterConfig fConfig) throws ServletException {
        connection = SingleConnectionBanco.getConnection();
    }

}
