import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class RandomArt {

    public static void main(String[] args) {
        
        ArtFrame f = new ArtFrame();
        f.start();
    }
} // end of RandomArt class

class ArtFrame extends JFrame {
   
	private RandomExpression exp;
    private ArtPanel thePanel;
    private JLabel theCurrentExpression;

    public ArtFrame(){
        setTitle("Assignment 4 - Random Art");
        setLocation(100,100);
     //   setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // add the menu for Colored Art options
        setJMenuBar(createMenu());

        // create panel with buttons
        thePanel = new ArtPanel();
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(getButton("New Gray", false));
        buttonPanel.add(getButton("New Color", true));

        // create the label with the current random expression
        theCurrentExpression = new JLabel(thePanel.getExpressionAsString());
        theCurrentExpression.setFont(new Font("Serif", Font.PLAIN, 16));


        // add components to frame
        add(thePanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        add(theCurrentExpression, BorderLayout.NORTH);
        pack();
    }

    // create menu options for which method is used
    // to create colored random art. Students must implemement
    // at least two different ways of generating a random
    // art image with color.
    private JMenuBar createMenu() {
        final int NUM_OPTIONS = ArtPanel.NUM_COLOR_OPTIONS;
        JMenuBar menu = new JMenuBar();
        final String label = "Color Option";
        final JMenu colorOptions = new JMenu(label);
        menu.add(colorOptions);
        // add the options. Must connect listeners for when these
        // options are selected. When activated the menu option
        // will update the panel with the new color option.
        for(int i = 0; i < NUM_OPTIONS; i++) {
            final JMenuItem temp = colorOptions.add(label + " " + (i + 1));
            temp.addActionListener(new ActionListener() {
           	 
                public void actionPerformed(ActionEvent e)
                {
                	int color = 0;
                	if (colorOptions.getItem(0) == temp)
                		color = 2;
                	else
                		color = 1;
                	thePanel.newExpression(color);
                	theCurrentExpression.setText(thePanel.getExpressionAsString());
                    thePanel.repaint();
                }
            });
        }
        return menu;        
    }

    public JButton getButton(String label, final boolean makeColor){
        JButton result = new JButton(label);
        result.addActionListener(new ActionListener() {
        	 
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
            	int color = 0;
            	if (!makeColor)
            		color = 0;
            	else
            	{
            		if (thePanel.color != 0)
            			color = thePanel.color;
            		else
            			color = 2;
            	}
            	thePanel.newExpression(color);
            	theCurrentExpression.setText(thePanel.getExpressionAsString());
                thePanel.repaint();
            }
        });  
        
        return result;
    }

    public void start(){
        setVisible(true);
    }
} // end of ArtFrame class

class ArtPanel extends JPanel   {
	
    public static final int SIZE = 500;
    public static final int NUM_COLOR_OPTIONS = 2;
    double xValue = -1;
    double yValue = -1;
    double value = 0;
    int color = 0;
    
    private RandomExpression exp;
    private RandomExpression exp1;
    private RandomExpression exp2;
   

    public ArtPanel(){ 
        setPreferredSize(new Dimension(SIZE, SIZE));
        
        // samples of interesting grayscale formulas
        // xCySM
        // yCCSxxMSSAS, interesting 4 deep formula
        // yxMSSCS, another interesting 4 deep formula
        // yCSSSxxACySyyAAACM, yet another interesting 4 deep
        // xxMCxyMSASS one more good 4 deep
        // xCxSAxCSMSSSQSCyCSSSCMS
        // xCySMS
        
        // default expression
        exp = new RandomExpression("xxACSSxCAyCyxASASCAyCCAyyyAAxMSxCxCAxSySMMCMCSMSCS");
        exp1 = new RandomExpression();
        exp2 = new RandomExpression();
    }
    
    

    public String getExpressionAsString() {
    	String result = "";
    	if (color == 0)
    		result =  "Gray Scale Equation: " + exp.toString();
        if(color == 1)
    		result =  "<html><body style=\"white-space:nowrap\">Color Equation: " 
        + exp.toString() + "</body></html>";
        if (color == 2)
    		result =  "<html><body style=\"white-space:nowrap\">Color Equation 1: " 
    		        + exp.toString() + "<br/>" +
    		        		"Color Equation 2: " 
    		        + exp1.toString() + "<br/>" +
    		        		"Color Equation 3: " 
    		        + exp2.toString() + "</body></html>";
        
        return result;
    }

    public void newExpression(int type)
    {
    	if (type ==1)
    		exp = new RandomExpression(9, .9);
    	else if (type == 2)
    		exp = new RandomExpression(7, .65);
    	else
        	exp = new RandomExpression();
    	exp1 = new RandomExpression(9, .9);
    	exp2 = new RandomExpression(9, .9);
    	color = type;
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        
       if (color == 0)
        	makeGrey(g2);
       if (color == 1)
    	   makeColor1(g2);
        if (color == 2)
        	makeColor2(g2);
    }
    
    public void makeGrey(Graphics g2)
    {
    	xValue = -1;
    	yValue = -1;
        double width = getWidth();
        double height = getHeight();
        double wIncrement = 1/(width/2);
        double hIncrement = 1/(height/2);
        int col = 0;
	    for (int y = 0; y <= getHeight(); y++)
        	{
        		for (int x = 0; x <= getWidth(); x++)
        		{
        			value = exp.getResult(xValue, yValue);
        			col = (int) ((value*127.5) + 127.5);
        			Color c = new Color(col,col,col);
        			g2.setColor(c);
        			g2.fillRect(x,y,1,1);
        			xValue += wIncrement;
        		}
        		xValue = -1;
    			yValue += hIncrement;
        	}
    }
    
    public void makeColor1(Graphics g2)
    {
    		xValue = -1;
    		yValue = -1;
	        double width = getWidth();
	        double height = getHeight();
	        double wIncrement = 1/(width/2);
	        double hIncrement = 1/(height/2);
	        int col = 0;
		    for (int y = 0; y <= getHeight(); y++)
	        	{
	        		for (int x = 0; x <= getWidth(); x++)
	        		{
	        			Color c;
	        			value = exp.getResult(xValue, yValue);
	        			col = (int) ((value*8388608) + 8388608);
	        			c = new Color(col);
	        			g2.setColor(c);
	        			g2.fillRect(x,y,1,1);
	        			xValue += wIncrement;
	        		}
	        		xValue = -1;
	    			yValue += hIncrement;
	        	}
    }
    
    public void makeColor2(Graphics g2)
    {		
    		xValue = -1;
    		yValue = -1;
	        double width = getWidth();
	        double height = getHeight();
	        double wIncrement = 1/(width/2);
	        double hIncrement = 1/(height/2);
		    for (int y = 0; y <= getHeight(); y++)
        	{
        		for (int x = 0; x <= getWidth(); x++)
        		{
        			Color c;
        			value = exp.getResult(xValue, yValue);
        			int col = (int) ((value*127.5) + 127.5);
        			value = exp1.getResult(xValue, yValue);
        			int col1 = (int) ((value*127.5) + 127.5);
        			value = exp2.getResult(xValue, yValue);
        			int col2 = (int) ((value*127.5) + 127.5);

        			c = new Color(col, col1, col2, 240);
        			g2.setColor(c);
        			g2.fillRect(x,y,1,1);
        			xValue += wIncrement;
        		}
        		xValue = -1;
    			yValue += hIncrement;
        	}
		    yValue = -1;
		    xValue = -1;
    }


} // end of ArtPanel class

class RandomExpression {
    // each char represents a possible operator;
    // S = sin, C = cos, A = average, M = multiply
    // NOTE!! If operator is added must update
    // SINGLE_OPERAND_OPERATORS constant!
    
    private static final String OPERATORS = "SSSSCCCCAMR";
    
    // functions that take a single operand
    private static final String SINGLE_OPERAND_OPERATORS = "SCR";

    private static final String OPERANDS = "xy";
    
    // probability that operand will be another expression
    // instead of a primitive
    private final double PROBABILITY_DEEPER;


    // String representation of expression. Shown is postfix
    // notation to make for easier evaluation
    private final String randExpression;

    // higher number means more complex
    // lowest allowed value = 0
    private final int EXPRESSION_COMPLEXITY; 

    private static final int DEFAULT_MAX_COMPLEXITY = 10;
    private static final double DEFAULT_PROBABILITY_USE_OPERATOR_FOR_OPERAND = 0.8;

    // create a new Random Expression
    // with probabilityDeeper = 0.8
    // and expressionComplexity = 10
    public RandomExpression(){
        this(DEFAULT_MAX_COMPLEXITY, DEFAULT_PROBABILITY_USE_OPERATOR_FOR_OPERAND);
    }

    // pre: complexity >= 0, 0 <= deeper <= 1.0
    // higher values for complexity and deeper lead to
    // more complex expresions
    public RandomExpression(int complexity, double deeper){
        EXPRESSION_COMPLEXITY = complexity;
        PROBABILITY_DEEPER = deeper;
        randExpression = createExpression(0);
    }

    // a way to create a hard coded expression
    public RandomExpression(String s){
        EXPRESSION_COMPLEXITY = -1;
        PROBABILITY_DEEPER = -1;        
        randExpression = s;
    }


    private String createExpression(int currentLevel){
        int op = (int)(Math.random() * OPERATORS.length());
        int oper1 = (int)(Math.random() * 2);
        int oper2 = (int)(Math.random() * 2);
        String result = OPERATORS.substring(op, op + 1);
        boolean deeperFirstOperand = Math.random() < PROBABILITY_DEEPER;
        boolean deeperSecondOperand = Math.random() < PROBABILITY_DEEPER;

        // single operand operators
        if(isSingleOperandOperator(result)){
            // base case, operands are simple values, x or y
            if(!deeperFirstOperand || currentLevel == EXPRESSION_COMPLEXITY) {
                result = OPERANDS.charAt(oper1) +  result;
            }
            // recursive case, operand is another expression
            else{
                result = createExpression(currentLevel + 1) +  result;
            }
        }
        else{
            // base case, operands are simple values, x or y
            if(currentLevel == EXPRESSION_COMPLEXITY || (!deeperFirstOperand && !deeperSecondOperand)){
                result = OPERANDS.charAt(oper1) + "" + OPERANDS.charAt(oper2) + result;
            }
            // first opearnd is simple value, second is another expression
            else if(!deeperFirstOperand){
                result = OPERANDS.charAt(oper1) +  createExpression(currentLevel + 1) + result;
            }
            // second opearnd is simple value, first is another expression
            else if(!deeperSecondOperand){
                result = createExpression(currentLevel + 1) + OPERANDS.charAt(oper2) + result;
            }
            // both operands are complex expressions
            else{
                result = createExpression(currentLevel + 1) + createExpression(currentLevel + 1) + result;
            }
        }
        return result;
    }

    private boolean isSingleOperandOperator(String operator) {
        return SINGLE_OPERAND_OPERATORS.contains(operator);
    }

    // called to get result of expression at a given
    // value of x and y.
    // pre: -1.0 <= x <= 1.0, -1.0 <= y <= 1.0, 
    // post: return a value between -1.0 and 1.0, inclusive
    public double getResult(double x, double y){
        Stack<Double> operands = new Stack<Double>();
        for(int i = 0; i < randExpression.length(); i++){
            char ch = randExpression.charAt(i);
            if(ch == 'x')
                operands.push(x);
            else if(ch == 'y')
                operands.push(y);
            else
            {
                // operator
                double op1 = operands.pop();
                if(ch == 'S')
                    operands.push(Math.sin(Math.PI * op1));
                else if(ch == 'C')
                    operands.push(Math.cos(Math.PI * op1));
                else if(ch == 'M')
                    operands.push(op1 * operands.pop());
                else if (ch == 'A')
                    operands.push(ave(op1, operands.pop()));
                else if (ch == 'R')
                	operands.push(sqrt(op1));
                
                // CS324e Students: Add your operator here!!!
            }
        }
        assert operands.size() == 1 : operands.size();
        double result = operands.pop();
        result = (result < -1.0) ? -1.0 : (result > 1.0) ? 1.0 : result;
        assert -1.0 <= result && result <= 1.0 : result;
        return result;
    }

    private static double ave(double x, double y){
        return (x + y) / 2.0;
    }
    
    private static double sqrt(double x)
    {
    	return Math.sqrt(Math.abs(x));
    }
    
    public String toString(){
        return randExpression;
    }

    // from random art, test method
    public static double getValExp(double x, double y){
        return Math.sin(Math.PI * Math.sin(Math.PI * Math.sin(Math.PI * (Math.sin(Math.PI * Math.sin(Math.PI * Math.sin(Math.PI * Math.sin(Math.PI * Math.cos(Math.PI * y))))) * Math.cos(Math.PI * Math.sin(Math.PI * Math.cos(Math.PI * ave(Math.sin(Math.PI * y), (x * x)))))))));
    }

    // simple by hand test
    public static double getValueHardCoded(double x, double y){
        double pi = Math.PI;
        return Math.sin(pi * Math.cos(pi * Math.cos(pi * Math.sin(pi * ave(Math.cos(pi * y),y) * Math.sin(pi * x * y )))));
    } 
} // end of RandomExpression class

//yCCSxxMSSAS, interesting 4 deep formula
//yxMSSCS, another interesting 4 deep formula
//yCSSSxxACySyyAAACM, yet another interesting 4 deep
//xxMCxyMSASS one more good 4 deep
