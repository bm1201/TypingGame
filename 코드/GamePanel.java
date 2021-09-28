import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GamePanel extends JPanel {
	private JTextField input = new JTextField(20);//사용자가 단어입력하는 곳
	private JLabel [] levelLabel = new JLabel[5];//사용자가 선택한 레벨 텍스트
	private static int life = 5;
	private JLabel [] blackText = new JLabel[500];//JLabel 객체를 가르키는 레퍼런스 변수 text
	private JLabel [] lifeLabel = new JLabel[5];//목숨
	
	//그림아이콘들
	private ImageIcon backgroundIcon = new ImageIcon("start.jpg");
	private Image backgroundImage = backgroundIcon.getImage();
	private ImageIcon lifeIcon = new ImageIcon("life2.jpg");
	
	//패널
	private ScorePanel scorePanel = null;
	private EditPanel editPanel = null;
	private TextSource textSource = new TextSource();//단어벡터 생성
	private GameGroundPanel gameGroundPanel = null;
	private InputPanel inputPanel = null;
	
	//스레드
	private WordMakeThread makeWord = null;//단어만드는 스레드
	private WordDownThread [] downWord = new WordDownThread[50];//단어가 떨어지는 스레드
	private StopMakeThread stopMakeThread = null;//만드는 스레드 멈춘 후 재실행
	private StopDownThread stopDownThread = null;//떨어지는 스레드 잠시 멈춤
	
	//게임리셋에 필요힌 변수
	private static int wordCount;
	private static int makeDelay, downDelay, downSpeed;
	
	public GamePanel(ScorePanel scorePanel, EditPanel editPanel) {//게임패널틀
		this.scorePanel = scorePanel;
		this.editPanel = editPanel;
		
		for(int i=0; i<lifeLabel.length; i++) {//생명라벨
			lifeLabel[i] = new JLabel(lifeIcon);
		}
		
		for(int i=0; i<levelLabel.length; i++) {//레벨
			levelLabel[i] = new JLabel("Level" + Integer.toString(i+1));
			levelLabel[i].setFont(new Font("Gothic", Font.BOLD, 20));
		}
		
		//게임단어생성
		initText();
		
		//각각의 패널들 붙이기
		gameGroundPanel = new GameGroundPanel();
		inputPanel = new InputPanel();
		setLayout(new BorderLayout());
		add(gameGroundPanel, BorderLayout.CENTER);
		add(inputPanel, BorderLayout.SOUTH);
		
		//단어 맞췄을 때 점수변경기능 및 보너스기능
		input.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JTextField t = (JTextField)(e.getSource());
				String inWord = t.getText();
				for(int i=0; i<wordCount; i++) {
					if(blackText[i].getForeground() == Color.RED) {//빨간단어
						if(blackText[i].getText().equals(inWord)) {//맞추기 성공
							gameGroundPanel.remove(blackText[i]);//그 단어는 지움
							downWord[i].interrupt();
							gameGroundPanel.repaint();
							scorePanel.decrease();
							t.setText("");
						}
					}
					else if(blackText[i].getForeground() == Color.BLUE) {//파란단어
						if(blackText[i].getText().equals(inWord)) {
							gameGroundPanel.remove(blackText[i]);//그 단어는 지움
							scorePanel.bonusIncrease();
							t.setText("");
							makeWord.interrupt();
							for(int j=0; j<wordCount; j++) {
								if(downWord[j] != null) {//진행중인 스레드만
									downWord[j].interrupt();
									stopDownThread = new StopDownThread(j);
									stopDownThread.start();
								}
							}
							stopMakeThread = new StopMakeThread(); 
							stopMakeThread.start();
							gameGroundPanel.repaint();
						}
					}
					else {
						if(blackText[i].getText().equals(inWord)) {//맞추기 성공
							gameGroundPanel.remove(blackText[i]);//그 단어는 지움
							downWord[i].interrupt();
							gameGroundPanel.repaint();
							scorePanel.increase();
							t.setText("");
						}
					}
				}//end of for
			}
		});	
	}
	
	public void initText() {
		for(int i=0; i<blackText.length;i++) {//단어라벨
			blackText[i]= new JLabel(textSource.get());
		}
	}
	
	public void startGame() {//게임이 시작 기본값은 1단계랑 동일
		for(int i=1; i<5; i++) {
			inputPanel.remove(levelLabel[i]);
		}
		levelLabel[0].setVisible(true);
		inputPanel.repaint();
		wordCount = 0;
		makeDelay = 1000;
		downSpeed = 15;
		downDelay = 1000;
		makeWord = new WordMakeThread(inputPanel, makeDelay, downSpeed, downDelay);
		makeWord.start();
	}
	
	public void stopGame() {//게임이 종료
		makeWord.interrupt();//스레드 종료
		for(int i=0; i<wordCount; i++) {
			downWord[i].interrupt();
		}
		gameGroundPanel.removeAll();
		inputPanel.initInput();
		life = 5;
		initText();
		gameGroundPanel.repaint();
	}

	public void level2Game() {
		//level2표시
		inputPanel.remove(levelLabel[0]);
		inputPanel.remove(levelLabel[2]);
		inputPanel.remove(levelLabel[3]);
		inputPanel.remove(levelLabel[4]);
		levelLabel[1].setVisible(true);
		inputPanel.repaint();
		wordCount = 0;
		makeDelay = 1000;
		downSpeed = 20;
		downDelay = 1000;
		makeWord = new WordMakeThread(inputPanel, makeDelay, downSpeed, downDelay);
		makeWord.start();
	}
	
	public void level3Game() {
		inputPanel.remove(levelLabel[0]);
		inputPanel.remove(levelLabel[1]);
		inputPanel.remove(levelLabel[3]);
		inputPanel.remove(levelLabel[4]);
		levelLabel[2].setVisible(true);
		inputPanel.repaint();
		wordCount = 0;
		makeDelay = 1000;
		downSpeed = 30;
		downDelay = 1000;
		makeWord = new WordMakeThread(inputPanel, makeDelay, downSpeed, downDelay);
		makeWord.start();
	}
	
	public void level4Game() {
		inputPanel.remove(levelLabel[0]);
		inputPanel.remove(levelLabel[1]);
		inputPanel.remove(levelLabel[2]);
		inputPanel.remove(levelLabel[4]);
		levelLabel[3].setVisible(true);
		inputPanel.repaint();
		wordCount = 0;
		makeDelay = 1000;
		downSpeed = 40;
		downDelay = 1000;
		makeWord = new WordMakeThread(inputPanel, makeDelay, downSpeed, downDelay);
		makeWord.start();
	}
	
	public void level5Game() {
		inputPanel.remove(levelLabel[0]);
		inputPanel.remove(levelLabel[1]);
		inputPanel.remove(levelLabel[2]);
		inputPanel.remove(levelLabel[3]);
		levelLabel[4].setVisible(true);
		inputPanel.repaint();
		wordCount = 0;
		makeDelay = 1000;
		downSpeed = 50;
		downDelay = 1000;
		makeWord = new WordMakeThread(inputPanel, makeDelay, downSpeed, downDelay);
		makeWord.start();
	}

	//게임그라운드패널
	class GameGroundPanel extends JPanel {
		
		public GameGroundPanel() {//단어 게임이 진행되는 곳
			setLayout(null);
			
		}
		
		public void paintComponent(Graphics g) {
			g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), gameGroundPanel);
		}	
	}

	class WordMakeThread extends Thread{//단어 만드는 스레드
		private InputPanel inputPanel;
		private int makeDelay, downSpeed, downDelay;
		
		public WordMakeThread (InputPanel inputPanel, int makeDelay, int downSpeed, int downDelay) {
			this.inputPanel = inputPanel;
			this.makeDelay = makeDelay;
			this.downSpeed = downSpeed;
			this.downDelay = downDelay;
		}
		
		public void run() {//스레드 실행
			
			while(true) {
				if(wordCount != 0) {
					for(int i=wordCount; i<blackText.length; i++) {
						try {
							sleep(makeDelay);
						} 
						catch (InterruptedException e) {
							return;
						}
						boolean redText = ((int)(Math.random()*10) + 1) / 10 != 0;
						boolean blueText = ((int)(Math.random()*10) + 1) / 10 != 0;
						
						blackText[i].setSize(200,30);
						blackText[i].setLocation(50 + (int)(Math.random()*8)*100, 10);
						
						if(redText == true) {//빨간단어
							blackText[i].setForeground(Color.RED);
							blackText[i].setFont(new Font("Gothic", Font.BOLD, 18));
							gameGroundPanel.add(blackText[i]);
							downWord[i] = new WordDownThread(gameGroundPanel, blackText[i], downSpeed, downDelay);
							downWord[i].start();
							wordCount++;
							gameGroundPanel.repaint();
						}
						else if(blueText == true) {//파란단어
							blackText[i].setForeground(Color.BLUE);
							blackText[i].setFont(new Font("Gothic", Font.BOLD, 18));
							gameGroundPanel.add(blackText[i]);
							downWord[i] = new WordDownThread(gameGroundPanel, blackText[i], downSpeed, downDelay);
							downWord[i].start();
							wordCount++;
							gameGroundPanel.repaint();
						}
						else {//검은단어
							blackText[i].setForeground(Color.BLACK);
							blackText[i].setFont(new Font("Gothic", Font.BOLD, 18));
							gameGroundPanel.add(blackText[i]);
							downWord[i] = new WordDownThread(gameGroundPanel, blackText[i], downSpeed, downDelay);
							downWord[i].start();
							wordCount++;
							gameGroundPanel.repaint();
						}
					}//end of for
				}
				
				for(int i=0; i<blackText.length; i++) {
					try {
						sleep(makeDelay);
					} 
					catch (InterruptedException e) {
						return;
					}
					boolean redText = ((int)(Math.random()*10) + 1) / 10 != 0;
					boolean blueText = ((int)(Math.random()*10) + 1) / 10 != 0;
					
					blackText[i].setSize(200,30);
					blackText[i].setLocation(50 + (int)(Math.random()*8)*100, 10);
					
					if(redText == true) {//빨간단어
						blackText[i].setForeground(Color.RED);
						blackText[i].setFont(new Font("Gothic", Font.BOLD, 18));
						gameGroundPanel.add(blackText[i]);
						downWord[i] = new WordDownThread(gameGroundPanel, blackText[i], downSpeed, downDelay);
						downWord[i].start();
						wordCount++;
						gameGroundPanel.repaint();
					}
					else if(blueText == true) {//파란단어
						blackText[i].setForeground(Color.BLUE);
						blackText[i].setFont(new Font("Gothic", Font.BOLD, 18));
						gameGroundPanel.add(blackText[i]);
						downWord[i] = new WordDownThread(gameGroundPanel, blackText[i], downSpeed, downDelay);
						downWord[i].start();
						wordCount++;
						gameGroundPanel.repaint();
					}
					else {//검은단어
						blackText[i].setForeground(Color.BLACK);
						blackText[i].setFont(new Font("Gothic", Font.BOLD, 18));
						gameGroundPanel.add(blackText[i]);
						downWord[i] = new WordDownThread(gameGroundPanel, blackText[i], downSpeed, downDelay);
						downWord[i].start();
						wordCount++;
						gameGroundPanel.repaint();
					}
				}//end of for
			}//end of while
		}//end of run
	}
	
	//떨어지는 단어 스레드
	class WordDownThread extends Thread {
		private GameGroundPanel gameGroundPanel;
		private JLabel blackText;
		private int downSpeed, downDelay;
		
		public WordDownThread (GameGroundPanel gameGroundPanel, JLabel blackText, int downSpeed, int downDelay) {
			this.blackText = blackText;
			this.gameGroundPanel = gameGroundPanel;
			this.downSpeed = downSpeed;
			this.downDelay = downDelay;
		}
		
		public void run() {
			while(true) {
				try {
					sleep(downDelay);
				}
				catch (InterruptedException e) {
					return;
				}
				
				int y = blackText.getY() + 10;
				if(y >= gameGroundPanel.getHeight() - blackText.getHeight() - 20) {
					blackText.setText("");
					gameGroundPanel.repaint();
					if(blackText.getForeground() == Color.BLACK) {
						life--;
						inputPanel.changeLife();
						break;
					}
				}
				
				blackText.setLocation(blackText.getX(), blackText.getY() + downSpeed);
				gameGroundPanel.repaint();
				
			}
		}
	}
	
	class StopMakeThread extends Thread {

		public void run() {
			while(true) {
				try {
					sleep(2000);
				}
				catch (InterruptedException e) {
					return;
				}

				makeWord = new WordMakeThread(inputPanel, makeDelay, downSpeed, downDelay);
				makeWord.start();
				return;
			}
		}
	}
	
	class StopDownThread extends Thread {
		private int j;
		
		public StopDownThread(int j) {
			this.j = j;
		}
		
		public void run() {
			while(true) {
				try {
					sleep(2000);
				}
				catch (InterruptedException e) {
					return;
				}
				
				downWord[j] = new WordDownThread(gameGroundPanel, blackText[j], downSpeed, downDelay);
				downWord[j].start();
				return;
			}
		}
	}
	
	//단어입력패널
	class InputPanel extends JPanel {//사용자가 단어입력하는 곳

		public InputPanel() {
			setLayout(new FlowLayout());
			setBackground(Color.WHITE);
			for(int i=0; i<lifeLabel.length; i++) {
				add(lifeLabel[i]);
			}
			add(input);
			for(int i=0; i<levelLabel.length; i++) {
				add(levelLabel[i]);
				levelLabel[i].setVisible(false);
			}
		}
		
		public void initInput() {
			for(int i=0; i<lifeLabel.length; i++) {
				lifeLabel[i].setVisible(true);
			}
			add(input);
			for(int i=0; i<levelLabel.length; i++) {
				add(levelLabel[i]);
				levelLabel[i].setVisible(false);
			}
		}

		public void changeLife() {//하트변경하는 기능
			switch(life) {
			case 4:
				lifeLabel[4].setVisible(false);
				inputPanel.repaint();
				break;
			case 3:
				lifeLabel[3].setVisible(false);
				inputPanel.repaint();
				break;
			case 2:
				lifeLabel[2].setVisible(false);
				inputPanel.repaint();
				break;
			case 1:
				lifeLabel[1].setVisible(false);
				inputPanel.repaint();
				break;
			case 0:
				lifeLabel[0].setVisible(false);
				inputPanel.repaint();
				makeWord.interrupt();
				for(int i=0; i<wordCount; i++) {
					downWord[i].interrupt();
				}
				stopGame();
				gameGroundPanel.removeAll();
				gameGroundPanel.repaint();
			}
		}
	}
}
