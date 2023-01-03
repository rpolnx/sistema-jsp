package com.org.sistema_jsp.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.org.sistema_jsp.dao.DAOEquipamentoRepository;
import com.org.sistema_jsp.model.ModelEquipamento;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet("/ServletEquipamentoController")
public class ServletEquipamentoController<BigInteger> extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private DAOEquipamentoRepository daoEquipamentoRepository = new DAOEquipamentoRepository();

    public ServletEquipamentoController() {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {

            String acao = request.getParameter("acao");

            if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletar")) {
                String idUser = request.getParameter("id");
                daoEquipamentoRepository.deletarEquipamento(idUser);
                request.setAttribute("msg", "Excluido com sucesso!");
                request.getRequestDispatcher("principal/equipamento.jsp").forward(request, response);
            } else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletarajax")) {

                String idUser = request.getParameter("id");
                daoEquipamentoRepository.deletarEquipamento(idUser);
                response.getWriter().write("Excluido com sucesso!");
            } else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarEquipamentoAjax")) {
                String nomeBusca = request.getParameter("nomeBusca");
                List<ModelEquipamento> dadosJsonUser = daoEquipamentoRepository.consultaEquipamentoList(nomeBusca);
                ObjectMapper mapper = new ObjectMapper();
                String json = mapper.writeValueAsString(dadosJsonUser);
                response.getWriter().write(json);

            } else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarEditar")) {
                String id = request.getParameter("id");

                ModelEquipamento modelEquipamento = daoEquipamentoRepository.consultaEquipamentoID(id);

                request.setAttribute("msg", "Equipamento em edi�ao");
                request.setAttribute("modelEquipamento", modelEquipamento);
                request.getRequestDispatcher("principal/equipamento.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("principal/equipamento.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
            request.setAttribute("msg", e.getMessage());
            redirecionar.forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {

            String msg = "Opera��o realizada com sucesso!";

            String id = request.getParameter("id");
            String descricao = request.getParameter("descricao");
            System.out.println(request.getParameter("tipo_equipamento"));
            BigDecimal tipo_equipamento = new BigDecimal(request.getParameter("tipo_equipamento"));

            String data_nf_string = request.getParameter("data_nf");
            String numero_nf = request.getParameter("numero_nf");
            String cod_objetocusto = request.getParameter("cod_objetocusto");
            String solicitante_equipamento = request.getParameter("solicitante_equipamento");
            String usuario_cadastro = request.getParameter("usuario_cadastro");
            String data_cadastro_string = request.getParameter("data_cadastro");

            ModelEquipamento modelEquipamento = new ModelEquipamento();

            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            LocalDate date_nf = LocalDate.parse(data_nf_string, dateFormat);
            LocalDate data_cadastro = LocalDate.parse(data_cadastro_string, dateFormat);

            modelEquipamento.setId(id != null && !id.isEmpty() ? Long.parseLong(id) : null);
            modelEquipamento.setDescricao(descricao);
            modelEquipamento.setTipo_equipamento(tipo_equipamento);
            modelEquipamento.setData_nf(date_nf);


            modelEquipamento.setNumero_nf(numero_nf);
            modelEquipamento.setCod_objetocusto(cod_objetocusto);
            modelEquipamento.setSolicitante_equipamento(solicitante_equipamento);
            modelEquipamento.setUsuario_cadastro(usuario_cadastro);
            modelEquipamento.setData_cadastro(data_cadastro);

            if (daoEquipamentoRepository.validarEquipamento(modelEquipamento.getDescricao()) && modelEquipamento.getId() == null) {
                msg = "J� existe equipamento com o mesmo Descricao, informe outro ;";
            } else {
                if (modelEquipamento.isNovoEquipamento()) {
                    msg = "Equipamento Gravado com sucesso!";
                } else {
                    msg = "Atualizado com sucesso!";
                }
                modelEquipamento = daoEquipamentoRepository.gravarEquipamento(modelEquipamento);
            }

            request.setAttribute("msg", msg);
            request.setAttribute("modelEquipamento", modelEquipamento);
            request.getRequestDispatcher("principal/equipamento.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
            request.setAttribute("msg", e.getMessage());
            redirecionar.forward(request, response);
        }
    }
}
