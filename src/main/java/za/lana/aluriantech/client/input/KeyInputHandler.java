package za.lana.aluriantech.client.input;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {
    public static KeyBinding drillKey;
    public static void register() {
        drillKey = new KeyBinding("key.aluriantech.drillKey",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_G,
                "key.category.aluriantech");
        KeyBindingHelper.registerKeyBinding(drillKey);
    }
}
