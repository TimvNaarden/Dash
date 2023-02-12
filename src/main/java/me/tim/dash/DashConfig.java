package me.tim.dash;


import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class DashConfig {
    public static final ForgeConfigSpec SERVER_SPEC;

    public static final ServerConfig SERVER_CONFIG;

    public static Integer Distance() {
        return ((Integer) SERVER_CONFIG.Distance.get().intValue());
    }
    public static Integer Cooldown() {
        return ((Integer) SERVER_CONFIG.Cooldown.get().intValue());
    }

    static {
        Pair<ServerConfig, ForgeConfigSpec> serverPair = (new ForgeConfigSpec.Builder()).configure(ServerConfig::new);
        SERVER_CONFIG = (ServerConfig)serverPair.getLeft();
        SERVER_SPEC = (ForgeConfigSpec)serverPair.getRight();
    }

    private static class ServerConfig {
        public final ForgeConfigSpec.ConfigValue<Integer> Distance;
        public final ForgeConfigSpec.ConfigValue<Integer> Cooldown;

        public ServerConfig(ForgeConfigSpec.Builder builder) {
            builder.comment("Dash Server/World Config");
            builder.push("Distance");
            this.Distance = (ForgeConfigSpec.ConfigValue<Integer>)builder.comment("\n The distance that the player dashes").define("Distance", 4);
            builder.pop();
            builder.push("Cooldown");
            this.Cooldown = (ForgeConfigSpec.ConfigValue<Integer>)builder.comment("\n The cooldown between the dashes").define("Cooldown", 100);
            builder.pop();
        }
    }
}
