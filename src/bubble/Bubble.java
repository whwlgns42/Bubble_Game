package bubble;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Bubble extends JLabel implements Moveable {

	// 컨텍스트 정보를 들고 올 수 있다.
	private BubbleFrame mContext;

	// 의존성 컴포지션 관계
	Player player;
	BackGroundBubbleService backGroundBubbleService;
	Enemy enemy;

	// 버블에 위치 상태
	private int x;
	private int y;

	// 움직임 상태
	private boolean left;
	private boolean right;
	private boolean up;
	private boolean enmyAttack;

	// 적군을 맞춘 상태
	private int state; // 0(물방울) , 1, (적을 가둔 물방울)

	private ImageIcon bubble; // 물방울
	private ImageIcon bubbled; // 적을 가둔 물방울
	private ImageIcon bomb; // 물발울이 터진 상태

	public Bubble(BubbleFrame mContext) {
		this.mContext = mContext;
		this.player = mContext.getPlayer();
		initData();
		setInitLayout();
	}

	private void initData() {
		bubble = new ImageIcon("images/bubble.png");
		bubbled = new ImageIcon("images/bubbled.png");
		bomb = new ImageIcon("images/bomb.png");
		backGroundBubbleService = new BackGroundBubbleService(this);
		left = false;
		right = false;
		up = false;
		state = 0;
	}

	private void setInitLayout() {
		// 플레이어가 있는 위치에서 태어 나야 한다.
		x = player.getX();
		y = player.getY();
		setIcon(bubble);
		setSize(50, 50);
		setLocation(x, y);
	}

//	private void initThread() {
//		// 버블은 스레드가 하나면 된다.
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				if (player.getPWay() == PlayerWay.LEFT) {
//					left();
//				} else {
//					right();
//				}
//			}
//		}).start();
//	}

	@Override
	public void left() {
		left = true;
		for (int i = 0; i < 400; i++) {
			x--;
			setLocation(x, y);

			System.out.println(backGroundBubbleService.leftWall());

			if (backGroundBubbleService.leftWall()) {
				break;
			}

//			Math.abs(x - mContext.getEnemy().getX()) < 10
			// &&

			if ((Math.abs(x - mContext.getEnemy().getX()) < 10) && Math.abs(y - mContext.getEnemy().getY()) < 50) {
				System.out.println("적군과 버블이 부딪힘");
				
				if(mContext.getEnemy().getState() == 0) {
					crash();
				}
			}

			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		up();
	}

	@Override
	public void right() {
		right = true;
		for (int i = 0; i < 400; i++) {
			x++;
			setLocation(x, y);

			if (backGroundBubbleService.rightWall()) {
				break;
			}

			if ((Math.abs(x - mContext.getEnemy().getX()) < 10) && Math.abs(y - mContext.getEnemy().getY()) < 50) {
				System.out.println("적군과 버블이 부딪힘");
				
				if(mContext.getEnemy().getState() == 0) {
					crash();
					
				}
			}
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		up();
	}

	@Override
	public void up() {
		up = true;
		while (true) {
			y--;
			setLocation(x, y);

			if (backGroundBubbleService.topWall()) {
				break;
			}
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		clearBubble();
	}

	// 행위 -> clear(동사) -> bubble(목적어)
	private void clearBubble() {

		try {
			Thread.sleep(3000);
			// bomb
			setIcon(bomb);
			Thread.sleep(500);

			// BubbleFrame의 bubble 객체를 메모리에서 소멸 시켜버리기

			mContext.remove(this); // 메모리에서 내리기
			mContext.repaint(); // BubbleFrame전체를 다시 그려준다. (메모리에 없는건 그리지 않음)

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void crash() {
		// 적군에 살아 있는 상태를 버블에 갇힌 상태로 변경
		mContext.getEnemy().setState(1);
		state = 1;
		setIcon(bubbled);
		// 프레임에서 존재 여부를 없애 버린다.
		// heap 메모리에서는 아직 살아 있다. (가비지컬렉터에 제거 대상이 된다.)
		mContext.remove(mContext.getEnemy());
		mContext.repaint();
	}

	public void enemyAttack() {
		enmyAttack = true;

	}

}
