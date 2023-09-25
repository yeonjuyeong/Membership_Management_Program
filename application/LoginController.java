package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
	//로그인 성공 여부를 저장할 변수
	Boolean AdminLogin = false;
	Boolean UserLogin = false;
	
	@FXML
	Button loginButton, joinButton, cancelButton, closeButton;
	@FXML
	TextField idTextField;
	@FXML
	PasswordField pwPasswordField;
	@FXML
	CheckBox adminCheckBox;
	
	@FXML
	private void loginButtonAction(ActionEvent e) {
		// 만약 아이디 또는 비번이 비어있다면{
			//경고창
		//그 외에는 {
					//관리자에 체크가 되어 있으면
						//관리자 로그인 메소드 호출
						//isAdminLogin
					//그외에는
						//사용자 로그인 메소드 호출
						//isUserLogin
	//}
	if(loginButton.getText().equals("로그인")) {
		if(idTextField.getText().isBlank() || pwPasswordField.getText().isBlank()) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setContentText("아이디와 비밀번호를 모두 입력해주세요");
			alert.show();
		}else {
			//
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
				//유저 로그인 메소드 호출 ==> 반환값 받기
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
		
}
	
	private void isLogout() {
		Main.Global_userid="";
		AdminLogin = false;
		UserLogin = false;
		loginButton.setText("로그인");
		idTextField.setText(null);
		pwPasswordField.setText(null);
		joinButton.setDisable(false);
		joinButton.setText("회원가입");
		adminCheckBox.setSelected(false);
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setContentText("로그아웃!");
		alert.show();
	}

	private Boolean isUserLogin() {
		
		Boolean result = false;
		
		DBconnect conn = new DBconnect();
		Connection conn2 = conn.getConn();
		
		String sql = "select user_id, user_pw"
				+ " from user_table"
				+ " where user_id = ? and user_pw = ?";
		
		try {
			PreparedStatement ps = conn2.prepareStatement(sql);
			ps.setString(1, idTextField.getText());
			ps.setString(2, pwPasswordField.getText());
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				Main.Global_userid=idTextField.getText();
				result = true;
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return result;
		
	}

	public Boolean isAdminLogin() {
		
		//result는 로그인 성공여부를 저장하는 변수
		//false는 로그인 안됨
		//true는 로그인 성공
		
		Boolean result = false;
		
		//디비 접속, sql문 만들기
		
		DBconnect conn = new DBconnect();
		Connection conn2 = conn.getConn();
		
		String sql = "select admin_id, admin_pw"
				+ " from admin_table"
				+ " where admin_id = ? and admin_pw = ?";
		try {
			PreparedStatement ps = conn2.prepareStatement(sql);
			ps.setString(1, idTextField.getText());
			ps.setString(2, pwPasswordField.getText());
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				//AdminLogin = true;
				result = true;
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return result;
		
	}

	@FXML
	private void joinButtonAction(ActionEvent e) {
		//관리자에 체크가 되어 있으면
			//로그인 상태이면  (AdminLogin==true) ==> 회원관리 띄우기
			//로그아웃 상태이면 ==> 아무것도 안해
		
		//그 외에는(일반인 상태)
			//로그인 상태이면 (UserLogin==true) ==> 회원정보 띄우기
			//로그아웃 상태이면 ==> 회원가입 띄우기
		
		if(adminCheckBox.isSelected() == true) {
			if(AdminLogin==true){
				//회원관리 창 보여줘
				try {
					Parent root = FXMLLoader.load(getClass().getResource("Manage.fxml"));
					Stage stage = new Stage();
					Scene scene = new Scene(root);
					stage.setTitle("회원관리 화면");
					stage.setScene(scene);
					stage.show();
				} catch(Exception e1) {
					System.out.println("DB접속 실패");
					e1.printStackTrace();
				}
			}			
		}else {
			if(UserLogin==true) {
					//회원정보 창 보여줘
				try {
					Parent root = FXMLLoader.load(getClass().getResource("modify.fxml"));
					Stage stage = new Stage();
					Scene scene = new Scene(root);
					stage.setTitle("회원정보수정 화면");
					stage.setScene(scene);
					stage.show();
				} catch(Exception e1) {
					System.out.println("DB접속 실패");
					e1.printStackTrace();
				}
			}else {
				//회원가입 창 보여줘
				try {
						Parent root = FXMLLoader.load(getClass().getResource("join.fxml"));
						Stage stage = new Stage();
						Scene scene = new Scene(root);
						stage.setTitle("회원가입 화면");
						stage.setScene(scene);
						stage.show();
					} catch(Exception e1) {
						System.out.println("DB접속 실패");
						e1.printStackTrace();
					}
				}
			}
		}
	
	@FXML
	private void cancelButtonAction(ActionEvent e) {
		idTextField.setText("");
		pwPasswordField.setText("");
	}
	
	@FXML
	private void closeButtonAction(ActionEvent e) {
		Stage stage = (Stage)closeButton.getScene().getWindow();
		stage.close();

	}
	
	@FXML
	private void adminCheckBoxAction(ActionEvent e) {
		
		if(adminCheckBox.isSelected() == true) {
			joinButton.setDisable(true);
			joinButton.setText("회원관리메뉴");
		}else {
			joinButton.setDisable(false);
			joinButton.setText("회원가입");
		}
		
	}

}
