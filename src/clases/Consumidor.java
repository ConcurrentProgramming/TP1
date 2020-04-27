package clases;

public class Consumidor extends Thread {

    Buffer almacen;
    int cuenta;

    public Consumidor(Buffer almacen){
        this.almacen = almacen;
        cuenta = 0;
    }

    @Override
    public void run(){
        boolean condicion = true;
        while(condicion){
        	/* get product */
            almacen.sacar();
            cuenta++;
            /* check to the end program*/
            if(almacen.terminarConsumidores()){
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
