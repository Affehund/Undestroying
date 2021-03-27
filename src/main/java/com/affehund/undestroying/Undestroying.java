package com.affehund.undestroying;

import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.data.DataGenerator;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantment.Rarity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod(ModConstants.MOD_ID)
public class Undestroying {

	public static final Logger LOGGER = LogManager.getLogger(ModConstants.MOD_NAME);
	public static final Random RANDOM = new Random();

	public static Undestroying INSTANCE;

	final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
	final IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;

	public Undestroying() {
		INSTANCE = this;
		LOGGER.debug("Loading up " + ModConstants.MOD_NAME + "!");
		modEventBus.addListener(this::gatherData);
		forgeEventBus.register(this);

		ModLoadingContext.get().registerConfig(Type.COMMON, UndestroyingConfig.COMMON_CONFIG_SPEC,
				ModConstants.COMMON_CONFIG_NAME);
		ENCHANTMENTS.register(modEventBus);
	}

	public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister
			.create(ForgeRegistries.ENCHANTMENTS, ModConstants.MOD_ID);

	public static final RegistryObject<Enchantment> UNDESTROYING_ENCHANTMENT = ENCHANTMENTS.register(
			ModConstants.MOD_ID,
			() -> new UndestroyingEnchantment(Rarity.RARE, EnchantmentType.BREAKABLE, EquipmentSlotType.values()));

	private void gatherData(final GatherDataEvent event) {
		DataGenerator gen = event.getGenerator();
		if (event.includeClient()) {
			gen.addProvider(new LanguageGen(gen, ModConstants.MOD_ID, "de_de"));
			gen.addProvider(new LanguageGen(gen, ModConstants.MOD_ID, "en_us"));
		}
	}

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public void tooltip(final ItemTooltipEvent event) {
		ItemStack stack = event.getItemStack();
		if (UndestroyingConfig.COMMON_CONFIG.SHOW_TOOLTIP.get() && Undestroying.isItemEnabledForUndestroying(stack)
				&& EnchantmentHelper.getEnchantments(stack).containsKey(Undestroying.UNDESTROYING_ENCHANTMENT.get())) {
			if (Undestroying.hasMinUndestroyingLevel(1, stack)) {
				event.getToolTip().add(new TranslationTextComponent(ModConstants.TOOLTIP_ENCHANTMENT_UNDESTROYING)
						.withStyle(TextFormatting.GREEN));
				event.getToolTip().add(new TranslationTextComponent(ModConstants.TOOLTIP_ENCHANTMENT_UNDESTROYING_1)
						.withStyle(TextFormatting.GREEN));
			}
			if (Undestroying.hasMinUndestroyingLevel(2, stack)) {
				event.getToolTip().add(new TranslationTextComponent(ModConstants.TOOLTIP_ENCHANTMENT_UNDESTROYING_2)
						.withStyle(TextFormatting.GREEN));
			}
			if (Undestroying.hasMinUndestroyingLevel(3, stack)) {
				event.getToolTip().add(new TranslationTextComponent(ModConstants.TOOLTIP_ENCHANTMENT_UNDESTROYING_3)
						.withStyle(TextFormatting.GREEN));
			}
		}
	}

	public static boolean isItemEnabledForUndestroying(ItemStack stack) {
		return UndestroyingConfig.COMMON_CONFIG.INVERTED_BLACKLIST
				.get() == UndestroyingConfig.COMMON_CONFIG.BLACKLISTED_ITEMS.get()
						.contains(stack.getItem().getItem().getRegistryName().toString());
	}

	public static boolean hasMinUndestroyingLevel(float level, ItemStack stack) {
		return (EnchantmentHelper.getItemEnchantmentLevel(UNDESTROYING_ENCHANTMENT.get(), stack) >= level);
	}

	public static final class LanguageGen extends LanguageProvider {

		public LanguageGen(DataGenerator gen, String modid, String locale) {
			super(gen, ModConstants.MOD_ID, locale);
		}

		@Override
		protected void addTranslations() {
			String locale = this.getName().replace("Languages: ", "");
			switch (locale) {
			case "de_de":
				add("_comment", "Translation (de_de) by Affehund");
				add(Undestroying.UNDESTROYING_ENCHANTMENT.get(), "Unzerstörbarkeit");
				add(ModConstants.TOOLTIP_ENCHANTMENT_UNDESTROYING, "Verhindert Zerstörung durch:");
				add(ModConstants.TOOLTIP_ENCHANTMENT_UNDESTROYING_1, "- Kakteen");
				add(ModConstants.TOOLTIP_ENCHANTMENT_UNDESTROYING_2, "- Feuer");
				add(ModConstants.TOOLTIP_ENCHANTMENT_UNDESTROYING_3, "- Explosionen");

				break;
			case "en_us":
				add("_comment", "Translation (en_us) by Affehund");
				add(Undestroying.UNDESTROYING_ENCHANTMENT.get(), "Undestroying");
				add(ModConstants.TOOLTIP_ENCHANTMENT_UNDESTROYING, "Prevents destruction from:");
				add(ModConstants.TOOLTIP_ENCHANTMENT_UNDESTROYING_1, "- cacti");
				add(ModConstants.TOOLTIP_ENCHANTMENT_UNDESTROYING_2, "- fire");
				add(ModConstants.TOOLTIP_ENCHANTMENT_UNDESTROYING_3, "- explosions");
				break;
			}
		}
	}

	public static ResourceLocation getModResourceLocation(String path) {
		return new ResourceLocation(ModConstants.MOD_ID, path);
	}
}
