package ex01_lombok;

public class Ex02_User {
	private String id;
	private String pw;

	public Ex02_User(Builder builder) {
		this.id = builder.id;
		this.pw = builder.pw;
	}

	public static Builder builder() {
		return new Builder();
	}

	public String toString(){
		return "USER : [id :"+id+", pw : "+pw+" ]";
	}
	
	//내부클래스
	public static class Builder{
		private String id;
		private String pw;
		public Builder id(String id) {
			this.id = id;
			return this;
		}
		public Builder pw(String pw) {
			this.pw = pw;
			return this;
		}
		public Ex02_User build() {
			return new Ex02_User(this);
		}
	}
	
	public static void main(String[] args) {
		//User.builder() : 내부클래스의 객체
		//id() : 내부클래스의 메서드 (User.builder.id(String))
		Ex02_User e1 = Ex02_User.builder().id("하이루")
							.pw("1234").build();
		//setter이용하지않고 객체하나를 뚝딱
		
		System.out.println(e1);
		
	}
}
