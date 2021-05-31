package com.affehund.undestroying;

import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

import net.minecraft.client.Minecraft;
import net.minecraft.client.util.InputMappings;
import net.minecraft.data.DataGenerator;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantment.Rarity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BookItem;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
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

	public static final EnchantmentType ALL_ITEMS = EnchantmentType.create(ModConstants.ALL_ITEMS_STRING,
			(Item item) -> item instanceof BookItem || ModUtils.isItemEnabledForUndestroying(new ItemStack(item)));

	public static final RegistryObject<Enchantment> UNDESTROYING_ENCHANTMENT = ENCHANTMENTS.register(
			ModConstants.MOD_ID,
			() -> new UndestroyingEnchantment(Rarity.UNCOMMON, ALL_ITEMS, EquipmentSlotType.values()));

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
		Item item = stack.getItem();
		if (UndestroyingConfig.COMMON_CONFIG.SHOW_TOOLTIP.get() && ModUtils.isItemEnabledForUndestroying(stack)
				&& EnchantmentHelper.getEnchantments(stack).containsKey(Undestroying.UNDESTROYING_ENCHANTMENT.get())
				&& !(item instanceof EnchantedBookItem)) {

			if (stack.isDamageableItem()) {
				if (ModUtils.toolMatches(item)) {
					if (ModUtils.aboutToBreak(stack)) {
						event.getToolTip().add(new TranslationTextComponent(ModConstants.TOOLTIP_TOOL_BROKEN)
								.withStyle(TextFormatting.DARK_RED));
					}
				}
			}

			if (InputMappings.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_KEY_LEFT_SHIFT)) {
				event.getToolTip().add(new TranslationTextComponent(ModConstants.TOOLTIP_ENCHANTMENT_UNDESTROYING)
						.withStyle(TextFormatting.GRAY));

				if (ModUtils.hasConfigLevel(UndestroyingConfig.COMMON_CONFIG.ANVIL, stack)) {
					event.getToolTip()
							.add(new TranslationTextComponent(ModConstants.TOOLTIP_ENCHANTMENT_UNDESTROYING_ANVIL,
									ModUtils.intToRoman(UndestroyingConfig.COMMON_CONFIG.ANVIL.get()))
											.withStyle(TextFormatting.GRAY));
				}
				if (ModUtils.hasConfigLevel(UndestroyingConfig.COMMON_CONFIG.CACTUS, stack)) {
					event.getToolTip()
							.add(new TranslationTextComponent(ModConstants.TOOLTIP_ENCHANTMENT_UNDESTROYING_CACTUS,
									ModUtils.intToRoman(UndestroyingConfig.COMMON_CONFIG.CACTUS.get()))
											.withStyle(TextFormatting.GRAY));
				}
				if (ModUtils.hasConfigLevel(UndestroyingConfig.COMMON_CONFIG.DESPAWNING, stack)) {
					event.getToolTip()
							.add(new TranslationTextComponent(ModConstants.TOOLTIP_ENCHANTMENT_UNDESTROYING_DESPAWNING,
									ModUtils.intToRoman(UndestroyingConfig.COMMON_CONFIG.DESPAWNING.get()))
											.withStyle(TextFormatting.GRAY));
				}
				if (ModUtils.hasConfigLevel(UndestroyingConfig.COMMON_CONFIG.EXPLOSION, stack)) {
					event.getToolTip()
							.add(new TranslationTextComponent(ModConstants.TOOLTIP_ENCHANTMENT_UNDESTROYING_EXPLOSION,
									ModUtils.intToRoman(UndestroyingConfig.COMMON_CONFIG.EXPLOSION.get()))
											.withStyle(TextFormatting.GRAY));
				}
				if (ModUtils.hasConfigLevel(UndestroyingConfig.COMMON_CONFIG.FIRE, stack)) {
					event.getToolTip()
							.add(new TranslationTextComponent(ModConstants.TOOLTIP_ENCHANTMENT_UNDESTROYING_FIRE,
									ModUtils.intToRoman(UndestroyingConfig.COMMON_CONFIG.FIRE.get()))
											.withStyle(TextFormatting.GRAY));
				}
				if (ModUtils.hasConfigLevel(UndestroyingConfig.COMMON_CONFIG.LAVA, stack)) {
					event.getToolTip()
							.add(new TranslationTextComponent(ModConstants.TOOLTIP_ENCHANTMENT_UNDESTROYING_LAVA,
									ModUtils.intToRoman(UndestroyingConfig.COMMON_CONFIG.LAVA.get()))
											.withStyle(TextFormatting.GRAY));
				}
				if (ModUtils.hasConfigLevel(UndestroyingConfig.COMMON_CONFIG.LIGHTNING_BOLT, stack)) {
					event.getToolTip()
							.add(new TranslationTextComponent(
									ModConstants.TOOLTIP_ENCHANTMENT_UNDESTROYING_LIGHTNING_BOLT,
									ModUtils.intToRoman(UndestroyingConfig.COMMON_CONFIG.LIGHTNING_BOLT.get()))
											.withStyle(TextFormatting.GRAY));
				}
				if (ModUtils.hasConfigLevel(UndestroyingConfig.COMMON_CONFIG.TOOL_BREAKING, stack)
						&& ModUtils.toolMatches(item)) {
					event.getToolTip()
							.add(new TranslationTextComponent(
									ModConstants.TOOLTIP_ENCHANTMENT_UNDESTROYING_TOOL_BREAKING,
									ModUtils.intToRoman(UndestroyingConfig.COMMON_CONFIG.TOOL_BREAKING.get()))
											.withStyle(TextFormatting.GRAY));
				}
				if (ModUtils.hasConfigLevel(UndestroyingConfig.COMMON_CONFIG.VOID, stack)) {
					event.getToolTip()
							.add(new TranslationTextComponent(ModConstants.TOOLTIP_ENCHANTMENT_UNDESTROYING_VOID,
									ModUtils.intToRoman(UndestroyingConfig.COMMON_CONFIG.VOID.get()))
											.withStyle(TextFormatting.GRAY));
				}
			}
		}
	}

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public void clickEvent(InputEvent.ClickInputEvent event) {
		Minecraft mc = Minecraft.getInstance();
		if (mc.player == null || mc.player.isCreative())
			return;
		ItemStack stack = mc.player.getItemInHand(event.getHand());
		if (!ModUtils.aboutToBreak(stack))
			return;
		if (!ModUtils.isAntiBreakingEnabled())
			return;
		if (!ModUtils.hasConfigLevel(UndestroyingConfig.COMMON_CONFIG.TOOL_BREAKING, stack))
			return;
		if (!ModUtils.toolMatches(stack.getItem()))
			return;
		if (stack.isEmpty())
			return;
		if (!stack.isDamageableItem())
			return;
		else {
			mc.gui.setOverlayMessage(
					new TranslationTextComponent(ModConstants.TOOLTIP_TOOL_BROKEN).withStyle(TextFormatting.DARK_RED),
					false);
			event.setCanceled(true);
			event.setSwingHand(false);
		}
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
				add(ModConstants.TOOLTIP_ENCHANTMENT_UNDESTROYING_ANVIL, "- Amboss (%s)");
				add(ModConstants.TOOLTIP_ENCHANTMENT_UNDESTROYING_CACTUS, "- Kaktus (%s)");
				add(ModConstants.TOOLTIP_ENCHANTMENT_UNDESTROYING_DESPAWNING, "- Verschwinden (%s)");
				add(ModConstants.TOOLTIP_ENCHANTMENT_UNDESTROYING_EXPLOSION, "- Explosion (%s)");
				add(ModConstants.TOOLTIP_ENCHANTMENT_UNDESTROYING_FIRE, "- Feuer (%s)");
				add(ModConstants.TOOLTIP_ENCHANTMENT_UNDESTROYING_LAVA, "- Lava (%s)");
				add(ModConstants.TOOLTIP_ENCHANTMENT_UNDESTROYING_LIGHTNING_BOLT, "- Blitz (%s)");
				add(ModConstants.TOOLTIP_ENCHANTMENT_UNDESTROYING_TOOL_BREAKING, "- Werkzeug Brechung (%s)");
				add(ModConstants.TOOLTIP_ENCHANTMENT_UNDESTROYING_VOID, "- Leere (%s)");

				add(ModConstants.TOOLTIP_TOOL_BROKEN, "Werkzeug zerbrochen!");
				break;

			case "en_us":
				add("_comment", "Translation (en_us) by Affehund");
				add(Undestroying.UNDESTROYING_ENCHANTMENT.get(), "Undestroying");
				add(ModConstants.TOOLTIP_ENCHANTMENT_UNDESTROYING, "Prevents destruction from:");
				add(ModConstants.TOOLTIP_ENCHANTMENT_UNDESTROYING_ANVIL, "- Anvil (%s)");
				add(ModConstants.TOOLTIP_ENCHANTMENT_UNDESTROYING_CACTUS, "- Cactus (%s)");
				add(ModConstants.TOOLTIP_ENCHANTMENT_UNDESTROYING_DESPAWNING, "- Despawning (%s)");
				add(ModConstants.TOOLTIP_ENCHANTMENT_UNDESTROYING_EXPLOSION, "- Explosion (%s)");
				add(ModConstants.TOOLTIP_ENCHANTMENT_UNDESTROYING_FIRE, "- Fire (%s)");
				add(ModConstants.TOOLTIP_ENCHANTMENT_UNDESTROYING_LAVA, "- Lava (%s)");
				add(ModConstants.TOOLTIP_ENCHANTMENT_UNDESTROYING_LIGHTNING_BOLT, "- Lightning Bolt (%s)");
				add(ModConstants.TOOLTIP_ENCHANTMENT_UNDESTROYING_TOOL_BREAKING, "- Tool Breaking (%s)");
				add(ModConstants.TOOLTIP_ENCHANTMENT_UNDESTROYING_VOID, "- Void (%s)");

				add(ModConstants.TOOLTIP_TOOL_BROKEN, "Tool broken!");
				break;
			}
		}
	}
}
