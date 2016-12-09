package com.github.thedatasmith;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.UIManager;

import com.fazecast.jSerialComm.SerialPort;

public class GeneralSerialMonitor extends JPanel implements ActionListener, Runnable
{
	public static final String TITLE = "General Serial Monitor";
	public static final Dimension DEFAULT_SIZE = new Dimension(800, 600);
	public static final Dimension COMPONENT_SIZE = new Dimension(128, 24);
	public static final Integer[] BAUD_RATES =
		{110, 300, 600, 1200, 2400, 4800, 9600, 14400, 19200, 38400, 57600, 115200, 128000, 256000};
	
	public static void main(String[] args)
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(Exception e) {}
		
		new GeneralSerialMonitor();
	}
	
	JFrame frame;
	
	JPanel selectionPanel;
	JButton refreshButton;
	JComboBox<String> comSelector;
	JComboBox<Integer> baudRateSelector;
	JButton connectButton;
	
	JTextArea console;
	
	SerialPort[] ports;
	
	GeneralSerialMonitor()
	{
		frame = new JFrame("");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setPreferredSize(DEFAULT_SIZE);
		this.setLayout(new BorderLayout());
		frame.add(this);
		
		selectionPanel = new JPanel();
		selectionPanel.setLayout(new BoxLayout(selectionPanel, BoxLayout.PAGE_AXIS));
		
		refreshButton = new JButton("Refresh");
		refreshButton.addActionListener(this);
		refreshButton.setAlignmentX(0.5f);
		refreshButton.setMaximumSize(COMPONENT_SIZE);
		refreshButton.setPreferredSize(COMPONENT_SIZE);
		selectionPanel.add(refreshButton);
		
		selectionPanel.add(new JLabel("Device:"));
		
		comSelector = new JComboBox<String>();
		//comSelector.setPreferredSize(new Dimension(64, 32));
		comSelector.setMaximumSize(COMPONENT_SIZE);
		comSelector.setPreferredSize(COMPONENT_SIZE);
		selectionPanel.add(comSelector);

		selectionPanel.add(new JLabel("Baud Rate:"));
		
		baudRateSelector = new JComboBox<Integer>(BAUD_RATES);
		baudRateSelector.setMaximumSize(COMPONENT_SIZE);
		baudRateSelector.setPreferredSize(COMPONENT_SIZE);
		selectionPanel.add(baudRateSelector);
		
		connectButton = new JButton("Connect");
		connectButton.addActionListener(this);
		connectButton.setMaximumSize(COMPONENT_SIZE);
		connectButton.setPreferredSize(COMPONENT_SIZE);
		connectButton.setAlignmentX(0.5f);
		selectionPanel.add(connectButton);
		
		selectionPanel.add(Box.createGlue());
		
		this.add(selectionPanel, BorderLayout.LINE_START);
		
		console = new JTextArea("Hello world");
		console.setEditable(false);
		this.add(console, BorderLayout.CENTER);
		
		frame.pack();
		frame.setVisible(true);
		
		refreshSerialPorts();
	}
	
	void refreshSerialPorts()
	{
		ports = SerialPort.getCommPorts();
		comSelector.removeAllItems();
		for(SerialPort port : ports)
		{
			comSelector.addItem(port.getSystemPortName());
		}
	}

	@Override
	public void actionPerformed(ActionEvent ev)
	{
		if(ev.getSource() == refreshButton)
		{
			refreshSerialPorts();
		}
	}

	@Override
	public void run()
	{
		// TODO Auto-generated method stub
		
	}
	
	
}
