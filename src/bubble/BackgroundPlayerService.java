package bubble;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import lombok.Getter;

//메인 스레드 바쁨 (이벤트 리스너 처리중 )
// 백그라운드에서 계속 Player의 움직임을 관찰
// 뒤에서 남 모르게 돌아가는 서비스

public class BackgroundPlayerService implements Runnable {

	private BufferedImage image;
	// 의존성 포함관계, 의존성 컴포지션
	// 매개변수로 올려둔 player가 없으면 연결되지 않음

	private Player player;

	public BackgroundPlayerService(Player player) {
		this.player = player;
		try {
			image = ImageIO.read(new File("images/backgroundMapService.png"));
//			image = ImageIO.read(new File("images/test.png"));
		} catch (IOException e) {
			System.out.println("이미지 파일명 및 파일 경로를 확인 바랍니다.");
		}
	}

	@Override
	public void run() {
		while (true) {
			// 색상확인
			Color leftColor = new Color(image.getRGB(player.getX(), player.getY()) + 25);
			Color rightColor = new Color(image.getRGB(player.getX() + 50 + 10, player.getY()) + 25);

//			Color bottomColor = new Color(image.getRGB(player.getX(), player.getY() + 50 + 5));
			int bottomColorLeft = image.getRGB(player.getX() + 20, player.getY() + 50 + 5); // -1
			int bottomColorRight = image.getRGB(player.getX() + 50 - 10, player.getY() + 50 + 5); // -1
			// 바닥인 경우 (빨강) 255 0 0 , (파랑)0 0 255
			// (흰색) 255 255 255
			// 흰색이 아니면 바닥이다.
			if (bottomColorLeft + bottomColorRight != -2) {
				// 여기가 바닥입니다.
				player.setDown(false);
			} else {
				// 조금 점프하는 순간 bottomColor 값이 -1이 되기 때문에,
				// 65번 돌아야하는데 for문이 첫번째 도는 순간에
				// player.down() 메서드가 실행
				if (!player.isUp() && !player.isDown()) {
					// for문 65번 성공!
					// 플레이어가 올라가는 도중이 아닐 때 down()메서드를 실행 시켜야 한다.
					// 다운 메서드는 단 한번만 실행됩니다.
					player.down();

				}
			}

			if (leftColor.getRed() == 255 && leftColor.getGreen() == 0 && leftColor.getBlue() == 25) {
				player.setLeftWallCrash(true);
				player.setLeft(false);
			} else if (rightColor.getRed() == 255 && rightColor.getGreen() == 0 && rightColor.getBlue() == 25) {
				player.setRightWallCrash(true);
				player.setRight(false);
			} else {
				player.setLeftWallCrash(false);
				player.setRightWallCrash(false);
			}
			
			
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
