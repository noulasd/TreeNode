import java.util.LinkedList;
import java.util.Queue;
import java.util.List;	
import java.util.ArrayList;
import java.util.Stack;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;	
import java.io.BufferedReader;
import java.io.InputStreamReader;


public class TreeNode {
	private TreeNode right;
	private TreeNode left;
	private String word;
	private String definition;
	public String getWord() { return word; } //getters
	public String getDefinition() { return definition; }
	public String getLetter() { return word.substring(0, 1);}
	public TreeNode(String s, String d) { //constructor
		word = s;
		definition = d;
	}
	

	public static void inOrder(TreeNode root, String s) //ενδοδιάταξη, print αριστερό παιδί, γονέας, δεξί παιδί
		{											// για αλφαβητική σειρά
			if(root != null)
			{	
				String w = root.getWord();
				if(w.startsWith(s)){
					String t = root.getDefinition(); 
					inOrder(root.left,s); //εδώ γίνεται αναδρομικά το print αριστερό
					System.out.print(w + " " + t + "\n"); //print τον γονέα
					inOrder(root.right,s); //αναδρομικά η ενδοδιάταξη για το δεξί παιδί
				}
			}
	}


	public void find (String s) {
		
		int n = s.compareToIgnoreCase(word); // η compareto συγκρινει αλφαβητική σειρά
		//οπότε έχουμε ίδια δομή με ένα δένδρο με τους αριθμούς της αλφαβητικής σειράς
		if (n == 0){	
			System.out.println(word + " " + definition); //αν βρούμε την λέξη την εκτυπώνουμε
			inOrder(right,s); //ενδοδιάταξη για εκτύπωση τον υπόλοιπων λέξεων με αλφαβητική σειρά
		}
		if (n  > 0) { // n > 0 σημαίνει η s που ψάχνουμε είναι πιό μετά από την λέξη του κόμβου που βρισκόμαστε
			if (right != null) //άρα ψάχνουμε αναδρομικά στο δεξί παιδί αν υπάρχει
				right.find(s);
			
		}
		if (n<0){ // αν η λέξη μας είναι πιο πριν αλφαβητικά από τον κόμβο που βρισκόμαστε
			if (word.startsWith(s)){ //αλλά ο κόμβος την περιέχει σαν υπολέξη τότέ
				
				inOrder(this, s);  //ειμαστε στην περίπτωση s = chil -> chill, children, chilly
									//δεν βρήκαμε την λέξη γιατί δεν υπάρχει αλλά εκτυπώνουμε με
									//ενδοδιάταξη το υποδέντρο όσο περιέχονται τα αρχικά γράμματα της s
		}else if (left !=  null) {
			left.find(s);
		}}
	}
		
	public void insert (String s, String d) {
		if (s.equals(word)) { //ομοίως η σύγκριση με το find, αν υπάρχει ανανεώνουμε τη μετάφραση
			definition = d;
			return;
		}
		if (s.compareTo(word) > 0) { //πιο μετά σε αλφαβητική σειρά + ο κόμβος δεν έχει δεξί παιδί
			if (right != null)		 // -> νέο δεξί παιδί, αλλιώς αναδρομή στο υπάρχον δεξί παιδί	
				right.insert(s,d);
			else
				right = new TreeNode(s, d);
			return;
		}
		if (left != null) 	//ομοίως με δεξί παιδί
			left.insert(s,d);
		else
			left = new TreeNode(s, d);
	}

	public static TreeNode DeleteNode(TreeNode root, String s) 
    { 
		/* Βάση Αναδρομής */
		
		int key = s.compareToIgnoreCase(root.word); //ομοίως η αλφαβητική σύγκριση με compareto
		
        if (root == null){  return root;} 
  
        /* ψάχνοτας αναδρομήικά με την αλφαβητική διάταξη */
        if (key < 0) 
            root.left = DeleteNode(root.left, s); 
        else if (key > 0 ) 
            root.right = DeleteNode(root.right, s); 
  
        // Διαγραφή 
        else
        { 
            // Ένα η κανένα παιδί μπαίνει το παιδί στην θέση του κόμβου που διαγράφεται
            if (root.left == null) 
                return root.right; 
            else if (root.right == null) 
                return root.left; 
  
			// Δύο παιδιά: θέλουμε για αντικατάσταση το επόμενο στοιχείο της ενδοδιάταξης δηλαδή βρίσκουμε το
			//αριστερότερο στο δεξί υποδένδρο
			
            root.word = minNode(root.right); 
  
            // διαγράφουμε εκείνο το στοιχείο από την παλιά του θέση
            root.right = DeleteNode(root.right, root.word); 
        } 
  
        return root; 
    } 
	 public static String minNode(TreeNode root) 
    { 
        String str = root.getWord(); //κάνουμε αναδρομή για να βρούμε το αριστερότερο στοιχείο ενός δένδρου
        while (root.left != null)   //που χρησιμοποιήθηκε στο delete για το δεξί υπόδεντρο
        { 
            str = root.left.getWord();
            root = root.left; 
        } 
        return str; 
	} 

/************************* Εδώ είναι οι συναρτήσεις της εκτύπωσης ************************* */

	public static int findHeight(TreeNode aNode) {
	    if (aNode == null) {
	        return 0;
	    }
			//αναδρομικά μετράμε τα ύψη του κάθε υποδένδρου για 
			//κάθε γονέα και κρατάμε το μεγαλύερο και προσθέτουμε +1 για τον γονέα
	    int lefth = findHeight(aNode.left);
	    int righth = findHeight(aNode.right);

	    if (lefth > righth) {
	        return lefth + 1;
	    } else {
	        return righth + 1;
	    }
	}


	
	 public static String spaces(TreeNode root){ //εδώ δημιουργείται το string που περιέχει το γράμμα της λέξης
		 String str="";							//και τα κενά που του αντιστοιχούν αριστερά και δεξιά που είναι ίσα με
		 for(int i=0;i<NodeSum(root.left);i++){str+="_";} //τους πόσους κόμβους έχει το αριστερό και δεξί αντίστοιχα υπόδεντρο
		 str+=root.getLetter();
		 for(int i=0;i<NodeSum(root.right);i++){str+="_";}
		 return str;
	 }

	
	 public static void printLevelOrder(TreeNode root) //κάνουμε print του κάθε επιπέδου ξεχωριστά
    { 	
        int h = findHeight(root); 			//αυτή είναι η συνάρτηση που κάνει επανάληψη για όλα τα επίπεδα
		int i; 								//όσο είναι το ύψος του δέντρου
		
		String str="";
        for (i=1; i<=h; i++) {
			String s = printGivenLevel(root, i); //λόγω ότι υπάρχει ένα +"_" στο κάθε level
			str+=s.substring(0,s.length()-1); // αφαιρούμε το τελευταίο που δεν χρησιμεύει από την κάθε
												//γραμμή εκτύπωσης
			 
			str+="\n";
		
	}System.out.println(str);
 }

	public static String printGivenLevel (TreeNode root ,int level) 
	{ 
			//εκτυπώνουμε το κάθε επίπεδο σε μία γραμμή
			//κάνουμε αναδρομή στα επίπεδα και ότι string παράξουμε το κάνουμε concatenation για το τελικό του γονέα
	if (root == null) 
		
		return "_"; 
	if (level == 1) 
		return spaces(root)+"_"; //το +"_" είναι ώστε κάθε γράμμα να δεσμεύει την κάθε στήλη στην εκτύπωση

	else
	{ 
		return printGivenLevel(root.left, level-1)+printGivenLevel(root.right, level-1); 
		
	} 
	
	} 

	 

	 public static int NodeSum(TreeNode root){ //αναδρομικά βρίσκουμε πόσους κόμβους έχει ένα δέντρο
		
		if (root!=null){
			return NodeSum(root.left)+NodeSum(root.right)+1;
		}else return 0;

	 }
	 /************************************************************************ */


	 /************************************************************************* 
	 Από εδώ και πέρα που είναι η main και τρέχει το πρόγραμμα γίνεται αρχικά η εισαγωγή κάποιων 
	 ενδεικτικών λέξεων για το λεξικό και μετά είναι ο χειρισμός του χρήστη της java

	 Δημιουργούμε ένα αντικείμενο το οποίο θεωρούμε κορυφή του δέντρου και με αυτό το αντικείμενο
	 ξεκινάει η αναδρομή στις από πάνω συναρτήσεις για την κάθε λειτουργία του προγράμματος
	**************************************************************************** */
	public static void main(String args[]) {
		
		TreeNode root = new TreeNode("disturbed","διαταραγμένος");
		
    root.insert("interrupt","διακοπή");
    root.insert("long","μακρύ");
    root.insert("sticky","κολλώδης");
    root.insert("dear","αγαπητός");
    root.insert("judge","δικαστής");
    root.insert("laughable","γελοίος");
    root.insert("behave","συμπεριφέρομαι");
    root.insert("best","καλύτερος");
    root.insert("luck","τύχη");
    root.insert("gold","χρυσός");
    root.insert("dust","σκόνη");
    root.insert("scrape","ξύνω");
    root.insert("faithful","πιστός");
    root.insert("plastic","πλαστικό");
    root.insert("paper","χαρτί");
    root.insert("company","εταιρία");
    root.insert("horrible","φρικτός");
    root.insert("quicksand","κινούμενη άμμος");
    root.insert("sister","αδερφή");
    root.insert("tacit","σιωπηρός");
    root.insert("mountain","βουνό");
    root.insert("stereotyped","στερεότυπος");
    root.insert("gleaming","λαμπερό");
    root.insert("awesome","φοβερό");
    root.insert("slim","λεπτό");
    root.insert("unequaled","αμίμητος");
    root.insert("valuable","πολύτιμος");
    root.insert("present","δώρο");
    root.insert("worm","σκουλήκι");
    root.insert("wet","βρεγμένο");
    root.insert("rest","υπόλοιπο");
    root.insert("zesty","ζωηρό");
    root.insert("damaged","σκάρτος");
    root.insert("defective","ελαττωματικός");
    root.insert("talk","μιλώ");
    root.insert("pat","ελαφρό κτύπημα");
    root.insert("cowardly","δειλά");
    root.insert("moon","φεγγάρι");
    root.insert("weight","βάρος");
    root.insert("lively","ζωηρός");
    root.insert("release","ελευθέρωση");
    root.insert("thirsty","διψασμένος");
    root.insert("bitter","πικρός");
    root.insert("alike","ομοίως");
    root.insert("reason","λόγος");
    root.insert("try","προσπάθεια");
    root.insert("substance","ουσία");
    root.insert("abandoned","εγκαταλειμμένος");
    root.insert("degree","βαθμός");
    root.insert("poor","φτωχός");
    root.insert("prickly","ακανθώδης");
    root.insert("carpenter","ξυλουργός");
    root.insert("exchange","ανταλλαγή");
    root.insert("title","τίτλος");
    root.insert("condition","κατάσταση");
    root.insert("drain","διοχετεύω");
    root.insert("oil","λάδι");
    root.insert("touch","αφή");
    root.insert("ghost","φάντασμα");
    root.insert("agonizing","αγωνιώδης");
    root.insert("swift","ταχύς");
    root.insert("comb","χτένα");
    root.insert("jellyfish","μέδουσα");
    root.insert("rigid","άκαμπτος");
    root.insert("painful","επώδυνος");
    root.insert("chew","μασάω");
    root.insert("broad","ευρύς");
    root.insert("aberrant","ανώμαλο");
    root.insert("racial","φυλετικός");
    root.insert("flawless","άψογο");
    root.insert("fluffy","χνουδωτό");
    root.insert("satisfying","ικανοποίηση");
    root.insert("breakable","έυθραυστο");
    root.insert("flow","ροή");
    root.insert("quick","γρήγορο");
    root.insert("vessel","σκάφος");
    root.insert("careful","προσεκτικός");
    root.insert("develop","αναπτύσσω");
    root.insert("bury","θάβω");
    root.insert("cooperative","συνεργάσιμος");
    root.insert("scent","μυρωδιά");
    root.insert("head","κεφάλι");
    root.insert("brake","φρένο");
    root.insert("fold","πτυχή");
    root.insert("numerous","πολυάριθμος");
    root.insert("bushes","θάμνοι");
    root.insert("road","δρόμος");
    root.insert("beef","βοδινό κρέας");
    root.insert("number","αριθμός");
    root.insert("scold","μαλώνω");
	root.insert("touch","αφή");
	root.insert("chill","ψύχρα");
	root.insert("child","παιδί");
	root.insert("children","παιδιά");

	

		
	boolean flag = true;
	while(flag){
		System.out.println("\n");
		System.out.println("1. Display Binary Search Tree");
		System.out.println("2. Insert");
		System.out.println("3. Find");
		System.out.println("4. Delete");
		System.out.println("5. Exit");
		



		String input = "";
		int n = 0;
		while(true) {
		    try {
				
		        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		        input = br.readLine();
		        n = Integer.parseInt(input);
		        if( n>=1 && n<=5 ){break;}
		        else {System.out.println("Μη έγκυρη καταχώρηση!πληκτρολογείστε ξανά!");}
		    } catch (NumberFormatException ex) {
		       System.out.println("Μη έγκυρη καταχώρηση!πληκτρολογείστε ξανά!");
		 } catch (IOException e){
			System.out.println("Μη έγκυρη καταχώρηση!πληκτρολογείστε ξανά!");
		 }
	}




		switch (n) {
			case 1:

				printLevelOrder(root);
				break;

			case 2:	
				
			String input1="";
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			try {
		
				System.out.println("word:");
				input1 = br.readLine();
				String word=input1;
		
		
				System.out.println("translation:");
				input1 = br.readLine();
				String translation =input1;
				
				root.insert(word,translation);
		
				
			} catch (IOException e) {
				
				//e.printStackTrace();
			}

		
				break;
			
			case 3:

			String input2="";
			BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
			try {
		
				System.out.println("Search:");
				input2 = br1.readLine();
				String w=input2;
				System.out.println("\n");
				root.find(w);
			} catch (IOException e) {
				
				//e.printStackTrace();
			}
				break;
			case 4:

			String input3="";
			BufferedReader br2 = new BufferedReader(new InputStreamReader(System.in));
			try {
		
				System.out.println("Delete:");
				input3 = br2.readLine();
				String w=input3;
				System.out.println("\n");
				
				root = DeleteNode(root,w);
				System.out.println("Επιτυχής Διαγραφή");
				
			} catch (IOException e) {
				
				e.printStackTrace();
			} catch (NullPointerException e){
				System.out.println("Δεν βρέθηκε η λέξη");
			}

				break;
			case 5:
				flag=false;
				break;	
	}}
	}
}