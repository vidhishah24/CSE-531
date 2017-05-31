import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

public final class LCS_Vidhi_Shah_vidhijat {
public static char[] LCS(char input2[],char input1[],int n,int m){
	int opt[][]=new int [n+1][m+1];     // Matrix that stores the LCS at each interval
	String pi[][]=new String[n+1][m+1];   //Stores the path to trace back LCS
	for (int j=0;j<=m;j++){
		opt[0][j]=0;
	}
	for(int i=0;i<n;i++){
		opt[i][0]=0;
		for(int j=0;j<m;j++){
			if(input1[i]==input2[j]){      //If there is a match we increase by 1 the element from its diagonal element
				opt[i+1][j+1]=opt[i][j]+1;
				pi[i+1][j+1]="diag";
				
			}
			else if(opt[i+1][j]>=opt[i][j+1]){ //else the LCS is the max of difference between the element to the right and at the top
				opt[i+1][j+1]=opt[i+1][j];
				pi[i+1][j+1]="left";
			}else
			{
				opt[i+1][j+1]=opt[i][j+1];
				pi[i+1][j+1]="up";
			}
		}
	}
	char s[]=new char[opt[n][m]];
	int i=n,j=m;
	int c=0;
	while(i>0 && j>0){
		if(pi[i][j]=="diag"){
			s[c]=input1[i-1];
			c++;
			i=i-1;
			j=j-1;	
		}
		
		else if(pi[i][j]=="up"){
			i=i-1;
		}else
		{
			j=j-1;
		}
	}
	return s;
}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("input6.txt")); //Reads the Input file 
		Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("output.txt")));
		 
		String b=br.readLine(); //Reads the first String from the input file
		String a=br.readLine(); //Reads the second  string from input file
		int m=a.length();
		int n=b.length();	//Calculates the length of the two string
		char input2[]=a.toCharArray();
		char input1[]=b.toCharArray();
		char s[]=LCS(input2,input1,n,m); //Function call to LCS
		out.write(s.length+"\r\n");  //Outputs the length of LCS
		
		for(int k=s.length-1;k>=0;k--){
		out.write(s[k]);}    //Prints the LCS
		out.close();
	}

}
