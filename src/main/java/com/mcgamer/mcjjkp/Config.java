package com.mcgamer.mcjjkp;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = JJKMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.BooleanValue SIX_EYES_ENABLED = BUILDER
            .comment("Whether the Six Eyes technique is enabled. If true, randomnly assigns the Six Eyes to a player.")
            .define("sixEyesEnabled", true);

    private static final ModConfigSpec.BooleanValue RANDOM_ASSIGN_TECHNIQUES = BUILDER
            .comment("If true, randomly assigns a curse technique to each player on server join.")
            .define("randomAssignTechniques", true);

    public static final ModConfigSpec.IntValue CURSED_ENERGY_LOSS_RANGE = BUILDER
            .comment("When ever you used cursed energy, a fraction of it is lost, this determines the " +
                    "range of that fraction.")
            .comment("When using a cursed technique, a random number will be chosen within this range and that " +
                    "percentage of cursed energy will be used on top of the cursed energy used in the technique.")
            .comment("If value is 0, no cursed energy is lost (effectively Six Eyes), if value is 100, all cursed " +
                    "energy is used up every time.")
            .defineInRange("cursedEnergyLossRange%", 5, 0, 100);

    static final ModConfigSpec SPEC = BUILDER.build();

    public static boolean sixEyesEnabled;
    public static boolean randomAssignTechniques;
    public static int cursedEnergyLossRange;


    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        sixEyesEnabled = SIX_EYES_ENABLED.get();
        randomAssignTechniques = RANDOM_ASSIGN_TECHNIQUES.get();
        cursedEnergyLossRange = CURSED_ENERGY_LOSS_RANGE.get();

    }
}
