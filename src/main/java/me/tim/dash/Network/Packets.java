package me.tim.dash.Network;

import me.tim.dash.Dash;
import me.tim.dash.Network.ClienttoServer.DashVisualsPacket;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class Packets {
    private static final String PROTOCOL_VERSION = "1";

    private static int index = 0;

    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(Dash.locate("main"), () -> "1", "1"::equals, "1"::equals);

    public static void register() {
        register(DashVisualsPacket.class, DashVisualsPacket::encoder, DashVisualsPacket::decoder, DashVisualsPacket::handler);
    }

    private static <MSG> void register(Class<MSG> packet, BiConsumer<MSG, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, MSG> decoder, BiConsumer<MSG, Supplier<NetworkEvent.Context>> messageConsumer) {
        INSTANCE.registerMessage(index, packet, encoder, decoder, messageConsumer);
        index++;
    }

    public static void sendToClient(Object packet, ServerPlayer serverPlayer) {
        if (!(serverPlayer instanceof net.minecraftforge.common.util.FakePlayer) && serverPlayer.connection != null)
            INSTANCE.sendTo(packet, serverPlayer.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }

    public static void sendToAllClients(Object packet, Level world) {
        world.players().forEach(player -> sendToClient(packet, (ServerPlayer)player));
    }

    public static void sendToServer(Object packet) {
        INSTANCE.sendToServer(packet);
    }
}

