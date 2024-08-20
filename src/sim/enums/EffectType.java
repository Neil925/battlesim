package sim.enums;

//Note that only spells can target other players with a positive effect. 
//Skills can only target players with a negative effect or the invoker with a positive effect.

public enum EffectType {
  // Causes an attack to the targeted player
  Attack,
  // Momentarily disables the targeted player
  Stagger,
  // Increases the player's attack multiplier
  AttackBuff,
  // Increases the player's defense multiplier
  Defense,
  // Casts healing on a targeted player
  Heal,
}
