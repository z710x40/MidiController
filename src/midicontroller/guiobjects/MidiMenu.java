package midicontroller.guiobjects;

import javafx.scene.control.MenuBar;


import IODevice.MidiControllerDevice;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToggleGroup;
import midicontroller.MidiControllerEvent;
import midicontroller.MidiControllerEventDispatcher;
import midicontroller.database.ControllerAppletContainer;
import midicontroller.database.Globals;

/**
 * This class maintains the menustructure
 * @author gebruiker
 *
 */
public class MidiMenu extends MenuBar 
{

	// Top menubar items
	Menu effect=new Menu("Effect     ");
	Menu midi=  new Menu("Midi output");
	

	
	final ToggleGroup toggleGroup = new ToggleGroup();
	
	
	
	public MidiMenu()
	{
		this.getMenus().addAll(effect,midi);
	}
	
	/**
	 * Add the list of effects to the effect list in the menu bar
	 * 
	 * @param controllerAppletContainer
	 */
	public void AddEffects(ControllerAppletContainer controllerAppletContainer) {
		for (final ControllerApplet cApp : controllerAppletContainer.getList()) {
			MenuItem tempItem = new MenuItem(cApp.getEffectName());
			
			// Stuur class MidiController een event om van applet te wisselen
			tempItem.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					MidiControllerEventDispatcher.SendEvent(new MidiControllerEvent(null, "change_controller",
							cApp.getHexSelectString(), cApp.getAppletNumber(), Globals.channel));
				}
			});
			effect.getItems().add(tempItem);		// Voeg het effect aan het menu toe
		}
	}

	/**
	 * Add the list of Midi controllers to the menu item
	 * 
	 * @param midiControllerDevice
	 */
	public void addMidiDevices(MidiControllerDevice midiControllerDevice) {

		for (final String device : midiControllerDevice.getDeviceArray()) {

			MenuItem tempItem = new MenuItem(device);
			tempItem.setOnAction(new EventHandler<ActionEvent>() {		// Stuur een event naar MidiControllerDevice om van midi device te wisselen
				@Override
				public void handle(ActionEvent e) {

					MidiControllerEventDispatcher.SendEvent(new MidiControllerEvent(null, "change_mididevice", device, 0, 0));
				}
			});

			midi.getItems().add(tempItem);		// Voe het midi device toe aan het menu
		}

	}



}
