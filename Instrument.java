/*
Container class for PriceBar objects. Represents a single company.
*/

import java.util.ArrayList;

public class Instrument extends ArrayList<PriceBar>
{
  private String name;
  //private boolean MovingDifferenceSet = false; //To ensure that MovingDifference are set before SetMovingRatio is called
  //private boolean MovingRatioSet = false; //To ensure that MovingDifferncess are set before FindStreaks is called
  //private Instrument streaks;
  //private int movingDifferenceRange;
  
  public Instrument(String name) //, int movingDifferenceRange)
  {
    this.name = name;   
    //this.movingDifferenceRange = movingDifferenceRange;
  }
  
  //***Redo in MovingDifference abstraction model and replace w/ dependency injection
  public void setMovingDifference()
  {
    if (size() < 21)
    {
      System.out.println("There must be at least 21 stocks in this instrument to calculate MovingDifference's.");
      return;
    }
    
    int numOldS = 0, numNewS = 20;
    
    while (numNewS < size())
    {
      get(numNewS).setMovingDifference(new MovingDifference(get(numOldS), get(numNewS))); //Replace w/ Dependency Injection
      numOldS++;
      numNewS++;
    }
    
    MovingDifferenceSet = true;
  }
  
  //My thoughts: Assign the ratio to each PriceBar that can have one, then choose
  //clusters (they'll automatically be sorted by date) with the highest and lowest MovingRatio's.  
  //Then, find the beginning of each cluster and use DecisionPoints to find our Signal.
  //  
  public void setMovingRatio(int windowSize)  
  {
    if (MovingDifferenceSet == false)
      setMovingDifference();
      
    if (windowSize < 20)
    {
      System.out.println("MovingRatio window size is too small.");
      return;
    }
      
    //These ints are the bounds of the window over which each MovingRatio is computed. The window moves until it hits the instrument's edge.    
    int leftBound = 20;
    int rightBound = 20 + windowSize;  
  
    while (rightBound < size())
    {                                          
      get(leftBound).setMovingRatio(new MovingRatio(this, leftBound, rightBound));
      leftBound++;
      rightBound++;
    }  
    
    MovingRatioSet = true;
  }
  
  //Returns a list of the first PriceBar in the longest MovingRatio = 1 streak and the longest MovingRatio = 0 streak.
  //This returns a list in case more streaks will be added later on (more than just 1 rise, 1 fall).
  public Instrument findStreaks()
  {
    if (MovingRatioSet == false)
      setMovingRatio(50);
      
    streaks = new Instrument(name + "'s Streaks", this.movingDifferenceRange);
    
    int j = 0;
        
    for (int i = 0; i < size(); i++)
    {      
      try{
      
      if (get(i).getMovingRatio().getValue() == 1.0 && get(i-1).getMovingRatio().getValue() != 1.0) //If this might be the first in a MovingRatio = 1 streak
      {
        streaks.add(get(i));
        
        while (get(i+j).getMovingRatio().getValue() == 1.0 && i < size())
        {
          get(i).raiseStreakCount();
          j++;
        }
          
        j = 0;
      }
      
      }
      catch (NullPointerException e)
      {
        System.out.println(i);
      }
    }
    
    for (int i = 0; i < size(); i++)
    {
      try{ 
      
      if (get(i).getMovingRatio().getValue() == 0.0 && get(i-1).getMovingRatio().getValue() != 0.0) //If this might be the first in a MovingRatio = 0 streak
      {
        streaks.add(get(i));
        
        while (get(i+j).getMovingRatio().getValue() == 0.0 && i < size())
        {
          get(i).raiseStreakCount();
          j++;
        }
          
        j = 0;
      }
      }
      
      catch (NullPointerException e)
      {
        System.out.println(i);
        System.out.println(streaks.size());
      }
    }
    
    PriceBar bullMax = streaks.get(0);
    PriceBar bearMax = streaks.get(0);
    
    for (int i = 1; i < streaks.size(); i++) //In order to find the first dates in the longest rise and fall streaks
    {
      PriceBar pb = streaks.get(i); //One way to replace long strings of get method calls (will apply later throughout project)
      
      if (pb.getMovingRatio().getValue() == 1.0 && pb.getStreakCount() > bullMax.getStreakCount())
        bullMax = pb;
        
      if (pb.getMovingRatio().getValue() == 0.0 && pb.getStreakCount() > bearMax.getStreakCount())
        bearMax = pb;  
    }
    
    int k = 0;
    
    while (k < streaks.size()) //Filter out every streak starting date but those that precede the longest streaks.
    {
      if (!streaks.get(k).getDate().equals(bullMax.getDate()) && !streaks.get(k).getDate().equals(bearMax.getDate())) 
      {
        streaks.remove(k);
      }     
      else
      {
        k++;
      }
    }
    
    return streaks;
  }
  
  //I am no longer using this method, but it could be used to track severity in MovingDifference's.
  public void sortStocksByMovingDifference()
  {
    //Argh! Repeating code, no!
    if (size() < 21)
    {
      System.out.println("There must be at least 21 stocks in this instrument to calculate MovingDifference's.");
      return;
    }
    
    //Selection Sort
    for (int i = 0; i < size() - 1; i++)
    {
      int index = i;
        
      for (int j = i + 1; j < size(); j++)       //Oh, dear. This is GET-ting ugly!
        if (!get(j).getMovingDifference().equals(null) && get(j).getMovingDifference().getValue() < get(index).getMovingDifference().getValue())
          index = j;
    }
  }
  
  //For use in sorting
  public void swap(PriceBar p1, PriceBar p2)
  {
    PriceBar temp = p1;
    set(indexOf(p1), p2);
    set(indexOf(p2), temp);
  } 
  
  public String toString()
  {
    return "This represents the historical stock prices for " + name + ": " + super.toString();
  } 
}