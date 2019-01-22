/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.una.geneticAlgorithm;

import java.util.Random;

/**
 *
 * @author konecta
 */
public class GeneticAlgorithm {
    
    static int filas=6;
    static  int columnas=5;
    static int Nganadores=3;
    
    static String [][] Poblacion = new String[filas][columnas];
    static String [][] PoblacionTem = new String[filas][columnas];
    static String [] Parejas = new String[filas];
    static String [] Ganadores = new String[Nganadores];
    static  int Ngenes=5;
    static double sumatoria=0;

    public static void IniciarPoblacion(String[][] Poblacion) {
        System.out.println("******************************************************");
        System.out.println("***************Iniciar Poblacion***********************");
        System.out.println("******************************************************");
        String Individuo="";
        Random ri = new Random();
        for (int i = 0;i< filas; i++) {
            Individuo="";
            for (int k = 0; k < Ngenes; k++) {
                Individuo+=ri.nextInt(2)+",";
                 
            }
            Poblacion[i][0]=""+i;
            Poblacion[i][1]=Individuo;
            
        }//for
    }//iniciarPoblación
    
    //Calculo del valor del individuo de acuerdo a sus genes 0 y 1 */
    public static void convert_individuo(String[][] Poblacion) {
        double  valor=0; 
        for (int i = 0; i < filas; i++) {
            valor=0;
            String [] valores = Poblacion[i][1].split(",");
            int indice=0; 
            //Cada valor se multiplica por potencia de 2
            for (int k = valores.length-1; k >= 0; k--) {
                valor=valor + (Double.parseDouble(valores[k])*Math.pow(2, indice));
                indice++;
            }//for interno
         Poblacion[i][2]=""+valor;
        //sumatoria+=valor;
        
        }//for externo
        
    }//convertir_individuo
    
    //La formula que indica la adaptabilidad del indiviuo
    public static double calidad_individuo(String[][] Poblacion) {
        //columna que tiene  el valor del individuo
        double mayor= Double.parseDouble(Poblacion[0][2]);
        double valor=0;
        for (int i = 0; i < filas; i++) {
            //evalua cada uno con la función x elevada al cuadrado
            valor=funcion_fx(Double.parseDouble(Poblacion[i][2]));
            //valor=valor=funcion_Fx2(Double.parseDouble(Poblacion[i][2]));
            //se coloca en la columna 3
            Poblacion[i][3]=""+valor;
            sumatoria+=valor;
            // se busca el mayor valor  de todos este será el más adaptado
            if (mayor<valor) {
                mayor=valor;
            }
        }//for
        System.out.println("***************Mejor Adaptado***********************");
        System.out.println("******************"+ mayor + "********************");
        System.out.println("******************************************************");
        return (mayor);
    }//calidad_individuo
    //Combinación y mutación en otras se comparten los genes del apareamiento
    
    public static void Combinacion_Mutación(String[][] Poblacion, String[][] PoblacionTem) {
        System.out.println("***************Combinacion y Mutación***********************");
        System.out.println("************************************************************");
        System.out.println("************************************************************");
        Random ri = new Random();//aleatorio para el punto de Combinación
        int puntocruce=0;
        String [] IndividuoA;
        String [] IndividuoB;
        String ParejaA="";
        //Se hace solo la mitad porque cada uno tiene una pareja
        for (int i = 0; i < filas/2; i++) {
            IndividuoA=Poblacion[i][1].split(",");
            ParejaA= Parejas[i];//se obtiene la parej del vector de parejas
            String cadAdn="";
            IndividuoB=Poblacion[Integer.parseInt(ParejaA)][1].split(",");
            puntocruce=ri.nextInt(4);//punto de cruce aleatorio
            System.out.println("Punto de cruce ["+puntocruce+"]["+Poblacion[i][0]+"]["+Poblacion[i][1]+"]"+"[Cruzado con] ["+Poblacion[Integer.parseInt(ParejaA)][0]+"]"+"["+Poblacion[Integer.parseInt(ParejaA)][1]+"]");
            //Genes del primer individuo
            for (int t = 0; t < puntocruce; t++) {
                cadAdn+=IndividuoA[t]+",";
            }
            //Genes del segundo individuo
            for (int t = puntocruce; t < IndividuoA.length; t++) {
                cadAdn+=IndividuoB[t]+",";
            }
            System.out.println("Nuevo individuo ["+cadAdn+"]");
            PoblacionTem[i][0]=""+i;
            PoblacionTem[i][1]=cadAdn;
        }//for parejas
        for (int i = 0; i < Parejas.length; i++) {
            Poblacion[i][0]=PoblacionTem[i][0];
            Poblacion[i][1]=PoblacionTem[i][1];
           
        }//se muta gen despues de la combinación
        int  mutado = (Parejas.length/2)+1;
        IndividuoA= Poblacion[mutado][1].split(",");
        System.out.println("*****************************Mutación***********************");
        System.out.println("****Individuo*********************************Resultado******");
        int gen=ri.nextInt(4);//gen aleatorio
        if (IndividuoA[gen].equals("0")) {
            IndividuoA[gen]="1";
        }else{
            IndividuoA[gen]="0";
        }
        //Se arma la cadena de ADN para mutar
        String cadAdn="";
        for (int t = 0; t < IndividuoA.length; t++) {
            cadAdn+=IndividuoA[t]+",";
        }
        //Sytem.out.println("mutado-->"+cadAdn)
        System.out.println("["+Poblacion[mutado][0]+"]"+"["+ Poblacion[mutado][1]+"]"+"Gen mutado"+"["+gen+"] Resultado= > ["+Poblacion[mutado][0]+"]"+"["+cadAdn+"]");
        Poblacion[mutado][1]=cadAdn;// se adiciona el mutado a la población
        
      }//Combinacion_Mutación
      
    //los mejores  individuos puden copiarse sacar otra copia de si mismos
    public static void Copiarse(String[][] Poblacion, String[][] PoblacionTem) {
        System.out.println("*****************************Copiarse***********************");
        int indice =0;
        int t=0;
        //se saca del vector de ganadores
        for (int i = 0; i < Ganadores.length; i++) {
            int ganador=Integer.parseInt(Ganadores[i]);
             PoblacionTem[indice][0]=""+(i+t);//Nombre del individuo
             PoblacionTem[indice+1][0]=""+(i+1+t);//nombre del individuo copiado
             //demas columnas
             // se copia cada una de las columnas de población a población Temporal
             for (int f = 0; f < columnas; f++) {
                  PoblacionTem[indice][f]= Poblacion[ganador][f];
                  PoblacionTem[indice+1][f]= Poblacion[ganador][f];   
            }
            indice+=2;
            t++;
        }
        //Se pasa de la estructura temporal a la Original
        for (int i = 0; i < filas; i++) {
            Poblacion[i][0]= PoblacionTem[i][0];
            Poblacion[i][1]= PoblacionTem[i][1];
        }//for de pasada
    
    }//copiarse
    public static void verGanadores(String[] Ganadores) {
        int gano=0;
        //for para ver los ganadores
        for (int i = 0; i < Ganadores.length; i++) {
            gano=Integer.parseInt(Ganadores[i]);
            System.out.println("["+Poblacion[gano][0]+"] [ "+Poblacion[gano][1]+"] [ "+Poblacion[gano][2]+"] ["+Poblacion[gano][3]+"] [");
            
        }//for
    }//verGanadores
    
    public static void Torneo(String[][] Poblacion) {
        System.out.println("*****************************Copiarse***********************");
        String desempenoA="";
        String ParejaA="";
        String desempenoB="";
        int indP=0;
        //Torneo entre parejas  se realiza hasta la mitad porque cada uno tiene su pareja
        for (int i = 0; i < filas/2; i++) {
            //desempeño de los individuos de acuerdo a la función actual
            desempenoA=Poblacion[i][3];
            ParejaA=Parejas[i];
            desempenoB=Poblacion[Integer.parseInt(ParejaA)][3];
            System.out.println("["+Poblacion[i][0]+"] [ "+Poblacion[i][1]+"] [ "+Poblacion[i][2]+" ] "
                    +" [ "+desempenoA+"] contra ["+Poblacion[Integer.parseInt(ParejaA)][1]+"] "
                            + "["+Poblacion[Integer.parseInt(ParejaA)][2]+"]"+"["+desempenoB+"]");
            // se comparan aqui compiten
            if (Double.parseDouble(desempenoA)>=Double.parseDouble(desempenoB)) {
                Ganadores[indP]=Poblacion[i][0];
            } else {
                Ganadores[indP]=ParejaA;
            }
            indP++;//indice de intercambio
        }//for de parejas
    
    }//Torneo
    //Aqui se seleccionan las tareas, se realiza la rotacion
    //la ultima con la primera  la antepenultima con la segunda.... y así
    public static void Seleccion_Parejas(String[][] Poblacion) {
        System.out.println("**********************************************************");
        System.out.println("*********************Seleccion Parejas********************");
        String aux =Poblacion[1][0];
         for (int i = 0; i < filas; i++) {
            Parejas[(filas-1)-i]=Poblacion[i][0]; 
         }//for 
    }//Seleccion_Parejas
    
    //calcula la probabilidad de adptabilidad de cada individuo 
    
    public static void adaptabilidad(String[][] Poblacion, double sumatoria2) {
        System.out.println("**********************************************************");
        System.out.println("*********************Adaptabilidad************************");
        for (int i = 0; i < Parejas.length; i++) {
            Poblacion[i][4]=""+(Double.parseDouble(Poblacion[i][3])/sumatoria2); 
            
        }//for
    }//adaptabilidad
    
    //Metodo que muestra la poblacion de individuos y sus valores
    
     public static void verPoblacion(String[][] Poblacion, boolean pareja) {
        System.out.println("**********************************************************");
        System.out.println("*********************Poblacion Actual************************");
        String Cadena="";
        //Hasta el numero de individuos filas
         for (int i = 0; i < filas; i++) {
             for (int k = 0; k < columnas; k++) {
                 Cadena+="["+Poblacion[i][k]+"]";
             }
             //si se visualiza con pareja o no
             if (pareja) {
                 Cadena+="Pareja"+Parejas[i]+"\n";
             } else {
                 Cadena+=""+"\n";
             }
         }//for
         System.out.println(Cadena);
     
     }//verPoblacion
    public static double funcion_fx(double x) {
        return (x*x); 
    }
     public static double funcion_fx2(double x) {
        return (x*x*x); 
    }
     
    // Metodo principal
    public static void main(String[] args) {
        IniciarPoblacion(Poblacion);
        double adaptados=0;
        while (adaptados<961) {
            convert_individuo(Poblacion);
            adaptados=calidad_individuo(Poblacion);
            adaptabilidad(Poblacion, sumatoria);
            verPoblacion(Poblacion, true);
            Seleccion_Parejas(Poblacion);
            Torneo(Poblacion);
            verGanadores(Ganadores);
            Copiarse(Poblacion, PoblacionTem);
            verPoblacion(PoblacionTem, true);
            Seleccion_Parejas(Poblacion);
            Combinacion_Mutación(Poblacion, PoblacionTem);
            
        }
        adaptados=calidad_individuo(Poblacion);
        verPoblacion(Poblacion, true);
    }
}    

