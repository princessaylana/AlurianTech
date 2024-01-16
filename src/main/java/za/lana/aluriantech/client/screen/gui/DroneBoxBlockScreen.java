/**
 * Lana 2024
 */
package za.lana.aluriantech.client.screen.gui;

import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;

public class DroneBoxBlockScreen extends CottonInventoryScreen<DroneBoxDescription> {
    public DroneBoxBlockScreen(DroneBoxDescription gui, PlayerInventory player, Text title) {
        super(gui, player, title);
    }
}