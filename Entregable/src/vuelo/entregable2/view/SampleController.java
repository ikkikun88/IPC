package vuelo.entregable2.view;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import application.data.Data;
import application.model.Airport;
import application.model.Flight;
public class SampleController extends Application {
    
	@FXML
    private TextField numAeropuertos;
	@FXML
    private TableView<Flight> tablaSalidas;
    @FXML
    private TableColumn<Flight, String> DestinoSalidas;
    @FXML
    private TableColumn<Flight, String> vueloLlegadas;
    @FXML
    private TableColumn<Flight, String> modeloSalidas;
    @FXML
    private TableColumn<Flight, String> retrasoLlegadas;
    @FXML
    private TableColumn<Flight, String> tipoLlegadas;
    @FXML
    private TableColumn<Flight, String> horaLlegadas;
    @FXML
    private TableColumn<Flight, String> horaSalidas;
    @FXML
    private TableColumn<Flight, String> modeloLlegadas;
    @FXML
    private TableView<Flight> tablaLlegadas;
    @FXML
    private TableColumn<Flight,String> retrasoSalidas;
    @FXML
    private TableColumn<Flight,String> vueloSalidas;
    @FXML
    private TableColumn<Flight, String>origenLlegadas;
    @FXML
    private TableColumn<Flight, String> tipoSalidas;
    @FXML
    private TableColumn<Flight, String> companiaLlegadas;
    @FXML
    private TableColumn<Flight, String> fechaSalidas;
    @FXML
    private TableColumn<Flight, String> fechaLlegadas;
    @FXML
    private TableColumn<Flight, String> companiaSalidas;
	@FXML
	private TableView<Airport> tabla;
	@FXML
	private TableColumn<Airport, String> c1, c2;
	@FXML
	private LineChart<LocalDate,Integer> vuelosPorDia;
	@FXML
	private BarChart<String, Number> retrasoAeropuertos;
	@FXML
	private PieChart vuelosAeropuertos;
	@FXML
	private ScatterChart<String,Number> compania;
	@FXML
	private Label numeros, numeros1, numeros2;
	@FXML
	private DatePicker fechaInicio, fechaFin;
	@FXML
	private CheckBox conFechas;
	@FXML
	private Tab tabSalidas;
	@FXML
	private Tab tabLlegadas;
	@FXML
	private Tab tabGraficas;
	@FXML
	private Tab tabRetraso;
	@FXML
	private Tab tabDias;
	@FXML
	private Tab tabAeropuertos;
	
	LocalDate fecha1, fecha2;
		
	public void graficoVuelosPorDia() {
		final CategoryAxis ejeX = new CategoryAxis();
		ejeX.setLabel("Fechas");
		final NumberAxis ejeY = new NumberAxis();
		ejeY.setLabel("Vuelos");
		vuelosPorDia.setTitle("Vuelos Internacionales, nacionales y vuelos totales por dia");
		Data datos = Data.getInstance();
		ObservableList<Airport> listaAeropuertos = FXCollections.observableList(datos.getAirportList());
		List<LocalDate> listaFechas = datos.getDates();
		int vuelosNacionales=0;
		int vuelosInternacionales=0;
		XYChart.Series nacionales = new XYChart.Series();
		XYChart.Series internacionales = new XYChart.Series();
		XYChart.Series totales = new XYChart.Series();
		totales.setName("Vuelos Totales");
		internacionales.setName("Vuelos Internacionales");
		nacionales.setName("Vuelos Nacionales");
		for(int j = 0; j<listaFechas.size(); j++){
			vuelosNacionales=0;
			vuelosInternacionales=0;
			for(int i=0;i<listaAeropuertos.size();i++){
				if(datos.getAirportFlights(listaAeropuertos.get(i),listaFechas.get(j))!=null){
				vuelosNacionales+=datos.getAirportFlights(listaAeropuertos.get(i),listaFechas.get(j)).getNumNationalFlights();
				vuelosInternacionales+=datos.getAirportFlights(listaAeropuertos.get(i),listaFechas.get(j)).getNumInternationalFlights();
				}
			}
			nacionales.getData().add(new XYChart.Data(listaFechas.get(j).toString(), vuelosNacionales));
			internacionales.getData().add(new XYChart.Data(listaFechas.get(j).toString(), vuelosInternacionales));
			totales.getData().add(new XYChart.Data(listaFechas.get(j).toString(),vuelosInternacionales+vuelosNacionales));
		}
		vuelosPorDia.getData().addAll(nacionales, internacionales, totales);
		 Tooltip mitooltip = new Tooltip("");
		 for (Series<LocalDate,Integer> serie: vuelosPorDia.getData()){
	            for (javafx.scene.chart.XYChart.Data<LocalDate, Integer> item: serie.getData()){
	            	mitooltip = new Tooltip("");
	            	Tooltip.install(item.getNode(),mitooltip);
	            	mitooltip.setText(item.getYValue()+": Vuelos ");
	            	}
	        }
	}
	public void RetrasoPorAeropuerto(int numero){
		retrasoAeropuertos.getData().clear();
		final CategoryAxis ejeX = new CategoryAxis();
		ejeX.setLabel("Aeropuertos");
		DecimalFormat df = new DecimalFormat("#.##");
		final NumberAxis ejeY = new NumberAxis();
		ejeY.setLabel("Retraso");
		retrasoAeropuertos.setTitle("Retraso medio de los aeropuertos");
		XYChart.Series delay = new XYChart.Series();
		Data datos = Data.getInstance();
		Map<Double,String> map = new HashMap<Double,String>();
		ObservableList<Airport> listaAeropuertos = FXCollections.observableList(datos.getAirportList());
		double retrasos[]=new double [listaAeropuertos.size()];
		for (int i=0; i<listaAeropuertos.size();i++){
			retrasos[i]=datos.getAirportFlights(listaAeropuertos.get(i)).getDelay();
			map.put(datos.getAirportFlights(listaAeropuertos.get(i)).getDelay(), listaAeropuertos.get(i).getName());
		}
		Arrays.sort(retrasos);
		for(int i=0;i<numero;i++){
			delay.getData().add(new XYChart.Data(map.get(retrasos[retrasos.length-i-1]),retrasos[retrasos.length-i-1]));			
		}
		retrasoAeropuertos.getData().addAll(delay);
		Tooltip mitooltip = new Tooltip("");
		for (Series<String,Number> serie: retrasoAeropuertos.getData()){
	            for (XYChart.Data<String, Number> item: serie.getData()){	
	            	mitooltip = new Tooltip("");
	            	Tooltip.install(item.getNode(),mitooltip);
	   			 	mitooltip.setText(item.getXValue()+": "+df.format(item.getYValue())+" segundos de retraso medio");
	            }
	        }
		}
	public void VuelosPorAeropuerto(int numero){
		vuelosAeropuertos.setTitle("Número de vuelos por aeropuertos");
		ObservableList<PieChart.Data> datosTarta=FXCollections.observableArrayList();
		Data datos = Data.getInstance();
		Map<Integer,String> map = new HashMap<Integer,String>();
		Map<String, Integer> mapNacionales = new HashMap<String, Integer>();
		Map<String, Integer> mapInternacionales = new HashMap<String, Integer>();
		ObservableList<Airport> listaAeropuertos = FXCollections.observableList(datos.getAirportList());
		Integer vuelos[]=new Integer[listaAeropuertos.size()];
		for (int i=0; i<listaAeropuertos.size();i++){
			vuelos[i]=datos.getAirportFlights(listaAeropuertos.get(i)).getNumFlights();
			map.put(datos.getAirportFlights(listaAeropuertos.get(i)).getNumFlights(), listaAeropuertos.get(i).getName());
			mapNacionales.put(listaAeropuertos.get(i).getName(), datos.getAirportFlights(listaAeropuertos.get(i)).getNumNationalFlights());
			mapInternacionales.put(listaAeropuertos.get(i).getName(), datos.getAirportFlights(listaAeropuertos.get(i)).getNumInternationalFlights());
		}
		Arrays.sort(vuelos);
		for (int i=0;i<numero;i++){
			datosTarta.add(new PieChart.Data(map.get(vuelos[vuelos.length-i-1]), vuelos[vuelos.length-i-1]));
			vuelosAeropuertos.setData(datosTarta);
		}
		Tooltip mitooltip = new Tooltip("");
		 for(PieChart.Data data : vuelosAeropuertos.getData()){
			 String name = data.getName();
             int total = mapNacionales.get(name)+mapInternacionales.get(name);
			 mitooltip = new Tooltip("");
			 mitooltip.setText("Vuelos Nacionales: " + mapNacionales.get(name)+"\n"+"Vuelos Internacionales: "+mapInternacionales.get(name)+"\n"+"Vuelos Totales: "+total);
			 Tooltip.install(data.getNode(),mitooltip);
			 }    
	}

	public void rellenaTabla(Airport aeropuerto){
		Data datos = Data.getInstance();
		
		ObservableList<Flight> salidas = FXCollections.observableArrayList(datos.getAirportFlights(aeropuerto).getDepartures().getFlights().values());
		ObservableList<Flight> llegadas = FXCollections.observableArrayList(datos.getAirportFlights(aeropuerto).getArrivals().getFlights().values());
		
		tablaSalidas.setItems(salidas);
		vueloSalidas.setCellValueFactory((e)->new SimpleStringProperty(e.getValue().getFlightNumber()));
		fechaSalidas.setCellValueFactory((e)->new SimpleStringProperty(e.getValue().getDate().toString()));
		horaSalidas.setCellValueFactory(e->new SimpleStringProperty(e.getValue().getTime().toString()));
		retrasoSalidas.setCellValueFactory(e->new SimpleStringProperty(String.valueOf(e.getValue().getDelay())));
		companiaSalidas.setCellValueFactory(e->new SimpleStringProperty(e.getValue().getCompany()));
		tipoSalidas.setCellValueFactory(e->new SimpleStringProperty(e.getValue().getFlightDomain().toString()));
		DestinoSalidas.setCellValueFactory((e)->new SimpleStringProperty(e.getValue().getDestiny()));
		modeloSalidas.setCellValueFactory((e)->new SimpleStringProperty(e.getValue().getPlane()));
		
		tablaLlegadas.setItems(llegadas);
		vueloLlegadas.setCellValueFactory((e)->new SimpleStringProperty(e.getValue().getFlightNumber()));
		fechaLlegadas.setCellValueFactory((e)->new SimpleStringProperty(e.getValue().getDate().toString()));
		horaLlegadas.setCellValueFactory(e->new SimpleStringProperty(e.getValue().getTime().toString()));
		retrasoLlegadas.setCellValueFactory(e->new SimpleStringProperty(String.valueOf(e.getValue().getDelay())));
		companiaLlegadas.setCellValueFactory(e->new SimpleStringProperty(e.getValue().getCompany()));
		tipoLlegadas.setCellValueFactory(e->new SimpleStringProperty(e.getValue().getFlightDomain().toString()));
		origenLlegadas.setCellValueFactory((e)->new SimpleStringProperty(e.getValue().getOrigin()));
		modeloLlegadas.setCellValueFactory((e)->new SimpleStringProperty(e.getValue().getPlane()));
	}
	public void rellenaTabla(Airport aeropuerto, LocalDate fechaInicio, LocalDate fechaFinal){
		Data datos = Data.getInstance();
		
		ObservableList<Flight> salidas = FXCollections.observableArrayList();
		ObservableList<Flight> llegadas = FXCollections.observableArrayList();
		
		for(LocalDate i=fechaInicio;i.isBefore(fechaFinal.plusDays(1));){
		salidas.addAll(datos.getAirportFlights(aeropuerto,i).getDepartures().getFlights().values());
		llegadas.addAll(datos.getAirportFlights(aeropuerto,i).getArrivals().getFlights().values());
		
		tablaSalidas.setItems(salidas);
		vueloSalidas.setCellValueFactory((e)->new SimpleStringProperty(e.getValue().getFlightNumber()));
		fechaSalidas.setCellValueFactory((e)->new SimpleStringProperty(e.getValue().getDate().toString()));
		horaSalidas.setCellValueFactory(e->new SimpleStringProperty(e.getValue().getTime().toString()));
		retrasoSalidas.setCellValueFactory(e->new SimpleStringProperty(String.valueOf(e.getValue().getDelay())));
		companiaSalidas.setCellValueFactory(e->new SimpleStringProperty(e.getValue().getCompany()));
		tipoSalidas.setCellValueFactory(e->new SimpleStringProperty(e.getValue().getFlightDomain().toString()));
		DestinoSalidas.setCellValueFactory((e)->new SimpleStringProperty(e.getValue().getDestiny()));
		modeloSalidas.setCellValueFactory((e)->new SimpleStringProperty(e.getValue().getPlane()));
		
		tablaLlegadas.setItems(llegadas);
		vueloLlegadas.setCellValueFactory((e)->new SimpleStringProperty(e.getValue().getFlightNumber()));
		fechaLlegadas.setCellValueFactory((e)->new SimpleStringProperty(e.getValue().getDate().toString()));
		horaLlegadas.setCellValueFactory(e->new SimpleStringProperty(e.getValue().getTime().toString()));
		retrasoLlegadas.setCellValueFactory(e->new SimpleStringProperty(String.valueOf(e.getValue().getDelay())));
		companiaLlegadas.setCellValueFactory(e->new SimpleStringProperty(e.getValue().getCompany()));
		tipoLlegadas.setCellValueFactory(e->new SimpleStringProperty(e.getValue().getFlightDomain().toString()));
		origenLlegadas.setCellValueFactory((e)->new SimpleStringProperty(e.getValue().getOrigin()));
		modeloLlegadas.setCellValueFactory((e)->new SimpleStringProperty(e.getValue().getPlane()));
		i=i.plusDays(1);
		}
	}
	public void handleBoxFechas(Boolean e, LocalDate fecha1, LocalDate fecha2){
		if(e){
			tabla.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)->rellenaTabla(newValue, fecha1, fecha2));
		} else {
			tabla.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)->rellenaTabla(newValue));
		}
	}
	
    private static boolean isNumeric(String cadena){
    	try {
    		Integer.parseInt(cadena);
    		return true;
    	} catch (NumberFormatException nfe){
    		return false;
    	}
    }
    @FXML
    private void handleAbout() {
		Alert dialogo = new Alert(AlertType.INFORMATION);
		dialogo.initStyle(StageStyle.UNIFIED);
		dialogo.setTitle("Panel de información de vuelos");
		dialogo.setContentText("Autores: Jorge Ibáñez Felipe || Antoni Giménez Rodríguez");
		dialogo.setHeaderText(null);
		dialogo.show();
		}
    @FXML
    private void handleFuente() {
		Alert dialogo = new Alert(AlertType.INFORMATION);
		dialogo.initStyle(StageStyle.UNIFIED);
		dialogo.setTitle("Panel de información de vuelos");
		dialogo.setContentText("Toda la información mostrada en esta aplicación ha sido proporcionada por la libreria 'AENAFlightsData-1.0.jar'");
		dialogo.setHeaderText(null);
		dialogo.show();
		}
	public void initialize(){
		numAeropuertos.setText("6");
		int numero = Integer.parseInt(numAeropuertos.getText());
		Data datos = Data.getInstance();
		ObservableList<Airport> listaAeropuertos = FXCollections.observableList(datos.getAirportList());
		List<LocalDate> listaFechas = datos.getDates();
		fechaInicio.setValue(listaFechas.get(0));
		fechaFin.setValue(listaFechas.get(listaFechas.size()-1));
		RetrasoPorAeropuerto(numero);
		graficoVuelosPorDia();
		VuelosPorAeropuerto(numero);
		tabla.setItems(listaAeropuertos);
		retrasoAeropuertos.setLegendVisible(false);
		numAeropuertos.textProperty().addListener((observable, oldValue, newValue) -> {
		boolean numerico = isNumeric(newValue);
		if ((newValue != null) && (!newValue.equals(""))){
				if (numerico&&Integer.parseInt(newValue)>0&&Integer.parseInt(newValue)<listaAeropuertos.size()+1){
			RetrasoPorAeropuerto(Integer.parseInt(newValue));
			VuelosPorAeropuerto(Integer.parseInt(newValue));
			}
			 else {
				Alert alerta = new Alert(AlertType.ERROR);
				alerta.initStyle(StageStyle.UNIFIED);
				
				alerta.setContentText("Por favor, introduzca un numero entero comprendido entre 1 y "+listaAeropuertos.size());
				alerta.setHeaderText("Error número aeropuertos");
				alerta.show();
			 }
			}
		});
		
		
		c1.setCellValueFactory((e)-> new SimpleStringProperty(e.getValue().getName()));
		c2.setCellValueFactory((e)-> new SimpleStringProperty(e.getValue().getCode()));
	
		tabla.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)->rellenaTabla(newValue));
		fecha1=fechaInicio.getValue();
		fecha2=fechaFin.getValue();
		fechaInicio.setOnAction(new EventHandler() {
		     public void handle(Event t) {
		         fecha1 = fechaInicio.getValue();
		         if(conFechas.isSelected()){
		        	 rellenaTabla(tabla.getSelectionModel().selectedItemProperty().get(), fecha1, fecha2);
		         }
		     }
		 });
		fechaFin.setOnAction(new EventHandler() {
		     public void handle(Event t) {
		         fecha2 = fechaFin.getValue();
		         if(conFechas.isSelected()){
		        	 rellenaTabla(tabla.getSelectionModel().selectedItemProperty().get(), fecha1, fecha2);
		         }
		       }
		 });
		conFechas.setOnAction((event)->{
			if(conFechas.isSelected()){
				rellenaTabla( tabla.getSelectionModel().selectedItemProperty().get(), fecha1, fecha2);
			} else{
				rellenaTabla(tabla.getSelectionModel().selectedItemProperty().get());
			}
			
		});
		}
	
	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
