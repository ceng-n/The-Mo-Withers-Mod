package net.endermanofdoom.mowithers.render;

import net.endermanofdoom.mca.client.render.RenderBaseWither;
import net.endermanofdoom.mowithers.MoWithers;
import net.endermanofdoom.mowithers.entity.wither.EntityWitherCoal;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderWitherCoal extends RenderBaseWither<EntityWitherCoal>
{
    public RenderWitherCoal(RenderManager renderManagerIn)
    {
        super(renderManagerIn);
    }

    protected ResourceLocation getEntityTexture(EntityWitherCoal entity)
    {
        int i = entity.getInvulTime();
        return i > 0 && (i > 80 || i / 5 % 2 != 1) ? new ResourceLocation(MoWithers.MODID, "textures/entity/wither/block/wither_coal_invulnerable.png") : new ResourceLocation(MoWithers.MODID, "textures/entity/wither/block/wither_coal.png");
    }

    protected ResourceLocation getAuraTexture(EntityWitherCoal entity)
    {
        return new ResourceLocation("textures/blocks/coal_block.png");
    }
}