require 'java'

java_import 'net.scapeemulator.game.model.player.Interface'
java_import 'net.scapeemulator.game.model.player.Skill'

LEVEL_UP_WINDOW       = 741
SKILL_INFO_WINDOW     = 499
AMOUNT_CHILD_BUTTONS  = 15
CHILD_START_BUTTON    = 10

FLASHING_ICON_VARBIT  = 4729
MAIN_INFO_VARBIT      = 3288
CHILD_INFO_VARBIT     = 3289

SKILL_BUTTONS = { Skill::ATTACK => 125, Skill::STRENGTH => 126, Skill::DEFENCE => 127, Skill::RANGED => 128,
                  Skill::PRAYER => 129, Skill::MAGIC => 130, Skill::RUNECRAFTING => 131, Skill::CONSTRUCTION => 132,
                  Skill::HITPOINTS => 133, Skill::AGILITY => 134, Skill::HERBLORE => 135, Skill::THIEVING => 136,
                  Skill::CRAFTING => 137, Skill::FLETCHING => 138, Skill::SLAYER => 139, Skill::HUNTER => 140,
                  Skill::MINING => 141, Skill::SMITHING => 142, Skill::FISHING => 143, Skill::COOKING => 144,
                  Skill::FIREMAKING => 145, Skill::WOODCUTTING => 146, Skill::FARMING => 147, Skill::SUMMONING => 148
                }

module RuneEmulator
	class SkillMenu
		class << self
			def open_window(player, skill_id)
				if player.state_set.is_bit_state_active(Skill::getFlashingIcon(skill_id))	

					# Set the skill that we are alerting that was leveled up	
					player.state_set.set_bit_state(FLASHING_ICON_VARBIT, Skill::getConfigValue(skill_id))

					# Stop the skill icon from flashing
					player.state_set.set_bit_state(Skill::getFlashingIcon(skill_id), false)
					
					# Open up the level up window
					player.interface_set.open_window(LEVEL_UP_WINDOW)
				else

					# Set the skill info child config
					player.state_set.set_bit_state(CHILD_INFO_VARBIT, 0)

					# Set the skill that we will be viewing the menu for	
					player.state_set.set_bit_state(MAIN_INFO_VARBIT, Skill::getConfigValue(skill_id))

					# Open the window for the skill menu
					player.interface_set.open_window(SKILL_INFO_WINDOW)
				end
			end
		end
	end
end

for i in (0...AMOUNT_CHILD_BUTTONS)
	RuneEmulator::Bootstrap.bind_button(SKILL_INFO_WINDOW, CHILD_START_BUTTON + i) { |player, child, param|
		player.state_set.set_bit_state(CHILD_INFO_VARBIT, child - CHILD_START_BUTTON)
	}
end

for i in (0...Skill::AMOUNT_SKILLS)
	RuneEmulator::Bootstrap.bind_button(Interface::SKILLS, SKILL_BUTTONS[i]) { |player, child, param|
		RuneEmulator::SkillMenu.open_window(player, SKILL_BUTTONS.key(child))
	}
end