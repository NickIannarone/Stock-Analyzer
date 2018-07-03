import java.util.ArrayList;

public class MovingRatio
{
  private ArrayList<Double> values;
  private int period;                             
  
  public MovingRatio(Instrument stocks, int left, int right)
  {  
    int i = left, numPos = 0;
    
    while (i < right)
    {
      if (stocks.get(i).getMovingDifference().getValue() > 0)
        numPos++;
        
      i++;
    }
    
    value = (double)numPos/(right - left);
  }
  
  //Repeating code! I'd say to use an interface here, but MovingRatio's rely on MovingDifference's
  public double getValue()
  {
    return Math.round(value * 100.0) / 100.0;
  }
  
  public String toString()
  {
    return "" + value;
  }
}