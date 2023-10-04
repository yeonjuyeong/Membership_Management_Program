# Membership_Management_Program
SceneBuilder 있는 login.fxml을 가져와서 화면에 보여줌
![image](https://github.com/yeonjuyeong/Membership_Management_Program/assets/123055714/8c6c5d85-e4d8-4d2c-9fbd-da408c6e1c71)
<br>
<br>
<br>checkEmpty(빈칸여부), checkId(아이디중복확인), checkPw(비밀번호재확인이일치), checkNum(학번오류확인)이 true이면 회원가입 성공(DB저장)
![image](https://github.com/yeonjuyeong/Membership_Management_Program/assets/123055714/c5349263-da3d-419d-ad4b-a9055adfaa6a)
<br>
<br>
<br>4개 중 하나라도 오류가 있으면 alert
```java
else {
				if(checkEmpty==false) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setContentText("모든 항목을 입력하세요.");
					alert.show();
					//경고:모든 항목을 입력해야합니다.
				}else if(checkId==false) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setContentText("이미 사용중인 아이디 입니다.");
					alert.show();
					//경고:이미 사용중인 아이디입니다.
				}else if(checkPw==false) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setContentText("암호를 다시 확인해주세요.");
					alert.show();
					//경고:암호를 다시 확인해주세요.
				}else if(checkNum==false) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setContentText("학번이 잘못되었습니다.");
					alert.show();
					//경고:학번이 잘못되었습니다.
				}
			}
```
<br>
<br>
<br>
![image](https://github.com/yeonjuyeong/Membership_Management_Program/assets/123055714/a3a6ba20-3271-4809-9030-1ef834f12aae)
<br>idTextField와 pwPasswordField에 값이 있고
```java
if(loginButton.getText().equals("로그인")) {
		if(idTextField.getText().isBlank() || pwPasswordField.getText().isBlank()) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setContentText("아이디와 비밀번호를 모두 입력해주세요");
			alert.show();
		}else {
```
![image](https://github.com/yeonjuyeong/Membership_Management_Program/assets/123055714/819a93cc-89bf-4762-9522-d6f4ced40d10)
<br>adminCheckBox에 체크가 되어있으면 어드민 로그인으로 변환
```java
			if(adminCheckBox.isSelected() == true) {
				//관리자 로그인 메소드 호출 ==> 반환값 받기
				AdminLogin = isAdminLogin();
				if(isAdminLogin() == true) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setContentText("관리자 로그인 성공!");
					alert.show();
					loginButton.setText("로그아웃");
					joinButton.setDisable(false);
					joinButton.setText("회원관리메뉴");
				}else {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setContentText("관리자 로그인 실패!");
					alert.show();
				}
			}else {
```
![image](https://github.com/yeonjuyeong/Membership_Management_Program/assets/123055714/51578a03-0308-43d8-a01a-62f065436fe6)
<br>안되어있으면 UserLogin으로 로그인
```java
UserLogin = isUserLogin();
				if(isUserLogin() == true) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setContentText("사용자 로그인 성공!");
					alert.show();
					loginButton.setText("로그아웃");
					joinButton.setText("회원정보수정");
				}else {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setContentText("사용자 로그인 실패!");
					alert.show();
				}
			}
		}
	}else {
		isLogout();
	}
		
```












