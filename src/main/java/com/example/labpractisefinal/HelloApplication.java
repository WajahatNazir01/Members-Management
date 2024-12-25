package com.example.labpractisefinal;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Path;
import java.time.LocalDate;

public class HelloApplication extends Application {
    static final String PATH = "member.txt";
    @Override
    public void start(Stage stage) {

        Button addnewmember = new Button("Add New Member");
        Button viewallmmbers = new Button("View All Members");
        Button searchmember = new Button("Search Member");
        Button removemember = new Button("Remove Member");
        Label alertlabel= new Label();

        stylebutton(searchmember);
        stylebutton(addnewmember);
        stylebutton(viewallmmbers);
        stylebutton(removemember);

        VBox full = new VBox(20, addnewmember, viewallmmbers, searchmember,removemember,alertlabel);
        full.setAlignment(Pos.CENTER);

        addnewmember.setOnAction(e -> {
           addingmembers(stage);
        });

        viewallmmbers.setOnAction(e -> {
            displayingmembers(stage);
        });

        searchmember.setOnAction(e -> {
           searching(stage);
        });

        removemember.setOnAction(e -> {
            deletefromfile(stage);
        });

        Scene scene = new Scene(full, 620, 440);
        stage.setTitle("Main layout");
        stage.setScene(scene);
        stage.show();
    }
    public void stylebutton(Button button){
        button.setStyle("-fx-background-color: BLACK; -fx-text-fill: #b430c1; -fx-font-size: 15px;");
    }

    public void addingmembers(Stage stage){
        Label name = new Label("NAME:");
        TextField nameentered = new TextField();
        HBox namebox = new HBox(20,name, nameentered);
        namebox.setAlignment(Pos.CENTER);

        RadioButton male = new RadioButton("Male");
        RadioButton female = new RadioButton("Female");
        Label genderselected = new Label("Select gender:");
        ToggleGroup group = new ToggleGroup();
        male.setToggleGroup(group);
        female.setToggleGroup(group);
        HBox genders= new HBox(20,genderselected,male,female);
        genders.setAlignment(Pos.CENTER);

       // group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {})
        Label Selecttype = new Label("Select type of membership:");
        ObservableList<String> alltypes = FXCollections.observableArrayList("Standerd","Premium","VIP");
        ComboBox<String> type = new ComboBox(alltypes);
        HBox typebox = new HBox(20,Selecttype,type);
        typebox.setAlignment(Pos.CENTER);
      //  String selectedtype = type.getValue();

        Label dob = new Label("DOB:");
        DatePicker date = new DatePicker();
        HBox dobbox = new HBox(20,dob,date);
        dobbox.setAlignment(Pos.CENTER);

        Button savebutton = new Button("Save");
        savebutton.setAlignment(Pos.CENTER);
        stylebutton(savebutton);

        Button cancelbutton = new Button("Cancel");
        cancelbutton.setAlignment(Pos.CENTER);
        stylebutton(cancelbutton);
        cancelbutton.setOnAction(e -> {
           start(stage);
        });

        savebutton.setOnAction(e->{
            LocalDate selectedDate = date.getValue();
            String name1 = nameentered.getText();
            String gender = male.isSelected()?"Male":"Female";
            String membershiptype_= type.getValue();
            String date_ = selectedDate.toString();
            writetofile(PATH,name1,gender,membershiptype_,date_);

        });
        VBox fulllayout = new VBox(20,namebox,genders,typebox,dobbox,savebutton,cancelbutton);
        fulllayout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(fulllayout,620,440);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("add new members");

    }
    public void writetofile(String filepath,String name, String gender, String membership,String dob){
        Path p1 = Path.of(filepath);
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filepath,true))){
            bw.write(name+","+gender+","+membership+","+dob);
            bw.newLine();
            bw.close();

        }catch (IOException e){
            e.printStackTrace();
        }
    }


    public void displayingmembers(Stage stage) {
        ObservableList<Member> memberstotal = FXCollections.observableArrayList(readfromfile(PATH));

        TableView<Member> displayedmembers = new TableView<>();
        displayedmembers.setEditable(Boolean.FALSE);

        TableColumn<Member, String> name = new TableColumn<>("Name");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Member, String> gender = new TableColumn<>("Gender");
        gender.setCellValueFactory(new PropertyValueFactory<>("gender"));

        TableColumn<Member, String> membership = new TableColumn<>("Membership");
        membership.setCellValueFactory(new PropertyValueFactory<>("membershiptype"));

        TableColumn<Member, String> dob = new TableColumn<>("DOB");
        dob.setCellValueFactory(new PropertyValueFactory<>("dob"));

        displayedmembers.getColumns().addAll(name, gender, membership, dob);
        displayedmembers.setItems(memberstotal);

        Button cancelbutton = new Button("BACK");
        cancelbutton.setAlignment(Pos.TOP_LEFT);
        cancelbutton.setOnAction(e -> {
            start(stage);
        });
        stylebutton(cancelbutton);

        FlowPane fp = new FlowPane();
        fp.getChildren().addAll(cancelbutton, displayedmembers);

        Scene scene = new Scene(fp, 620, 440);
        stage.setScene(scene);
        stage.show();
    }

    public ObservableList<Member> readfromfile(String filename) {
        Path p1 = Path.of(filename);
        ObservableList<Member> members = FXCollections.observableArrayList();
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            while ((line = br.readLine()) != null) {
                String[] information = line.split(",");

                    if (information.length >= 4) {
                    String name = information[0];
                    String gender = information[1];
                    String membership = information[2];
                    String dob = information[3];

                    members.add(new Member(name, gender, membership, dob));
                } else {
                    System.out.println("some unexpected error in " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return members;
    }

    public void searching(Stage stage) {
        Label name = new Label("NAME:");
        TextField nameentered = new TextField();
        HBox namebox = new HBox(name,nameentered);
        namebox.setAlignment(Pos.CENTER);

        Button b1 = new Button("Search");
        stylebutton(b1);
       Label information =new Label();

        b1.setOnAction(e -> {
            information.setText(searchfromfile(PATH,nameentered));
        });

        VBox full = new VBox(20,namebox,b1,information);
        full.setAlignment(Pos.CENTER);
        Scene scene = new Scene(full,620,440);
        stage.setScene(scene);
        stage.show();

       // ObservableList<Member> memberstotal = FXCollections.observableArrayList();
    }

    public String searchfromfile(String filename, TextField searchfield) {
        Path p1 = Path.of(filename);
        String line;
        try(BufferedReader br1 = new BufferedReader(new FileReader(filename))){
            while ((line = br1.readLine()) != null) {
                String[] information = line.split(",");
                if (information.length >= 4) {
                    String name = information[0];
                    String gender = information[1];
                    String membership = information[2];
                    String dob = information[3];

                    if(name.equals(searchfield.getText())){
                        String searchedname = new Member(name,gender,membership,dob).toString();
                        return searchedname;
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

//    public void deletefromfile(Stage stage) {
//        Label name = new Label("NAME:");
//        TextField nameentered = new TextField();
//        HBox namebox = new HBox(name,nameentered);
//        namebox.setAlignment(Pos.CENTER);
//
//        Button b1 = new Button("Delete");
//        stylebutton(b1);
//        Label information =new Label();
//
//        b1.setOnAction(e -> {
//            information.setText(deletefromfile(PATH,nameentered));
//          //  information.setText(deletefromfile(PATH,String.valueOf(nameentered)));
//
//        });
//
//        VBox full = new VBox(20,namebox,b1,information);
//        full.setAlignment(Pos.CENTER);
//        Scene scene = new Scene(full,620,440);
//        stage.setScene(scene);
//        stage.show();
//    }
//    public ObservableList<Member> deletefromfile(String filepath, TextField nameentered) {
//        Path p1 = Path.of(filepath);
//        String line;
//        ObservableList<Member> members = FXCollections.observableArrayList();
//        boolean memberFound = false;
//
//        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
//            // Read all lines from file and add them to the members list
//            while ((line = br.readLine()) != null) {
//                String[] information = line.split(",");
//                if (information.length >= 4) {
//                    String name = information[0];
//                    String gender = information[1];
//                    String membership = information[2];
//                    String dob = information[3];
//
//                    // Check if the name matches the entered name
//                    if (name.equals(nameentered.getText())) {
//                        memberFound = true;  // Mark that the member was found
//                    } else {
//                        // Add members that do not match the entered name
//                        members.add(new Member(name, gender, membership, dob));
//                    }
//                }
//            }
//
//            if (!memberFound) {
//                System.out.println("not found");
//            }
//
//            // If the member was found, rewrite the file without that member
//            try (BufferedWriter bw = new BufferedWriter(new FileWriter(filepath))) {
//                for (Member member : members) {
//                    bw.write(member.getName() + "," + member.getGender() + "," + member.getMembershiptype() + "," + member.getDob());
//                    bw.newLine();
//                }
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return members;
//    }

    public void deletefromfile(Stage stage) {
        Label name = new Label("NAME:");
        TextField nameentered = new TextField();
        HBox namebox = new HBox(name, nameentered);
        namebox.setAlignment(Pos.CENTER);

        Button b1 = new Button("Delete");
        stylebutton(b1);
        Button cancel = new Button("Cancel");
        stylebutton(cancel);

        cancel.setOnAction(e -> {
            start(stage);
        });
        Label information = new Label();

        b1.setOnAction(e -> {
            // Get feedback message (either success or failure)
            String resultMessage = deleteFromFile(PATH, nameentered);
            information.setText(resultMessage); // Show result to the user
        });

        VBox full = new VBox(20, namebox, b1,cancel, information);
        full.setAlignment(Pos.CENTER);
        Scene scene = new Scene(full, 620, 440);
        stage.setScene(scene);
        stage.show();
    }

    public String deleteFromFile(String filepath, TextField nameentered) {
        Path p1 = Path.of(filepath);
        String line;
        ObservableList<Member> members = FXCollections.observableArrayList();
        boolean memberFound = false;

        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            // Read all lines from file and add them to the members list
            while ((line = br.readLine()) != null) {
                String[] information = line.split(",");
                if (information.length >= 4) {
                    String name = information[0];
                    String gender = information[1];
                    String membership = information[2];
                    String dob = information[3];

                    // Check if the name matches the entered name
                    if (name.equals(nameentered.getText())) {
                        memberFound = true;  // Mark that the member was found
                        // Skip adding this member to the list (delete it)
                    } else {
                        // Add members that do not match the entered name
                        members.add(new Member(name, gender, membership, dob));
                    }
                }
            }

            // If the member was found, rewrite the file without that member
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(filepath))) {
                for (Member member : members) {
                    bw.write(member.getName() + "," + member.getGender() + "," + member.getMembershiptype() + "," + member.getDob());
                    bw.newLine();
                }
            }

            // Return a message based on the result of the deletion
            if (memberFound) {
                return "Member successfully deleted.";
            } else {
                return "Member not found.";
            }

        } catch (IOException e) {
            e.printStackTrace();
            return "An error occurred while trying to delete the member.";
        }
    }



    public static void main(String[] args) {
        launch();
    }
}