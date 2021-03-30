package com.sorin.mobilecmd.desktopcmd;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import org.apache.log4j.Logger;

import com.sorin.mobilecmd.data.ClientFile;
import com.sorin.mobilecmd.desktopcmd.util.RuntimeResponse;
import com.sorin.mobilecmd.desktopcmd.ws.Session;
import com.sorin.mobilecmd.desktopcmd.ws.WSUtil;

public class FileSelect extends JFrame {
	private static final Logger log = Logger.getLogger(FileSelect.class);

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	/**
	 * Create the frame.
	 */
	public FileSelect() {
		log.debug("FileSelect - open Other Window");
		final JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		fileChooser.setMultiSelectionEnabled(true);
		
		setTitle("Mobile CMD - Desktop Client --- " + Session.getSession().getUser().getDisplayName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 640, 371);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		final JCheckBox chckbxAllowCommands = new JCheckBox("Enable cmdlet control");
		chckbxAllowCommands.setToolTipText("Check / Uncheck if you wish that a Mobile Device have Windows Runtime Commands rights.");		
		chckbxAllowCommands.setBounds(16, 295, 155, 23);
		chckbxAllowCommands.setSelected(Session.getSession().getClient().isAllowCommands());
		contentPane.add(chckbxAllowCommands);
		
		JButton btnOpenFile = new JButton("");
		btnOpenFile.setToolTipText("Open file");
		btnOpenFile.setIcon(new ImageIcon(FileSelect.class.getResource("/open.png")));
		btnOpenFile.setBounds(560, 21, 48, 48);
		contentPane.add(btnOpenFile);
		
		JButton btnRemove = new JButton("");
		btnRemove.setToolTipText("Remove files");
		btnRemove.setIcon(new ImageIcon(FileSelect.class.getResource("/delete.png")));
		btnRemove.setBounds(560, 139, 48, 48);
		contentPane.add(btnRemove);
		
		JButton btnAddFiles = new JButton("");
		btnAddFiles.setToolTipText("Add files");
		btnAddFiles.setIcon(new ImageIcon(FileSelect.class.getResource("/add.png")));
		btnAddFiles.setBounds(560, 80, 48, 48);
		contentPane.add(btnAddFiles);
		
		JButton btnSave = new JButton("");
		btnSave.setToolTipText("Save configuration");
		btnSave.setIcon(new ImageIcon(FileSelect.class.getResource("/save.png")));
		btnSave.setBounds(560, 270, 48, 48);
		contentPane.add(btnSave);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(16, 21, 525, 243);
		contentPane.add(scrollPane);
		
		final JList list = new JList();
		scrollPane.setViewportView(list);
		final List<ClientFile> clientFiles = Session.getSession().getFiles();
		updateList(list, clientFiles);
		
		btnOpenFile.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (list.getSelectedIndices().length == 1) {
						int index = list.getSelectedIndex();
						
						RuntimeResponse rr = RuntimeResponse.run("start \'" + clientFiles.get(index).getPath() + "\'");
												
						if (rr.getExitValue() != 0)
							throw new Exception(rr.getResponse());
							
					} else {
						throw new Exception("Please select a single file / folder to open");
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(contentPane, ex.getMessage());
					ex.printStackTrace();
				}
			}
		});
		
		btnAddFiles.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {				
				int returnVal = fileChooser.showOpenDialog(FileSelect.this);
				
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File[] choosedFiles = fileChooser.getSelectedFiles();					
					for (File f : choosedFiles) {
						String path = f.getAbsolutePath();
						
						ClientFile clientFile = new ClientFile();
						clientFile.setPath(path);
						clientFile.setFolder(f.isDirectory());
						clientFile.setRecursive(f.isDirectory());
						clientFile.setDateAdded(new Date());
						
						clientFiles.add(clientFile);
					}
					
					updateList(list, clientFiles);
	            } 
			}
			
		});
		
		btnRemove.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (list.getSelectedIndices().length > 0) {
					List<Integer> indices = new ArrayList<Integer>();				
					for (int index : list.getSelectedIndices()) {
						indices.add(index);
					}
					
					Collections.reverse(indices);				
					for (int index : indices)
						clientFiles.remove(index);
						
					updateList(list, clientFiles);
				} else {
					JOptionPane.showMessageDialog(contentPane, "Please select files or folders to remove");
				}
			}
		});
		
		btnSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				boolean allowCmd = chckbxAllowCommands.isSelected();
				try {
					if (WSUtil.saveConfiguration(allowCmd, clientFiles))
						JOptionPane.showMessageDialog(contentPane, "Configuration successfully saved!");
					else	
						throw new Exception("Could not save the configuration!");
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(contentPane, ex.getMessage());
					ex.printStackTrace();
				}
					
			}
		});
	}

	protected void updateList(JList list, List<ClientFile> clientFiles) {

		List<String> pathList = ClientFile.getPathForFiles(clientFiles);
		final String[] pathArray = pathList.toArray(new String[pathList.size()]);
		
		list.setModel(new AbstractListModel() {
			private static final long serialVersionUID = 1L;
			String[] values = pathArray;
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		
	}
}
