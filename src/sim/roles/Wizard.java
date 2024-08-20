package sim.roles;

import java.util.*;

import sim.enums.*;
import sim.interfaces.*;
import sim.specialEffect.*;

public class Wizard extends Role implements IAIMode, IDifficulty, IPlayer {
  public Wizard(String userName, int identifier, int hp, int maxHp, double defense, double attack,
      ArrayList<Spell> spells, int mp, int maxMp, double mAttack, Difficulty difficulty, AIMode aiMode) {
    super(userName, identifier, hp, maxHp, defense, attack);
    setDifficulty(difficulty);
    setAiMode(aiMode);
    setSpells(spells);
    setMaxMp(maxMp);
    setMp(mp);
    setmAttack(mAttack);
  }

  public Wizard(String userName, int identifier, int hp, int maxHp, double defense, double attack,
      ArrayList<Spell> spells, int mp, int maxMp, double mAttack) {
    super(userName, identifier, hp, maxHp, defense, attack);
    setDifficulty();
    setAiMode();
    setSpells(spells);
    setMaxMp(maxMp);
    setMp(mp);
    setmAttack(mAttack);
  }

  private ArrayList<Spell> spells = new ArrayList<Spell>();

  public ArrayList<Spell> getSpells() {
    return spells;
  }

  public void setSpells(ArrayList<Spell> spells) {
    this.spells = spells;
  }

  private AIMode aiMode;

  public AIMode getAiMode() {
    return aiMode;
  }

  public void setAiMode(AIMode aiMode) {
    this.aiMode = aiMode;
  }

  public void setAiMode() {
    System.out.print("Options are: ");
    for (AIMode a : AIMode.values())
      System.out.print(a.toString() + ", ");

    System.out.print("\nPlease select the roles AI mode: ");

    AIMode aiMode = null;

    while (aiMode == null) {
      try {
        aiMode = AIMode.valueOf(input.nextLine());
      } catch (Exception error) {
        System.out.println("Something went wrong. Try again.");
      }
    }

    this.aiMode = aiMode;
  }

  // magic points; cast every time a spell is cast.
  private int mp;
  private int maxMp;

  // Base attack points for magic spells only.
  private double mAttack;

  private Difficulty difficulty;

  public Difficulty getDifficulty() {
    return difficulty;
  }

  public void setDifficulty(Difficulty difficulty) {
    this.difficulty = difficulty;
  }

  public void setDifficulty() {
    System.out.print("Options are: ");
    for (Difficulty d : Difficulty.values())
      System.out.print(d.toString() + ", ");

    System.out.print("\nPlease select the enemy difficulty: ");

    Difficulty difficulty = null;

    while (difficulty == null) {
      try {
        difficulty = Difficulty.valueOf(input.nextLine());
      } catch (Exception error) {
        System.out.println("Something went wrong. Try again.");
      }
    }

    this.difficulty = difficulty;
  }

  private static Random rand = new Random();
  private static Scanner input = new Scanner(System.in);

  public double getmAttack() {
    return mAttack;
  }

  public void setmAttack(double mAttack) {
    this.mAttack = mAttack;
  }

  public int getMp() {
    return mp;
  }

  public void setMp(int mp) {
    if (mp > getMaxMp())
      this.mp = getMaxMp();
    else if (mp < 0)
      this.mp = 0;
    else
      this.mp = mp;
  }

  public int getMaxMp() {
    return maxMp;
  }

  public void setMaxMp(int maxMp) {
    this.maxMp = maxMp;
  }

  @Override
  public String toString() {
    return super.toString() + "\tMP: " + getMp() + "/" + getMaxMp();
  }

  // Returns true if the attack succeeded in killing the target.
  @Override
  public void attack(Role target, String specialName) {
    if (getStaggered()) {
      setStaggered(false);
      System.out.println(
          getUserName() + " was staggered so he couldn't attack! The stagger however has been broken.");
      return;
    }

    if (specialName == null) {
      target.damage(applyDifficultyMultiplier(this.getAttack()), this.getUserName(), false, null, null, false);
      return;
    }

    Optional<Spell> spell = spells.stream().filter(x -> x.getSkillName() == specialName).findFirst();

    if (spell.isEmpty()) {
      System.out.println("Could not find that spell!");
      return;
    }

    EffectType type = spell.get().getEffectType();
    double effectMultiplier = spell.get().getEffectMultiplier();
    boolean trueDamage = spell.get().getTrueDamage();
    int cost = spell.get().getMpCost();

    // Magic casters die when trying to execute magic way beyond their capacity.
    // Otherwise, their health will serve as a substitute.
    if (cost > getMp() && this.damage(cost - getMp(), getUserName(), true, null, null, true)) {
      System.out.println(getUserName() + " died before they were able to cast their spell.");
      return;
    }

    setMp(getMp() - cost);

    switch (type) {
      case Attack:
        target.damage(applyDifficultyMultiplier(getmAttack() * effectMultiplier), this.getUserName(), trueDamage,
            specialName, null, false);
        break;
      case AttackBuff:
        target.setAttack(target.getAttack() * effectMultiplier);
        System.out.println("Attack has been increased to " + (target.getAttack()) + ". ");
        break;
      case Defense:
        target.setDefense(target.getDefense() * effectMultiplier);
        System.out.println("Defense has been increased to " + (target.getDefense()) + ". ");
        break;
      case Heal:
        // Healing is equal to the product of the cost of the spell and the
        // effectMultiplier
        target.setHp((int) (target.getHp() + (cost * effectMultiplier)));
        System.out.println((cost * effectMultiplier) + " health has been given to " + target.getUserName() + ". ");
        break;
      case Stagger:
        target.setStaggered(true);
        break;
      default:
        System.out.println("Invalid enum!");
        break;
    }
  }

  public Spell getRandomSpell() {
    return getSpells().get(rand.nextInt(getSpells().size()));
  }

  @Override
  public double applyDifficultyMultiplier(double value) {
    switch (difficulty) {
      case Hard:
        return value * hardMultiplier;
      case Easy:
        return value * easyMultiplier;
      case Normal:
        return value * normalMultiplier;
      default:
        return value;
    }
  }

  @Override
  public void PlayerDecision(Role target) {
    System.out.print("Choose your action (attack, spell, nothing): ");

    String action = input.next();

    while (!(action.contains("attack") || action.contains("spell") || action.contains("nothing"))) {
      System.out.println("Invalid. Please input an action.");
      action = input.nextLine();
    }

    if (action.contains("nothing")) {
      System.out.println(this.getUserName() + " has decided to do nothing and watch the situation.");
      this.setStaggered(false);
      return;
    }

    if (action.contains("attack")) {
      System.out.println(this.getUserName() + " is doing a melee attack. ");
      this.attack(target, null);
      return;
    }

    if (!action.contains("spell"))
      return;

    System.out.println("Here are your spells: ");
    for (Spell s : this.spells)
      System.out.print(s.getSkillName() + ", ");
    System.out.println("Please select which spell you'd like to use.");

    Spell spell = null;

    do {
      try {
        input.nextLine();
        String userSpellInput = input.nextLine().toLowerCase();

        spell = this.spells.stream().filter(x -> x.getSkillName().toLowerCase().contains(userSpellInput)).findFirst()
            .orElseGet(null);
      } catch (Exception error) {
        System.out.println("Invalid spell! Please try again!");
        input.nextLine();
      }
    } while (spell == null);

    System.out.println(this.getUserName() + " is using the spell: " + spell.getSkillName() + ". ");

    switch (spell.getEffectType()) {
      case Attack:
      case Stagger:
        this.attack(target, spell.getSkillName());
        break;
      case AttackBuff:
      case Defense:
      case Heal:
        this.attack(this, spell.getSkillName());
        break;
      default:
        break;
    }
  }

  @Override
  public void RandomDecision(Role target) {
    int action = rand.nextInt(70);

    if (action == 0) {
      System.out.println(this.getUserName() + " has decided to do nothing and watch the situation." + "\n");
      this.setStaggered(false);
      return;
    }

    if (action > 50) {
      System.out.println(this.getUserName() + " is doing a melee attack. ");
      this.attack(target, null);
      System.out.println("\n");
      return;
    }

    Spell spell = this.getRandomSpell();
    System.out.println(this.getUserName() + " is using the spell: " + spell.getSkillName() + ". ");

    switch (spell.getEffectType()) {
      case Attack:
      case Stagger:
        this.attack(target, spell.getSkillName());
        break;
      case AttackBuff:
      case Defense:
      case Heal:
        this.attack(this, spell.getSkillName());
        break;
      default:
        break;
    }
  }

  @Override
  public void BestDecision(Role target) {
    if (this.getStaggered()) {
      System.out.println(this.getUserName() + " has decided to do nothing and watch the situation." + "\n");
      this.setStaggered(false);
      return;
    }

    int lowestCost = this.spells.stream().min((a, b) -> a.getMpCost() - b.getMpCost()).get().getMpCost();

    Optional<Spell> healing = this.spells.stream().filter(x -> x.getEffectType() == EffectType.Heal).findFirst();

    if (this.getHp() <= this.getMaxHp() / 2 && healing.isPresent() && healing.get().getMpCost() < this.mp) {
      System.out.println(this.getUserName() + " is using the spell: " + healing.get().getSkillName() + ". ");
      this.attack(target, healing.get().getSkillName());
      return;
    }

    if (this.getHp() >= this.getMaxHp() / 2
        || (this.getHp() > lowestCost && target.getHp() <= target.getMaxHp() / 2)) {
      Spell spell;

      // Too lazy to do this more efficiently.
      do {
        spell = this.getRandomSpell();
      } while (spell.getMpCost() > this.mp && spell.getEffectType() == EffectType.Heal);

      System.out.println(this.getUserName() + " is using the spell: " + spell.getSkillName() + ". ");

      switch (spell.getEffectType()) {
        case Attack:
        case Stagger:
          this.attack(target, spell.getSkillName());
          break;
        case AttackBuff:
        case Defense:
        case Heal:
          this.attack(this, spell.getSkillName());
          break;
        default:
          break;
      }

      System.out.println("\n");
      return;
    }

    System.out.println(this.getUserName() + " is doing a melee attack. ");
    this.attack(target, null);
  }

  @Override
  public void makeDecision(Role target) {
    switch (aiMode) {
      case BestDecision:
        BestDecision(target);
        break;
      case Player:
        PlayerDecision(target);
        break;
      case Random:
        RandomDecision(target);
        break;
      default:
        break;
    }
  }
}
