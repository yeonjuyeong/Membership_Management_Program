package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ManageController implements Initializable{

	@FXML
	Button updateButton, deleteButton, readButton, closeButton;
	@FXML
	TextField nameTextField, idTextField, hakTextField, banTextField, bunTextField;
	@FXML
	PasswordField pwPasswordField, pw2PasswordField;
	@FXML
	TableView<Memver> memberTableView;
	@FXML
	TableColumn<Memver, String> nameTableColumn, idTableColumn, pwTableColumn, hakTableColumn, banTableColumn, bunTableColumn;
	
	@FXML 
	private void updateButtonAction(ActionEvent event){
		//체크 (빈칸, 암호일치, 학번) ==> 이상이 없으면 ==> DB접속, 수정
			//이상이 있으면 경고메세지
		
		
		if(ischekEmpty()==true && ischeckPw()==true && ischeckNum()==true) {
			//디비접속 ==> sql문 작성,데이터수정
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setContentText("정말 바꾸시겠습니까?");
			Optional<ButtonType> AR = alert.showAndWait();
			if(AR.get() == ButtonType.OK) {		
				DBconnect conn = new DBconnect();
				Connection conn2 = conn.getConn();
				
				String sql = "update user_table set user_name=?, user_pw=?, hak=?, ban=?, bun=?"
						+ " where user_id=?";
				
				try {
					PreparedStatement ps = conn2.prepareStatement(sql);
					ps.setString(1, nameTextField.getText());
					ps.setString(2, pwPasswordField.getText());
					ps.setString(3, hakTextField.getText());
					ps.setString(4, banTextField.getText());
					ps.setString(5, bunTextField.getText());
					ps.setString(6, idTextField.getText());
					ResultSet rs = ps.executeQuery();
					if(rs.next()) {
						
					}
					System.out.println("데이터 바뀜");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
		}else {
			if(ischekEmpty()==false) {
				//경고메세지
				Alert alert = new Alert(AlertType.WARNING);
				alert.setContentText("빈칸이 있습니다.");
				alert.show();
			}if( ischeckPw()==false) {
				//경고메세지
				Alert alert = new Alert(AlertType.WARNING);
				alert.setContentText("비밀번호가 일치하지 않습니다.");
				alert.show();
			}if(ischeckNum()==false) {
				//경고메세지
				Alert alert = new Alert(AlertType.WARNING);
				alert.setContentText("학번이 일치 하지 않습니다.");
				alert.show();
			}
		}
		
		
	}
	
	private boolean ischekEmpty() {
		boolean result = false;
		if(nameTextField.getText().isEmpty()==false
			&& pwPasswordField.getText().isEmpty()==false 
			&& pw2PasswordField.getText().isEmpty()==false
			&& hakTextField.getText().isEmpty()==false
			&& banTextField.getText().isEmpty()==false
			&& bunTextField.getText().isEmpty()==false) {
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
			if((hak >= 1 && hak <=3) && (ban >= 1 && ban <=12) && (bun >=1 && bun <=30)) {
				result = true;
			}
		} catch (Exception e) {
		  e.printStackTrace();
		}
		return result;			
	}

	private boolean ischeckPw() {
		boolean result = false;
		if(pwPasswordField.getText().equals(pw2PasswordField.getText())) {
			result = true;
		}
		return result;
	}


	@FXML 
	private void deleteButtonAction(ActionEvent event){
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setContentText("정말 삭제하시겠습니까?");
		Optional<ButtonType> AR = alert.showAndWait();
		if(AR.get() == ButtonType.OK) {		
			DBconnect conn = new DBconnect();
			Connection conn2 = conn.getConn();
			
			String sql = "delete from user_table"
					+ " where user_id=? ";
			try {
				PreparedStatement ps = conn2.prepareStatement(sql);
				ps.setString(1, idTextField.getText());
				ResultSet rs = ps.executeQuery();
				nameTextField.setText("");
				idTextField.setText("");
				pwPasswordField.setText("");
				pw2PasswordField.setText("");
				hakTextField.setText("");
				banTextField.setText("");
				bunTextField.setText("");
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
	}
	
	@FXML
	private void memberTableViewAction(MouseEvent event) {
		
		//만약 테이블뷰에서 선택한 값이 비어 있지 않으면
			//테이블뷰에서 클릭한 부분의 값을 위의 콘트롤에 표시해줘
		if(memberTableView.getSelectionModel().getSelectedItem() != null) {
			nameTextField.setText(memberTableView.getSelectionModel().getSelectedItem().getName());
			idTextField.setText(memberTableView.getSelectionModel().getSelectedItem().getId());
			pwPasswordField.setText(memberTableView.getSelectionModel().getSelectedItem().getPw());
			pw2PasswordField.setText(memberTableView.getSelectionModel().getSelectedItem().getPw());
			hakTextField.setText(memberTableView.getSelectionModel().getSelectedItem().getHak());
			banTextField.setText(memberTableView.getSelectionModel().getSelectedItem().getBan());
			bunTextField.setText(memberTableView.getSelectionModel().getSelectedItem().getBun());
			
		}
	}
	
	@FXML 
	private void readButtonAction(ActionEvent event){
	//DB 접속
		//사용자 테이블에 있는 자료 가져오기(SQL 작성, 실행)
		DBconnect conn = new DBconnect();
		Connection conn2 = conn.getConn();
		
		String sql = "select *"
				+" from user_table"
				+" order by idx";
		
		try {
			PreparedStatement ps = conn2.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			
			//어레이 리스트 만들기
			ObservableList<Memver> memberlist = FXCollections.observableArrayList();
			
			while(rs.next()) {
				//어레이리스트에 값을 넣어줘
				memberlist.add(new Memver(rs.getString(2), 
						rs.getString(3), 
						rs.getString(4), 
						rs.getString(5), 
						rs.getString(6), 
						rs.getString(7)
								)
						);
			}
			
			//어레이리스트에 있는 값을 테이블뷰에 세팅하기
			memberTableView.setItems(memberlist);
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}
	
	@FXML
	private void closeButtonAction(ActionEvent event) {
		Stage stage = (Stage)closeButton.getScene().getWindow();
		stage.close();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		nameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		idTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		pwTableColumn.setCellValueFactory(new PropertyValueFactory<>("pw"));
		hakTableColumn.setCellValueFactory(new PropertyValueFactory<>("hak"));
		banTableColumn.setCellValueFactory(new PropertyValueFactory<>("ban"));
		bunTableColumn.setCellValueFactory(new PropertyValueFactory<>("bun"));
	}
}
