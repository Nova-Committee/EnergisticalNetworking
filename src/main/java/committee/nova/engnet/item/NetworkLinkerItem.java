package committee.nova.engnet.item;

import com.redgrapefruit.itemnbt3.DataClient;
import committee.nova.engnet.block.AbstractNetworkMemberBlockEntity;
import committee.nova.engnet.block.NetworkEmitterBlockEntity;
import committee.nova.engnet.block.NetworkReceiverBlockEntity;
import committee.nova.engnet.block.NetworkTerminalBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class NetworkLinkerItem extends Item {
    public NetworkLinkerItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (context.getWorld().isClient()) return super.useOnBlock(context);
        World world = context.getWorld();
        AtomicReference<ActionResult> ret = new AtomicReference<>(ActionResult.PASS);
        DataClient.use(NetworkLinkerData::new, context.getStack(), data -> {
            if (data.getSelectedPos() != null) {
                if (world.getBlockEntity(context.getBlockPos()) instanceof AbstractNetworkMemberBlockEntity) {
                    BlockEntity targetBe = world.getBlockEntity(context.getBlockPos());
                    BlockEntity sourceBe = world.getBlockEntity(data.getSelectedPos());
                    if (context.getBlockPos().isWithinDistance(new Vec3i(data.getSelectedPos().getX(),
                            data.getSelectedPos().getY(),
                            data.getSelectedPos().getZ()),
                            128.0d)) {
                        if (targetBe instanceof NetworkTerminalBlockEntity) {
                            if (sourceBe instanceof NetworkEmitterBlockEntity emitter) {
                                emitter.setTargetPos(context.getBlockPos());
                                data.setSelectedPos(null);
                                ret.set(ActionResult.SUCCESS);
                            }
                            if (sourceBe instanceof NetworkReceiverBlockEntity receiver) {
                                receiver.setSourcePos(context.getBlockPos());
                                data.setSelectedPos(null);
                                ret.set(ActionResult.SUCCESS);
                            }
                        } else if (targetBe instanceof NetworkEmitterBlockEntity emitter) {
                            if (sourceBe instanceof NetworkTerminalBlockEntity) {
                                emitter.setTargetPos(data.getSelectedPos());
                                data.setSelectedPos(null);
                                ret.set(ActionResult.SUCCESS);
                            }
                        } else if (targetBe instanceof NetworkReceiverBlockEntity receiver) {
                            if (sourceBe instanceof NetworkTerminalBlockEntity) {
                                receiver.setSourcePos(data.getSelectedPos());
                                data.setSelectedPos(null);
                                ret.set(ActionResult.SUCCESS);
                            }
                        }
                    } else {
                        if (context.getPlayer() != null) {
                            context.getPlayer().sendMessage(Text.translatable("message.energistical_networking.link_too_far"), true);
                        }
                    }
                }
            } else {
                if (world.getBlockEntity(context.getBlockPos()) instanceof AbstractNetworkMemberBlockEntity) {
                    data.setSelectedPos(context.getBlockPos());
                    ret.set(ActionResult.SUCCESS);
                }
            }
        });
        return ret.get();
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        DataClient.use(NetworkLinkerData::new, stack, data -> {
            if (data.getSelectedPos() != null) {
                tooltip.add(Text.translatable("tooltip.energistical_networking.linker.info",
                        data.getSelectedPos().getX(),
                        data.getSelectedPos().getY(),
                        data.getSelectedPos().getZ()).formatted(Formatting.BLUE));
            }
        });
    }
}
