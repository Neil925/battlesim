package sim;

import sim.enums.AIMode;
import sim.enums.Difficulty;
import sim.enums.EffectType;
import sim.roles.*;
import sim.specialEffect.*;
import java.util.*;

public class GamePlayer {
  private static Scanner input = new Scanner(System.in);

  private static ArrayList<Skill> debugTestSkills = new ArrayList<Skill>() {
    private static final long serialVersionUID = 1L;

    {
      add(new Skill("Heavy Slash", EffectType.Attack, 1.5, 10));
      add(new Skill("Roar", EffectType.Stagger, 0, 5));
      add(new Skill("Gaurd", EffectType.Defense, 1.25, 5));
      add(new Skill("Tri-Strike", EffectType.Attack, 1.4, 3, 25));
      add(new Skill("Focus", EffectType.AttackBuff, 1.2, 15));
    }
  };

  private static ArrayList<Spell> debugTestSpells = new ArrayList<Spell>() {
    private static final long serialVersionUID = 1L;

    {
      add(new Spell("Thunder Spear", EffectType.Attack, 1.6, true, 20));
      add(new Spell("Water Jet", EffectType.Attack, 1.8, false, 20));
      add(new Spell("Healing", EffectType.Heal, 1.75, false, 15));
      add(new Spell("Shock", EffectType.Stagger, 0, false, 20));
      // Yeah, this attack essentially can't be cast without creating an overpowered
      // magic wielder.
      // It would be a pretty cool ace if I ever introduce a leveling system and gave
      // it only to a max leveled user.
      // add(new Spell("Esotric Art: I AM ATOMIC", EffectType.Attack, 1000, true,
      // 999));
    }
  };
  // These spells and skills should really be pre-defined in some other class with
  // a dictionary or something. But I've already done way too much for this
  // project by this point.

  /*
   * For the purposes of this demonstration, I'll have this program play against
   * itself with two separate characters by deciding on moves at random. I might
   * actually try to provide some user input in the future if I ever feel bored
   * again.
   */
  public static void startGame() {
    int count = 1;
    System.out.println("Creating warrior character.");
    Warrior warrior = new Warrior("Sword Master", 4598, 150, 150, 1.5, 15, debugTestSkills, 40, 40); // Plays on odd

    System.out.println("Creating wizzard character.");
    Wizard wizzard = new Wizard("MagicMan", 1098, 100, 100, 1.8, 3, debugTestSpells, 120, 120, 20); // Plays on even

    System.out.println(warrior.toString());
    System.out.println(wizzard.toString() + "\n");

    while (wizzard.getHp() > 0 && warrior.getHp() > 0) {
      if (count % 2 == 0) {
        warrior.makeDecision(wizzard);
        System.out.println("\n" + wizzard.toString() + "\n");
      } else {
        wizzard.makeDecision(warrior);
        System.out.println("\n" + warrior.toString() + "\n");
      }

      System.out.println("Turn " + count + " ended.");
      count++;
      System.out.println("Press enter to continue...");
      input.nextLine();
    }

    System.out.println("Game over.");
  }

  public static Wizard Cid_Kagenou = new Wizard("The Eminence in Shadow", 6969, Integer.MAX_VALUE,
      Integer.MAX_VALUE, Double.MAX_VALUE, Integer.MAX_VALUE, debugTestSpells, Integer.MAX_VALUE,
      Integer.MAX_VALUE, Integer.MAX_VALUE, Difficulty.Hard, AIMode.BestDecision);
}
