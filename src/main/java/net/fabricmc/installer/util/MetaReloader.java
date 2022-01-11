package net.fabricmc.installer.util;

import net.fabricmc.installer.Handler;
import net.fabricmc.installer.Main;

import java.io.IOException;
import java.util.List;

public class MetaReloader {
	public static boolean updateMeta(List<Handler> handlers) {
		boolean didCatchError = false;

		Main.GAME_VERSION_META = new MetaHandler(Reference.getMetaServerEndpoint("v2/versions/game"));
		Main.LOADER_META =  new MetaHandler(Reference.getMetaServerEndpoint("v2/versions/loader"));

		try{
			Main.GAME_VERSION_META.load();
			Main.LOADER_META.load();
		}catch (IOException exception){
			exception.printStackTrace();
			didCatchError = true;

			Reference.metaServerUrl = Reference.startingMetaServerUrl;

			Main.GAME_VERSION_META = new MetaHandler(Reference.getMetaServerEndpoint("v2/versions/game"));
			Main.LOADER_META =  new MetaHandler(Reference.getMetaServerEndpoint("v2/versions/loader"));

			try{
				Main.GAME_VERSION_META.load();
				Main.LOADER_META.load();
			}catch (IOException exception1){
				exception.printStackTrace();
			}

		}

		updateGameVersions(handlers);
		updateLoaderVersions(handlers);

		return didCatchError;
	}

	public static void updateGameVersions(List<Handler> handlers){
		for(int i = 0; i <= handlers.size() - 1; i++){
			handlers.get(i).gameVersionComboBox.removeAllItems();

			for (MetaHandler.GameVersion version : Main.GAME_VERSION_META.getVersions()) {
				if (!handlers.get(i).snapshotCheckBox.isSelected() && !version.isStable()) {
					continue;
				}
				handlers.get(i).gameVersionComboBox.addItem(version.getVersion());
			}

			handlers.get(i).gameVersionComboBox.setSelectedIndex(0);
		}
	}

	public static void updateLoaderVersions(List<Handler> handlers){
		for(int i = 0; i <= handlers.size() - 1; i++){
			handlers.get(i).loaderVersionComboBox.removeAllItems();

			for (MetaHandler.GameVersion version : Main.LOADER_META.getVersions()) {
				handlers.get(i).loaderVersionComboBox.addItem(version.getVersion());
			}

			handlers.get(i).loaderVersionComboBox.setSelectedIndex(0);
		}
	}
}
