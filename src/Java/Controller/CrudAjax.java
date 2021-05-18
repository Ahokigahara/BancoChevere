package Controller;

import ControllerDAO.ProductoJDBC;
import ControllerDAO.ReferenciaPagoJDBC;
import ControllerDAO.TerceroJDBC;
import ControllerDAO.UsuarioJDBC;
import Model.Producto;
import Model.ReferenciaPago;
import Model.Tercero;
import Model.Usuario;
import static com.sun.corba.se.spi.presentation.rmi.StubAdapter.request;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
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
            Usuario usuario = getSessionUsuario(request.getSession());
            String mensaje = "";

            switch (accion) {
                case "insercion":
                    String origen = "";
                    String valor = "";
                    String concepto = "";

                    switch (tabla) {
                        case "transferencias":
                            origen = getParameterString(request, "productoOrigenTransferencia");
                            String destino = getParameterString(request, "productoDestinoTransferencia");
                            valor = getParameterString(request, "valorTransferencia");
                            concepto = getParameterString(request, "conceptoTransferencia");

                            registros = "{\"RESULTADO\":true,\"MENSAJE\":\"Transferecnia de " + origen + " hasta " + destino + " realizada por valor " + valor + ", concepto " + concepto + " \"}";
                            break;

                        case "pagos":
                            origen = getParameterString(request, "productoOrigenPago");
                            String entidad = getParameterString(request, "entidadPago");
                            String referencia = getParameterString(request, "referenciaPago");
                            valor = "0";
                            concepto = "-";

                            registros = "{\"RESULTADO\":true,\"MENSAJE\":\"Pago de " + origen + " a " + entidad + " referencia " + referencia + ", valor " + valor + ", concepto " + concepto + " \"}";
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
                            Integer entidad = getParameterInteger(request, "entidad");
                            String referencia = getParameterString(request, "referencia");
                            Double valorReferencia = 0.0;
                            String conceptoReferencia = "";
                            mensaje = "";
                            
                            ReferenciaPagoJDBC referenciaPagoJDBC = new ReferenciaPagoJDBC();
                            ReferenciaPago referenciaPago = referenciaPagoJDBC.consultarReferenciaPago(entidad, referencia, mensaje);
                            
                            if(referenciaPago!=null){
                                valorReferencia = referenciaPago.getMonto();
                                conceptoReferencia = "Referencia "+referenciaPago.getReferencia()+", "+referenciaPago.getTercero().getDocumento()+" - "+referenciaPago.getTercero().getNombres()+" "+referenciaPago.getTercero().getApellidos()+", "+String.valueOf(referenciaPago.getMonto());
                            }
                            
                            registros = "{\"VALOR\":"+Double.toString(valorReferencia)+",\"CONCEPTO\":\""+conceptoReferencia+"\",\"MENSAJE\":\""+mensaje+"\"}";
                            break;

                        case "entidades":
                            registros = "";
                            mensaje = "";
                            if (usuario != null) {
                                TerceroJDBC terceroJDBC = new TerceroJDBC();
                                List<Tercero> terceros = terceroJDBC.consultarEntidades(mensaje);
                                for (int row = 0; row < terceros.size(); row++) {
                                    Tercero tercero = terceros.get(row);
                                    registros += (registros.length() > 0 ? "," : "") + "{\"ID\":" + Integer.toString(tercero.getId()) + ",\"NOMBRE\":\""+tercero.getNombres()+" "+tercero.getApellidos()+ "\"}";
                                }
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
                            Producto producto = new Producto();

                            switch (alcance) {

                                case "origen":
                                    registros = "";
                                    mensaje = "";
                                    if (usuario != null) {
                                        ProductoJDBC productoJDBC = new ProductoJDBC();
                                        List<Producto> productos = productoJDBC.consultarProductos(usuario.getId(), mensaje);
                                        for (int row = 0; row < productos.size(); row++) {
                                            producto = productos.get(row);
                                            registros += (registros.length() > 0 ? "," : "") + "{\"VALOR\":" + Integer.toString(producto.getId()) + ",\"TEXTO\":\"" + Integer.toString(producto.getId()) + " - " + producto.getProductoTipo().getNombre() + " - " + producto.getTercero().getNombres() + " " + producto.getTercero().getApellidos() + "\"}";
                                        }
                                    }
                                    registros = "[" + registros + "]";
                                    break;

                                case "origen-tabla":
                                    registros = "";
                                    mensaje = "";
                                    if (usuario != null) {
                                        ProductoJDBC productoJDBC = new ProductoJDBC();
                                        List<Producto> productos = productoJDBC.consultarProductos(usuario.getId(), mensaje);
                                        for (int row = 0; row < productos.size(); row++) {
                                            producto = productos.get(row);
                                            registros += (registros.length() > 0 ? "," : "") + "{\"NUMERO\":" + Integer.toString(producto.getId()) + ",\"TIPO\":\"" + producto.getProductoTipo().getNombre() + "\",\"SALDO\":\"" + Double.toString(producto.getSaldo()) + "\",\"TITULAR\":\"" + producto.getTercero().getDocumentoTipo().getId() + producto.getTercero().getDocumento() + "\",\"MENSAJE\":\"" + mensaje + "\"}";
                                        }
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
                                    String user = getParameterString(request, "usuario");
                                    String clave = getParameterString(request, "clave");
                                    String acceso = "false";
                                    mensaje = "Usuario no existe";

                                    HttpSession session = request.getSession();
                                    session.setAttribute("USUARIO", null);

                                    UsuarioJDBC usuarioJDBC = new UsuarioJDBC();
                                    usuario = usuarioJDBC.consultarUsuario(user, mensaje);

                                    if (usuario != null) {
                                        if (usuario.getClave().equals(clave)) {
                                            acceso = "true";
                                            mensaje = "Acceso correcto";
                                            session.setAttribute("USUARIO", usuario);
                                        } else {
                                            acceso = "false";
                                            mensaje = "Clave incorrecta (" + usuario.getClave() + "<>" + clave + ")";
                                        }
                                    } else {
                                        mensaje += ", No se puede validar el usuario";
                                    }

                                    registros = "{\"ACCESO\":" + acceso + ",\"MENSAJE\":\"" + mensaje + "\"}";
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

    public Integer getParameterInteger(HttpServletRequest request, String parametro) {
        Integer variable = 0;
        try {
            variable = Integer.parseInt((String) request.getParameter(parametro));
        } catch (Exception excepcion) {
            variable = 0;
        }
        return variable;
    }
    
    public Usuario getSessionUsuario(HttpSession session) {
        Usuario usuario = null;
        try {
            usuario = (Usuario) session.getAttribute("USUARIO");
        } catch (Exception excepcion) {
            usuario = null;
        }
        return usuario;
    }

}
