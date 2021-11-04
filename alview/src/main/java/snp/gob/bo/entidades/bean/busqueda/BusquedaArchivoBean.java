/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snp.gob.bo.entidades.bean.busqueda;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
//
//import net.sf.jasperreports.engine.JRException;
//import net.sf.jasperreports.engine.JasperExportManager;
//import net.sf.jasperreports.engine.JasperFillManager;
//import net.sf.jasperreports.engine.JasperPrint;
//import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import snp.gob.bo.almodel.entidad.BusquedaArchivo;
import snp.gob.bo.almodel.entidad.Dominio;
import snp.gob.bo.almodel.entidad.Usuario;
import snp.gob.bo.almodel.servicio.BusquedaArchivoService;
import snp.gob.bo.almodel.servicio.ComunService;
import snp.gob.bo.almodel.servicio.DominioService;
import snp.gob.bo.almodel.servicio.UsuarioPaginaService;
import snp.gob.bo.almodel.servicio.UsuarioService;
import snp.gob.bo.entidades.bean.common.AbstractManagedBean;

/**
 *
 * @author Placido Castro
 * @version 1.0, 05/07/2017
 *
 */
@ManagedBean(name = "busquedaArchivosBean")
@ViewScoped
public class BusquedaArchivoBean extends AbstractManagedBean implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -7951367411130310594L;

	@ManagedProperty(value = "#{dominioService}")
	private DominioService dominioService;

	@ManagedProperty(value = "#{usuarioService}")
	private UsuarioService usuarioService;

	@ManagedProperty(value = "#{usuarioPaginaService}")
	private UsuarioPaginaService usuarioPaginaService;

	@ManagedProperty("#{busquedaArchivoService}")
	private BusquedaArchivoService busquedaArchivoService;

	@ManagedProperty("#{comunService}")
	private ComunService comunService;

	// <editor-fold defaultstate="collapsed" desc="Definicion de servicios y
	// atributos">
	private Boolean criterioRendered = true;
	private String marcaDescripcion = "";
	private Long nroSolicitud = null;
	private Long nroPublicacion = null;
	private Long nroRegistro = null;
	private Long nroResolucion = null;
	private String etiqueta;
	private double tiempoEjecucion;
	private Usuario usuario;
	private Boolean tieneAcceso;

	// variables parametros
	public String subFondo;
	public List<Dominio> lstSubFondo = new ArrayList<>();
	public String seccion;
	public List<Dominio> lstSeccion = new ArrayList<>();
	public String serie;
	public List<Dominio> lstSerie = new ArrayList<>();
	private Date fechaDesde;
	private Date fechaHasta;

	// atributos reportes
	private String imgSenapi = "";
	private String imgEscudo = "";
	private String fechaHoy = "";
	private String horaHoy = "";
	private String campoBusqueda = "";
	private String campo = "";
	private String frase = "";
	private String criterio = "";

	private Map<String, Object> parametros = new HashMap<String, Object>();

	BusquedaArchivo busquedaArchivo;
	private List<BusquedaArchivo> listaBusquedaArchivo = new ArrayList<BusquedaArchivo>();

	// </editor-fold>
	public BusquedaArchivoBean() {
	}

	@PostConstruct
	public void BusquedaArchivosInit() {

		try {
			if (getIdUsuarioSession() != null) {
				this.usuario = this.usuarioService.buscaUsuarioPorIdUsuario(getIdUsuarioSession());
			}
			this.tieneAcceso = this.usuarioPaginaService.estadoBotonUsuario(this.usuario.getIdusuario(),
					"Imprimir Reporte");
		} catch (Exception e) {
		}

	}

	///// metodos de archivos
	public void listaDatosComboSeccion(String subFondoVista) throws Exception {
		this.lstSeccion = this.dominioService.ListaTipoTramiteReciboDominio("seccion", subFondoVista);

		if (this.lstSeccion.isEmpty()) {
			this.seccion = "";
			this.serie = "";
		}
		this.lstSerie = new ArrayList<>();
	}

	public void listaDatosSerie(String seccionVista) throws Exception {
		this.lstSerie = this.dominioService.ListaTipoTramiteReciboDominio("serie", seccionVista);
		if (this.lstSerie.isEmpty()) {
			this.serie = "";
		}

	}

	public String devuelveSm(String tipoSolicitud, Long numeroSolicitud, Long gestionSolicitud) throws Exception {
		if (numeroSolicitud != 0) {
			return tipoSolicitud + "-" + numeroSolicitud + "-" + gestionSolicitud;
		}
		return "";
	}

	public String devuelveRegistro(Long numeroRegistro, String serieRegistro) throws Exception {
		String vnumeroRegistro = "";

		if (numeroRegistro != 0) {
			if (!serieRegistro.equals("")) {
				vnumeroRegistro = numeroRegistro + "-" + serieRegistro;
			} else {
				vnumeroRegistro = numeroRegistro.toString();
			}
		}
		return vnumeroRegistro;
	}

	public String devuelveResolucion(Long numeroResolucion, Long gestionResolucion) throws Exception {
		String vnumeroResolucion = "";
		if (numeroResolucion != 0) {
			if (gestionResolucion != 0) {
				vnumeroResolucion = numeroResolucion + "/" + gestionResolucion;
			} else {
				vnumeroResolucion = numeroResolucion.toString();
			}
		}
		return vnumeroResolucion;
	}

	/**
	 * * Método para realizar la busqueda de signos Creado: Placido Castro
	 * Fecha:05/07/2017
	 *
	 */
	public void accionBusquedaArchivo() {

		try {
			Long timeStart, timeEnd;

			timeStart = System.currentTimeMillis();
			if (this.marcaDescripcion == null) {
				this.marcaDescripcion = "";
			} else {
				this.marcaDescripcion = this.marcaDescripcion.trim();
			}

			// realizar las busqueda
			this.listaBusquedaArchivo = this.busquedaArchivoService.realizarBusquedaArchivo(this.subFondo, this.seccion, this.serie, this.fechaDesde, 
					this.fechaHasta, this.marcaDescripcion, this.nroSolicitud, this.nroPublicacion, this.nroRegistro, this.nroResolucion);

			timeEnd = System.currentTimeMillis();
			this.setTiempoEjecucion((timeEnd - timeStart) / 100);

		} catch (Exception ex) {
			Logger.getLogger(BusquedaArchivoBean.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * Metodo para llenar los datos del reporte y su generacion en formato pdf
	 *
	 * Creado: Ruben Ramirez Fecha: 20/10/2016
	 *
	 */
	public void reportePDF() throws  IOException, Exception {

		if (this.listaBusquedaArchivo.size() > 0) {
			this.imgSenapi = FacesContext.getCurrentInstance().getExternalContext()
					.getRealPath("/images/logoNuevo.jpg");
			this.imgEscudo = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/images/esc-Bolivia");

			DateFormatSymbols dateFormatSymbols = new DateFormatSymbols();
			dateFormatSymbols.setWeekdays(new String[] { "Unused", "domingo", "lunes", "martes", "miercoles", "jueves",
					"viernes", "sábado" });
			SimpleDateFormat formateador3 = new SimpleDateFormat("EEEEE, dd MMMMM, yyyy", dateFormatSymbols);
			SimpleDateFormat formateador4 = new SimpleDateFormat("h:mm a");

			// fecha actual de la base de datos
			Date fechaPresente = this.comunService.obtenerFechaHoraServidor(1L);

			if (fechaPresente != null) {
				this.fechaHoy = formateador3.format(fechaPresente);
				this.horaHoy = formateador4.format(fechaPresente);
			}

			if (this.marcaDescripcion == null) {
				this.marcaDescripcion = "";
			}

			this.parametros.put("imgSenapi", this.imgSenapi);
			this.parametros.put("imgEscudo", this.imgEscudo);
			this.parametros.put("fechaHoy", this.fechaHoy);
			this.parametros.put("horaHoy", this.horaHoy);
			this.parametros.put("marcaDescripcion", this.marcaDescripcion.toUpperCase());

			String filename = "BusquedaArchivos.pdf";
			String jasperPath = "/template/busquedas/BusquedaArchivos.jasper";
		//	this.PDF(this.parametros, jasperPath, this.listaBusquedaArchivo, filename);
		}
	}

	/**
	 * Metodo para generar el reporte en formato pdf
	 *
	 * Creado: Ruben Ramirez Fecha: 20/10/2016
	 *
	 * @param params
	 * @param jasperPath
	 * @param dataSource
	 * @param fileName
	 * @throws net.sf.jasperreports.engine.JRException
	 * @throws java.io.IOException
	 */
	public void PDF(Map<String, Object> params, String jasperPath, List<?> dataSource, String fileName)
			throws  IOException {
		/*String relativeWebPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath(jasperPath);
		File file = new File(relativeWebPath);
		JasperPrint print = new JasperPrint();
		JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(dataSource, false);
		print = JasperFillManager.fillReport(file.getPath(), params, source);
		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
				.getResponse();
		response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
		ServletOutputStream stream = response.getOutputStream();
		JasperExportManager.exportReportToPdfStream(print, stream);
		FacesContext.getCurrentInstance().responseComplete();*/
	}

	/**
	 * Metodo para generar el reporte en formato Excel
	 *
	 */

	public void reporteExcel(Object document) {
		HSSFWorkbook wb = (HSSFWorkbook) document;
		HSSFSheet sheet = wb.getSheetAt(0);
		CellStyle style = wb.createCellStyle();
		style.setFillBackgroundColor(IndexedColors.AQUA.getIndex());

		for (Row row : sheet) {
			for (Cell cell : row) {
				cell.setCellValue(cell.getStringCellValue());
				cell.setCellStyle(style);
			}
		}
	}

	// <editor-fold defaultstate="collapsed" desc="Definicion de getters y
	// setters">
	public DominioService getDominioService() {
		return this.dominioService;
	}

	public void setDominioService(DominioService dominioService) {
		this.dominioService = dominioService;
	}

	public UsuarioService getUsuarioService() {
		return this.usuarioService;
	}

	public void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public UsuarioPaginaService getUsuarioPaginaService() {
		return this.usuarioPaginaService;
	}

	public void setUsuarioPaginaService(UsuarioPaginaService usuarioPaginaService) {
		this.usuarioPaginaService = usuarioPaginaService;
	}

	public String getImgSenapi() {
		return this.imgSenapi;
	}

	public void setImgSenapi(String imgSenapi) {
		this.imgSenapi = imgSenapi;
	}

	public String getImgEscudo() {
		return this.imgEscudo;
	}

	public void setImgEscudo(String imgEscudo) {
		this.imgEscudo = imgEscudo;
	}

	public String getFechaHoy() {
		return this.fechaHoy;
	}

	public void setFechaHoy(String fechaHoy) {
		this.fechaHoy = fechaHoy;
	}

	public String getHoraHoy() {
		return this.horaHoy;
	}

	public void setHoraHoy(String horaHoy) {
		this.horaHoy = horaHoy;
	}

	public String getCampoBusqueda() {
		return this.campoBusqueda;
	}

	public void setCampoBusqueda(String campoBusqueda) {
		this.campoBusqueda = campoBusqueda;
	}

	public String getCampo() {
		return this.campo;
	}

	public void setCampo(String campo) {
		this.campo = campo;
	}

	public String getFrase() {
		return this.frase;
	}

	public void setFrase(String frase) {
		this.frase = frase;
	}

	public String getCriterio() {
		return this.criterio;
	}

	public void setCriterio(String criterio) {
		this.criterio = criterio;
	}

	public Map<String, Object> getParametros() {
		return this.parametros;
	}

	public void setParametros(Map<String, Object> parametros) {
		this.parametros = parametros;
	}

	public ComunService getComunService() {
		return this.comunService;
	}

	public void setComunService(ComunService comunService) {
		this.comunService = comunService;
	}

	public Boolean getCriterioRendered() {
		return this.criterioRendered;
	}

	public void setCriterioRendered(Boolean criterioRendered) {
		this.criterioRendered = criterioRendered;
	}

	public String getMarcaDescripcion() {
		return marcaDescripcion;
	}

	public void setMarcaDescripcion(String marcaDescripcion) {
		this.marcaDescripcion = marcaDescripcion;
	}

	public String getEtiqueta() {
		return this.etiqueta;
	}

	public Long getNroSolicitud() {
		return nroSolicitud;
	}

	public void setNroSolicitud(Long nroSolicitud) {
		this.nroSolicitud = nroSolicitud;
	}

	public Long getNroPublicacion() {
		return nroPublicacion;
	}

	public void setNroPublicacion(Long nroPublicacion) {
		this.nroPublicacion = nroPublicacion;
	}

	public Long getNroRegistro() {
		return nroRegistro;
	}

	public void setNroRegistro(Long nroRegistro) {
		this.nroRegistro = nroRegistro;
	}

	public Long getNroResolucion() {
		return nroResolucion;
	}

	public void setNroResolucion(Long nroResolucion) {
		this.nroResolucion = nroResolucion;
	}

	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}

	public double getTiempoEjecucion() {
		return this.tiempoEjecucion;
	}

	public void setTiempoEjecucion(double tiempoEjecucion) {
		this.tiempoEjecucion = tiempoEjecucion;
	}

	public Boolean getTieneAcceso() {
		return this.tieneAcceso;
	}

	public void setTieneAcceso(Boolean tieneAcceso) {
		this.tieneAcceso = tieneAcceso;
	}

	public String getSubFondo() {
		return this.subFondo;
	}

	public void setSubFondo(String subFondo) {
		this.subFondo = subFondo;
	}

	public List<Dominio> getLstSubFondo() throws Exception {
		// return lstSubFondo;
		return this.lstSubFondo = this.dominioService.obtenerListadoDominio("subfondo");
	}

	public void setLstSubFondo(List<Dominio> lstSubFondo) {
		this.lstSubFondo = lstSubFondo;
	}

	public String getSeccion() {
		return this.seccion;
	}

	public void setSeccion(String seccion) {
		this.seccion = seccion;
	}

	public List<Dominio> getLstSeccion() {
		return this.lstSeccion;
	}

	public void setLstSeccion(List<Dominio> lstSeccion) {
		this.lstSeccion = lstSeccion;
	}

	public String getSerie() {
		return this.serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public List<Dominio> getLstSerie() {
		return this.lstSerie;
	}

	public void setLstSerie(List<Dominio> lstSerie) {
		this.lstSerie = lstSerie;
	}

	public Date getFechaDesde() {
		return this.fechaDesde;
	}

	public void setFechaDesde(Date fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public Date getFechaHasta() {
		return this.fechaHasta;
	}

	public void setFechaHasta(Date fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

	public BusquedaArchivo getBusquedaArchivo() {
		return this.busquedaArchivo;
	}

	public void setBusquedaArchivo(BusquedaArchivo busquedaArchivo) {
		this.busquedaArchivo = busquedaArchivo;
	}

	public List<BusquedaArchivo> getListaBusquedaArchivo() {
		return this.listaBusquedaArchivo;
	}

	public void setListaBusquedaArchivo(List<BusquedaArchivo> listaBusquedaArchivo) {
		this.listaBusquedaArchivo = listaBusquedaArchivo;
	}

	public BusquedaArchivoService getBusquedaArchivoService() {
		return this.busquedaArchivoService;
	}

	public void setBusquedaArchivoService(BusquedaArchivoService busquedaArchivoService) {
		this.busquedaArchivoService = busquedaArchivoService;
	}

	// </editor-fold>
}
