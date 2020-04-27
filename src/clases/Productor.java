package clases;


public class Productor extends Thread{

    Buffer almacen;
    int cuenta;

    public Productor(Buffer almacen){
        this.almacen = almacen;
        cuenta = 0;
    }

    @Override
    public void run(){
        boolean condicion = true;
        while(condicion){
        	/*add product*/
            almacen.agregar();
            cuenta++;
            /* check to the end program*/
            if(almacen.terminarProductores()){
                condicion = false;
            }
        }
        System.out.printf(getName() + " ha finalizado. "
        		+ "Se llevaron a cabo %d operaciones\n", cuenta);
    }

    public int getCuenta(){
        return cuenta;
    }


}
