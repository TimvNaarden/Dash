package me.tim.dash;

import me.tim.dash.Network.ClienttoServer.DashVisualsPacket;
import me.tim.dash.Network.Packets;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static me.tim.dash.DashConfig.Cooldown;
import static me.tim.dash.DashConfig.Distance;

public class DashTriggered
{
    int cooldown = 0;

    @SubscribeEvent
    public void onKeyPressed(InputEvent.Key event)
    {
        Minecraft mc = Minecraft.getInstance();

        if (mc.player == null) { return; }
        Player player = mc.player;

        if (Dash.DashClient.DASH_KEYBIND.isDown() && cooldown <= 0)
        {
            Packets.sendToServer(new DashVisualsPacket());
            int Distance = Distance();
            Vec3 playerLook = player.getViewVector(1);
            Vec3 dashVec = new Vec3(playerLook.x() * Distance, player.getDeltaMovement().y() * Distance, playerLook.z() * Distance);
            player.setDeltaMovement(dashVec);

            cooldown = Cooldown();
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent.PlayerTickEvent event)
    {
        if (cooldown > 0 && event.phase.equals(Phase.START) && event.side.isClient() && event.player.equals(Minecraft.getInstance().player))

            --cooldown;

    }
}


