import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Emily Becher
 * 2/21/19
 * Videos Done
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initializes all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 1.0 (February 2002)
 */

class Game 
{
    private Parser parser;
    private Room currentRoom;
    Room outside, theatre, pub, lab, office, onetwenty, study, restaurant; 
    Room billiard, conservatory, workshop, metro, library, ballroom, kitchen;
    ArrayList<Item> inventory = new ArrayList<Item>();
    int count = 0;
    boolean check = false;
    Scanner scanner = new Scanner(System.in);
        
    /**
     * Create the game and initialize its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
    }
    
    public static void main(String[] args) {
    	Game mygame = new Game();
    	mygame.play();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
      
        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theatre = new Room("in a lecture theatre");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        onetwenty = new Room("in the coolest place in the world");
        study = new Room("in the study");
        kitchen = new Room("in the kitchen");
        ballroom = new Room("in the ballroom");
        library = new Room("in the library");
        billiard = new Room("in a game room with table tennis and billiards");
        conservatory = new Room("in the conservatory");
        workshop = new Room("in the workshop where a lot of wood working is done");
        metro = new Room("in an abandonned subway station");
        restaurant = new Room("in a restaurant");
        
        // initialize room exits
        outside.setExit("north", theatre);
        outside.setExit("east", onetwenty);
        outside.setExit("south", lab);
        outside.setExit("west", restaurant);

        theatre.setExit("south", outside);
        theatre.setExit("west", ballroom);

        pub.setExit("north", billiard);
        pub.setExit("east", restaurant);
        pub.setExit("south", metro);
        pub.setExit("west", kitchen);

        lab.setExit("north", outside);
        lab.setExit("east", office);
        lab.setExit("south", workshop);
        lab.setExit("west", library);

        office.setExit("north", onetwenty);
        office.setExit("south", conservatory);
        office.setExit("west", lab);
        
        onetwenty.setExit("south", office);
        onetwenty.setExit("west", outside);
        
        study.setExit("north", library);
        study.setExit("east", workshop);
        
        kitchen.setExit("east", pub);
        
        ballroom.setExit("east", theatre);
        ballroom.setExit("south", restaurant);
        ballroom.setExit("west", billiard);
        
        library.setExit("north", restaurant);
        library.setExit("east", lab);
        library.setExit("south", study);
        library.setExit("west", metro);
        
        billiard.setExit("east", ballroom);
        billiard.setExit("south", pub);
        
        conservatory.setExit("north", office);
        conservatory.setExit("west", workshop);
        
        workshop.setExit("north", lab);
        workshop.setExit("east", conservatory);
        workshop.setExit("west", study);
        
        metro.setExit("north", pub);
        metro.setExit("east", library);
        
        restaurant.setExit("north", ballroom);
        restaurant.setExit("east", outside);
        restaurant.setExit("south", library);
        restaurant.setExit("west", pub);

        currentRoom = outside;  // start game outside
        
        //add items to rooms
        inventory.add(new Item("computer"));
        onetwenty.setItem(new Item("robot"));
        library.setItem(new Item("candlestick"));
        outside.setItem(new Item("knife"));
        office.setItem(new Item("keys"));
        kitchen.setItem(new Item("pistol"));
        billiard.setItem(new Item("wrench"));
        
        //add clues to rooms (for the talk command)
        onetwenty.setClue(new Clue("That robot seems to be trying to become friends with the projector."));
        outside.setClue(new Clue("Isn't the wood handle of the knife beautiful?"));
        theatre.setClue(new Clue("I'm missing my weapon! I can't kill him!"));
        pub.setClue(new Clue("It's a mess back here. Water is everywhere!"));
        lab.setClue(new Clue("My project seems to have wandered off."));
        office.setClue(new Clue("These aren't my keys."));
        study.setClue(new Clue("Where did it go? I have a project due at midnight!"));
        kitchen.setClue(new Clue("The pistol is clearly fake."));
        ballroom.setClue(new Clue("There isn't quite enough light to create a good mood."));
        library.setClue(new Clue("Fire shouldn't be around books!"));
        billiard.setClue(new Clue("There are no pipes to repair in here."));
        conservatory.setClue(new Clue("Be Quiet! If I so much as hear a pin drop..."));
        workshop.setClue(new Clue("I can't find my woodworking design project!"));
        metro.setClue(new Clue("People call this a black hole for lost items"));
        restaurant.setClue(new Clue("I can't open the door and people will be here any minute."));
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing. Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private boolean printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to Adventure!");
        System.out.println("Someone has stolen things and scattered them around the town!");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        //ask if user would like hints
        System.out.println("Would you like to know if your item placement is correct");
        String yesno = scanner.nextLine();
		if (yesno.equals("yes") || yesno.equals("y")) {
			check = true;}
		else {
			check = false;}
        System.out.println(currentRoom.getLongDescription());
        return check;
    }

    /**
     * Given a command, process (that is: execute) the command.
     * If this command ends the game, true is returned, otherwise false is
     * returned.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("inventory")) {
        	printInventory();
        }
        else if (commandWord.equals("get")) {
        	getItem(command);
        }
        else if (commandWord.equals("drop")) {
        	wantToQuit = dropItem(command);
        }
        else if (commandWord.equals("talk")) {
        	Speak(command);
        }
        return wantToQuit;
    }
    //Drop Item Command
    private boolean dropItem(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to drop...
            System.out.println("Drop what?");
            return false;
        }
        //second word is the item being dropped
        String item = command.getSecondWord();

        // Try to leave current room.
        Item newItem = null;
        int index = 0;
        for (int i = 0; i < inventory.size(); i++) {
        	if (inventory.get(i).getDescription().equals(item)) {
        		newItem = inventory.get(i);
        		index = i;
        	}
        }

        if (newItem == null)
            System.out.println("You can't drop something you don't have!");
        else {
            inventory.remove(index);
            if (currentRoom == metro) {//if item is dropped in the metro randomly put it in another room
            	Random rand = new Random();
            	int num = rand.nextInt(14);//generate random number
            	switch (num) {//changes random number into random room and moves the item there
            	case 0:
            		System.out.println("0");
            		outside.setItem(new Item(item));
            		break;
            	case 1:
            		System.out.println("1");
            		theatre.setItem(new Item(item));
            		break;
            	case 2:
            		System.out.println("2");
            		pub.setItem(new Item(item));
            		break;
            	case 3:
            		System.out.println("3");
            		lab.setItem(new Item(item));
            		break;
            	case 4:
            		System.out.println("4");
            		office.setItem(new Item(item));
            		break;
            	case 5:
            		System.out.println("5");
            		onetwenty.setItem(new Item(item));
            		break;
            	case 6:
            		System.out.println("6");
            		study.setItem(new Item(item));
            		break;
            	case 7:
            		System.out.println("7");
            		kitchen.setItem(new Item(item));
            		break;
            	case 8:
            		System.out.println("8");
            		ballroom.setItem(new Item(item));
            		break;
            	case 9:
            		System.out.println("9");
            		library.setItem(new Item(item));
            		break;
            	case 10:
            		System.out.println("10");
            		billiard.setItem(new Item(item));
            		break;
            	case 11:
            		System.out.println("11");
            		conservatory.setItem(new Item(item));
            		break;
            	case 12:
            		System.out.println("12");
            		workshop.setItem(new Item(item));
            		break;
            	case 13:
            		System.out.println("13");
            		restaurant.setItem(new Item(item));
            		break;
            	}
            }
            else {//put item in current room
            	currentRoom.setItem(new Item(item));
            }
            System.out.println("Dropped: " + item);//informs user that item was dropped
            if (currentRoom == conservatory) {//dropping items in conservatory results in loss
            	System.out.println("You lose");
            	return true;
            }
            String knife = "knife";
            String pistol = "pistol";
            String computer = "computer";
            String robot = "robot";
            String candlestick = "candlestick";
            String keys = "keys";
            String wrench = "wrench";
            //Check if items were dropped in correct locations
            if (item.equals(knife) && currentRoom == workshop) {
            	count ++;//count successes
            	if (check == true) {
                	onetwenty.setClue(new Clue("This room is complete."));
                	workshop.setClue(new Clue("Thank you for finding my knife."));
                	System.out.println("Success: " + count + "/7");
            	}
            }
            else if (item.equals(pistol) && currentRoom == theatre) {
            	count ++;
            	if (check == true) {
            		office.setClue(new Clue("This room is complete."));
                	theatre.setClue(new Clue("Thank you for finding my pistol."));
                	System.out.println("Success: " + count + "/7");
            	}
            }
            else if (item.equals(computer) && currentRoom == study) {
            	count ++;
            	if (check == true) {
                	study.setClue(new Clue("Thank you for finding my computer."));
                	System.out.println("Success: " + count + "/7");
            	}
            }
            else if (item.equals(robot) && currentRoom == lab) {
            	count ++;
            	if (check == true) {
                	onetwenty.setClue(new Clue("This room is complete."));
                	lab.setClue(new Clue("Thank you for finding my robot."));
                	System.out.println("Success: " + count + "/7");
            	}
            }
            else if (item.equals(candlestick) && currentRoom == ballroom) {
            	count ++;
            	if (check == true) {
                	library.setClue(new Clue("This room is complete."));
                	ballroom.setClue(new Clue("Thank you for finding my candlestick."));
                	System.out.println("Success: " + count + "/7");
            	}
            }
            else if (item.equals(keys) && currentRoom == restaurant) {
            	count ++;
            	if (check == true) {
                	kitchen.setClue(new Clue("This room is complete."));
                	restaurant.setClue(new Clue("Thank you for finding my keys."));
                	System.out.println("Success: " + count + "/7");
            	}
            }
            else if (item.equals(wrench) && currentRoom == pub) {
            	count ++;
            	if (check == true) {
                	billiard.setClue(new Clue("This room is complete."));
                	pub.setClue(new Clue("Thank you for finding my wrench."));
                	System.out.println("Success: " + count + "/7");
            	}
            }
            if (count > 6) {//when all objects have been placed in the correct location you win
            	System.out.println("You saved the town from descending into evitable chaos through entropy!");
            	return true;//quits
            }
        }
        return false;
    }
    
    private void getItem(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to get...
            System.out.println("Get what?");
            return;
        }

        String item = command.getSecondWord();

        // Try to get item in current room.
        Item newItem = currentRoom.getItem(item);

        if (newItem == null)
            System.out.println("That item isn't here!");
        else {
            inventory.add(newItem);
            currentRoom.removeItem(item);
            System.out.println("Picked up: " + item);
        }
    }
    
    //Gets the clue for the room
    private void Speak(Command command)
    {
		System.out.println(currentRoom.getSpeech());
    }

    // implementations of user commands:

    private void printInventory() {
		// TODO Auto-generated method stub
		String output = "";
		for (int i = 0; i < inventory.size(); i++) {
			output += inventory.get(i).getDescription() + " ";
		}
		System.out.println("You are carrying:");
		System.out.println(output);
	}

	/**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander around");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to go to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null)
            System.out.println("There is no door!");
        else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game. Return true, if this command
     * quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else
            return true;  // signal that we want to quit
    }
}
