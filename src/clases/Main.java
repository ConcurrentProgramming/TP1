package clases;

import java.io.*;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Main {

    /**
     * Launcher del programa, crea los hilos consumidores, productores y el buffer.
     * Luego de lanzar los mismos, se encarga de ir actualizando el log.txt hasta que todos los hilos finalizen.
     * @param args
     */
    public static void main(String[] args){

        final int cantidad_consumidores = 3;
        final int cantidad_productores = 3;

        Buffer almacen = new Buffer();
        Thread[] hilo_p = new Thread[cantidad_productores];
        Thread[] hilo_c = new Thread[cantidad_consumidores];

        /* creating the producer*/
        for(int i=0; i<cantidad_productores; i++){
            hilo_p[i] = new Productor(almacen);
            hilo_p[i].setName("Productor: " + (i+1));
        }
        
        /* creating the customer*/
        for(int i=0; i<cantidad_consumidores; i++){
            hilo_c[i] = new Consumidor(almacen);
            hilo_c[i].setName("consumidor: " + (i+1));
        }

        try {
        	/* creating the file*/
            FileOutputStream file = new FileOutputStream("log.txt");
            PrintStream pw = new PrintStream(file);
            
            /* Print previous information */
            pw.println(" --Informacion previa [" + new Date() + "]--");
            
            for(int i=0; i<cantidad_consumidores; i++){
                pw.println("Estado de " + hilo_c[i].getName() + " : " + hilo_c[i].getState());
            }
            
            pw.println("Elementos en el buffer: " + almacen.getNumero_elementos());
            pw.println("**--0--**");

            /* run thread */
            for(int i=0; i<cantidad_productores; i++){
                hilo_p[i].start();
            }
            
            for(int i=0; i<cantidad_consumidores; i++){
                hilo_c[i].start();
            }

            boolean condicion = true;
            int impresiones = 1;
            while(condicion){
            	/* waiting two second */
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                 
                /* get and write States */
                pw.println(new Date());
                for(int i=0; i<cantidad_consumidores; i++){
                    pw.println("Estado " + hilo_c[i].getName() + ": " + hilo_c[i].getState());
                }
                pw.println("E en buffer: " + almacen.getNumero_elementos());
                pw.println("**--"+impresiones+"--**");
                impresiones++;
                /* check the end of the program */
                if(fin_programa(hilo_p, hilo_c)){
                    condicion = false;
                }

            }
            pw.println("FIN");

        } catch (IOException e) {
            e.printStackTrace();
        }

        /* print completion information */
        System.out.printf("*****************************************************************************\n");
        System.out.printf("El programa a concluido. Se produjeron %d paquetes incluidos los perdidos.\n", almacen.getContador_total());
        System.out.printf("Se perdieron %d paquetes porque el buffer estaba lleno.\n", almacen.getPer_lleno());
        System.out.printf("Se intentaron retirar %d paquetes del buffer vacio.\n", almacen.getPer_vacio());
        System.out.printf("*****************************************************************************\n");



    }

    /**
     * Metodo que verifica si todos los hilos pasados como parametros terminaron.
     * @param hilos_p
     * @param hilos_c
     * @return true si todos los hilos finalizaron.
     */
    public static boolean fin_programa(Thread[] hilos_p, Thread[] hilos_c){

        int i, c, p;
        c = 0;
        p = 0;

        for(i =0; i<hilos_c.length; i++){
            if(!hilos_c[i].isAlive()){
                c++;
            }
        }
        for(i=0; i<hilos_p.length; i++){
            if(!hilos_p[i].isAlive()){
                p++;
            }
        }

        if(c == hilos_c.length && p == hilos_p.length){
            return true;
        } else{
            return false;
        }

    }

}
