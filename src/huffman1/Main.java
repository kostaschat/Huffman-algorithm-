package huffman1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
         char checker;
	static Node node;
	static Node newRoot;
	static String codedString = "";

	public static void main(String[] args) throws FileNotFoundException {
                Scanner sc = new Scanner(new File("keimeno.txt"));            //diabazei to keimeno apo txt arxeio 
                String mhnuma="";
                mhnuma = sc.nextLine();
		char[] mnmxar = mhnuma.toCharArray();  //  metatrepei to string se array xarakthrwn
		ArrayList<Character> xarakthres = new ArrayList<Character>();

		for (int i = 0; i < mnmxar.length; i++) {      //apothikeuei olous tous xarakthres xwris na epanalambanete kapoios apo tous xarakthres
			if (!(xarakthres.contains(mnmxar[i]))) {  // mnmxar exei to sunolo twn xarakthrwn poy exei to arxiko keimeno
				xarakthres.add(mnmxar[i]);
			}
		}
		
		 System.out.println("oi xarakthres einai : "+xarakthres);                          // ekttupwnei tous xarakthres 
                 System.out.println("sunolo xarakthrwn tou arxikou keimenou : "+mnmxar.length);           
		int[] countOfChar = new int[xarakthres.size()];           
		for (int x = 0; x < countOfChar.length; x++) {           
			countOfChar[x] = 0;
		}
		for (int i = 0; i < xarakthres.size(); i++) {                //briskei thn suxnotita tou xarakthra sto keimeno 
			char checker = xarakthres.get(i);
			for (int x = 0; x < mnmxar.length; x++) {
				if (checker == mnmxar[x]) {
					countOfChar[i]++;
				}
			}
		}
		for (int i = 0; i < countOfChar.length - 1; i++) {
			for (int j = 0; j < countOfChar.length - 1; j++) {        // taksinomhsh xarakthrwn kata fthinousa 
				if (countOfChar[j] < countOfChar[j + 1]) {
					int temp = countOfChar[j];
					countOfChar[j] = countOfChar[j + 1];
					countOfChar[j + 1] = temp;

					char tempChar = xarakthres.get(j);
					xarakthres.set(j, xarakthres.get(j + 1));
					xarakthres.set(j + 1, tempChar);
				}
			}
		}
                System.out.println("-----------------------------------------------------------------------------");   
		for (int x = 0; x < countOfChar.length; x++) {
                    
		System.out.println("o xarakthras "+xarakthres.get(x) + " emfanizete sunolika sto arxiko keimeno " +countOfChar[x]+" apo tous "+mnmxar.length+" xarakthres" );       /* ektupwsh xarakthrwn me ton arithmo emfanishs sto keimeno */
                        
		}
                System.out.println("-----------------------------------------------------------------------------");   
		
                /* Form Leaf Nodes and Arrange them in a linked list */

		Node root = null;
		Node current = null;
		Node end = null;

		for (int i = 0; i < countOfChar.length; i++) {
			Node node = new Node(xarakthres.get(i).toString(), countOfChar[i]);
			if (root == null) {
				root = node;
				end = node;
			} else {
				current = root;
				while (current.linker != null) {
					current = current.linker;
				}
				current.linker = node;
				current.linker.linkerBack = current;
				end = node;
			}
		}
		dentro(root, end);
		inOrder(node);
		char[] messageArray = mhnuma.toCharArray();
	        char checker;
              int k=0;
		for (int i = 0; i < messageArray.length; i++) {      // gia na balei ston kombo an einai apo aristera=0 h deksia=1 
                    
			current = node;
			checker = messageArray[i];
			String code = "";
			while (true) {
				if (current.left.value.toCharArray()[0] == checker) {
					code += "0";
					break;
				} else {
					code += "1";
					if (current.right != null) {
						if (current.right.value.toCharArray()[0] == xarakthres
                                                        .get(countOfChar.length - 1)) {
							break;
						}
						current = current.right;
					} else {
						break;
					}
				}
			}                   
                        if(k<countOfChar.length){
                         try { 
                    FileWriter writer = new FileWriter("codegr.cm", true);     
                    writer.write(checker +"-"+code);
                    writer.write("\r\n");  
                    k++;
                    writer.close();
                 } catch (IOException e) {
                     e.printStackTrace();
                       }
                        }
			codedString += code;    
                }
                System.out.println("o pinakas me tous xarakthres kwdikopoihshs briskete sto arxeio codegr");
		System.out.println("h kwdikopoihmenh eisodos briskete sto arxeio code");
                try {
                    FileWriter writer = new FileWriter("code.huf", true);
                    writer.write(codedString);
                    writer.write("\r\n");  
                    
                    writer.close();
                 } catch (IOException e) {
                     e.printStackTrace();
                    }
                }
	public static void inOrder(Node root) {

		if (root != null) {
			inOrder(root.left);
			
			inOrder(root.right);
		}
	}
	public static void dentro(Node root, Node end) {
		node = new Node(end.linkerBack.value + end.value, end.linkerBack.count
				+ end.count);
		node.left = end.linkerBack;
		node.right = end;
		end.linkerBack.linkerBack.linker = node;
		node.linkerBack = end.linkerBack.linkerBack;
		end = node;
		end.linker = null;
		Node current = root;

		while (current.linker != null) {			
			current = current.linker;
		}

		if (root.linker == end) {
			node = new Node(root.value + end.value, root.count + end.count);
			node.left = root;
			node.right = end;
			node.linker = null;
			node.linkerBack = null;
			
			newRoot = node;
		} else {
			dentro(root, end);
		}
	}

}
class Node {
	String value;
	int count;
	Node left;
	Node right;
	Node linker;
	Node linkerBack;
	Node(String value, int count) {

		this.value = value;
		this.count = count;
		this.left = null;
		this.right = null;
		this.linker = null;
		this.linkerBack = null;
	}

}