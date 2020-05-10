package clases;


public class Productor extends Thread{

    private Buffer almacen;
    private ArrayList<Producto> lista_producto;
    int cuenta;

    public Productor(Buffer almacen){
        this.almacen = almacen;
        cuenta = 0;
        lista_producto = new ArrayList<Producto>();
    }

    @Override
    public void run(){
        while(terminar()){
        	/*create product*/
        	Producto producto;
        	producto = Producto.crearProducto(Thread.currentThread().getName());
            almacen.agregar(producto);
        }
    }

    public int getCuenta(){
        return cuenta;
    }
    
    public Producto getProductoProducido(int ir) {
    	if(ir <= lista_producto.size()) {
    	}
    }
    
    private void terminar() {
    	return almacen.terminarProductores();	
    }

}
