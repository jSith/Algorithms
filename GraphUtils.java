//Author: Jessica Smith
//Johnson Trotter Source: http://introcs.cs.princeton.edu/java/23recursion/JohnsonTrotter.java.html
//Where I learned the method I used: https://www.youtube.com/watch?v=UCle3Smvh1s
//Warning: runs in O(n!). Do not use for large inputs. 

package unl.cse.graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.GraphMapping;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultGraphMapping;
import org.jgrapht.graph.SimpleGraph;

//import unl.cse.counting.LexicoPermutation;
//import unl.cse.counting.Permutation;

public class GraphUtils {
	
	public static void main (String[] args){
		
		UndirectedGraph<String, DefaultEdge> g1 =
	            new SimpleGraph<String, DefaultEdge>(DefaultEdge.class);

		UndirectedGraph<String, DefaultEdge> g2 =
	            new SimpleGraph<String, DefaultEdge>(DefaultEdge.class);
		
		String v1 = "v1"; 
		String v2 = "v2"; 
		String v3 = "v3"; 
		String v4 = "v4"; 
		String v5 = "v5"; 
		String v6 = "v6"; 
						
		g1.addVertex(v1); 
		g1.addVertex(v2);
		g1.addVertex(v3);
		g1.addVertex(v4);
		g1.addVertex(v5);
		g1.addVertex(v6);
		
		g1.addEdge(v1, v2);
		g1.addEdge(v1, v3);
		g1.addEdge(v2, v3);
		g1.addEdge(v3, v4);
		g1.addEdge(v4, v2);
		g1.addEdge(v5, v1);
		g1.addEdge(v5, v3);
		g1.addEdge(v6, v1);
		g1.addEdge(v6, v2);
		g1.addEdge(v6, v3);
		g1.addEdge(v6, v5);
		
		g2.addVertex(v1); 
		g2.addVertex(v2); 
		g2.addVertex(v3); 
		g2.addVertex(v4); 
		g2.addVertex(v5); 
		g2.addVertex(v6); 
		
		g2.addEdge(v1, v2); 
		g2.addEdge(v1, v3);
		g2.addEdge(v2, v3); 		
		g2.addEdge(v2, v4); 
		g2.addEdge(v2, v6); 
		g2.addEdge(v4, v1); 
		g2.addEdge(v4, v3); 
		g2.addEdge(v4, v6); 
		g2.addEdge(v5, v1); 
		g2.addEdge(v5, v6);
		g2.addEdge(v6, v1); 

		System.out.println(getIsomorphicMap(g1, g2)); 
	} 

	/**
	 * Returns true if and only if the two graphs are isomorphic; false otherwise
	 * @param a
	 * @param b
	 * @return
	 */
	public static boolean isomorphic(UndirectedGraph<String, DefaultEdge> a, UndirectedGraph<String, DefaultEdge> b) {
		return (getIsomorphicMap(a, b) != null);
	}
	
	/**
	 * If the given graphs are isomorphic, returns a map between the vertex sets witnessing the isomorphism.  If 
	 * the given graphs are not isomorphic, returns null.
	 * @param a
	 * @param b
	 * @return
	 */
	public static Map<String, String> getIsomorphicMap(UndirectedGraph<String, DefaultEdge> a, UndirectedGraph<String, DefaultEdge> b) {
	
		if (a.vertexSet().size() != b.vertexSet().size()){
			return null; 
		}
		
		ArrayList<String> aVertices = orderVertices(a); 
		ArrayList<String> bVertices = orderVertices(b); 
		HashMap<String, ArrayList<String>> aMap = convertEdgeSet(a.edgeSet());
		HashMap<String, ArrayList<String>> bMap = convertEdgeSet(b.edgeSet()); 
		
		int[][] aMatrix = convertMatrix(a, aMap);
		int[][] bMatrix = convertMatrix(b, bMap); 
		int size = bMatrix.length; 
		int[][] permutation = perm(size, aMatrix, bMatrix);

		if (permutation == null){
			return null; 
		} 
		
		HashMap<String, String> isoMap = new HashMap<String, String>(); 
		
		int i = 0; 
		for (String s: aVertices){
			for (int j = 0; j < permutation.length; j++){
				if (permutation[i][j] != 0){
					isoMap.put(s, bVertices.get(j)); 
				}
			}
			i++; 
		}
		
		return isoMap; 
		
	}
	
	public static int[][] perm (int n, int[][] firstMatrix, int[][] secondMatrix) {
        int[] p   = new int[n];     // permutation
        int[] pi  = new int[n];     // inverse permutation
        int[] dir = new int[n];     // direction = +1 or -1
        for (int i = 0; i < n; i++) {
            dir[i] = -1;
            p[i]  = i;
            pi[i] = i;
        }
        
        int[][] perm = perm(0, p, pi, dir, firstMatrix, secondMatrix);
        
        return perm; 
    }

    public static int[][] perm(int n, int[] p, int[] pi, int[] dir, int[][] firstMatrix, int[][] secondMatrix) { 
    	
        int[][] matrix1 = generateMatrix(p.length, p); 
    
        if (checkMatrix(matrix1, firstMatrix, secondMatrix)){
        	return matrix1; 
        }
        
        if (n >= p.length) {
    		return null; 
        }
      
        int[][] m3 = perm(n+1, p, pi, dir, firstMatrix, secondMatrix);
        if (m3 != null){
        	return m3; 
        }
        
        for (int i = 0; i <= n-1; i++) {
            int z = p[pi[n] + dir[n]];
            p[pi[n]] = z;
            p[pi[n] + dir[n]] = n;
            pi[z] = pi[n];
            pi[n] = pi[n] + dir[n];  
            int[][] m2 = perm(n+1, p, pi, dir, firstMatrix, secondMatrix); 
            if (m2 != null){
            	return m2; 
            }
        }
        
        dir[n] = -dir[n];
		return null; 

    }
    
	public static ArrayList<String> orderVertices (UndirectedGraph<String, DefaultEdge> map){
		
		ArrayList<String> vertices = new ArrayList<String>(); 
		for (String s: map.vertexSet()){
			vertices.add(s); 
		}
		
		return vertices; 
		
	}
	
	public static int[][] generateMatrix (int size, int[] order){
		
		int[][] ident = new int[size][size]; 
		
		for (int i = 0; i < size; i++){
			int row = order[i]; 
			for (int j = 0; j < size; j++){
				if (j == row){
					ident[i][j] = 1; 
				} else {
					ident[i][j] = 0; 
				}
			}
		}
		return ident; 
		
	}
	
	public static int[][] generateIdentityMatrix (int size){
		
		int[][] ident = new int[size][size]; 
		
		for (int i = 0; i < size; i++){
			for (int j = 0; j < size; j++){
				if (i == j){
					ident[i][j] = 1; 
				} else {
					ident[i][j] = 0; 
				}
			}
		}
		
		return ident; 
		
	}

	//This will be a square matrix representation of a graph. 1 indicates that a mapping exists. 0 indicates that there is no such mapping. 
	//The inner array will represent what column you're in (x, moves across horizontally)
	//The outer array will represent what row you're in (y, moves across vertically)
	public static int[][] convertMatrix (UndirectedGraph<String, DefaultEdge> graph, HashMap<String, ArrayList<String>> map){
		int size = graph.vertexSet().size(); 
		int[][] matrix = new int[size][size]; 
		int x = 0; 
		int y = 0; 
				
		for (String col: graph.vertexSet()){
			for (String row: graph.vertexSet()){
				if (map.containsKey(col)){
					if (map.get(col).contains(row)){
						matrix[y][x] = 1;  
					} else {
						matrix[y][x] = 0; 
					}
					x++; 
				}
			}
			y++; 
			x = 0; 
		}
		
		return matrix; 
		
	}
	
	public static int[][] swapRows (int[][] matrix, int row1, int row2){
		
		int size = matrix.length; 
		int[][] newMatrix = new int[size][size]; 
		
		for (int x = 0; x < size; x++){
			newMatrix[row1][x] = matrix[row2][x]; 
			newMatrix[row2][x] = matrix[row1][x]; 
		}
		
		return newMatrix; 
	}
	
	//For isometric mapping, both matrices will always be the same size
	//We will have already checked that they have the same number of vertices
	public static int[][] product (int[][] first, int[][] second){
		
		int size = first.length; 
		int[][] result = new int[size][size]; 
		int product = 0; 
		
		for (int firstRow = 0; firstRow < size; firstRow++){
			for (int secondColumn = 0; secondColumn < size; secondColumn++){
				int secondRow = 0; 
				for (int firstColumn = 0; firstColumn < size; firstColumn++){
					product += first[firstRow][firstColumn] * second[secondRow][secondColumn]; 
					secondRow++; 
				}
				result[firstRow][secondColumn] = product; 
				product = 0; 
			}
		}
		
		return result; 
		
	}
	
	//This method only works for square matrices
	public static int[][] transpose (int[][] matrix){
		
		int size = matrix.length; 
		int[][] transpose = new int[size][size]; 
		
		for (int y = 0; y < size; y++){
			for (int x = 0; x < size; x++){
				transpose[x][y] = matrix[y][x]; 
			}
		}
		
		return transpose; 
		
	}
	
	public static void printMatrix (int[][] matrix){
		for (int i = 0; i < matrix.length; i++){
			for (int j = 0; j < matrix.length; j++){
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
		} 
		System.out.println();
	}
	
	public static boolean checkMatrix (int[][] matrix, int[][] firstMatrix, int[][] secondMatrix){
		
        int[][] product = product(matrix, secondMatrix); 
		int[][] result = product(product, transpose(matrix));
		if (matrixEquals(result, firstMatrix)){
			return true; 
		} else {
			return false; 
		}
		
	} 
	
	public static boolean matrixEquals (int[][] a, int[][] b){
		
		if (a.length != b.length){
			return false; 
		}
		
		for (int i = 0; i < a.length; i++){
			for (int j = 0; j < a.length; j++){
				if (a[i].length != b[i].length){
					return false; 
				} else {
					if (a[i][j] != b[i][j]){
						return false; 
					}
				}
			}
		}
		
		return true; 
	}
	
	
	public static String[] convertEdge (DefaultEdge e){

		String edge[] = e.toString().split(": "); 
		
		for (int i = 0; i < edge.length; i++){
			edge[i] = edge[i].replace(")", ""); 
			edge[i] = edge[i].replace("(", ""); 
			edge[i] = edge[i].replace(" ", ""); 
		}
		
		return edge; 
	}
	
	public static HashMap<String, ArrayList<String>> convertEdgeSet (Set<DefaultEdge> s){
		
		HashMap<String, ArrayList<String>> set = new HashMap<String,ArrayList<String>>(); 
		
		for (DefaultEdge e: s){
			ArrayList<String> mappedValuesLeft = new ArrayList<String>(); 			
			ArrayList<String> mappedValuesRight = new ArrayList<String>(); 
			String[] edge = convertEdge(e); 
			String key = edge[0];
			String value = edge[1]; 
		   			
			if (set.containsKey(key)){
				mappedValuesLeft = set.get(key); 
				set.remove(key);
			} 
			
			if (set.containsKey(value)){
				mappedValuesRight = set.get(value); 
				set.remove(value); 
			}
			mappedValuesLeft.add(value); 
			mappedValuesRight.add(key); 
			set.put(key, mappedValuesLeft);
			set.put(value, mappedValuesRight); 
		}
		
		return set; 
	}
	
	
	
	public static boolean equal(UndirectedGraph<String, DefaultEdge> a, UndirectedGraph<String, DefaultEdge> b) {
		
		if(!a.vertexSet().equals(b.vertexSet())) 
			return false;

		if(a.edgeSet().size() != b.edgeSet().size()) 
			return false;
		
		for(DefaultEdge e : a.edgeSet()) {
			if(b.getEdge(a.getEdgeSource(e), a.getEdgeTarget(e)) == null)
				return false;
		}

		return true;
	}
}
