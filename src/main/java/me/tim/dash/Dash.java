package me.tim.dash;

import me.tim.dash.Network.Packets;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;


// The value here should match an entry in the META-INF/mods.toml file
@Mod(Dash.MODID)
public class Dash
{
    public static final String NAME = "Dash";
    public static final String MODID = "dash";

    public static ResourceLocation locate(String name)
    {
        return new ResourceLocation(MODID, name);
    }

    public static String find(String name)
    {
        return MODID + ":" + name;
    }

    public Dash()
    {
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, DashConfig.SERVER_SPEC);

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () ->
        {
            MinecraftForge.EVENT_BUS.register(new DashTriggered());
            FMLJavaModLoadingContext.get().getModEventBus().addListener(DashClient::registerKeys);
        });

        FMLJavaModLoadingContext.get().getModEventBus().addListener((FMLCommonSetupEvent event) -> Packets.register());
    }

    @OnlyIn(Dist.CLIENT)
    static class DashClient
    {
        @OnlyIn(Dist.CLIENT)
        public static net.minecraft.client.KeyMapping DASH_KEYBIND = new net.minecraft.client.KeyMapping("dash.key.dash", 82, "key.categories.movement");

        @SubscribeEvent
        public static void registerKeys(RegisterKeyMappingsEvent event)
        {
            event.register(DASH_KEYBIND);
        }
    }
}

