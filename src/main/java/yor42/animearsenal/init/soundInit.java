package yor42.animearsenal.init;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import yor42.animearsenal.main;

public class soundInit {

    public static SoundEvent railgun_fire;
    public static SoundEvent railgun_charge;
    public static SoundEvent railgun_noammo;
    public static SoundEvent vitality;

    public static void registerSounds(){
        railgun_fire = registerSounds("railgun_fire");
        railgun_charge = registerSounds("railgun_charge");
        railgun_noammo= registerSounds("railgun_noammo");
        vitality = registerSounds("vitality");
    }

    private static SoundEvent registerSounds(String soundName) {
        final SoundEvent sound = new SoundEvent(new ResourceLocation(main.MOD_ID, soundName)).setRegistryName(new ResourceLocation(main.MOD_ID, soundName));
        ForgeRegistries.SOUND_EVENTS.register(sound);
        return sound;
    }

}
