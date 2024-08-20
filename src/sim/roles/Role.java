package sim.roles;

public abstract class Role {
  public Role(String userName, int identifier, int hp, int maxHp, double defense, double attack) {
    super();
    setUserName(userName);
    setIdentifier(identifier);
    setMaxHp(maxHp);
    setHp(hp);
    setDefense(defense);
    setAttack(attack);
  }

  private String userName;
  private int identifier;

  // health points
  private int hp;
  private int maxHp;

  // defense refers to the multiplier applied to damage taken.
  private double defense;
  // attack refers to the amount of damage a basic melee will inflict.
  private double attack;

  // whether or not the player is currently staggered
  private boolean staggered = false;

  @Override
  public String toString() {
    return getClass().getSimpleName() + " " + getUserName() + "\tHP: " + getHp() + "/" + getMaxHp() + "\t"
        + "\tStaggered: " + getStaggered() + "    \tAttack: " + attack + "\tDefense: " + defense;
  }

  public boolean getStaggered() {
    return staggered;
  }

  public void setStaggered(boolean staggered) {
    this.staggered = staggered;
  }

  public int getIdentifier() {
    return identifier;
  }

  public void setIdentifier(int identifier) {
    this.identifier = identifier;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public int getHp() {
    return hp;
  }

  public void setHp(int hp) {
    if (hp > maxHp)
      this.hp = maxHp;
    else if (hp < 0)
      this.hp = 0;
    else
      this.hp = hp;
  }

  public int getMaxHp() {
    return maxHp;
  }

  public void setMaxHp(int maxHp) {
    this.maxHp = maxHp;
  }

  public double getDefense() {
    return defense;
  }

  public void setDefense(double defense) {
    this.defense = defense;
  }

  public double getAttack() {
    return attack;
  }

  public void setAttack(double attack) {
    this.attack = attack;
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Role))
      return false;

    Role otherPlayer = (Role) obj;

    if (otherPlayer.accountId() == accountId())
      return true;

    return false;
  }

  public String accountId() {
    return userName + identifier;
  }

  public abstract void makeDecision(Role target);

  public abstract void attack(Role target, String specialName);

  // In a real game development context, I'd probably create a whole handler class
  // just for damage instead of pass through so many parameters...
  public boolean damage(double damage, String attacker, boolean trueDamage, String spellName, String skillName,
      boolean mpDeficiency) {
    // True damage ignores defense points.
    if (trueDamage || mpDeficiency) {
      hp -= damage;
      System.out.println(damage + " damage points were inflected "
          + (mpDeficiency ? "onto the caster due to mp deficiency. " : "onto the enemy. "));
    } else {
      hp -= damage / defense;
      System.out.println((damage / defense) + " damage points were inflected "
          + (mpDeficiency ? "onto the caster due to mp deficiency. " : "onto the enemy. "));
    }

    if (hp <= 0) {
      playerDeath(attacker, damage, spellName, skillName, mpDeficiency);
      // Returns true if the player dies as a result of the damage.
      return true;
    }

    return false;
  }

  public void playerDeath(String attacker, double damage, String spellName, String skillName, boolean mpDeficiency) {
    String message = "Player " + userName + " was killed by " + attacker + " after taking " + damage
        + " damage points ";

    if (mpDeficiency)
      message += "due to trying to use a spell beyond their capacity.";
    else if (spellName != null)
      message += "from the magic spell: " + spellName + '.';
    else if (skillName != null)
      message += "from the combat skill: " + skillName + '.';
    else
      message += "from a basic melee attack.";

    System.out.println(message);
  }
}
