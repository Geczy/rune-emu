require 'java'

java_import 'net.scapeemulator.game.model.object.GroundObjectListenerAdapter'
java_import 'net.scapeemulator.game.task.Task'
java_import 'net.scapeemulator.game.task.DistancedAction'
java_import 'net.scapeemulator.game.model.player.Equipment'

=begin
* (1341, Tree stump),
(1342, Tree stump),
(1343, Tree stump), orange?
(1344, Tree stump), 1349 but shorter (still 2x2)
(1345, Tree stump), 1348 but smaller (still 2x2)
(1346, Tree stump), HUGE AS FUCK?
(1347, Tree stump), 1348 but larger (still 2x2)
(1348, Tree stump), 1349 but darker
(1349, Tree stump), medium, light, chips, 2x2
(1350, Tree stump), small, dark brown, chips, 2x2
(1351, Tree stump), same as 1353 but slightly lighter
(1352, Tree stump), small, green same as 1353
(1353, Tree stump), small, dark brown, no chips, 1x1
(1354, Tree stump), white(lol wtf?) (2x2)
(1355, Tree stump), medium, chips (3x3)
(1356, Tree stump), medium, tan, thin, no chips (3x3)
(1357, Tree stump), medium, brown, light middle, chips (3x3)
(1358, Tree stump), small, brown, light middle, no chips (1x1)
(1359, Tree stump), small, black, no chips (1x1)

1278 = 2x2
1281 = 4x4
*/
=end

#<Identifier, TreeType>
TREE_TYPES =  {}

#<ID, Hatchet>
HATCHETS = {}

#<Position, Tree>
TREES = {}

#Object IDs for each of the TreeTypes
NORMAL_TREES =  [ 1276, 1277, 1278, 1279, 1280, 1282, 1283, 1284, 1285, 1285,
  1286, 1289, 1290, 1291, 1315, 1316, 1318, 1330, 1331, 1332,
  1365, 1383, 1384, 2409, 3033, 3034, 3035, 3036, 3881, 3882,
  3883, 5902, 5903, 5904, 10041
]
ACHEY_TREES = [2023]
OAK_TREES = [ 1281, 3037 ]
WILLOW_TREES = [5551, 5552, 5553]
TEAK_TREES = [9036]
MAPLE_TREES = [1307, 4674]
MAHOGANY_TREES = [9034]
ARCTIC_PINE_TREES = []
EUCALYPTUS_TREES = []
YEW_TREES = [1309]
MAGIC_TREES = [1292, 1306]

module RuneEmulator
  class Woodcutting
    class << self
      def bind_handlers()
        Bootstrap.bind_object_option(:one) { |player, object, option, context|
          if option.eql?("chop down")
            if TREES.include?(object.position)
              player.start_action(WoodcuttingAction.new(player, TREES[object.position]))
            context.stop
            end
          end
        }
      end

      def create_tree_types()
        #lvl, xp, log id, max logs, respawn time, stump id
        TREE_TYPES[:normal] = TreeType.new(1, 25, 1511, 1, 3, 1342)
        TREE_TYPES[:achey]= TreeType.new(1, 25, 2862, 1, 10, 3371)
        TREE_TYPES[:oak] = TreeType.new(15, 37.5, 1521, 6, 10, 1342)
        TREE_TYPES[:willow] = TreeType.new(30, 67.5, 1519, 10, 20, 1342)
        TREE_TYPES[:teak] = TreeType.new(35, 85, 6333, 10, 30, 1342)
        TREE_TYPES[:maple] = TreeType.new(45, 100, 1517, 10, 35, 1342)
        TREE_TYPES[:mahogany] = TreeType.new(50, 125, 6332, 10, 45, 1342)
        TREE_TYPES[:arctic_pine] = TreeType.new(54, 140, 10810, 10, 45, 1342)
        TREE_TYPES[:eucalyptus] = TreeType.new(58, 165, 12581, 10, 45, 1342)
        TREE_TYPES[:yew] = TreeType.new(60, 175, 1515, 15, 60, 1342)
        TREE_TYPES[:magic] = TreeType.new(75, 250, 1513, 20, 120, 1342)
      end

      def create_hatchets()
        HATCHETS[1351] = Hatchet.new(1, Animation.new(879), 1) # Bronze
        HATCHETS[1349] = Hatchet.new(1, Animation.new(877), 2) # Iron
        HATCHETS[1353] = Hatchet.new(6, Animation.new(875), 3) # Steel
        HATCHETS[1361] = Hatchet.new(6, Animation.new(873), 4) # Black
        HATCHETS[1355] = Hatchet.new(21, Animation.new(871), 5) # Mithril
        HATCHETS[1357] = Hatchet.new(31, Animation.new(869), 6) # Adamant
        HATCHETS[1359] = Hatchet.new(41, Animation.new(867), 7) # Runite
        HATCHETS[6739] = Hatchet.new(61, Animation.new(2846), 8) # Dragon
        HATCHETS[13661] = Hatchet.new(61, Animation.new(10251), 9) # Inferno adze
      end

      def refresh()
        OBJECT_LIST.fire_all_events(TreeObjectListener.new)
      end

    end

    class Tree
      attr_accessor :tree_type, :object, :logs_left
      def initialize(object, tree_type)
        @object = object
        @original_id = object.id
        @tree_type = tree_type
        @logs_left = 1 + rand(@tree_type.log_amount)
      end

      #remove one log, called any time a player successfully chops
      def chop_a_log()
        @logs_left -= 1
        if @logs_left < 1
          timber()
        end
      end

      #regrow tree, called by the regrow task
      def regrow()
        @object.setId(@original_id)
        @logs_left = 1 + rand(@tree_type.log_amount)
        p @logs_left
      end

      #TIMBERRRRR! Out of logs, turn into stump and schedule regrow task
      def timber()
        @object.setId(@tree_type.stump_id)
        TASK_SCHEDULER.schedule(RegrowTask.new(self))
      end
    end

    class WoodcuttingAction < DistancedAction
      def initialize(player, tree)
        super(1, true, player, tree.object.position, 3)
        @player = player
        @tree = tree
      end

      def get_wc_lvl
        return @player.getSkillSet().getCurrentLevel(Skill::WOODCUTTING)
      end

      #use either the currently equipped hatchet if we have the requirement, or the best in the inventory. is the equipped one supposed to overrule the inventory?
      def find_hatchet
        weapon = @player.getEquipment.get(Equipment::WEAPON)
        if !weapon.nil? && HATCHETS.include?(weapon.getId) && get_wc_lvl() >= HATCHETS[weapon.getId].level
          return HATCHETS[weapon.getId]
        else
          best_hatchet = nil
          HATCHETS.each { |id, hatchet|
            next if(!best_hatchet.nil? && HATCHETS[id].speed < best_hatchet.speed)
            (best_hatchet = hatchet) if (@player.getInventory.slotOf(id) > -1 && get_wc_lvl() >= hatchet.level)
          }
        return best_hatchet
        end
      end

      def get_next_log
        #calculations and shit here
        @next_log = rand(2) + 2
      end

      def executeAction
        if @tree.logs_left < 1
          @player.sendMessage("This tree has run out of logs.")
          stop()
        return
        end

        if !@player.notWalking() || !@player.getWalkingQueue().isEmpty()
        return
        end

        if !@turned
          @player.turnToPosition(@tree.object.getCenterPosition())
          @turned = true
        end

        if get_wc_lvl() < @tree.tree_type.lvl_req
          @player.sendMessage("You need a Woodcutting level of #{@tree.tree_type.lvl_req} to cut this tree.")
          stop()
        return
        end

        @hatchet = find_hatchet
        if @hatchet.nil?
          @player.sendMessage("You do not have an axe that you have the Woodcutting level to use.")
          stop()
        return
        end

        if @player.getInventory().freeSlots() < 1
          @player.sendMessage("Your inventory is too full to hold any more logs.")
          stop()
        return
        end

        if !@started
          @player.sendMessage("You swing your axe at the tree.")
          @player.playAnimation(@hatchet.animation)
          @started = true
          get_next_log()
        #return
        end

        @player.playAnimation(@hatchet.animation)
        @next_log -= 1
        if @next_log < 1
          @player.getInventory().add(Item.new(@tree.tree_type.log_id))
          @player.sendMessage("You get some logs.")
          @player.getSkillSet().addExperience(Skill::WOODCUTTING, @tree.tree_type.xp)
          @tree.chop_a_log
          if @tree.logs_left < 1
            @player.sendMessage("This tree has run out of logs.")
            @player.playAnimation(CANCEL_ANIMATION)
            stop()
          return
          end
          get_next_log()
        end
      end

    end

    class RegrowTask < Task
      def initialize(tree)
        super(tree.tree_type.respawn_delay, false)
        @tree = tree
      end

      def execute()
        @tree.regrow()
        stop()
      end
    end

    class TreeObjectListener < GroundObjectListenerAdapter
      def groundObjectAdded(object)
        if NORMAL_TREES.include?(object.id)
          TREES[object.position] = Tree.new(object, TREE_TYPES[:normal])
        elsif ACHEY_TREES.include?(object.id)
          TREES[object.position] = Tree.new(object, TREE_TYPES[:achey])
        elsif OAK_TREES.include?(object.id)
          TREES[object.position] = Tree.new(object, TREE_TYPES[:oak])
        elsif WILLOW_TREES.include?(object.id)
          TREES[object.position] = Tree.new(object, TREE_TYPES[:willow])
        elsif TEAK_TREES.include?(object.id)
          TREES[object.position] = Tree.new(object, TREE_TYPES[:teak])
        elsif MAPLE_TREES.include?(object.id)
          TREES[object.position] = Tree.new(object, TREE_TYPES[:maple])
        elsif MAHOGANY_TREES.include?(object.id)
          TREES[object.position] = Tree.new(object, TREE_TYPES[:mahogany])
        elsif ARCTIC_PINE_TREES.include?(object.id)
          TREES[object.position] = Tree.new(object, TREE_TYPES[:arctic_pine])
        elsif EUCALYPTUS_TREES.include?(object.id)
          TREES[object.position] = Tree.new(object, TREE_TYPES[:eucalyptus])
        elsif YEW_TREES.include?(object.id)
          TREES[object.position] = Tree.new(object, TREE_TYPES[:yew])
        elsif MAGIC_TREES.include?(object.id)
          TREES[object.position] = Tree.new(object, TREE_TYPES[:magic])
        end
      end
    end

    class TreeType
      attr_accessor :lvl_req, :xp, :log_id, :log_amount, :respawn_delay, :stump_id
      def initialize(lvl_req, xp, log_id, log_amount, respawn_delay, stump_id)
        @lvl_req = lvl_req
        @xp = xp
        @log_id = log_id
        @log_amount = log_amount
        @respawn_delay = respawn_delay
        @respawn_delay *= 5.0/3.0
        @stump_id = stump_id
      end
    end

    class Hatchet
      attr_accessor :level, :animation, :speed
      def initialize(level, animation, speed)
        @level = level
        @animation = animation
        @speed = speed
      end
    end
  end
end

RuneEmulator::Woodcutting.create_tree_types()
RuneEmulator::Woodcutting.create_hatchets()
RuneEmulator::Woodcutting.bind_handlers()
RuneEmulator::Woodcutting.refresh()