package net.endermanofdoom.mowithers.render;

import net.endermanofdoom.mca.client.render.RenderBaseWither;
import net.endermanofdoom.mca.entity.boss.EntityBaseWither;
import net.endermanofdoom.mowithers.MoWithers;
import net.endermanofdoom.mowithers.entity.wither.EntityWitherHare;
import net.endermanofdoom.mowithers.model.ModelWitherHare;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderWitherHare extends RenderBaseWither<EntityWitherHare>
{
    public RenderWitherHare(RenderManager renderManagerIn)
    {
        super(renderManagerIn);
        this.mainModel = new ModelWitherHare(0.0F);
        this.auraModel = new ModelWitherHare(0.5F);
    }

    protected ResourceLocation getEntityTexture(EntityWitherHare entity)
    {
        int i = entity.getInvulTime();
        return i > 0 && (i > 80 || i / 5 % 2 != 1) ? new ResourceLocation(MoWithers.MODID, "textures/entity/wither/evil/wither_hare_invulnerable.png") : new ResourceLocation(MoWithers.MODID, "textures/entity/wither/evil/wither_hare.png");
    }

    protected ResourceLocation getAuraTexture(EntityBaseWither entity)
    {
        return new ResourceLocation(MoWithers.MODID, "textures/entity/wither/wither_armor.png");
    }
}