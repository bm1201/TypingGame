import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ScorePanel extends JPanel {
	//게임하는 사람의 점수
	private int score = 0;
	private JTextField playerName = new JTextField(50);
	private JLabel textLabel = new JLabel("내 점수");
	private JLabel scoreLabel = new JLabel(Integer.toString(score));
	private JLabel guideLabel = new JLabel("게임종료 후 이름을 입력하세요");
	
	//기록된 사람들의 점수
	private JLabel [] rankLabel = new JLabel[5];
	private JLabel [] rankPlayerName = new JLabel[5];
	private JLabel [] rankScoreLabel = new JLabel[5];
	
	private FileWriter fout = null;
	private HashMap<Integer, String> scoreMap = new HashMap<Integer, String>();
	private Vector<Integer> v= new Vector<Integer>();

	public ScorePanel() {
		
		setBackground(Color.BLACK);
		setLayout(null);
		//점수라벨
		textLabel.setSize(100, 30);
		textLabel.setLocation(70, 15);
		textLabel.setFont(new Font("Gothic", Font.BOLD, 25));
		textLabel.setForeground(Color.WHITE);
		add(textLabel);
		
		//score라벨
		scoreLabel.setSize(100, 30);
		scoreLabel.setLocation(200, 15);
		scoreLabel.setFont(new Font("Gothic", Font.BOLD, 25));
		scoreLabel.setForeground(Color.WHITE);
		add(scoreLabel);
		
		//등수라벨
		for(int i=0; i<rankLabel.length; i++) {
			rankLabel[i] = new JLabel((i+1) + "st");
			rankLabel[i].setSize(100, 30);
			rankLabel[i].setLocation(30, (i * 50) + 70);
			rankLabel[i].setFont(new Font("Gothic", Font.BOLD, 25));
			rankLabel[i].setForeground(Color.WHITE);
			add(rankLabel[i]);
		}
		
		getPlayerScore();//해쉬맵으로 읽어와서 점수벡터 만듬
		compareScore();
		//플레이어이름
		for(int i=0; i<rankPlayerName.length; i++) {
			rankPlayerName[i] = new JLabel(scoreMap.get(v.get(i)));
			rankPlayerName[i].setSize(100, 30);
			rankPlayerName[i].setLocation(100, (i * 50) + 70);
			rankPlayerName[i].setFont(new Font("Gothic", Font.BOLD, 25));
			rankPlayerName[i].setForeground(Color.WHITE);
			add(rankPlayerName[i]);
		}
		
		//등수별 점수
		for(int i=0; i<rankLabel.length; i++) {
			rankScoreLabel[i] = new JLabel(Integer.toString(v.get(i)));
			rankScoreLabel[i].setSize(100, 20);
			rankScoreLabel[i].setLocation(220, (i * 50) + 73);
			rankScoreLabel[i].setFont(new Font("Gothic", Font.BOLD, 25));
			rankScoreLabel[i].setForeground(Color.WHITE);
			add(rankScoreLabel[i]);
		}
		
		//안내라벨
		guideLabel.setSize(500, 30);
		guideLabel.setLocation(20, 315);
		guideLabel.setFont(new Font("Gothic", Font.BOLD, 20));
		guideLabel.setForeground(Color.WHITE);
		add(guideLabel);
		
		//player이름 입력하는 곳
		playerName.setSize(200, 30);
		playerName.setLocation(50, 350);
		add(playerName);
		
		playerName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JTextField t = (JTextField)e.getSource();
				String playerName = t.getText();
				saveScore(playerName);
				initScore();
				t.setText("");
			}
		});
	}
	
	public void initScore() {//스코어를 초기화
		score = 0;
		scoreLabel.setText(Integer.toString(score));
	}
	
	public void increase() {//점수가 증가하는 기능
		score = score + 10;
		scoreLabel.setText(Integer.toString(score));
	}
	
	public void decrease() {//점수가 감소하는 기능
		score = score - 30;
		scoreLabel.setText(Integer.toString(score));
	}
	
	public void bonusIncrease() {//보너스 점수 기능
		score = score + 50;
		scoreLabel.setText(Integer.toString(score));
	}
	
	//게임이 끝나고 이름과 점수를 기록하는 기능
	public void saveScore(String playerName) {
		try {
			fout = new FileWriter("C:\\자바학습\\프로젝트\\MiniProject\\score.txt", true);//파일과 연결된 출력문자 스트림 생성
			fout.write(playerName + " ");
			fout.write(Integer.toString(score));
			fout.write("\r\n", 0, 2);
			fout.close();
		}
		catch (IOException e) {
			System.out.println("입출력오류");
		}
	}
	
	//모든 플레이어의 점수를 벡터에 저장
	public void getPlayerScore() {
		try {
			Scanner fscanner = new Scanner(new FileReader(new File("C:\\자바학습\\프로젝트\\MiniProject\\score.txt")));
			while (fscanner.hasNext()) {
				String name = fscanner.next();
				int score = fscanner.nextInt();
				scoreMap.put(score, name);
			}
			fscanner.close();
		}
		catch (IOException e) {
			return;
		}
		
		//모든 점수를 벡터에 저장
		Set<Integer> keys = scoreMap.keySet();
		Iterator<Integer> it = keys.iterator();

		while(it.hasNext()) {
			int score = it.next();
			v.add(score);
		}
	}
	
	public void compareScore() {//내림차순으로 정
		for(int i=0; i<v.size(); i++) {
			for(int j=0; j<i; j++) {
				if(v.get(i) > v.get(j)) {
					v.add(j, v.get(i));
					v.remove(i+1);
				}
			}	
		}
	}
}
