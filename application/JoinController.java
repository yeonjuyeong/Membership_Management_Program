package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class JoinController {
		
	@FXML Button joinButton, cancelButton, closeButton;
	
	@FXML TextField nameTextField, idTextField, hakTextField, banTextField, bunTextField;
	
	@FXML PasswordField pwPasswordField, pw2PasswordField;
	
	
		@FXML
		private void joinButtonAction(ActionEvent e) {
			//빈칸 여부 확인
			//아이디 중복 확인
			//비번 2개가 일치
			//학번확인
			//==> 위에 네가지를 확인한 결과를 저장할 변수 선언(불린형)
			
			boolean checkEmpty=false;
			boolean checkId=false;
			boolean checkPw=false;
			boolean checkNum=false;
			
			checkEmpty = ischeckEmpty();
			checkId = ischeckid();
			checkPw = ischeckpw();
			checkNum = ischeckNum();
			
			//만약에 위의 네가지 확인 결과가 모두 true이면
				//DB에 저장해
			//그 외에는
				//빈칸이 있으면 ==> 경고메세지
				//아이디가 중복이면 ==> 경고메세지
				//비번이 불일치하면 ==> 경고메세지
				//학번이 잘못됐으면 ==> 경고메세지
			
			if(checkEmpty==true && checkId==true && checkPw==true && checkNum==true) {
				//회원자료 디비에 저장해
				//sql 작성 ==> 실행 ==> 완료메세지
				DBconnect conn = new DBconnect();
				Connection conn2 = conn.getConn();
				
				String sql="insert into USER_TABLE"
						+ " (idx,user_name,user_id,user_pw,hak,ban,bun)"
						+ " values"
						+ " (user_idx_pk.nextval, ?, ?, ?, ?, ?, ?)";
				
				try {
					PreparedStatement ps = conn2.prepareStatement(sql);
					ps.setString(1, nameTextField.getText());
					ps.setString(2, idTextField.getText());
					ps.setString(3, pwPasswordField.getText());
					ps.setString(4, hakTextField.getText());
					ps.setString(5, banTextField.getText());
					ps.setString(6, bunTextField.getText());
					
					ResultSet rs = ps.executeQuery();
					
					//만약 rs에 값이 있으면 ==> 메세지 띄우기
					if(rs.next()) {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setContentText("회원가입이 완료되었습니다.");
						alert.show();
						Stage stage = (Stage)closeButton.getScene().getWindow();
						stage.close();
					}
					
					rs.close();
					ps.close();
					conn2.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
			}else {
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
		}
	
		private boolean ischeckNum() {
			Boolean result = false;
			
			int hak;
			int ban;
			int bun;
			
			try {
				hak=Integer.parseInt(hakTextField.getText());
			ban=Integer.parseInt(banTextField.getText());
			bun=Integer.parseInt(bunTextField.getText());
			
				if((hak>=1 && hak<=3)&&(ban>=1 && ban<=12)&&(bun>=1 && bun<=30)) {
				result=true;
				}
			} catch (Exception e) {				
			  }
			
			
			return result;			
		}

		private boolean ischeckpw() {
			Boolean result= false;
			//만약 첫번째 비번과 두번째 비번이 같으면
				//result값을 트루로
			if(pwPasswordField.getText().equals(pw2PasswordField.getText())) {
				result=true;
			}
			return result;
		}

		private boolean ischeckid() {
			//아이디 중복 체크
			//디비에 접속해
			//sql (현재 id칸에 입력된 값과 동일한 자료 검색)
			//만약 결과 값이 있다면
				//result false로
			//그외에는
				//result true로
			boolean result=false;
			
			DBconnect conn = new DBconnect();
			Connection conn2 = conn.getConn();
			
			String sql = "select user_id"
					+ " from user_table"
					+ " where user_id = ?";
			try {
				PreparedStatement ps = conn2.prepareStatement(sql);
				ps.setString(1, idTextField.getText());

				ResultSet rs = ps.executeQuery();
				
				if(rs.next()) {
					result = false;
				}else {
					result = true;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return result;
		}

		private boolean ischeckEmpty() {
			//반환값을 저장할 변수 선언
			boolean result=false;
			//만약에 이름,아이디,비번,비번2,학년,반,번호가 비어있지 않으면
				//변수 result 값을 true로 
			if (nameTextField.getText().isEmpty()==false
				&& idTextField.getText().isEmpty()==false 
				&& pwPasswordField.getText().isEmpty()==false
				&& pw2PasswordField.getText().isEmpty()==false
				&& hakTextField.getText().isEmpty()==false
				&& banTextField.getText().isEmpty()==false
				&& bunTextField.getText().isEmpty()==false){
				result = true;
			}
			
			return result;
		}

		@FXML
		private void cancelButtonAction(ActionEvent e) {
			//입력된 값 모두 초기화
			nameTextField.setText("");
			idTextField.setText("");
			pwPasswordField.setText("");
			pw2PasswordField.setText("");
			hakTextField.setText("");
			banTextField.setText("");
			bunTextField.setText("");
		}
		
	
		@FXML
		private void closeButtonAction(ActionEvent e) {
			//현재 창 닫기
			Stage stage = (Stage)closeButton.getScene().getWindow();
			stage.close();
		}
	}


