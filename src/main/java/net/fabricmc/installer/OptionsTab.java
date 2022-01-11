package net.fabricmc.installer;

import net.fabricmc.installer.util.MetaHandler;
import net.fabricmc.installer.util.Reference;

import javax.swing.*;

import java.awt.*;
import java.io.IOException;
import java.util.List;

public class OptionsTab {
	public JTextField newMetaURL = new JTextField();
	public JButton confirmButton = new JButton("confirm");

	public JPanel makePanel(List<Handler> handlers){
		JPanel pane = new JPanel();
		pane.setLayout(new BoxLayout(pane, BoxLayout.LINE_AXIS));

		pane.add(newMetaURL);
		newMetaURL.setText(Reference.metaServerUrl);
		newMetaURL.setMaximumSize(new Dimension(275, 30));

		pane.add(confirmButton);
		confirmButton.addActionListener(e -> {
			System.out.println(handlers.size());
			Reference.metaServerUrl = newMetaURL.getText();
			updateGameVersions(handlers);
			updateLoaderVersions(handlers);
		});

		return pane;
	}

	public void updateGameVersions(List<Handler> handlers){
		Main.GAME_VERSION_META = new MetaHandler(Reference.getMetaServerEndpoint("v2/versions/game"));

		try {
			Main.GAME_VERSION_META.load();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

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

	public void updateLoaderVersions(List<Handler> handlers){
		Main.LOADER_META =  new MetaHandler(Reference.getMetaServerEndpoint("v2/versions/loader"));

		try {
			Main.LOADER_META.load();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		for(int i = 0; i <= handlers.size() - 1; i++){
			handlers.get(i).loaderVersionComboBox.removeAllItems();

			for (MetaHandler.GameVersion version : Main.LOADER_META.getVersions()) {
				handlers.get(i).loaderVersionComboBox.addItem(version.getVersion());
			}

			handlers.get(i).loaderVersionComboBox.setSelectedIndex(0);
		}
	}
}
