/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.perkolation;

public class Perkolation {
	
	public static void main (String[] args) {
		
		System.out.println("p =");
		float p = Lire.f();
		System.out.println("nb lignes");
		int l = Lire.i();
		System.out.println("nb colonne");
		int c = Lire.i();
		
		int[][] Grille = new int[l][c];
                Grille = perkolation(p,l,c);
                affichePerkolation(Grille);
		
                
                boolean[][] casesVisitees = new boolean[l][c];
		int i=0,j=0,m,n;
                boolean v;
                
                v = eltInfiniBIS(Grille, casesVisitees,l,c);
                
                
                if(v) {
                    System.out.println("Es gibt eine undendliche zusammenhangende komponente");
                }
                else { System.out.println("keine undendliche zusammenhangende komponente gefunden :( "); } 
                // fin de l'exemple 
                
                //statistics(p,l,c);
}
 
    
	

	
	public static int[][] perkolation(float p, int l, int c) {
		int i,j;
                int[][] G = new int[l][c];
                for(i=0;i<l;i++) {       //apparition aléatoires des bords 
			for(j=0;j<c;j++) {
				if(G[i][j]==0) {
					if(Math.random() <= p){
						G[i][j] = 1; 
					} else {
						G[i][j] = 0; 
					}
				}
			}
		}
                
		for(i=1;i<l;i=i+2){       // les zones inacessibles "les carrés" 
			for(j=1;j<c;j=j+2) {
				G[i][j]= 0;
			}
		} 
		
		for(i=0;i<l;i+=2) {     // les points (on les rend "accessibles")
			for(j=0;j<c;j+=2) {
				G[i][j] = 1;
			}
		}

                return(G);
		
}

        public static void affichePerkolation(int[][] G){
            int i=0,j=0;
            for(i=0;i<G.length;i++){
		System.out.println(" ");
		for(j=0;j<G[0].length;j++) {
			System.out.print(G[i][j] + "  ");
		}
	}
}

        // renvoie true si chemin de gauche à droite trouvé, false sinon
        public static boolean eltInfini(int[][] G, boolean[][] cVisitees,int l, int c, int i, int j) {            
            if(i<0 || j<0 || i>=l || j>=c) {
                return false;
            }
            else if( G[i][j] == 0 || cVisitees[i][j] == true) {
                return false; 
            }                                                   // le elif evite IOException
            
            if(j==c-1) {
                return true;
            }
            
            cVisitees[i][j] = true;
            
            return eltInfini(G,cVisitees,l,c,i,j+1) || eltInfini(G,cVisitees,l,c,i+1,j) || eltInfini(G,cVisitees,l,c,i-1,j) || eltInfini(G,cVisitees,l,c,i,j-1);
          
        }
        
        public static boolean eltInfiniBIS(int[][] G, boolean[][] cVisitees, int l, int c) {
            boolean v = false;
            for(int ligne=0;ligne<l;ligne++) {   //"on essaie de partir de chaque case de la premiere ligne"
                    v = eltInfini(G,cVisitees,l,c,ligne,0);
                    if (v==true) {
                        break; }

                    }
            return (v);
        } 
        
        //stats kritisches wert : avec un pas ??? 
        /*public void statsKW() {
            System.out.println("grosse des Gitter fur das studium");
            System.out.println("Reihen :"); // lignes
            int l = Lire.i();
            System.out.println("Spalten :"); 
            int c = Lire.i();
            System.out.println("prazision der Kritische wert (zb : 0.01) :");
            float epsilon = Lire.f();
            int[][] git = new int[l][c];
            
            // calculs :
            float pc = -1;
            boolean v;
            boolean[][] cV = new boolean[l][c];
            while() {
                git = perkolation(pc,l,c);
                v = eltInfini(git,cV,l,c,int i = 0,int j =0);
                // ici peut etre reajouter une fonction eltInfiniBIS qui contient les departs de chacune des cases 
                //de la premiere colonne en plus de eltInfini pour ne pas les reecrire a chaque fois 
            }
           
            
        } */

}

