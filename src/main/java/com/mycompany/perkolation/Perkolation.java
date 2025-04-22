/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.perkolation;

public class Perkolation {
	
	public static void main (String[] args) {
                System.out.println("Hallo :) Lass uns Perkolation studieren. Aber was ist Perkolation ??? Hier ist ein Beispiel. ");
		System.out.println("p =");
		float p = Lire.f();
		System.out.println("Zahl von Reihen");
		int l = Lire.i();
		System.out.println("Zahl von Spalten");
		int c = Lire.i();
		
		int[][] Grille = new int[l][c];
                Grille = perkolation(p,l,c);
                affichePerkolation(Grille);
		
                
                boolean[][] casesVisitees = new boolean[l][c];
		int i=0,j=0,m,n;
                boolean v;
                
                v = eltInfiniBIS(Grille, casesVisitees,l,c);
                
                
                if(v) {
                    System.out.println("\n \n Es gibt eine unendliche zusammenhangende komponente");
                }
                else { System.out.println("\n \n keine unendliche zusammenhangende komponente gefunden :( "); } 
                System.out.println("\n \n Ende des Beispiels. Jetzt Berechnung !! "); 
                
                statsKW();
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
                
		                
                for(i=1;i<l;i=i+2){       // les zones inacessibles "le centre des carrés"
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
            if(i<0 || j<0 || i>=l || j>=c) {                    // si on est sortis de la grille
                return false;
            }
            else if( G[i][j] == 0 || cVisitees[i][j] == true) {  // si on a deja visité cette case ou si elle est inaccessible (0)
                return false; 
            }                                                   // le elif evite IOException
            
            if(j==c-1) {          //signifie qu'on est arrives de l'autre cote 
                return true;
            }
            
            cVisitees[i][j] = true;
            
            return eltInfini(G,cVisitees,l,c,i,j+1) || eltInfini(G,cVisitees,l,c,i+1,j) || eltInfini(G,cVisitees,l,c,i-1,j) || eltInfini(G,cVisitees,l,c,i,j-1);
          
        }
        
        public static boolean eltInfiniBIS(int[][] G, boolean[][] cVisitees, int l, int c) {
            boolean v = false;
            for(int ligne=0;ligne<l;ligne++) {   //"on essaie de partir de chaque case de la premiere ligne", car element infini ne part que de (i=0;j=0)
                    v = eltInfini(G,cVisitees,l,c,ligne,0);
                    if (v==true) {
                        break; }

                    }
            return (v);
        } 
        
        //stats kritisches wert 
        // idee : creer un tableau contenant toutes les valeurs de p selon le pas indiqué et faire une dichotomie 
        public static void statsKW() {
            System.out.println("Lass uns das Kritisches wert schatzen. Es soll nach unseres Dokument 0.5 sein.");
            System.out.println("grosse des Gitter fur das studium");
            System.out.println("Reihen :"); // lignes
            int l = Lire.i();
            System.out.println("Spalten :"); 
            int c = Lire.i();
            System.out.println("prazision der Kritische wert (zb : 0.01) :");
            float epsilon = Lire.f();
            int t = (Math.round(1/epsilon) + 1);
            float[] pw = new float[t];
            pw[0] = 0;
            
            int i=1; 
            // remplissage pw / pb que j'ai pas reussi à resoudre : c'est pas des valeurs arrondies à epsilon près alors que epsilon est arrondi 
            while(i<t) {
                pw[i]=pw[i-1]+epsilon;
                i++;   
            }
            
            //affichage tableau pw : c'est le tableau contenant toutes les probabilités qui vont être testees
            /*for(i=0;i<t;i++) {
                System.out.println(pw[i]); }
            */
            
            //******************************************************************************************
            
             // ok --> dichotomie 
             // premier p et dernier d et milieu M (correspond aux valeurs de pw) 
             // evaluation de la verite de eltInfiniBIS pour p = pw[M] -
             // si vrai : dernier = M-1 et M= (p+d)/2
             // si faux : premier = M+1 et M = (p+d)/2
             // une fois que d=p on a la plus petite valeur de p qui annonce qu'il y a un elt infini
             // la probabilité correspondante est recupere dans pw 
             
            // ****************************************************************************************
             
             int p=0;
             int d=t-1;
             int M=(p+d)/2;
             
             int[][] git = new int[l][c];
             boolean[][] cV;
             boolean v; 
             
             while((d-p)>0) {
                 cV = new boolean[l][c];
                 git = perkolation(pw[M],l,c);
                 v = eltInfiniBIS(git,cV,l,c);
                 if(v) {
                     d = M-1;
                     M=(p+d)/2;
                 }
                 else { 
                     p=M+1;
                     M=(p+d)/2;
                 }
             }
             System.out.println("schatzung des kritisches wertes : " + pw[M]);
             
            
            
        } 

}

