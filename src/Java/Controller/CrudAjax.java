package Controller;

import ControllerDAO.MovimientoJDBC;
import ControllerDAO.ProductoJDBC;
import ControllerDAO.ReferenciaPagoJDBC;
import ControllerDAO.TerceroJDBC;
import ControllerDAO.UsuarioJDBC;
import Model.Movimiento;
import Model.Producto;
import Model.ReferenciaPago;
import Model.Tercero;
import Model.Usuario;
import static com.sun.corba.se.spi.presentation.rmi.StubAdapter.request;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
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
            HttpSession httpSession = request.getSession();
            String consultaTipo = "";

            switch (accion) {
                case "insercion":
                    String origen = "";
                    String valor = "";
                    String concepto = "";
                    String codigo = "";
                    String codigoConfirmacion = "";

                    switch (tabla) {
                        case "transferencias":
                            registros = "";
                            origen = getParameterString(request, "productoOrigenTransferencia");
                            String destino = getParameterString(request, "productoDestinoTransferencia");
                            valor = getParameterString(request, "valorTransferencia");
                            concepto = getParameterString(request, "conceptoTransferencia");
                            codigo = getSessionCodigoConfirmacion(httpSession);
                            codigoConfirmacion = getParameterString(request, "codigo");

                            if (codigoConfirmacion.equals(codigo)) {
                                request.getSession().removeAttribute("cod");
                                MovimientoJDBC movimientoPagoJDBC = new MovimientoJDBC();
                                registros = movimientoPagoJDBC.insertarMovimientoTransferencia("TRANSFERENCIA", Integer.parseInt(origen), Integer.parseInt(destino), Double.parseDouble(valor), concepto, mensaje);
                            } else {
                                registros = "{\"RESULTADO\":false,\"MENSAJE\":\"Codigo incorrecto. " + codigo + "<>" + codigoConfirmacion + ", por favor intente nuevamente\"}";
                            }                            
                            
                            break;

                        case "pagos":
                            registros = "";
                            origen = getParameterString(request, "productoOrigenPago");
                            String entidad = getParameterString(request, "entidadPago");
                            String referencia = getParameterString(request, "referenciaPago");
                            codigo = getSessionCodigoConfirmacion(httpSession);
                            codigoConfirmacion = getParameterString(request, "codigo");
                            valor = "0";
                            concepto = getParameterString(request, "conceptoPago");

                            if (codigoConfirmacion.equals(codigo)) {
                                request.getSession().removeAttribute("cod");
                                MovimientoJDBC movimientoPagoJDBC = new MovimientoJDBC();
                                registros = movimientoPagoJDBC.insertarMovimientoPago("PAGO", Integer.parseInt(origen), Integer.parseInt(entidad), referencia, concepto, mensaje);
                            } else {
                                registros = "{\"RESULTADO\":false,\"MENSAJE\":\"Codigo incorrecto. " + codigo + "<>" + codigoConfirmacion + ", por favor intente nuevamente\"}";
                            }

                            break;

                    }
                    out.println(registros);
                    break;

                case "consulta":
                    switch (tabla) {
                        case "referencias":
                            Integer entidad = getParameterInteger(request, "entidad");
                            String referencia = getParameterString(request, "referencia");
                            Double valorReferencia = 0.0;
                            String conceptoReferencia = "";
                            mensaje = "";

                            ReferenciaPagoJDBC referenciaPagoJDBC = new ReferenciaPagoJDBC();
                            ReferenciaPago referenciaPago = referenciaPagoJDBC.consultarReferenciaPago(entidad, referencia, mensaje);

                            if (referenciaPago != null) {
                                valorReferencia = referenciaPago.getMonto();
                                conceptoReferencia = "Referencia " + referenciaPago.getReferencia() + ", " + referenciaPago.getTercero().getDocumento() + " - " + referenciaPago.getTercero().getNombres() + " " + referenciaPago.getTercero().getApellidos() + ", " + String.valueOf(referenciaPago.getMonto());
                            }

                            registros = "{\"VALOR\":" + Double.toString(valorReferencia) + ",\"CONCEPTO\":\"" + conceptoReferencia + "\",\"MENSAJE\":\"" + mensaje + "\"}";
                            break;

                        case "entidades":
                            registros = "";
                            mensaje = "";
                            if (usuario != null) {
                                TerceroJDBC terceroJDBC = new TerceroJDBC();
                                List<Tercero> terceros = terceroJDBC.consultarEntidades(mensaje);
                                for (int row = 0; row < terceros.size(); row++) {
                                    Tercero tercero = terceros.get(row);
                                    registros += (registros.length() > 0 ? "," : "") + "{\"ID\":" + (tercero.getId()) + ",\"NOMBRE\":\"" + tercero.getNombres() + " " + tercero.getApellidos() + "\"}";
                                }
                            }
                            registros = "[" + registros + "]";
                            break;

                        case "pagos":
                            registros = "";
                            List<Movimiento> movimientosPagos = MovimientoJDBC.instancia().consultarMovimientosPorTipo(usuario.getId(), 1, mensaje);
                            consultaTipo = "Movimientos tipo: " + Integer.toString(1);

                            for (int row = 0; row < movimientosPagos.size(); row++) {
                                Movimiento mov = movimientosPagos.get(row);
                                registros += (registros.length() > 0 ? "," : "") + "{\"ID\":" + Integer.toString(mov.getId()) + ", \"CONSECUTIVO\":" + Integer.toString(row) + ",\"FECHA\":\"" + mov.getFecha() + "\",\"ORIGEN\":\"" + mov.getProductoOrigen().getIdentificardorProducto() + "\",\"DESTINO\":\"" + mov.getProductoDestino().getIdentificardorProducto() + "\",\"ENTIDAD\":\"" + mov.getProductoDestino().getTercero().getNombreCompleto() + "\"" + ",\"REFERENCIA\":\"" + Integer.toString(mov.getReferenciaPago().getId()) + "\""
                                        + ",\"TITULARDESTINO\":\"" + mov.getProductoDestino().getTercero().getNombreCompleto() + "\",\"VALOR\":" + mov.getMonto() + ",\"CONCEPTO\":\"" + mov.getConcepto() + "\",\"TIPO\":\"" + mov.getMovimientoTipo().getNombre() + "\",\"CONSULTATIPO\":\"" + consultaTipo + "\"}";
                            }
                            registros = "[" + registros + "]";
                            break;
                            
                        case "transferencias":
                            registros = "";
                            List<Movimiento> movimientosTransferencias = MovimientoJDBC.instancia().consultarMovimientosPorTipo(usuario.getId(), 2, mensaje);
                            consultaTipo = "Movimientos tipo: " + Integer.toString(2);

                            for (int row = 0; row < movimientosTransferencias.size(); row++) {
                                Movimiento mov = movimientosTransferencias.get(row);
                                registros += (registros.length() > 0 ? "," : "") + "{\"ID\":" + Integer.toString(mov.getId()) + ", \"CONSECUTIVO\":" + Integer.toString(row) + ",\"FECHA\":\"" + mov.getFecha() + "\",\"ORIGEN\":\"" + mov.getProductoOrigen().getIdentificardorProducto() + "\",\"DESTINO\":\"" + mov.getProductoDestino().getIdentificardorProducto() + "\",\"ENTIDAD\":\"" + mov.getProductoDestino().getTercero().getNombreCompleto() + "\"" + ",\"REFERENCIA\":\"" + Integer.toString(mov.getReferenciaPago().getId()) + "\""
                                        + ",\"TITULARDESTINO\":\"" + mov.getProductoDestino().getTercero().getNombreCompleto() + "\",\"VALOR\":" + mov.getMonto() + ",\"CONCEPTO\":\"" + mov.getConcepto() + "\",\"TIPO\":\"" + mov.getMovimientoTipo().getNombre() + "\",\"CONSULTATIPO\":\"" + consultaTipo + "\"}";
                            }
                            registros = "[" + registros + "]";
                            break;

                        case "movimientos":
                            registros = "";
                            Integer productoId = getParameterInteger(request, "id");
                            List<Movimiento> movimientos = MovimientoJDBC.instancia().consultarMovimientosPorProducto(productoId, mensaje);
                            consultaTipo = "Movimientos producto: " + Integer.toString(productoId);

                            for (int row = 0; row < movimientos.size(); row++) {
                                Movimiento mov = movimientos.get(row);
                                registros += (registros.length() > 0 ? "," : "") + "{\"ID\":" + Integer.toString(mov.getId()) + ", \"CONSECUTIVO\":" + Integer.toString(row) + ",\"FECHA\":\"" + mov.getFecha() + "\",\"ORIGEN\":\"" + mov.getProductoOrigen().getIdentificardorProducto() + "\",\"DESTINO\":\"" + mov.getProductoDestino().getIdentificardorProducto() + "\",\"ENTIDAD\":\"" + mov.getProductoDestino().getTercero().getNombreCompleto() + "\"" + ",\"REFERENCIA\":\"" + Integer.toString(mov.getReferenciaPago().getId()) + "\""
                                        + ",\"TITULARDESTINO\":\"" + mov.getProductoDestino().getTercero().getNombreCompleto() + "\",\"VALOR\":" + mov.getMonto() + ",\"CONCEPTO\":\"" + mov.getConcepto() + "\",\"TIPO\":\"" + mov.getMovimientoTipo().getNombre() + "\",\"CONSULTATIPO\":\"" + consultaTipo + "\"}";
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
                                            registros += (registros.length() > 0 ? "," : "") + "{\"VALOR\":" + Integer.toString(producto.getNumero()) + ",\"TEXTO\":\"" + Integer.toString(producto.getNumero()) + " - " + producto.getProductoTipo().getNombre() + "\"}";
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
                                            registros += (registros.length() > 0 ? "," : "") + "{\"ID\":" + Integer.toString(producto.getId()) + ",\"NUMERO\":" + Integer.toString(producto.getNumero()) + ",\"TIPO\":\"" + producto.getProductoTipo().getNombre() + "\",\"SALDO\":\"" + Double.toString(producto.getSaldo()) + "\",\"TITULAR\":\"" + producto.getTercero().getDocumentoTipo().getId() + producto.getTercero().getDocumento() + "\",\"MENSAJE\":\"" + mensaje + "\"}";
                                        }
                                    }
                                    registros = "[" + registros + "]";
                                    break;

                                case "destino":
                                    String productoDestino = getParameterString(request, "productoDestino");
                                    if (productoDestino != null || !productoDestino.isEmpty()) {
                                        Producto productoDest = ProductoJDBC.instancia().consultarProducto(Integer.parseInt(productoDestino), "");
                                        // origen = getParameterString(request, "productoOrigenTransferencia");

                                        if (productoDest != null) {
                                            //if(productoDest.getNumero()==Integer.parseInt(origen)){
                                            //  registros = "{\"PRODUCTO\":\"" + productoDestino + "\",\"RESULTADO\":\"dup\",\"TITULAR\":\"" + productoDest.getTercero().getNombres()+" "+productoDest.getTercero().getApellidos() + "\"}";
                                            //}else{
                                            registros = "{\"PRODUCTO\":\"" + productoDestino + "\",\"RESULTADO\":true,\"TITULAR\":\"" + productoDest.getTercero().getNombres() + " " + productoDest.getTercero().getApellidos() + "\"}";
                                            //}
                                        } else {
                                            registros = "{\"PRODUCTO\":\"" + productoDestino + "\",\"RESULTADO\":false,\"TITULAR\":\"\"}";
                                        }
                                    } else {
                                        registros = "{\"PRODUCTO\":\"" + productoDestino + "\",\"RESULTADO\":null,\"TITULAR\":\"\"}";
                                    }

                                    break;
                            }

                            break;

                        case "usuarios":
                            String tipo = getParameterString(request, "tipo");

                            switch (tipo) {
                                case "validar":
                                    String user = getParameterString(request, "usuario");
                                    String clave = getParameterString(request, "clave");
                                    codigo = getSessionCodigoConfirmacion(httpSession);
                                    codigoConfirmacion = getParameterString(request, "codigo");
                                    String acceso = "false";
                                    mensaje = "Usuario <b>" + user + "</b> no existe, clave ncorrecta o código de confrimacion.";

                                    httpSession.setAttribute("USUARIO", null);

                                    UsuarioJDBC usuarioJDBC = new UsuarioJDBC();
                                    usuario = usuarioJDBC.consultarUsuario(user, mensaje);

                                    if (usuario != null) {
                                        if (codigoConfirmacion.equals(codigo)) {
                                            if (usuario.getClave().equals(clave)) {
                                                acceso = "true";
                                                mensaje = "Acceso correcto";
                                                httpSession.setAttribute("USUARIO", usuario);
                                            } else {
                                                acceso = "false";
                                                mensaje += "Clave incorrecta (" + usuario.getClave() + "<>" + clave + ")";
                                            }
                                        } else {
                                            mensaje += "Codigo incorrecto. " + codigo + "<>" + codigoConfirmacion + ", por favor intente nuevamente";
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

                case "confirmacion":
                    accionCodigoConfirmacion("codigo", usuario, request, httpSession);
                    registros = "{\"CODIGO\": \"" + getSessionCodigoConfirmacion(httpSession) + "\"}";
                    out.println(registros);
                    break;

                default:
                    out.println("[\"ERROR\":\"SIN TIPO\"]");
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

    public String getSessionCodigoConfirmacion(HttpSession httpSession) {
        String codigo = "!";
        try {
            codigo = (String) httpSession.getAttribute("CODIGO-CONFIRMACION");
        } catch (Exception excepcion) {
            codigo = "!";
        }
        return codigo;
    }

    public Usuario getSessionUsuario(HttpSession httpSession) {
        Usuario usuario = null;
        try {
            usuario = (Usuario) httpSession.getAttribute("USUARIO");
        } catch (Exception excepcion) {
            usuario = null;
        }
        return usuario;
    }

    public void accionCodigoConfirmacion(String asunto, Usuario usuario, HttpServletRequest request, HttpSession httpSession) {
        String codigo = generarCodigo();
        httpSession.setAttribute("CODIGO-CONFIRMACION", codigo);

        try {
            //enviarCorreo(asunto, usuario, codigo);
        } catch (Exception excepcion) {
            System.out.println(excepcion.getMessage());
        }
    }

    private String generarCodigo() {

        String key = "23456789ABCDEFGHJKMNPQRSTUVWXYZabcdefghijkmnpqrstuvwxyz";
        String pswd = "";
        for (int i = 0; i < 6; i++) {
            pswd += (key.charAt((int) (Math.random() * key.length())));
        }
        return pswd;
    }

    private boolean validarSaldo(int saldo, int valor) {
        if (saldo >= valor) {
            return true;
        } else if (saldo < valor) {
            return false;
        }
        return false;
    }

    public void enviarCorreo(String asunto, Usuario usuario, String codigo) {

        final Properties properties = new Properties();
        Session session;
        String subject = "";
        String mensaje = "";
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.port", 25);
        properties.put("mail.smtp.mail.sender", "notificacionesbmu@gmail.com");
        properties.put("mail.smtp.user", "notificacionesbmu");
        properties.put("mail.smtp.clave", "Clave12345!");    //La clave de la cuenta
        properties.put("mail.smtp.auth", "true");    //Usar autenticación mediante usuario y clave
        properties.put("mail.smtp.starttls.enable", "true"); //Para conectar de manera segura al servidor SMTP
        properties.put("mail.smtp.port", "587");

        session = Session.getDefaultInstance(properties);

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress((String) properties.get("mail.smtp.mail.sender")));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(usuario.getEmail()));
            switch (asunto) {
                case "codigo":
                    subject = "Código de seguridad";
                    mensaje = "Estimado cliente:\n"
                            + "\n"
                            + "Banco MU le informa que el codigo de seguridad para realizar su Pago es " + codigo + " \n"
                            + "\n"
                            + "Banco MU.\n"
                            + "\n"
                            + "============================================\n"
                            + "\n"
                            + "Por favor no responda este correo.\n"
                            + "\n"
                            + "Para cualquier informacion adicional puede consultar nuestra pagina de Internet o comunicarse con nosotros a traves de las siguientes opciones:\n"
                            + "\n"
                            + "Linea MU\n"
                            + "Bogota 3077060\n"
                            + "Resto del pais 018000910038\n"
                            + "============================================\n"
                            + "\n"
                            + "Este correo fue enviado por peticion suya. Si desea no ser contactado desde esta direccion de correo, por favor ingrese a nuestra pagina de Internet o acerquese a la oficina sede de su cuenta para modificar la matricula de notificaciones. Toda informacion contenida en este mensaje es considerada de caracter confidencial y/o privilegiado y esta dirigida unicamente a su destinatario, quien por tal razon es el unico autorizado para leerla y utilizarla. Si usted ha recibido por error este mensaje debe eliminarlo totalmente de su sistema y comunicar tal situacion al remitente de inmediato.\n"
                            + "============================================\n"
                            + "\n"
                            + "Tildes omitidas para manejar compatibilidad entre agentes de correo\n"
                            + "\n"
                            + "============================================";

                    break;
                case "ingreso":

                    subject = "Ingreso a canal digital ";
                    mensaje = "Estimado cliente.\n"
                            + "\n"
                            + "Banco MU le informa que usted ingreso el " + new Date() + " a nuestro canal digital. Si usted no es quien esta intentando ingresar, por favor comuniquese de inmediato con la Linea de atencion 307 7060 en Bogota, 01 8000 910 038 en el ambito nacional o #233 marcando desde su celular. De lo contrario, omita este mensaje.\n"
                            + "\n"
                            + "Su banco MU.\n"
                            + "\n"
                            + "============================================\n"
                            + "\n"
                            + "Por favor no responda este correo.\n"
                            + "\n"
                            + "Para cualquier informacion adicional puede consultar nuestra pagina de Internet o comunicarse con nosotros a traves de las siguientes opciones:\n"
                            + "\n"
                            + "Linea MU\n"
                            + "Bogota 3077060\n"
                            + "Resto del pais 018000910038\n"
                            + "============================================\n"
                            + "\n"
                            + "Este correo fue enviado por peticion suya. Si desea no ser contactado desde esta direccion de correo, por favor ingrese a nuestra pagina de Internet o acerquese a la oficina sede de su cuenta para modificar la matricula de notificaciones. Toda informacion contenida en este mensaje es considerada de caracter confidencial y/o privilegiado y esta dirigida unicamente a su destinatario, quien por tal razon es el unico autorizado para leerla y utilizarla. Si usted ha recibido por error este mensaje debe eliminarlo totalmente de su sistema y comunicar tal situacion al remitente de inmediato.\n"
                            + "\n"
                            + "============================================\n"
                            + "\n"
                            + "Tildes omitidas para manejar compatibilidad entre agentes de correo\n"
                            + "\n"
                            + "============================================";
                    break;
                case "confirmacion":

                    subject = "NOTIFICACION DE TRANSACCION ";
                    mensaje = "Estimado cliente con cuenta \n"
                            + "\n"
                            + "Banco MU le informa que el dia " + new Date() + " se realizo una transaccion EXITOSA a traves del canal digital. Oficina/Terminal: , que cumple con las condiciones de notificacion establecidas por usted.\n"
                            + "\n"
                            + "Nos complace contar con clientes como usted.\n"
                            + "\n"
                            + "============================================\n"
                            + "\n"
                            + "Por favor no responda este correo.\n"
                            + "\n"
                            + "Para cualquier informacion adicional puede consultar nuestra pagina de Internet\n"
                            + "\n"
                            + "============================================\n"
                            + "\n"
                            + "Este correo fue enviado por peticion suya. Si desea no ser contactado desde esta direccion de correo, por favor ingrese a nuestra pagina de Internet o acerquese a la oficina sede de su cuenta para modificar la matricula de notificaciones. Toda informacion contenida en este mensaje es considerada de caracter confidencial y/o privilegiado y esta dirigida unicamente a su destinatario, quien por tal razon es el unico autorizado para leerla y utilizarla. Si usted ha recibido por error este mensaje debe eliminarlo totalmente de su sistema y comunicar tal situacion al remitente de inmediato.\n"
                            + "\n"
                            + "============================================\n"
                            + "\n"
                            + "Tildes omitidas para manejar compatibilidad entre agentes de correo\n"
                            + "\n"
                            + "============================================";
                    break;

            }
            message.setSubject(subject);
            message.setText(mensaje);
            Transport t = session.getTransport("smtp");

            t.connect("smtp.gmail.com", (String) properties.get("mail.smtp.user"), "Clave12345!");
            t.sendMessage(message, message.getAllRecipients());
            t.close();
        } catch (MessagingException me) {
            me.printStackTrace();   //Si se produce un error
            //Aqui se deberia o mostrar un mensaje de error o en lugar
            //de no hacer nada con la excepcion, lanzarla para que el modulo
            //superior la capture y avise al usuario con un popup, por ejemplo.
            return;
        }

    }
}
