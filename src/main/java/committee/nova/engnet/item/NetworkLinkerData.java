package committee.nova.engnet.item;

import com.redgrapefruit.itemnbt3.CustomData;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NetworkLinkerData implements CustomData {
    private @Nullable BlockPos selectedPos;

    @Override
    public @NotNull String getNbtCategory() {
        return "linkerData";
    }

    public void setSelectedPos(@Nullable BlockPos selectedPos) {
        this.selectedPos = selectedPos;
    }

    public @Nullable BlockPos getSelectedPos() {
        return selectedPos;
    }

    @Override
    public void readNbt(@NotNull NbtCompound nbt) {
        if (nbt.getInt("x") != 0 && nbt.getInt("y") != 0 && nbt.getInt("z") != 0) {
            selectedPos = new BlockPos(nbt.getInt("x"), nbt.getInt("y"), nbt.getInt("z"));
        } else {
            selectedPos = null;
        }
    }

    @Override
    public void writeNbt(@NotNull NbtCompound nbt) {
        if (selectedPos != null) {
            nbt.putInt("x", selectedPos.getX());
            nbt.putInt("y", selectedPos.getY());
            nbt.putInt("z", selectedPos.getZ());
        } else {
            nbt.putInt("x", 0);
            nbt.putInt("y", 0);
            nbt.putInt("z", 0);
        }
    }
}
