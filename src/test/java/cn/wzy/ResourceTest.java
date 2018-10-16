package cn.wzy;

import java.util.Date;

/**
 * @author wzy 不短不长八字刚好.
 * @since 2018/9/24 8:38
 */
public class ResourceTest {

	private static UserConstant role;

	private enum UserConstant{
		ADMIN(1),
		STUDENT(2);
		private int role;
		UserConstant(int role) {
			this.role = role;
		}

		@Override
		public String toString() {
			return this.role + "";
		}
	}

	public static void main(String[] args) {
		role = UserConstant.ADMIN;
		System.out.println(UserConstant.ADMIN);
	}
}
