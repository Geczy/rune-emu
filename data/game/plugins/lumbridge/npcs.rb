require 'java'

java_import 'net.scapeemulator.game.dialogue.HeadAnimation'
java_import 'net.scapeemulator.game.dialogue.DialogueContext'

# Build the man and women dialogue
RuneEmulator::Utilities.build_dialogue(:lumbridge_man_woman) { |builder|

	# Append the start of the dialogue
	builder.append_start { |stage|

		# On initialization send the player with a greeting
		stage.on_init { |context|
			context.open_player_conversation_dialogue([ "Salutations!", "Hello there.", "Greetings." ].sample, HeadAnimation::HAPPY, true)
		}

		# On input just send the dialogue to the next stage
		stage.on_option { |context|
			context.set_stage('response')
		}
	}

	# Append the response of the dialogue	
	builder.append_stage('response') { |stage|

		# On initialization send the player the response text
		stage.on_init { |context|
			context.open_npc_conversation_dialogue("Sorry, I prefer not to talk to strangers.", DialogueContext::CURRENT_TARGET, HeadAnimation::HAPPY, true)
		}

		# Stop the dialogue on any sort of input
		stage.on_option { |context|
			context.stop
		}
	}
}