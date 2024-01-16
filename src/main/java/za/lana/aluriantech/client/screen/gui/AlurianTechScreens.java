/**
 * Lana 2024
 */
package za.lana.aluriantech.client.screen.gui;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import za.lana.aluriantech.AlurianTech;

public class AlurianTechScreens {

    //
    public static ScreenHandlerType<DroneBoxDescription> DRONEBOX_GUI =
            Registry.register(Registries.SCREEN_HANDLER,  new Identifier(AlurianTech.MOD_ID, "dronebox_gui"),
                    new ScreenHandlerType<>((syncId, inventory) ->
                            new DroneBoxDescription(AlurianTechScreens.DRONEBOX_GUI, syncId, inventory, ScreenHandlerContext.EMPTY),
                            FeatureFlags.VANILLA_FEATURES));

    public static void registerGuiScreens(){
        AlurianTech.LOGGER.info("Registering GuiScreens for " + AlurianTech.MOD_ID);
    }
}
