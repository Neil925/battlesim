package sim.roles;

import java.util.*;

import sim.enums.*;
import sim.specialEffect.*;
import sim.interfaces.*;

public class Warrior extends Role implements IAIMode, IDifficulty, IPlayer {
  public Warrior(String userName, int identifier, int hp, int maxHp, double defense, double attack,
      ArrayList<Skill> skills, int stamina, int maxStamina, Difficulty difficulty, AIMode aiMode) {
    super(userName, identifier, hp, maxHp, defense, attack);
    setDifficulty(difficulty);
    setAiMode(aiMode);
    setSkills(skills);
    setMaxStamina(maxStamina);
    setStamina(stamina);
  }

  public Warrior(String userName, int identifier, int hp, int maxHp, double defense, double attack,
      ArrayList<Skill> skills, int stamina, int maxStamina) {
    super(userName, identifier, hp, maxHp, defense, attack);
    setDifficulty();
    setAiMode();
    setSkills(skills);
    setMaxStamina(maxStamina);
    setStamina(stamina);
  }

  /*
   * I'd love to sit here and create fields and classes for armor and weaponry,
   * but frankly that would take forever and the uml would be a pain. So I'll have
   * to save that for when I decide to make an actual game for fun.
   */

  private ArrayList<Skill> skills = new ArrayList<Skill>();

  // Stamina is used every time a skill is cast.
  private int stamina;
  private int maxStamina;

  private Difficulty difficulty;

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

    System.out.print("\nPlease select the role's AI mode: ");

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

  @Override
  public String toString() {
    return super.toString() + "\t" + "Stamina: " + stamina + "/" + maxStamina;
  }

  private static Random rand = new Random();
  private static Scanner input = new Scanner(System.in);

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

  public int getStamina() {
    return stamina;
  }

  public void setStamina(int stamina) {
    if (stamina > getMaxStamina())
      this.stamina = getMaxStamina();
    else if (stamina < 0)
      this.stamina = 0;
    else
      this.stamina = stamina;
  }

  public int getMaxStamina() {
    return maxStamina;
  }

  public void setMaxStamina(int maxStamina) {
    this.maxStamina = maxStamina;
  }

  public ArrayList<Skill> getSkills() {
    return skills;
  }

  public void setSkills(ArrayList<Skill> skills) {
    this.skills = skills;
  }

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

    Optional<Skill> skill = skills.stream().filter(x -> x.getSkillName() == specialName).findFirst();

    if (skill.isEmpty()) {
      System.out.println("Could not find that special attack!");
      return;
    }

    EffectType type = skill.get().getEffectType();
    double effectMultiplier = skill.get().getEffectMultiplier();
    int repeats = skill.get().getRepeats();
    int cost = skill.get().getStaminaCost();

    // Warriors will not die or take damage if they try to execute a skill beyond
    // their ability.
    // Instead, they'll just lose their remaining stamina and get staggered.
    if (cost > getStamina()) {
      setStamina(0);
      setStaggered(true);
      System.out.println(getUserName() + " was not able to execute the skill " + specialName);
      return;
    }

    setStamina(getStamina() - cost);

    switch (type) {
      case Attack:
        target.damage(applyDifficultyMultiplier((getAttack() * effectMultiplier) * repeats), this.getUserName(),
            false, null, specialName, false);
        break;
      case AttackBuff:
        setAttack(getAttack() * effectMultiplier);
        System.out.println("Attack has been increased to " + (getAttack()) + ". ");
        break;
      case Defense:
        setDefense(getDefense() * effectMultiplier);
        System.out.println("Defense has been increased to " + (getDefense()) + ". ");
        break;
      case Heal:
        System.out.println("Healing skills should not exist!");
        break;
      case Stagger:
        target.setStaggered(true);
        break;
      default:
        System.out.println("Invalid enum!");
        break;
    }
  }

  public Skill getRandomSkill() {
    return getSkills().get(rand.nextInt(getSkills().size()));
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

  // For players only
  @Override
  public void PlayerDecision(Role target) {
    System.out.print("Choose your action (attack, skill, nothing): ");

    String action = input.nextLine();

    while (!(action.contains("attack") || action.contains("skill") || action.contains("nothing"))) {
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

    if (!action.contains("skill"))
      return;

    System.out.println("Here are your skills: ");
    for (Skill s : this.skills)
      System.out.print(s.getSkillName() + ", ");
    System.out.println("Please select which skill you'd like to use.");

    Skill skill = null;

    do {
      try {
        String userSkillInput = input.nextLine().toLowerCase();

        skill = this.skills.stream().filter(x -> x.getSkillName().toLowerCase().contains(userSkillInput)).findFirst()
            .orElseGet(null);
      } catch (Exception error) {
        System.out.println("Invalid skill! Please try again!");
        input.nextLine();
      }
    } while (skill == null);

    System.out.println(this.getUserName() + " is using the skill: " + skill.getSkillName() + ". ");

    switch (skill.getEffectType()) {
      case Attack:
      case Stagger:
        this.attack(target, skill.getSkillName());
        break;
      case AttackBuff:
      case Defense:
      case Heal:
        this.attack(this, skill.getSkillName());
        break;
      default:
        break;
    }
  }

  // For AI only
  @Override
  public void RandomDecision(Role target) {
    int action = rand.nextInt(101);

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

    Skill skill = this.getRandomSkill();
    System.out.println(this.getUserName() + " is using the skill: " + skill.getSkillName() + ". ");

    switch (skill.getEffectType()) {
      case Attack:
      case Stagger:
        this.attack(target, skill.getSkillName());
        break;
      case AttackBuff:
      case Defense:
      case Heal:
        this.attack(this, skill.getSkillName());
        break;
      default:
        break;
    }
  }

  // For AI only
  @Override
  public void BestDecision(Role target) {
    if (this.getStaggered()) {
      System.out.println(this.getUserName() + " has decided to do nothing and watch the situation." + "\n");
      this.setStaggered(false);
      return;
    }

    int lowestCost = this.skills.stream().min((a, b) -> a.getStaminaCost() - b.getStaminaCost()).get()
        .getStaminaCost();

    if (this.getStamina() >= this.getMaxStamina() / 2
        || (this.getStamina() > lowestCost && target.getHp() <= target.getMaxHp() / 2)) {
      Skill skill;

      // Too lazy to do this more efficiently.
      do {
        skill = this.getRandomSkill();
      } while (skill.getStaminaCost() > this.stamina);

      System.out.println(this.getUserName() + " is using the skill: " + skill.getSkillName() + ". ");

      switch (skill.getEffectType()) {
        case Attack:
        case Stagger:
          this.attack(target, skill.getSkillName());
          break;
        case AttackBuff:
        case Defense:
        case Heal:
          this.attack(this, skill.getSkillName());
          break;
        default:
          break;
      }

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
