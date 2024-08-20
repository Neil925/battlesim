package sim.interfaces;

public interface IDifficulty {
  double hardMultiplier = 1.6;
  double normalMultiplier = 1;
  double easyMultiplier = 0.8;

  double applyDifficultyMultiplier(double value);
}
