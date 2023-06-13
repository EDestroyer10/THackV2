package ubl.nohurtcam.mixin;

import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.screen.slot.Slot;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(HandledScreen.class)
public interface HandledScreenAccessor {
    @Accessor("focusedSlot")
    @Nullable
    Slot mi_getFocusedSlot();

    @Accessor("x")
    int mi_getX();

    @Accessor("y")
    int mi_getY();
}