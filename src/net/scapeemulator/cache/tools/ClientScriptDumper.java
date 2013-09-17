package net.scapeemulator.cache.tools;

import java.io.File;
import java.io.FileOutputStream;
import net.scapeemulator.cache.Cache;
import net.scapeemulator.cache.Container;
import net.scapeemulator.cache.FileStore;
import net.scapeemulator.cache.ReferenceTable;
import net.scapeemulator.cache.ReferenceTable.Entry;
import net.scapeemulator.cache.def.ClientScript;

import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

public final class ClientScriptDumper {

    private static final Map<Integer, String> opcodes = new HashMap<>();

    static {
        opcodes.put(0, "pushi");
        opcodes.put(1, "pushi_cfg");
        opcodes.put(2, "popi_cfg");
        opcodes.put(3, "pushs");
        opcodes.put(6, "goto");
        opcodes.put(7, "if_ne");
        opcodes.put(8, "if_eq");
        opcodes.put(9, "if_lt");
        opcodes.put(10, "if_gt");
        opcodes.put(21, "return");
        opcodes.put(25, "pushi_varbit");
        opcodes.put(26, "popi_varbit");
        opcodes.put(31, "if_lteq");
        opcodes.put(32, "if_gteq");
        opcodes.put(33, "loadi");
        opcodes.put(34, "storei");
        opcodes.put(35, "loads");
        opcodes.put(36, "stores");
        opcodes.put(37, "concat_str");
        opcodes.put(38, "popi");
        opcodes.put(39, "pops");
        opcodes.put(40, "call");
        opcodes.put(42, "loadi_global");
        opcodes.put(43, "storei_global");
        opcodes.put(44, "dim");
        opcodes.put(45, "push_array");
        opcodes.put(46, "pop_array");
        opcodes.put(47, "loads_global");
        opcodes.put(48, "stores_global");
        opcodes.put(51, "switch");
        opcodes.put(100, "new_dyn_comp");  
        opcodes.put(101, "dlt_dyn_comp");
        opcodes.put(102, "dlt_dyn_comps");
        opcodes.put(200, "set_active_component");
        
        opcodes.put(1000, "set_dyn_comp_position");
        opcodes.put(1003, "set_dyn_comp_hidden");
        opcodes.put(1005, "set_dyn_comp_image");
        opcodes.put(1101, "set_dyn_comp_color");
        opcodes.put(1103, "set_dyn_comp_alpha");
        opcodes.put(1200, "display_item_on_dyn_comp");
        opcodes.put(1205, "display_item_on_dyn_comp2");
        opcodes.put(1300, "set_dyn_comp_option");
        opcodes.put(1301, "set_dyn_comp_parent");
        opcodes.put(1408, "set_dyn_comp_loop_script");
        
        opcodes.put(2000, "set_static_comp_position");
        opcodes.put(2003, "set_static_comp_hidden");
        opcodes.put(2005, "set_static_comp_image");
        opcodes.put(2101, "set_static_comp_color");
        opcodes.put(2103, "set_static_comp_alpha");
        opcodes.put(2200, "display_item_on_static_comp");
        opcodes.put(2205, "display_item_on_static_comp2");
        opcodes.put(2300, "set_static_comp_option");
        opcodes.put(2301, "set_static_comp_parent");
        opcodes.put(2408, "set_static_comp_loop_script");
        
        opcodes.put(2704, "is_opened");
        opcodes.put(2705, "is_opened");     // Client allows for two cases
        opcodes.put(3300, "pushi_lcycle");  
        opcodes.put(3301, "pushi_itemid");  
        opcodes.put(3302, "pushi_itemamt");  
        opcodes.put(3303, "pushi_amtincontainer");  
        opcodes.put(3304, "pushi_containersize");
        opcodes.put(3313, "pushi_temp_itemid");
        opcodes.put(3314, "pushi_temp_itemamt");
        opcodes.put(3315, "pushi_temp_amtincontainer");
        opcodes.put(3316, "pushi_rights");
        
        opcodes.put(3400, "pushs_cs2constant");
        opcodes.put(3408, "pusho_cs2constant");
        
        opcodes.put(4000, "iadd"); 
        opcodes.put(4001, "isub"); 
        opcodes.put(4002, "imult"); 
        opcodes.put(4003, "idiv"); 
        
        opcodes.put(4106, "pushs_i2str"); 
        
        opcodes.put(4200, "pushs_itemname"); 
    }

    public static void main(String[] args) throws IOException {
       
        PrintStream newStream = new PrintStream(new FileOutputStream(new File("./data/game/dumps/scripts")));
        System.setOut(newStream);
        
        try (Cache cache = new Cache(FileStore.open("./data/game/cache/"))) {
            ReferenceTable rt = ReferenceTable.decode(Container.decode(cache.getStore().read(255, 12)).getData());
            for (int id = 0; id < rt.capacity(); id++) {
                Entry entry = rt.getEntry(id);
                if (entry == null) {
                    continue;
                }

                ClientScript script = ClientScript.decode(cache.read(12, id).getData());
                System.out.println("===== script " + id + " ======");
                System.out.println("iargs: " + script.getIntStackDepth() + ", sargs: " + script.getStrStackDepth());
                for (int op = 0; op < script.getLength(); op++) {
                    int opcode = script.getOpcode(op);

                    String str = script.getStrOperand(op);
                    int val = script.getIntOperand(op);

                    String name = opcodes.get(opcode);
                    if (name == null) {
                        name = "opcode: " + opcode;
                    }

                    String param = str != null ? str : Integer.toString(val);
                    
                    if(name.equals("pushi")) {
                        
                        switch(val) {
                            case 0x80000001:
                                param = "mouse_x";
                                break;
                            case 0x80000002:
                                param = "mouse_y";
                                break;
                            case 0x80000003:
                                param = "component_hash";
                                break;
                            case 0x80000004:
                                param = "option_id";
                                break;
                            case 0x80000005:
                                param = "component_dyn_id";
                                break;
                            case 0x80000006:
                                param = "secondary_comp_hash";
                                break;
                            case 0x80000007:
                                param = "secondary_comp_dynid";
                                break;
                            case 0x80000008:
                                param = "keycode";
                                break;
                            case 0x80000009:
                                param = "keychar";
                                break;
                            default:
                                 if((val >> 16) != 0 && (val & 0xffff) <= 256 && val != 0xffffffff) {
                                    param = "id: " + (val >> 16) + ", comp: " + (val & 0xffff) + "";
                                }
                                break;
                        }
                    }
                    System.out.println(op + " " + name + " " + param);
                }
                System.out.println();
            }
        }
    }
}
