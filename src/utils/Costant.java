package utils;

public final class Costant {
	final public static int DIMGRIDX=3;
	final public static int DIMGRIDY=3;
	final public static int WINCOUNT=3;
	final public static float ALPHA=0.1f; //learning rate //set generally between 0 and 1
	final public static float EPSION=1f;
	final public static float EPSION_MIN=0.025f;
	final public static float DISCOUNT_FACTOR=0.88f;  
	//final public static float DELTA=0.03f;  
	final public static int MAXREP=200000;
	final public static String NAME_FILE="C:\\Users\\Mariangela\\tesiWorkspace\\tris\\qtableObject";
	final public static int NUM_PLAYERS=2;
	final public static float WIN_REWARD=1;
	final public static float LOSE_REWARD=-1f;
	final public static float EXPONENTIAL_DECAY=0.00001f;  //costante di decadimento esponenziale lambda-> formula e^(-t/lambda)

	final public static float DRAW_REWARD=0f;
	final public static String MARKPL2 ="O";
	final public static String MARKPL1 ="X";
	final public static boolean LOAD_QTABLE =false;
	final public static boolean TURN_MODE = true;
	
	final public static boolean VERSIONS_QTABLE_UPDATE = true;




}
