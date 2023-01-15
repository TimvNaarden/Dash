package me.tim.dash;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;

@EventBusSubscriber(modid = Dash.MODID, bus = Bus.MOD)
public class RegisterDash
{
    private static final ResourceLocation DASH_KEY = Dash.locate("entity.player.ench_dash");

    public static final Lazy<SoundEvent> DASH_SOUND = Lazy.of(() -> new SoundEvent(DASH_KEY));

    @SubscribeEvent
    public static void onRegistry(RegisterEvent event)
    {
        if (event.getRegistryKey().equals(ForgeRegistries.Keys.SOUND_EVENTS))
            event.register(ForgeRegistries.SOUND_EVENTS.getRegistryKey(), DASH_KEY, DASH_SOUND);
    }
}


