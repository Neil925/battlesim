# Basic Java Battle Sim
The following is an RPG style battle simulator AI modes (best decision and random) as well as an option for players. Whether you choose to watch two AIs go at it or want to party it out with a friend, this program is mad for you!

> Note: In lue of the developments of modern AI, I want to make clear that the "Artificial Intelligence" in this case is a basic algorithm and not driven by a neural network.

## Starting
1. Two characters are created to battle in this game, for each:
2. A difficulty is selected where "Easy" gives the character more advantage and "Hard" a disadvantage.
3. Next, the means for operating the character will be asked.
  - Random: The character will take random actions.
  - BestDecision: The character will automatically choose an action that best fits the situation as decided by a simple algorithm.
  - Player: Input will be taken as the characters actions.

## Gameplay
### Basic Explanation
As with any battle simulator, the goal is to defeat your enemy by dropping their HP or health points to 0. The characters are offered the following actions: attack, spells/skills, and doing nothing. Attacks are more effective for a sword master, however a wizard's spells are more versatile than a sword master's skills.

Within the gameplay, the sword master has "skills" while the wizard has "spells". The primary difference between the two is that spells use "MP" or "magic points" to execute while skills use "SP" or "skill points". MP can be substituted with HP when deficient. *Do be warned that the overuse of spells can result in a self inflected game over!*.

As for other parameters, all characters have an attack and defense stat. These stats affect the amount of damage they deal and how tolerant they are to damage respectively. As well as that, characters can be stunned. This may occur as a result of certain spells/skills used during the gameplay.

### Spells and Skills
The following is a basic break down of the skills and spells.

| **Skills**                                                                         | **Spells**                                                                                   |
| ---------------------------------------------------------------------------------- | -------------------------------------------------------------------------------------------- |
| **Thunder Spear**: Decent damage with chance of shock.                             | **Heavy Slash**: Attack with increased damage.                                               |
| **Water Jet**: Decent damage with lower MP cost.                                   | **Roar**: Chance at paralyzing the enemy for a turn.                                         |
| **Healing**: Restores health to a target.                                          | **Guard**: Increases defense.                                                                |
| **Tri-Strike**: An attack with very heavy damage and high SP cost.                 | **Shock**: Guarnteed paralysis of the enemy.                                                 |
| **Focus**: Increases the attack potential.                                     |                                                                                                  |

Thank you for reading and I hope you enjoy my game!
