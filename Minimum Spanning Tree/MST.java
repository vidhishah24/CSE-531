import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.NoSuchElementException;

class node {
	int vertex;
	int weight;
}

class Heap {
	static int heapSize = 0;
	static node heap[];				

	public Heap(int size) {						//Size of heap is equal to the number of vertices

		heap = new node[size + 1];
	}

	static HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();

	public static boolean isEmpty() { 			 //checks if the heap is Empty
		return heapSize == 0;
	}

	public boolean isFull() { 					// Checks if the heap is full
		return heapSize == heap.length;
	}

	private static int parent(int i) {			//Returns  parent of a given node of heap
		return i / 2;
	}

	private static int leftChild(int i) { 		// Returns Left child of a given node
		return 2 * i;
	}

	private static int rightChild(int i) {		 // Returns right child of a given node
		return 2 * i + 1;
	}

	public void insert(int vertex, int weight) {     //Inserts the element in heap
		node n = new node();
		n.vertex = vertex;
		n.weight = weight;

		if (isFull())
			throw new NoSuchElementException();
		heapSize = heapSize + 1;
		heap[heapSize] = n;
		map.put(heap[heapSize].vertex, heapSize);

		heapifyUp(heapSize);            //Performs heapify up
	}

	public static int getWeight(int v) {
		int v_index = map.get(v);
		int w = heap[v_index].weight;
		return w;

	}

	public static void decreaseKey(int v, int value) {     //Decrease the vale of a given node
		int v_index = map.get(v);
		heap[v_index].weight = value;
		heapifyUp(v_index);			//Performs heapify up

	}

	private static void heapifyUp(int childInd) {
		while (childInd > 1) {										//While root of the heap is reached
			int parentInd = parent(childInd);
			if (heap[childInd].weight < heap[parentInd].weight) {    //checks if the weight of child node is less than the weight of parent node 
				node tmp = heap[childInd];							//If yes than swap the child and parent node
				heap[childInd] = heap[parentInd];
				heap[parentInd] = tmp;
				map.put(heap[childInd].vertex, childInd);      //Update the values of index of child and parent vertex in map
				map.put(tmp.vertex, parentInd);
				childInd = parentInd;
			} else {
				break;
			}
		}
	}

	public static node extractMin() {

		if (isEmpty())
			throw new NoSuchElementException();
		node keyItem = heap[1];
		heap[1] = heap[heapSize]; //extracts the root of heap
		heapSize = heapSize - 1;   //updates the heap size
		if (heapSize >= 1) {	
			heapifyDown(1);         //perform heapify down
		}
		return keyItem;
	}

	private static void heapifyDown(int ind) {
		int j;

		while (2 * ind <= heapSize) {
			if (2 * ind == heapSize || heap[leftChild(ind)].weight <= heap[rightChild(ind)].weight) {    //checks whether the weight of left child is small or right child
				j = leftChild(ind);

			} else {
				j = rightChild(ind);
			}
			if (heap[j].weight < heap[ind].weight) { //check if the weight of either of child is small than its parent
				node tmp = heap[ind];
				heap[ind] = heap[j];				//swap the child and its parent
				heap[j] = tmp;
				map.put(heap[ind].vertex, ind);	// updates the values in map
				map.put(tmp.vertex, j);
				ind = j;
			} else {
				break;
			}

		}
	}
}

class Vertex_Weight {
	int source;
	int destination;
	int weight;

	Vertex_Weight(int source, int destination, int weight) {
		this.source = source;
		this.destination = destination;
		this.weight = weight;
	}

	Vertex_Weight(int destination, int weight) {
		this.destination = destination;
		this.weight = weight;
	}
}

class Graph5 {
	HashMap<Integer, LinkedList<Vertex_Weight>> m1 = new HashMap<Integer, LinkedList<Vertex_Weight>>();  //Maps the vertex with all of its 
																											//	neighbors along with their corresponding neighbors
	static HashMap<Integer, Integer> MST_edge = new HashMap<Integer, Integer>();
	Heap h;
	node v;
	int x = 0;
	int totalWeight = 0;

	void initialize(int n) {
		for (int i = 1; i <= n; i++) {
			LinkedList<Vertex_Weight> l = new LinkedList<Vertex_Weight>();
			m1.put(i, l);
		}
	}

	public void addEdge(Vertex_Weight vw) {         //Adds all the neighbors of a vertex to its corresponding LinkedList
		LinkedList<Vertex_Weight> source_neighbors = m1.get(vw.source);
		source_neighbors.add(new Vertex_Weight(vw.destination, vw.weight));
		LinkedList<Vertex_Weight> destination_neighbors = m1.get(vw.destination);
		destination_neighbors.add(new Vertex_Weight(vw.source, vw.weight));
	}

	public void Prims(int V) {           //Implementation of Prim's Algorithm 
		int visited[] = new int[V + 1];
		while (!h.isEmpty()) {           //do until there are no elements in the heap
			v = h.extractMin();           // Extracts Minimum Weighted vertex i.e the root of the heap
			totalWeight = totalWeight + v.weight; // Calculates total weight of MST
			visited[v.vertex] = 1;					//Include the minimum vertex in MST visited vertices array
			LinkedList<Vertex_Weight> l1 = m1.get(v.vertex); //It gets all the neighbors of the minimum vertex
			for (Vertex_Weight v1 : l1) {
				int i = v1.destination;
					if (visited[i] == 0) {  //check if the neighbor is already in minimum spanning tree
			
					if (v1.weight < h.getWeight(i)) { //checks if the weight of the edge between the minimum vertex and itself is less than that vertex 
						h.decreaseKey(i, v1.weight);    //If,yes than update the weight of the vertex with the edge that has minimum weight
						MST_edge.put(i, v.vertex);	 //map the vertex with its parent that gives minimum  weight
						}
					}
				}
			}
		}

}

public class MST_Vidhi_Shah_50207090 {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("input.txt")); //Reads the Input file 
		Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("output.txt")));
		String CurrentLine = br.readLine();
		Graph5 g1 = new Graph5();
		String[] tokens = CurrentLine.split(" ");
		int V = Integer.parseInt(tokens[0]);
		g1.initialize(V);
		int E = Integer.parseInt(tokens[1]);

		Heap h = new Heap(V);       //Creates an object of class Heap
		for (int i = 0; i < E; i++) {    // Reads the input line by line and stores the result of vertex 1, vertex 2 and its corresponding weight in u,v,w respectively
			CurrentLine = br.readLine();
			tokens = CurrentLine.split(" ");
			int u = Integer.parseInt(tokens[0]);
			int v = Integer.parseInt(tokens[1]);
			int w = Integer.parseInt(tokens[2]);
			Vertex_Weight X = new Vertex_Weight(u, v, w); // calls the constructor of vertex_weight class
			g1.addEdge(X);

		}
		for (int i = 1; i <= V; i++) {
			h.insert(i, Integer.MAX_VALUE); //Set the weight of all the vertices to infinity
		}

		h.decreaseKey(1, 0); //Set the weight of first vertex to 0

		g1.Prims(V);
		out.write(g1.totalWeight + "\r\n");  //Outputs the total Weight of MST
		for (int i = 2; i <= V; i++) {
			out.write(g1.MST_edge.get(i) + " " + i + "\r\n"); //Outputs all the edges in MST
		}
		out.close();

	}
}
