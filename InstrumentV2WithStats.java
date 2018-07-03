//This project needs some heavy restructuring b/c of both dependency
//injection and Moving Difference and Ratio being made into base classes.
//I will rename this as Instrument once I'm able to replace that with this class.

import java.util.ArrayList;

//The stats, currently moving difference and ratio, are specifically meant to
//read past data (the PriceBar(s)) and find ideal trading points for analysis.
public class InstrumentV2WithStats extends InstrumentV2
{
  private ArrayList<MovingDifference> movingDifferences;
  private ArrayList<MovingRatio> movingRatios;
  private PriceType priceType;
  
  public InstrumentV2WithStats()
  {
    super();
  }
  
  @Inject
  public InstrumentV2WithStats(String name, ArrayList<MovingDifference> diffs, ArrayList<MovingRatio> ratios, PriceType priceType)
  {
    super(name);
    movingDifferences = diffs;
    movingRatios = ratios;  
    this.priceType = priceType;
  } 
  
  public void fillMovingDifference()
  {
    ArrayList<Double> prices = new ArrayList<Double>();
    
    switch (priceType) {
      case OPEN:
        for (PriceBar pb : this)
          prices.add(pb.getOpen());
        MovingDifference.setValues(prices);
        break;
        
      case HIGH:
        for (PriceBar pb : this)
          prices.add(pb.getHigh());
        MovingDifference.setValues(prices);
        break;  
      
      case LOW:
        for (PriceBar pb : this)
          prices.add(pb.getLow());
        MovingDifference.setValues(prices);
        break;
      
      case CLOSE:
        for (PriceBar pb : this)
          prices.add(pb.getClose());
        MovingDifference.setValues(prices);
        break;
        
      deafult:
        System.out.println("Invalid PriceType");
        break;
    }
  }
}