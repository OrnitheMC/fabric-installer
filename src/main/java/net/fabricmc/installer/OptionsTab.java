package net.fabricmc.installer;

import net.fabricmc.installer.util.MetaHandler;
import net.fabricmc.installer.util.MetaReloader;
import net.fabricmc.installer.util.Reference;

import javax.swing.*;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
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
			Reference.metaServerUrl = newMetaURL.getText();
			if (MetaReloader.updateMeta(handlers)) {
				System.out.println("Error");
			} else {
				System.out.println("Success");
			}
		});

		return pane;
	}

}
