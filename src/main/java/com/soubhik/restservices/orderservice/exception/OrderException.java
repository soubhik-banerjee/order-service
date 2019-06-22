package com.soubhik.restservices.orderservice.exception;

public class OrderException {

	public static class OrderBookAlreadyPresent extends RuntimeException{ 

		private static final long serialVersionUID = -6876904701973642669L;

		public OrderBookAlreadyPresent(String msg) {
			super(msg);
		}
	}

	public static class OrderBookNotFound extends RuntimeException{

		public OrderBookNotFound(String msg) {
			super(msg);
		}
	}

	public static class OrderBookClosed extends RuntimeException{

		public OrderBookClosed(String msg) {
			super(msg);
		}
	}

	public static class OrderAlreadyAdded extends RuntimeException{

		public OrderAlreadyAdded(String msg) {
			super(msg);
		}
	}

	public static class OrderAdditionFailed extends RuntimeException{

		public OrderAdditionFailed(String msg) {
			super(msg);
		}
	}
	
	public static class ExecutionPriceMismatch extends RuntimeException{

		public ExecutionPriceMismatch(String msg) {
			super(msg);
		}
	}
	
	public static class OrderBookNotExecutable extends RuntimeException{

		public OrderBookNotExecutable(String msg) {
			super(msg);
		}
	}
}
