package Clases;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TiendaImpl {
	
	public int iD;
	public String fecha_y_hora_de_venta;
	public String componente;
	public String modelo;
	public int cantidad_vendida;
	public double precio_unitario;
	public double precio_total_de_venta;
	public int Cantidad_en_Stock;
	public String proveedor;
	private String ERROR = "C:\\Users\\DAW MAÑANA\\Desktop\\work-examen\\ExamenTrimestre3\\documentos\\errores.log";
	static boolean hayError = false;
	static Integer ContadorBien = 0;
	
	//Primer fichero
	//Generar un archivo ventas.dat para almacenar las ventas
	//Formato: IDFecha_y_hora_de_ventaComponenteModeloCantidad_vendidaPrecio_unitarioPrecio_total_de_venta
	//Ejemplo: 12024-04-30 10:30:49Placa baseASUS ROG Strix Z590-E Gaming43001200
	
	//Segundo fichero
	//Formato: Componente;Modelo;Cantidad_en_stock;Precio_unitario;Proveedor
	
	//Tercer fichero
	//Generar errores.log
	//Hacer validaciones
	//NO SE ACTUALIZA INVENTARIO SI NO CUMPLE VALIDACION
	//Validacion inventario (Cantidad_en_stock es 0 o negativo)
	public TiendaImpl(int iD, String fecha_y_hora_de_venta, String componente, String modelo, int cantidad_vendida,
			double precio_unitario, double precio_total_de_venta, int Cantidad_en_Stock, String proveedor) {
		this.iD = iD;
		this.fecha_y_hora_de_venta = fecha_y_hora_de_venta;
		this.componente = componente;
		this.modelo = modelo;
		this.cantidad_vendida = cantidad_vendida;
		this.precio_unitario = precio_unitario;
		this.precio_total_de_venta = precio_total_de_venta;
		this.Cantidad_en_Stock = Cantidad_en_Stock;
		this.proveedor = proveedor;
	}

	public int getiD() {
		return iD;
	}

	public void setiD(int iD) {
		this.iD = iD;
	}

	public String getFecha_y_hora_de_venta() {
		return fecha_y_hora_de_venta;
	}

	public void setFecha_y_hora_de_venta(String fecha_y_hora_de_venta) {
		this.fecha_y_hora_de_venta = validarFechayHora(fecha_y_hora_de_venta);
	}

	public String getComponente() {
		return componente;
	}

	public void setComponente(String componente) {
		this.componente = validarComponente(componente);
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = validarModelo(modelo);
	}

	public int getCantidad_vendida() {
		return cantidad_vendida;
	}

	public void setCantidad_vendida(int cantidad_vendida) {
		this.cantidad_vendida = validarCantidadVendida(cantidad_vendida);
	}

	public double getPrecio_unitario() {
		return precio_unitario;
	}

	public void setPrecio_unitario(double precio_unitario) {
		this.precio_unitario = validarPrecioUnitario(precio_unitario);
	}

	public double getPrecio_total_de_venta() {
		return precio_total_de_venta;
	}

	public void setPrecio_total_de_venta(double precio_total_de_venta) {
		this.precio_total_de_venta = validarPrecioTotalVentas(precio_total_de_venta);
	}
	
	public int getCantidad_en_Stock() {
		return Cantidad_en_Stock - this.cantidad_vendida;
	}

	public void setCantidad_en_Stock(int cantidad_en_Stock) {
		Cantidad_en_Stock = cantidad_en_Stock;
	}
	
	public String getProveedor() {
		return proveedor;
	}

	public void setProveedor(String proveedor) {
		this.proveedor = proveedor;
	}
	//Validaciones de errores

	public String validarFechayHora(String fecha) {
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime ldt = LocalDateTime.parse(fecha, dtf);
		String comprobacion = ldt.toString();
		String bien = comprobacion.replace("T", " ");
		if(fecha.equalsIgnoreCase(bien)) {
			return fecha;
		}else {
			erroresLog("Fecha", fecha);
			return "";
		}
		
	}
	
	public String validarComponente(String componente) {
		if(componente != null) {
			return componente;
		}else {
			erroresLog("Componente", componente);
			return "";
		}
	}
	
	public String validarModelo(String modelo) {
		if(modelo != null && modelo.length()>0 && modelo.length()<50) {
			return modelo;
		}else {
			erroresLog("Modelo", modelo);
			return "";
		}
	}
	public int validarCantidadVendida(int cantidadVendida) {
		Integer can = cantidadVendida;
		if(can != null && cantidadVendida > 0) {
			return cantidadVendida;
		}else {
			erroresLogInt("Cantidad vendida", cantidadVendida);
			return 0;
		}
	}
	public double validarPrecioUnitario(double precioUnitario) {
		Double pre = precioUnitario;
		if(pre != null && precioUnitario >= 0.01) {
			return precioUnitario;
		}else {
			erroresLogDouble("Precio Unitario", precioUnitario);
			return 0.0;
		}
	}
	public double validarPrecioTotalVentas(double precioTotalVentas) {
		Double pre = precioTotalVentas;
		if(pre != null && precioTotalVentas >= 0.01) {
			return precioTotalVentas;
		}else {
			erroresLogDouble("Precio total de ventas", precioTotalVentas);
			return 0.0;
		}
	}
	//Ejercicio 1
	public void leerVentas(String url) {
		try{
			DataInputStream di = new DataInputStream(new FileInputStream(url));
				
			System.out.println(di.available());
			this.setiD(di.readInt());
			this.setFecha_y_hora_de_venta(di.readUTF());
			this.setComponente(di.readUTF());
			this.setModelo(di.readUTF());
			this.setCantidad_vendida(di.readInt());
			this.setPrecio_unitario(di.readDouble());
			this.setPrecio_total_de_venta(di.readDouble());
			
			di.close();
			
				
			
			throw new ErrorFicheroNoEncontrado("Finalizado");
		} catch (ErrorFicheroNoEncontrado e) {
			System.out.println(e.getMessage());
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	//Ejercicio Inventario
	
	public void escribirInventario(String url){
		if(hayError == false) {
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(url));
				LocalDateTime dt = LocalDateTime.now();
				String fecha = dt.toString();
				bw.write("Ultima actualizacion: " + fecha + "\n");
				bw.write(this.getComponente() + ";" + this.getModelo() + ";" + this.getCantidad_en_Stock()+ ";" + this.getPrecio_unitario() + ";Distribuidora de Hardware S.A.");
				bw.close();
				ContadorBien = 4;
				
				
				throw new ErrorFicheroNoEncontrado("Finalizado");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (ErrorFicheroNoEncontrado e) {
				System.out.println(e.getMessage());
			}catch (IOException e) {
				
				e.printStackTrace();
			}
		}else {
			System.out.println("No se ha subido, hay errores");
		}
		
	}
	
	public void leerInventario(String url) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(url));
			String linea;
			String[] datos;
			int datosSegundaLinea = 0;
			while ((linea = br.readLine()) != null) {
				if(datosSegundaLinea == 0) {
					datosSegundaLinea++;
				}else {
					datos = linea.split(";");
					this.setComponente(datos[0]);
					this.setModelo(datos[1]);
					this.setCantidad_en_Stock(Integer.parseInt(datos[2]));
					this.setPrecio_unitario(Double.parseDouble(datos[3]));
					this.setProveedor(datos[4]);
				}
				
			}
			br.close();
			
			
			
			throw new ErrorFicheroNoEncontrado("Finalizado");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (ErrorFicheroNoEncontrado e) {
			System.out.println(e.getMessage());
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void erroresLog(String tipo, String valor) {
		try {
			hayError = true;
			BufferedWriter bw = new BufferedWriter(new FileWriter(this.ERROR,true));
			
			bw.write("ID: " + this.getiD() + "-> Campo: " + tipo + ". Valor: " + valor + "\n");
			
			bw.close();
			
			
			
			throw new ErrorFicheroNoEncontrado("Finalizado");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (ErrorFicheroNoEncontrado e) {
			System.out.println(e.getMessage());
		}catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	public void erroresLogInt(String tipo, int valor) {
		try {
			hayError = true;
			BufferedWriter bw = new BufferedWriter(new FileWriter(this.ERROR,true));
			
			bw.write("ID: " + this.getiD() + "-> Campo: " + tipo + ". Valor: " + valor + "\n");
			
			bw.close();
			
			
			
			throw new ErrorFicheroNoEncontrado("Finalizado");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (ErrorFicheroNoEncontrado e) {
			System.out.println(e.getMessage());
		}catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	public void erroresLogDouble(String tipo, double valor) {
		try {
			hayError = true;
			BufferedWriter bw = new BufferedWriter(new FileWriter(this.ERROR,true));
			
			bw.write("ID: " + this.getiD() + "-> Campo: " + tipo + ". Valor: " + valor + "\n");
			
			bw.close();
			
			
			
			throw new ErrorFicheroNoEncontrado("Finalizado");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (ErrorFicheroNoEncontrado e) {
			System.out.println(e.getMessage());
		}catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	public void contador() {
		System.out.println("Subido correctamente: " + ContadorBien);
		Integer contadorError = 0;
		try {
			BufferedReader br = new BufferedReader(new FileReader(this.ERROR));
			String linea;
			try {
				while((linea = br.readLine()) != null) {
					contadorError++;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Numero errores: " + contadorError);
	}
	
	//Me ha faltado poner que cuando Cantidad en Stock es negativo, no lo he avisado en fichero.log
	
	
	
	
}
