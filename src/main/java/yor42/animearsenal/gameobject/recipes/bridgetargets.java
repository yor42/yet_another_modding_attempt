package yor42.animearsenal.gameobject.recipes;

import com.google.common.collect.Maps;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import truefantasy.animcolle.entity.*;
import truefantasy.animcolle.init.ItemInit;

import java.util.Map;
import java.util.function.Function;

public class bridgetargets {
    public static final bridgetargets bridge_targets = new bridgetargets();

    //list of spawn results
    private final Map<ItemStack, Function<World, Entity>> target_list = Maps.newHashMap();

    public static bridgetargets instance(){return bridge_targets;}

    public void addSummoningRecipe(ItemStack input, Function<World, Entity> entity)
    {
        if (getRecipeResult(input) != null) { net.minecraftforge.fml.common.FMLLog.log.info("Summoning recipe conflict detected! ignoring...: {} = {}", input, entity); return; }
        this.target_list.put(input, entity);
    }

    public void addSummoningRecipe(Item input, Function<World, Entity> entity)
    {
        addSummoningRecipe(new ItemStack(input), entity);
    }

    public Function<World, Entity> getRecipeResult(ItemStack stack)
    {
        for (Map.Entry<ItemStack, Function<World, Entity>> entry : this.target_list.entrySet())
        {
            if (this.compareRecipe(stack, entry.getKey()))
            {
                return entry.getValue();
            }
        }
        return null;
    }

    private boolean compareRecipe(ItemStack stack1, ItemStack stack2)
    {
        return stack2.getItem() == stack1.getItem();
    }

    public void addSummoningRecipiesAnimecolle(){
        addSummoningRecipe(ItemInit.MISC_416BERET, Entity416:: new);
        addSummoningRecipe(ItemInit.MISC_AMIYAVIOLIN, EntityAmiya:: new);
        addSummoningRecipe(ItemInit.MISC_AQUASCARF, EntityAqua::new);
        addSummoningRecipe(ItemInit.MISC_ATAGORIBBON, EntityAtago::new);
        addSummoningRecipe(ItemInit.MISC_BISMARCKEMBLEM, EntityBismarck::new);
        addSummoningRecipe(ItemInit.MISC_WEIRDGUITARPICK, EntityStellaUnibell::new);
        addSummoningRecipe(ItemInit.MISC_FAMILYPICTURE, EntityEstia::new);
        addSummoningRecipe(ItemInit.MISC_NAGATOHAIRDECO, EntityNagato::new);
        addSummoningRecipe(ItemInit.MISC_ELMASOFTBUN, EntityElma::new);
        addSummoningRecipe(ItemInit.MISC_INHERITEDARSENAL, EntityM4A1::new);
        addSummoningRecipe(ItemInit.MISC_TOHRUDRAGONSCALE, EntityTohru::new);
        addSummoningRecipe(ItemInit.MISC_KANNAHAIRBAND, EntityKanna::new);
        addSummoningRecipe(ItemInit.MISC_BLACKHAIRBAND, EntitySylviLee::new);
        addSummoningRecipe(ItemInit.MISC_MEGUMINSHAT, EntityMegumin::new);
        addSummoningRecipe(ItemInit.MISC_MASHUGLASSES, EntityMashu::new);
        addSummoningRecipe(ItemInit.MISC_KITSUNEBIBLUE,EntityKaga::new);
        addSummoningRecipe(ItemInit.MISC_KITSUNEBIRED, EntityAkagi::new);
        addSummoningRecipe(ItemInit.MISC_MEDALOFHONOR, EntityEnterprise::new);
        addSummoningRecipe(ItemInit.MISC_SANAEHAIRBAND, EntitySanae::new);
        addSummoningRecipe(ItemInit.MISC_FOXMASK, EntityYae::new);
        addSummoningRecipe(ItemInit.MISC_EMBLEMOFSMOL,EntitySmolOne::new);
        addSummoningRecipe(ItemInit.MISC_MOTORCYCLELAMP, EntityIrisYuma::new);
        addSummoningRecipe(ItemInit.MISC_PLUMPEDFEATHER, EntityLucoa::new);
        addSummoningRecipe(ItemInit.MISC_WHITEMUFFLER, EntitySinonAsada::new);
        addSummoningRecipe(ItemInit.MISC_ROSESEALSTONE, EntityNero::new);
    }


}
