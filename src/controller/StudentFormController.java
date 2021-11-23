package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Student;
import view.tm.StudentTM;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Observable;

public class StudentFormController {
    public JFXTextField txtId;
    public JFXTextField txtName;
    public JFXTextField txtContact;
    public JFXTextField txtAddress;
    public TableView tblStudent;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colContact;
    public TableColumn colAddress;
    public TableColumn colDelete;
    public AnchorPane studentFormContext;

    static ArrayList<Student> studentList = new ArrayList();

    public void initialize(){
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDelete.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }

    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/DashBoardForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) studentFormContext.getScene().getWindow();
        window.setScene(new Scene(load));
    }

    public void newStudentOnAction(ActionEvent actionEvent) {}

    public void saveUpdateOnAction(ActionEvent actionEvent){
         Student student = new Student(txtId.getText(), txtName.getText(), txtContact.getText(), txtAddress.getText());
            if (studentList.add(student)) {
                //alert ---> save
                new Alert(Alert.AlertType.CONFIRMATION, "Saved..", ButtonType.CLOSE).show();
                loadAllStudents();
            } else {
                //alert --->try again
                new Alert(Alert.AlertType.WARNING, "Try again..", ButtonType.CLOSE).show();
            }
        }

    private void loadAllStudents(){
        ObservableList<StudentTM> tmObservableList = FXCollections.observableArrayList();
        for (Student temp : studentList
             ) {
            Button btn = new Button("Delete");
            tmObservableList.add(
                    new StudentTM(temp.getId(),temp.getName(),temp.getContact(),temp.getAddress(),btn)
            );

            btn.setOnAction((e)->{
                studentList.remove(temp);
                loadAllStudents();
            });

        }
        tblStudent.setItems(tmObservableList);
    }
}
