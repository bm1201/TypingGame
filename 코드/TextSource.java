import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.JLabel;

public class TextSource {
	private Vector<String> v = new Vector<String>(); 
	
	public TextSource() {//���Ͽ��� �б�
		InputStreamReader in = null;
		FileInputStream fin = null;
		
		try {
			fin = new FileInputStream("study.txt");
			in = new InputStreamReader(fin, "MS949");
			
			int c;
			StringBuffer sb = new StringBuffer();
			while((c = in.read()) != -1) {//���� ������ �б�
				if(c == '\n') {//�ܾ �Ѿ��
					v.add(sb.toString().trim());//����ܾ� ���Ϳ� ���̱�
					sb.setLength(0);//StringBuffer�ʱ�ȭ
				}
				else {//StringBuffer�� ä���
					sb.append((char)c);
				}
			}
			
			if(sb.length() != 0) {
				v.add(sb.toString());
			}
		} 
		
		catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
		try {
			in.close();
			fin.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String get() {//get�� �θ��� �ܾ �ϳ���
		int index = (int)(Math.random()*v.size());
		return v.get(index);
	}
	
	public Vector getV() {
		return v;
	}
}
