package cn.wzy;

/**
 * @author wzy 不短不长八字刚好.
 * @since 2018/9/24 14:17
 */
public class Singleton {

	public static Singleton singleton;

	public static Singleton getInstance() {
		if (singleton == null) {
			synchronized (Singleton.class) {
				if (singleton == null) {
					singleton = new Singleton();
				}
			}
		}
		return singleton;
	}
}
