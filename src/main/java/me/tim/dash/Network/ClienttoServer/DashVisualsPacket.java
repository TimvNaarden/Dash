package me.tim.dash.Network.ClienttoServer;

import me.tim.dash.RegisterDash;
import java.util.function.Supplier;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.network.NetworkEvent;

public class DashVisualsPacket {
    public static void encoder(DashVisualsPacket packet, FriendlyByteBuf buff) {}

    public static DashVisualsPacket decoder(FriendlyByteBuf buff) {
        return new DashVisualsPacket();
    }

    public static void handler(DashVisualsPacket packet, Supplier<NetworkEvent.Context> context) {
        ((NetworkEvent.Context)context.get()).enqueueWork(() -> handlePacket(packet, ((NetworkEvent.Context)context.get()).getSender()));
        ((NetworkEvent.Context)context.get()).setPacketHandled(true);
    }

    private static void handlePacket(DashVisualsPacket packet, ServerPlayer player) {
        ServerLevel lvl = player.getLevel();

        var width = player.getBbWidth() / 2;
        var height = player.getBbHeight() * 0.3F;
        lvl.sendParticles(ParticleTypes.POOF, player.getX(), player.getY() + height, player.getZ(), 30, width, height, width, 0.08F);
        lvl.sendParticles(ParticleTypes.ENCHANTED_HIT, player.getX(), player.getY() + height, player.getZ(), 10, width, height, width, 0.2F);

        lvl.playSound(null, player, RegisterDash.DASH_SOUND.get(), SoundSource.PLAYERS, 1.0F, 2.0F);
    }
}
