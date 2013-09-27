=begin
* Name: mining.rb
* A mining plugin for the scapeemulator (or rune-emu) source
* Author: Davidi2 (David Insley)
* Date: September 26, 2013
=end

require 'java'

java_import 'net.scapeemulator.game.model.object.GroundObjectListenerAdapter'
java_import 'net.scapeemulator.game.task.Task'
java_import 'net.scapeemulator.game.task.DistancedAction'
java_import 'net.scapeemulator.game.model.player.Equipment'

#<IDS_ARRAY, RockType>
ROCK_TYPES =  {}

#<ID, Pickaxe>
PICKAXES = {}

#<Position, Rock>
ROCKS = {}
=begin

case 2092:
case 2093:
case 31071:
case 31072:
case 31073:
case 37307:
case 37308:
case 37309:
p.getSkills().getMining().startMining(RockType.IRON);
break;
case 2100:
case 2101:
p.getSkills().getMining().startMining(RockType.SILVER);
break;
case 11183:
case 11184:
case 11185:
case 2098:
case 2099:
case 31066:
case 31065:
case 37310:
case 37312:
p.getSkills().getMining().startMining(RockType.GOLD);
break;
case 2096:
case 2097:
case 31068:
case 31069:
case 31070:
case 11930:
case 11932:
p.getSkills().getMining().startMining(RockType.COAL);
break;
case 2102:
case 2103:
case 31086:
case 31087:
case 31088:
case 11942:
case 11944:
p.getSkills().getMining().startMining(RockType.MITHRIL);
break;
case 2104:
case 2105:
case 31085:
case 31084:
case 31083:
case 11941:
case 11939:
p.getSkills().getMining().startMining(RockType.ADAMANT);
break;
case 2106:
case 2107:
case 14859:
case 14860:
case 14861:
p.getSkills().getMining().startMining(RockType.RUNITE);
break;
=end
#Object IDs for each of the RockTypes, still need to find a lot
CLAY_ROCKS =  [ 31062, 31063 ]
RUNE_ESSENCE_ROCKS = []
COPPER_ROCKS = [ 2090, 2091, 2110, 11189, 11190, 11191, 31080, 31081, 31082, 11936, 11937, 11938 ]
TIN_ROCKS = [ 2094, 2311, 37304, 37305, 37306, 11186, 11187, 11188, 2095, 31077, 31078, 31079, 11933, 11934, 11935 ]
IRON_ROCKS = [31071, 31072, 31073]
SILVER_ROCKS = [ 31074, 31075, 31076 ]
COAL_ROCKS = [ 31068, 31069, 31070]
GOLD_ROCKS = [ 31065, 31066, 31067]
MITHRIL_ROCKS = [ 31086, 31087, 31088]
ADAMANTITE_ROCKS = [ 31083, 31084, 31085 ]
RUNITE_ROCKS = []

#These are rocks that are in the world already depleted, rather than have them go unhandled this way they will say they have been depleted.
DEPLETED_ROCKS = [450, 452]

module RuneEmulator
  class Mining
    class << self
      def bind_handlers
        Bootstrap.bind_object_option(:one) { |player, object, option, context|
          if option.eql?("mine")
            if ROCKS.include?(object.position)
              player.start_action(MiningAction.new(player, ROCKS[object.position]))
            context.stop
            end
          end
        }
      end

      def create_rock_types
        #lvl, xp, ore id, respawn time (seconds)
        ROCK_TYPES[CLAY_ROCKS] = RockType.new(1, 5, 434, 2)
        ROCK_TYPES[COPPER_ROCKS] = RockType.new(1, 17.5, 436, 3)
        ROCK_TYPES[TIN_ROCKS] = RockType.new(1, 17.5, 438, 3)
        ROCK_TYPES[IRON_ROCKS] = RockType.new(15, 35, 440, 7)
        ROCK_TYPES[SILVER_ROCKS] = RockType.new(20, 40, 442, 80)
        ROCK_TYPES[COAL_ROCKS] = RockType.new(30, 50, 453, 40)
        ROCK_TYPES[GOLD_ROCKS] = RockType.new(40, 65, 444, 80)
        ROCK_TYPES[MITHRIL_ROCKS] = RockType.new(55, 80, 447, 120)
        ROCK_TYPES[ADAMANTITE_ROCKS] = RockType.new(70, 95, 449, 360)
        ROCK_TYPES[RUNITE_ROCKS] = RockType.new(85, 125, 451, 900)
      end

      def create_pickaxes
        PICKAXES[1265] = Pickaxe.new(1, Animation.new(625), 1) # Bronze
        PICKAXES[1267] = Pickaxe.new(1, Animation.new(626), 2) # Iron
        PICKAXES[1269] = Pickaxe.new(6, Animation.new(627), 3) # Steel
        PICKAXES[1273] = Pickaxe.new(21, Animation.new(629), 5) # Mithril
        PICKAXES[1271] = Pickaxe.new(31, Animation.new(628), 6) # Adamant
        PICKAXES[1275] = Pickaxe.new(41, Animation.new(624), 7) # Runite
        PICKAXES[13661] = Pickaxe.new(41, Animation.new(10222), 8) # Inferno adze
      end

      def refresh
        listener = RockObjectListener.new
        OBJECT_LIST.fire_all_events(listener)
        OBJECT_LIST.add_listener(listener)
      end

    end

    class Rock
      attr_reader :rock_type, :object, :depleted
      DEPLETED_IDS = {}

      def initialize(object, rock_type, depleted=false)
        @object = object
        @original_id = object.id
        @rock_type = rock_type
        @depleted = depleted
        if DEPLETED_IDS.key?(@original_id)
          @depleted_id = DEPLETED_IDS[@original_id]
        else
          DEPLETED_IDS[@original_id] = find_depleted_id
          @depleted_id = DEPLETED_IDS[@original_id]
        end
      end

      #mine the ore, turn into depleted rock
      def mine
        @object.id = @depleted_id
        TASK_SCHEDULER.schedule(RespawnTask.new(self))
        @depleted = true
      end

      #respawn ore, called by the respawn task
      def respawn
        @object.id = @original_id
        @depleted = false
      end

      def find_depleted_id
        if @original_id >= 2090 && @original_id <= 2109
        return (@original_id % 2 == 0) ? 450 : 452
        elsif @original_id >= 31062 && @original_id <= 31088
        return 31059 + (@original_id % 3)
        end
        return 450
      end
    end

    class MiningAction < DistancedAction
      def initialize(player, rock)
        super(1, true, player, rock.object.position, 1)
        @player = player
        @rock = rock
      end

      def get_mining_lvl
        return @player.get_skill_set.get_current_level(Skill::MINING)
      end

      #use either the currently equipped pickaxe if we have the requirement, or the best in the inventory. is the equipped one supposed to overrule the inventory?
      def find_pickaxe
        weapon = @player.get_equipment.get(Equipment::WEAPON)
        if !weapon.nil? && PICKAXES.include?(weapon.id) && get_mining_lvl >= PICKAXES[weapon.id].level
          return PICKAXES[weapon.id]
        else
          best_pickaxe = nil
          PICKAXES.each do |id, pickaxe|
            next if(!best_pickaxe.nil? && PICKAXES[id].speed < best_pickaxe.speed)
            (best_pickaxe = pickaxe) if (@player.get_inventory.slot_of(id) > -1 && get_mining_lvl >= pickaxe.level)
          end
        return best_pickaxe
        end
      end

      def should_mine
        #calculations and shit here
        return true if rand(5) > 2
        return false
      end

      def executeAction
        if @rock.depleted
          @player.send_message("There is currently no ore available in this rock.")
          stop
        return
        end

        if !@player.not_walking || !@player.get_walking_queue.is_empty
        return
        end

        if !@turned
          @player.turn_to_position(@rock.object.get_center_position)
          @turned = true
        end

        if get_mining_lvl < @rock.rock_type.lvl_req
          @player.send_message("You need a Mining level of #{@rock.rock_type.lvl_req} to mine this ore.")
          stop
        return
        end

        @pickaxe = find_pickaxe
        if @pickaxe.nil?
          @player.send_message("You do not have a pickaxe that you have the Mining level to use.")
          stop
        return
        end

        if @player.get_inventory.free_slots < 1
          @player.send_message("Your inventory is too full to hold any more ore.")
          stop
        return
        end

        if !@started
          @player.send_message("You swing your pickaxe at the rock.")
          @player.play_animation(@pickaxe.animation)
          @started = true
        return
        end

        @player.play_animation(@pickaxe.animation)
        if should_mine
          item = Item.new(@rock.rock_type.ore_id)
          @player.get_inventory.add(item)
          @player.send_message("You manage to mine some #{item.get_definition.name.downcase}.")
          @player.get_skill_set.add_experience(Skill::MINING, @rock.rock_type.xp)
          @rock.mine
          @player.play_animation(CANCEL_ANIMATION)
          stop
        end
      end

    end

    class RespawnTask < Task
      def initialize(rock)
        super(rock.rock_type.respawn_delay, false)
        @rock = rock
      end

      def execute
        @rock.respawn
        stop
      end
    end

    class RockObjectListener < GroundObjectListenerAdapter
      def groundObjectAdded(object)
        if DEPLETED_ROCKS.include?object.id
          ROCKS[object.position] = Rock.new(object, nil, true)
        return
        end
        ROCK_TYPES.each do |ids, rock_type|
          if ids.include?object.id
            ROCKS[object.position] = Rock.new(object, rock_type)
          break
          end
        end
      end
    end

    class RockType
      attr_reader :lvl_req, :xp, :ore_id, :respawn_delay
      def initialize(lvl_req, xp, ore_id, respawn_delay)
        @lvl_req = lvl_req
        @xp = xp
        @ore_id = ore_id
        @respawn_delay = respawn_delay
        @respawn_delay *= 5.0/3.0
      end
    end

    class Pickaxe
      attr_reader :level, :animation, :speed
      def initialize(level, animation, speed)
        @level = level
        @animation = animation
        @speed = speed
      end
    end
  end
end

RuneEmulator::Mining.create_rock_types
RuneEmulator::Mining.create_pickaxes
RuneEmulator::Mining.bind_handlers
RuneEmulator::Mining.refresh