package sim.interfaces;

import sim.roles.*;

public interface IPlayer {
  int playerTurn = 0;

  void PlayerDecision(Role target);
}
