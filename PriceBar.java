/*
Nick Iannarone
V1: 06/23/17
Represents a single day's price bar (candlestick)
This version assumes the price bar is given as a String in the format: "Date-Month-Year, Open, High, Low, Close, Volume"
*/

import java.util.ArrayList;

public class PriceBar  
{
  private String date;
  private double open;
  private double high;
  private double low;
  private double close;
  private int volume;
  //private int numDate; //Numerical date so that the code can sort moving differences by date
  //private MovingDifference diff; //Initialized in Instrument if it fits the MovingDifference range. Linked to the newer stock used. Think of it as a measure of change.
  //private MovingRatio ratio;
  //private int streakCount; //Only will be used for a few PriceBars for finding the beginning of a steady rise or fall in price.
  
  //Splits input into substrings, omitting the commas, and assigns those to the fields
  public PriceBar(String input)
  {    
    ArrayList<String> stats = new ArrayList<String>();
    
    for (int i = 0; input.contains(","); i++)
    {
      stats.add(input.substring(0, input.indexOf(",")));
      input = input.substring(input.indexOf(",") + 1);
    }
    
    date = stats.get(0); 
    open = Double.parseDouble(stats.get(1));
    high = Double.parseDouble(stats.get(2));
    low = Double.parseDouble(stats.get(3));
    close = Double.parseDouble(stats.get(4)); 
    volume = Integer.parseInt(input);
    
    //Numerize the stock date for comparison (might wanna clean this up by moving it to another class)
    
    /*
    int d, m, y;
    String dateCopy = date;
    
    for (int i = stats.size(); dateCopy.contains("-"); i++)
    {
      stats.add(dateCopy.substring(0, dateCopy.indexOf("-")));
      dateCopy = dateCopy.replace(stats.get(i) + "-", "");
    }
    
    d = Integer.parseInt(stats.get(stats.size()-2));
    
    switch (stats.get(stats.size()-1))
    {
      case "Jan": m = 1;
                  break;
      case "Feb": m = 2;
                  break;
      case "Mar": m = 3;
                  break;
      case "Apr": m = 4;
                  break;
      case "May": m = 5;
                  break;
      case "Jun": m = 6;
                  break;      
      case "Jul": m = 7;
                  break;
      case "Aug": m = 8;
                  break;
      case "Sep": m = 9;
                  break;
      case "Oct": m = 10;
                  break;
      case "Nov": m = 11;
                  break;
      case "Dec": m = 12;
                  break; 
      default: System.out.println("Invalid date.");
               m = 0;
               break;
    }
        
    y = Integer.parseInt(dateCopy);
    
    numDate = d + (m * 30) + (y * 365);
    */
  }
  
  public double getOpenPrice()
  {
    return open;
  }
  
  public double getHighPrice()
  {
    return high;
  }
  
  public double getLowPrice()
  {
    return low;
  }
  
  public double getClosePrice()
  {
    return close;
  }
  
  public MovingDifference getMovingDifference()
  {
  /*
    if (diff == null)
    {
      System.out.println("This stock is outside the date range for MovingDifference's.");
      return diff;
    }
  */  
    return diff;
  }
  
  public MovingRatio getMovingRatio()
  {
  /*
    if (ratio == null)
    {
      System.out.println("This stock is outside the date range for MovingRatio's.");
      return ratio;
    }
 */  
    return ratio;
  }
  
  public String getDate()
  {
    return date;
  }

  public void setMovingDifference(MovingDifference md)
  {
    diff = md;
  }
  
  public void setMovingRatio(MovingRatio mr)
  {
    ratio = mr;
  }
  
  public void raiseStreakCount()
  {
    streakCount++;
  }
  
  public int getStreakCount()
  {
    return streakCount;
  }
  
  //For testing (might want to remove this later unless other classes will print PriceBar objects)
  public String toString()
  {
    return date + "  " + close + "  " + ratio;
  }
   
  //For testing  
  public static void main(String[] args)
  {
    PriceBar pb = new PriceBar("2-Jan-09,21.11,23.10,21.11,23.02,6673430");
    //System.out.println(pb.numDate);
  }
}