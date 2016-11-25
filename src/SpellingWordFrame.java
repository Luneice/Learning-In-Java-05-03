import java.awt.*;
import java.awt.event.*;
import javax.swing.Box;
public class SpellingWordFrame extends Frame implements KeyListener,
		ActionListener {
	TextField inputWord;
	Button button;
	LetterLabel label[];
	Panel northP, centerP;
	Box wordBox;
	String hintMessage = "用鼠标单击字母，按左右箭头交换字母，将其排列成所输入的单词";
	Label messaageLabel = new Label(hintMessage);
	String word = "";
	
	SpellingWordFrame() {
		setTitle("英语单词拼写训练");
		inputWord = new TextField(12);
		button = new Button("确定");
		button.addActionListener(this);
		inputWord.addActionListener(this);
		northP = new Panel();
		northP.add(new Label("输入一个英文单词："));
		northP.add(inputWord);
		northP.add(button);
		centerP = new Panel();
		wordBox = Box.createHorizontalBox();
		centerP.add(wordBox);
		add(northP, BorderLayout.NORTH);
		add(centerP, BorderLayout.CENTER);
		add(messaageLabel, BorderLayout.SOUTH);
		setBounds(100, 100, 350, 180);
		setVisible(true);
		validate();
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
	public void actionPerformed(ActionEvent e) {
		word = inputWord.getText();
		int n = word.length();
		RandomString rondom = new RandomString();
		String randomWord = rondom.getRandomString(word);
		wordBox.removeAll();
		messaageLabel.setText(hintMessage);
		if (n > 0) {
			label = LetterLabel.getLetterLabel(n);
			for (int k = 0; k < label.length; k++) {
				label[k].addKeyListener(this);// 将当前窗口注册为label[k]的键盘监视器
				label[k].setLabel("" + randomWord.charAt(k));
				wordBox.add(label[k]);
			}
			validate();
			inputWord.setText(null);
			label[0].requestFocus();
		}
	}
	
	//键盘监听器
	public void keyPressed(KeyEvent e) {
		LetterLabel sourceLabel = (LetterLabel) e.getSource();
		int index = -1;
		if (e.getKeyCode()==KeyEvent.VK_LEFT) // 判断按下的是否是←键)
		{
			for (int k = 0; k < label.length; k++) {
				if (label[k] == sourceLabel) {
					index = k;
					break;
				}
			}
			if (index != 0) {
				String temp = label[index].getLabel();
				label[index].setLabel(label[index - 1].getLabel());
				label[index - 1].setLabel(temp);
				label[index - 1].requestFocus();
			}
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) // 判断按下的是否是→键
		{
			for (int k = 0; k < label.length; k++) {
				if (label[k] == sourceLabel) {
					index = k;
					break;
				}
			}
			if (index != label.length - 1) {
				String temp = label[index].getLabel();
				label[index].setLabel(label[index + 1].getLabel());
				label[index + 1].setLabel(temp);
				label[index + 1].requestFocus();
			}
		}
		validate();
	}
	public void keyTyped(KeyEvent e) {
	}
	public void keyReleased(KeyEvent e) {
		String success = "";
		for (int k = 0; k < label.length; k++) {
			String str = label[k].getLabel();
			success = success + str;
		}
		if (success.equals(word)) {
			messaageLabel.setText("恭喜你，你成功了");
			for (int k = 0; k < label.length; k++) {
				label[k].removeKeyListener(this);//移除label[k]的所注册的键盘监视器
				label[k].removeFocusListener(label[k]);//移除label[k]的所注册的焦点监视器
				label[k].setBackground(Color.green);
			}
			inputWord.requestFocus();
		}
	}
}
