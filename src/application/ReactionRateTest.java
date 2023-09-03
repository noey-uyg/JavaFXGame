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

    boolean ready,red,green,err,end; //클릭시 화면 판별하기 위해
    
    Timer timer = new Timer();
    
    long timerate,starttime,endtime,timeminus,sumtime;
    int count=1;
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		gc = canvas.getGraphicsContext2D();
		ReadyScreen();
	}
	
    @FXML
    void ClickCanvas(MouseEvent event) {//화면클릭시
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
        		endtime = System.currentTimeMillis();//클릭을 한 시점의 시간
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
    
    void GreenScreen() {//클릭화면
		starttime = System.currentTimeMillis();//초록화면이 뜬 시점의 시간
		
    	gc.setFill(Color.GREEN);
    	gc.fillRect(0, 0, 500, 500);
    	
		outputbig.setText("클릭");
		outputsmall.setText("클릭해주세요!");
		
		ready=false;
		red=false;
		green=true;
		err=false;
		end=false;
    }
    
    void errorScreen() {//준비화면에 눌렀을 때 에러화면
    	gc.setFill(Color.GRAY);
    	gc.fillRect(0, 0, 500, 500);
    	
		outputbig.setText("빨간화면에선 클릭금지 !");
		outputsmall.setText("화면을 클릭하여 처음으로 돌아가주세요.");
		
		ready=false;
		red=false;
		green=false;
		err=true;
		end=false;
    }
    
    void RedScreen() {//준비화면
    	gc.setFill(Color.RED);
    	gc.fillRect(0, 0, 500, 500);
    	
		outputbig.setText("준비( 바로 전 반응 : "+timeminus+" ms)");
		outputsmall.setText("초록화면이 되면 클릭해주세요");
		
		ready=false;
		red=true;
		green=false;
		err=false;
		end=false;
    }
    
    void ReadyScreen() {//시작화면
    	sumtime = 0; endtime = 0; starttime = 0; count = 1; //다시 시작할 때를 위해 모두 초기화
    	
    	gc.setFill(Color.LIGHTBLUE);
		gc.fillRect(0, 0, 500, 500);
		
		outputbig.setText("반응속도 테스트 !");
		outputsmall.setText("총 기회는 5번 주어집니다.\n빨간화면이 초록색이 되면 클릭해주세요.\n시작하려면 화면을 클릭해주세요");
		
		ready=true;
		red=false;
		green=false;
		err=false;
		end=false;
    }
    
    void endScreen() {//끝났을 때 화면
    	sumtime+=timeminus;

    	sumtime=sumtime/count;
    	count = 1;//다시 시작을 위해 카운트 초기화

    	gc.setFill(Color.LIGHTBLUE);
    	gc.fillRect(0, 0, 500, 500);
    	
    	outputbig.setText("반응속도 테스트 끝");
    	outputsmall.setText("평균 반응속도 : "+sumtime+" ms");
    
    	ready=false;
    	red=false;
    	green=false;
    	err=false;
    	end=true;
    }
    
    void screentime() {//랜덤한 시간에 초록색 화면이 뜨게 하기 위해
    	timerate=(long)(Math.random()*5000)+1000;//너무 빠르게 화면이 바뀌지 않게 하기 위해 +1000
    	
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
    
    //뒤로가기
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
