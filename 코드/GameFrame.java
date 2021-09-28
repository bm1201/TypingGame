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
	//JMenu 값들
	private JMenuItem startItem = new JMenuItem("start");
	private JMenuItem stopItem = new JMenuItem("stop");
	private JMenuItem exitItem = new JMenuItem("exit");
	private JMenuItem level1Item = new JMenuItem("Level1");
	private JMenuItem level2Item = new JMenuItem("Level2");
	private JMenuItem level3Item = new JMenuItem("Level3");
	private JMenuItem level4Item = new JMenuItem("Level4");
	private JMenuItem level5Item = new JMenuItem("Level5");
	
	//JToolBar 값들
	private JButton startBtn = new JButton("start");
	private JButton stopBtn = new JButton("stop");
	
	//Panel 값들
	private ScorePanel scorePanel = new ScorePanel();
	private EditPanel editPanel = new EditPanel();
	private GamePanel gamePanel = new GamePanel(scorePanel, editPanel);

	public GameFrame() {//GameFrame생성자
		setTitle("타이핑 게임");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1200, 800);
		splitPane();//JSplitPane을 생성하여 컨텐트팬의 센터에 부착
		makeMenu();//메뉴를 만듬
		makeToolBar();//툴바
		setResizable(false);//크기변경안됨
		setVisible(true);
	}
	
	private void splitPane() {//기본틀
		JSplitPane hPane = new JSplitPane();
		getContentPane().add(hPane,BorderLayout.CENTER);
		hPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);//수직으로 나누기
		hPane.setDividerLocation(850);
		hPane.setEnabled(false);
		hPane.setLeftComponent(gamePanel);//왼쪽에는 게임패널
		
		JSplitPane pPane = new JSplitPane();
		pPane.setOrientation(JSplitPane.VERTICAL_SPLIT);//수평으로 나누기
		pPane.setDividerLocation(400);
		pPane.setEnabled(false);
		pPane.setTopComponent(scorePanel);//위에는 점수패널
		//기능추가
		pPane.setBottomComponent(editPanel);//아래에는 단어추가패널
		hPane.setRightComponent(pPane);//오른쪽패널에는 점수패널과 단어추가패널
		
	}
	
	private void makeMenu() {//메뉴바
		JMenuBar mBar = new JMenuBar();//JMenu생성
		setJMenuBar(mBar);
		
		JMenu fileMenu = new JMenu("Game");//Game메뉴
		fileMenu.add(startItem);
		fileMenu.add(stopItem);
		fileMenu.addSeparator();
		fileMenu.add(exitItem);
		mBar.add(fileMenu);
		
		JMenu levelMenu = new JMenu("Level");//레벨선택메뉴
		levelMenu.add(level1Item);
		levelMenu.add(level2Item);
		levelMenu.add(level3Item);
		levelMenu.add(level4Item);
		levelMenu.add(level5Item);
		mBar.add(levelMenu);
		
		
		startItem.addActionListener(new StartAction());//start을 누르면 게임이 시작
		stopItem.addActionListener(new StopAction());//stop을 누르면 게임종료
		
		exitItem.addActionListener(new ActionListener() {//exit을 누르면 프로그램 종료
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		level1Item.addActionListener(new ActionListener() {//1단계
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
		
		level2Item.addActionListener(new ActionListener() {//2단계
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
		
		level3Item.addActionListener(new ActionListener() {//3단계
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
		
		level4Item.addActionListener(new ActionListener() {//4단계
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
		
		level5Item.addActionListener(new ActionListener() {//5단계
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
	
	private void makeToolBar() {//ToolBar메뉴
		JToolBar tBar = new JToolBar();
		tBar.add(startBtn);
		tBar.add(stopBtn);
		getContentPane().add(tBar, BorderLayout.NORTH);
		
		startBtn.addActionListener(new StartAction());//start를 누르면 게임이 시작
		stopBtn.addActionListener(new StopAction());//stop을 누르면 게임이 종료
	}
	
	private class StartAction implements ActionListener {//start 버튼을 누르면 발생
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
	
	private class StopAction implements ActionListener {//stop 버튼을 누르면 발생
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
