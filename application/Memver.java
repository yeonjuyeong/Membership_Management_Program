package application;

public class Memver {

	String name = null;
	String id = null;
	String pw = null;
	String hak = null;
	String ban = null;
	String bun = null;
	
	//생성자 만들기(매개변수 없는것 + 있는것)
	//게터, 세터 만들기
	public Memver() {
		super();
	}

	public Memver(String name, String id, String pw, String hak, String ban, String bun) {
		super();
		this.name = name;
		this.id = id;
		this.pw = pw;
		this.hak = hak;
		this.ban = ban;
		this.bun = bun;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getHak() {
		return hak;
	}

	public void setHak(String hak) {
		this.hak = hak;
	}

	public String getBan() {
		return ban;
	}

	public void setBan(String ban) {
		this.ban = ban;
	}

	public String getBun() {
		return bun;
	}

	public void setBun(String bun) {
		this.bun = bun;
	}
	
}
