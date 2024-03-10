/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package alemanpluming;

import alemanpluming.Querys;
import alemanpluming.factura;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author dsoto
 */
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

public class SegundaController implements Initializable {

    @FXML
    private Button saveBoton;
    @FXML
    private TextArea terminos;
    @FXML
    private Label label_terminos;
    @FXML
    private Label terminos_invoice;
    @FXML
    private Button boton_search;
    @FXML
    private TextField cliente;
    @FXML
    private TextField direccion;
    @FXML
    private DatePicker fecha;
    @FXML
    private Label folio;
    @FXML
    private TextField txt_factura;
    @FXML
    private TextArea detalle_materiales;

    @FXML
    private TextField material;
    @FXML
    private TextField cantidad;
    @FXML
    private TextField costo;
    @FXML
    private TextField orden;
    @FXML
    private TextArea descripcion;
    @FXML
    private TextField total_orden;
    @FXML
    private TextField cargo_viaje;
    @FXML
    private TextField trabajador;
    @FXML
    private DatePicker trabajador_fecha;
    @FXML
    private TextArea comentarios;
    @FXML
    private TextField costo_materiales;
    @FXML
    private TextField impuesto;
    @FXML
    private TextField tarifa_madera;
    @FXML
    private TextField cargo_trabajo;
    @FXML
    private TextField tarifa_cobros;
    @FXML
    private TextField tarifa_cobros_idroject;
    @FXML
    private TextField total;

    @FXML
    private Label numero_db;
    @FXML

    private Label cliente_r;
    @FXML

    private Label fecha_r;

    @FXML
    private Label txt_res_numero;
    @FXML
    private Label monto_r;

    private materiales instancia;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try (Connection con = conexion.conectar()) {
            Querys.crearTablaFactura(con);
            try {
                obtenerFolio();
                obtenerTerminos();
                obtenercantidad();
                obtenerUltimaFactura();

            } catch (Exception e) {

            }
        } catch (ClassNotFoundException | SQLException ex) {
            System.err.println(ex.getMessage());
        }

        instancia = new materiales();
        fecha.setValue(LocalDate.now());
        trabajador_fecha.setValue(LocalDate.now());
        trabajador_fecha.setConverter(new StringConverter<LocalDate>(){
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            @Override
            public String toString(LocalDate date) {
                 if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
            
        });
        fecha.setConverter(new StringConverter<LocalDate>(){
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            @Override
            public String toString(LocalDate date) {
                 if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
            
        });

    }

    @FXML
    private void buscar_numero_factura() {
        String facturaNumero = txt_factura.getText();

        if (!facturaNumero.isEmpty()) {

            try {
                factura factura = Querys.getFactura(facturaNumero);
                txt_res_numero.setText("Invoice Found : " + factura.getFactura());

                Alert al = new Alert(Alert.AlertType.CONFIRMATION);
                al.setTitle("Confirmation");
                al.setContentText("The invoice has been found, waiting for a coin to be generated.");
                al.showAndWait();
                Pdf(factura, factura.getMaterialesList());
            } catch (SQLException | ClassNotFoundException ex) {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle("Error");
                al.setContentText("The invoice is not registered in the database.");
                al.showAndWait();
                txt_res_numero.setText("Results:");

                txt_factura.requestFocus();
            }

        }
    }

    private void Pdf(factura f, ArrayList<materiales> mate) {
        try {

            List<String> matea = new ArrayList<>();

// Obtener las primeras columnas (ajusta según tus necesidades)
            for (materiales material : mate) {
                String cantidad = String.valueOf(material.getCantidad());
                String nombre = material.getNombre();
                String costo = String.valueOf(material.getCosto());
                String total = String.valueOf(material.getCantidad() * material.getCosto());

                // Agregar una instancia de FilaInforme a la lista
                matea.add(nombre + " - " + costo + " * " + cantidad + " => " + total + "\n");  // Agrega un salto de línea (\n)

            }

            URL reportePath = getClass().getResource("/resources/reporte.jasper");
            //InputStream inputStream = reportePath.openStream();
            //JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
            JasperReport reporte=(JasperReport) JRLoader.loadObject(reportePath);
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("folio", f.getFactura());
            parametros.put("cliente", f.getCliente().getOrderBy());
            parametros.put("direccion", f.getCliente().getAddress());
            parametros.put("fecha", f.getCliente().getDate());
            parametros.put("descripcion_trabajo", f.getTrabajo().getDetalles());
            parametros.put("trabajo", (f.getTrabajo().getTrabajo()).toUpperCase());
            parametros.put("cargo_viaje", f.getTrabajo().getCargo_viaje());
            parametros.put("total_labor", f.getTrabajo().getTotal());
            parametros.put("material_f", f.getCostos().getCosto_materiales());
            parametros.put("tax_f", f.getCostos().getImpuesto());
            parametros.put("lumber_f", f.getCostos().getTarifa_madera());
            parametros.put("cargo_f", f.getCostos().getCargo_trabajo());
            parametros.put("snake_f", f.getCostos().getTarifa_cobros());
            parametros.put("jetter_f", f.getCostos().getTarifa_cobros_idroject());
            parametros.put("total_f", f.getCostos().getTotal());
            parametros.put("trabajador", f.getOtros().getTrabajador());
            parametros.put("fecha_trabajador", f.getOtros().getFecha().toString());
            parametros.put("comentarios_trabajo", f.getOtros().getComentarios());
            parametros.put("terminos", f.getOtros().getTerminos());
            parametros.put("logo",getClass().getResourceAsStream("/imagenes/logo.png"));
            String mateaString = String.join("", matea);

            parametros.put("lista_material", mateaString);

           

            JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parametros, new JREmptyDataSource());
            JasperViewer jv = new JasperViewer(jasperPrint, false);
            jv.setVisible(true);

        } catch (JRException e) {
            System.err.println(e.getMessage());
        }
    }

    private void obtenerTerminos() {
        try {
            String termino = Querys.GetTerminos();
            if (!termino.equals("0")) {
                label_terminos.setText(termino);
                terminos_invoice.setText(termino);
            } else {
                label_terminos.setText("No Configurado");
                terminos_invoice.setText("No Configurado");
            }

        } catch (Exception ex) {
            label_terminos.setText("Not Configured");
            terminos_invoice.setText("Not Configured");
            System.out.println(ex.getMessage());
        }
    }

    private void obtenerFolio() {
        try {
            String re = Querys.getFolio();
            if (!re.equals("0")) {
                folio.setText(re);
            } else {
                folio.setText(String.format("%04d", 1));

            }
        } catch (SQLException | ClassNotFoundException ex) {
            folio.setText(String.format("%04d", 1));
            System.out.println("Error al obtener el folio: " + ex.getMessage());
        }
    }

    private void obtenercantidad() {
        try {
            int re = Querys.getCantidad();
            if (re != 0) {
                numero_db.setText(String.valueOf(re));
            } else {
                numero_db.setText("0");
            }
        } catch (SQLException | ClassNotFoundException ex) {
            numero_db.setText("0");
            System.out.println("Error al obtener el cantidad: " + ex.getMessage());
        }
    }

    private void obtenerUltimaFactura() {
        try {
            ArrayList<String> re = Querys.UltimaFactura();
            if (!re.isEmpty()) {
                for (String value : re) {

                    cliente_r.setText(re.get(0));
                    fecha_r.setText(re.get(1));
                    monto_r.setText(re.get(2));
                }
            }
        } catch (SQLException | ClassNotFoundException ex) {
            cliente_r.setText("No Data");
            fecha_r.setText("No Data");
            monto_r.setText("No Data");
            System.out.println("Error al obtener FACTURA: " + ex.getMessage());
        }
    }

    @FXML
    private void onSaveBotonClicked(ActionEvent event
    ) {

        try {
            String termino = terminos.getText();
            if (termino.equals("")) {
                terminos.requestFocus();
                mostrarAlerta("Error", "Empty Term Field", "Please enter a term before saving.");

            } else {
                boolean respuesta = Querys.InsertarTermino(termino);
                if (respuesta) {
                    label_terminos.setText(terminos.getText());
                    terminos_invoice.setText(terminos.getText());
                    terminos.setText("");
                    mostrarAlerta("Success", "Term Saved", "The term has been saved successfully.");

                } else {
                    mostrarAlerta("Error", "Error Saving", "There was an issue trying to save the term.");

                }
            }

        } catch (ClassNotFoundException | SQLException ex) {
            mostrarAlerta("Error", "Database Error", "There was an issue connecting to the database.");
            System.err.println(ex.getMessage());
        }

    }

    @FXML
    private void onSearchBotonClicked(ActionEvent event
    ) {
        try {
            String nombre = cliente.getText();
            if (nombre.isEmpty()) {
                cliente.requestFocus();
                mostrarAlerta("Error", "Empty Client Field", "Please enter a client before searching.");

            } else {
                String respuesta = Querys.GetCliente(nombre);
                if (!respuesta.equals("0")) {
                    direccion.setText(respuesta);
                } else {
                    mostrarAlerta("Error", "Not Data", "The client is not registered in the database.");

                    cliente.requestFocus();
                    direccion.setText("");
                    fecha.setValue(LocalDate.now());
                }
            }

        } catch (ClassNotFoundException | SQLException ex) {
            cliente.requestFocus();
            direccion.setText("");
            fecha.setValue(LocalDate.now());
            mostrarAlerta("Error", "Database Error", "There was an issue connecting to the database.");
            System.err.println(ex.getMessage());
        }

    }

    @FXML
    private void onGuardarBotonClicked(ActionEvent event
    ) {
        ArrayList<materiales> mate = instancia.getMateriales();

        if (ValidarCampos()) {
            clientr cl = new clientr(cliente.getText(), direccion.getText(), Date.valueOf(fecha.getValue()));
            trabajo tr = new trabajo(descripcion.getText(), orden.getText(), Double.parseDouble(cargo_viaje.getText()), Double.parseDouble(total_orden.getText()));
            otros ot = new otros(trabajador.getText(), Date.valueOf(trabajador_fecha.getValue()), comentarios.getText(), terminos_invoice.getText());
            costos co = new costos(Double.parseDouble(costo_materiales.getText()), Double.parseDouble(impuesto.getText()), Double.parseDouble(tarifa_madera.getText()), Double.parseDouble(cargo_trabajo.getText()), Double.parseDouble(tarifa_cobros.getText()), Double.parseDouble(tarifa_cobros_idroject.getText()), Double.parseDouble(total.getText()));
            factura fa = new factura(cl, instancia, tr, ot, co, folio.getText());
            factura f = Querys.GuardarFactura(mate, fa);
            if (f != null) {
                mostrarMensajeExito();
                obtenerFolio();
                obtenerUltimaFactura();
                obtenercantidad();
                Pdf(f, mate);
            }
        }
    }

    @FXML
    private void Salir(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    private void onGuardarMaterialClicked(ActionEvent event
    ) {
        if (ValidarCamposMaterial()) {
            AgregarMaterial();
        }
    }

    @FXML
    private void onKeyReleaseText() {
        Calcular_Total();
    }

    @FXML
    private void onKeyReleaseText2() {
        Calcular_Total2();
    }

    private boolean ValidarCamposMaterial() {
        boolean camposValidos = true;
        // Validar material
        if (material.getText().isEmpty()) {
            mostrarMensajeError("The Material field cannot be empty.");
            camposValidos = false;
            material.requestFocus();
        }

        // Validar cantidad
        try {
            int cantidadValue = Integer.parseInt(cantidad.getText());
            if (cantidadValue <= 0) {
                mostrarMensajeError("The quantity must be a positive number.");
                camposValidos = false;
                cantidad.requestFocus();
            }
        } catch (NumberFormatException e) {
            mostrarMensajeError("The quantity must be a positive number.");
            camposValidos = false;
            cantidad.requestFocus();

        }
        try {
            double cantidadValue = Double.parseDouble(costo.getText());
            if (cantidadValue <= 0) {
                mostrarMensajeError("The cost must be a positive number.");
                camposValidos = false;
                costo.requestFocus();
            }
        } catch (NumberFormatException e) {
            mostrarMensajeError("The cost must be a positive number.");
            camposValidos = false;
            costo.requestFocus();

        }
        return camposValidos;
    }

    private void AgregarMaterial() {
        double m_cantidad = Double.parseDouble(cantidad.getText());
        double m_costo = Double.parseDouble(costo.getText());
        instancia.GuardarMaterial(material.getText(), m_cantidad, m_costo);

        Calcular_costo_materiales();
        Calcular_Total();

    }

    private void Calcular_costo_materiales() {
        StringBuilder detalleMaterialesTexto = new StringBuilder();
        ArrayList<materiales> array_materiales = instancia.getMateriales();
        BigDecimal totalo = BigDecimal.ZERO;
        for (materiales materi : array_materiales) {
            BigDecimal cantidadd = BigDecimal.valueOf(materi.getCantidad());
            BigDecimal costoo = BigDecimal.valueOf(materi.getCosto());
            BigDecimal totalMaterial = cantidadd.multiply(costoo);
            detalleMaterialesTexto.append("Quant. ").append(materi.getCantidad())
                    .append(" /").append(materi.getNombre())
                    .append("/ Cost: ").append(materi.getCosto())
                    .append(" Tot. ").append(totalMaterial)
                    .append("\n");

            totalo = totalo.add(totalMaterial);
        }
        detalle_materiales.clear();
        detalle_materiales.appendText(detalleMaterialesTexto.toString());
        costo_materiales.setText(String.valueOf(totalo));
    }

    private void Calcular_Total() {
        String costoMaterialesTexto = costo_materiales.getText();
        String impuestoTexto = impuesto.getText();
        String tarifaMaderaTexto = tarifa_madera.getText();
        String cargoTrabajoTexto = cargo_trabajo.getText();
        String tarifaCobrosTexto = tarifa_cobros.getText();
        String tarifaCobrosIdrojectTexto = tarifa_cobros_idroject.getText();

        // Convertir a BigDecimal
        BigDecimal c_materiales = new BigDecimal(costoMaterialesTexto);
        BigDecimal c_interes = new BigDecimal(impuestoTexto);
        BigDecimal c_lumberfee = new BigDecimal(tarifaMaderaTexto);
        BigDecimal c_cargo = new BigDecimal(cargoTrabajoTexto);
        BigDecimal snake_cargos = new BigDecimal(tarifaCobrosTexto);
        BigDecimal hidro_jetter = new BigDecimal(tarifaCobrosIdrojectTexto);
        BigDecimal totalo = BigDecimal.ZERO;

        // Sumar los valores
        totalo = totalo.add(c_materiales)
                .add(c_interes)
                .add(c_lumberfee)
                .add(c_cargo)
                .add(snake_cargos)
                .add(hidro_jetter);
        this.total.setText(totalo.toString());
    }

    private void Calcular_Total2() {
        String costoMaterialesTexto = costo_materiales.getText();
        String impuestoTexto = impuesto.getText();
        String tarifaMaderaTexto = tarifa_madera.getText();
        String cargoTrabajoTexto = total_orden.getText();
        String tarifaCobrosTexto = tarifa_cobros.getText();
        String tarifaCobrosIdrojectTexto = tarifa_cobros_idroject.getText();

        // Convertir a BigDecimal
        BigDecimal c_materiales = new BigDecimal(costoMaterialesTexto);
        BigDecimal c_interes = new BigDecimal(impuestoTexto);
        BigDecimal c_lumberfee = new BigDecimal(tarifaMaderaTexto);
        BigDecimal c_cargo = new BigDecimal(cargoTrabajoTexto);
        BigDecimal snake_cargos = new BigDecimal(tarifaCobrosTexto);
        BigDecimal hidro_jetter = new BigDecimal(tarifaCobrosIdrojectTexto);
        BigDecimal totalo = BigDecimal.ZERO;

        // Sumar los valores
        totalo = totalo.add(c_materiales)
                .add(c_interes)
                .add(c_lumberfee)
                .add(c_cargo)
                .add(snake_cargos)
                .add(hidro_jetter);
        this.total.setText(totalo.toString());
        cargo_trabajo.setText(c_cargo.toString());
    }

    @FXML
    private void Reset() {
        cliente.clear();
        direccion.clear();
        fecha.setValue(LocalDate.now());
        material.clear();
        costo.clear();
        cantidad.clear();
        detalle_materiales.clear();
        comentarios.setText("None");
        trabajador.clear();
        trabajador_fecha.setValue(LocalDate.now());
        orden.clear();
        descripcion.clear();
        cargo_trabajo.setText("0.0");
        cargo_viaje.setText("0.0");
        impuesto.setText("0.0");
        costo_materiales.setText("0.0");
        tarifa_cobros.setText("0.0");
        tarifa_cobros_idroject.setText("0.0");
        total_orden.clear();
        tarifa_madera.setText("0.0");
        total.setText("0.0");
        obtenerFolio();

    }

    private boolean ValidarCampos() {
        boolean camposValidos = true;

        // Validar cliente
        if (cliente.getText().isEmpty()) {
            mostrarMensajeError("The Client field cannot be empty.");
            camposValidos = false;
            cliente.requestFocus();
        }

        // Validar dirección
        if (direccion.getText().isEmpty()) {
            mostrarMensajeError("The Address field cannot be empty.");
            camposValidos = false;
            direccion.requestFocus();
        }

        // Validar fecha
        if (fecha.getValue() == null) {
            mostrarMensajeError("You must select a date.");
            camposValidos = false;
            fecha.requestFocus();
        }

        if (orden.getText().isEmpty()) {
            mostrarMensajeError("The Order field cannot be empty.");
            orden.requestFocus();
            camposValidos = false;
        }
        if (descripcion.getText().isEmpty()) {
            mostrarMensajeError("The description field cannot be empty.");
            descripcion.requestFocus();
            camposValidos = false;
        }
        try {
            double cantidadValue = Double.parseDouble(total_orden.getText());
            if (cantidadValue < 0) {
                mostrarMensajeError("The total order must be a positive number.");
                total_orden.requestFocus();
                camposValidos = false;
            }
        } catch (NumberFormatException e) {
            mostrarMensajeError("The total order must be a positive number.");
            camposValidos = false;
            total_orden.requestFocus();

        }
        try {
            double cantidadValue = Double.parseDouble(cargo_viaje.getText());
            if (cantidadValue < 0) {
                mostrarMensajeError("The travel charge must be a positive number.");
                cargo_viaje.requestFocus();
                camposValidos = false;
            }
        } catch (NumberFormatException e) {
            mostrarMensajeError("The travel charge must be a positive number.");
            camposValidos = false;
            cargo_viaje.requestFocus();

        }
        if (trabajador_fecha.getValue() == null) {
            mostrarMensajeError("You must select a date.");
            trabajador_fecha.requestFocus();
            camposValidos = false;
        }

        if (trabajador.getText().isEmpty()) {
            mostrarMensajeError("The Worker field cannot be empty.");
            trabajador.requestFocus();
            camposValidos = false;
        }
        if (comentarios.getText().isEmpty()) {
            mostrarMensajeError("The comments field cannot be empty.");
            comentarios.requestFocus();
            camposValidos = false;
        }
        try {
            double cantidadValue = Double.parseDouble(costo_materiales.getText());
            if (cantidadValue < 0) {
                mostrarMensajeError("The total cost of materials must be a positive number.");
                costo_materiales.requestFocus();
                camposValidos = false;
            }
        } catch (NumberFormatException e) {
            mostrarMensajeError("The total cost of materials must be a positive number.");
            camposValidos = false;
            costo_materiales.requestFocus();

        }
        try {
            double cantidadValue = Double.parseDouble(impuesto.getText());
            if (cantidadValue < 0) {
                mostrarMensajeError("The tax must be a positive number.");
                impuesto.requestFocus();
                camposValidos = false;
            }
        } catch (NumberFormatException e) {
            mostrarMensajeError("The tax must be a positive number.");
            camposValidos = false;
            impuesto.requestFocus();

        }
        try {
            double cantidadValue = Double.parseDouble(tarifa_madera.getText());
            if (cantidadValue < 0) {
                mostrarMensajeError("The rate must be a positive number.");
                tarifa_madera.requestFocus();
                camposValidos = false;
            }
        } catch (NumberFormatException e) {
            mostrarMensajeError("The rate must be a positive number.");
            camposValidos = false;
            tarifa_madera.requestFocus();

        }
        try {
            double cantidadValue = Double.parseDouble(cargo_trabajo.getText());
            if (cantidadValue < 0) {
                mostrarMensajeError("The labor charge must be a positive number.");
                cargo_trabajo.requestFocus();
                camposValidos = false;
            }
        } catch (NumberFormatException e) {
            mostrarMensajeError("The labor charge must be a positive number.");
            camposValidos = false;
            cargo_trabajo.requestFocus();

        }
        try {
            double cantidadValue = Double.parseDouble(tarifa_cobros.getText());
            if (cantidadValue < 0) {
                mostrarMensajeError("The fee rate must be a positive number.");
                tarifa_cobros.requestFocus();
                camposValidos = false;
            }
        } catch (NumberFormatException e) {
            mostrarMensajeError("The fee rate must be a positive number.");
            camposValidos = false;
            tarifa_cobros.requestFocus();

        }
        try {
            double cantidadValue = Double.parseDouble(tarifa_cobros_idroject.getText());
            if (cantidadValue < 0) {
                mostrarMensajeError("The fee rate must be a positive number.");
                tarifa_cobros_idroject.requestFocus();
                camposValidos = false;
            }
        } catch (NumberFormatException e) {
            mostrarMensajeError("The fee rate must be a positive number.");
            camposValidos = false;
            tarifa_cobros_idroject.requestFocus();

        }
        try {
            double cantidadValue = Double.parseDouble(total.getText());
            if (cantidadValue < 0) {
                mostrarMensajeError("The total must be a positive number.");
                total.requestFocus();
                camposValidos = false;
            }
        } catch (NumberFormatException e) {
            mostrarMensajeError("The total must be a positive number.");
            camposValidos = false;
            total.requestFocus();

        }

        return camposValidos;
    }

    private void mostrarMensajeExito() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Success");
        alert.setContentText("Invoice posted successfully. Please wait for the report to be generated.");
        alert.showAndWait();
    }

    private void mostrarMensajeError(String texto) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Validations Failed");
        alert.setContentText(texto);
        alert.showAndWait();
    }

    private void mostrarAlerta(String titulo, String encabezado, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(encabezado);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
