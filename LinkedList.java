
	import java.util.*;

	public class LinkedList<E>
	{
		
			private class Node<F>
			{	
				private F data;
				private Node<F> next;
			
				
				public Node(F childList) 		      //Constructor when only the data is supplied
				{
					this(childList, null);             //To call the other constructor to create the Node
				}
				
				public Node(F childList, Node<F> next) //Constructor when only the data is supplied
				{
						data = childList;
						//To call the other constructor to create the Node
				}	
					
				public F getData() {
					return data;
				}

				public void setData(F data) {
					this.data = data;
				}

				public Node<F> getNext() {
					return next;
				}

				public void setNext(Node<F> next) {
					this.next = next;
				}
			
			}
			
			public Node<E> getHead() {
				return head;
			}

			public void setHead(Node<E> head) {
				this.head = head;
			}

			public int getSize() {
				return size;
			}

			public void setSize(int size) {
				this.size = size;
			}

			private Node<E> head;  
			private int size = 0 ; 				//How many nodes in List

			public LinkedList()
			{
				this.head = new Node<E>(null); //Head is set to Null for initilization of LL 
				this.size = 0;                 //When the LL is created size = 0;
			}
			
			
			public void insert(E item) //This function adds node to the rear of list only
			{
				Node<E> node = new Node<>(item);
				if(head == null)
				{
					
					head.setNext(node); //If list is empty added node becomes the head
				}
				else {
					Node<E> h = head;
					while(h.getNext() != null)
					{
						h = h.getNext();
					}
					h.setNext(node);
				}
				size++;
			}
				
		public void set(int index, E newValue)
		{
			
			//Node<E> temp= getNode(index);
			if(index < 0|| index >= size) 
			{ 	//Checking for valid index
			System.out.println("invalid index"); // Take it out to return just null
					return ;
			}
			Node<E> temp= getNode(index);
				
			temp.setData(newValue);
			return ;
		
		}	

		public void add(int index, E item)
		{
		//This method adds an element to the LL
		//Depending on the index, it adds to the first location using addFirst()
		//or adds after a node using addAfter()
		if(index < 0 || index > size) { //Checking for valid index
			System.out.println("Invalid Index!");
			return;
			}
		else if(index == 0)
			{addFirst(item);
			}else
			{
		//If adding after some node, we need the reference of that node (Helper method)
			Node<E> node = getNode(index);
			addAfter(node,item);
			}
		}

		
			
		private void addAfter(Node<E> node, E item) {
			// Add the item after the reference "node"
			//node.next = new Node<E>(item, node.next);
			node.next = new Node<E>(item, null);
			size++;

			}
			private void addFirst(E item) {
			// We create a node, with item as data, and head.next as the next
			//The we update the head.next to point this newly created node
			//Finally we increment the size
			//Node<E> temp = new Node<E>(item, head.next);
			//head.next = temp;
			head.next = new Node<E>(item, null);
			size++;

			}	
			
			public E get(int index)
			{
				if(index < 0|| index >= size) { 	//Checking for valid index
				//	System.out.println("); // Take it out to return just null
					return null;
				}
				
				Node<E> temp ;          //To add Child List of each Node in the Adjency List
				if(index == 0)
				{
					temp = getNode(index);
					return temp.getData();
				}
				else 
				{
					temp = getNode(index + 1); 
				}
			    return temp.getData();//returns data at given index   
			}

			
			
			public Node<E> getNode(int index) 
			{
				if(index < 0|| index > size) 
				{ //Checking for valid index
					System.out.println("Invalid Index! perr ");
					return null;
				}
			// To get Node at index, it must loop over the LL, & return the reference
				Node<E> node = head; //Reference copy of the head
				if(index == 0) 
				{
					node = head.getNext();
					return node;
				}
				for(int i = 0; i < index && node != null; i++)
				{
					node = node.getNext();
				}
				
			return node; //return the references
			}

			//Implements toString() to print the LL data
				public String toString()
				{
					String s = "";
					Node<E> p = head; //This reference will be used to iterate over the LL
					if(p != null)
				{ //When there is no head, check the error
						while(p.getNext() != null)
						{
							s += p.getNext().getData() + "|";  //Adds the data to the String
							p = p.getNext(); //Go to the next Node
						}
				}
					
					return s;
				}
				
			

		 public LinkedList<E> UpdateState( LinkedList<E>[] hash, int x )
			{		
				LinkedList<E> headN = hash[x-1]; //points at the first node the linked list in o]
				return headN;	
			}
			
			
		 
		 
		 
		public String Eq_State(LinkedList<E>[] hash, LinkedList<E> headN)
		{
			String t ="";
			Node<E> c =  headN.getHead(); 	//point at head
			Node <E> h = c.getNext(); 		 //head's data (NFA state)
				if(h.getNext() == null)		 //checks if the the first child is null so it only returns  
				{	String o = "";
					o +=c.getNext().getData(); //data at head at this case 
					return t+= o ;
				}
				else //When the node actually has kids
				{	
					String p="";
					p+=c.getNext().getData(); // gets info at head  
					t+= p; //first comma 
					while(h.getNext() != null) // lL doesnt end 
					{	String s="";
						int k;
						s += h.getNext().getData(); //data saved at child 
						k = Integer.valueOf(s);
						
						s+= getNewState(hash,k);
						t+= ","+ s ;
						h = h.getNext();
					}	
				return  t  ;
				}


		}

		
		
		
		
		public String getNewState( LinkedList<E>[] arr , int ind) //gonna get the new linked list at index 
		{	
			String q = "";
			LinkedList<E> head;
			head = UpdateState(arr, ind);                //Where its gonna start 
			q +=JumpState(arr,head); 		
			return q;
		}

		
		
		
		

		public String JumpState(LinkedList<E>[] hash,LinkedList<E> headN) //Pointer to index of array and head of each ll
		{ 	
				String t ="";
				Node<E> c = headN.getHead();// gets the head from the linked list (
				Node <E> h = c.getNext();	 //head
				if(h.getNext() == null)		 //checks if the the first child is null so it only return head 
				{	String l = "";
					l+=c.getNext().getData();  
					return ""; 
				}		
				else //When the node actually has children
				{	String jk= " end of trial";
					while(h.getNext() != null) // until Linked List at node doesnt end 
					{	String v="";
						int k;
						v+= h.getNext().getData(); // gets info at head  
						k = Integer.valueOf(v);
					v+= getNewState(hash,k);
					t+=","+ v;
					h = h.getNext();
					}
				return t;
				}

		}


		public E remove(int index) 
		{
		// This met6hod removes and element from the LL at a given index
			if(index < 0|| index > size)
			{   //Checking for valid index
				System.out.println("Invalid Index!");
				return null;
			}
			else if(index == 0 )
			{
				
				return removeFirst();
			}
			else {
				Node<E> node = getNode(index);
				return removeAfter(node);
			}
		}
		
		private E removeFirst()
		{ // Deletes the first Node
			Node<E> temp = head; //Reference to head of list
			if(temp != null) //if not Empty return data at Head Node
			{ 
				head =head.next;
				size--;
				return temp.getData();
				
			}
			else {
				return null; //If list is empty return null
			}}
		
		private E removeAfter(Node<E> node)
		{
			Node<E> temp = node.getNext(); 		//Temp reference to next node
			if(temp != null) 			    	//if this next node is not the end of the list
			{  
				node.setNext(temp.getNext()); 	//node references the node after temp
				size--;
				return temp.getData();
						
			}
		else
			{	return null;  }					//if next node is null, it returns null
		}

		
		
		
		
		
		
		
		
		
		
		
		
		
	
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

