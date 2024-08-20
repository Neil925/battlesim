package sim.interfaces;

import sim.roles.*;

public interface IAIMode {
  int aiTurn = 1;

  void RandomDecision(Role target);

  void BestDecision(Role target);
}
