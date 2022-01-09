package net.fabricmc.installer;

import net.fabricmc.installer.util.MetaHandler;
import net.fabricmc.installer.util.Reference;

import javax.swing.*;

import java.io.IOException;
import java.util.List;

public class OptionsTab {
	public JTextField newMetaURL = new JTextField();
	public JButton confirmButton = new JButton("confirm");

	public JPanel makePanel(List<Handler> handlers){
		JPanel pane = new JPanel();
		//pane.setLayout(new BoxLayout(pane, BoxLayout.X_AXIS));
		pane.add(confirmButton);
		confirmButton.addActionListener(e -> {
			Reference.metaServerUrl = newMetaURL.getText();
			updateGameVersions(handlers);
			updateLoaderVersions(handlers);
		});
		pane.add(newMetaURL);
		newMetaURL.setText(Reference.metaServerUrl);

		return pane;
	}

	public void updateGameVersions(List<Handler> handlers){
		for(int i = 0; 1 <= handlers.size(); i++){
			Main.GAME_VERSION_META = new MetaHandler(Reference.getMetaServerEndpoint("v2/versions/game"));

			try {
				Main.GAME_VERSION_META.load();
			} catch (IOException ex) {
				ex.printStackTrace();
			}

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
		for(int i = 0; 1 <= handlers.size(); i++){
			Main.LOADER_META =  new MetaHandler(Reference.getMetaServerEndpoint("v2/versions/loader"));

			try {
				Main.LOADER_META.load();
			} catch (IOException ex) {
				ex.printStackTrace();
			}

			handlers.get(i).loaderVersionComboBox.removeAllItems();

			for (MetaHandler.GameVersion version : Main.LOADER_META.getVersions()) {
				handlers.get(i).loaderVersionComboBox.addItem(version.getVersion());
			}

			handlers.get(i).loaderVersionComboBox.setSelectedIndex(0);
		}
	}
}
