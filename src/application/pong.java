package application;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

public class pong implements Initializable{
	//변수
		private static final int width = 800; 
		private static final int height = 600;
		private static final int PLAYER_HEIGHT = 100;
		private static final int PLAYER_WIDTH = 15;
		private static final double BALL_R = 15;
		private int ballYSpeed = 1;
		private int ballXSpeed = 1;
		private double playerOneYPos = height / 2;
		private double playerTwoYPos = height / 2;
		private double ballXPos = width / 2;
		private double ballYPos = height / 2;
		private int scoreP1 = 0;
		private int scoreP2 = 0;
		private boolean gameStarted;
		private int playerOneXPos = 0;
		private double playerTwoXPos = width - PLAYER_WIDTH;
		@FXML
	    private Canvas canvas= new Canvas(width, height);;
		
	    //뒤로가기
	    @FXML
	    void mainscreen(MouseEvent event) throws IOException {
	    	Node button = (Node)(event.getSource());
	    	Stage stage = (Stage)(button.getScene().getWindow());
	    	Parent root = FXMLLoader.load(getClass().getResource("MainScreenUI.FXML"));
	    	Scene scene = new Scene(root);
	    	stage.setScene(scene);
	    	stage.show();
	    }


		private void run(GraphicsContext gc) {
			//배경 색
			//그래픽 설정
			gc.setFill(Color.BLACK);
			gc.fillRect(0, 0, width, height);
			
			//텍스트 설정
			gc.setFill(Color.WHITE);
			gc.setFont(Font.font(30));
			
			if(gameStarted) {
				//공 설정
				ballXPos+=ballXSpeed;
				ballYPos+=ballYSpeed;
				
				//컴퓨터 움직임
				if(ballXPos < width - width  / 4) {
					playerTwoYPos = ballYPos - PLAYER_HEIGHT / 2;
				}  else {
					playerTwoYPos =  ballYPos > playerTwoYPos + PLAYER_HEIGHT / 2 ?playerTwoYPos += 1: playerTwoYPos - 1;
				}
				//공 그리기
				gc.setFill(Color.WHITE);
				gc.fillOval(ballXPos, ballYPos, BALL_R, BALL_R);
				
			} else {
				
				//시작 텍스트
				gc.setStroke(Color.WHITE);
				gc.setTextAlign(TextAlignment.CENTER);
				gc.strokeText("클릭", width / 2, height / 2);
				
				
				
				//공 시작 위치
				ballXPos = width / 2;
				ballYPos = height / 2;
				
				//공 속도 리셋
				ballXSpeed = new Random().nextInt(2) == 0 ? 1: -1;
				ballYSpeed = new Random().nextInt(2) == 0 ? 1: -1;
			}
			
			
			if(ballYPos > height || ballYPos < 0) ballYSpeed *=-1;
			
			//플레이어가 공을 놓치면 컴퓨터 점수 획득
			if(ballXPos < playerOneXPos - PLAYER_WIDTH) {
				scoreP2++;
				gameStarted = false;
			}
			
			//컴퓨터가 공을 놓치면 플레이어 점수 획득
			if(ballXPos > playerTwoXPos + PLAYER_WIDTH) {  
				scoreP1++;
				gameStarted = false;
			}
		
			//공 튕기면 공 속도 증가
			if( ((ballXPos + BALL_R > playerTwoXPos) && ballYPos >= playerTwoYPos && ballYPos <= playerTwoYPos + PLAYER_HEIGHT) || 
				((ballXPos < playerOneXPos + PLAYER_WIDTH) && ballYPos >= playerOneYPos && ballYPos <= playerOneYPos + PLAYER_HEIGHT)) {
				ballYSpeed += 1 * Math.signum(ballYSpeed);
				ballXSpeed += 1 * Math.signum(ballXSpeed);
				ballXSpeed *= -1;
				ballYSpeed *= -1;
			}
			
			//스코어 텍스트
			gc.fillText(scoreP1 + "  vs  " + scoreP2, width / 2, 100);
			//플레이어 1,2 그리기
			gc.setFill(Color.BLUE);
			gc.fillRect(playerTwoXPos, playerTwoYPos, PLAYER_WIDTH, PLAYER_HEIGHT);
			gc.setFill(Color.RED);
			gc.fillRect(playerOneXPos, playerOneYPos, PLAYER_WIDTH, PLAYER_HEIGHT);
		}

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {

			//배경 설정
			GraphicsContext gc = canvas.getGraphicsContext2D();
			
			//애니메이션
			Timeline tl = new Timeline(new KeyFrame(Duration.millis(10), e -> run(gc)));
			tl.setCycleCount(Timeline.INDEFINITE);
			
			//마우스 컨트롤
			canvas.setOnMouseMoved(e ->  playerOneYPos  = e.getY());
			canvas.setOnMouseClicked(e ->  gameStarted = true);

			tl.play();
			
		}
}
