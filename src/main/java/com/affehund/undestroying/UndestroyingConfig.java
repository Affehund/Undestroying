package com.affehund.undestroying;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import net.fabricmc.loader.api.FabricLoader;

/**
 * @author Affehund
 *
 */
public class UndestroyingConfig {
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	private static final File FILE = new File(
			FabricLoader.getInstance().getConfigDir() + ModConstants.COMMON_CONFIG_NAME);

	public ArrayList<String> BLACKLISTED_ITEMS;
	public Boolean COMPATIBLE_WITH_CURSE_OF_BINDING;
	public Boolean INVERTED_BLACKLIST;
	public Integer MAX_LEVEL;
	public Boolean SHOW_TOOLTIP;
	public Integer VOID_TELEPORT_RANGE;

	public Integer ANVIL;
	public Integer CACTUS;
	public Integer DESPAWNING;
	public Integer EXPLOSION;
	public Integer FIRE;
	public Integer LAVA;
	public Integer LIGHTNING_BOLT;
	public Integer TOOL_BREAKING;
	public Integer VOID;

	public UndestroyingConfig() {
		this.BLACKLISTED_ITEMS = new ArrayList<String>();
		this.COMPATIBLE_WITH_CURSE_OF_BINDING = true;
		this.INVERTED_BLACKLIST = false;
		this.MAX_LEVEL = 3;
		this.SHOW_TOOLTIP = true;
		this.VOID_TELEPORT_RANGE = 128;

		this.ANVIL = 1;
		this.CACTUS = 1;
		this.DESPAWNING = 3;
		this.EXPLOSION = 2;
		this.FIRE = 2;
		this.LAVA = 2;
		this.LIGHTNING_BOLT = 2;
		this.TOOL_BREAKING = 3;
		this.VOID = 3;
	}

	public UndestroyingConfig(UndestroyingConfig config) {
		this.BLACKLISTED_ITEMS = config.BLACKLISTED_ITEMS;
		this.COMPATIBLE_WITH_CURSE_OF_BINDING = config.COMPATIBLE_WITH_CURSE_OF_BINDING;
		this.INVERTED_BLACKLIST = config.INVERTED_BLACKLIST;
		this.MAX_LEVEL = config.MAX_LEVEL;
		this.SHOW_TOOLTIP = config.SHOW_TOOLTIP;
		this.VOID_TELEPORT_RANGE = config.VOID_TELEPORT_RANGE;

		this.ANVIL = config.ANVIL;
		this.CACTUS = config.CACTUS;
		this.DESPAWNING = config.DESPAWNING;
		this.EXPLOSION = config.EXPLOSION;
		this.FIRE = config.FIRE;
		this.LAVA = config.LAVA;
		this.LIGHTNING_BOLT = config.LIGHTNING_BOLT;
		this.TOOL_BREAKING = config.TOOL_BREAKING;
		this.VOID = config.VOID;
	}

	public static UndestroyingConfig setup() {
		if (!FILE.exists()) {
			UndestroyingConfig config = new UndestroyingConfig();
			config.create();
			return config;
		}
		try {
			FileReader fileReader = new FileReader(FILE);
			UndestroyingConfig config = GSON.fromJson(fileReader, UndestroyingConfig.class);
			UndestroyingFabric.LOGGER.debug("Reading config {}", FILE.getName());
			return config != null ? config : new UndestroyingConfig();
		} catch (IOException | JsonIOException | JsonSyntaxException e) {
			UndestroyingFabric.LOGGER.error(e.getMessage(), e);
			return new UndestroyingConfig();
		}
	}

	public void create() {
		try (FileWriter fileWriter = new FileWriter(FILE)) {
			fileWriter.write(GSON.toJson(this));
			UndestroyingFabric.LOGGER.debug("Created new config {}", FILE.getName());
		} catch (IOException e) {
			UndestroyingFabric.LOGGER.error(e.getMessage(), e);
		}
	}
}
