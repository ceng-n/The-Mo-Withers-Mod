package net.endermanofdoom.mowithers.render;

import net.endermanofdoom.mca.client.model.ModelBaseWither;
import net.endermanofdoom.mca.client.render.RenderBaseWither;
import net.endermanofdoom.mca.entity.boss.EntityBaseWither;
import net.endermanofdoom.mowithers.MoWithers;
import net.endermanofdoom.mowithers.entity.wither.EntityWitherCreeper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderWitherCreeper extends RenderBaseWither<EntityWitherCreeper>
{
    public RenderWitherCreeper(RenderManager renderManagerIn)
    {
        super(renderManagerIn);
        this.addLayer(new LayerCharge(this));
    }

    protected ResourceLocation getEntityTexture(EntityWitherCreeper entity)
    {
        int i = entity.getInvulTime();
        return i > 0 && (i > 80 || i / 5 % 2 != 1) ? new ResourceLocation(MoWithers.MODID, "textures/entity/wither/mob/wither_creeper_invulnerable.png") : new ResourceLocation(MoWithers.MODID, "textures/entity/wither/mob/wither_creeper.png");
    }

    protected ResourceLocation getAuraTexture(EntityBaseWither entity)
    {
        return new ResourceLocation(MoWithers.MODID, "textures/entity/wither/mob/wither_creeper_armor.png");
    }    
    
    /**
     * Allows the render to do state modifications necessary before the model is rendered.
     */
    protected void preRenderCallback(EntityWitherCreeper entitylivingbaseIn, float partialTickTime)
    {
        float f = entitylivingbaseIn.getCreeperFlashIntensity(partialTickTime);
        float f1 = 1.0F + MathHelper.sin(f * 100.0F) * f * 0.01F;
        f = MathHelper.clamp(f, 0.0F, 1.0F);
        f = f * f;
        f = f * f;
        float f2 = (1.0F + f * 0.4F) * f1;
        float f3 = (1.0F + f * 0.1F) / f1;
        GlStateManager.scale(f2, f3, f2);
        
        f = entitylivingbaseIn.getWitherScale();
        int i = entitylivingbaseIn.getInvulTime();
        this.shadowSize = f * 0.5F;
        if (i > 0)
        {
            f -= ((float)i - partialTickTime) / 220.0F * 0.5F;
        }

        GlStateManager.scale(f, f, f);
    }

    /**
     * Gets an RGBA int color multiplier to apply.
     */
    protected int getColorMultiplier(EntityWitherCreeper entitylivingbaseIn, float lightBrightness, float partialTickTime)
    {
        float f = entitylivingbaseIn.getCreeperFlashIntensity(partialTickTime);

        if ((int)(f * 10.0F) % 2 == 0)
        {
            return 0;
        }
        else
        {
            int i = (int)(f * 0.2F * 255.0F);
            i = MathHelper.clamp(i, 0, 255);
            return i << 24 | 822083583;
        }
    }
    
	public class LayerCharge implements LayerRenderer<EntityWitherCreeper>
    {
        private final RenderWitherCreeper witherRenderer;
        private final ModelBaseWither witherModel = new ModelBaseWither(1.0F);

        public LayerCharge(RenderWitherCreeper witherRendererIn)
        {
            this.witherRenderer = witherRendererIn;
        }

        public void doRenderLayer(EntityWitherCreeper entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
        {
            if (entitylivingbaseIn.getPowered())
            {
                boolean flag = entitylivingbaseIn.isInvisible();
                GlStateManager.depthMask(!flag);
                this.witherRenderer.bindTexture(new ResourceLocation(MoWithers.MODID, "textures/entity/wither/mob/wither_creeper_armor.png"));
                GlStateManager.matrixMode(5890);
                GlStateManager.loadIdentity();
                float f = (float)entitylivingbaseIn.ticksExisted + partialTicks;
                GlStateManager.translate(f * 0.01F, f * 0.01F, 0);
                GlStateManager.matrixMode(5888);
                GlStateManager.enableBlend();
                float f1 = 0.5F;
                GlStateManager.color(f1, f1, f1, 1.0F);
                GlStateManager.disableLighting();
                GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
                this.witherModel.setModelAttributes(this.witherRenderer.getMainModel());
                this.witherModel.setLivingAnimations(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks);
                Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
                this.witherModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
                Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
                GlStateManager.matrixMode(5890);
                GlStateManager.loadIdentity();
                GlStateManager.matrixMode(5888);
                GlStateManager.enableLighting();
                GlStateManager.disableBlend();
                GlStateManager.depthMask(flag);
            }
        }

        public boolean shouldCombineTextures()
        {
            return false;
        }
    }
}