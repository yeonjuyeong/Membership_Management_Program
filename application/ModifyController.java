package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ModifyController implements Initializable {

	//컨트롤 등록
	@FXML Button modifyButton, cancelButton, closeButton;
	@FXML TextField nameTextField, idTextField, hakTextField, banTextField, bunTextField;
	@FXML PasswordField pwPasswordField, pw2PasswordField;
	
	//액션메소드 작성
	@FXML
	private void modifyButtonActiom(ActionEvent event) {
		//만약, 검증(빈칸, 비번일치, 학번)을 모두 통과하면
			//디비 접속==> 정보 수정
		//그 외에는
			//만약 빈칸 오류이면 ==> 경고메세지
			//만약 비번 오류이면 ==> 경고메세지
			//만약 학번 오류이면 ==> 경고메세지
		boolean checkEmpty = false;
		boolean checkPw = false;
		boolean checkNum = false;
			
		checkEmpty = ischeckEmpty();
		checkPw = ischeckPw();
		checkNum = ischeckNum();
		
		
		if(checkEmpty == true&& checkPw == true&& checkNum == true) {
			//메세지 띄우기(다시 한번 물어보기 ==> 대답이 2가지)
				//대답이 YES이면 ==> DB 접속, sql 작성, 실행, 정보수정
				//대답이 NO라면 취소
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setContentText(Main.Global_userid+" 님의 정보를 정말 수정하시겠습니까?");
			Optional<ButtonType> AR = alert.showAndWait();
			if(AR.get() == ButtonType.OK) {
				//디비 접속, sql작성, 실행
				
				//디비 접속, sql 작성, 실행, 정보 수정
		 DBconnect conn = new DBconnect();
		 Connection conn2 = conn.getConn();
				
				//사용자 정보 수정
				
		 String sql = "UPDATE user_table SET user_name = ?, user_pw = ?, hak = ?, ban = ?, bun = ?"
				  	+" WHERE user_id = ?";

			
		 try {
			 PreparedStatement ps;
			ps = conn2.prepareStatement(sql);
			ps.setString(1, nameTextField.getText());
			ps.setString(2, pwPasswordField.getText());
			ps.setString(3, hakTextField.getText());
			ps.setString(4, banTextField.getText());
			ps.setString(5, bunTextField.getText());
			ps.setString(6, Main.Global_userid);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()){
				Alert alert1 = new Alert(AlertType.INFORMATION);
				alert1.setContentText(Main.Global_userid+"님의 회원정보가 수정되었습니다");
				alert1.show();
				closeButtonAction(event);				
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		 
			}
			
		}else {
			if(checkEmpty == false) {
				//경고메세지
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText("모든 칸을 입력하세요");
				alert.show();
			}else if(checkPw == false){
				//경고메세지
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText("비밀번호를 입력하세요");
				alert.show();
			}else if(checkNum == false){
				//경고메세지
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText("학번칸을 잘못 입력하셨습니다.");
				alert.show();
			}
		}
			
	}
	



	private boolean ischeckEmpty() {
		boolean result = false;
		//만약 모든 칸이 비어 있지 않으면
			//result 값을 true로
		if(nameTextField.getText().isEmpty()==false&&
				pwPasswordField.getText().isEmpty()==false&&
				pw2PasswordField.getText().isEmpty()==false&&
				hakTextField.getText().isEmpty()==false&&
				banTextField.getText().isEmpty()==false&&
				bunTextField.getText().isEmpty()==false) {		
			result = true;
		}
		return result;
	}
	
	private boolean ischeckPw() {
		boolean result = false;
		
		//만약 첫번째 비번과 두번째 비번이 같으면
			//result 값을 true로
		if(pwPasswordField.getText().equals(pw2PasswordField.getText())){
			result = true;
		}
		return result;
	}

	private boolean ischeckNum() {
		boolean result = false;
		
		try {
			int hak = Integer.parseInt(hakTextField.getText());
			int ban = Integer.parseInt(banTextField.getText());
			int bun = Integer.parseInt(bunTextField.getText());
			
			if((hak>=1&&hak<=3)&&(ban>=1&&ban<=12)&&(bun>=1&&bun<=30)){
				result = true;
			}
			
		} catch (Exception e) {
		}
		return result;
	}
	
	@FXML
	private void cancelButtonAction(ActionEvent event) {
		nameTextField.setText("");
		pwPasswordField.setText("");
		pw2PasswordField.setText("");
		hakTextField.setText("");
		banTextField.setText("");
		bunTextField.setText("");
	}
	

	@FXML
	private void closeButtonAction(ActionEvent event) {
		Stage stage = (Stage)closeButton.getScene().getWindow();
		stage.close();
	}

	//회원정보수정 화면 초기화 작업
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//디비 접속
		DBconnect conn = new DBconnect();
		Connection conn2 = (conn).getConn();
		
		//sql 작성(현재 로그인된 아이디에 해당하는 자료 검색)
		String sql = "select user_name, user_id, user_pw, hak, ban, bun"
				+ " from user_table"
				+ " where user_id = ?";	
		PreparedStatement ps;
		try {
			ps = conn2.prepareStatement(sql);
			ps.setString(1, Main.Global_userid);
			
			ResultSet rs = ps.executeQuery();
			
			//만약 결과값이 있다면
				//화면의 각 콘트롤에 값표시
			if(rs.next()) {
				nameTextField.setText(rs.getString(1));
				idTextField.setText(rs.getString(2));
				pwPasswordField.setText(rs.getString(3));
				pw2PasswordField.setText(rs.getString(3));
				hakTextField.setText(rs.getString(4));
				banTextField.setText(rs.getString(5));
				bunTextField.setText(rs.getString(6));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//sql 실행 ==> 화면에 표시
	}
}