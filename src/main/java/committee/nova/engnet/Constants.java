package committee.nova.engnet;

import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Constants {
    public static final Logger LOGGER = LoggerFactory.getLogger("Energistical Networking");
    public static final String MOD_ID = "energistical_networking";

    public static @NotNull Identifier id(String name) {
        return new Identifier(MOD_ID, name);
    }
}
