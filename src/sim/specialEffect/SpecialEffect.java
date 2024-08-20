package sim.specialEffect;

import sim.enums.EffectType;

public abstract class SpecialEffect {
  public SpecialEffect(String skillName, EffectType effectType, double effectMultiplier) {
    super();
    this.skillName = skillName;
    this.effectType = effectType;
    this.effectMultiplier = effectMultiplier;
  }

  private String skillName;
  private EffectType effectType;
  // In an actual Role Playing Game scope would refer to how many enemies the
  // attack targets at once. However, this program has no logic written for that
  // at this time.
  // private int scope;
  private double effectMultiplier;

  public String getSkillName() {
    return skillName;
  }

  public void setSkillName(String skillName) {
    this.skillName = skillName;
  }

  public EffectType getEffectType() {
    return effectType;
  }

  public void setEffectType(EffectType effectType) {
    this.effectType = effectType;
  }

  public double getEffectMultiplier() {
    return effectMultiplier;
  }

  public void setEffectMultiplier(double effectMultiplier) {
    this.effectMultiplier = effectMultiplier;
  }
}
