import java.nio.file.*; //Libraries imported to read file and user input
import java.io.*;
import static java.nio.file.StandardOpenOption.*;
import java.util.*;

//Josahandi Cisneros
//L.Vasquez
//CSC471


public class Test02 {

	public static void main(String[] args) {
	
		Scanner inputUser= new Scanner(System.in); 
		System.out.println( "Enter Name file containing Rules : " ); 
		String fileName;
	
		fileName = inputUser.nextLine();
		Path file = Paths.get(fileName); 
		String s = ""; 		 	//creates a string to read each line from text inputFile
		String ss = "";
		int[][] tD_arr; 		//for dependancy matrix 
		String  T_table ="";  	//To print out contents of Input File
		
		LinkedList obj[]= new LinkedList[10]; //Array of references to a linked list for each state in the NFA to be converted
		String cur = ""; 		//String used to print out new DFA states  
		String EList = "" ;
	
		int yo;
		
	 try 
		 { //Begins to read Text File 
			InputStream input = new BufferedInputStream(Files.newInputStream(file));
			BufferedReader reader = new BufferedReader(new InputStreamReader(input)); 	
			int index = 0 ; 		// To keep track of each line 
			s = reader.readLine();	// 1st line is read as String to get info to construct Adj List(graph)
	
			while( s != null)		//While there is another line to read 
			{  T_table += s + "\n";  //to print out table 
						
			if(s.contains("0"))	//ads possible null variables to Varr
			{
				EList += index;
		
			}
		
			if(s.contains("|")) 
			{
				char ucode = 124;
				//System.out.print( "code for actual shit " + );
				String b = s.replace( ucode, '@' );
				 ye(b.substring(2),index,obj); //yea creates the list of each one starting at index 0
				//System.out.println(dep(b.substring(2), index, depg));
			}
			else 
			{
				
				 ye(s.substring(2),index,obj); 
			
			}
			 index++; 
			 s =reader.readLine();
			 
			//loops again s is affected so loops again. 
		}
			
			System.out.println( "Text File was read as :  " ); 
			System.out.println(T_table); 
			System.out.println( "creating dependency graph"  );
			
			tD_arr = twoDar(index, obj); //object is my list and index is the size of graph
			//step 1 

			EList = step1(EList, obj, tD_arr, index);
			System.out.println("   "  );
			toString(obj);
			System.out.println("   "  );
			EList = AddtoVarr( obj,  EList, tD_arr ); //takes Empty list and ddmatx and ll[] return new list 
		
			System.out.println("  After step1   "  ); 
			System.out.println("   "  );
			obj = clean(obj, index);
			System.out.println("   "  );
			System.out.println(" Replacing all nullable  "  );
			getsall(EList,obj);
			
			
			
	 	} catch(Exception e) 
		 	{ 
		 		System.out.println("Message " + e); 
		 	} 	}



	
static String ye(String b, int index, LinkedList<String>[] obj) //splits each rule into their different versions 
{	
	obj[index]= new LinkedList<String>(); //creates LL at each  index
	
	String[] arrOfStr = b.split("@"); 
	for (String a : arrOfStr) 
    {	
    	obj[index].insert(a); //adds each part of the split string into ArrayList 
    }  

 return obj[index].toString(); 
}



static String dep(int[][] depg, LinkedList<String> list, int index) //this should be completed once after shits have been split using the array 
{
	//int indx = 0;
	String x=""; 
	int s;
	
	try {
	s = list.getSize();
	
	for(int i = 0; i<s;i++) 
	{
		x += list.get(i);
		
	
		if(x.contains("S")) 
			//point to which element in split array you got
			{
				depg[index][0] = 1;	
			}
			if(x.contains("A")) 
			{
				depg[index][1] = 1;	
			}
			if(x.contains("B")) 
			{
				depg[index][2] = 1;
			}
			if(x.contains("C")) 
			{
				depg[index][3] = 1;
			}
			if(x.contains("D")) 
			{
				depg[index][4] = 1;
			}
	
	}
	return "";
	
	}catch(Exception e)
	{
	 return "  ";
		//System.out.println("Something went wrong.");
	}
	
}


 
//retun a dp graph in form of a matrix
static  int[][] twoDar(int dim, LinkedList<String>[] obj ) //given array of linked lists.
{
	int j= 0;
	int[][] array = new int[dim][dim]; //Index here is just to actually know dimenstions 
	System.out.println(Arrays.deepToString(array));
	LinkedList<String> i;
	do {
			i = obj[j] ;
			dep(array,i, j);
			j++;
	}while(i != null);
	
	System.out.println(Arrays.deepToString(array));
	
	return array;
}
//This step 1 getting rid of all empty shit. 
static String step1(String Elist, LinkedList<String>[] obj, int[][] matrix, int ind)
{//String a is the Elist , ind is the dimention of int[][]matrix
	
	
	LinkedList<String> k; 
	int p;
	String data="";
	int l;
	String Ecopy="";
	int size;
	char w;
	String kee="";
	 
	p = 1;//default at 1 /for exmpyt rules to delete right awya 

	
try {	
	for(int i = 0;i < Elist.length(); i++ ) //to transverse Elist (all rules that contain a zero 
	{
		l = Character.getNumericValue(Elist.charAt(i)); //get index where to look 
		
		
	
		k = obj[l]; 
		size = k.getSize();
		
		
		if(size == 1) //if the rule only has an elemetn to the right only 
		{
			 
		
			for(int x = 0; x<size;x++) 
			{
				data += k.get(x);//gets data from the first child  
				
				
				if(data.contains("0"))
					k.remove(x);
				
				
			}	
			 // size of LL at empty rule is now zero 	
			k.remove(i);
			
			for(int m = 0;m < ind; m++)  //ind is the dimention from when it was processed 
			{
				if(matrix[m][l] == 1) // check if its dependent 
					// where A was before (S and B) which are index 0 & 2
				replace(obj,m,l); //p which stayed the same for this loop in txt2 was A (1)
	
			}
			
			kee += l;
			Ecopy = Elist.replaceAll(kee, "");
		//	
			
			//System.out.print( kee+ "           replace" + Elist);
			data= "";
			kee="";
		} else
		{
			
		
		
		
			for(int s = 0; s < size;s++) 
			{
				data += k.get(s);//gets data from the first child  
				
				if(data.contains("0"))
					k.remove(s);
				data ="";
			}	

			//System.out.print(k.toString());
		}
	
	
	
	}	
	
}catch(Exception e)
{
 return "  ";
	//System.out.println("Something went wrong.");
}
	return Ecopy;
	
	

}



static void replace(LinkedList<String>[] obj, int reindex, int cap) //reindex is where it need to be replaced
{   //cap means which capital letter 
	
	String data=""; //to get string at this llis t 
	String uc="";
	LinkedList<String> list;
		//transverse list get data and replace all ('A' with 0)
if(cap==0)
	cap+=83; //83 ='S'
if(cap >= 1) 
	cap += 64;//to get it to be Which ever Capital letter represents 
				//65 is 'A'
	char Upcase = (char)cap ;
	uc+= Upcase; // which letter will be replaced 
	
	list = obj[reindex] ; //In which to do the replacement 
	
	for(int i = 0; i < list.getSize();i++) 
	{
		data += list.get(i);//gets data from the first child  
	
		if(data.contains(uc)) {
			data= data.replaceAll(uc, "");		
			list.set(i, data);//it actually changes it
			}
		
		
		data ="";//dep(array, i, j);
	}	

}


static void toString(LinkedList<String> [] obj) 
{
	int i=0;
	
	for(int s=0;s<10;s++) 
	{
		LinkedList<String> rule = obj[s];
		if(rule != null)
		{ 
			
			System.out.println( cast(s)  +"-"+rule.toString());
		}
	}
	
		
		
	}


static boolean depen(int[][] matrix, int row, int col  )
{
	int ind = matrix.length;
	
	boolean de = false;
	
	if(matrix[row][col] == 1) // check if its dependent 
	{ de =true;
		
	}else {
			de = false;

	}

	return de;




}

static void DFAStateSet( String N, int index, String curr, int z,LinkedList<String> b)
{ //curr emtpy string , int z, length for N 
	//String v=N;
	int n = N.length();
	
	if (index == n)
	{
		if(z == 0) 
		{
				
			System.out.print( curr );
				b.insert(curr);
			
			
		}
		else 
		{
		
			System.out.print(  curr + "|" );
				b.insert(curr);
			
		}
			
	return ;
	}
	DFAStateSet(N, index+1 , curr  + N.charAt(index) ,z--,b);
	DFAStateSet(N, index+1 , curr, z, b);	
	//System.out.println( "c is " + c );
}	

static void getsall(String Elist, LinkedList<String>[] obj)
{
	int l;
	int kk;
	String f="";
	String y="";
	String x="";
	char[] gg;
	int[] cha;
	int v =Elist.length();
	LinkedList<String> k;
	LinkedList<String> uu;
	
	
		
	try{
		for(int i=0; i< v;i++) //for each element in the Varray
	{
		l = Character.getNumericValue(Elist.charAt(i));
		k = obj[l]; //points at chosn ll
		
		
		for(int j= 0;j < k.getSize();j++)
		{	f+=k.get(j);
		//System.out.println("gots " +f + "here");
		
		if((f.contains("A"))||(f.contains("B"))||(f.contains("C"))||(f.contains("D"))||(f.contains("E")) )          
		{	
			x=k.get(j);
			kk=(x.length());
			
			DFAStateSet(x, j, y, kk, k );
			
		}	//
		f="";
		
		}
	

	}
	
	System.out.println(" jk " );
	toString(obj);
	} catch(Exception e) 
 	{ 
 		//System.out.println("Message " + e); 
 	} 	

	

}






static String AddtoVarr(LinkedList<String>[] obj, String Elist, int[][] matrix ) 
{//return new Elist ;
	
	LinkedList<String> k;
	int s;
	char b;
	int gh;
	
	String data ="";
	int size ;
	int h;
	int i =0 ;
	String Ecopy="";
	String B="";
	boolean isAllinV=true;
	boolean ch =false;
	
	
	
	do {
		s = Character.getNumericValue(Elist.charAt(i)); 
	for(int ii = 0;ii < matrix.length; ii++ ) //for each of the rules
	{	
		
		if(depen(matrix,ii,s)) //if its dependent then check them other wise skip
		{	
			k = obj[s]; //at zeor linked list 
		
			for(int t = 0;t < k.getSize();t++) //to process the date check if upper case 
			{	
				data += k.get(t);//gonna get first child 
				
				if(iS(data,Elist)) //if its true that ll of the children are in Varray 
				{
					B+=ii;	
					if(!Elist.contains(B))
					{
						Ecopy += B;// add it to list
					
					}
				
					B="";
				}
				data="";
				
			}
			
		}}
		i++;
	}while(i < Elist.length());

	
	Elist += Ecopy;	
	
return Elist;
	
}




public static LinkedList<String>[] clean( LinkedList<String>[] hash, int a )
	{	
		
		LinkedList<String> b;
	
		toString(hash);
		String x ="";
		
	 try {					
		for(int v = 0;v < a; v++  )
	 	{	b = hash[v];
			
		
			if((b.get(0)=="")&&(b.getSize()!=0))
					{
						System.out.println("hlj " + b.getSize());
						b.set(0, b.get((b.getSize()-1)));
						
						b.remove(b.getSize()-1);
						
					}
			
	
			if((b.getSize()==0)) 
			{
				hash[v]=null;
				
			}}
		
	 }catch(Exception e) 
	 	{ 
	 		System.out.println("Message " + e); 
	 	}
	toString(hash);	
	return hash;	
	}


static boolean isV(int a, String b )  //checks if it can be added to V
{
	boolean yes = false ;
	
	int p; 
	
	for(int k = 0; k < b.length(); k++ )
	{
		p = Character.getNumericValue(b.charAt(k));
		System.out.print( "Fist elemet in elist " + p +"comparing"+ a);
		if(a == p)
		{
			System.out.print( a + " comparing  nums " + b );
			yes = true;
		}	
		
	}
return yes;	
}

static boolean iS(String b ,String Elist )
{
int h;
String data = "";
boolean ch;
boolean in = true;

	for(int t = 0; t < b.length(); t++)	
	{
		if(Character.isUpperCase(b.charAt(t)))//If ist upper case 
		{	
			h = cast(b.charAt(t)); //cast change it numerical value 
			
			System.out.println ("int val " + h + " r f " + Elist );	
			data +=h;
			if(!Elist.contains(data)) {
				System.out.print("out n"+ data );
			in =false;
			break;
			}}
	

	}
return in;
}




static int cast(char b)
{
	int t =0 ;

	if(b=='S') 
	return t = 0;
	if(b=='A') 
		return t =1;
	if(b=='B') 
		return	t =2;
	if(b=='C') 
		return t =3;
	if(b=='D') 
		return t =4;
	return t;
}

static char cast(int b)
{
	char t=' ';
	
	if(b==0) 
	return t = 'S';
	if(b==1) 
		return t ='A';
	if(b==2) 
		return	t ='B';
	if(b==3) 
		return t ='C';
	if(b==4) 
		return t ='D';
	
	
	return t;
}




}
