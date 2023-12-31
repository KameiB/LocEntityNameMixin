package locentitynamemixin.mixin.vanilla;

import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.resources.I18n;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import locentitynamemixin.handlers.ForgeConfigHandler;

@Mixin(EntityRenderer.class)
public abstract class EntityRendererMixin {
    /**
     * @author KameiB
     * @reason If name for Name Plate contains a lang key, translate it at rendering.
     */
    @ModifyVariable(
            method = "drawNameplate",
            at = @At("HEAD"),
            argsOnly = true,
            ordinal = 0
    )
    // Line 2019: public static void drawNameplate(FontRenderer fontRendererIn, String str, float x, float y, float z, int verticalShift, float viewerYaw, float viewerPitch, boolean isThirdPersonFrontal, boolean isSneaking)
    private static String locentitynamemixinEntityRender_CustomName(String str) {
        if (ForgeConfigHandler.clientConfig.vanillaMobLocCustomNames) {
            int lastIndex = str.lastIndexOf("§r");
            String langKey = lastIndex == (str.length() - 2) ? str.substring(0, lastIndex) : str;
            return I18n.hasKey(langKey) ? I18n.format(langKey) : str;
        }
        else { // Argument as is (default behaviour)
            return str;
        }
    }
}
