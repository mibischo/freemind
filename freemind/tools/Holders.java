package freemind.tools;

public class Holders {

	public static class IntHolder {
		private int value;

		public IntHolder() {
		}

		public IntHolder(int value) {
			this.value = value;
		}

		public void setValue(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}

		public String toString() {
			return new String("IntHolder(") + value + ")";
		}
	}

	public static class BooleanHolder {
		private boolean value;

		public BooleanHolder() {
		}

		public BooleanHolder(boolean initialValue) {
			value = initialValue;
		}

		public void setValue(boolean value) {
			this.value = value;
		}

		public boolean getValue() {
			return value;
		}
	}

	public static class ObjectHolder {
		Object object;

		public ObjectHolder() {
		}

		public void setObject(Object object) {
			this.object = object;
		}

		public Object getObject() {
			return object;
		}
	}

	public static class Pair {
		Object first;

		Object second;

		public Pair(Object first, Object second) {
			this.first = first;
			this.second = second;
		}

		public Object getFirst() {
			return first;
		}

		public Object getSecond() {
			return second;
		}
	}
}
