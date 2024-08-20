package sim.specialEffect;

import sim.enums.EffectType;

public class Spell extends SpecialEffect {
  public Spell(String skillName, EffectType effectType, double effectMultiplier, boolean trueDamage, int mpCost) {
    super(skillName, effectType, effectMultiplier);
    setTrueDamage(trueDamage);
    setMpCost(mpCost);
  }

  // Whether or not the attack ignores defense
  private boolean trueDamage;

  // How much MP it costs to cast the spell.
  private int mpCost;

  public int getMpCost() {
    return mpCost;
  }

  public void setMpCost(int mpCost) {
    this.mpCost = mpCost;
  }

  public boolean getTrueDamage() {
    return trueDamage;
  }

  public void setTrueDamage(boolean trueDamage) {
    this.trueDamage = trueDamage;
  }
}
