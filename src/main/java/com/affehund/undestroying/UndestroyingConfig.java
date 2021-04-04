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
	public Boolean INVERTED_BLACKLIST;
	public Boolean SHOW_TOOLTIP;
	public Integer MAX_LEVEL;

	public Integer ANVIL;
	public Integer CACTUS;
	public Integer DESPAWNING;
	public Integer EXPLOSION;
	public Integer FIRE;
	public Integer LAVA;
	public Integer LIGHTNING_BOLT;
	public Integer TOOL_BREAKING;
	public Integer VOID;

	public Integer VOID_TELEPORT_RANGE;

	public UndestroyingConfig() {
		this.BLACKLISTED_ITEMS = new ArrayList<String>();
		this.INVERTED_BLACKLIST = false;
		this.SHOW_TOOLTIP = true;
		this.MAX_LEVEL = 3;

		this.ANVIL = 1;
		this.CACTUS = 1;
		this.DESPAWNING = 3;
		this.EXPLOSION = 2;
		this.FIRE = 2;
		this.LAVA = 2;
		this.LIGHTNING_BOLT = 2;
		this.TOOL_BREAKING = 3;
		this.VOID = 3;

		this.VOID_TELEPORT_RANGE = 128;
	}

	public UndestroyingConfig(UndestroyingConfig config) {
		this.BLACKLISTED_ITEMS = config.BLACKLISTED_ITEMS;
		this.INVERTED_BLACKLIST = config.INVERTED_BLACKLIST;
		this.SHOW_TOOLTIP = config.SHOW_TOOLTIP;
		this.MAX_LEVEL = config.MAX_LEVEL;

		this.ANVIL = config.ANVIL;
		this.CACTUS = config.CACTUS;
		this.DESPAWNING = config.DESPAWNING;
		this.EXPLOSION = config.EXPLOSION;
		this.FIRE = config.FIRE;
		this.LAVA = config.LAVA;
		this.LIGHTNING_BOLT = config.LIGHTNING_BOLT;
		this.TOOL_BREAKING = config.TOOL_BREAKING;
		this.VOID = config.VOID;

		this.VOID_TELEPORT_RANGE = config.VOID_TELEPORT_RANGE;
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
