
public class Producto {

	private String etiqueta;
	private static int id_producto;
	
	public Producto(String etiqueta) {
		this.etiqueta = etiqueta + (++id_producto);
	}

	public void setEtiqueta(String nueva_etiqueta) {
		this.etiqueta = nueva_etiqueta + (++id_producto);
	}
	
	public String getEtiqueta() {
		return etiqueta;
	}
	
	public synchronized Producto crearProducto(String etiqueta) {
		/* se crea un producto*/
		try {
			Thread.sleep(Math.random()*100);
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		return new Producto(etiqueta);
	}
	
}
