import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class EditPanel extends JPanel{//단어파일 저장하는 곳
	private JTextField edit = new JTextField(15);
	private JLabel title = new JLabel("공부하고 싶은 단어를 입력");
	private TextSource textSource = new TextSource();
	private FileWriter fout = null;
	private Vector<String> v = textSource.getV();
	private JList<String> scrollList = new JList<String>(v);
	
	public EditPanel() {
		setBackground(Color.GRAY);
		setLayout(null);
		title.setSize(500, 30);
		title.setLocation(30, 30);
		title.setFont(new Font("Gothic", Font.BOLD, 20));
		title.setForeground(Color.BLACK);
		add(title);
		
		edit.setSize(250, 30);
		edit.setLocation(30, 70);
		add(edit);
		
		
		scrollList.setVisibleRowCount(10);
		scrollList.setFixedCellWidth(100);
		JScrollPane wordList = new JScrollPane(scrollList);
		wordList.setSize(250, 150);
		wordList.setLocation(30, 120);
		add(wordList);
		
		edit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					fout = new FileWriter("C:\\자바학습\\프로젝트\\MiniProject\\study.txt", true);
					JTextField t = (JTextField)(e.getSource());
					String addWord = t.getText();//단어 읽어오기
					t.setText("");
					fout.write(addWord);//읽은 문자열 파일에 저장
					fout.write("\r\n", 0, 2);
					fout.close();
					v.add(addWord);
					scrollList.setListData(v);
				}
				catch (IOException e1) {
					return;
				}
			}
		});
	}
}
