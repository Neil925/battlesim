package sim.specialEffect;

import sim.enums.EffectType;

public class Skill extends SpecialEffect {
  public Skill(String skillName, EffectType effectType, double effectMultiplier, int repeats, int staminaCost) {
    super(skillName, effectType, effectMultiplier);
    setRepeats(repeats);
    setStaminaCost(staminaCost);
  }

  public Skill(String skillName, EffectType effectType, double effectMultiplier, int staminaCost) {
    super(skillName, effectType, effectMultiplier);
    setRepeats(1);
    setStaminaCost(staminaCost);
  }

  // How many times the same skill is repeated in one attack.
  private int repeats;

  // How much stamina is costs to use this skill.
  private int staminaCost;

  public int getStaminaCost() {
    return staminaCost;
  }

  public void setStaminaCost(int staminaCost) {
    this.staminaCost = staminaCost;
  }

  public int getRepeats() {
    return repeats;
  }

  public void setRepeats(int repeats) {
    this.repeats = repeats;
  }
}
