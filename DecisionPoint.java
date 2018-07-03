/*
06/29: Right now, I'm very close to point in the project. I just have to finish isolating periods of interest (the beginning of large,
steady inclines or declines in stock price). Examples of DecisionPoints will include peaks/inflection in MA's of various length and 
intersection of the MACD and the signal line.
*/

public interface DecisionPoint
{
  double operation();  //The math that calculates the statistic
}