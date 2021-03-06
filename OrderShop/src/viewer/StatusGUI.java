/* authors: Kontogeorgos Georgios & Mitrousis Alexandros
 * All copyrights reserved 2019-2020
 */

package viewer;

import model.QueueCustomer;
import model.Staff;

import java.awt.EventQueue;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import controller.AllOrders;
import ourExceptions.InvalidCategoryException;
import ourExceptions.InvalidItemException;
import ourExceptions.InvalidItemIDLengthException;
import ourExceptions.InvalidOrderCustomerID;
import ourExceptions.InvalidOrderCustomerIDException;
import ourExceptions.InvalidOrderCustomerNameException;
import ourExceptions.InvalidOrderTimeStamp;
import ourExceptions.InvalidOrderTimeStampException;
import ourExceptions.InvalidPriceException;
import shop.CsvReader;
import shop.Order;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.JProgressBar;
import javax.swing.JSpinner;
import javax.swing.JTextArea;

public class StatusGUI extends JFrame implements Observer {

	private static final long serialVersionUID = -7483455288689655101L;
	private DefaultListModel<String> model = new DefaultListModel<String>();
	private DefaultListModel<String> modelOnline = new DefaultListModel<String>();
	private JList<String> listCustomerQueue = new JList<>();
	private ArrayList<JTextArea> staff = new ArrayList<JTextArea>();
	private QueueCustomer queue;
	private ArrayList <Order> orders = new ArrayList <Order>();
	private JScrollPane scrollOrders;
	private JTextArea textArea_1,textArea_2,textArea_3, textArea_4,textArea_5;
	private Staff server;
	private JLabel simulationSpeedLabel;
	public JLabel threadsLabel;
	public JSlider simulationSpeedSlider;
	private JScrollPane scrollPane_1;
	private JList onlineList;
	private JButton btnRemoveServer, btnAddServer ;
	private JLabel lblOfflineOrders;
	
	
	
	public StatusGUI(QueueCustomer model, ArrayList<Staff> staffs)  throws FileNotFoundException, InvalidPriceException, InvalidCategoryException,
	InvalidOrderTimeStampException, InvalidOrderCustomerIDException, InvalidOrderCustomerNameException,
	InvalidItemIDLengthException, InvalidItemException {
		this.queue = model;
		queue.addObserver(this);

		for(Staff st : staffs){
			st.addObserver(this);
		}

		JFrame();
	}
	
	private void JFrame() {

		JFrame frame = new JFrame("Shop Simulation");
		frame.getContentPane().setBackground(SystemColor.inactiveCaptionBorder);
		frame.setBackground(SystemColor.text);
		frame.setLocation(350, 120);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
		JPanel panel = new JPanel();
		JPanel panel_1 = new JPanel();
		
		// Set up the title for different panels
		panel.setBorder(BorderFactory.createTitledBorder("Waiting Queue"));
		panel_1.setBorder(BorderFactory.createTitledBorder("Servers"));
		
		simulationSpeedSlider = new JSlider(0, 10);
		simulationSpeedSlider.setPaintTicks(true);
		simulationSpeedSlider.setPaintLabels(true);
		simulationSpeedSlider.setMajorTickSpacing(5);
		simulationSpeedSlider.setBackground(SystemColor.inactiveCaptionBorder);
		
		simulationSpeedLabel = new JLabel("Simulation Speed");
		simulationSpeedLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		btnRemoveServer = new JButton("REMOVE SERVER");
		
		btnAddServer = new JButton("ADD SERVER");
		
		threadsLabel = new JLabel("Thread's Simulation Time: 5 seconds" );
		threadsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(btnAddServer, GroupLayout.PREFERRED_SIZE, 167, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnRemoveServer, GroupLayout.PREFERRED_SIZE, 179, GroupLayout.PREFERRED_SIZE)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(94)
							.addComponent(simulationSpeedSlider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(53)
							.addComponent(threadsLabel, GroupLayout.PREFERRED_SIZE, 245, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(142)
							.addComponent(simulationSpeedLabel, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(197, Short.MAX_VALUE))
				.addComponent(panel_1, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 1181, Short.MAX_VALUE)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 1181, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(0)
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 262, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(35)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(btnAddServer, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnRemoveServer, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(10)
							.addComponent(simulationSpeedLabel, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(simulationSpeedSlider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(threadsLabel, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))))
					.addGap(93))
		);

		
		textArea_1 = new JTextArea();
		textArea_1.setColumns(10);
		textArea_1.setEditable(false);
		
		textArea_2 = new JTextArea();
		textArea_2.setColumns(10);
		textArea_2.setEditable(false);

		textArea_3 = new JTextArea();
		textArea_3.setColumns(10);
		textArea_3.setEditable(false);

		textArea_4 = new JTextArea();
		textArea_4.setColumns(10);
		textArea_4.setEditable(false);

		textArea_5 = new JTextArea();
		textArea_5.setColumns(10);
		textArea_5.setEditable(false);

		staff.add(0,textArea_1);
		staff.add(1,textArea_2);
		staff.add(2,textArea_3);
		staff.add(3,textArea_4);
		staff.add(4,textArea_5);

		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addComponent(textArea_1, GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(textArea_2, GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(textArea_3, GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(textArea_4, GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(textArea_5, GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
					.addGap(0))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(textArea_1, GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE)
						.addComponent(textArea_2, GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE)
						.addComponent(textArea_3, GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE)
						.addComponent(textArea_4, GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE)
						.addComponent(textArea_5, GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE))
					.addGap(5))
		);
		panel_1.setLayout(gl_panel_1);
		
		JScrollPane scrollPane = new JScrollPane();
		
		scrollPane_1 = new JScrollPane();
		
		JLabel lblNewLabel = new JLabel("Online Orders");
		
		lblOfflineOrders = new JLabel("Offline Orders");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 582, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblOfflineOrders))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 577, Short.MAX_VALUE)))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblOfflineOrders))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
						.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE))
					.addContainerGap())
		);
		
		onlineList = new JList<String>(modelOnline);
		scrollPane_1.setViewportView(onlineList);
		
		// slider for the threads
		simulationSpeedSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				threadsLabel.setText("Thread's Simulation Time: " + Integer.toString(((JSlider) e.getSource()).getValue()) + " seconds");
			}
		});
		
		listCustomerQueue = new JList<String>(model);
		
		scrollPane.setViewportView(listCustomerQueue);
		panel.setLayout(gl_panel);
		
		frame.getContentPane().setLayout(groupLayout);
		
		//frame.setContentPane(panel);
	    frame.pack();
		frame.setVisible(true);

	}
	
	
	public void clear(int number) {
		switch (number){
		case 1: 
			textArea_1.setText(" ");
			break;
		case 2: 
			textArea_2.setText(" ");
	    	break;
		case 3:
			textArea_3.setText(" ");
			break;
		case 4:
			textArea_4.setText(" ");
			break;
		case 5:
			textArea_5.setText(" ");
			break;
		default:
			break;

		}
		
	}

	// synchronize update for queue and staff
	@Override
	public synchronized void update(Observable arg0, Object arg1) {
		
		// checks if the observable is the queue
		if (arg0 == queue){
			// assigns the que to linkedlist in order to get the last element of the queue
			LinkedList<Order> q = new LinkedList<Order>(((QueueCustomer) arg1).get_queue());	
			if( model.size() > q.size()){
				model.remove(0);
			} else if(model.size() < q.size()){

				String name = q.getLast().getCustomerName();
				int items = q.getLast().getItems().size();			

				String combo = name + " " + String.valueOf(items) + " item(s)";
				
				model.addElement(combo);
			}
			LinkedList<Order> online_q = new LinkedList<Order>(((QueueCustomer) arg1).get_online());
			if( modelOnline.size() > online_q.size()){
				modelOnline.remove(0);
			} // checks and gets the online order from the queue
			else if(modelOnline.size() < online_q.size()){
				String name = online_q.getLast().getCustomerName();
				int items = online_q.getLast().getItems().size();				
				String combo = name + " " + String.valueOf(items) + " item(s)";
				modelOnline.addElement(combo);
				}			
		}
		
		// if the observable is staff, then it displays the order's information of the customer from the queue
		else if (arg1 instanceof Staff){
			
			int server_no = ((Staff) arg1).getNumber();
			System.out.println(server_no);
			String displayOrder = ((Staff) arg1).getGUIDisplay();
			
			switch (server_no){
			case 1: 
				textArea_1.setText(displayOrder);
				break;
			case 2: 
				textArea_2.setText(displayOrder);
		    	break;
			case 3:
				textArea_3.setText(displayOrder);
				break;
			case 4:
				textArea_4.setText(displayOrder);
				break;
			case 5:
				textArea_5.setText(displayOrder);
				break;
			default:
				break;

			}

		}
			
		
	}
	
	// this is to add server
	public void addServer(ActionListener e) {
		btnAddServer.addActionListener(e);
	}
	
	// this is to remove server
	public void removeServer(ActionListener e) {
		btnRemoveServer.addActionListener(e);
	}

	// this is to add simulation speed
	public void addSpeedListener(ChangeListener e){
		simulationSpeedSlider.addChangeListener(e);	
	}
}
