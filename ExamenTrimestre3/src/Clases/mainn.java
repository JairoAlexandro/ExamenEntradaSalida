package Clases;

public class mainn {

	public static void main(String[] args) {
		if(args.length != 2) {
			System.exit(1);
		}
		
		String ventas = args[0];
		String inventario = args[1];
		TiendaImpl tienda = new TiendaImpl(0, "", "", "", 0, 0, 0, 0, "");
		tienda.leerVentas(ventas);
		tienda.escribirInventario(inventario);
		tienda.leerInventario(inventario);
		tienda.contador();
		
		


	}

}
