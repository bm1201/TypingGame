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
	
	public TextSource() {//파일에서 읽기
		InputStreamReader in = null;
		FileInputStream fin = null;
		
		try {
			fin = new FileInputStream("study.txt");
			in = new InputStreamReader(fin, "MS949");
			
			int c;
			StringBuffer sb = new StringBuffer();
			while((c = in.read()) != -1) {//파일 끝까지 읽기
				if(c == '\n') {//단어가 넘어갈때
					v.add(sb.toString().trim());//현재단어 벡터에 붙이기
					sb.setLength(0);//StringBuffer초기화
				}
				else {//StringBuffer에 채우기
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

	public String get() {//get을 부르면 단어를 하나줌
		int index = (int)(Math.random()*v.size());
		return v.get(index);
	}
	
	public Vector getV() {
		return v;
	}
}
