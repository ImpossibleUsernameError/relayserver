import javax.swing.*;

/**
 * Created by Michael on 24.02.2018.
 */
public class MainWindow {

	public MainWindow(){
		ta_main.enableInputMethods(false);
		ta_main.setEditable(false);
	}

	private JPanel jp_main;
	private JTextArea ta_main;

	public JPanel getJp_main(){
		return jp_main;
	}

	public void appendTextToMainView(String text){

		ta_main.setText(ta_main.getText() + "\n" + text);
	}
}
