package bubble;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import lombok.Getter;

@Getter
public class BubbleFrame extends JFrame {
	// 포함관계 (컴포지션 관계)

	private BubbleFrame mContext = this;

	private JLabel backgroundMap;
	private Player player;
	private Enemy enemy;

	public BubbleFrame() {
		initData();
		setInitLayout();
		addEventListener();
//		new BackgroundPlayerService(player); // Runnable
		
		

	}

	private void initData() {
		backgroundMap = new JLabel(new ImageIcon("images/backgroundMap.png"));
//		backgroundMap = new JLabel(new ImageIcon("images/test.png"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(backgroundMap); // add와 같은 기능
		setSize(1000, 640);
		
		enemy = new Enemy(mContext);
		player = new Player(mContext);
		new BGM();

	}

	private void setInitLayout() {
		setVisible(true);
		setResizable(false);
		setLayout(null); // absoulte 레이아웃
		setLocationRelativeTo(null); // JFrame 가운데 배치하기

		add(player);
		add(enemy);

	}

	private void addEventListener() {
		this.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				System.out.println(e.getKeyCode());

				switch (e.getKeyCode()) {
				case KeyEvent.VK_LEFT:

					// L L L L L
					// 스레드 5개 생성 new L new L new L new L new L
					// 스레드 1번만 생성하기 위한 코드

					// 1. 플레이어가 왼쪽으로 가고 있지 않을 때, player.left() 실행해야 한다
					// 2. 왼쪽벽에 맞닿아 있으면 left() 메서드를 실행시키면 안된다.
					if (!player.isLeft() && !player.isLeftWallCrash()) {
						// boolean의 게터 셋터는 is가 들어감
						player.left();
					}

					break;
				case KeyEvent.VK_RIGHT:

					if (!player.isRight() && !player.isRightWallCrash()) {
						player.right();
					}

					break;
				case KeyEvent.VK_UP:
					if (!player.isUp() && !player.isDown()) {
						player.up();
					}
					break;
				case KeyEvent.VK_SPACE:
					player.attack();
					break;
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_LEFT:
					// 왼쪽으로 가는거 멈춰야해
					player.setLeft(false);

					break;
				case KeyEvent.VK_RIGHT:
					// 오른쪽으로 가는거 멈춰야해
					player.setRight(false);
					break;

				}
			}
		});

	}

	public static void main(String[] args) {
		new BubbleFrame();
	}
}
