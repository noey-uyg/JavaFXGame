package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ReactionRateTest implements Initializable{
	
	private Stage stage;
	
    @FXML
    private Canvas canvas;
    GraphicsContext gc;
    
    @FXML
    private Text outputbig;

    @FXML
    private Text outputsmall;

    boolean ready,red,green,err,end; //Ŭ���� ȭ�� �Ǻ��ϱ� ����
    
    Timer timer = new Timer();
    
    long timerate,starttime,endtime,timeminus,sumtime;
    int count=1;
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		gc = canvas.getGraphicsContext2D();
		ReadyScreen();
	}
	
    @FXML
    void ClickCanvas(MouseEvent event) {//ȭ��Ŭ����
    	if(count>=5) {
			endScreen();
		}
    	else {
    		if(ready) {
        		RedScreen();
        		screentime();
        	}
        	else if(red) {
        		errorScreen();
        	}
        	else if(err) {
        		ReadyScreen();
        	}
        	else if(green) {
        		endtime = System.currentTimeMillis();//Ŭ���� �� ������ �ð�
        		timeminus = endtime - starttime;
            	sumtime+=timeminus;
        		RedScreen();
        		count++;
        	}
        	else if(end) {
        		ReadyScreen();
        	}
    	}
    	
    } 
    
    void GreenScreen() {//Ŭ��ȭ��
		starttime = System.currentTimeMillis();//�ʷ�ȭ���� �� ������ �ð�
		
    	gc.setFill(Color.GREEN);
    	gc.fillRect(0, 0, 500, 500);
    	
		outputbig.setText("Ŭ��");
		outputsmall.setText("Ŭ�����ּ���!");
		
		ready=false;
		red=false;
		green=true;
		err=false;
		end=false;
    }
    
    void errorScreen() {//�غ�ȭ�鿡 ������ �� ����ȭ��
    	gc.setFill(Color.GRAY);
    	gc.fillRect(0, 0, 500, 500);
    	
		outputbig.setText("����ȭ�鿡�� Ŭ������ !");
		outputsmall.setText("ȭ���� Ŭ���Ͽ� ó������ ���ư��ּ���.");
		
		ready=false;
		red=false;
		green=false;
		err=true;
		end=false;
    }
    
    void RedScreen() {//�غ�ȭ��
    	gc.setFill(Color.RED);
    	gc.fillRect(0, 0, 500, 500);
    	
		outputbig.setText("�غ�( �ٷ� �� ���� : "+timeminus+" ms)");
		outputsmall.setText("�ʷ�ȭ���� �Ǹ� Ŭ�����ּ���");
		
		ready=false;
		red=true;
		green=false;
		err=false;
		end=false;
    }
    
    void ReadyScreen() {//����ȭ��
    	sumtime = 0; endtime = 0; starttime = 0; count = 1; //�ٽ� ������ ���� ���� ��� �ʱ�ȭ
    	
    	gc.setFill(Color.LIGHTBLUE);
		gc.fillRect(0, 0, 500, 500);
		
		outputbig.setText("�����ӵ� �׽�Ʈ !");
		outputsmall.setText("�� ��ȸ�� 5�� �־����ϴ�.\n����ȭ���� �ʷϻ��� �Ǹ� Ŭ�����ּ���.\n�����Ϸ��� ȭ���� Ŭ�����ּ���");
		
		ready=true;
		red=false;
		green=false;
		err=false;
		end=false;
    }
    
    void endScreen() {//������ �� ȭ��
    	sumtime+=timeminus;

    	sumtime=sumtime/count;
    	count = 1;//�ٽ� ������ ���� ī��Ʈ �ʱ�ȭ

    	gc.setFill(Color.LIGHTBLUE);
    	gc.fillRect(0, 0, 500, 500);
    	
    	outputbig.setText("�����ӵ� �׽�Ʈ ��");
    	outputsmall.setText("��� �����ӵ� : "+sumtime+" ms");
    
    	ready=false;
    	red=false;
    	green=false;
    	err=false;
    	end=true;
    }
    
    void screentime() {//������ �ð��� �ʷϻ� ȭ���� �߰� �ϱ� ����
    	timerate=(long)(Math.random()*5000)+1000;//�ʹ� ������ ȭ���� �ٲ��� �ʰ� �ϱ� ���� +1000
    	
    	TimerTask m_task = new TimerTask() {
            @Override
            public void run() {
            	if(red) {
            		GreenScreen();
            	}
            }
        };
        timer.schedule(m_task, 500, timerate);
	}
    
    //�ڷΰ���
    @FXML
    void MainScreen(MouseEvent event) throws IOException {
    	Node button = (Node)(event.getSource());
    	stage = (Stage)(button.getScene().getWindow());
    	Parent root = FXMLLoader.load(getClass().getResource("MainScreenUI.FXML"));
    	Scene scene = new Scene(root);
    	stage.setScene(scene);
    	stage.show();
    }

			
}
