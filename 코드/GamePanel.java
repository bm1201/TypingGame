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
	private JTextField input = new JTextField(20);//����ڰ� �ܾ��Է��ϴ� ��
	private JLabel [] levelLabel = new JLabel[5];//����ڰ� ������ ���� �ؽ�Ʈ
	private static int life = 5;
	private JLabel [] blackText = new JLabel[500];//JLabel ��ü�� ����Ű�� ���۷��� ���� text
	private JLabel [] lifeLabel = new JLabel[5];//���
	
	//�׸������ܵ�
	private ImageIcon backgroundIcon = new ImageIcon("start.jpg");
	private Image backgroundImage = backgroundIcon.getImage();
	private ImageIcon lifeIcon = new ImageIcon("life2.jpg");
	
	//�г�
	private ScorePanel scorePanel = null;
	private EditPanel editPanel = null;
	private TextSource textSource = new TextSource();//�ܾ�� ����
	private GameGroundPanel gameGroundPanel = null;
	private InputPanel inputPanel = null;
	
	//������
	private WordMakeThread makeWord = null;//�ܾ��� ������
	private WordDownThread [] downWord = new WordDownThread[50];//�ܾ �������� ������
	private StopMakeThread stopMakeThread = null;//����� ������ ���� �� �����
	private StopDownThread stopDownThread = null;//�������� ������ ��� ����
	
	//���Ӹ��¿� �ʿ��� ����
	private static int wordCount;
	private static int makeDelay, downDelay, downSpeed;
	
	public GamePanel(ScorePanel scorePanel, EditPanel editPanel) {//�����г�Ʋ
		this.scorePanel = scorePanel;
		this.editPanel = editPanel;
		
		for(int i=0; i<lifeLabel.length; i++) {//�����
			lifeLabel[i] = new JLabel(lifeIcon);
		}
		
		for(int i=0; i<levelLabel.length; i++) {//����
			levelLabel[i] = new JLabel("Level" + Integer.toString(i+1));
			levelLabel[i].setFont(new Font("Gothic", Font.BOLD, 20));
		}
		
		//���Ӵܾ����
		initText();
		
		//������ �гε� ���̱�
		gameGroundPanel = new GameGroundPanel();
		inputPanel = new InputPanel();
		setLayout(new BorderLayout());
		add(gameGroundPanel, BorderLayout.CENTER);
		add(inputPanel, BorderLayout.SOUTH);
		
		//�ܾ� ������ �� ���������� �� ���ʽ����
		input.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JTextField t = (JTextField)(e.getSource());
				String inWord = t.getText();
				for(int i=0; i<wordCount; i++) {
					if(blackText[i].getForeground() == Color.RED) {//�����ܾ�
						if(blackText[i].getText().equals(inWord)) {//���߱� ����
							gameGroundPanel.remove(blackText[i]);//�� �ܾ�� ����
							downWord[i].interrupt();
							gameGroundPanel.repaint();
							scorePanel.decrease();
							t.setText("");
						}
					}
					else if(blackText[i].getForeground() == Color.BLUE) {//�Ķ��ܾ�
						if(blackText[i].getText().equals(inWord)) {
							gameGroundPanel.remove(blackText[i]);//�� �ܾ�� ����
							scorePanel.bonusIncrease();
							t.setText("");
							makeWord.interrupt();
							for(int j=0; j<wordCount; j++) {
								if(downWord[j] != null) {//�������� �����常
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
						if(blackText[i].getText().equals(inWord)) {//���߱� ����
							gameGroundPanel.remove(blackText[i]);//�� �ܾ�� ����
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
		for(int i=0; i<blackText.length;i++) {//�ܾ��
			blackText[i]= new JLabel(textSource.get());
		}
	}
	
	public void startGame() {//������ ���� �⺻���� 1�ܰ�� ����
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
	
	public void stopGame() {//������ ����
		makeWord.interrupt();//������ ����
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
		//level2ǥ��
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

	//���ӱ׶����г�
	class GameGroundPanel extends JPanel {
		
		public GameGroundPanel() {//�ܾ� ������ ����Ǵ� ��
			setLayout(null);
			
		}
		
		public void paintComponent(Graphics g) {
			g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), gameGroundPanel);
		}	
	}

	class WordMakeThread extends Thread{//�ܾ� ����� ������
		private InputPanel inputPanel;
		private int makeDelay, downSpeed, downDelay;
		
		public WordMakeThread (InputPanel inputPanel, int makeDelay, int downSpeed, int downDelay) {
			this.inputPanel = inputPanel;
			this.makeDelay = makeDelay;
			this.downSpeed = downSpeed;
			this.downDelay = downDelay;
		}
		
		public void run() {//������ ����
			
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
						
						if(redText == true) {//�����ܾ�
							blackText[i].setForeground(Color.RED);
							blackText[i].setFont(new Font("Gothic", Font.BOLD, 18));
							gameGroundPanel.add(blackText[i]);
							downWord[i] = new WordDownThread(gameGroundPanel, blackText[i], downSpeed, downDelay);
							downWord[i].start();
							wordCount++;
							gameGroundPanel.repaint();
						}
						else if(blueText == true) {//�Ķ��ܾ�
							blackText[i].setForeground(Color.BLUE);
							blackText[i].setFont(new Font("Gothic", Font.BOLD, 18));
							gameGroundPanel.add(blackText[i]);
							downWord[i] = new WordDownThread(gameGroundPanel, blackText[i], downSpeed, downDelay);
							downWord[i].start();
							wordCount++;
							gameGroundPanel.repaint();
						}
						else {//�����ܾ�
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
					
					if(redText == true) {//�����ܾ�
						blackText[i].setForeground(Color.RED);
						blackText[i].setFont(new Font("Gothic", Font.BOLD, 18));
						gameGroundPanel.add(blackText[i]);
						downWord[i] = new WordDownThread(gameGroundPanel, blackText[i], downSpeed, downDelay);
						downWord[i].start();
						wordCount++;
						gameGroundPanel.repaint();
					}
					else if(blueText == true) {//�Ķ��ܾ�
						blackText[i].setForeground(Color.BLUE);
						blackText[i].setFont(new Font("Gothic", Font.BOLD, 18));
						gameGroundPanel.add(blackText[i]);
						downWord[i] = new WordDownThread(gameGroundPanel, blackText[i], downSpeed, downDelay);
						downWord[i].start();
						wordCount++;
						gameGroundPanel.repaint();
					}
					else {//�����ܾ�
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
	
	//�������� �ܾ� ������
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
	
	//�ܾ��Է��г�
	class InputPanel extends JPanel {//����ڰ� �ܾ��Է��ϴ� ��

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

		public void changeLife() {//��Ʈ�����ϴ� ���
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
