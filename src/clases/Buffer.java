package clases;
 
import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * Un buffer pura perdida, que lleva cuenta de paquetes perdidos e 
 * intentos fallidos de retirar paquetes.
 */
public class Buffer {

    int limite_elementos;
    int numero_elementos;
    int contador_total;
    int per_lleno;
    int per_vacio;
    Random random;
    Semaphore semaphore;
    
    public Buffer(){
        limite_elementos = 25;
        numero_elementos = 0;
        contador_total = 0;
        per_lleno = 0;
        per_vacio = 0;
        random = new Random();
        semaphore =new Semaphore(1);
    }

    public Buffer(int limite, int inicial){
        this.limite_elementos = limite;
        this.numero_elementos = inicial;
        contador_total = 0;
        per_lleno = 0;
        per_vacio = 0;
        random = new Random();
        semaphore =new Semaphore(1);
    }

    /**
     * Luego de verificar que tenga espacio, se agrega un elemento al buffer. De no haber lugar, se descarta el elemento
     * y se incrementa el contador de paquetes perdidos.
     */
    public synchronized void agregar(){
    	/* we check if contador_total is 1000 o more */
        if(contador_total>=1000){
        	return;
        }
        try {
            Thread.sleep(random.nextInt(50));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
       /* we check if there is space for an element in the buffer */ 
       if(numero_elementos < limite_elementos){
           numero_elementos++;
       } else{
           per_lleno++;
       }
       
       contador_total++;
       System.out.printf("Produccion: %d\n", contador_total);
    }

    /*
    public void agregar(){
    	//we check if contador_total is 1000 o more 
        if(contador_total>=1000){
        	return;
        }
        try {
        	semaphore.aqcuire();
            Thread.sleep(random.nextInt(50));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
       // we check if there is space for an element in the buffer  
       if(numero_elementos < limite_elementos){
           numero_elementos++;
       } else{
           per_lleno++;
       }
       
       contador_total++;
       System.out.printf("Produccion: %d\n", contador_total);
       semaphore.release();
    }
 */   
    /**
     * Luego de verificar que no este vacio, se saca un elemento del buffer. De no haber ninguno,
     *  se incrementa el contador de intentos fallados.
     */
    public synchronized void sacar(){
        try {
            Thread.sleep(random.nextInt(50));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        /* we check if it's possible to get an element */
        if(numero_elementos >0){
            numero_elementos--;
        } else{
            per_vacio++;
        }
    }
/*
    public void sacar(){
        try {
        	semaphore.aqcuire();
            Thread.sleep(random.nextInt(50));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(numero_elementos >0){
            numero_elementos--;
        } else{
            per_vacio++;
        }
        semaphore.release();
    }
 */   
    public synchronized int getContador_total(){
        return contador_total;
    }

    public synchronized int getPer_lleno(){
        return per_lleno;
    }

    public int getPer_vacio(){
        return per_vacio;
    }

    public int getNumero_elementos() {
        return numero_elementos;
    }

    /**
     * Metodo que verifica si se alcanzo el limite maximo de produccion de paquetes.
     * @return true si ya se alcanzo el maximo.
     */
    public boolean terminarProductores(){
        if(contador_total >=1000){
            return true;
        } else{
            return false;
        }
    }

    /**
     * Metodo que verifica si se alcanzo el limite maximo de produccion de paquetes y ya no quedan paquetes en el buffer.
     * @return true si ya se alcanzo el maximo y el buffer esta vacio.
     */
    public boolean terminarConsumidores(){
        if(contador_total >=1000 && numero_elementos <=0){
            return true;
        } else{
            return false;
        }
    }

}
