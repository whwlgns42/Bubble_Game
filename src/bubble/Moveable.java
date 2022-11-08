package bubble;

/**
 * 
 * @author jihun
 * defrault를 사용하면 인터페이스도 몸체가 있는 메소드를 만들수 있다.
 * 어댑터 패턴 대용으로 사용하기도 한다. 
 *
 */

public interface Moveable {

	public abstract void left();
	public abstract void right();
	public abstract void up();
	// 인터페이스 (추상 메소드, 상수 - static) -----추가개념
	default public  void down() {};
	

}
