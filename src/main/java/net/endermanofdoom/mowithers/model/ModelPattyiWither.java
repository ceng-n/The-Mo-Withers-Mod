package net.endermanofdoom.mowithers.model;

import net.endermanofdoom.mca.client.model.ModelBaseWither;
import net.endermanofdoom.mca.entity.boss.EntityBaseWither;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelPattyiWither extends ModelBaseWither 
{
	  private ModelRenderer[] clover;
	  
	public ModelPattyiWither(float p_i46302_1_) 
	{
		super(p_i46302_1_);
	    this.heads[0].setTextureOffset(0, 54).addBox(-4.5F, -5.0F, -4.5F, 9, 1, 9, p_i46302_1_);
	    this.heads[0].setTextureOffset(24, 27).addBox(-4.0F, -13.0F, -4.0F, 8, 8, 8, p_i46302_1_);
	    this.clover = new ModelRenderer[4];
	    this.clover[0] = new ModelRenderer(this, 48, 56);
	    this.clover[0].addBox(-8.5F, 0.5F, 0.0F, 8, 8, 0, p_i46302_1_);
	    this.clover[1] = new ModelRenderer(this, 48, 48);
	    this.clover[1].addBox(-8.5F, -8.5F, 0.0F, 8, 8, 0, p_i46302_1_);
	    this.clover[2] = new ModelRenderer(this, 48, 56);
	    this.clover[2].addBox(-8.5F, 0.5F, 0.0F, 8, 8, 0, p_i46302_1_);
	    this.clover[3] = new ModelRenderer(this, 48, 48);
	    this.clover[3].addBox(-8.5F, -8.5F, 0.0F, 8, 8, 0, p_i46302_1_);
	}

	
	  public void render(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_)
	  {
		  super.render(p_78088_1_, p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_);

		  ModelRenderer[] amodelrenderer = this.clover;
		  int i = amodelrenderer.length;
		  for (int j = 0; j < i; j++)
		  {
			  ModelRenderer modelrenderer = amodelrenderer[j];
			  modelrenderer.render(p_78088_7_);
		  }
	  }

	    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
	    {
	    	super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
			EntityBaseWither wither = (EntityBaseWither)entityIn;
	        float f11 = wither.getStandingAnimationScale(ageInTicks - (float)wither.ticksExisted);
	        f11 = f11 * f11;
		    this.clover[0].rotateAngleY = 0.0F;
		    this.clover[1].rotateAngleY = 0.0F;
		    this.clover[2].rotateAngleY = 3.2F;
		    this.clover[3].rotateAngleY = 3.2F;
		    
			  ModelRenderer[] amodelrenderer = this.clover;
			  int i = amodelrenderer.length;
			  for (int j = 0; j < i; j++)
			  {
				  ModelRenderer modelrenderer = amodelrenderer[j];
				  modelrenderer.rotationPointY = -26.0F + (f11 * 20F);
			  }
	  }
}
