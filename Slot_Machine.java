import java.util.*;


class Slot_Machine
{
    static final HashMap<String, Integer> frequency = new HashMap<String, Integer>();
    static final HashMap<String, Integer> reward = new HashMap<String, Integer>();

    static {
    frequency.put("A", 10);
    frequency.put("B", 8);
    frequency.put("C",6);
    frequency.put("D", 2);

    reward.put("A", 7);
    reward.put("B", 8);
    reward.put("C", 9);
    reward.put("D", 10);
    }

    public static float get_Deposit(Scanner scan) { 
        System.out.print("Enter the amount to deposit: "); 
        float deposit = scan.nextFloat(); // Read value from user
        return deposit;
      }

    public static float get_Bet(Scanner scan) { 
       
        System.out.print("Enter the amount to bet: "); 
        float bet = scan.nextFloat(); 
        String str =scan.nextLine(); 
        return bet;
    }

    public static int get_Lines(Scanner scan) { 
        System.out.print("Enter the number of lines (maximum of 3): "); 
        int lines = scan.nextInt(); 
        return lines;
    }

    public static ArrayList<String> generate_symbols(){
        ArrayList<String> reel_symbols = new ArrayList<String>();

        for (Map.Entry<String, Integer> set :
             frequency.entrySet()) {
            for (int i = 0; i < set.getValue(); i++) {
                reel_symbols.add(set.getKey());
            }
        }

        return reel_symbols;

    }

    public static String[][] spin() { 
        int rows = 3;
        int columns = 3;
        String[][] displayed_columns = new String[3][3];

        for (int j = 0; j < columns; j++) {
            ArrayList<String> reel_symbols = generate_symbols();
            Random random = new Random();

            for (int i = 0; i < rows; i++) {
                int randomIndex = random.nextInt(reel_symbols.size());
                displayed_columns[i][j] = reel_symbols.get(randomIndex); 
                reel_symbols.remove(randomIndex);
                //System.out.print(displayed_columns[i][j]);
            }
            //System.out.print("\n");
        }
       
        return displayed_columns;


    }

    public static void print(String[][] displayed_columns){
        int rows = 3;
        int columns = 3;
        for (int i = 0; i < rows; i++) {
            if (i != 0){
                System.out.print("-------------" + "\n");
            }
            System.out.print("| ");
            for (int j = 0; j < columns; j++) {
                System.out.print(displayed_columns[i][j] + (" | "));
            }
            System.out.println();
        }
        System.out.println();

    }

    public static int check_winnings(String[][] displayed_columns, int lines, float bet){
        int score = 0;
        int rows = 3; 
        int count = 0;

        for (int i = 0; i < rows; i++){
            if (displayed_columns[i][0].equals(displayed_columns[i][1]) && displayed_columns[i][1].equals(displayed_columns[i][2])){
                count += 1;
                if (count <= lines){
                    score += (reward.get((displayed_columns[i][0]))*bet);
                }
            }
        } 

        return score;
    }
    public static void main(String args[])
    {
        Scanner scan = new Scanner(System.in); // Create Reader 

        float balance = get_Deposit(scan);
        boolean spin_again = true;

        while (spin_again){
            
            int lines = get_Lines(scan);

            while (lines < 1 || lines > 3){
                System.out.print("The number of lines must be 1, 2 or 3." + "\n");
                lines = get_Lines(scan);
            }

            float bet = get_Bet(scan);

            while ((bet * lines) > balance){ 
                System.out.print("Insufficient balance! Please try again: " + "\n");
                bet = get_Bet(scan);
            }

            balance -= bet * lines; 

            String[][] displayed_columns = spin();
            print(displayed_columns);
            int score = check_winnings(displayed_columns, lines, bet);
            balance += score;
            
            System.out.println("You won £" + score);
            System.out.println("Your balance is £" + balance);

            System.out.println("Would you like to spin again (Y/N)? ");
            String response = scan.nextLine();

            if (response.toLowerCase().equals("y")){
                spin_again = true;
            }
            else{
                spin_again = false;
            }

        }
        System.out.println("Cashed out! Your final takings are £" + balance +"\n" + "Thanks for playing");

    }
}