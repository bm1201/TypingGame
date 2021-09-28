import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;

public class GameFrame extends JFrame{
	//JMenu ����
	private JMenuItem startItem = new JMenuItem("start");
	private JMenuItem stopItem = new JMenuItem("stop");
	private JMenuItem exitItem = new JMenuItem("exit");
	private JMenuItem level1Item = new JMenuItem("Level1");
	private JMenuItem level2Item = new JMenuItem("Level2");
	private JMenuItem level3Item = new JMenuItem("Level3");
	private JMenuItem level4Item = new JMenuItem("Level4");
	private JMenuItem level5Item = new JMenuItem("Level5");
	
	//JToolBar ����
	private JButton startBtn = new JButton("start");
	private JButton stopBtn = new JButton("stop");
	
	//Panel ����
	private ScorePanel scorePanel = new ScorePanel();
	private EditPanel editPanel = new EditPanel();
	private GamePanel gamePanel = new GamePanel(scorePanel, editPanel);

	public GameFrame() {//GameFrame������
		setTitle("Ÿ���� ����");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1200, 800);
		splitPane();//JSplitPane�� �����Ͽ� ����Ʈ���� ���Ϳ� ����
		makeMenu();//�޴��� ����
		makeToolBar();//����
		setResizable(false);//ũ�⺯��ȵ�
		setVisible(true);
	}
	
	private void splitPane() {//�⺻Ʋ
		JSplitPane hPane = new JSplitPane();
		getContentPane().add(hPane,BorderLayout.CENTER);
		hPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);//�������� ������
		hPane.setDividerLocation(850);
		hPane.setEnabled(false);
		hPane.setLeftComponent(gamePanel);//���ʿ��� �����г�
		
		JSplitPane pPane = new JSplitPane();
		pPane.setOrientation(JSplitPane.VERTICAL_SPLIT);//�������� ������
		pPane.setDividerLocation(400);
		pPane.setEnabled(false);
		pPane.setTopComponent(scorePanel);//������ �����г�
		//����߰�
		pPane.setBottomComponent(editPanel);//�Ʒ����� �ܾ��߰��г�
		hPane.setRightComponent(pPane);//�������гο��� �����гΰ� �ܾ��߰��г�
		
	}
	
	private void makeMenu() {//�޴���
		JMenuBar mBar = new JMenuBar();//JMenu����
		setJMenuBar(mBar);
		
		JMenu fileMenu = new JMenu("Game");//Game�޴�
		fileMenu.add(startItem);
		fileMenu.add(stopItem);
		fileMenu.addSeparator();
		fileMenu.add(exitItem);
		mBar.add(fileMenu);
		
		JMenu levelMenu = new JMenu("Level");//�������ø޴�
		levelMenu.add(level1Item);
		levelMenu.add(level2Item);
		levelMenu.add(level3Item);
		levelMenu.add(level4Item);
		levelMenu.add(level5Item);
		mBar.add(levelMenu);
		
		
		startItem.addActionListener(new StartAction());//start�� ������ ������ ����
		stopItem.addActionListener(new StopAction());//stop�� ������ ��������
		
		exitItem.addActionListener(new ActionListener() {//exit�� ������ ���α׷� ����
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		level1Item.addActionListener(new ActionListener() {//1�ܰ�
			public void actionPerformed(ActionEvent e) {
				gamePanel.startGame();
				startBtn.setEnabled(false);
				startItem.setEnabled(false);
				level1Item.setEnabled(false);
				level2Item.setEnabled(false);
				level3Item.setEnabled(false);
				level4Item.setEnabled(false);
				level5Item.setEnabled(false);
				stopBtn.setEnabled(true);
				stopItem.setEnabled(true);
			}
		});
		
		level2Item.addActionListener(new ActionListener() {//2�ܰ�
			public void actionPerformed(ActionEvent e) {
				gamePanel.level2Game();
				startBtn.setEnabled(false);
				startItem.setEnabled(false);
				level1Item.setEnabled(false);
				level2Item.setEnabled(false);
				level3Item.setEnabled(false);
				level4Item.setEnabled(false);
				level5Item.setEnabled(false);
				stopBtn.setEnabled(true);
				stopItem.setEnabled(true);
			}
		});
		
		level3Item.addActionListener(new ActionListener() {//3�ܰ�
			public void actionPerformed(ActionEvent e) {
				gamePanel.level3Game();
				startBtn.setEnabled(false);
				startItem.setEnabled(false);
				level1Item.setEnabled(false);
				level2Item.setEnabled(false);
				level3Item.setEnabled(false);
				level4Item.setEnabled(false);
				level5Item.setEnabled(false);
				stopBtn.setEnabled(true);
				stopItem.setEnabled(true);
			}
		});
		
		level4Item.addActionListener(new ActionListener() {//4�ܰ�
			public void actionPerformed(ActionEvent e) {
				gamePanel.level4Game();
				startBtn.setEnabled(false);
				startItem.setEnabled(false);
				level1Item.setEnabled(false);
				level2Item.setEnabled(false);
				level3Item.setEnabled(false);
				level4Item.setEnabled(false);
				level5Item.setEnabled(false);
				stopBtn.setEnabled(true);
				stopItem.setEnabled(true);
			}
		});
		
		level5Item.addActionListener(new ActionListener() {//5�ܰ�
			public void actionPerformed(ActionEvent e) {
				gamePanel.level5Game();
				startBtn.setEnabled(false);
				startItem.setEnabled(false);
				level1Item.setEnabled(false);
				level2Item.setEnabled(false);
				level3Item.setEnabled(false);
				level4Item.setEnabled(false);
				level5Item.setEnabled(false);
				stopBtn.setEnabled(true);
				stopItem.setEnabled(true);
			}
		});
	}
	
	private void makeToolBar() {//ToolBar�޴�
		JToolBar tBar = new JToolBar();
		tBar.add(startBtn);
		tBar.add(stopBtn);
		getContentPane().add(tBar, BorderLayout.NORTH);
		
		startBtn.addActionListener(new StartAction());//start�� ������ ������ ����
		stopBtn.addActionListener(new StopAction());//stop�� ������ ������ ����
	}
	
	private class StartAction implements ActionListener {//start ��ư�� ������ �߻�
		public void actionPerformed(ActionEvent e) {
			gamePanel.startGame();
			startBtn.setEnabled(false);
			startItem.setEnabled(false);
			level1Item.setEnabled(false);
			level2Item.setEnabled(false);
			level3Item.setEnabled(false);
			level4Item.setEnabled(false);
			level5Item.setEnabled(false);
			stopBtn.setEnabled(true);
			stopItem.setEnabled(true);
		}
	}
	
	private class StopAction implements ActionListener {//stop ��ư�� ������ �߻�
		public void actionPerformed(ActionEvent e) {
			gamePanel.stopGame();
			stopBtn.setEnabled(false);
			stopItem.setEnabled(false);
			startBtn.setEnabled(true);
			startItem.setEnabled(true);
			level1Item.setEnabled(true);
			level2Item.setEnabled(true);
			level3Item.setEnabled(true);
			level4Item.setEnabled(true);
			level5Item.setEnabled(true);
		}
	}
}
