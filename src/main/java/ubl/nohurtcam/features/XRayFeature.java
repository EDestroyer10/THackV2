package ubl.nohurtcam.features;

import java.util.ArrayList;

import ubl.nohurtcam.feature.Feature;
import net.minecraft.block.Blocks;

import static ubl.nohurtcam.NoHurtCam.MC;

public class XRayFeature extends Feature {

    public ArrayList<String> blocks = new ArrayList<>();
    
    public XRayFeature() {
        super("XRay", "Allows you to see only the selected blocks");

        /* ores */
        this.blocks.add(Blocks.COAL_ORE.getTranslationKey());
        this.blocks.add(Blocks.IRON_ORE.getTranslationKey());
        this.blocks.add(Blocks.GOLD_ORE.getTranslationKey());
        this.blocks.add(Blocks.REDSTONE_ORE.getTranslationKey());
        this.blocks.add(Blocks.LAPIS_ORE.getTranslationKey());
        this.blocks.add(Blocks.DIAMOND_ORE.getTranslationKey());
        this.blocks.add(Blocks.EMERALD_ORE.getTranslationKey());

        /* deepslate varients */
        this.blocks.add(Blocks.DEEPSLATE_COAL_ORE.getTranslationKey());
        this.blocks.add(Blocks.DEEPSLATE_IRON_ORE.getTranslationKey());
        this.blocks.add(Blocks.DEEPSLATE_GOLD_ORE.getTranslationKey());
        this.blocks.add(Blocks.DEEPSLATE_REDSTONE_ORE.getTranslationKey());
        this.blocks.add(Blocks.DEEPSLATE_LAPIS_ORE.getTranslationKey());
        this.blocks.add(Blocks.DEEPSLATE_DIAMOND_ORE.getTranslationKey());
        this.blocks.add(Blocks.DEEPSLATE_EMERALD_ORE.getTranslationKey());

        /* blocks of ore */
        this.blocks.add(Blocks.COAL_BLOCK.getTranslationKey());
        this.blocks.add(Blocks.IRON_BLOCK.getTranslationKey());
        this.blocks.add(Blocks.GOLD_BLOCK.getTranslationKey());
        this.blocks.add(Blocks.REDSTONE_BLOCK.getTranslationKey());
        this.blocks.add(Blocks.LAPIS_BLOCK.getTranslationKey());
        this.blocks.add(Blocks.DIAMOND_BLOCK.getTranslationKey());
        this.blocks.add(Blocks.EMERALD_BLOCK.getTranslationKey());
    } 

    @Override
    public void onEnable() {
        super.onEnable();
        MC.options.getGamma().setValue(10d);
        if (MC.worldRenderer == null) return;
        MC.worldRenderer.reload();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        MC.options.getGamma().setValue(1d);
        if (MC.worldRenderer == null) return;
        MC.worldRenderer.reload();
    }
    public boolean isXRayBlock(String block) {
		return this.blocks.contains(block);
    }

}