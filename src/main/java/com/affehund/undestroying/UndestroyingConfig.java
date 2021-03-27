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

	public UndestroyingConfig() {
		this.BLACKLISTED_ITEMS = new ArrayList<String>();
		this.INVERTED_BLACKLIST = false;
		this.SHOW_TOOLTIP = true;
	}

	public UndestroyingConfig(UndestroyingConfig config) {
		this.BLACKLISTED_ITEMS = config.BLACKLISTED_ITEMS;
		this.INVERTED_BLACKLIST = config.INVERTED_BLACKLIST;
		this.SHOW_TOOLTIP = config.SHOW_TOOLTIP;
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
