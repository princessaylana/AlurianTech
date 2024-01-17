package za.lana.aluriantech.sound;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import za.lana.aluriantech.AlurianTech;

public class AlurianTechSounds {

    public static SoundEvent DRILLRIG_IDLE = registerSoundEvent("drillrig_idle");
    public static SoundEvent DRILLRIG_LASER= registerSoundEvent("drillrig_laser");
    public static SoundEvent DRILLRIG_MOVE = registerSoundEvent("drillrig_move");

    private static SoundEvent registerSoundEvent(String name){
        Identifier id = new Identifier(AlurianTech.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }
    public static void registerModSounds() {
        AlurianTech.LOGGER.info("Registering Sounds for  " + AlurianTech.MOD_ID);
    }

}
