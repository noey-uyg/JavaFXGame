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
	//����
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
		
	    //�ڷΰ���
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
			//��� ��
			//�׷��� ����
			gc.setFill(Color.BLACK);
			gc.fillRect(0, 0, width, height);
			
			//�ؽ�Ʈ ����
			gc.setFill(Color.WHITE);
			gc.setFont(Font.font(30));
			
			if(gameStarted) {
				//�� ����
				ballXPos+=ballXSpeed;
				ballYPos+=ballYSpeed;
				
				//��ǻ�� ������
				if(ballXPos < width - width  / 4) {
					playerTwoYPos = ballYPos - PLAYER_HEIGHT / 2;
				}  else {
					playerTwoYPos =  ballYPos > playerTwoYPos + PLAYER_HEIGHT / 2 ?playerTwoYPos += 1: playerTwoYPos - 1;
				}
				//�� �׸���
				gc.setFill(Color.WHITE);
				gc.fillOval(ballXPos, ballYPos, BALL_R, BALL_R);
				
			} else {
				
				//���� �ؽ�Ʈ
				gc.setStroke(Color.WHITE);
				gc.setTextAlign(TextAlignment.CENTER);
				gc.strokeText("Ŭ��", width / 2, height / 2);
				
				
				
				//�� ���� ��ġ
				ballXPos = width / 2;
				ballYPos = height / 2;
				
				//�� �ӵ� ����
				ballXSpeed = new Random().nextInt(2) == 0 ? 1: -1;
				ballYSpeed = new Random().nextInt(2) == 0 ? 1: -1;
			}
			
			
			if(ballYPos > height || ballYPos < 0) ballYSpeed *=-1;
			
			//�÷��̾ ���� ��ġ�� ��ǻ�� ���� ȹ��
			if(ballXPos < playerOneXPos - PLAYER_WIDTH) {
				scoreP2++;
				gameStarted = false;
			}
			
			//��ǻ�Ͱ� ���� ��ġ�� �÷��̾� ���� ȹ��
			if(ballXPos > playerTwoXPos + PLAYER_WIDTH) {  
				scoreP1++;
				gameStarted = false;
			}
		
			//�� ƨ��� �� �ӵ� ����
			if( ((ballXPos + BALL_R > playerTwoXPos) && ballYPos >= playerTwoYPos && ballYPos <= playerTwoYPos + PLAYER_HEIGHT) || 
				((ballXPos < playerOneXPos + PLAYER_WIDTH) && ballYPos >= playerOneYPos && ballYPos <= playerOneYPos + PLAYER_HEIGHT)) {
				ballYSpeed += 1 * Math.signum(ballYSpeed);
				ballXSpeed += 1 * Math.signum(ballXSpeed);
				ballXSpeed *= -1;
				ballYSpeed *= -1;
			}
			
			//���ھ� �ؽ�Ʈ
			gc.fillText(scoreP1 + "  vs  " + scoreP2, width / 2, 100);
			//�÷��̾� 1,2 �׸���
			gc.setFill(Color.BLUE);
			gc.fillRect(playerTwoXPos, playerTwoYPos, PLAYER_WIDTH, PLAYER_HEIGHT);
			gc.setFill(Color.RED);
			gc.fillRect(playerOneXPos, playerOneYPos, PLAYER_WIDTH, PLAYER_HEIGHT);
		}

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {

			//��� ����
			GraphicsContext gc = canvas.getGraphicsContext2D();
			
			//�ִϸ��̼�
			Timeline tl = new Timeline(new KeyFrame(Duration.millis(10), e -> run(gc)));
			tl.setCycleCount(Timeline.INDEFINITE);
			
			//���콺 ��Ʈ��
			canvas.setOnMouseMoved(e ->  playerOneYPos  = e.getY());
			canvas.setOnMouseClicked(e ->  gameStarted = true);

			tl.play();
			
		}
}
