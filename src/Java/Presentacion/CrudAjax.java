package Presentacion;

import static com.sun.corba.se.spi.presentation.rmi.StubAdapter.request;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Guillermo
 */
@WebServlet(name = "CrudAjax", urlPatterns = {"/crudAjax.jsp"})
public class CrudAjax extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json; charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            String accion = getParameterString(request, "accion");
            String tabla = getParameterString(request, "tabla");
            String registros = "";
            Integer total = 0;

            switch (accion) {
                case "insercion":
                    String origen ="";
                    String valor = "";
                    String concepto = "";
                    
                    switch (tabla) {
                        case "transferencias":
                            origen = getParameterString(request, "productoOrigenTransferencia");
                            String destino = getParameterString(request, "productoDestinoTransferencia");
                            valor = getParameterString(request, "valorTransferencia");
                            concepto= getParameterString(request, "conceptoTransferencia");

                            registros = "{\"RESULTADO\":true,\"MENSAJE\":\"Transferecnia de "+origen+" hasta "+destino+" realizada por valor "+valor+", concepto "+concepto+" \"}";
                            break;

                        case "pagos":
                            origen = getParameterString(request, "productoOrigenPago");
                            String entidad = getParameterString(request, "entidadPago");
                            String referencia = getParameterString(request, "referenciaPago");
                            valor = "0";
                            concepto = "-";

                            registros = "{\"RESULTADO\":true,\"MENSAJE\":\"Pago de "+origen+" a "+entidad+" referencia "+referencia+", valor "+valor+", concepto "+concepto+" \"}";
                            break;

                    }
                    out.println(registros);
                    break;

                case "consulta":
                    switch (tabla) {
                        case "transferencias":
                            registros = "";
                            for (int row = 0; row < 15; row++) {
                                registros += (registros.length() > 0 ? "," : "") + "{\"CONSECUTIVO\":" + Integer.toString(row) + ",\"FECHA\":\"x\",\"ORIGEN\":\"o\",\"CANAL\":\"l\",\"VALOR\":\"1\"}";
                            }
                            registros = "[" + registros + "]";
                            break;

                        case "referencias":
                            String entidad = getParameterString(request, "entidad");
                            String referencia = getParameterString(request, "referencia");
                            registros = "{\"VALOR\":1200,\"CONCEPTO\":\"Prueba\"}";
                            break;

                        case "entidades":
                            registros = "";
                            for (int row = 0; row < 15; row++) {
                                registros += (registros.length() > 0 ? "," : "") + "{\"ID\":" + Integer.toString(row) + ",\"NOMBRE\":\"ENTIDAD " + Integer.toString(row) + "\"}";
                            }
                            registros = "[" + registros + "]";
                            break;

                        case "pagos":
                            registros = "";
                            for (int row = 0; row < 15; row++) {
                                registros += (registros.length() > 0 ? "," : "") + "{\"CONSECUTIVO\":" + Integer.toString(row) + ",\"FECHA\":\"x\",\"ORIGEN\":\"o\",\"CANAL\":\"l\",\"VALOR\":\"1\"}";
                            }
                            registros = "[" + registros + "]";
                            break;

                        case "productos":
                            String alcance = getParameterString(request, "alcance");
                            switch (alcance) {

                                case "origen":
                                    registros = "";
                                    for (int row = 0; row < 15; row++) {
                                        registros += (registros.length() > 0 ? "," : "") + "{\"VALOR\":" + Integer.toString(row) + ", \"TEXTO\":\"" + alcance + " - " + Integer.toString(row) + "\"}";
                                    }
                                    registros = "[" + registros + "]";
                                    break;

                                case "origen-tabla":
                                    registros = "";
                                    for (int row = 0; row < 15; row++) {
                                        registros += (registros.length() > 0 ? "," : "") + "{\"CONSECUTIVO\":" + Integer.toString(row) + ",\"FECHA\":\"x\",\"ORIGEN\":\"o\",\"CANAL\":\"l\",\"VALOR\":\"1\"}";
                                    }
                                    registros = "[" + registros + "]";
                                    break;

                                case "destino":
                                    String productoDestino = getParameterString(request, "productoDestino");
                                    registros = "{\"PRODUCTO\":\"" + productoDestino + "\",\"TITULAR\":\"Titular Producto " + productoDestino + "\"}";
                                    break;
                            }

                            break;

                        case "usuarios":
                            String tipo = getParameterString(request, "tipo");

                            switch (tipo) {
                                case "validar":
                                    String usuario = getParameterString(request, "usuario");
                                    String clave = getParameterString(request, "clave");

                                    registros = "{\"ACCESO\":true,\"MENSAJE\":\"Acceso correcto\"}";
                                    break;
                            }
                            break;
                    }
                    out.println(registros);
                    break;

                default:
                    out.println("[]");
                    break;
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    public String getParameterString(HttpServletRequest request, String parametro) {
        String variable = "";
        try {
            variable = (String) request.getParameter(parametro);
        } catch (Exception excepcion) {
            variable = "";
        }
        return variable;
    }
}
